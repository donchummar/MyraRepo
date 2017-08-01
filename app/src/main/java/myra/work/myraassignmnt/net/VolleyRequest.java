package myra.work.myraassignmnt.net;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.error.AuthFailureError;
import com.android.volley.error.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;

import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * Created by Don on 31/7/17.
 */
public class VolleyRequest extends Request<IDataModel> {

    private final Gson gson = new Gson();
    private final IDataModel iDataModel;
    private  Map<String, String> headers;
    private final Map<String, String> params;
    private final Response.Listener<IDataModel> listener;
    private final Response.ErrorListener errorListener;
    private final String mUrl;
    private String mRequestBody;

    private static final String PROTOCOL_CHARSET = "utf-8";
    private static final String PROTOCOL_CONTENT_TYPE = String.format("application/json; charset=%s", PROTOCOL_CHARSET);


    public VolleyRequest(String url, IDataModel clazz, Map<String, String> headers,
                            Response.Listener<IDataModel> listener, Response.ErrorListener errorListener, Map<String, String> bodyParam, int method) {
        super(method, url, errorListener);
        this.iDataModel = clazz;
        this.headers = headers;
        this.listener = listener;
        this.params = bodyParam;
        this.errorListener = errorListener;
        this.mUrl = url;
        //if(BuildConfig.DEBUGBUILD)System.out.println("url :: request" + mUrl);
        //if(BuildConfig.DEBUGBUILD)System.out.println("headers : " + this.headers);
    }

    public void setRequestBody(String requestBody){
       // if(BuildConfig.DEBUGBUILD)System.out.println("url :: request body" + requestBody);
        mRequestBody = requestBody;
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        return headers != null ? headers : super.getHeaders();
    }


    public  void updateHeader(Map<String, String> header){
        this.headers = header;
    }
    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return params != null ? params : super.getParams();
    }


    @Override
    protected void deliverResponse(IDataModel response) {
        listener.onResponse(response);
    }

    @Override
    protected Response<IDataModel> parseNetworkResponse(NetworkResponse response) {
        try {
            String json = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
           // if(BuildConfig.DEBUGBUILD)System.out.println("url ::" + mUrl);
            //if(BuildConfig.DEBUGBUILD)System.out.println("url :: response ::"+json);
            return Response.success(iDataModel.parseData(json), HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return Response.error(new VolleyError(e));
        }
    }

    @Override
    public void deliverError(final VolleyError error) {
       // if(BuildConfig.DEBUGBUILD)System.out.println("url " + mUrl);
        errorListener.onErrorResponse(error);

    }

    @Override
    public byte[] getBody() throws AuthFailureError {
        try {
            return mRequestBody == null ? null : mRequestBody.getBytes(PROTOCOL_CHARSET);
        } catch (UnsupportedEncodingException uee) {
            return null;
        }
    }


    @Override
    public String getBodyContentType() {
        return PROTOCOL_CONTENT_TYPE;
    }
}
