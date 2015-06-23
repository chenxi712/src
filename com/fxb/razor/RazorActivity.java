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
    private static final String FLURRY_ID = "MBBFSVGJRHC6B2P6P7HP";
    private static final String[] SKU_ID;
    private static final int[] SKU_NUM;
    private static RazorActivity activity;
    private static float avaliableMem;
    public static Runnable purchaseOkCallBack;
    private static long serverTime;
    String base64EncodedPublicKey;
//    public final Handler billHandler;
//    private Goods[] goodsArray;
//    private Store store;
    
    static {
        SKU_ID = new String[] { "hint_10", "hint_30", "hint_80", "hint_200", "hint_600", "hint_1600" };
        SKU_NUM = new int[] { 10, 30, 80, 200, 600, 1600 };
    }
    
    public RazorActivity() {
        this.base64EncodedPublicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA5glSg4SbV+Na1GqspDCC0f1s5FP0WHrJGCXA0Gw8BWXWwXU+7xm62qroU404aJF0TkZSp1bsLQzocZAXxQtWXnWaNhahAuiigQ7DTKqwSG0je1e2L9bfpehbSbUbt6wHYOsKV0bSziyUdF66jQNZqwTmGfl0A1X/7a/tJ00qfzyw/8Z6wLABEzmOFnTXwhC442DfZwvhaHMunYCdxE5ngPacnQ2c9W0n6dVYRONE/WvgnvYS+x82i9zG4MElvfmayc6BUdZQzVefNUORuF7fowP/kPVwtiYccnBJEp9B7ZBlGYDpHwxejranL1eegyoN0vHPtIZyP+B76eetkXo3AQIDAQAB";
//        this.goodsArray = new Goods[] { new HintGoods(this, RazorActivity.SKU_ID[0], RazorActivity.SKU_NUM[0], false), new HintGoods(this, RazorActivity.SKU_ID[1], RazorActivity.SKU_NUM[1], true), new HintGoods(this, RazorActivity.SKU_ID[2], RazorActivity.SKU_NUM[2], true), new HintGoods(this, RazorActivity.SKU_ID[3], RazorActivity.SKU_NUM[3], true), new HintGoods(this, RazorActivity.SKU_ID[4], RazorActivity.SKU_NUM[4], true), new HintGoods(this, RazorActivity.SKU_ID[5], RazorActivity.SKU_NUM[5], true) };
//        this.store = new Store(this.base64EncodedPublicKey, this.goodsArray);
//        this.billHandler = this.store.getBillingHandler();
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
        return RazorActivity.serverTime;
    }
    
    public static void goToRate() {
//        Platform.getHandler(RazorActivity.activity).sendEmptyMessage(8);
    }
    
    public static boolean isAdfree() {
        return Global.isAdFree;
    }
    
    public static boolean isShowingAd() {
    	return false;
//        return Platform.isFullScreenSmallShowing();
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
//        this.store.onCreate(this);
        this.setAvaliableMem();
        final Hashtable<String, String> hashtable = new Hashtable<String, String>();
        hashtable.put("disable_advertising_id_check", "true");
//        TapjoyConnect.requestTapjoyConnect(this.getApplicationContext(), "cf9dc8a5-651c-403f-b811-2c85efc2cfc9", "EPdJbLSURbmJ5LmYLjHA", hashtable);
        final AndroidApplicationConfiguration androidApplicationConfiguration = new AndroidApplicationConfiguration();
        androidApplicationConfiguration.useGL20 = false;
        this.initialize(new MainGame(), androidApplicationConfiguration);
    }
    
    @Override
    protected void onDestroy() {
        super.onDestroy();
//        Platform.onDestroy();
//        this.store.onDestroy();
    }
    
    @Override
    protected void onPause() {
        super.onPause();
//        Platform.onPause();
    }
    
    protected void onRestart() {
        super.onRestart();
    }
    
    @Override
    protected void onResume() {
        super.onResume();
//        Platform.onResume();
//        try {
//            SponsorPay.start("47fcdaac18477cf3a6a6429b47577600", null, null, this);
//        }
//        catch (RuntimeException ex) {
//            this.log("FYBER", ex.getLocalizedMessage());
//        }
    }
    
    protected void onSaveInstanceState(final Bundle bundle) {
        super.onSaveInstanceState(bundle);
    }
    
    protected void onStart() {
        super.onStart();
//        FlurryAgent.onStartSession((Context)this, "MBBFSVGJRHC6B2P6P7HP");
    }
    
    protected void onStop() {
        super.onStop();
//        FlurryAgent.onEndSession((Context)this);
//        Platform.onStop();
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
    
//    public class HintGoods extends Goods
//    {
//        private boolean adFree;
//        private int increment;
//        private Activity mainActivity;
//        
//        public HintGoods(final Activity mainActivity, final String s, final int increment, final boolean adFree) {
//            super(s);
//            this.mainActivity = mainActivity;
//            this.increment = increment;
//            this.adFree = adFree;
//        }
//        
//        private int findIndex(final int[] array, final int n) {
//            for (int i = 0; i < array.length; ++i) {
//                if (array[i] == n) {
//                    return i;
//                }
//            }
//            return -1;
//        }
//        
//        @Override
//        public final void onPurchaseSuccess() {
//            Global.totalMondNum += this.increment;
//            if (this.adFree) {
//                Global.isAdFree = true;
//                RazorActivity.closeFeatureView();
//            }
//            PreferHandle.writeCommon();
//            FlurryHandle.buyMond(this.findIndex(RazorActivity.SKU_NUM, this.increment) + 1);
//            if (RazorActivity.purchaseOkCallBack != null) {
//                ((MainGame)((RazorActivity)this.mainActivity).getApplicationListener()).postTask(RazorActivity.purchaseOkCallBack);
//                RazorActivity.purchaseOkCallBack = null;
//            }
//        }
//    }
    
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
