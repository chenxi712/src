package com.fxb.razor.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

public class Language {
	public static enum Regions {
		English, Chinese
	}
	
	public static Regions region;
	
	private static int idx;
	
	static {
		region = Regions.Chinese;
		if (region == Regions.Chinese)
			idx = 1;
		else if (region == Regions.English)
			idx = 0;
	}
	
	public static String tapToFire () {
		String[] str = {"Tap to fire!",
				"点击开火！"};
		return str[idx];
	}
}
