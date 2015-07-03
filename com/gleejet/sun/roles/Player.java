package com.gleejet.sun.roles;

import com.badlogic.gdx.scenes.scene2d.actions.*;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.utils.*;
import com.badlogic.gdx.math.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.gleejet.sun.common.*;
import com.gleejet.sun.objects.*;
import com.gleejet.sun.objects.maingun.*;
import com.gleejet.sun.objects.subgun.*;
import com.gleejet.sun.stages.*;
import com.gleejet.sun.utils.*;
import com.gleejet.sun.utils.ui.*;

public class Player extends Group
{
    private Actor actor1;
    private Actor actor2;
    private Array<BaseMainGun> arrMainGun;
    float attackTime;
    float collisionTime;
    private GroupHp curGroupHp;
    private BaseMainGun curGun;
    private float drumAddPercent;
    private float drumDural;
    private MyImage imgAddSpeed;
    MyImage imgInvince;
    private float invinceDural;
    boolean isBloodShake;
    boolean isBulletsShake;
    private boolean isDead;
    private InputListener listener;
    private float shieldDamage;
    private float shieldDural;
    private BaseSubGun subGun;
    private Turtle turtle;
    
    public Player() {
        this.arrMainGun = new Array<BaseMainGun>();
        this.isDead = false;
        this.curGun = null;
        this.shieldDamage = 0.0f;
        this.shieldDural = 0.0f;
        this.invinceDural = 0.0f;
        this.drumAddPercent = 0.0f;
        this.drumDural = 0.0f;
        this.collisionTime = 0.0f;
        this.attackTime = 0.0f;
        this.isBloodShake = false;
        this.isBulletsShake = false;
        this.listener = new InputListener() {
            boolean isDown = false;
            
            @Override
            public boolean touchDown(InputEvent inputEvent, float n, float n2, int n3, int n4) {
                if (Global.bossLevelState != Constant.BossLevelState.Boss_Start) {
                    Constant.tranSpeed = 1.75f;
                    this.isDown = true;
                }
                return true;
            }
            
            @Override
            public void touchDragged(InputEvent inputEvent, float tranSpeed, float n, int n2) {
                if (inputEvent.getListenerActor().hit(tranSpeed, n, true) == null) {
                    this.isDown = false;
                    if (Global.bossLevelState == Constant.BossLevelState.Boss_Start) {
                        tranSpeed = 0.0f;
                    }
                    else {
                        tranSpeed = 0.35f;
                    }
                    Constant.tranSpeed = tranSpeed;
                }
            }
            
            @Override
            public void touchUp(InputEvent inputEvent, float tranSpeed, float n, int n2, int n3) {
                if (this.isDown) {
                    this.isDown = false;
                    if (Global.bossLevelState == Constant.BossLevelState.Boss_Start) {
                        tranSpeed = 0.0f;
                    }
                    else {
                        tranSpeed = 0.35f;
                    }
                    Constant.tranSpeed = tranSpeed;
                }
            }
        };
        this.myclear();
        this.addActor(this.turtle = new Turtle());
        for (int i = 0; i < Global.arrStrMainSelect.size; ++i) {
            String s = Global.arrStrMainSelect.get(i);
            BaseMainGun baseMainGun = null;
            if (s.equals("SinglePipe")) {
                baseMainGun = new SinglePipe();
            }
            else if (s.equals("Cannon")) {
                baseMainGun = new Cannon();
            }
            else if (s.equals("Scatter")) {
                baseMainGun = new Scatter();
            }
            else if (s.equals("Flame")) {
                baseMainGun = new Flame();
            }
            else if (s.equals("Laser")) {
                baseMainGun = new Laser();
            }
            else if (s.equals("Electricity")) {
                baseMainGun = new Electricity();
            }
            else if (s.equals("DoublePipe")) {
                baseMainGun = new DoublePipe();
            }
            else if (s.equals("Acid")) {
                baseMainGun = new Acid();
            }
            else if (s.equals("Freezefog")) {
                baseMainGun = new Freezefog();
            }
            else if (s.equals("Missile")) {
                baseMainGun = new Missile();
            }
            else if (s.equals("Track")) {
                baseMainGun = new Track();
            }
            else if (s.equals("Shock")) {
                baseMainGun = new Shock();
            }
            else if (s.equals("Leap")) {
                baseMainGun = new Leap();
            }
            if (baseMainGun != null) {
                this.arrMainGun.add(baseMainGun);
                this.addActor(baseMainGun);
                baseMainGun.setVisible(false);
                baseMainGun.getGunIcon().setPosition(2.0f, (new float[] { 293.0f, 206.0f, 116.0f })[3 - Global.arrStrMainSelect.size + i]);
            }
        }
        String strSubGun = Global.strSubGun;
        if (strSubGun != null) {
            if (strSubGun.equals("Guinsoo")) {
                this.subGun = new Guinsoo();
            }
            else if (strSubGun.equals("Twine")) {
                this.subGun = new Twine();
            }
            else if (strSubGun.equals("Bomb")) {
                this.subGun = new Bomb();
            }
            else if (strSubGun.equals("Drum")) {
                this.subGun = new Drum();
            }
            else if (strSubGun.equals("Invince")) {
                this.subGun = new Invince();
            }
            else if (strSubGun.equals("Pastor")) {
                this.subGun = new Pastor();
            }
            else if (strSubGun.equals("Shield")) {
                this.subGun = new Shield();
            }
            else if (strSubGun.equals("Subcan")) {
                this.subGun = new Subcan();
            }
            if (this.subGun != null) {
                this.subGun.getSubGunIcon().setPosition(12.0f, 12.0f);
                this.addActor(this.subGun);
                if (this.subGun.getActorEffect() != null) {
                    this.addActor(this.subGun.getActorEffect());
                }
                if (strSubGun.equals("Shield") || strSubGun.equals("Invince")) {
                    this.subGun.getActorEffect().setZIndex(this.getChildren().size - 2);
                }
                if (this.subGun.getActorEffect2() != null) {
                    this.addActor(this.subGun.getActorEffect2());
                }
            }
        }
        this.setGunIndex(0);
        (this.actor1 = new Actor()).setSize(120.0f, 50.0f);
        this.actor1.setPosition(this.getX() + 70.0f, this.getY() + 148.0f);
        (this.actor2 = new Actor()).setSize(140.0f, 150.0f);
        this.actor2.setPosition(this.getX(), this.getY());
        (this.imgAddSpeed = UiHandle.addItem(this, Assets.atlasLoad, "white", 55.0f, -5.0f, this.listener)).setSize(130.0f, 75.0f);
        this.imgAddSpeed.setColor(MyShade.colorTran);
        this.initImgEffect();
    }
    
    private void autoResume(float n) {
        int n2 = 0;
        int n3;
        for (int i = 0; i < this.arrMainGun.size; ++i, n2 = n3) {
            n3 = n2;
            if (this.arrMainGun.get(i).getCurHp() > 0.0f) {
                n3 = n2 + 1;
            }
        }
        if (n2 == 1) {
            this.getCurMainGun().autoResume(n);
        }
    }
    
    private void initImgEffect() {
        (this.imgInvince = UiHandle.addItem((Group)null, Assets.atlasUiGame, "fanghuzhao", 58.0f, 72.0f, null, "show_invince")).setTouchable(Touchable.disabled);
        MyMethods.setActorOrigin(this.imgInvince, 0.5f, 0.5f);
    }
    
    private void showInvince() {
        this.imgInvince.clearActions();
        this.imgInvince.setScale(0.1f);
        this.imgInvince.addAction(Actions.sequence(Actions.scaleTo(1.0f, 1.0f, 0.5f, Interpolation.swingOut), Actions.forever(Actions.sequence(Actions.scaleTo(0.9f, 0.9f, 0.5f), Actions.scaleTo(1.05f, 1.05f, 0.5f)))));
        Global.groupEffectPlayer.addActor(this.imgInvince);
    }
    
    @Override
    public void act(float n) {
        super.act(n);
        if (this.shieldDural > 0.0f) {
            this.shieldDural -= n;
            if (this.shieldDural <= 0.0f) {
                this.shieldDural = 0.0f;
                this.shieldDamage = 0.0f;
                Actor actor = Global.groupEffectPlayer.findActor("show_shield");
                if (actor != null) {
                    actor.remove();
                }
            }
        }
        if (this.invinceDural > 0.0f) {
            this.invinceDural -= n;
            if (this.invinceDural <= 0.0f) {
                Actor actor2 = Global.groupEffectPlayer.findActor("show_invince");
                if (actor2 != null) {
                    actor2.remove();
                }
            }
        }
        if (this.drumDural > 0.0f) {
            this.drumDural -= n;
            if (this.drumDural <= 0.0f) {
                Actor actor3 = Global.groupEffectPlayer.findActor("show_drum");
                if (actor3 != null) {
                    actor3.remove();
                }
            }
        }
        if (this.attackTime > 0.0f) {
            this.attackTime -= n;
            if (this.attackTime <= 0.0f) {
                this.setColor(Color.WHITE);
            }
        }
        if (this.collisionTime > 0.0f) {
            this.collisionTime -= n;
            if (this.collisionTime <= 0.0f && Global.bossLevelState != Constant.BossLevelState.Boss_Start) {
                Constant.tranSpeed = 0.35f;
            }
        }
        this.checkCollision();
        this.checkBlood();
        this.checkBullets();
        this.autoResume(n);
    }
    
    public void addHpPercent(float n) {
        BaseMainGun curMainGun = this.getCurMainGun();
        if (curMainGun.getCurHp() > 0.0f) {
            curMainGun.addHpPercent(n);
        }
    }
    
    public void beAttacked(float n) {
        if (this.isInvince()) {
            return;
        }
        if (this.isShieldValid()) {
            this.shieldDamage(n);
            return;
        }
        this.getCurMainGun().BeAttacked(n);
    }
    
    public void beCollision(int n, float collisionTime) {
        this.turtle.switchBeattack();
        ((GameStage)this.getStage()).shake(n);
        this.collisionTime = collisionTime;
        if (Global.bossLevelState != Constant.BossLevelState.Boss_Start) {
            Constant.tranSpeed *= 0.1f;
        }
    }
    
    public void checkBlood() {
        Actor actor = this.getStage().getRoot().findActor("imgBlood");
        if (!this.isBloodShake) {
            if (this.getCurMainGun().getHpRate() < 0.3f) {
                UiHandle.startShake(actor, 0.6f);
                this.isBloodShake = true;
            }
        }
        else if (this.getCurMainGun().getHpRate() > 0.3f) {
            UiHandle.stopShake(actor);
            this.isBloodShake = false;
        }
    }
    
    protected void checkBullets() {
        if (!this.isBulletsShake) {
            if (this.getCurMainGun().getDuralRate() < 0.2f) {
                UiHandle.startShake(this.getStage().getRoot().findActor("imgNobullets"), 0.6f);
                this.isBulletsShake = true;
            }
        }
        else if (this.getCurMainGun().getDuralRate() > 0.2f) {
            UiHandle.stopShake(this.getStage().getRoot().findActor("imgNobullets"));
            this.isBulletsShake = false;
        }
    }
    
    public void checkCollision() {
        for (int i = Global.groupBulletEnemy.getChildren().size - 1; i >= 0; --i) {
            BulletEnemy bulletEnemy = (BulletEnemy)Global.groupBulletEnemy.getChildren().get(i);
            if (this.isOverlap(bulletEnemy)) {
                bulletEnemy.remove();
                Pools.free(bulletEnemy);
                this.beAttacked(bulletEnemy.getDamage());
                if (bulletEnemy.bulletType == Constant.BulletEnemyType.Freezecar || bulletEnemy.bulletType == Constant.BulletEnemyType.Machine) {
                    Effect.addSmoke(this.getX() + this.getWidth() / 2.0f + MathUtils.random(1.0f, 40.0f), this.getY() + MathUtils.random(30.0f, 60.0f), MathUtils.random(0.6f, 0.8f));
                }
                else if (bulletEnemy.bulletType == Constant.BulletEnemyType.Boss) {
                    Effect.addSmoke(this.getX() + this.getWidth() / 2.0f + MathUtils.random(1.0f, 40.0f), this.getY() + 50.0f, 0.7f);
                }
            }
        }
    }
    
    public void die() {
        this.isDead = true;
        this.turtle.switchDead();
    }
    
    @Override
    public void draw(SpriteBatch spriteBatch, float parentAlpha) {
//        BaseMainGun curMainGun = this.getCurMainGun();
//        while (true) {
//            if (curMainGun == null || curMainGun.getCurHp() > 0.0f) {
//                super.draw(spriteBatch, parentAlpha);
//                return;
//            }
//            continue;
//        }
          BaseMainGun curMainGun = this.getCurMainGun();
          if (curMainGun != null && curMainGun.getCurHp() > 0.0f) {
              super.draw(spriteBatch, parentAlpha);
          }
    }
    
    
    
    public Actor getActor1() {
        return this.actor1;
    }
    
    public Actor getActor2() {
        return this.actor2;
    }
    
    public Array<BaseMainGun> getArrayMainGun() {
        return this.arrMainGun;
    }
    
    public float getAttackRate() {
        float n = 1.0f;
        if (this.drumDural > 0.0f) {
            n = 1.0f + this.drumAddPercent;
        }
        return n;
    }
    
    public BaseMainGun getCurMainGun() {
        return this.curGun;
    }
    
    public MyImage getImgAddSpeed() {
        return this.imgAddSpeed;
    }
    
    public BaseSubGun getSubGun() {
        return this.subGun;
    }
    
    public boolean isDead() {
        return this.isDead;
    }
    
    public boolean isDrumValid() {
        return this.drumDural > 0.0f;
    }
    
    public boolean isInvince() {
        return this.invinceDural > 0.0f;
    }
    
    public boolean isOverlap(Actor actor) {
        return Collision.IsOverlap(this.actor1, actor) || Collision.IsOverlap(this.actor2, actor);
    }
    
    public boolean isShieldValid() {
        return this.shieldDamage > 0.0f && this.shieldDural > 0.0f;
    }
    
    public void myclear() {
        this.clearActions();
        this.setRotation(0.0f);
        this.setScale(1.0f);
        this.setSize(196.0f * this.getScaleX(), 189.0f * this.getScaleY());
        this.setOrigin(this.getWidth() / 2.0f, this.getHeight() / 2.0f);
        this.setPosition(70.0f, 105.0f);
    }
    
    public void revive() {
        this.isDead = false;
        this.turtle.switchMove();
        for (int i = 0; i < this.getArrayMainGun().size; ++i) {
            BaseMainGun baseMainGun = this.getArrayMainGun().get(i);
            baseMainGun.addHpPercent(0.8f);
            baseMainGun.revive();
        }
        this.getCurMainGun().setState(BaseMainGun.WorkState.State_Work);
        this.setInvinceTime(3.0f);
        this.showInvince();
    }
    
    public void setAttackTime() {
        this.attackTime = 0.08f;
        this.setColor(Constant.colorBlink);
    }
    
    public void setCurGun(BaseMainGun curGun) {
        if (this.curGun != null && this.curGun.isVisible()) {
            if (this.curGun.getState() != BaseMainGun.WorkState.State_Dead) {
                this.curGun.setState(BaseMainGun.WorkState.State_Rest);
            }
            this.curGun.setVisible(false);
            this.curGun.getGunIcon().setLightVisible(false);
        }
        (this.curGun = curGun).setOrigin(20.0f, 20.0f);
        this.curGun.setVisible(true);
        this.curGun.clearActions();
        this.curGun.setRotation(0.0f);
        this.curGun.setState(BaseMainGun.WorkState.State_Work);
        this.curGun.getGunIcon().setLightVisible(true);
        if (this.curGroupHp != null && this.curGroupHp.isVisible()) {
            this.curGroupHp.setVisible(false);
        }
        (this.curGroupHp = curGun.getGroupHp()).setVisible(true);
    }
    
    public void setDrum(float drumAddPercent, float drumDural) {
        this.drumAddPercent = drumAddPercent;
        this.drumDural = drumDural;
    }
    
    public void setGunIndex(int n) {
        this.setCurGun(this.arrMainGun.get(n));
    }
    
    public void setInvinceTime(float invinceDural) {
        this.invinceDural = invinceDural;
        this.showInvince();
    }
    
    public void setShield(float shieldDamage, float shieldDural) {
        this.shieldDamage = shieldDamage;
        this.shieldDural = shieldDural;
    }
    
    public void shieldDamage(float n) {
        this.shieldDamage -= n;
        if (this.shieldDamage <= 0.0f) {
            this.shieldDamage = 0.0f;
            this.shieldDural = 0.0f;
        }
    }
    
    public void showEdge() {
        MyMethods.showEdge(Global.rend, this.actor1);
        MyMethods.showEdge(Global.rend, this.actor2);
    }
}
