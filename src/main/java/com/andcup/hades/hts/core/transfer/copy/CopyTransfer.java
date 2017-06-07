package com.andcup.hades.hts.core.transfer.copy;

import com.andcup.hades.hts.config.F;
import com.andcup.hades.hts.core.exception.ConsumeException;
import com.andcup.hades.hts.core.transfer.Transfer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Date;

/**
 * Created by Amos
 * Date : 2017/6/1 14:02.
 * Description:
 */
public class CopyTransfer extends Transfer {

    public CopyTransfer(F.Server server) {
        super(server);
    }

    @Override
    public void abort() {

    }

    @Override
    public void dlFromRemote(String src, String dst) throws ConsumeException {
        try {
            copyFromChannel(new File(src), new File(dst));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void upToRemote(String src, String dst) throws ConsumeException {
        try {
            copyFromChannel(new File(src), new File(dst));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public long copyFromChannel(File src,File dst) throws Exception{
        long time=new Date().getTime();
        int length=2097152;
        FileInputStream in=new FileInputStream(src);
        FileOutputStream out=new FileOutputStream(dst);
        FileChannel inC=in.getChannel();
        FileChannel outC=out.getChannel();
        ByteBuffer b;
        while(true){
            if(inC.position()==inC.size()){
                inC.close();
                outC.close();
                return new Date().getTime()-time;
            }
            if((inC.size()-inC.position())<length){
                length=(int)(inC.size()-inC.position());
            }else {
                length = 2097152;
            }
            b=ByteBuffer.allocateDirect(length);
            inC.read(b);
            b.flip();
            outC.write(b);
            outC.force(false);
        }
    }
}
