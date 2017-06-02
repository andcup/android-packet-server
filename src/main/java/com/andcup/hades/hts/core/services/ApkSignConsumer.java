package com.andcup.hades.hts.core.services;

import com.andcup.hades.hts.Hades;
import com.andcup.hades.hts.core.MqConsumer;
import com.andcup.hades.hts.core.annotation.Consumer;
import com.andcup.hades.hts.core.model.Message;
import com.andcup.hades.hts.core.model.State;
import com.andcup.hades.hts.core.model.Task;
import com.andcup.hades.hts.core.model.Topic;
import com.andcup.hades.hts.core.tools.CommandRunner;
import com.andcup.hades.hts.core.tools.MetaInfMatcher;
import com.andcup.hades.hts.server.utils.LogUtils;
import org.zeroturnaround.zip.ZipBreakException;

/**
 * Created by Amos
 * Date : 2017/5/23 11:45.
 * Description:
 */

@Consumer(topic = Topic.SIGN, bind = Topic.UPLOADING, match = Task.TYPE_ANDROID_COMPILE)
public class ApkSignConsumer extends MqConsumer{

    final String    command     = "jarsigner -verbose -keystore %s -storepass %s -signedjar %s -digestalg SHA1 -sigalg MD5withRSA %s %s";
    boolean         interrupt   = false;

    @Override
    public State doInBackground(Message<Task> message) {
        interrupt = false;
        Task task = message.getData();

        String unsignedApk = Task.Helper.getChannelUnsignedPath(task);
        String signedApk = Task.Helper.getChannelPath(task);

        String formatCommand = String.format(command,
                Hades.sInstance.r.getApkSignKeyPath(),
                Hades.sInstance.r.getPassword(),
                signedApk,
                unsignedApk,
                Hades.sInstance.r.getAlias()
        );
        LogUtils.info(ApkSignConsumer.class, formatCommand);
        CommandRunner runner = new CommandRunner(ApkSignConsumer.class, message, formatCommand);
        State state = runner.exec(getTimeOut());
        if(state == State.SUCCESS){
            state = waitForSigning(signedApk, getTimeOut(), formatCommand);
        }
        return state;
    }

    private State waitForSigning(String signedPath, long timeout, String command){
        long      loop     = 0;
        final int interval = 100;
        while (!match(signedPath)){
            //任务超时.
            if((loop++ * interval) > timeout){
                LogUtils.info(ApkSignConsumer.class, command + " time used > " + timeout);
                return State.FAILED;
            }
            //任务被中断.
            if(interrupt){
                LogUtils.info(ApkSignConsumer.class, command + " interrupt!" );
                return State.FAILED;
            }
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return State.SUCCESS;
    }

    @Override
    public void abort() {
        interrupt = true;
    }

    private boolean match(String signedPath){
        try{
            return MetaInfMatcher.match(signedPath);
        }catch (ZipBreakException e){
            return true;
        }
    }
}
