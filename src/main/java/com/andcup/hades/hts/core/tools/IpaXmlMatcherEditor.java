package com.andcup.hades.hts.core.tools;

import com.andcup.hades.hts.core.services.IpaCompressConsumer;
import com.andcup.hades.httpserver.utils.LogUtils;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * Created by Amos
 * Date : 2017/6/1 17:07.
 * Description:
 */
public class IpaXmlMatcherEditor extends XmlMatchEditor {

    public IpaXmlMatcherEditor(Match match) {
        super(match);
    }

    @Override
    public boolean edit(String src, String dst, Map<String, String> maps) {
        try {
            String fileValue = new String(FileUtils.load(src), "UTF-8");
            LogUtils.info(IpaCompressConsumer.class, fileValue);
            for (String key : maps.keySet()) {
                String value = maps.get(key);
                fileValue = fileValue.replace(key, value);
            }
            FileUtils.store(dst, fileValue);
            return true;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return false;
    }
}
