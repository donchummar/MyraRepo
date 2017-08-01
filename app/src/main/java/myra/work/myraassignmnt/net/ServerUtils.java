package myra.work.myraassignmnt.net;

import android.content.Context;
import android.graphics.Bitmap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

import myra.work.myraassignmnt.model.OrderRequest;

/**
 * Created by don on 1/8/17.
 */

public class ServerUtils {
    public static HashMap<String, String> getHeaders(Context context) {
        HashMap<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("Authorization", "MRauth f637a551a4ba4e56b742d6f0541d62cd");
        return headers;
    }

    public static Map<String, String> getTextRequest(Map<Integer, OrderRequest> tags) {
        Map<String , String> jsonObject = new HashMap<>();
        try {
            jsonObject.put("markers" , getmarkers(tags));
            //jsonObject.put("prescription_image" , getCompressBitmap(bitmap) );
        }catch (Exception e){}
        return jsonObject;
    }

    private static String getmarkers(Map<Integer, OrderRequest> tags) {
        JSONArray markerArray = new JSONArray();
        if (tags != null){
            for (Map.Entry order: tags.entrySet()){
                OrderRequest orderData = (OrderRequest) order.getValue();
                JSONObject markerObject = new JSONObject();
                try {
                    markerObject.put("text", orderData.getTextOrder());
                    JSONObject positions = new JSONObject();
                    positions.put("x", orderData.getxCordinate());
                    positions.put("y", orderData.getyCordinate());
                    markerObject.put("position", positions);
                    markerArray.put(markerObject);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        return markerArray.toString();
    }

    public static Map<String, DataPart> getImageData(Bitmap bitmap){
        Map<String, DataPart> imageData = new HashMap<>();
        DataPart dataPart = new DataPart("file_name.jpg", getCompressBitmap(bitmap), "image/jpeg");
        imageData.put("prescription_image", dataPart);
        return imageData;

    }

    public static byte[] getCompressBitmap(Bitmap bitmap) {

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 75, stream);
        return stream.toByteArray();
       //return Base64.encodeToString(stream.toByteArray(), Base64.NO_WRAP);
    }
}
