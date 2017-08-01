package myra.work.myraassignmnt.net;

import java.io.Serializable;

/**
 * Created by Don on 31/7/17.
 */
public interface IDataModel extends Serializable {
    IDataModel parseData(String data);
}
