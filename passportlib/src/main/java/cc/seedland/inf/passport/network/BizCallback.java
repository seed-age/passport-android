package cc.seedland.inf.passport.network;

import android.os.Bundle;

import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;

import org.json.JSONObject;

import java.lang.ref.Reference;
import java.util.Iterator;

import cc.seedland.inf.network.JsonCallback;
import cc.seedland.inf.passport.base.BaseViewGuard;
import cc.seedland.inf.passport.base.IPassportView;
import cc.seedland.inf.passport.util.Constant;
import cc.seedland.inf.passport.util.LogUtil;

/**
 * Created by xuchunlei on 2017/11/16.
 */

public abstract class BizCallback<T extends IPassportView> extends JsonCallback{

    protected Reference<T> view;

    public BizCallback(Reference<T> view) {
        this.view = view;
    }

    @Override
    public void onStart(Request<JSONObject, ? extends Request> request) {
        super.onStart(request);
        BaseViewGuard.callShowloadingSafely(view);

    }

    @Override
    public void onSuccess(Response<JSONObject> response) {
        LogUtil.d(Constant.TAG, "api-response:" + response.body().toString());
        try {
            doSuccess(response.body());
        }catch (Throwable t) {

        }

    }

    @Override
    public void onError(Response<JSONObject> response) {
        super.onError(response);
        String msg = "unknown error";
        if(response != null && response.getException() != null) {
            msg = response.getException().getMessage();

        }
        BaseViewGuard.callShowToastSafely(view, msg != null && msg.trim().length() > 0 ? msg : "unknown error");
    }

    @Override
    public void onFinish() {
        super.onFinish();
        BaseViewGuard.callHideloadingSafely(view);
    }

    protected abstract void doSuccess(JSONObject data) throws Throwable;

    protected static Bundle toArgs(JSONObject data) {
        Bundle args = new Bundle();
        if(data != null) {
            try {
                Iterator<String> iterator = data.keys();
                while (iterator.hasNext()){
                    String key = iterator.next();
                    args.putString(key, data.opt(key) + "");
//                    if(value instanceof String) {
//                        args.putString(key, "" + value);
//                    }else (if value instanceof )
                }
            } catch (Exception e) {

            }

        }

        return args;
    }
}
