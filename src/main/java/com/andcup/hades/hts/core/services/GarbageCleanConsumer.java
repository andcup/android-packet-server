package com.andcup.hades.hts.core.services;

import com.andcup.hades.hts.core.MqConsumer;
import com.andcup.hades.hts.core.annotation.Consumer;
import com.andcup.hades.hts.core.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

/**
 * Created by Amos
 * Date : 2017/5/25 13:44.
 * Description:
 */

@Consumer(topic = Topic.GARBAGE_CLEAN, bind = Topic.COMPLETE, last = State.DEFAULT)
public class GarbageCleanConsumer extends MqConsumer {

    Logger logger = LoggerFactory.getLogger(GarbageCleanConsumer.class.getName());

    @Override
    protected State doInBackground(Message<Task> message) {
        Task task = message.getData();

        logger.info(" clean : " + Task.Helper.getChannelPath(task));
        // Clean unsigned apk.
        new File(Task.Helper.getChannelPath(task)).delete();
        logger.info(" clean : " + Task.Helper.getChannelUnsignedPath(task));
        // Clean signed apk.
        new File(Task.Helper.getChannelUnsignedPath(task)).delete();
        logger.info(" clean : " + Task.Helper.getRulePath(task));
        // Clean rule file
        new File(Task.Helper.getRulePath(task)).delete();
        return message.getLastState();
    }
}
