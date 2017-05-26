package com.andcup.hades.hts.core.services;

import com.andcup.hades.hts.core.MqConsumer;
import com.andcup.hades.hts.core.annotation.Consumer;
import com.andcup.hades.hts.core.model.*;
import com.andcup.hades.hts.server.utils.LogUtils;

import java.io.File;

/**
 * Created by Amos
 * Date : 2017/5/25 13:44.
 * Description:
 */

@Consumer(topic = Topic.GARBAGE_CLEAN, bind = Topic.COMPLETE, last = State.DEFAULT)
public class GarbageCleanConsumer extends MqConsumer {


    @Override
    protected State doInBackground(Message<Task> message) {
        Task task = message.getData();

        LogUtils.info(GarbageCleanConsumer.class, " clean : " + Task.Helper.getChannelPath(task));
        // Clean unsigned apk.
        new File(Task.Helper.getChannelPath(task)).delete();
        LogUtils.info(GarbageCleanConsumer.class," clean : " + Task.Helper.getChannelUnsignedPath(task));
        // Clean signed apk.
        new File(Task.Helper.getChannelUnsignedPath(task)).delete();
        LogUtils.info(GarbageCleanConsumer.class," clean : " + Task.Helper.getRulePath(task));
        // Clean rule file
        new File(Task.Helper.getRulePath(task)).delete();
        return message.getLastState();
    }
}
