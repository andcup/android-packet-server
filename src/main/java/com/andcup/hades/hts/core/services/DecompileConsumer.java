package com.andcup.hades.hts.core.services;

import com.andcup.hades.hts.boot.HadesApplication;
import com.andcup.hades.hts.core.MqConsumer;
import com.andcup.hades.hts.core.annotation.Consumer;
import com.andcup.hades.hts.core.exception.ConsumeException;
import com.andcup.hades.hts.core.model.Message;
import com.andcup.hades.hts.core.model.State;
import com.andcup.hades.hts.core.model.Task;
import com.andcup.hades.hts.core.model.Topic;
import com.andcup.hades.hts.core.tools.CommandRunner;
import com.andcup.hades.httpserver.utils.LogUtils;

import java.io.File;

/**
 * Created by Amos
 * Date : 2017/5/23 11:45.
 * Description:
 */

@Consumer(topic = Topic.DECOMPILING, bind = Topic.COMPILING, match = Task.TYPE_ANDROID_COMPILE)
public class DecompileConsumer extends MqConsumer {

    final String command = "java -jar %s d -f -s %s -o %s";

    @Override
    public State doInBackground(Message<Task> message) throws ConsumeException {
        Task task = message.getData();

        String apk = Task.Helper.getDownloadPath(task);
        String decodePath = Task.Helper.getApkDecodePath(task);
        String formatCommand = String.format(command,
                HadesApplication.sInstance.r().getApkTool(),
                apk,
                decodePath
        );
        if(Task.Global.hasDecompiled(task)){
            LogUtils.info(DecompileConsumer.class,">>>>>>>> 已编译 : " + formatCommand + " <<<<<<<<<<<<<<<< ");
            return message.getLastState();
        }
        LogUtils.info(DecompileConsumer.class,formatCommand);
        State state = new CommandRunner(DecompileConsumer.class, message, formatCommand).exec(getTimeOut());
        if(state == State.SUCCESS){
            // Copy AndroidManifest
            new File(Task.Helper.getAndroidManifest(task)).delete();
            new File(Task.Helper.getApkDecodeAndroidManifestPath(task)).renameTo(new File(Task.Helper.getAndroidManifest(task)));
            // Set Apk has decompile.
            Task.Global.setHasDecompiled(task, true);
        }
        return state;
    }
}
