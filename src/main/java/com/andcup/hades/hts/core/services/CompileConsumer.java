package com.andcup.hades.hts.core.services;

import com.andcup.hades.hts.HadesRootConfigure;
import com.andcup.hades.hts.core.MqConsumer;
import com.andcup.hades.hts.core.annotation.Consumer;
import com.andcup.hades.hts.core.exception.ConsumeException;
import com.andcup.hades.hts.core.model.Message;
import com.andcup.hades.hts.core.model.State;
import com.andcup.hades.hts.core.model.Task;
import com.andcup.hades.hts.core.model.Topic;
import com.andcup.hades.hts.core.tools.AndroidManifestHelper;
import com.andcup.hades.hts.core.tools.CommandRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Amos
 * Date : 2017/5/23 11:45.
 * Description:
 */

@Consumer(topic = Topic.COMPILING, bind = Topic.SIGN, match = Task.TYPE_COMPILE)
public class CompileConsumer extends MqConsumer {

    final Logger sLogger = LoggerFactory.getLogger(CompileConsumer.class);

    final String introduction = "introduction";
    final String sourceid = "sourceid";
    final String other = "other";

    final String command = "java -jar %s b %s -o %s";

    Map<String, String> maps = new HashMap<String, String>();

    @Override
    public State doInBackground(Message<Task> message) throws ConsumeException {

        Task task = message.getData();
        /**修改数据.*/
        maps.put(introduction, task.id);
        maps.put(sourceid, task.sourceId);
        maps.put(other, task.other);

        AndroidManifestHelper.edit(Task.Helper.getAndroidManifest(task),
                Task.Helper.getApkDecodeAndroidManifestPath(task),
                maps);

        String channelApk = Task.Helper.getChannelUnsignedPath(task);
        String decodePath = Task.Helper.getApkDecodePath(task);
        String formatCommand = String.format(command,
                HadesRootConfigure.sInstance.apktool,
                decodePath,
                channelApk
        );
        sLogger.info(formatCommand);
        /**开始编译.*/
        return new CommandRunner(DecompileConsumer.class.getName(), message).exec(formatCommand);
    }
}
