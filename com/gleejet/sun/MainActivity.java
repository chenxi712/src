package com.gleejet.sun;

import android.os.Bundle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.gleejet.sun.common.MusicHandle;

public class MainActivity extends AndroidApplication {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        AndroidApplicationConfiguration cfg = new AndroidApplicationConfiguration();
        cfg.useGL20 = false;
        
        initialize(new MainGame(), cfg);
    }
    
    public void onWindowFocusChanged(final boolean b) {
        super.onWindowFocusChanged(b);
        if (b) {
            Gdx.app.postRunnable(new Runnable() {
                @Override
                public void run() {
                    MusicHandle.resume();
                }
            });
            return;
        }
        Gdx.app.postRunnable(new Runnable() {
            @Override
            public void run() {
                MusicHandle.pause();
            }
        });
    }
}