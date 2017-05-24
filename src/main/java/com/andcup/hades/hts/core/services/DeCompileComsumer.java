package com.andcup.hades.hts.core.services;

import com.andcup.hades.hts.HadesRootConfigure;
import com.andcup.hades.hts.core.MqConsumer;
import com.andcup.hades.hts.core.annotation.Consumer;
import com.andcup.hades.hts.core.exception.ConsumeException;
import com.andcup.hades.hts.core.model.Message;
import com.andcup.hades.hts.core.model.State;
import com.andcup.hades.hts.core.model.Task;
import com.andcup.hades.hts.core.model.Topic;
import com.thoughtworks.studios.javaexec.CommandExecutor;
import com.thoughtworks.studios.javaexec.CommandExecutorException;
import com.thoughtworks.studios.javaexec.LineHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.Arrays;

/**
 * Created by Amos
 * Date : 2017/5/23 11:45.
 * Description:
 */

@Consumer(topic = Topic.DECOMPILING, bind = Topic.COMPILING, match = Task.TYPE_COMPILE)
public class DeCompileComsumer extends MqConsumer {

    final Logger sLogger = LoggerFactory.getLogger(DeCompileComsumer.class);

    String command = "java -jar %s d -f -s %s -o %s";
    int outLineCount = 0;

    @Override
    public State doInBackground(Message<Task> message) throws ConsumeException {
        Task task = message.getData();
        if(Task.Global.hasDecompiled(task)){
            return message.getLastState();
        }
        outLineCount = 0;
        String apk = Task.Helper.getApkPath(task);
        String decodePath = Task.Helper.getApkDecodePath(task);
        command = String.format(command,
                HadesRootConfigure.sInstance.apktool,
                apk,
                decodePath
        );
        sLogger.info(command);

        State state = decompile(message, command);
        if(state == State.SUCCESS){
            // Copy AndroidManifest
            new File(Task.Helper.getAndroidManifest(task)).delete();
            new File(Task.Helper.getApkDecodeAndroidManifestPath(task)).renameTo(new File(Task.Helper.getAndroidManifest(task)));
            // Set Apk has decompile.
            Task.Global.setHasDecompiled(task, true);
        }
        return state;
    }

    private State decompile(Message<Task> message, String command) {
        CommandExecutor executor = new CommandExecutor(Arrays.asList(command.split(" ")));
        try {
            executor.run(new LineHandler() {
                public void handleLine(String line) {
                    if (outLineCount++ >= 20) {
                        return;
                    }
                    sLogger.info(message.getName() + " decode line : " + line + " out line count = " + outLineCount);
                }
            });
            return State.SUCCESS;
        } catch (CommandExecutorException e) {
            sLogger.info(message.getName() + " decode error : " + e.getCause().getMessage());
            return State.FAILED;
        }
    }
}
