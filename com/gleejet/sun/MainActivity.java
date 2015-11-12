package com.gleejet.sun;

import java.util.HashMap;
import java.util.Map;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.bx.pay.BXPay;
import com.bx.pay.backinf.PayCallback;
import com.gleejet.sun.common.Global;
import com.gleejet.sun.common.MusicHandle;
import com.gleejet.sun.common.PreferHandle;

public class MainActivity extends AndroidApplication {
	
	private static MainActivity activity;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        activity = MainActivity.this;
        
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
    
    public static void buyGoods(int idx, final Runnable purchaseOkCallBack) {
    	String[] payCode = {"0001", "0002", "0003", "0004", "0005", "0006"}; 
    	
    	
    	BXPay bxPay = new BXPay(MainActivity.activity);
    	Log.i("gdx", "payCode:" + payCode[idx]);
    	bxPay.pay(payCode[idx], new PayCallback() {
    		
			@Override
			public void pay(Map resultInfo) {
				
				String payType = (String) resultInfo.get("payType");	// 支付方式
				String logCode = (String) resultInfo.get("logCode");	// 订单编号
				String callbackMsg = (String) resultInfo.get("showMsg");	// 支付结果描述
				String price = (String) resultInfo.get("price");	// 价格
				
				Map<String, Integer> goodNames = new HashMap<String, Integer>();
				String code = (String) resultInfo.get("payCode");	// 计费点编号
		    	goodNames.put("0001", 10);
		    	goodNames.put("0002", 30);
		    	goodNames.put("0003", 80);
		    	goodNames.put("0004", 200);
		    	goodNames.put("0005", 600);
		    	goodNames.put("0006", 1600);
		    	String result = (String) resultInfo.get("result");		// 支付结果
		    	Log.i("gdx", "payType:" + payType);
		    	Log.i("gdx", "logCode:" + logCode);
		    	Log.i("gdx", "callbackMsg:" + callbackMsg);
		    	Log.i("gdx", "price:" + price);
		    	Log.i("gdx", "result:" + result);
				if (result.equals("success") || result.equals("pass")) {
					int num = goodNames.get(code);
					if (purchaseOkCallBack != null) {
						Global.totalMondNum += num;
						PreferHandle.writeMond();
						((MainGame) activity.getApplicationListener()).postTask(purchaseOkCallBack);
					}
					
					String showMsg = String.format(activity.getString(R.string.pay_success), num);
					new AlertDialog.Builder(activity)
					.setTitle(activity.getString(R.string.pay_dlg_title))
			        .setMessage(showMsg)
			        .setPositiveButton(activity.getString(R.string.confirm),
	                    new DialogInterface.OnClickListener(){
	                        public void onClick(DialogInterface dialoginterface, int i){
	                        	dialoginterface.dismiss();
	                        }
	                    })
			        .show();
				} else {
					new AlertDialog.Builder(activity)
					.setTitle(activity.getString(R.string.pay_dlg_title))
			        .setMessage(activity.getString(R.string.pay_failed))
			        .setPositiveButton(activity.getString(R.string.confirm),
	                    new DialogInterface.OnClickListener(){
	                        public void onClick(DialogInterface dialoginterface, int i){
	                        	dialoginterface.dismiss();
	                        }
	                    })
			        .show();
				}
			}
		});
    }
    
    public static void pay(final int idx, final Runnable purchaseOkCallBack){
    	Runnable payTask = new Runnable(){  
    		@Override  
    		public void run() {  
    			Looper.prepare();
    			Handler payHandler = new Handler() {
    				public void handleMessage(Message msg) {
    					try{
    						//TODO invoke code
    						buyGoods(idx, purchaseOkCallBack);
    					}catch(Exception e){
    					}
    				}
    			};
    			payHandler.sendMessage(Message.obtain(payHandler, 0, null)); 	
    			Looper.loop();
    		}  
    	}; 
    	new Thread(payTask).start();
    }

}