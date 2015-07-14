package com.gleejet.sun.common;

import com.badlogic.gdx.utils.*;
import com.badlogic.gdx.math.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.audio.*;
import com.gleejet.sun.objects.maingun.*;
import com.gleejet.sun.objects.subgun.*;
import com.gleejet.sun.roles.*;
import com.gleejet.sun.utils.Debug;

public class Control
{
    private static Array<Constant.EnemyType> arrBossRandType;
    private static Array<Integer> arrWave;
    static Constant.EnemyType lastBuild1;
    static Constant.EnemyType lastBuild2;
    static Constant.EnemyType lastMove1;
    static Constant.EnemyType lastMove2;
    static Constant.EnemyType[] typeBuild2;
    static Constant.EnemyType[] typeBuild3;
    static Constant.EnemyType[] typeBuild4;
    static Constant.EnemyType[] typeMove1;
    static Constant.EnemyType[] typeMove2;
    static Constant.EnemyType[] typeMove3;
    static Constant.EnemyType[] typeMove4;
    
    static {
        Control.typeMove1 = new Constant.EnemyType[] { Constant.EnemyType.Archer1, Constant.EnemyType.Lancer1, Constant.EnemyType.Airforce1 };
        Control.typeMove2 = new Constant.EnemyType[] { Constant.EnemyType.Archer1, Constant.EnemyType.Lancer1, Constant.EnemyType.Airforce1, Constant.EnemyType.Dragon1, Constant.EnemyType.Bomber1, Constant.EnemyType.Selfdes1 };
        Control.typeBuild2 = new Constant.EnemyType[] { Constant.EnemyType.Mine1, Constant.EnemyType.Tower1, Constant.EnemyType.Spikeweed1, Constant.EnemyType.Artillery1, Constant.EnemyType.CanTower1 };
        Control.typeMove3 = new Constant.EnemyType[] { Constant.EnemyType.Archer2, Constant.EnemyType.Lancer2, Constant.EnemyType.Airforce2, Constant.EnemyType.Dragon2, Constant.EnemyType.Bomber2, Constant.EnemyType.Selfdes2 };
        Control.typeBuild3 = new Constant.EnemyType[] { Constant.EnemyType.Mine2, Constant.EnemyType.Tower2, Constant.EnemyType.Spikeweed2, Constant.EnemyType.Artillery2, Constant.EnemyType.CanTower2 };
        Control.typeMove4 = new Constant.EnemyType[] { Constant.EnemyType.Archer3, Constant.EnemyType.Lancer3, Constant.EnemyType.Airforce3, Constant.EnemyType.Dragon3, Constant.EnemyType.Bomber3, Constant.EnemyType.Selfdes3 };
        Control.typeBuild4 = new Constant.EnemyType[] { Constant.EnemyType.Mine3, Constant.EnemyType.Tower3, Constant.EnemyType.Spikeweed3, Constant.EnemyType.Artillery3, Constant.EnemyType.CanTower3 };
        Control.lastMove1 = null;
        Control.lastMove2 = null;
        Control.lastBuild1 = null;
        Control.lastBuild2 = null;
        Control.arrBossRandType = new Array<Constant.EnemyType>();
        Control.arrWave = new Array<Integer>();
    }
    
    public static void addEndlessEnemy(boolean b) {
        if (Global.endlessHashState > 1) {
            return;
        }
        Constant.EnemyType lastMove1 = null;
        boolean b2 = false;
        int n = 0;
        boolean b3;
        while (true) {
            b3 = b2;
            if (n >= 10) {
                break;
            }
            if (b) {
                lastMove1 = getMoveType();
            }
            else {
                lastMove1 = getBuildType();
            }
            if (Global.mapEnemy.get(lastMove1) == null) {
                Global.mapEnemy.put(lastMove1, new Array<BaseEnemy>());
            }
            if (Global.mapEnemy.get(lastMove1).size > 0) {
                b3 = true;
                break;
            }
            ++n;
        }
        if (b) {
            Control.lastMove2 = Control.lastMove1;
            Control.lastMove1 = lastMove1;
        }
        else {
            Control.lastBuild2 = Control.lastBuild1;
            Control.lastBuild1 = Control.lastBuild2;
        }
        addForType(lastMove1, b3);
    }
    
    public static void addForType(Constant.EnemyType enemyType, boolean b) {
        if (b) {
            BaseEnemy baseEnemy = Global.mapEnemy.get(enemyType).get(0);
            Global.mapEnemy.get(enemyType).removeIndex(0);
            baseEnemy.Clear();
            baseEnemy.setPosition(900.0f, 95.0f);
            Global.arrEnemyCreate.add(baseEnemy);
        }
    }
    
    public static void bossCall(int i) {
        JsonValue value = Global.manager.get("json/bosscall/boss_call.json", JsonValue.class).get(0).get(Global.curBossType.toString()).get("waves").get(i - 1);
        Control.arrWave.clear();
        for (i = 0; i < value.size; ++i) {
            Control.arrWave.add(value.get(i).asInt());
        }
        int j;
        BaseEnemy enemy;
        for (i = 0; i < value.size; ++i) {
            for (j = 0; j < Control.arrWave.get(i); ++j) {
                enemy = TypeHandle.createEnemy(Control.arrBossRandType.get(i));
                enemy.setPosition(800.0f + MathUtils.random(50, 200), 100.0f);
                Global.arrBossCallEnemy.add(enemy);
            }
        }
        Global.bossCallState = -1;
    }
    
    public static Constant.EnemyType getBuildType() {
        while (Global.tranLength >= 1000.0f) {
            Constant.EnemyType enemyType;
            if (Global.tranLength < 2500.0f) {
                enemyType = Control.typeBuild2[MathUtils.random(0, Control.typeBuild2.length - 1)];
            }
            else if (Global.tranLength < 4000.0f) {
                enemyType = Control.typeBuild3[MathUtils.random(0, Control.typeBuild3.length - 1)];
            }
            else {
                enemyType = Control.typeBuild4[MathUtils.random(0, Control.typeBuild4.length - 1)];
            }
            Constant.EnemyType enemyType2 = enemyType;
            if (enemyType != Control.lastBuild1) {
                return enemyType2;
            }
            if (enemyType != Control.lastBuild2) {
                return enemyType;
            }
        }
        return null;
    }
    
    private static int getGameLevel() {
        int n;
        if (Global.gameMode == Constant.GameMode.Easy) {
            n = Global.curGameLevelEasy;
        }
        else {
            n = Global.curGameLevelHard;
        }
        int n2 = n;
        if (n < 1) {
            n2 = 1;
        }
        int n3;
        if ((n3 = n2) > 50) {
            n3 = 50;
        }
        return MathUtils.clamp(n3, 1, 50);
    }
    
    public static Constant.EnemyType getMoveType() {
        Constant.EnemyType enemyType;
        do {
            if (Global.tranLength < 1000.0f) {
                enemyType = Control.typeMove1[MathUtils.random(0, Control.typeMove1.length - 1)];
            }
            else if (Global.tranLength < 2500.0f) {
                enemyType = Control.typeMove2[MathUtils.random(0, Control.typeMove2.length - 1)];
            }
            else if (Global.tranLength < 4000.0f) {
                enemyType = Control.typeMove3[MathUtils.random(0, Control.typeMove3.length - 1)];
            }
            else {
                enemyType = Control.typeMove4[MathUtils.random(0, Control.typeMove4.length - 1)];
            }
        } while (enemyType == Control.lastMove1 && enemyType == Control.lastMove2);
        return enemyType;
    }
    
    private static boolean isBossType(Constant.EnemyType enemyType) {
        return enemyType == Constant.EnemyType.Bear1 || enemyType == Constant.EnemyType.Bear2 || enemyType == Constant.EnemyType.Wasp1 || enemyType == Constant.EnemyType.Wasp2 || enemyType == Constant.EnemyType.Rinocer1 || enemyType == Constant.EnemyType.Rinocer2 || enemyType == Constant.EnemyType.Dinosaur1 || enemyType == Constant.EnemyType.Dinosaur2 || enemyType == Constant.EnemyType.BDragon1 || enemyType == Constant.EnemyType.BDragon2;
    }
    
    private static boolean isBoxType(Constant.EnemyType enemyType) {
        return enemyType == Constant.EnemyType.Box1 || enemyType == Constant.EnemyType.Box2 || enemyType == Constant.EnemyType.Box3;
    }
    
    public static void levelClear() {
        if (!Global.isEndlessMode) {
            levelClearForNormal();
        }
        else {
            levelClearForEndless1();
        }
        Global.createEnemyArrayState = 2;
    }
    
    public static void levelClearForEndless1() {
        Global.mapEnemy.clear();
        for (int i = 0; i < Control.typeMove1.length; ++i) {
            Array<BaseEnemy> array = new Array<BaseEnemy>();
            Constant.EnemyType enemyType = Control.typeMove1[i];
            for (int j = 0; j < 12; ++j) {
                array.add(TypeHandle.createEnemy(enemyType));
            }
            Global.mapEnemy.put(enemyType, array);
        }
        Global.endlessHashState = 1;
    }
    
    public static void levelClearForEndless2() {
        for (int i = 0; i < Control.typeMove1.length; ++i) {
            Global.mapEnemy.get(Control.typeMove1[i]).clear();
        }
        Global.mapEnemy.clear();
        for (int j = 0; j < Control.typeMove2.length; ++j) {
            Array<BaseEnemy> array = new Array<BaseEnemy>();
            Constant.EnemyType enemyType = Control.typeMove2[j];
            for (int k = 0; k < 8; ++k) {
                array.add(TypeHandle.createEnemy(enemyType));
            }
            Global.mapEnemy.put(enemyType, array);
        }
        for (int l = 0; l < Control.typeBuild2.length; ++l) {
            Array<BaseEnemy> array2 = new Array<BaseEnemy>();
            Constant.EnemyType enemyType2 = Control.typeBuild2[l];
            for (int n = 0; n < 4; ++n) {
                array2.add(TypeHandle.createEnemy(enemyType2));
            }
            Global.mapEnemy.put(enemyType2, array2);
        }
        Global.endlessHashState = 1;
    }
    
    public static void levelClearForEndless3() {
        for (int i = 0; i < Control.typeMove2.length; ++i) {
            Global.mapEnemy.get(Control.typeMove2[i]).clear();
        }
        for (int j = 0; j < Control.typeBuild2.length; ++j) {
            Global.mapEnemy.get(Control.typeBuild2[j]).clear();
        }
        Global.mapEnemy.clear();
        for (int k = 0; k < Control.typeMove3.length; ++k) {
            Array<BaseEnemy> array = new Array<BaseEnemy>();
            Constant.EnemyType enemyType = Control.typeMove3[k];
            for (int l = 0; l < 8; ++l) {
                array.add(TypeHandle.createEnemy(enemyType));
            }
            Global.mapEnemy.put(enemyType, array);
        }
        for (int n = 0; n < Control.typeBuild3.length; ++n) {
            Array<BaseEnemy> array2 = new Array<BaseEnemy>();
            Constant.EnemyType enemyType2 = Control.typeBuild3[n];
            for (int n2 = 0; n2 < 4; ++n2) {
                array2.add(TypeHandle.createEnemy(enemyType2));
            }
            Global.mapEnemy.put(enemyType2, array2);
        }
        Global.endlessHashState = 1;
    }
    
    public static void levelClearForEndless4() {
        for (int i = 0; i < Control.typeMove3.length; ++i) {
            Global.mapEnemy.get(Control.typeMove3[i]).clear();
        }
        for (int j = 0; j < Control.typeBuild3.length; ++j) {
            Global.mapEnemy.get(Control.typeBuild3[j]).clear();
        }
        Global.mapEnemy.clear();
        for (int k = 0; k < Control.typeMove4.length; ++k) {
            Array<BaseEnemy> array = new Array<BaseEnemy>();
            Constant.EnemyType enemyType = Control.typeMove4[k];
            for (int l = 0; l < 8; ++l) {
                array.add(TypeHandle.createEnemy(enemyType));
            }
            Global.mapEnemy.put(enemyType, array);
        }
        for (int n = 0; n < Control.typeBuild4.length; ++n) {
            Array<BaseEnemy> array2 = new Array<BaseEnemy>();
            Constant.EnemyType enemyType2 = Control.typeBuild4[n];
            for (int n2 = 0; n2 < 4; ++n2) {
                array2.add(TypeHandle.createEnemy(enemyType2));
            }
            Global.mapEnemy.put(enemyType2, array2);
        }
        Global.endlessHashState = 1;
    }
    
    private static void levelClearForNormal() {
        int n;
        if (Global.gameMode == Constant.GameMode.Easy) {
            n = Global.curGameLevelEasy;
        }
        else {
            n = Global.curGameLevelHard;
        }
        JsonValue value = Global.manager.get("json/enemy/level" + n + ".json", JsonValue.class).get(0);
        Global.arrEnemyCreate.clear();
        Global.arrBossPos.clear();
        Global.arrBoxPos.clear();
        int n2 = 0;
        while (true) {
            JsonValue value2 = value.get(n2);
            if (value2 == null) {
                break;
            }
            Constant.EnemyType value3 = Constant.EnemyType.valueOf(value2.getString("name").substring(4));
            BaseEnemy enemy = TypeHandle.createEnemy(value3);
            float float1 = value2.getFloat("x");
            float float2 = value2.getFloat("y");
            if (value3 == Constant.EnemyType.Flag) {
                Global.levelEndPosX = float1;
            }
            else if (isBoxType(value3)) {
                Global.arrBoxPos.add(float1);
            }
            else if (isBossType(value3)) {
                Global.arrBossPos.add(float1);
                Global.arrBossType.add(value3);
            }
            enemy.setPosition(float1, float2);
            Global.arrEnemyCreate.add(enemy);
            Global.totalEnemy = Global.arrEnemyCreate.size - 1;
            ++n2;
        }
        Global.endlessHashState = 1;
    }
    
    public static void loadForGame() {
        int sceneLevel = Global.sceneLevel;
        Global.manager.load("json/enemy/level" + getGameLevel() + ".json", JsonValue.class);
        Global.manager.finishLoading();
        Global.manager.load("scene/scene" + sceneLevel + ".pack", TextureAtlas.class);
        Global.manager.load("ui/ui_weapon_enhance.pack", TextureAtlas.class);
        Global.manager.load("ui/ui_game_new.pack", TextureAtlas.class);
//        while (true) {
//            if (Global.isUnlockWeapon()) {
//                Global.manager.load("sound/dead_foot_1.ogg", Sound.class);
//                Global.manager.load("sound/dead_foot_2.ogg", Sound.class);
//                Global.manager.load("sound/dead_foot_3.ogg", Sound.class);
//                Global.manager.load("sound/dead_dragon_1.ogg", Sound.class);
//                Global.manager.load("sound/dead_dragon_2.ogg", Sound.class);
//                Global.manager.load("sound/dead_bomb_1.ogg", Sound.class);
//                Global.manager.load("sound/dead_bomb_2.ogg", Sound.class);
//                Global.manager.load("sound/dead_bomb_3.ogg", Sound.class);
//                loadForMainGun();
//                loadForSubGun();
//                loadForTurtle();
//                loadForGameLevel();
//                return;
//            }
//            continue;
//        }
        Global.manager.load("sound/dead_foot_1.ogg", Sound.class);
        Global.manager.load("sound/dead_foot_2.ogg", Sound.class);
        Global.manager.load("sound/dead_foot_3.ogg", Sound.class);
        Global.manager.load("sound/dead_dragon_1.ogg", Sound.class);
        Global.manager.load("sound/dead_dragon_2.ogg", Sound.class);
        Global.manager.load("sound/dead_bomb_1.ogg", Sound.class);
        Global.manager.load("sound/dead_bomb_2.ogg", Sound.class);
        Global.manager.load("sound/dead_bomb_3.ogg", Sound.class);
        loadForMainGun();
        loadForSubGun();
        loadForTurtle();
        loadForGameLevel();
    }
    
    private static void loadForGameLevel() {
        setEnemyType();
        setBossCallType();
        for (int i = 0; i < Global.arrEnemyType.size; ++i) {
            TypeHandle.loadForType(Global.arrEnemyType.get(i));
        }
    }
    
    public static void loadForLoading() {
        Global.manager.load("data/load.pack", TextureAtlas.class);
    }
    
    private static void loadForMainGun() {
        for (int i = 0; i < Global.arrStrMainSelect.size; ++i) {
            String s = Global.arrStrMainSelect.get(i);
            if (s.equals("SinglePipe")) {
                SinglePipe.loadElements();
            }
            if (s.equals("Cannon")) {
                Cannon.loadElements();
            }
            if (s.equals("Scatter")) {
                Scatter.loadElements();
            }
            if (s.equals("Flame")) {
                Flame.loadElements();
            }
            if (s.equals("Laser")) {
                Laser.loadElements();
            }
            if (s.equals("Electricity")) {
                Electricity.loadElements();
            }
            if (s.equals("DoublePipe")) {
                DoublePipe.loadElements();
            }
            if (s.equals("Acid")) {
                Acid.loadElements();
            }
            if (s.equals("Freezefog")) {
                Freezefog.loadElements();
            }
            if (s.equals("Missile")) {
                Missile.loadElements();
            }
            if (s.equals("Track")) {
                Track.loadElements();
            }
            if (s.equals("Shock")) {
                Shock.loadElements();
            }
            if (s.equals("Leap")) {
                Leap.loadElements();
            }
        }
    }
    
    public static void loadForMenu() {
        for (int min = Math.min(5, (Global.maxGameLevelEasy - 1) / 10 + 1), i = 0; i < min; ++i) {
            Global.manager.load("scene/scene" + (i + 1) + ".pack", TextureAtlas.class);
        }
        Global.manager.load("ui/ui_start_bg.pack", TextureAtlas.class);
        Global.manager.load("ui/ui_start.pack", TextureAtlas.class);
        Global.manager.load("ui/ui_level_select.pack", TextureAtlas.class);
        Global.manager.load("ui/ui_level_bg.pack", TextureAtlas.class);
        Global.manager.load("ui/ui_weapon_bg.pack", TextureAtlas.class);
        Global.manager.load("ui/ui_weapon_select.pack", TextureAtlas.class);
        Global.manager.load("ui/ui_weapon_enhance.pack", TextureAtlas.class);
        Global.manager.load("ui/ui_hero.pack", TextureAtlas.class);
        Global.manager.load("ui/ui_enemy_kill.pack", TextureAtlas.class);
        Global.manager.load("ui/ui_libao.pack", TextureAtlas.class);
    }
    
    public static void loadForStart() {
        Global.manager.load("font/msb15.fnt", BitmapFont.class);
        Global.manager.load("font/msb14.fnt", BitmapFont.class);
        Global.manager.load("font/kt14.fnt", BitmapFont.class);
        Global.manager.load("font/kt15.fnt", BitmapFont.class);
        Global.manager.load("data/trace2.png", Texture.class);
        for (int i = 0; i < 5; ++i) {
            Global.manager.load("json/scene/backscene" + (i + 1) + ".json", JsonValue.class);
        }
        Global.manager.load("json/weapon.json", JsonValue.class);
        Global.manager.load("json/enemy.json", JsonValue.class);
        Global.manager.load("json/bosscall/boss_call.json", JsonValue.class);
        Global.manager.load("effect/break.pack", TextureAtlas.class);
        Global.manager.load("effect/smoke.pack", TextureAtlas.class);
        Global.manager.load("effect/pipe_trace.pack", TextureAtlas.class);
        Global.manager.load("boss/pack/boss_bullet.pack", TextureAtlas.class);
        Global.manager.load("json/weapon_enhance.json", JsonValue.class);
        Global.manager.load("sound/button2.ogg", Sound.class);
        Global.manager.load("sound/button3.ogg", Sound.class);
        Global.manager.load("sound/number.ogg", Sound.class);
        Global.manager.load("sound/warning.ogg", Sound.class);
        Global.manager.load("sound/gold.ogg", Sound.class);
        Global.manager.load("sound/levelup.ogg", Sound.class);
        Global.manager.load("sound/star.ogg", Sound.class);
        Global.manager.load("sound/dead_boss.ogg", Sound.class);
        Global.manager.load("game/archer.pack", TextureAtlas.class);
        Global.manager.load("data/number.pack", TextureAtlas.class);
        Global.manager.load("data/instruction.pack", TextureAtlas.class);
        Global.manager.load("ui/ui_game.pack", TextureAtlas.class);
        Global.manager.load("ui/ui_pause_over.pack", TextureAtlas.class);
        Global.manager.load("ui/ui_store.pack", TextureAtlas.class);
        Global.manager.load("music/menu.ogg", Music.class);
        Global.manager.load("music/battle.ogg", Music.class);
        Global.manager.load("music/lose.ogg", Music.class);
        Global.manager.load("music/win.ogg", Music.class);
        loadForMenu();
    }
    
    private static void loadForSubGun() {
        if (Global.strSubGun.equals("Guinsoo")) {
            Guinsoo.loadElements();
        }
        else {
            if (Global.strSubGun.equals("Twine")) {
                Twine.loadElements();
                return;
            }
            if (Global.strSubGun.equals("Bomb")) {
                Bomb.loadElements();
                return;
            }
            if (Global.strSubGun.equals("Drum")) {
                Drum.loadElements();
                return;
            }
            if (Global.strSubGun.equals("Invince")) {
                Invince.loadElements();
                return;
            }
            if (Global.strSubGun.equals("Pastor")) {
                Pastor.loadElements();
                return;
            }
            if (Global.strSubGun.equals("Shield")) {
                Shield.loadElements();
                return;
            }
            if (Global.strSubGun.equals("Subcan")) {
                Subcan.loadElements();
            }
        }
    }
    
    private static void loadForTurtle() {
        Turtle.loadElements();
    }
    
    public static void setBossCallType() {
        Control.arrBossRandType.clear();
        Array<Constant.EnemyType> array = new Array<Constant.EnemyType>();
        for (int i = 0; i < Global.arrEnemyType.size; ++i) {
            Constant.EnemyType enemyType = Global.arrEnemyType.get(i);
            if (isBossType(enemyType)) {
                array.add(enemyType);
            }
        }
        if (array.size != 0) {
            JsonValue value = Global.manager.get("json/bosscall/boss_call.json", JsonValue.class).get(0);
            for (int j = 0; j < array.size; ++j) {
                JsonValue value2 = value.get(array.get(j).toString()).get("randTypes");
                for (int k = 0; k < value2.size; ++k) {
                    Control.arrBossRandType.add(Constant.EnemyType.valueOf(value2.get(k).asString()));
                }
            }
            for (int l = 0; l < Control.arrBossRandType.size; ++l) {
                Constant.EnemyType enemyType2 = Control.arrBossRandType.get(l);
                if (!Global.arrEnemyType.contains(enemyType2, true)) {
                    Global.arrEnemyType.add(enemyType2);
                }
            }
        }
    }
    
    private static void setEnemyType() {
        Global.arrEnemyType.clear();
        if (!Global.isEndlessMode) {
            int n;
            if (Global.gameMode == Constant.GameMode.Easy) {
                n = Global.curGameLevelEasy;
            }
            else {
                n = Global.curGameLevelHard;
            }
            JsonValue value = Global.manager.get("json/enemy/level" + n + ".json", JsonValue.class).get(0);
            int n2 = 0;
            while (true) {
                JsonValue value2 = value.get(n2);
                if (value2 == null) {
                    break;
                }
                Constant.EnemyType value3 = Constant.EnemyType.valueOf(value2.getString("name").substring(4));
                boolean b = false;
                int n3 = 0;
                boolean b2;
                while (true) {
                    b2 = b;
                    if (n3 >= Global.arrEnemyType.size) {
                        break;
                    }
                    if (Global.arrEnemyType.get(n3).equals(value3)) {
                        b2 = true;
                        break;
                    }
                    ++n3;
                }
                if (!b2) {
                    Global.arrEnemyType.add(value3);
                }
                ++n2;
            }
        }
        else {
            for (int i = 0; i < Constant.EnemyType.values().length - 11; ++i) {
                Global.arrEnemyType.add(Constant.EnemyType.values()[i]);
            }
        }
    }
    
    public static void unloadForGame() {
        for (int i = 0; i < 5; ++i) {
            String string = "scene/scene" + (i + 1) + ".pack";
            if (Global.manager.isLoaded(string)) {
                Global.manager.unload(string);
            }
        }
        Global.manager.unload("json/enemy/level" + getGameLevel() + ".json");
        Global.manager.unload("ui/ui_game_new.pack");
        Global.manager.unload("ui/ui_weapon_enhance.pack");
//        while (true) {
//            if (Global.isUnlockWeapon()) {
//                Global.manager.unload("sound/dead_foot_1.ogg");
//                Global.manager.unload("sound/dead_foot_2.ogg");
//                Global.manager.unload("sound/dead_foot_3.ogg");
//                Global.manager.unload("sound/dead_dragon_1.ogg");
//                Global.manager.unload("sound/dead_dragon_2.ogg");
//                Global.manager.unload("sound/dead_bomb_1.ogg");
//                Global.manager.unload("sound/dead_bomb_2.ogg");
//                Global.manager.unload("sound/dead_bomb_3.ogg");
//                unloadForGameLevel();
//                unloadForTurtle();
//                unloadForSubGun();
//                unloadForMainGun();
//                return;
//            }
//            continue;
//        }
        Global.manager.unload("sound/dead_foot_1.ogg");
        Global.manager.unload("sound/dead_foot_2.ogg");
        Global.manager.unload("sound/dead_foot_3.ogg");
        Global.manager.unload("sound/dead_dragon_1.ogg");
        Global.manager.unload("sound/dead_dragon_2.ogg");
        Global.manager.unload("sound/dead_bomb_1.ogg");
        Global.manager.unload("sound/dead_bomb_2.ogg");
        Global.manager.unload("sound/dead_bomb_3.ogg");
        unloadForGameLevel();
        unloadForTurtle();
        unloadForSubGun();
        unloadForMainGun();
}
    
    private static void unloadForGameLevel() {
        for (int i = 0; i < Global.arrEnemyType.size; ++i) {
            TypeHandle.loadForType(Global.arrEnemyType.get(i));
        }
    }
    
    public static void unloadForLoading() {
    }
    
    private static void unloadForMainGun() {
        for (int i = 0; i < Global.arrStrMainSelect.size; ++i) {
            String s = Global.arrStrMainSelect.get(i);
            if (s.equals("SinglePipe")) {
                SinglePipe.unloadElements();
            }
            if (s.equals("Cannon")) {
                Cannon.unloadElements();
            }
            if (s.equals("Scatter")) {
                Scatter.unloadElements();
            }
            if (s.equals("Flame")) {
                Flame.unloadElements();
            }
            if (s.equals("Laser")) {
                Laser.unloadElements();
            }
            if (s.equals("Electricity")) {
                Electricity.unloadElements();
            }
            if (s.equals("DoublePipe")) {
                DoublePipe.unloadElements();
            }
            if (s.equals("Acid")) {
                Acid.unloadElements();
            }
            if (s.equals("Freezefog")) {
                Freezefog.unloadElements();
            }
            if (s.equals("Missile")) {
                Missile.unloadElements();
            }
            if (s.equals("Track")) {
                Track.unloadElements();
            }
            if (s.equals("Shock")) {
                Shock.unloadElements();
            }
            if (s.equals("Leap")) {
                Leap.unloadElements();
            }
        }
    }
    
    public static void unloadForMenu() {
        for (int min = Math.min(5, (Global.maxGameLevelEasy - 1) / 10 + 1), i = 0; i < min; ++i) {
            String string = "scene/scene" + (i + 1) + ".pack";
            if (Global.manager.isLoaded(string)) {
                Global.manager.unload(string);
            }
        }
        Global.manager.unload("ui/ui_start_bg.pack");
        Global.manager.unload("ui/ui_start.pack");
        Global.manager.unload("ui/ui_level_select.pack");
        Global.manager.unload("ui/ui_level_bg.pack");
        Global.manager.unload("ui/ui_weapon_bg.pack");
        Global.manager.unload("ui/ui_weapon_select.pack");
        Global.manager.unload("ui/ui_weapon_enhance.pack");
        Global.manager.unload("ui/ui_hero.pack");
        Global.manager.unload("ui/ui_enemy_kill.pack");
        Global.manager.unload("ui/ui_libao.pack");
    }
    
    public static void unloadForStart() {
    }
    
    private static void unloadForSubGun() {
        if (Global.strSubGun.equals("Guinsoo")) {
            Guinsoo.unloadElements();
        }
        else {
            if (Global.strSubGun.equals("Twine")) {
                Twine.unloadElements();
                return;
            }
            if (Global.strSubGun.equals("Bomb")) {
                Bomb.unloadElements();
                return;
            }
            if (Global.strSubGun.equals("Drum")) {
                Drum.unloadElements();
                return;
            }
            if (Global.strSubGun.equals("Invince")) {
                Invince.unloadElements();
                return;
            }
            if (Global.strSubGun.equals("Pastor")) {
                Pastor.unloadElements();
                return;
            }
            if (Global.strSubGun.equals("Shield")) {
                Shield.unloadElements();
                return;
            }
            if (Global.strSubGun.equals("Subcan")) {
                Subcan.unloadElements();
            }
        }
    }
    
    private static void unloadForTurtle() {
        Turtle.unloadElements();
    }
}
