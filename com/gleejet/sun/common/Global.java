package com.gleejet.sun.common;

import com.badlogic.gdx.graphics.g2d.*;

import java.util.*;
import java.util.concurrent.*;

import com.badlogic.gdx.graphics.glutils.*;
import com.badlogic.gdx.assets.loaders.resolvers.*;
import com.badlogic.gdx.assets.loaders.*;
import com.badlogic.gdx.assets.*;
import com.badlogic.gdx.utils.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.math.*;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.*;
import com.gleejet.sun.*;
import com.gleejet.sun.flash.*;
import com.gleejet.sun.objects.*;
import com.gleejet.sun.roles.*;
import com.gleejet.sun.screens.*;
import com.gleejet.sun.utils.loader.*;
import com.gleejet.sun.utils.ui.*;

public class Global
{
    public static Array<BaseEnemy> arrBossCallEnemy;
    public static Array<Float> arrBossPos;
    public static Array<Constant.EnemyType> arrBossType;
    public static Array<Float> arrBoxPos;
    public static Array<BaseEnemy> arrEnemyCollision;
    public static Array<BaseEnemy> arrEnemyCreate;
    public static Array<Constant.EnemyType> arrEnemyType;
    public static Array<String> arrMainGunGet;
    public static Array<String> arrStrMainSelect;
    public static Array<String> arrSubGunGet;
    public static int autoRateCount;
    public static SpriteBatch batch;
    public static int bossCallState;
    public static Constant.BossLevelState bossLevelState;
    public static Bullet8 bullet;
    public static float coinGet;
    public static int continualDays;
    public static int createEnemyArrayState;
    public static Constant.EnemyType curBossType;
    public static int curCombo;
    public static int curGameLevelEasy;
    public static int curGameLevelHard;
    public static float deltaY;
    private static final int[] endlessCoins;
    public static int endlessHashState;
    public static int enemyKill;
    public static MainGame game;
    public static int gameInitNum;
    public static Constant.GameMode gameMode;
    public static int gamePlayNum;
    public static GameScreen gameScreen;
    public static Constant.GameState gameState;
    public static Group groupBulletEnemy;
    public static Group groupBulletPlayer;
    public static Group groupCoin;
    public static Group groupDamage;
    public static Group groupEffectPlayer;
    public static Group groupEffectSmoke;
    public static Group groupEnemy;
    public static Group groupFlag;
    public static Group groupSynMove;
    public static MyImage imgAim;
    public static int inCoinGet;
    public static boolean isAdFree;
    public static boolean isAllKill;
    public static boolean isAutoRate;
    public static boolean isAutoSpesold;
    public static boolean isAutoStoreEnhance;
    public static boolean isBuyOk;
    public static boolean isEndlessMode;
    public static boolean isGameAgain;
    public static boolean isSpeSoldShowing;
    public static boolean isUseRGBA4444;
    public static MyLabel labelComboNumber;
    public static MyLabel labelComboShow;
    public static long laserSoundId;
    public static long lastLoginRewardTime;
    public static long lastProbaTime;
    public static float levelEndPosX;
    public static float levelRate;
    public static int loseCount;
    public static int mainGunCount;
    public static AssetManager manager;
    public static HashMap<Constant.EnemyType, Array<BaseEnemy>> mapEnemy;
    public static HashMap<Constant.EnemyType, Integer> mapTypeKill;
    public static int maxCombo;
    public static int maxGameLevelEasy;
    public static int maxGameLevelHard;
    public static Constant.NextScreen nextScreen;
    public static float oriX;
    public static float oriY;
    public static Vector2 p5;
    public static Vector2 pAdd;
    public static Vector2 pCross;
    public static Vector2 pEnd;
    public static Vector2 pMain1;
    public static Vector2 pMain2;
    public static Vector2 pStart;
    public static Vector2 pSub1;
    public static Vector2 pSub2;
    public static Constant.NextScreen preScreen;
    public static Vector2 ptEnd;
    public static Vector2 ptStart;
    public static ArrayBlockingQueue<SoundHandle.SoundItem> queueSound;
    public static ShapeRenderer rend;
    public static int sceneLevel;
    public static Actor selectActor;
    public static float soundVolume;
    public static long speStartTime;
    public static int storeSelectIndex;
    public static String strCurWeaponName;
    public static String strSubGun;
    public static float totalCoinNum;
    public static int totalEnemy;
    public static float totalMondNum;
    public static float tranLength;
    
    static {
        Global.maxGameLevelEasy = 45;
        Global.maxGameLevelHard = 15;
        Global.manager = null;
        Global.batch = null;
        Global.rend = null;
        Global.mainGunCount = 0;
        Global.curGameLevelEasy = 1;
        Global.curGameLevelHard = 1;
        Global.sceneLevel = 1;
        Global.levelRate = 0.0f;
        Global.bossLevelState = Constant.BossLevelState.Boss_Hide;
        Global.arrBossCallEnemy = new Array<BaseEnemy>();
        Global.curBossType = null;
        Global.enemyKill = 0;
        Global.coinGet = 0.0f;
        Global.inCoinGet = 0;
        Global.totalEnemy = 0;
        Global.isAllKill = true;
        Global.storeSelectIndex = 1;
        Global.nextScreen = Constant.NextScreen.Loading_Screen;
        Global.preScreen = Constant.NextScreen.Loading_Screen;
        Global.arrEnemyType = new Array<Constant.EnemyType>();
        Global.arrEnemyCreate = new Array<BaseEnemy>();
        Global.arrEnemyCollision = new Array<BaseEnemy>();
        Global.levelEndPosX = 0.0f;
        Global.createEnemyArrayState = 0;
        Global.endlessHashState = 0;
        Global.mapEnemy = new HashMap<Constant.EnemyType, Array<BaseEnemy>>();
        Global.bossCallState = 0;
        Global.gameState = Constant.GameState.Game_On;
        Global.game = null;
        Global.pCross = new Vector2();
        Global.pMain1 = new Vector2();
        Global.pMain2 = new Vector2();
        Global.pSub1 = new Vector2();
        Global.pSub2 = new Vector2();
        Global.queueSound = new ArrayBlockingQueue<SoundHandle.SoundItem>(5);
        Global.soundVolume = 0.3f;
        Global.gameScreen = null;
        Global.totalCoinNum = 0.0f;
        Global.totalMondNum = 0.0f;
        Global.deltaY = 0.0f;
        Global.isEndlessMode = false;
        Global.tranLength = 0.0f;
        Global.maxCombo = 0;
        Global.curCombo = 0;
        Global.arrMainGunGet = new Array<String>();
        Global.arrSubGunGet = new Array<String>();
        Global.lastProbaTime = 0L;
        Global.lastLoginRewardTime = 0L;
        Global.continualDays = 0;
        Global.mapTypeKill = new HashMap<Constant.EnemyType, Integer>();
        Global.isBuyOk = false;
        Global.gamePlayNum = 0;
        Global.gameInitNum = 0;
        Global.gameMode = Constant.GameMode.Easy;
        Global.strCurWeaponName = "SinglePipe";
        Global.isSpeSoldShowing = false;
        Global.isUseRGBA4444 = true;
        Global.isGameAgain = false;
        Global.speStartTime = 0L;
        Global.arrBoxPos = new Array<Float>();
        Global.arrBossPos = new Array<Float>();
        Global.arrBossType = new Array<Constant.EnemyType>();
        Global.autoRateCount = 0;
        Global.isAutoRate = false;
        Global.isAutoSpesold = false;
        Global.loseCount = 0;
        Global.isAutoStoreEnhance = false;
        Global.laserSoundId = -1L;
        Global.isAdFree = false;
        endlessCoins = new int[] { 20, 50, 100, 200, 300, 400, 500, 600, 700, 800, 900, 1000, 1200, 1400, 1600, 1800, 2000, 2200, 2400, 2600, 2800, 3000, 3400, 3800, 4200, 4800, 5200 };
    }
    
    public static void dispose() {
        PreferHandle.writeCommon();
        PreferHandle.writeWeaponGet();
        Global.manager.dispose();
        Global.batch.dispose();
    }
    
    public static int getEndlessCoin() {
        for (int i = 0; i < Global.endlessCoins.length; ++i) {
            if (Global.coinGet <= Global.endlessCoins[i]) {
                return Global.endlessCoins[i];
            }
        }
        return Global.endlessCoins[Global.endlessCoins.length - 1];
    }
    
    public static String getUnlockName() {
        switch (Global.maxGameLevelEasy) {
            default: {
                return null;
            }
            case 1: {
                return "Cannon";
            }
            case 8: {
                return "Twine";
            }
            case 15: {
                return "Subcan";
            }
        }
    }
    
	public static void init() {
        Global.manager = new AssetManager();
        InternalFileHandleResolver internalFileHandleResolver = new InternalFileHandleResolver();
        Global.manager.setLoader(FlashElements.class, new FlashLoader(internalFileHandleResolver));
        Global.manager.setLoader(JsonValue.class, new JsonLoader(internalFileHandleResolver));
        Global.manager.setLoader(ArrayEnemy.class, new EnemyLoader(internalFileHandleResolver));
        Global.manager.setLoader(Texture.class, new MyTextureLoader(internalFileHandleResolver));
        Global.batch = new SpriteBatch();
        Global.rend = new ShapeRenderer();
        Matrix4 matrix4 = new Matrix4();
        matrix4.setToOrtho2D(0.0f, 0.0f, 800.0f, 480.0f);
        Global.batch.setProjectionMatrix(matrix4);
        Global.rend.setProjectionMatrix(matrix4);
        Global.arrStrMainSelect = new Array<String>();
        Global.strSubGun = "Pastor";
        Global.groupEffectPlayer = UiHandle.createGroup(null, false);
        Global.groupEffectSmoke = UiHandle.createGroup(null, false);
        Global.groupSynMove = UiHandle.createGroup(null, false);
        Global.groupDamage = UiHandle.createGroup(null, false);
        Global.groupCoin = UiHandle.createGroup(null, false);
        Global.p5 = new Vector2();
        Global.pAdd = new Vector2();
        Global.ptStart = new Vector2();
        Global.ptEnd = new Vector2();
//        while (true) {
//            if (Global.maxGameLevelEasy == 1) {
//                PreferHandle.readCommon();
//                PreferHandle.readWeaponSelect();
//                PreferHandle.readWeaponGet();
//                PreferHandle.readTypeKill();
//                ++Global.gameInitNum;
//                LineHandle.init();
//                return;
//            }
//            continue;
//        }
        PreferHandle.readCommon();
        PreferHandle.readWeaponSelect();
        PreferHandle.readWeaponGet();
        PreferHandle.readTypeKill();
        ++Global.gameInitNum;
        LineHandle.init();
    }
    
    public static boolean isSpesoldValid() {
        return !Global.arrMainGunGet.contains("Flame", false) && 172800 - (int)(System.currentTimeMillis() / 1000L - Global.speStartTime) >= 0;
    }
    
    public static boolean isUnlockWeapon() {
        return Global.maxGameLevelEasy == Global.curGameLevelEasy && (Global.maxGameLevelEasy == 1 || Global.maxGameLevelEasy == 8 || Global.maxGameLevelEasy == 15);
    }
    
    private static boolean isUseRGBA4444(final boolean b) {
        return Gdx.graphics.getWidth() < 800 || Gdx.graphics.getHeight() < 480 || PlatformHandle.getAvaliableMem() < 40.0f || (b && Gdx.graphics.getFramesPerSecond() < 40);
    }
    
    public static void setUseRGBA4444(final boolean b) {
        Global.isUseRGBA4444 = isUseRGBA4444(b);
        if (Global.isUseRGBA4444) {
            return;
        }
    }
}
