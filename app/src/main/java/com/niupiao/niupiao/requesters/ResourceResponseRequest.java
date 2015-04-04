package com.niupiao.niupiao.requesters;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

/**
 * Created by Inanity on 4/4/2015.
 * Taken from: http://grepcode.com/file/repo1.maven.org/maven2/com.mcxiaoke.volley/library/1.0.6/com/android/volley/toolbox/JsonArrayRequest.java
 * and : http://stackoverflow.com/questions/18048806/volley-sending-a-post-request-using-jsonarrayrequest
 */

//TODO: Better to do this, or better to directly modify JSONArrayRequest? Legal issues?
public class ResourceResponseRequest extends JsonRequest<JSONArray> {
    private ResourceCallback callback;

    public ResourceResponseRequest(ResourceCallback callback, int method, String url, JSONObject jsonRequest,
                            Response.Listener<JSONArray> listener) {
        super(method, url, (jsonRequest == null) ? null : jsonRequest.toString(),
                listener, new NiuErrorListener(callback));
        this.callback = callback;
    }

    @Override
    protected Response<JSONArray> parseNetworkResponse(NetworkResponse response) {
        try {
            String jsonString =
                            new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            return Response.success(new JSONArray(jsonString),
                                HttpHeaderParser.parseCacheHeaders(response));
                    } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
            } catch (JSONException je) {
            return Response.error(new ParseError(je));
            }
        }
}
