package com.andcup.hades.hts.core.model;

/**
 * Created by Amos
 * Date : 2017/5/2 9:57.
 * Description:
 */
public enum Topic {

    /*
     完成任务细分：
     1. 打包成功：    0
     3. 打包失败：   -1

     失败任务细分：
     1.上传失败：    -1
     2.下载失败：    -2
     3.反编译失败：  -3
     4.编译失败：    -4
     5.修改数据失败： -5
     6.打包超时：    -6
     7.打包强制结束： -7
     8.反馈超时：    -8
     */
 /*
         等待打包:
         1. 已加入等待队列:     0
         3. 检查文件是否存在:   1

         打包任务状态细分：
         1. 下载中 ：    2
         2. 上传中 ：    3
         3. 反编译 ：    4
         4. 数据修改：   5
         5. 编译：      6
         6. 反馈：      7
         */
    DOWNLOADING(1, "下载..."),
    COMPRESS(2, "压缩..."),
    DECOMPILING(3, "反编译..."),
    COMPILING(4, "编译..."),
    SIGN(5, "签名..."),
    UPLOADING(6, "上传..."),
    COMPLETE(7, "完成...");

    int    code;
    String name;

    Topic(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
