package com.andcup.hades.hts.hts.fragment;

import com.andcup.hades.hts.hts.base.TransferFragment;

/**
 * Created by Amos
 * Date : 2017/4/28 10:17.
 * Description: 判断文件是否存在
 */
public class FileUploadFragment extends TransferFragment {

    public boolean download(String dlFromRemotePath, String saveAsLocalPath) {
        return false;
    }

    public boolean upload(String ulFromLocal, String saveAsRemotePath) {
        return false;
    }
}
