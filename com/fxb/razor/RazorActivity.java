package com.fxb.razor;

import android.os.*;
import android.app.*;
import android.content.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

import com.badlogic.gdx.backends.android.*;
import com.badlogic.gdx.*;
import com.badlogic.gdx.Net.*;
import com.fxb.razor.common.*;
import com.fxb.razor.utils.Debug;

public class RazorActivity extends AndroidApplication
{
    private static RazorActivity activity;
    private static float avaliableMem;
    public static Runnable purchaseOkCallBack;
    private static long serverTime;
    String base64EncodedPublicKey;
//    public final Handler billHandler;
        
    public RazorActivity() {
        this.base64EncodedPublicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA5glSg4SbV+Na1GqspDCC0f1s5FP0WHrJGCXA0Gw8BWXWwXU+7xm62qroU404aJF0TkZSp1bsLQzocZAXxQtWXnWaNhahAuiigQ7DTKqwSG0je1e2L9bfpehbSbUbt6wHYOsKV0bSziyUdF66jQNZqwTmGfl0A1X/7a/tJ00qfzyw/8Z6wLABEzmOFnTXwhC442DfZwvhaHMunYCdxE5ngPacnQ2c9W0n6dVYRONE/WvgnvYS+x82i9zG4MElvfmayc6BUdZQzVefNUORuF7fowP/kPVwtiYccnBJEp9B7ZBlGYDpHwxejranL1eegyoN0vHPtIZyP+B76eetkXo3AQIDAQAB";
    }
    
    public static void buyGoods(final int n) {
//        RazorActivity.activity.billHandler.sendEmptyMessage(n);
    }
    
    public static void closeAd() {
//        Platform.getHandler(RazorActivity.activity).sendEmptyMessage(16);
    }
    
    public static void closeFeatureView() {
        try {
//            Platform.getHandler(RazorActivity.activity).sendEmptyMessage(6);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public static float getAvaliableMem() {
        return RazorActivity.avaliableMem;
    }
    
    public static long getServerTime() {
    	final Calendar instance = Calendar.getInstance();
    	RazorActivity.serverTime = instance.getTimeInMillis();
        return RazorActivity.serverTime;
    }
    
    public static void goToRate() {
//        Platform.getHandler(RazorActivity.activity).sendEmptyMessage(8);
    }
    
    public static boolean isAdfree() {
        return Global.isAdFree;
    }
    
    public static boolean isShowingAd() {
//        return Platform.isFullScreenSmallShowing();
    	return false;
    }
    
    public static void setServerTime(final long n) {
        RazorActivity.serverTime = 1000L * n;
        final Calendar instance = Calendar.getInstance();
        instance.setTimeInMillis(RazorActivity.serverTime);
        System.out.println(instance.getTime());
    }
    
    public static void showAdBig() {
        if (!isAdfree()) {
//            Platform.getHandler(RazorActivity.activity).sendEmptyMessage(9);
        }
    }
    
    public static void showAdSmall() {
        if (!isAdfree()) {
//            Platform.getHandler(RazorActivity.activity).sendMessage(Platform.getHandler(RazorActivity.activity).obtainMessage(17, (Object)true));
        }
    }
    
    public static void showFeatureView() {
        if (!isAdfree()) {
//            Message.obtain(Platform.getHandler(RazorActivity.activity), 5, 14, 12, (Object)new Rect(160, 380, 640, 600)).sendToTarget();
        }
    }
    
    public static void showMore() {
//        Platform.getHandler(RazorActivity.activity).sendEmptyMessage(2);
    }
    
    protected void onActivityResult(final int n, final int n2, final Intent intent) {
//        if (!this.store.onActivityResult(n, n2, intent)) {
//            super.onActivityResult(n, n2, intent);
//        }
    }
    
    protected void onCreate(final Bundle bundle) {
        super.onCreate(bundle);
        this.getWindow().setFlags(128, 128);
//        Platform.onCreate(this);
        RazorActivity.activity = this;
//        Platform.setGetServerTimeListener(new Resources.GetServerTimeListener() {
//            @Override
//            public void onServerTimeRecived(final long serverTime) {
//                Log.e("ServerTime", "ServerTime: " + serverTime);
//                RazorActivity.setServerTime(serverTime);
//            }
//        });
//        Platform.getServerTime();
//        Platform.setFullScreenCloseListener(new Resources.FullScreenCloseListener() {
//            @Override
//            public void onFullSCreenClosed() {
//                Log.e("FullScreenCallBack", "FullScreen Closed!");
//            }
//        });
        this.setAvaliableMem();
        final Hashtable<String, String> hashtable = new Hashtable<String, String>();
        hashtable.put("disable_advertising_id_check", "true");
        final AndroidApplicationConfiguration androidApplicationConfiguration = new AndroidApplicationConfiguration();
        this.initialize(new MainGame(), androidApplicationConfiguration);
    }
    
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
    
    @Override
    protected void onPause() {
        super.onPause();
    }
    
    protected void onRestart() {
        super.onRestart();
    }
    
    @Override
    protected void onResume() {
        super.onResume();
    }
    
    protected void onSaveInstanceState(final Bundle bundle) {
        super.onSaveInstanceState(bundle);
    }
    
    protected void onStart() {
        super.onStart();
    }
    
    protected void onStop() {
        super.onStop();
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
    
    public void setAvaliableMem() {
        final ActivityManager activityManager = (ActivityManager)this.getSystemService("activity");
        final ActivityManager.MemoryInfo activityManager$MemoryInfo = new ActivityManager.MemoryInfo();
        activityManager.getMemoryInfo(activityManager$MemoryInfo);
        RazorActivity.avaliableMem = activityManager$MemoryInfo.availMem / 1024L / 1024L;
    }
    
    public static void InitServerTime () {
        
        HttpRequest httpRequest = new HttpRequest(Net.HttpMethods.GET);
        httpRequest.setUrl("http://currentmillis.com/api/millis-since-unix-epoch.php");
        HttpResponseListener responseListener = new HttpResponseListener() {
           
           @Override
           public void handleHttpResponse(HttpResponse httpResponse) {
        	   String inputLine;
			   final InputStream resultAsStream = httpResponse.getResultAsStream();
			   final BufferedReader in = new BufferedReader(new InputStreamReader(resultAsStream));
			   try {
				   if ((inputLine = in.readLine()) != null) {
					   setServerTime (Long.parseLong(inputLine));
				   }
			   } catch (NumberFormatException e1) {
				// TODO Auto-generated catch block
				   e1.printStackTrace();
			   } catch (IOException e1) {
				// TODO Auto-generated catch block
				   e1.printStackTrace();
			   }
           }
           
           @Override
           public void failed(Throwable t) {
              Debug.log("InitServerTime fail");
           }
        };
        Gdx.net.sendHttpRequest(httpRequest, responseListener);
    }
}
