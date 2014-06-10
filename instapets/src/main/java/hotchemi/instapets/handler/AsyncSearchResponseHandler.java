package hotchemi.instapets.handler;

import com.loopj.android.http.AsyncHttpResponseHandler;

import hotchemi.instapets.events.BusProvider;
import hotchemi.instapets.events.SearchFailureEvent;
import hotchemi.instapets.events.SearchSuccessEvent;

public class AsyncSearchResponseHandler extends AsyncHttpResponseHandler {

    @Override
    public void onSuccess(String content) {
        BusProvider.getInstance().post(new SearchSuccessEvent(content));
        super.onSuccess(content);
    }

    @Override
    public void onFailure(int statusCode, Throwable error, String content) {
        BusProvider.getInstance().post(new SearchFailureEvent(statusCode, content));
        super.onFailure(statusCode, error, content);
    }

}
