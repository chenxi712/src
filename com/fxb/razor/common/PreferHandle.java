package com.fxb.razor.common;

import com.badlogic.gdx.utils.*;
import com.badlogic.gdx.*;
import com.fxb.razor.utils.*;

public class PreferHandle
{
    public static void checkSame(final Array<String> array) {
        for (int i = 0; i < array.size - 1; ++i) {
            for (int j = array.size - 1; j > i; --j) {
                if (array.get(i).equals(array.get(j))) {
                    array.removeIndex(j);
                }
            }
        }
    }
    
    public static void clearPreference() {
        clearWeaponGet();
        final Preferences preferences = Gdx.app.getPreferences("instruction");
        preferences.clear();
        preferences.flush();
        final Preferences preferences2 = Gdx.app.getPreferences("weapon_select");
        preferences2.clear();
        preferences2.flush();
        final Preferences preferences3 = Gdx.app.getPreferences("common");
        preferences3.clear();
        preferences3.flush();
    }
    
    public static void clearWeaponGet() {
        final Preferences preferences = Gdx.app.getPreferences("weapon_get");
        preferences.clear();
        preferences.flush();
    }
    
    public static boolean isTypeRecord(final Constant.EnemyType enemyType) {
        return enemyType != Constant.EnemyType.Mine1 && enemyType != Constant.EnemyType.Mine2 && enemyType != Constant.EnemyType.Mine3 && enemyType != Constant.EnemyType.Spikeweed1 && enemyType != Constant.EnemyType.Spikeweed2 && enemyType != Constant.EnemyType.Spikeweed3 && enemyType != Constant.EnemyType.Box1 && enemyType != Constant.EnemyType.Box2 && enemyType != Constant.EnemyType.Box3;
    }
    
    public static void readCommon() {
        final Preferences preferences = Gdx.app.getPreferences("common");
        Global.maxGameLevelEasy = preferences.getInteger("maxGameLevelEasy", 1);
        Global.curGameLevelEasy = preferences.getInteger("curGameLevelEasy", 1);
        Global.maxGameLevelHard = preferences.getInteger("maxGameLevelHard", 1);
        Global.curGameLevelHard = preferences.getInteger("curGameLevelHard", 1);
        Global.continualDays = preferences.getInteger("continualDays", 0);
        Global.lastLoginRewardTime = preferences.getLong("lastLoginRewardTime", 0L);
        Global.lastProbaTime = preferences.getLong("lastProbaTime", 0L);
        Global.totalCoinNum = preferences.getFloat("totalCoinNum", 0.0f);
        Global.totalMondNum = preferences.getInteger("totalMondNum", 0);
        Global.gameInitNum = preferences.getInteger("gameInitNum", 0);
        Global.speStartTime = preferences.getLong("speStartTime", System.currentTimeMillis() / 1000L);
        Global.autoRateCount = preferences.getInteger("autoRateCount", 0);
        if (preferences.contains("isUseRGBA4444")) {
            Global.isUseRGBA4444 = preferences.getBoolean("isUseRGBA4444", false);
        }
        else {
            Global.setUseRGBA4444(false);
        }
        Global.soundVolume = preferences.getFloat("soundVolume", 0.3f);
        Global.isAdFree = preferences.getBoolean("isAdFree", false);
    }
    
    public static boolean readInstruction(final String s) {
        return Gdx.app.getPreferences("instruction").getBoolean(s, false);
    }
    
    public static boolean readLevelGoldGet(final int n, final int n2) {
        return Gdx.app.getPreferences("levelReward").getBoolean(StrHandle.get("gold", n, n2), false);
    }
    
    public static boolean readLevelMondGet(final int n, final int n2) {
        return Gdx.app.getPreferences("levelReward").getBoolean(StrHandle.get("mond", n, n2), false);
    }
    
    public static int readLevelStar(final int n, final Constant.GameMode gameMode) {
        final Preferences preferences = Gdx.app.getPreferences("levelStar");
        String s;
        if (gameMode == Constant.GameMode.Easy) {
            s = StrHandle.get("levelEasy", n);
        }
        else {
            s = StrHandle.get("levelHard", n);
        }
        return preferences.getInteger(s, 0);
    }
    
    public static void readTypeKill() {
        final Preferences preferences = Gdx.app.getPreferences("typeKill");
        for (int i = 0; i < Constant.enemyTypes.length - 11; ++i) {
            final Constant.EnemyType enemyType = Constant.enemyTypes[i];
            if (isTypeRecord(enemyType)) {
                Global.mapTypeKill.put(enemyType, preferences.getInteger(enemyType.toString(), 0));
            }
        }
    }
    
    public static int readWeaponEnhance(final String s) {
        return Gdx.app.getPreferences("weapon_enhance").getInteger(s, 0);
    }
    
    public static void readWeaponGet() {
        final Preferences preferences = Gdx.app.getPreferences("weapon_get");
        if (Global.maxGameLevelEasy == 1) {
            preferences.clear();
        }
        Global.arrMainGunGet.clear();
        int n = 0;
        while (true) {
            final String string = preferences.getString(StrHandle.get("mainGet", n + 1));
            if (string == null || string.equals("")) {
                break;
            }
            Global.arrMainGunGet.add(string);
            ++n;
        }
        checkSame(Global.arrMainGunGet);
        Global.arrSubGunGet.clear();
        int n2 = 0;
        while (true) {
            final String string2 = preferences.getString(StrHandle.get("subGet", n2 + 1));
            if (string2 == null || string2.equals("")) {
                break;
            }
            Global.arrSubGunGet.add(string2);
            ++n2;
        }
        checkSame(Global.arrSubGunGet);
        if (!Global.arrMainGunGet.contains("SinglePipe", false)) {
            Global.arrMainGunGet.add("SinglePipe");
        }
        if (Global.maxGameLevelEasy >= 2 && !Global.arrMainGunGet.contains("Cannon", false)) {
            Global.arrMainGunGet.add("Cannon");
        }
        if (Global.maxGameLevelEasy >= 5 && !Global.arrMainGunGet.contains("Scatter", false)) {
            Global.arrMainGunGet.add("Scatter");
        }
    }
    
    public static void readWeaponSelect() {
        Global.arrStrMainSelect.clear();
        final Preferences preferences = Gdx.app.getPreferences("weapon_select");
        for (int i = 0; i < 3; ++i) {
            final String string = preferences.getString(StrHandle.get("strMainGun", i + 1));
            if (string != null && string != "") {
                Global.arrStrMainSelect.add(string);
            }
        }
        Global.strSubGun = preferences.getString("strSubGun", "");
    }
    
    public static void writeCommon() {
        final Preferences preferences = Gdx.app.getPreferences("common");
        preferences.putInteger("maxGameLevelEasy", Global.maxGameLevelEasy);
        preferences.putInteger("curGameLevelEasy", Global.curGameLevelEasy);
        preferences.putInteger("maxGameLevelHard", Global.maxGameLevelHard);
        preferences.putInteger("curGameLevelHard", Global.curGameLevelHard);
        preferences.putInteger("continualDays", Global.continualDays);
        preferences.putLong("lastLoginRewardTime", Global.lastLoginRewardTime);
        preferences.putLong("lastProbaTime", Global.lastProbaTime);
        preferences.putFloat("totalCoinNum", Global.totalCoinNum);
        preferences.putInteger("totalMondNum", (int)Global.totalMondNum);
        preferences.putInteger("gameInitNum", Global.gameInitNum);
        preferences.putLong("speStartTime", Global.speStartTime);
        preferences.putInteger("autoRateCount", Global.autoRateCount);
        preferences.putBoolean("isUseRGBA4444", Global.isUseRGBA4444);
        preferences.putFloat("soundVolume", Global.soundVolume);
        preferences.putBoolean("isAdFree", Global.isAdFree);
        preferences.flush();
    }
    
    public static void writeGold() {
        final Preferences preferences = Gdx.app.getPreferences("common");
        preferences.putFloat("totalCoinNum", Global.totalCoinNum);
        preferences.flush();
    }
    
    public static void writeInstruction(final String s, final boolean b) {
        final Preferences preferences = Gdx.app.getPreferences("instruction");
        preferences.putBoolean(s, b);
        preferences.flush();
    }
    
    public static void writeLevelGoldGet(final int n, final int n2, final boolean b) {
        final Preferences preferences = Gdx.app.getPreferences("levelReward");
        preferences.putBoolean(StrHandle.get("gold", n, n2), b);
        preferences.flush();
    }
    
    public static void writeLevelMondGet(final int n, final int n2, final boolean b) {
        final Preferences preferences = Gdx.app.getPreferences("levelReward");
        preferences.putBoolean(StrHandle.get("mond", n, n2), b);
        preferences.flush();
    }
    
    public static void writeLevelStar(final int n, final int n2, final Constant.GameMode gameMode) {
        final Preferences preferences = Gdx.app.getPreferences("levelStar");
        String s;
        if (gameMode == Constant.GameMode.Easy) {
            s = StrHandle.get("levelEasy", n);
        }
        else {
            s = StrHandle.get("levelHard", n);
        }
        preferences.putInteger(s, n2);
        preferences.flush();
    }
    
    public static void writeMond() {
        final Preferences preferences = Gdx.app.getPreferences("common");
        preferences.putInteger("totalMondNum", (int)Global.totalMondNum);
        preferences.flush();
    }
    
    public static void writeTypeKill() {
        final Preferences preferences = Gdx.app.getPreferences("typeKill");
        for (int i = 0; i < Constant.enemyTypes.length - 11; ++i) {
            final Constant.EnemyType enemyType = Constant.enemyTypes[i];
            if (isTypeRecord(enemyType)) {
                preferences.putInteger(enemyType.toString(), Global.mapTypeKill.get(enemyType));
            }
        }
        preferences.flush();
    }
    
    public static void writeWeaponEnhance(final String s, final int n) {
        final Preferences preferences = Gdx.app.getPreferences("weapon_enhance");
        preferences.putInteger(s, n);
        preferences.flush();
    }
    
    public static void writeWeaponGet() {
        final Preferences preferences = Gdx.app.getPreferences("weapon_get");
        for (int i = 0; i < Global.arrMainGunGet.size; ++i) {
            preferences.putString("mainGet" + (i + 1), Global.arrMainGunGet.get(i));
        }
        for (int j = 0; j < Global.arrSubGunGet.size; ++j) {
            preferences.putString("subGet" + (j + 1), Global.arrSubGunGet.get(j));
        }
        preferences.flush();
    }
    
    public static void writeWeaponSelect() {
        final Preferences preferences = Gdx.app.getPreferences("weapon_select");
        for (int i = 0; i < 3; ++i) {
            String s = "";
            if (i < Global.arrStrMainSelect.size) {
                s = Global.arrStrMainSelect.get(i);
            }
            preferences.putString("strMainGun" + (i + 1), s);
        }
        preferences.putString("strSubGun", Global.strSubGun);
        preferences.flush();
    }
}
