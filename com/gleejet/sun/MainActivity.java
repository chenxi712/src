package com.gleejet.sun;

import java.util.Map;

import android.os.Bundle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.bx.pay.BXPay;
import com.bx.pay.backinf.PayCallback;
import com.gleejet.sun.common.MusicHandle;

public class MainActivity extends AndroidApplication {
	
	private static MainActivity activity;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        activity = this;
        
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
    
    public static void buyGoods(int idx) {
    	String[] payCode = {"0001", "0002", "0003", "0004", "0005", "0006"}; 
    	BXPay bxPay = new BXPay(MainActivity.activity);
    	bxPay.pay(payCode[idx], new PayCallback() {
			@Override
			public void pay(Map resultInfo) {
				String result = (String) resultInfo.get("result");		// 支付结果
//				String payType = (String) resultInfo.get("payType");	// 支付方式

//				String logCode = (String) resultInfo.get("logCode");	// 订单编号
				
				String showMsg = (String) resultInfo.get("showMsg");	// 支付结果描述
				if (result.equals("success") || result.equals("pass")) {
					String code = (String) resultInfo.get("payCode");	// 计费点编号
					String price = (String) resultInfo.get("price");	// 价格
				} else {
					
				}
			}
		});
    }
}