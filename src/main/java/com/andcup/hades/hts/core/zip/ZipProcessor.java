package com.andcup.hades.hts.core.zip;

import com.andcup.hades.hts.core.exception.ConsumeException;
import com.andcup.hades.hts.server.utils.LogUtils;
import org.zeroturnaround.zip.*;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;

/**
 * Created by Amos
 * Date : 2017/6/2 17:10.
 * Description:
 */
public interface ZipProcessor {

    boolean onProcessor(String zip, String target, String compressFile) throws ConsumeException;

    ZipProcessor APK = new ZipProcessor() {
        @Override
        public boolean onProcessor(String src, String dst, String appendFile) {
            File file = new File(appendFile);
            String metaInf = "META-INF/" + file.getName();
            ZipEntrySource[] addedEntries = new ZipEntrySource[]{new FileSource(metaInf, file)};
            try{
                ZipUtil.addEntries(new File(src), addedEntries, new File(dst));
            }catch (ZipException e){
                throw new ConsumeException(e.getMessage());
            }
            return true;
        }
    };

    ZipProcessor IPA = new ZipProcessor() {
        @Override
        public boolean onProcessor(String src, String dst, String appendFile) throws ConsumeException {
            try {
                ZipUtil.iterate(new File(src), new ZipInfoCallback() {
                    @Override
                    public void process(ZipEntry zipEntry) throws IOException {
                        if(zipEntry.getName().contains("_CodeSignature")){
                            throw new ConsumeException(zipEntry.getName());
                        }
                    }

                });
            }catch (ConsumeException e){
                File file = new File(appendFile);
                String codeSignature = e.getMessage() + file.getName();
                LogUtils.info(ZipProcessor.class, codeSignature);

                ZipEntrySource[] addedEntries = new ZipEntrySource[]{new FileSource(codeSignature, file)};
                try{
                    ZipUtil.addEntries(new File(src), addedEntries, new File(dst));
                }catch (ZipException e1){
                    throw new ConsumeException(e.getMessage());
                }
                return true;
            }
            return false;
        }
    };

    ZipProcessor PREPARE = new ZipProcessor() {
        @Override
        public boolean onProcessor(String zip, String target, final String compressFile) throws ConsumeException {
            ZipUtil.unpack(new File(zip), new File(target), new NameMapper() {
                @Override
                public String map(String s) {
                    if(s.startsWith(compressFile)){
                        LogUtils.info(ZipProcessor.class, "  " + s);
                       return s;
                    }
                    return null;
                }
            });
            return true;

//            try {
//                ZipUtil.iterate(new File(zip), new ZipEntryCallback() {
//                    @Override
//                    public void process(InputStream inputStream, ZipEntry zipEntry) throws IOException {
//                        LogUtils.info(ZipProcessor.class, "  " + zipEntry.getName());
//                        if(zipEntry.getName().contains(compressFile)){
//                            throw new ConsumeException(zipEntry.getName());
//                        }
//                    }
//                });
//            }catch (ConsumeException e){
//                String unZipFile = e.getMessage();
//
//                ZipUtil.unpackEntry(new File(zip), unZipFile, new File(target));
//                LogUtils.info(ZipProcessor.class, " 解压 : " + unZipFile + " 成功.");
//                return true;
//            }
//            return false;
        }
    };
}
