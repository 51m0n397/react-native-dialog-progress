package com.dialogprogress;

import android.app.Activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.Callback;

public class DialogProgressModule extends ReactContextBaseJavaModule {
    private ProgressDialog progressDialog;

    public DialogProgressModule(ReactApplicationContext reactContext) {
        super(reactContext);

    }

    @Override
    public String getName() {
        return "DialogProgress";
    }


    @ReactMethod
    public void show(ReadableMap map, final Callback callBack){
         if(map == null){
                return;
          }
          Activity context = this.getCurrentActivity();
          String title = map.hasKey("title") ? map.getString("title") : null;
          String message = map.hasKey("message") ? map.getString("message") : null;
          String cancelText = map.hasKey("cancelText") ? map.getString("cancelText") : null;
          boolean isCancelable = map.hasKey("isCancelable") ? map.getBoolean("isCancelable") : false;

          this.progressDialog = new ProgressDialog(context);
          this.progressDialog.setTitle(title);
          this.progressDialog.setMessage(message);
          this.progressDialog.setCancelable(false);
          this.progressDialog.setButton(DialogInterface.BUTTON_NEGATIVE, cancelText, new DialogInterface.OnClickListener() {
               @Override
               public void onClick(DialogInterface dialog, int which) {
                    callBack.invoke("canceled");
               }
          });
          this.progressDialog.show();
    }

    @ReactMethod
    public void hide(Promise promise){
            if(this.progressDialog ==null){ return; }
          try {
              if(this.progressDialog.isShowing()){
                  this.progressDialog.dismiss();
                  this.progressDialog=null;
              }
              promise.resolve("HIDE");
          }catch (Exception e){
              promise.reject("error");
          }
    }
  
}
