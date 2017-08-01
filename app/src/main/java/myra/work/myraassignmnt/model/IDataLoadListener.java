package myra.work.myraassignmnt.model;


import com.android.volley.error.VolleyError;

import myra.work.myraassignmnt.net.IDataModel;


/**
 * Created by don on 1/8/17.
 */
public interface IDataLoadListener {
    void onDataLoadSuccess(IDataModel dataModel);

    void onDataLoadFailure(VolleyError volleyError);
}