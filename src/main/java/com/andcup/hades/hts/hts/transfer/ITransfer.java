package com.andcup.hades.hts.hts.transfer;

/**
 * Created by Amos
 * Date : 2017/5/2 10:08.
 * Description:
 */
public interface ITransfer {

    /**
     * 从远程服务器下载文件到本地.
     * @param dlFromRemotePath 远程文件路径
     * @param saveAsLocalPath  下载文件保存路径.
     * */
    boolean download(String dlFromRemotePath, String saveAsLocalPath);

    /**
     * 上传文件到服务器.
     * @param ulFromLocal 上传文件本地路径
     * @param saveAsRemotePath 上传文件服务器路径.
     * */
    boolean upload(String ulFromLocal, String saveAsRemotePath);
}
