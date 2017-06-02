package com.andcup.hades.hts.core.services;

import com.andcup.hades.hts.core.MqConsumer;
import com.andcup.hades.hts.core.annotation.Consumer;
import com.andcup.hades.hts.core.compress.ICompress;
import com.andcup.hades.hts.core.model.Message;
import com.andcup.hades.hts.core.model.State;
import com.andcup.hades.hts.core.model.Task;
import com.andcup.hades.hts.core.model.Topic;
import com.andcup.hades.hts.core.tools.IpaXmlMatcherEditor;
import com.andcup.hades.hts.core.tools.XmlMatchEditor;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Amos
 * Date : 2017/6/1 16:06.
 * Description:
 */

@Consumer(topic = Topic.IPA_COMPRESS, bind = Topic.COMPRESS, match = Task.TYPE_IOS_QUICK)
public class IpaCompressConsumer extends MqConsumer {

    final String introduction = "introductionValue";
    final String sourceId = "sourceidValue";
    final String other = "otherValue";

    Map<String, String> maps = new HashMap<String, String>();

    @Override
    protected State doInBackground(Message<Task> message) {

        Task task = message.getData();
        /**修改数据.*/
        maps.put(introduction, task.id);
        maps.put(sourceId, task.sourceId);
        maps.put(other, task.other);
        /**
         * 编辑pList文件.
         * */
        new IpaXmlMatcherEditor(XmlMatchEditor.Match.IPA).edit("r/template/ios_quick.htr",Task.Helper.getPlist(task), maps);
        /**
         * 压缩plist文件.
         * */
        File pListFile = new File(Task.Helper.getPlist(task));
        State state = ICompress.IPA.pack(Task.Helper.getDownloadPath(task),
                Task.Helper.getChannelPath(task),
                pListFile) ?
                State.SUCCESS : State.FAILED;

        pListFile.delete();
        return state;
    }
}

