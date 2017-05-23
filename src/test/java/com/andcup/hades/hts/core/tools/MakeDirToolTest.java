package com.andcup.hades.hts.core.tools;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Amos
 * Date : 2017/5/23 14:42.
 * Description:
 */
public class MakeDirToolTest {
    @Test
    public void mkdir() throws Exception {
        MakeDirTool.mkdir("../temp/apk/601");
        MakeDirTool.mkdir("../temp/apk/602");
    }

    @Test
    public void mkdirByPath() throws Exception {
        MakeDirTool.mkdirByPath("../temp/apk/603/test.log");
        MakeDirTool.mkdirByPath("../temp/apk/603/test1.log");
    }

}