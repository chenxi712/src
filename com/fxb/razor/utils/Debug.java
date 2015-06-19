package com.fxb.razor.utils;

import com.badlogic.gdx.Gdx;
import com.fxb.razor.BuildConfig;

public class Debug {
	
	public static String tag = "gdx";
	
	public static void log (String message) {
		if (BuildConfig.DEBUG)
			Gdx.app.log (tag, message);
	}
	
	public static void debug (String message) {
		if (BuildConfig.DEBUG)
			Gdx.app.debug (tag, message);
	}
	
	public static void error (String message) {
		if (BuildConfig.DEBUG)
			Gdx.app.error(tag, message);
	}

}
