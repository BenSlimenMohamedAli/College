package com.astro_coder.college;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.dali.astrocoder.college.R;
import com.felipecsl.gifimageview.library.GifImageView;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;

public class Splash extends AppCompatActivity {
    private GifImageView imageView;

    public static int SPLASH_TIMEOUT = 5000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        imageView = (GifImageView) findViewById(R.id.splashIm);

        try{
            InputStream inputStream = getAssets().open("png_loader.gif");
            byte [] bytes = IOUtils.toByteArray(inputStream);
            imageView.setBytes(bytes);
            imageView.startAnimation();
        }catch (IOException ex){
            Toast.makeText(Splash.this, ex.toString(),Toast.LENGTH_LONG).show();
        }


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(Splash.this,MainActivity.class);
                startActivity(i);
                finish();
            }
        },SPLASH_TIMEOUT);
    }
}
