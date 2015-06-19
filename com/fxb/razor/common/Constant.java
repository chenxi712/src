package com.fxb.razor.common;

import com.badlogic.gdx.graphics.*;

public class Constant
{
    public static final float Base_Width = 50.0f;
    public static final float Ground_Height = 82.0f;
    public static final float HEIGHT = 480.0f;
    public static final float Translate_Speed = 0.35f;
    public static final float WIDTH = 800.0f;
    public static final Color colorBlink;
    public static final float comboGapTime = 0.8f;
    public static final float dragLimitLength = 15.0f;
    public static EnemyType[] enemyTypes;
    public static final boolean isBossTest = false;
    public static final boolean isFlagNear = false;
    public static final boolean isFreeBuy = false;
    public static final boolean isHighLife = false;
    public static final boolean isLogFps = false;
    public static final boolean isLogTime = false;
    public static final boolean isLowLife = false;
    public static boolean isShowEdge = false;
    public static final boolean isShowFps = false;
    public static final boolean isTestMode = false;
    public static final int showOverAdCount = 3;
    public static final String[] strMainOrders;
    public static final String[] strMains;
    public static final String[] strSubOrders;
    public static final String[] strSubs;
    public static float tranSpeed;
    
    static {
        colorBlink = new Color(0.8f, 0.3f, 0.3f, 1.0f);
        Constant.tranSpeed = 0.35f;
        Constant.isShowEdge = false;
        strMains = new String[] { "SinglePipe", "Cannon", "Scatter", "Flame", "Laser", "Electricity", "DoublePipe", "Acid", "Freezefog", "Missile", "Track", "Shock", "Leap" };
        strSubs = new String[] { "Pastor", "Drum", "Twine", "Shield", "Bomb", "Subcan", "Invince" };
        strMainOrders = new String[] { "SinglePipe", "Cannon", "Scatter", "DoublePipe", "Acid", "Freezefog", "Leap", "Flame", "Electricity", "Missile", "Track", "Laser", "Shock" };
        strSubOrders = new String[] { "Twine", "Subcan", "Drum", "Shield", "Bomb", "Pastor", "Invince" };
        Constant.enemyTypes = EnemyType.values();
    }
    
    public enum BossLevelState
    {
        Boss_Dead, 
        Boss_Hide, 
        Boss_Start;
    }
    
    public enum BossState
    {
        Attack, 
        Dead, 
        Dizzy, 
        Dizzy_After, 
        Dizzy_Front, 
        Idle, 
        Run, 
        Skill1, 
        Skill2;
    }
    
    public enum BulletEnemyType
    {
        Boss, 
        Common, 
        Freezecar, 
        Machine;
    }
    
    public enum BulletPlayerType
    {
        Acid, 
        Cannon, 
        DoublePipe, 
        Electricity, 
        Flame, 
        Freezefog, 
        Laser, 
        Leap, 
        Missile, 
        Scatter, 
        Shock, 
        SinglePipe, 
        Track;
    }
    
    public enum EnemyCategray
    {
        Air, 
        Build, 
        Ground;
    }
    
    public enum EnemyState
    {
        Attack, 
        Connect, 
        Dead, 
        Move_Away, 
        Move_To;
    }
    
    public enum EnemyType
    {
        Airforce1, 
        Airforce2, 
        Airforce3, 
        Archer1, 
        Archer2, 
        Archer3, 
        Artillery1, 
        Artillery2, 
        Artillery3, 
        BDragon1, 
        BDragon2, 
        Bear1, 
        Bear2, 
        Bomber1, 
        Bomber2, 
        Bomber3, 
        Box1, 
        Box2, 
        Box3, 
        CanTower1, 
        CanTower2, 
        CanTower3, 
        Dinosaur1, 
        Dinosaur2, 
        Dragon1, 
        Dragon2, 
        Dragon3, 
        Flag, 
        Flamecar, 
        Freezecar, 
        Lancer1, 
        Lancer2, 
        Lancer3, 
        Mine1, 
        Mine2, 
        Mine3, 
        Rinocer1, 
        Rinocer2, 
        Selfdes1, 
        Selfdes2, 
        Selfdes3, 
        Spikeweed1, 
        Spikeweed2, 
        Spikeweed3, 
        Stonecar, 
        Tower1, 
        Tower2, 
        Tower3, 
        Wasp1, 
        Wasp2;
    }
    
    public enum EnhanceType
    {
        AirDamage, 
        AllDamage, 
        AttackArea, 
        AttackSpeed, 
        BuildDamage, 
        BulletCount, 
        DisableProb, 
        FlameDamage, 
        FreezeProb, 
        FreezeTime, 
        GroundDamage, 
        KillProb, 
        LeapCount, 
        MaxHp, 
        PalasyProb;
    }
    
    public enum FontType
    {
        combo, 
        common, 
        damage, 
        star;
    }
    
    public enum GameMode
    {
        Easy, 
        Hard;
    }
    
    public enum GameState
    {
        Game_Instruct, 
        Game_On, 
        Game_Pause, 
        Game_PreStart, 
        Game_Revive, 
        Game_Unlock, 
        Level_Lose, 
        Level_Win;
    }
    
    public enum NextScreen
    {
        Game_Screen, 
        Game_Start, 
        Level_Screen, 
        Level_Small, 
        Load_Enemy, 
        Loading_Screen, 
        Set_Screen, 
        Start_Screen, 
        Store_Screen, 
        Weapon_Enhance, 
        Weapon_Screen;
    }
    
    public enum PlayState
    {
        Exit, 
        Lose, 
        Play, 
        Restart, 
        Win;
    }
    
    public enum SubEnhanceType
    {
        AddDamage, 
        AddDrum, 
        AddHp, 
        ColdTime, 
        MissileCount, 
        ShiedDamage, 
        TwineCount;
    }
    
    public enum SubGunState
    {
        Attack, 
        Idle;
    }
    
    public enum TurtleState
    {
        BeAttack, 
        Dead, 
        Move;
    }
}
