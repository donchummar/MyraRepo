package myra.work.myraassignmnt.model;

import myra.work.myraassignmnt.net.IDataModel;

/**
 * Created by don on 1/8/17.
 */

public abstract class MyraModel implements IDataModel {
    @Override
    public IDataModel parseData(String data) {
        return null;
    }
}
