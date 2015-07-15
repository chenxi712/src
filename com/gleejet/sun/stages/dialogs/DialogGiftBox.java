package com.gleejet.sun.stages.dialogs;

import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.JsonValue;
import com.gleejet.sun.common.Assets;
import com.gleejet.sun.common.Constant;
import com.gleejet.sun.common.DialogHandle;
import com.gleejet.sun.common.Global;
import com.gleejet.sun.common.MyMethods;
import com.gleejet.sun.common.PreferHandle;
import com.gleejet.sun.common.SoundHandle;
import com.gleejet.sun.common.UiHandle;
import com.gleejet.sun.screens.BaseScreen;
import com.gleejet.sun.screens.LevelSmallScreen;
import com.gleejet.sun.screens.WeaponEnhanceScreen;
import com.gleejet.sun.screens.WeaponScreen;
import com.gleejet.sun.utils.ButtonListener;
import com.gleejet.sun.utils.Debug;
import com.gleejet.sun.utils.StrHandle;
import com.gleejet.sun.utils.action.TouchAction;
import com.gleejet.sun.utils.ui.AutoLineLabel;
import com.gleejet.sun.utils.ui.MyGroup;
import com.gleejet.sun.utils.ui.MyImage;


public class DialogGiftBox extends BaseDialog 
{
	static final String[] strMains;
    static final String[] strSubs;
	private ButtonListener btnListener;
	private TextureAtlas atlasStart;
	private TextureAtlas atlasLibao;
	private TextureAtlas atlasLevelBg;
	private MyImage imgBuy = null;
	private float heroPrice = 0.0f;
	private String heroName = "";
	TextureAtlas atlasEnhance;
	TextureAtlas atlasWeaponBg;
	TextureAtlas atlasStore;
	BaseScreen baseScreen;
	
	static {
        strMains = Constant.strMains;
        strSubs = Constant.strSubs;
    }
	
	public DialogGiftBox (BaseScreen baseScreen) {
		this.baseScreen = baseScreen;
		this.btnListener = new ButtonListener() {
            @Override
            public void touchUp(final InputEvent inputEvent, final float n, final float n2, final int n3, final int n4) {
                super.touchUp(inputEvent, n, n2, n3, n4);
                if (this.isDown) {
                    final Actor listenerActor = inputEvent.getListenerActor();
                    SoundHandle.playForButton2();
                    if (listenerActor == imgClose) {
                        close();
                    }
                    else if (listenerActor == imgBuy) {
                    	buy();
                    }
                }
            }
        };
		init();
	}
	
	private void init() {
		 atlasWeaponBg = Global.manager.get("ui/ui_weapon_bg.pack", TextureAtlas.class);
		 atlasEnhance = Global.manager.get("ui/ui_weapon_enhance.pack", TextureAtlas.class);
		 this.atlasStore = Global.manager.get("ui/ui_store.pack", TextureAtlas.class);
		 this.atlasStart = Global.manager.get("ui/ui_start.pack", TextureAtlas.class);
		 this.atlasLibao = Global.manager.get("ui/ui_libao.pack", TextureAtlas.class);
		 this.atlasLevelBg = Global.manager.get("ui/ui_level_bg.pack", TextureAtlas.class);
		 this.imgBg = this.addItem(this, this.atlasStart, "waikuang", 0.0f, 0.0f);
		 initItems();
		 imgBuy = this.addItem(this, this.atlasLibao, "lingqu", 470, 0);
		 imgBuy.addListener(this.btnListener);
		 this.addItem(this, this.atlasLevelBg, "hengfu1", 170, 250);
		 this.addItem(this, this.atlasLibao, "libao_biaoti", 286, 278);
		 this.addItem(this, this.atlasLibao, "gift_tip", 350, 120);
		 this.initClose(this.btnListener);
		 this.setPosition(61.0f, 80.0f);
		 this.setSizeOrigin();
	}
	
	public void close() {
		DialogHandle.closeDialog(this, 0.35f);
    }
	
	@Override
    public void afterClose() {
        if (this.baseScreen instanceof WeaponScreen) {
            ((WeaponScreen)this.baseScreen).refreshWeapon();
        }
    }
	
	public void openDialog(Stage stage) {
         DialogHandle.openDialog(stage, this);
    }
	
	private void buy() {
		// Callback succeed 
		Global.arrMainGunGet.add(heroName);
        PreferHandle.checkSame(Global.arrMainGunGet);
        PreferHandle.writeWeaponGet();
        PreferHandle.writeCommon();
        close();
	}
	
	//SinglePipe Cannon Scatter Flame Laser Electricity DoublePipe Acid Freezefog Missile Track Shock Leap
	//Pastor Twine Drum Shield Subcan Bomb Invince
	private void initItems() {
		HashMap<String, Float> dictHeros = new HashMap<String, Float>();
		
		dictHeros.put("Laser", 2.0f);
		dictHeros.put("Electricity", 2.0f);
		dictHeros.put("Acid", 2.0f);
		dictHeros.put("Freezefog", 2.0f);
		dictHeros.put("Missile", 2.0f);
		dictHeros.put("Track", 2.0f);
		dictHeros.put("Shock", 2.0f);
		dictHeros.put("Flame", 2.0f);
		
		int count = Global.arrMainGunGet.size;
		for (int i = 0; i < count; ++i) {
			String name = Global.arrMainGunGet.get(i);
			if (dictHeros.containsKey(name)) {
				dictHeros.remove(name);
			}
			Debug.log("already heros: " + Global.arrMainGunGet.get(i));
		}
		if (dictHeros.size() > 0) {
			Set<Entry<String, Float>> sets = dictHeros.entrySet();
			int sum = 0;
			Random random = new Random();
			for (Entry<String, Float> entry : sets) {
				heroName = entry.getKey();
				heroPrice = entry.getValue();
				++sum;
				if (sum >= random.nextInt(dictHeros.size() - 1))
					break;
			}
			Debug.log("name:" + heroName + ", price:" + heroPrice);
		}
		InfoBoard info1 = new InfoBoard(this);
		info1.setPosition(60.0f, 22.0f);
		info1.setWeaponName(heroName);
		this.addActor(info1);
	}
	
	public static int HasGifts() {
		HashMap<String, Float> dictHeros = new HashMap<String, Float>();
		
		dictHeros.put("Laser", 2.0f);
		dictHeros.put("Electricity", 2.0f);
		dictHeros.put("Acid", 2.0f);
		dictHeros.put("Freezefog", 2.0f);
		dictHeros.put("Missile", 2.0f);
		dictHeros.put("Track", 2.0f);
		dictHeros.put("Shock", 2.0f);
		dictHeros.put("Flame", 2.0f);
		
		int count = Global.arrMainGunGet.size;
		for (int i = 0; i < count; ++i) {
			String name = Global.arrMainGunGet.get(i);
			if (dictHeros.containsKey(name)) {
				dictHeros.remove(name);
			}
		}
		return dictHeros.size();
	}
	
	
    private class InfoBoard extends MyGroup
    {
        MyImage[] imgAirLevelBgs;
        MyImage[] imgAirLevels;
        MyImage imgBg;
        MyImage[] imgBuildLevelBgs;
        MyImage[] imgBuildLevels;
        MyImage[] imgGroundLevelBgs;
        MyImage[] imgGroundLevels;
        MyImage imgWeapon;
        AutoLineLabel labelInfo;
        String strName;
        
        public InfoBoard(Group stage) {
            final TextureAtlas textureAtlas = Global.manager.get("ui/ui_start.pack", TextureAtlas.class);
            this.imgBg = UiHandle.addItem(this, DialogGiftBox.this.atlasWeaponBg, "waikuang2", 0.0f, 0.0f);
            this.imgWeapon = UiHandle.addItem(this, Assets.atlasUiGame, "ren0", 14.0f, 119.0f);
            UiHandle.addItem(this, DialogGiftBox.this.atlasEnhance, "ren", 93.0f, 174.0f);
            UiHandle.addItem(this, DialogGiftBox.this.atlasEnhance, "long", 93.0f, 145.0f);
            UiHandle.addItem(this, DialogGiftBox.this.atlasEnhance, "jianta", 93.0f, 119.0f);
            this.imgGroundLevelBgs = new MyImage[15];
            this.imgGroundLevels = new MyImage[15];
            this.imgAirLevelBgs = new MyImage[15];
            this.imgAirLevels = new MyImage[15];
            this.imgBuildLevelBgs = new MyImage[15];
            this.imgBuildLevels = new MyImage[15];
            for (int i = 0; i < this.imgGroundLevelBgs.length; ++i) {
                this.imgGroundLevelBgs[i] = UiHandle.addItem(this, DialogGiftBox.this.atlasEnhance, "shengji1", i * 5 + 121.0f, 193.0f - 12.0f);
                this.imgGroundLevels[i] = UiHandle.addItem(this, DialogGiftBox.this.atlasEnhance, "shengji2", i * 5 + 121.0f, 193.0f - 12.0f);
                this.imgAirLevelBgs[i] = UiHandle.addItem(this, DialogGiftBox.this.atlasEnhance, "shengji1", i * 5 + 121.0f, 165.0f - 12.0f);
                this.imgAirLevels[i] = UiHandle.addItem(this, DialogGiftBox.this.atlasEnhance, "shengji2", i * 5 + 121.0f, 165.0f - 12.0f);
                this.imgBuildLevelBgs[i] = UiHandle.addItem(this, DialogGiftBox.this.atlasEnhance, "shengji1", i * 5 + 121.0f, 135.0f - 12.0f);
                this.imgBuildLevels[i] = UiHandle.addItem(this, DialogGiftBox.this.atlasEnhance, "shengji2", i * 5 + 121.0f, 135.0f - 12.0f);
            }
            this.labelInfo = AutoLineLabel.createRoman16(this, "Continious and rapid fire, low damage to defensive structures.", 30.0f, 65.0f, 162.0f);
            this.setWeaponLevel(7, 8, 9);
            stage.addActor(this);
        }
        
        private void setWeaponLevel(int i, final int n, final int n2) {
            for (int j = 0; j < this.imgGroundLevels.length; ++j) {
                this.imgGroundLevels[j].setVisible(j < i);
            }
            for (i = 0; i < this.imgAirLevels.length; ++i) {
                this.imgAirLevels[i].setVisible(i < n);
            }
            for (i = 0; i < this.imgBuildLevels.length; ++i) {
                this.imgBuildLevels[i].setVisible(i < n2);
            }
        }
        
		private void setWeaponName(final String strName) {
            this.strName = strName;
            final JsonValue value = Assets.jsonEnhance.get(this.strName);
            final int weaponEnhance = PreferHandle.readWeaponEnhance(this.strName);
            float n = 100.0f;
            float n2 = 100.0f;
            float n3 = 100.0f;
            final JsonValue value2 = value.get("types");
            final JsonValue value3 = value.get("addValue");
            float n4;
            float n5;
            float n6;
            for (int i = 0; i < weaponEnhance; ++i, n = n4, n2 = n5, n3 = n6) {
                final String string = value2.get(i).asString();
                if (string.equals("AllDamage")) {
                    n4 = n + value3.get(i).asFloat();
                    n5 = n2 + value3.get(i).asFloat();
                    n6 = n3 + value3.get(i).asFloat();
                }
                else if (string.equals("AirDamage")) {
                    n5 = n2 + value3.get(i).asFloat();
                    n4 = n;
                    n6 = n3;
                }
                else if (string.equals("GroundDamage")) {
                    n4 = n + value3.get(i).asFloat();
                    n5 = n2;
                    n6 = n3;
                }
                else {
                    n4 = n;
                    n5 = n2;
                    n6 = n3;
                    if (string.equals("BuildDamge")) {
                        n6 = n3 + value3.get(i).asFloat();
                        n4 = n;
                        n5 = n2;
                    }
                }
            }
            final JsonValue value4 = value.get("damageRatio");
            this.setWeaponLevel((int)(value4.get(0).asInt() * n / 100.0f), (int)(value4.get(1).asInt() * n2 / 100.0f), (int)(value4.get(2).asInt() * n3 / 100.0f));
            this.labelInfo.setInfo(value.getString("selInfo"));
            final int n7 = MyMethods.findIndex(DialogGiftBox.strMains, this.strName) + 1;
            if (n7 > 0) {
                this.imgWeapon.setRegion(Assets.atlasUiGame.findRegion(StrHandle.get("ren", n7)));
                return;
            }
            this.imgWeapon.setRegion(DialogGiftBox.this.atlasStore.findRegion(StrHandle.get("fu", MyMethods.findIndex(DialogGiftBox.strSubs, this.strName) + 1)));
            this.imgWeapon.setSize(94.0f, 116.0f);
        }
        
        public void hide() {
            this.setWeaponLevel(0, 0, 0);
            this.imgWeapon.setRegion(Assets.atlasUiGame.findRegion("ren0"));
            this.labelInfo.setInfo("");
        }
    }
}
