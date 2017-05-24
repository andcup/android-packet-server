package com.andcup.hades.hts.core.services;

import com.andcup.hades.hts.HadesRootConfigure;
import com.andcup.hades.hts.core.MqConsumer;
import com.andcup.hades.hts.core.annotation.Consumer;
import com.andcup.hades.hts.core.model.Message;
import com.andcup.hades.hts.core.model.State;
import com.andcup.hades.hts.core.model.Task;
import com.andcup.hades.hts.core.model.Topic;
import com.andcup.hades.hts.core.tools.AndroidManifestHelper;
import com.andcup.hades.hts.core.tools.CommandRunner;

/**
 * Created by Amos
 * Date : 2017/5/23 11:45.
 * Description:
 */

@Consumer(topic = Topic.SIGN, bind = Topic.UPLOADING, match = Task.TYPE_COMPILE)
public class ApkSignComsumer extends MqConsumer{

    //String command    = "jarsigner -verbose -keystore ${path} -storepass ${pass} -signedjar %s -digestalg SHA1 -sigalg MD5withRSA %s ${alias}";
    String command    = "jarsigner -verbose -keystore %s -storepass %s -signedjar %s -digestalg SHA1 -sigalg MD5withRSA %s %s";

    @Override
    public State doInBackground(Message<Task> message) {

        Task task = message.getData();

        String unsignedApk = Task.Helper.getChannelUnsignedPath(task);
        String signedApk = Task.Helper.getChannelPath(task);

        command = String.format(command,
                HadesRootConfigure.sInstance.keyStore.path,
                HadesRootConfigure.sInstance.keyStore.pass,
                unsignedApk,
                signedApk,
                HadesRootConfigure.sInstance.keyStore.alias
        );

        return new CommandRunner(ApkSignComsumer.class.getName(), message).exec(command);
    }
}
