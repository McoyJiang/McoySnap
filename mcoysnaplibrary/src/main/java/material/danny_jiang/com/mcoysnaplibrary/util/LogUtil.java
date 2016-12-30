package material.danny_jiang.com.mcoysnaplibrary.util;

import android.util.Log;

/**
 * Created by axing on 16/12/30.
 */

public class LogUtil {
    private static final boolean MCOY_DEBUG = false;

    public static void e(String string) {
        if (MCOY_DEBUG)
        Log.e("TAG", string);
    }
}
