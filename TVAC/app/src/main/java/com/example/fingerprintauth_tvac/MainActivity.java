package com.example.fingerprintauth_tvac;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.KeyguardManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyPermanentlyInvalidatedException;
import android.security.keystore.KeyProperties;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

@RequiresApi(api = Build.VERSION_CODES.M)
public class MainActivity extends AppCompatActivity {

    private TextView mHeadingLabel,mParaLabel;
    private ImageView mFingerprintImage;

    private FingerprintManager fingerprintManager;
    private KeyguardManager keyguardManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mHeadingLabel = findViewById(R.id.text_phone_auth);
        mParaLabel = findViewById(R.id.text_inst);
        mFingerprintImage = findViewById(R.id.image_finger_print);

        //Check 1: Android Version should be greater or equal to M
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){

            //Check 2: Device has fingerprint Scanner
            fingerprintManager = (FingerprintManager) getSystemService(FINGERPRINT_SERVICE);
            keyguardManager = (KeyguardManager) getSystemService(KEYGUARD_SERVICE);
            if(!fingerprintManager.isHardwareDetected()){
                mParaLabel.setText("Fingerprint Scanner not detected in Device");
            }
            //Check 3: Have permission to use fingerprint scanner in the app
            else if(ContextCompat.checkSelfPermission(this, Manifest.permission.USE_FINGERPRINT) != PackageManager.PERMISSION_GRANTED){
                mParaLabel.setText("Permission not granted to use fingerprint scanner");
            }
            //Check 4: Lock Screen is secured with atleast 1 type of lock
            else if(!keyguardManager.isKeyguardSecure()){
                mParaLabel.setText("Add lock to your phone in Settings");
            }
            //Check 5: Device has atleast 1 Fingerprint is registered
            else if(!fingerprintManager.hasEnrolledFingerprints()){
                mParaLabel.setText("You should add atleast 1 Fingerprint to use this feature");
            }
            else{
                //Main Access
                mParaLabel.setText("Place your Finger on Scanner to Access");

                    FingerprintHandler fingerprintHandler = new FingerprintHandler(this);

                    fingerprintHandler.startAuth(fingerprintManager, null);

            }
        }
    }





}
