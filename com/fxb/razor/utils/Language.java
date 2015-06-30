package com.fxb.razor.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

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
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.absolute("/system/fonts/DroidSansFallback.ttf"));
		BitmapFont font12 = generator.generateFont(12); // font size 12 pixels
		BitmapFont font25 = generator.generateFont(25); // font size 25 pixels
		generator.dispose(); // don't forget to dispose to avoid memory leaks!
		String[] str = {"Tap to fire!",
				"点击开火！"};
		return str[idx];
	}
}
