package myra.work.myraassignmnt.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.android.volley.error.VolleyError;

import myra.work.myraassignmnt.model.IDataLoadListener;
import myra.work.myraassignmnt.net.DataLoader;
import myra.work.myraassignmnt.net.IDataModel;

/**
 * Created by don on 1/8/17.
 */

public class BaseActivity extends AppCompatActivity implements IDataLoadListener {

    public DataLoader dataLoader;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dataLoader = new DataLoader(this);
    }

    @Override
    public void onDataLoadSuccess(IDataModel dataModel) {

    }

    @Override
    public void onDataLoadFailure(VolleyError volleyError) {

    }
}
