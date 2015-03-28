package sample.com.advancedflickrsample.loaders;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

/**
 * Created by hzaied on 3/28/15.
 */
public abstract class AbstractLoader<T> extends AsyncTaskLoader<T> {
    T mResult;

    public AbstractLoader(Context context) {
        super(context);
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        if (mResult != null) {
            deliverResult(mResult);
        }

        if (takeContentChanged() || mResult == null) {
            forceLoad();
        }
    }

    @Override
    public void deliverResult(T data) {
        if (isReset()) {
            releaseResource(data);
            return;
        }

        T oldResult = mResult;
        mResult = data;

        if (isStarted()) {
            super.deliverResult(data);
        }

        if (oldResult != data && oldResult != null) {
            releaseResource(oldResult);
        }
    }

    @Override
    protected void onStopLoading() {
        super.onStopLoading();
        cancelLoad();
    }

    @Override
    protected void onReset() {
        super.onReset();

        onStopLoading();

        releaseResource(mResult);
        mResult = null;
    }

    @Override
    public void onCanceled(T data) {
        super.onCanceled(data);
    }

    protected void releaseResource(T result) {
    }
}
