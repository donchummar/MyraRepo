package myra.work.myraassignmnt.net;

import android.content.Context;
import android.graphics.Bitmap;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.error.VolleyError;

import java.util.Map;

import myra.work.myraassignmnt.app.VolleyController;
import myra.work.myraassignmnt.model.IDataLoadListener;
import myra.work.myraassignmnt.model.OrderDetailsResponse;
import myra.work.myraassignmnt.model.OrderRequest;
import myra.work.myraassignmnt.utils.Constants;

/**
 * Created by don on 1/8/17.
 */

public class DataLoader implements Response.Listener<IDataModel>, Response.ErrorListener {

    private final IDataLoadListener iDataLoadlistener;

    public DataLoader(IDataLoadListener listener) {
        iDataLoadlistener = listener;
    }


    @Override
    public void onErrorResponse(VolleyError error) {
        if (error == null) return;
        if (iDataLoadlistener != null) {
            iDataLoadlistener.onDataLoadFailure(error);
        }
    }

    @Override
    public void onResponse(IDataModel response) {
        if (iDataLoadlistener != null) {
            iDataLoadlistener.onDataLoadSuccess(response);
        }
    }

    public void postCandidateInformation(Context context, Map<Integer, OrderRequest>textData, Bitmap bitmap) {
        String url = Constants.ORDER_URL;
        VolleyMutipartReq request = new VolleyMutipartReq(url, new OrderDetailsResponse(),
                ServerUtils.getHeaders(context), this, this,
                ServerUtils.getTextRequest(textData),
                ServerUtils.getImageData(bitmap), Request.Method.POST);
        VolleyController.getRequestQueue(context).add(request);
    }

}
