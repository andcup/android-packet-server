package com.andcup.hades.hts.core.compress;

import com.andcup.hades.hts.core.exception.ConsumeException;
import org.zeroturnaround.zip.FileSource;
import org.zeroturnaround.zip.ZipEntrySource;
import org.zeroturnaround.zip.ZipException;
import org.zeroturnaround.zip.ZipUtil;

import java.io.File;

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

    ICompress ZT = new ICompress() {
        @Override
        public boolean pack(String src, String dst, File appendFile) {
            String metaInf = "META-INF/" + appendFile.getName();
            ZipEntrySource[] addedEntries = new ZipEntrySource[]{
                    new FileSource(metaInf, appendFile)
            };
            try{
                ZipUtil.addEntries(new File(src), addedEntries, new File(dst));
            }catch (ZipException e){
                throw new ConsumeException(e.getMessage());
            }
            return true;
        }
    };
}
