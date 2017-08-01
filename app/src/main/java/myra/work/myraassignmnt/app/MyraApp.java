package myra.work.myraassignmnt.app;

import android.app.Application;

/**
 * Created by don on 31/7/17.
 */

public class MyraApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        VolleyController.init(getApplicationContext());
    }
}
