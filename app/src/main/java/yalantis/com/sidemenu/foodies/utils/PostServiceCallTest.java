package yalantis.com.sidemenu.foodies.utils;


import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import yalantis.com.sidemenu.foodies.app.MyApplication;


public abstract class PostServiceCallTest implements IService2 {

    String response = null;
    private String url;
    private String object;

    public abstract void response(String response);

    public abstract void error(String error);

    public synchronized final PostServiceCallTest start() {
        call();
        return this;

    }

    public PostServiceCallTest(String url, String object) {
        this.url = url;
        this.object = object;
    }


    public void call() {

        JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST, url, object, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jobj) {

                try{
               response(jobj.toString());
                }catch(Exception e){
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                        error(error.getMessage());
            }
        });

        // time out, retry, multipiers
       req.setRetryPolicy(new DefaultRetryPolicy(5000,0,0));
       MyApplication.getInstance().addToRequestQueue(req);
    }
}

