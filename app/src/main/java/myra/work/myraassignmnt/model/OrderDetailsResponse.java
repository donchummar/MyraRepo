
package myra.work.myraassignmnt.model;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import myra.work.myraassignmnt.net.IDataModel;


public class OrderDetailsResponse implements IDataModel {

    @SerializedName("status")
    private Status mStatus;

    public Status getStatus() {
        return mStatus;
    }

    public void setStatus(Status status) {
        mStatus = status;
    }

    @Override
    public IDataModel parseData(String data) {
        Gson gson = new Gson();
        OrderDetailsResponse response = gson.fromJson(data, OrderDetailsResponse.class);
        return response;
    }
}
