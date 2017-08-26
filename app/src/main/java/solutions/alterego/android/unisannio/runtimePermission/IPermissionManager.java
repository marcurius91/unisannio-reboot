package solutions.alterego.android.unisannio.runtimePermission;

import android.support.annotation.NonNull;

public interface IPermissionManager {

    void managingPermission();

    void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults);
}
