
package myra.work.myraassignmnt.app;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.Volley;

public class VolleyController {
	
	private static RequestQueue mRequestQueue;
	
	private VolleyController() {}
	
	/**
	 * initialize Volley
	 */
	public static void init(Context context) {
		mRequestQueue = Volley.newRequestQueue(context);
		mRequestQueue.start();
	}
	
	public static RequestQueue getRequestQueue(Context context) {
		if (mRequestQueue != null) {
			return mRequestQueue;
		} else {
			return mRequestQueue = Volley.newRequestQueue(context, new HurlStack());
		}
	}
}
