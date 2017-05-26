package com.andcup.hades.hts.core.services;

import com.andcup.hades.hts.HadesRootConfigure;
import com.andcup.hades.hts.core.MqConsumer;
import com.andcup.hades.hts.core.annotation.Consumer;
import com.andcup.hades.hts.core.model.Message;
import com.andcup.hades.hts.core.model.State;
import com.andcup.hades.hts.core.model.Task;
import com.andcup.hades.hts.core.model.Topic;
import com.andcup.hades.hts.core.tools.CommandRunner;
import com.andcup.hades.hts.core.tools.MetaInfMatcher;
import com.andcup.hades.hts.server.utils.LogUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zeroturnaround.zip.ZipBreakException;

/**
 * Created by Amos
 * Date : 2017/5/23 11:45.
 * Description:
 */

@Consumer(topic = Topic.SIGN, bind = Topic.UPLOADING, match = Task.TYPE_COMPILE)
public class ApkSignConsumer extends MqConsumer{
    //String command    = "jarsigner -verbose -keystore ${path} -storepass ${pass} -signedjar %s -digestalg SHA1 -sigalg MD5withRSA %s ${alias}";
    final String command    = "jarsigner -verbose -keystore %s -storepass %s -signedjar %s -digestalg SHA1 -sigalg MD5withRSA %s %s";
    private boolean mIsInterrupt = false;
    @Override
    public State doInBackground(Message<Task> message) {
        mIsInterrupt = false;
        Task task = message.getData();

        String unsignedApk = Task.Helper.getChannelUnsignedPath(task);
        String signedApk = Task.Helper.getChannelPath(task);

        String formatCommand = String.format(command,
                HadesRootConfigure.sInstance.keyStore.path,
                HadesRootConfigure.sInstance.keyStore.pass,
                signedApk,
                unsignedApk,
                HadesRootConfigure.sInstance.keyStore.alias
        );
        LogUtils.info(ApkSignConsumer.class, formatCommand);
        State state = new CommandRunner(ApkSignConsumer.class, message).exec(formatCommand);
        if(state == State.SUCCESS){
            waitForSigning(signedApk);
        }
        return state;
    }

    private void waitForSigning(String signedPath){
        while (!mIsInterrupt && !match(signedPath)){
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean match(String signedPath){
        try{
            return MetaInfMatcher.match(signedPath);
        }catch (ZipBreakException e){
            return true;
        }
    }
}
