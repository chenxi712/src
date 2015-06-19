package com.fxb.razor.roles;

import com.fxb.razor.common.*;
import com.badlogic.gdx.scenes.scene2d.actions.*;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.math.*;

public class StillEnemy extends BaseEnemy
{
    protected TextureRegion region;
    
    public StillEnemy() {
        this.region = null;
    }
    
    private boolean isAttackType() {
        return this.type != Constant.EnemyType.Flag && this.type != Constant.EnemyType.Box1 && this.type != Constant.EnemyType.Box2 && this.type != Constant.EnemyType.Box3;
    }
    
    @Override
    public void Clear() {
        super.Clear();
        this.clearActions();
        this.setRotation(0.0f);
        this.setScale(1.0f);
        this.currentHp = this.maxHp;
        this.UpdateHp();
    }
    
    @Override
    public void Die() {
        super.Die();
        Effect.addSmoke(this.getX() + this.getWidth() / 2.0f, this.getY());
        this.deadHandle();
        SoundHandle.playForBomb();
    }
    
    @Override
    public Constant.EnemyCategray GetCategray() {
        return Constant.EnemyCategray.Build;
    }
    
    @Override
    public void UpdateHp() {
        this.spriteHpBack.setSize(this.getWidth() * 0.9f, 4.0f);
        this.spriteHpBack.setPosition(this.getX(), this.getTop() - 4.0f);
        this.spriteHpFront.setSize((this.getWidth() * 0.9f - 2.0f) * this.currentHp / this.maxHp, 2.0f);
        this.spriteHpFront.setPosition(this.getX() + 1.0f, this.getTop() - 3.0f);
    }
    
    @Override
    public void act(final float n) {
        super.act(n);
        this.translate(-Constant.tranSpeed, 0.0f);
        if (this.isAttackType() && this.player.getRight() - this.getX() > 10.0f) {
            this.state = Constant.EnemyState.Dead;
            this.player.beAttacked(this.attackDamage);
            this.player.beCollision(1, 1.0f);
            this.Die();
            Global.isAllKill = false;
        }
        if (this.type == Constant.EnemyType.Flag && this.getRight() < -10.0f) {
            this.remove();
            Global.isAllKill = false;
        }
        this.setPolygonPosition();
    }
    
    @Override
    public void deadHandle() {
        if (this.type == Constant.EnemyType.Box1 || this.type == Constant.EnemyType.Box2 || this.type == Constant.EnemyType.Box3) {
            switch (this.type) {
                case Box1: {
                    this.region = Assets.atlasArcher.findRegion("box_open2");
                    break;
                }
                case Box2: {
                    this.region = Assets.atlasArcher.findRegion("box_open2");
                    break;
                }
                case Box3: {
                    this.region = Assets.atlasArcher.findRegion("box_open3");
                    break;
                }
            }
            this.addAction(Actions.sequence(Actions.delay(1.0f), Actions.fadeOut(1.5f), Actions.removeActor()));
            return;
        }
        super.deadHandle();
    }
    
    @Override
    public void draw(final SpriteBatch spriteBatch, final float n) {
        final Color color = spriteBatch.getColor();
        spriteBatch.setColor(this.getColor());
        if (this.region != null) {
            spriteBatch.draw(this.region, this.getX(), this.getY(), this.getOriginX(), this.getOriginY(), this.getWidth(), this.getHeight(), this.getScaleX(), this.getScaleY(), this.getRotation());
        }
        super.draw(spriteBatch, n);
        spriteBatch.setColor(color);
    }
    
    public void setPolygonPosition() {
        if (this.polygon == null) {
            return;
        }
        switch (this.type) {
            default: {}
            case Mine1: {
                this.polygon.setPosition(this.getX(), this.getY());
            }
            case Mine2: {
                this.polygon.setPosition(this.getX(), this.getY());
            }
            case Mine3: {
                this.polygon.setPosition(this.getX(), this.getY());
            }
            case Spikeweed1: {
                this.polygon.setPosition(this.getX(), this.getY());
            }
            case Spikeweed2: {
                this.polygon.setPosition(this.getX(), this.getY());
            }
            case Spikeweed3: {
                this.polygon.setPosition(this.getX(), this.getY());
            }
            case Box1: {
                this.polygon.setPosition(this.getX(), this.getY());
            }
            case Box2: {
                this.polygon.setPosition(this.getX(), this.getY());
            }
            case Box3: {
                this.polygon.setPosition(this.getX(), this.getY());
            }
        }
    }
    
    @Override
    public void setType(final Constant.EnemyType type) {
        this.type = type;
        if (this.type == Constant.EnemyType.Spikeweed1) {
            this.region = Assets.atlasArcher.findRegion("dici-1");
            this.maxHp = 50.0f;
            this.attackDamage = 10.0f;
            this.attackInterval = 1.0f;
            this.maxAttackDistance = 2.0f;
            this.minAttackDistance = 0.0f;
            this.moveSpeed = 0.0f;
            this.categray = Constant.EnemyCategray.Build;
            this.setSize(this.region.getRegionWidth() / 1.0f, this.region.getRegionHeight() / 1.0f);
        }
        else if (this.type == Constant.EnemyType.Spikeweed2) {
            this.region = Assets.atlasArcher.findRegion("dici-2");
            this.maxHp = 50.0f;
            this.attackDamage = 10.0f;
            this.attackInterval = 1.0f;
            this.maxAttackDistance = 2.0f;
            this.minAttackDistance = 0.0f;
            this.moveSpeed = 0.0f;
            this.categray = Constant.EnemyCategray.Build;
            this.setSize(this.region.getRegionWidth() / 1.0f, this.region.getRegionHeight() / 1.0f);
        }
        else if (this.type == Constant.EnemyType.Spikeweed3) {
            this.region = Assets.atlasArcher.findRegion("dici-3");
            this.maxHp = 50.0f;
            this.attackDamage = 10.0f;
            this.attackInterval = 1.0f;
            this.maxAttackDistance = 2.0f;
            this.minAttackDistance = 0.0f;
            this.moveSpeed = 0.0f;
            this.categray = Constant.EnemyCategray.Build;
            this.setSize(this.region.getRegionWidth() / 1.0f, this.region.getRegionHeight() / 1.0f);
        }
        else if (this.type == Constant.EnemyType.Mine1) {
            this.region = Assets.atlasArcher.findRegion("dilei-1");
            this.maxHp = 50.0f;
            this.attackDamage = 50.0f;
            this.attackInterval = 1.0f;
            this.maxAttackDistance = 2.0f;
            this.minAttackDistance = 0.0f;
            this.moveSpeed = 0.0f;
            this.categray = Constant.EnemyCategray.Build;
            this.setSize(this.region.getRegionWidth() / 1.5f, this.region.getRegionHeight() / 1.5f);
        }
        else if (this.type == Constant.EnemyType.Mine2) {
            this.region = Assets.atlasArcher.findRegion("dilei-2");
            this.maxHp = 50.0f;
            this.attackDamage = 50.0f;
            this.attackInterval = 1.0f;
            this.maxAttackDistance = 2.0f;
            this.minAttackDistance = 0.0f;
            this.moveSpeed = 0.0f;
            this.categray = Constant.EnemyCategray.Build;
            this.setSize(this.region.getRegionWidth() / 1.5f, this.region.getRegionHeight() / 1.5f);
        }
        else if (this.type == Constant.EnemyType.Mine3) {
            this.region = Assets.atlasArcher.findRegion("dilei-3");
            this.maxHp = 50.0f;
            this.attackDamage = 50.0f;
            this.attackInterval = 1.0f;
            this.maxAttackDistance = 2.0f;
            this.minAttackDistance = 0.0f;
            this.moveSpeed = 0.0f;
            this.categray = Constant.EnemyCategray.Build;
            this.setSize(this.region.getRegionWidth() / 1.5f, this.region.getRegionHeight() / 1.5f);
        }
        else if (this.type != Constant.EnemyType.Flag) {
            if (this.type == Constant.EnemyType.Box1) {
                this.region = Assets.atlasArcher.findRegion("box2");
                this.categray = Constant.EnemyCategray.Build;
                this.moveSpeed = 0.0f;
                this.setSize(this.region.getRegionWidth(), this.region.getRegionHeight());
            }
            else if (this.type == Constant.EnemyType.Box2) {
                this.region = Assets.atlasArcher.findRegion("box2");
                this.categray = Constant.EnemyCategray.Build;
                this.moveSpeed = 0.0f;
                this.setSize(this.region.getRegionWidth(), this.region.getRegionHeight());
            }
            else if (this.type == Constant.EnemyType.Box3) {
                this.region = Assets.atlasArcher.findRegion("box3");
                this.categray = Constant.EnemyCategray.Build;
                this.moveSpeed = 0.0f;
                this.setSize(this.region.getRegionWidth(), this.region.getRegionHeight());
            }
        }
        this.setJsonValue(this.type.toString());
        final float[] array2;
        final float[] array = array2 = new float[8];
        array2[1] = (array2[0] = 0.0f);
        array2[2] = 50.0f;
        array2[3] = 0.0f;
        array2[4] = 50.0f;
        array2[5] = 40.0f;
        array2[6] = 0.0f;
        array2[7] = 40.0f;
        switch (this.type) {
            default: {}
            case Mine1: {
                this.polygon = new Polygon(new float[] { 29.0f, 37.0f, 18.0f, 18.0f, 0.0f, 5.0f, 71.0f, 6.0f, 44.0f, 34.0f });
            }
            case Mine2: {
                this.polygon = new Polygon(new float[] { 27.0f, 33.0f, 14.0f, 26.0f, 2.0f, 4.0f, 69.0f, 4.0f, 48.0f, 37.0f });
            }
            case Mine3: {
                this.polygon = new Polygon(new float[] { 30.0f, 43.0f, 28.0f, 34.0f, 16.0f, 30.0f, 4.0f, 7.0f, 68.0f, 11.0f, 54.0f, 43.0f });
            }
            case Spikeweed1: {
                this.polygon = new Polygon(new float[] { 8.0f, 23.0f, 1.0f, 2.0f, 63.0f, 3.0f, 56.0f, 25.0f, 52.0f, 14.0f, 39.0f, 14.0f, 33.0f, 28.0f, 27.0f, 15.0f, 15.0f, 14.0f });
            }
            case Spikeweed2: {
                this.polygon = new Polygon(new float[] { 9.0f, 23.0f, 4.0f, 3.0f, 68.0f, 3.0f, 59.0f, 25.0f, 44.0f, 14.0f, 34.0f, 28.0f, 26.0f, 14.0f });
            }
            case Spikeweed3: {
                this.polygon = new Polygon(new float[] { 11.0f, 27.0f, 3.0f, 4.0f, 74.0f, 4.0f, 62.0f, 30.0f, 49.0f, 14.0f, 36.0f, 32.0f, 26.0f, 15.0f });
            }
            case Box1: {
                this.polygon = new Polygon(array);
            }
            case Box2: {
                this.polygon = new Polygon(array);
            }
            case Box3: {
                this.polygon = new Polygon(new float[] { 0.0f, 0.0f, 60.0f, 0.0f, 60.0f, 48.0f, 0.0f, 48.0f });
            }
        }
    }
}
