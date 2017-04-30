package org.xuxiaoxiao.xiaoimagegallery;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import static android.Manifest.permission.ACCESS_NETWORK_STATE;
import static android.Manifest.permission.INTERNET;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

/**
 * Created by WuQiang on 2017/4/30.
 */

public class MediaItemActiviey extends UniversalFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new MediaItemFragment();
    }

    // 下面是跟权限有关的
    @Override
    protected String[] getDesiredPermissions() {
        return (new String[]{WRITE_EXTERNAL_STORAGE,INTERNET,ACCESS_NETWORK_STATE,READ_EXTERNAL_STORAGE});
//        return (new String[]{WRITE_EXTERNAL_STORAGE});
    }

    @Override
    protected void onPermissionDenied() {
        Toast.makeText(this, R.string.msg_sorry, Toast.LENGTH_LONG).show();
        finish();
    }

    @Override
    protected void onReady(Bundle state) {

    }
}
