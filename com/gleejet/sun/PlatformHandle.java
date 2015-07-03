package com.gleejet.sun;

public class PlatformHandle
{
    public static final boolean isTestMode = false;
    
    public static void buyGoods(final int n) {
        RazorActivity.buyGoods(n);
    }
    
    public static void closeAd() {
        RazorActivity.closeAd();
    }
    
    public static void closeFeatureView() {
        RazorActivity.closeFeatureView();
    }
    
    public static float getAvaliableMem() {
        return RazorActivity.getAvaliableMem();
    }
    
    public static long getServerTime() {
        return RazorActivity.getServerTime();
    }
    
    public static void goToRate() {
        RazorActivity.goToRate();
    }
    
    public static boolean isAdfree() {
        return RazorActivity.isAdfree();
    }
    
    public static boolean isShowingAd() {
        return RazorActivity.isShowingAd();
    }
    
    public static void setPurchaseOkCallback(final Runnable purchaseOkCallBack) {
        RazorActivity.purchaseOkCallBack = purchaseOkCallBack;
    }
    
    public static void showAdBig() {
        RazorActivity.showAdBig();
    }
    
    public static void showAdSmall() {
        RazorActivity.showAdSmall();
    }
    
    public static void showFeatureView() {
        RazorActivity.showFeatureView();
    }
    
    public static void showMore() {
        RazorActivity.showMore();
    }
}
