package com.gleejet.sun.roles;

import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.math.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.utils.*;
import com.gleejet.sun.common.*;
import com.gleejet.sun.objects.*;
import com.gleejet.sun.roles.boss.*;
import com.gleejet.sun.stages.*;
import com.gleejet.sun.utils.*;
import com.gleejet.sun.utils.ui.*;

public abstract class BaseEnemy extends Actor
{
    static int oriAddCoinCount;
    float acidDamage;
    float acidTime;
    int addCoinCount;
    public BulletPlayer attackBullet;
    protected float attackDamage;
    protected float attackInterval;
    protected float attackTime;
    protected float bulletBaseRotation;
    protected float bulletScale;
    protected float bulletSpeed;
    protected Constant.EnemyCategray categray;
    protected float coinKill;
    protected float currentHp;
    protected float currentTime;
    protected float dx;
    protected float dy;
    float flameDamage;
    float flameTime;
    protected float freezeRate;
    protected float freezeTime;
    float gapAcidTime;
    float gapFlameTime;
    boolean isDirectRight;
    public boolean isDrawHp;
    protected float lastAttackTime;
    protected float maxAttackDistance;
    protected float maxHp;
    protected float minAttackDistance;
    protected float moveSpeed;
    protected Color oriColor;
    protected float oriMoveSpeed;
    protected Vector2 originSpeed;
    protected Player player;
    public Polygon polygon;
    protected TextureRegion regionBullet;
    protected float slowTime;
    protected Vector2 speed;
    protected Sprite spriteHpBack;
    protected Sprite spriteHpFront;
    protected Constant.EnemyState state;
    protected final String strAtlas;
    float totalAcidTime;
    float totalFlameTime;
    protected Constant.EnemyType type;
    protected float yIncrease;
    
    static {
        BaseEnemy.oriAddCoinCount = 25;
    }
    
    public BaseEnemy() {
        this.speed = new Vector2();
        this.originSpeed = new Vector2();
        this.isDirectRight = false;
        this.bulletBaseRotation = 0.0f;
        this.bulletScale = 1.0f;
        this.polygon = null;
        this.attackBullet = null;
        this.slowTime = 0.0f;
        this.strAtlas = "game/archer.pack";
        this.isDrawHp = true;
        this.oriColor = Color.WHITE;
        this.addCoinCount = 0;
        (this.spriteHpBack = new Sprite(Assets.regionWhite2)).setColor(Color.BLACK);
        (this.spriteHpFront = new Sprite(Assets.regionWhite2)).setColor(Color.GREEN);
    }
    
    private int getCoinNum() {
        int n = 2;
        if (this.getType() == Constant.EnemyType.Box1 || this.getType() == Constant.EnemyType.Box2 || this.getType() == Constant.EnemyType.Box3) {
            n = 5;
        }
        else if (this instanceof BaseBoss) {
            return 10;
        }
        return n;
    }
    
    private void setSpeed() {
        final Vector2 speed = this.speed;
        float x;
        if (this.isDirectRight) {
            x = this.originSpeed.x;
        }
        else {
            x = -this.originSpeed.x;
        }
        speed.set(x, this.originSpeed.y);
    }
    
    public void BeAttacked(float n) {
        this.currentHp -= n;
        final int round = MathUtils.round(MathUtils.random(0.9f, 1.1f) * n);
        final int random = MathUtils.random(-1, 1);
        if (this instanceof BaseBoss) {
            n = this.getX() + this.getWidth() * 3.0f / 5.0f;
        }
        else {
            n = this.getX() + this.getWidth() / 5.0f;
        }
        MyLabel.obtain(Global.groupDamage, StrHandle.get("-", round + random), Constant.FontType.damage, MathUtils.random(-2, 2) + n, this.getY() + this.getHeight() * 3.0f / 4.0f);
        if (this.getStage() != null) {
            ((GameStage)this.getStage()).setCombo();
        }
        ++Global.curCombo;
        Global.labelComboNumber.setComboNumber(StrHandle.get(Global.curCombo));
        Global.labelComboShow.setComboShow();
        if (this.currentHp <= 0.0f) {
            this.currentHp = 0.0f;
            this.Die();
            ++Global.enemyKill;
            final int coinNum = this.getCoinNum();
            Coin.addCoin(Global.groupCoin, this.getX() + this.getWidth() / 2.0f, this.getY() + this.getHeight() * 2.0f / 3.0f, MathUtils.random(30, 50), MathUtils.random(coinNum, coinNum + 3), this.coinKill);
            int intValue;
            if (Global.mapTypeKill.get(this.getType()) == null) {
                intValue = 0;
            }
            else {
                intValue = Global.mapTypeKill.get(this.getType());
            }
            Global.mapTypeKill.put(this.getType(), intValue + 1);
        }
        this.UpdateHp();
        this.setAttackTime();
    }
    
    public void BeAttacked(final BulletPlayer bulletPlayer) {
        float n = 0.0f;
        switch (this.categray) {
            case Ground: {
                n = bulletPlayer.getDamageGround();
                break;
            }
            case Air: {
                n = bulletPlayer.getDamageAir();
                break;
            }
            case Build: {
                n = bulletPlayer.getDamageBuild();
                break;
            }
        }
        this.BeAttacked(n);
    }
    
    public void Clear() {
        this.slowTime = 0.0f;
        this.acidTime = 0.0f;
        this.flameTime = 0.0f;
        this.freezeTime = 0.0f;
    }
    
    public void Die() {
    }
    
    public Constant.EnemyCategray GetCategray() {
        return this.categray;
    }
    
    public abstract void UpdateHp();
    
    @Override
    public void act(final float n) {
        super.act(n);
        if (this.slowTime > 0.0f) {
            this.slowTime -= n;
            if (this.slowTime <= 0.0f) {
                this.setSpeed();
                if (this instanceof FlashEnemy) {
                    ((FlashEnemy)this).getCurFlash().play();
                }
            }
        }
        if (this.freezeTime > 0.0f) {
            this.freezeTime -= n;
            if (this.freezeTime <= 0.0f) {
                if (this.isDirectRight) {
                    this.speed.set(this.moveSpeed, 0.0f);
                }
                else {
                    this.speed.set(-this.moveSpeed, 0.0f);
                }
            }
        }
        if (this.attackTime > 0.0f) {
            this.attackTime -= n;
            if (this.attackTime <= 0.0f) {
                this.setColor(this.oriColor);
            }
        }
        if (this.currentHp > 0.0f && this.flameTime > 0.0f && this.gapFlameTime > 0.0f) {
            this.gapFlameTime -= n;
            if (this.gapFlameTime <= 0.0f) {
                this.flameTime -= 0.5f;
                this.gapFlameTime = 0.5f;
                if (MathUtils.random(0, 5) >= 3) {
                    Effect.addFlameEffect(this.getX() + this.getWidth() / 2.0f, this.getY() + this.getHeight() * 0.2f);
                }
                this.BeAttacked(this.flameDamage * 0.5f / this.totalFlameTime);
            }
        }
        if (this.currentHp > 0.0f && this.acidTime > 0.0f && this.gapAcidTime > 0.0f) {
            this.gapAcidTime -= n;
            if (this.gapAcidTime <= 0.0f) {
                this.acidTime -= 0.5f;
                this.gapAcidTime = 0.5f;
                if (MathUtils.random(0, 5) >= 3) {
                    Effect.addAcidEffect(this.getX() + this.getWidth() / 2.0f, this.getY() + this.getHeight() * 0.2f);
                }
                this.BeAttacked(this.acidDamage * 0.5f / this.totalAcidTime);
            }
        }
        this.dx = Global.pAdd.x;
        this.dy = Global.pAdd.y;
    }
    
    public void beAcid(final float acidDamage, final float n) {
        this.acidDamage = acidDamage;
        this.acidTime = n;
        this.gapAcidTime = 0.5f;
        this.totalAcidTime = n;
    }
    
    public void beFlame(final float flameDamage, final float n) {
        this.flameDamage = flameDamage;
        this.flameTime = n;
        this.totalFlameTime = n;
        this.gapFlameTime = 0.5f;
    }
    
    public void beFreeze(final float freezeRate, final float freezeTime) {
        this.freezeRate = freezeRate;
        this.freezeTime = freezeTime;
        this.originSpeed.set(Math.abs(this.speed.x), Math.abs(this.speed.y));
        if (this.isDirectRight) {
            this.speed.set(this.moveSpeed * this.freezeRate, 0.0f);
            return;
        }
        this.speed.set(-this.moveSpeed * this.freezeRate, 0.0f);
    }
    
    public void deadHandle() {
        this.remove();
        if (Global.isEndlessMode && Global.endlessHashState <= 1) {
            final Constant.EnemyType type = this.getType();
            if (Global.mapEnemy.get(type) != null && Global.mapEnemy.get(type).size < 8) {
                Global.mapEnemy.get(type).add(this);
            }
        }
    }
    
    public void draw(final SpriteBatch spriteBatch, final float n) {
        super.draw(spriteBatch, n);
        if (this.currentHp > 0.0f) {
            this.UpdateHp();
            if (this.isDrawHp) {
                this.spriteHpBack.draw(spriteBatch);
                this.spriteHpFront.draw(spriteBatch);
            }
        }
    }
    
    public float getAttackDamage() {
        return this.attackDamage;
    }
    
    public float getCurrentHp() {
        return this.currentHp;
    }
    
    public float getMaxHp() {
        return this.maxHp;
    }
    
    public Constant.EnemyState getState() {
        return this.state;
    }
    
    public Constant.EnemyType getType() {
        return this.type;
    }
    
    public boolean isDead() {
        return this.currentHp <= 0.0f;
    }
    
    @Override
    public boolean remove() {
        Global.arrEnemyCollision.removeValue(this, true);
        return super.remove();
    }
    
    public void setAttackTime() {
        this.attackTime = 0.08f;
        this.setColor(0.35f, 0.35f, 0.35f, 1.0f);
    }
    
    public void setBossValue() {
        this.setJsonValue(this.getClass().getName().substring(this.getClass().getName().lastIndexOf(46) + 1));
        this.attackInterval = 0.1f;
    }
    
    public void setJsonValue(final int n) {
        String jsonValue;
        final String s = jsonValue = this.getClass().getName().substring(this.getClass().getName().lastIndexOf(46) + 1);
        if (!s.equals("Freezecar")) {
            jsonValue = s;
            if (!s.equals("Stonecar")) {
                if (s.equals("Flamecar")) {
                    jsonValue = s;
                }
                else {
                    jsonValue = s + n;
                }
            }
        }
        this.setJsonValue(jsonValue);
    }
    
    public void setJsonValue(final String s) {
        this.type = Constant.EnemyType.valueOf(s);
        final JsonValue value = Assets.jsonProperty.get(s);
        if (value == null) {
            return;
        }
        float n;
        if (Global.gameMode == Constant.GameMode.Easy) {
            n = 1.0f;
        }
        else {
            n = 2.0f;
        }
        float n2;
        if (Global.gameMode == Constant.GameMode.Easy) {
            n2 = 1.0f;
        }
        else {
            n2 = 1.5f;
        }
        final float n3 = value.getFloat("maxHp", 0.0f) * n;
        this.maxHp = n3;
        this.currentHp = n3;
        this.attackDamage = value.getFloat("attackDamage", 0.0f) * n2;
        this.attackInterval = value.getFloat("attackInterval", 0.0f);
        if (this.attackInterval > 50.0f) {
            this.attackInterval /= 1000.0f;
        }
        this.moveSpeed = value.getFloat("moveSpeed", 0.0f) / 60.0f;
        this.maxAttackDistance = value.getFloat("maxAttackDistance", 0.0f);
        this.minAttackDistance = value.getFloat("minAttackDistance", 0.0f);
        this.bulletSpeed = value.getFloat("bulletSpeed", 0.0f) / 60.0f;
        this.yIncrease = value.getFloat("bulletGravity", 0.0f) / 60.0f;
        this.bulletBaseRotation = value.getFloat("bulletRotation", 0.0f);
        this.bulletScale = value.getFloat("bulletScale", 1.0f);
        this.coinKill = value.getFloat("coinKill", 0.0f) * n;
        this.categray = Constant.EnemyCategray.valueOf(value.getString("categray"));
        this.speed.set(-this.moveSpeed, 0.0f);
        this.originSpeed.set(Math.abs(this.speed.x), Math.abs(this.speed.y));
    }
    
    public void setPlayer(final Player player) {
        this.player = player;
    }
    
    public void setType(final Constant.EnemyType type) {
        this.type = type;
    }
    
    public void showEdge() {
        if (this.polygon != null && this.polygon.getVertices() != null) {
            MyMethods.showPolygon(Global.rend, this.polygon);
        }
    }
    
    public void slowSpeed(final float n, final float slowTime) {
        this.slowTime = slowTime;
        this.originSpeed.set(Math.abs(this.speed.x), Math.abs(this.speed.y));
        if (this.isDirectRight) {
            this.speed.set(this.moveSpeed * this.freezeRate, 0.0f);
        }
        else {
            this.speed.set(-this.moveSpeed * this.freezeRate, 0.0f);
        }
        if (this instanceof FlashEnemy) {
            ((FlashEnemy)this).getCurFlash().pause();
        }
    }
}
