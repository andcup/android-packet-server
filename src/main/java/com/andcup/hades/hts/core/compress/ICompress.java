package com.andcup.hades.hts.core.compress;

import com.andcup.hades.hts.core.exception.ConsumeException;
import com.andcup.hades.hts.server.utils.LogUtils;
import org.zeroturnaround.zip.*;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;

/**
 * Created by Amos
 * Date : 2017/5/23 16:42.
 * Description:
 */
public interface ICompress {
    /**
     * @param src 压缩源文件.
     * @param dst 压缩后文件路径.
     * @param appendFile 要添加的文件路径
     * */
    boolean pack(String src, String dst, File appendFile) throws ConsumeException;

    ICompress APK = new ICompress() {
        @Override
        public boolean pack(String src, String dst, File appendFile) {
            String metaInf = "META-INF/" + appendFile.getName();
            ZipEntrySource[] addedEntries = new ZipEntrySource[]{new FileSource(metaInf, appendFile)};
            try{
                ZipUtil.addEntries(new File(src), addedEntries, new File(dst));
            }catch (ZipException e){
                throw new ConsumeException(e.getMessage());
            }
            return true;
        }
    };

    ICompress IPA = new ICompress() {
        @Override
        public boolean pack(String src, String dst, File appendFile) throws ConsumeException {
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
                String codeSignature = e.getMessage() + appendFile.getName();
                LogUtils.info(ICompress.class, codeSignature);

                ZipEntrySource[] addedEntries = new ZipEntrySource[]{new FileSource(codeSignature, appendFile)};
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
}
