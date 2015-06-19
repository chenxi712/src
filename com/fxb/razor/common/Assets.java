package com.fxb.razor.common;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.JsonValue;

public class Assets
{
  public static TextureAtlas atlasArcher;
  public static TextureAtlas atlasBossBullet;
  public static TextureAtlas atlasBreak;
  public static TextureAtlas atlasInstruction;
  public static TextureAtlas atlasLoad = null;
  public static TextureAtlas atlasMainGun = null;
  public static TextureAtlas atlasNumber;
  public static TextureAtlas atlasPauseOver;
  public static TextureAtlas atlasPipeTrace;
  public static TextureAtlas atlasSmoke;
  public static TextureAtlas atlasStart;
  public static TextureAtlas atlasStore;
  public static TextureAtlas atlasUiGame;
  public static TextureAtlas atlasWeaponSelect;
  public static BitmapFont fontMs14 = null;
  public static BitmapFont fontMs16;
  public static JsonValue jsonEnhance;
  public static JsonValue jsonProperty;
  public static JsonValue jsonWeapon;
  public static TextureRegion regionWhite1;
  public static TextureRegion regionWhite2;
  public static Texture textureTrace;
  
  static
  {
    atlasBreak = null;
    atlasSmoke = null;
    atlasPipeTrace = null;
    atlasStore = null;
    atlasBossBullet = null;
    jsonProperty = null;
    jsonEnhance = null;
    jsonWeapon = null;
    atlasUiGame = null;
    atlasPauseOver = null;
    atlasWeaponSelect = null;
    regionWhite1 = null;
    regionWhite2 = null;
    textureTrace = null;
    atlasArcher = null;
    atlasStart = null;
    atlasNumber = null;
    atlasInstruction = null;
    fontMs16 = null;
  }
  
  public static void dispose() {}
  
  public static void init() {}
  
  public static void setAtlas()
  {
    jsonProperty = ((JsonValue)Global.manager.get("json/enemy.json", JsonValue.class)).get("enemyPropertys");
    jsonWeapon = ((JsonValue)Global.manager.get("json/weapon.json", JsonValue.class)).get("weapons");
    jsonEnhance = ((JsonValue)Global.manager.get("json/weapon_enhance.json", JsonValue.class)).get("enhance");
    atlasBreak = (TextureAtlas)Global.manager.get("effect/break.pack", TextureAtlas.class);
    atlasSmoke = (TextureAtlas)Global.manager.get("effect/smoke.pack", TextureAtlas.class);
    atlasPipeTrace = (TextureAtlas)Global.manager.get("effect/pipe_trace.pack", TextureAtlas.class);
    atlasBossBullet = (TextureAtlas)Global.manager.get("boss/pack/boss_bullet.pack", TextureAtlas.class);
    atlasUiGame = (TextureAtlas)Global.manager.get("ui/ui_game.pack", TextureAtlas.class);
    atlasPauseOver = (TextureAtlas)Global.manager.get("ui/ui_pause_over.pack", TextureAtlas.class);
    atlasWeaponSelect = (TextureAtlas)Global.manager.get("ui/ui_weapon_select.pack", TextureAtlas.class);
    atlasStore = (TextureAtlas)Global.manager.get("ui/ui_store.pack", TextureAtlas.class);
    atlasArcher = (TextureAtlas)Global.manager.get("game/archer.pack", TextureAtlas.class);
    atlasMainGun = atlasArcher;
    regionWhite2 = atlasArcher.findRegion("white");
    textureTrace = (Texture)Global.manager.get("data/trace2.png", Texture.class);
    textureTrace.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
    atlasNumber = (TextureAtlas)Global.manager.get("data/number.pack", TextureAtlas.class);
    atlasInstruction = (TextureAtlas)Global.manager.get("data/instruction.pack", TextureAtlas.class);
    atlasStart = (TextureAtlas)Global.manager.get("ui/ui_start.pack", TextureAtlas.class);
    fontMs16 = (BitmapFont)Global.manager.get("font/msb15.fnt", BitmapFont.class);
    fontMs14 = (BitmapFont)Global.manager.get("font/msb14.fnt", BitmapFont.class);
    fontMs16.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
    fontMs14.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
  }
}