package com.gleejet.sun.objects.maingun;

import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.math.*;
import com.badlogic.gdx.scenes.scene2d.actions.*;
import com.badlogic.gdx.scenes.scene2d.*;

import java.util.*;

import com.gleejet.sun.common.*;
import com.gleejet.sun.flash.*;
import com.gleejet.sun.objects.*;
import com.gleejet.sun.roles.*;
import com.gleejet.sun.stages.*;
import com.gleejet.sun.utils.*;
import com.gleejet.sun.utils.action.*;
import com.gleejet.sun.utils.ui.*;
import com.badlogic.gdx.utils.*;

public abstract class BaseMainGun extends Group
{
    protected MyAnimation animation;
    protected float attackInterval;
    protected float attackRadius;
    protected float attackTime;
    protected float bulletCount;
    protected float bulletHeight;
    protected float bulletSpeed;
    protected float bulletWidth;
    int count;
    protected float curAngle;
    protected float curDural;
    protected float curHp;
    protected int curIndex;
    protected float curResume;
    protected float curTime;
    protected float damageAir;
    protected float damageBuild;
    protected float damageGround;
    protected float disableProb;
    protected int endFrame;
    protected float flameDamage;
    protected FlashPlayer[] flashPlayers;
    public float freezeProb;
    public float freezeTime;
    protected Group groupGun;
    protected GroupHp groupHp;
    protected float gunOriginX;
    protected float gunOriginY;
    protected MyImage imgDrawf;
    protected MyImage imgGunBody;
    protected MyImage imgGunFront;
    protected MyImage imgGunShelf;
    protected MyImage imgPlatform;
    protected boolean isInputValid;
    protected float killProb;
    float lastAngle;
    protected float lastAttackTime;
    protected float lastX;
    protected float lastY;
    protected float leapCount;
    InputListener listener;
    protected MainGunIcon mainGunIcon;
    protected float maxHp;
    protected float palasyProb;
    protected Vector2 pos;
    protected Vector2 position;
    protected TextureRegion regionBullet;
    protected float scale;
    protected final String strAtlas;
    protected float timeRotate;
    protected float totalDural;
    protected float totalResume;
    protected WorkState workState;
    protected float yIncrease;
    
    public BaseMainGun() {
        this.workState = WorkState.State_Ready;
        this.lastAttackTime = -100.0f;
        this.maxHp = 1000.0f;
        this.curHp = this.maxHp;
        this.position = new Vector2();
        this.mainGunIcon = new MainGunIcon();
        this.groupHp = new GroupHp();
        this.timeRotate = 0.0f;
        this.isInputValid = false;
        this.pos = new Vector2();
        this.curIndex = 0;
        this.freezeProb = 0.3f;
        this.freezeTime = 2.0f;
        this.strAtlas = "game/archer.pack";
        this.lastAngle = 0.0f;
        this.count = 0;
        this.listener = new InputListener() {
            @Override
            public boolean touchDown(final InputEvent inputEvent, final float n, final float n2, final int n3, final int n4) {
                final Actor listenerActor = inputEvent.getListenerActor();
                listenerActor.setOrigin(listenerActor.getWidth() / 2.0f, listenerActor.getHeight() / 2.0f);
                listenerActor.addAction(TouchAction.downAction());
                return true;
            }
            
            @Override
            public void touchUp(final InputEvent inputEvent, final float n, final float n2, final int n3, final int n4) {
                final Actor listenerActor = inputEvent.getListenerActor();
                SoundHandle.playForButton3();
                listenerActor.setOrigin(listenerActor.getWidth() / 2.0f, listenerActor.getHeight() / 2.0f);
                listenerActor.addAction(TouchAction.upAction());
                if (inputEvent.getListenerActor() == BaseMainGun.this.mainGunIcon) {
                    ((GameStage)BaseMainGun.this.getStage()).doInstruction();
                    ((Player)BaseMainGun.this.getParent()).setCurGun(BaseMainGun.this);
                }
                super.touchUp(inputEvent, n, n2, n3, n4);
            }
        };
        this.groupGun = new Group();
        this.setVisible(false);
        this.curAngle = 0.0f;
        this.mainGunIcon.addListener(this.listener);
        this.groupHp.setHpRate(1.0f);
    }
    
    public void AddGunRecoil() {
    }
    
    public void BeAttacked(final float n) {
        this.curHp -= n;
        if (this.curHp < 0.0f) {
            this.curHp = 0.0f;
        }
        this.setAttackTime();
        ((Player)this.getParent()).setAttackTime();
    }
    
    @Override
    public void act(final float n) {
        super.act(n);
        this.curTime += n;
        if (this.workState == WorkState.State_Rest) {
            this.addRate(n / this.totalResume);
        }
        else if (this.workState != WorkState.State_Work && this.workState == WorkState.State_Dead) {
            this.curDural = 0.0f;
        }
        this.setChildrenColor(((Player)this.getParent()).getColor());
        if (this.timeRotate > 0.0f) {
            this.timeRotate -= n;
        }
        final float n2 = this.curDural / this.totalDural;
        this.mainGunIcon.setDural(n2);
        this.groupHp.setDural(n2);
        this.groupHp.setHpRate(this.curHp / this.maxHp);
        if (this.flashPlayers != null && this.flashPlayers[this.curIndex] != null) {
            this.flashPlayers[this.curIndex].updateRunTime(n);
            this.flashPlayers[this.curIndex].setPosition(this.getX(), this.getY());
            this.flashPlayers[this.curIndex].getPosition();
        }
    }
    
    public abstract void addBullet(final float p0, final float p1);
    
    public void addHpPercent(final float n) {
        this.curHp += this.maxHp * n;
        if (this.curHp > this.maxHp) {
            this.curHp = this.maxHp;
        }
    }
    
    public void addRate(final float n) {
        this.curDural += this.totalDural * n;
        if (this.curDural >= this.totalDural) {
            this.curDural = this.totalDural;
        }
    }
    
    public void autoResume(final float n) {
        this.addRate(n / this.totalResume);
    }
    
    public void checkState() {
        if (this.flashPlayers[this.curIndex].getFrameIndex() >= this.endFrame) {
            this.flashPlayers[this.curIndex].setFrameIndex(0);
            this.flashPlayers[this.curIndex].pause();
        }
    }
    
    public void die() {
        this.setState(WorkState.State_Dead);
        this.mainGunIcon.setDead();
        final Player player = (Player)this.getParent();
        player.beCollision(3, 2.0f);
        Effect.addSmoke(player.getX() + player.getWidth() / 2.0f + 20.0f, player.getY() + 30.0f, 1.1f);
    }
    
    public void dispose() {
    }
    
    @Override
    public void draw(final SpriteBatch spriteBatch, final float n) {
        if (this.count++ % 1 == 0) {
            this.setPos(this, Global.pMain2, Global.pMain1);
        }
        if (this instanceof Laser) {
            this.drawMainGun(spriteBatch);
            super.draw(spriteBatch, n);
            return;
        }
        super.draw(spriteBatch, n);
        this.drawMainGun(spriteBatch);
    }
    
    protected void drawMainGun(final SpriteBatch spriteBatch) {
        final Color color = spriteBatch.getColor();
        spriteBatch.setColor(((Player)this.getParent()).getColor());
        if (this.flashPlayers != null && this.flashPlayers[this.curIndex] != null) {
            this.flashPlayers[this.curIndex].drawFlashRotation(spriteBatch, this.gunOriginX, this.gunOriginY, this.curAngle);
        }
        spriteBatch.setColor(color);
    }
    
    protected void duralHandle() {
        --this.curDural;
        if ((int)this.curDural < 1) {
            return;
        }
    }
    
    public void enemyBeAttack(final BaseEnemy baseEnemy) {
        final float attackRate = ((Player)this.getParent()).getAttackRate();
        switch (baseEnemy.GetCategray()) {
            default: {}
            case Ground: {
                baseEnemy.BeAttacked(this.damageGround * attackRate);
            }
            case Air: {
                baseEnemy.BeAttacked(this.damageAir * attackRate);
            }
            case Build: {
                baseEnemy.BeAttacked(this.damageBuild * attackRate);
            }
        }
    }
    
    public float getCurHp() {
        return this.curHp;
    }
    
    public float getDuralRate() {
        return this.curDural / this.totalDural;
    }
    
    public GroupHp getGroupHp() {
        return this.groupHp;
    }
    
    public MainGunIcon getGunIcon() {
        return this.mainGunIcon;
    }
    
    public float getHpRate() {
        return this.curHp / this.maxHp;
    }
    
    public float getMaxHp() {
        return this.maxHp;
    }
    
    public WorkState getState() {
        return this.workState;
    }
    
    public float getTimeRotate() {
        return this.timeRotate;
    }
    
    protected boolean isCanAttack() {
        return (int)this.curDural > 0 && this.curTime - this.lastAttackTime >= this.attackInterval;
    }
    
    public void myclear() {
    }
    
    public void revive() {
        this.setState(WorkState.State_Ready);
        this.mainGunIcon.revive();
        this.curDural = this.totalDural;
    }
    
    public void setAngle(final float lastX, final float lastY) {
        if (this.lastX != lastX || this.lastY != lastY) {
            this.position.set(this.groupGun.getOriginX(), this.groupGun.getOriginY() + 10.0f);
            this.groupGun.localToStageCoordinates(this.position);
            final float curAngle = MathUtils.atan2(lastY - this.position.y, lastX - this.position.x) * 57.295776f;
            if (curAngle <= 70.0f && curAngle >= -70.0f) {
                this.groupGun.addAction(Actions.rotateTo(curAngle, 0.05f));
                this.timeRotate = 0.05f;
                this.curAngle = curAngle;
                this.lastX = lastX;
                this.lastY = lastY;
            }
        }
    }
    
    public void setAttackTime() {
        this.attackTime = 0.08f;
    }
    
    public void setBulletDamage(final BulletPlayer bulletPlayer) {
        final Player player = (Player)this.getParent();
        bulletPlayer.setDamage(this.damageGround * player.getAttackRate(), this.damageAir * player.getAttackRate(), this.damageBuild * player.getAttackRate());
    }
    
    public void setChildrenColor(final Color color) {
        this.imgDrawf.setColor(color);
        this.imgPlatform.setColor(color);
        this.imgGunBody.setColor(color);
        this.imgGunFront.setColor(color);
        this.imgGunShelf.setColor(color);
    }
    
    protected void setEnhanceLevel() {
        final String substring = this.getClass().getName().substring(this.getClass().getName().lastIndexOf(46) + 1);
        final JsonValue value = Assets.jsonEnhance.get(substring);
        final int weaponEnhance = PreferHandle.readWeaponEnhance(substring);
        final String[] array = new String[7];
        for (int i = 0; i < array.length; ++i) {
            array[i] = value.get("types").get(i).asString();
        }
        final float[] array2 = new float[7];
        for (int j = 0; j < array2.length; ++j) {
            array2[j] = value.get("addValue").get(j).asFloat();
        }
        final HashMap<Constant.EnhanceType, Float> hashMap = new HashMap<Constant.EnhanceType, Float>();
        for (int k = 0; k < Constant.EnhanceType.values().length; ++k) {
            hashMap.put(Constant.EnhanceType.values()[k], 0.0f);
        }
        for (int l = 0; l < weaponEnhance; ++l) {
            final Constant.EnhanceType value2 = Constant.EnhanceType.valueOf(array[l]);
            hashMap.put(value2, array2[l] + hashMap.get(value2));
        }
        this.damageGround *= (hashMap.get(Constant.EnhanceType.AllDamage) + (100.0f + hashMap.get(Constant.EnhanceType.GroundDamage))) / 100.0f;
        this.damageAir *= (hashMap.get(Constant.EnhanceType.AllDamage) + (100.0f + hashMap.get(Constant.EnhanceType.AirDamage))) / 100.0f;
        this.damageBuild *= (hashMap.get(Constant.EnhanceType.AllDamage) + (100.0f + hashMap.get(Constant.EnhanceType.BuildDamage))) / 100.0f;
        final float n = (hashMap.get(Constant.EnhanceType.MaxHp) + 100.0f) * this.maxHp / 100.0f;
        this.maxHp = n;
        this.curHp = n;
        this.attackInterval *= 100.0f / (hashMap.get(Constant.EnhanceType.AttackSpeed) + 100.0f);
        this.attackRadius *= (hashMap.get(Constant.EnhanceType.AttackArea) + 100.0f) / 100.0f;
        this.disableProb *= (hashMap.get(Constant.EnhanceType.DisableProb) + 100.0f) / 100.0f;
        this.flameDamage *= (hashMap.get(Constant.EnhanceType.FlameDamage) + 100.0f) / 100.0f;
        this.freezeProb *= (hashMap.get(Constant.EnhanceType.FreezeProb) + 100.0f) / 100.0f;
        this.freezeTime *= (hashMap.get(Constant.EnhanceType.FreezeTime) + 100.0f) / 100.0f;
        final float n2 = (hashMap.get(Constant.EnhanceType.BulletCount) + 100.0f) * this.totalDural / 100.0f;
        this.totalDural = n2;
        this.curDural = n2;
        this.palasyProb *= (hashMap.get(Constant.EnhanceType.PalasyProb) + 100.0f) / 100.0f;
        this.leapCount += hashMap.get(Constant.EnhanceType.LeapCount);
    }
    
    public void setGunOrigin(final float gunOriginX, final float gunOriginY) {
        this.gunOriginX = gunOriginX;
        this.gunOriginY = gunOriginY;
    }
    
    public void setGunVisible(final boolean b) {
        if (this.imgGunBody != null) {
            this.imgGunBody.setVisible(false);
        }
        if (this.imgGunFront != null) {
            this.imgGunFront.setVisible(false);
        }
        if (this.imgGunShelf != null) {
            this.imgGunShelf.setVisible(false);
        }
        if (this.imgPlatform != null) {
            this.imgPlatform.setVisible(false);
        }
    }
    
    protected void setIcon(final int n) {
        final String string = "ren" + n;
        this.mainGunIcon.setRegionAlive(Assets.atlasUiGame.findRegion(string + "-2"));
        this.mainGunIcon.setRegionDead(Assets.atlasUiGame.findRegion(string + "-b"));
        this.groupHp.setIcon(Assets.atlasUiGame.findRegion(string));
    }
    
    public void setInputValid() {
        this.isInputValid = true;
    }
    
    public void setJsonValue() {
        final JsonValue value = Assets.jsonWeapon.get(this.getClass().getName().substring(this.getClass().getName().lastIndexOf(46) + 1));
        final float n = value.getFloat("maxHp") * 1.0f;
        this.maxHp = n;
        this.curHp = n;
        final float n2 = value.getFloat("totalDural") * 1.0f;
        this.totalDural = n2;
        this.curDural = n2;
        this.totalResume = value.getFloat("totalResume");
        this.attackInterval = value.getFloat("attackInterval");
        if (this.attackInterval > 50.0f) {
            this.attackInterval /= 1000.0f;
        }
        this.damageGround = value.getFloat("damageGround") * 1.0f;
        this.damageAir = value.getFloat("damageAir") * 1.0f;
        this.damageBuild = value.getFloat("damageBuild") * 1.0f;
        this.bulletSpeed = value.getFloat("bulletSpeed") / 60.0f;
        this.yIncrease = value.getFloat("bulletGravity") / 60.0f;
        this.attackRadius = value.getFloat("attackRadius");
    }
    
    public void setPos(final Actor actor, final Vector2 v1, final Vector2 v2) {
        actor.setPosition(v1.x - 69.0f, v1.y - 93.0f);
        float lastAngle;
        final float n = lastAngle = MathUtils.atan2(v2.y - v1.y, v2.x - v1.x) * 57.295776f;
        if (Math.abs(n) < 2.0f) {
            lastAngle = n;
            if (Math.abs(n - this.lastAngle) < 0.1f) {
                lastAngle = 0.0f;
            }
        }
        actor.setRotation(this.lastAngle = lastAngle);
    }
    
    public void setState(final WorkState workState) {
        this.workState = workState;
    }
    
    public enum WorkState
    {
        State_Dead, 
        State_Ready, 
        State_Rest, 
        State_Work;
    }
}
