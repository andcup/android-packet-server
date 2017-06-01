package com.andcup.hades.hts.test;

import com.andcup.hades.hts.core.compress.ICompress;
import com.andcup.hades.hts.core.services.IpaCompressConsumer;
import com.andcup.hades.hts.core.tools.IpaXmlMatcherEditor;
import com.andcup.hades.hts.core.tools.XmlMatchEditor;
import com.andcup.hades.hts.server.utils.LogUtils;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Amos
 * Date : 2017/6/1 16:17.
 * Description:
 */
public class PListTest {


    public static void main(String[] args){
        String pList = IpaCompressConsumer.class.getClassLoader().getResource("YLinfo.plist").getPath();
        File listFile = new File(pList);
        if(listFile.exists()){
            long size = listFile.length();
            LogUtils.info(PListTest.class, " size = " + size);
        }
        final String introduction = "introductionValue";
        final String sourceId = "sourceidValue";
        final String other = "otherValue";

        Map<String, String> maps = new HashMap<String, String>();
        maps.put(introduction, "1");
        maps.put(sourceId, "1");
        maps.put(other, "1");
        new IpaXmlMatcherEditor(XmlMatchEditor.Match.IPA).edit(pList,
                "YLInfo.plist",
                maps);

        long size = new File("YLInfo.plist").length();
        LogUtils.info(PListTest.class, " size = " + size);

        ICompress.IPA.pack("YLSDK.zip", "YLSDK2.zip", new File("YLInfo.plist"));
    }
}
