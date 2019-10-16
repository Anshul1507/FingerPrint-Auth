package com.example.fingerprintauth_tvac;

import android.app.Activity;
import android.content.Context;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.CancellationSignal;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.widget.ImageView;
import android.widget.TextView;

@RequiresApi(api = Build.VERSION_CODES.M)
public class FingerprintHandler extends FingerprintManager.AuthenticationCallback {

    private Context context;

    public FingerprintHandler(Context context){
        this.context = context;
    }

    public void startAuth(FingerprintManager fingerprintManager,FingerprintManager.CryptoObject cryptoObject){

        CancellationSignal cancellationSignal = new CancellationSignal();

        fingerprintManager.authenticate(cryptoObject,cancellationSignal,0,this,null);

    }

    @Override
    public void onAuthenticationSucceeded(FingerprintManager.AuthenticationResult result) {
        this.update("You can now access the app.",true);
    }

    @Override
    public void onAuthenticationError(int errorCode, CharSequence errString) {
        this.update("There was an Auth Error."+errString,false);
    }

    private void update(String s, boolean b) {
        TextView paraLabel = ((Activity) context).findViewById(R.id.text_inst);
        ImageView imageView = ((Activity) context).findViewById(R.id.image_finger_print);

        paraLabel.setText(s);

        if(b == false){
            paraLabel.setTextColor(ContextCompat.getColor(context,R.color.colorAccent));
        }else{
            paraLabel.setTextColor(ContextCompat.getColor(context,R.color.colorPrimary));
            imageView.setImageResource(R.mipmap.ic_done_round);
        }
    }

    @Override
    public void onAuthenticationFailed() {
        this.update("Auth Failed.",false);
    }

    @Override
    public void onAuthenticationHelp(int helpCode, CharSequence helpString) {
        this.update("Error: " + helpString,false);
    }
}
