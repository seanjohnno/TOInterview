package timeout.slang.com.model.rx;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import rx.Observable;
import rx.Subscriber;

/**
 * Creates an Rx Observable out of a volley request. Makes it easy to chain with parsing logic
 */
public class RxOnSubscribeVolleyRequest implements Observable.OnSubscribe<String> {

    /* ------------------------------------------------------------------------------------------
     * Member Data
     * ------------------------------------------------------------------------------------------ */

    /**
     * Url we're requesting
     */
    private String mUrl;

    /**
     * Volley RequestQueue
     */
    private RequestQueue mQueue;

    /* ------------------------------------------------------------------------------------------
     * Construction + Public Methods
     * ------------------------------------------------------------------------------------------ */

    /**
     * @param queue     Volley RequestQueue
     * @param url       Url we're requesting
     */
    public RxOnSubscribeVolleyRequest(RequestQueue queue, String url) {
        mUrl = url;
        mQueue = queue;
    }

    /**
     * @param subscriber    Called when this observer is subscribed to
     */
    @Override
    public void call(final Subscriber<? super String> subscriber) {
        // Create and send the volley request
        mQueue.add( new StringRequest(Request.Method.GET, mUrl,
                new Response.Listener<String>() { public void onResponse(String response) {
                    subscriber.onNext(response);
                }},
                new Response.ErrorListener() { public void onErrorResponse(VolleyError error) {
                    subscriber.onError(error);
                }}
        ));
    }
}
