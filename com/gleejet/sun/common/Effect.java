package com.gleejet.sun.common;

import com.badlogic.gdx.utils.*;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.gleejet.sun.objects.maingun.*;
import com.gleejet.sun.utils.ui.*;

public class Effect
{
    static Array<TextureAtlas.AtlasRegion> arrRegionClick;
    
    static {
        Effect.arrRegionClick = null;
    }
    
    public static void addAcidEffect(final float n, final float n2) {
        if (n < 100.0f) {
            return;
        }
        final EffectAcid effectAcid = Pools.obtain(EffectAcid.class);
        effectAcid.setPosition(n - effectAcid.getOriginX(), n2 - effectAcid.getOriginY());
        effectAcid.setStart();
        Global.groupEffectPlayer.addActor(effectAcid);
    }
    
    public static void addExplosion(final float n, final float n2, final float scale) {
        if (n < 100.0f) {
            return;
        }
        final EffectExplosion effectExplosion = Pools.obtain(EffectExplosion.class);
        effectExplosion.setScale(scale);
        effectExplosion.setPosition(n - effectExplosion.getOriginX(), n2 - effectExplosion.getOriginY());
        effectExplosion.setStart();
        Global.groupEffectPlayer.addActor(effectExplosion);
    }
    
    public static void addFlameEffect(final float n, final float n2) {
        if (n < 100.0f) {
            return;
        }
        final EffectFlame effectFlame = Pools.obtain(EffectFlame.class);
        effectFlame.setPosition(n - effectFlame.getOriginX(), n2 - effectFlame.getOriginY());
        effectFlame.setStart();
        Global.groupEffectPlayer.addActor(effectFlame);
    }
    
    public static void addPipeClick(final float n, final float n2, final float rotation) {
        final EffectPipeClick effectPipeClick = Pools.obtain(EffectPipeClick.class);
        effectPipeClick.setRotation(rotation);
        effectPipeClick.setPosition(n - effectPipeClick.getOriginX(), n2 - effectPipeClick.getOriginY());
        effectPipeClick.setStart();
        Global.groupEffectPlayer.addActor(effectPipeClick);
    }
    
    public static void addSmoke(final float n, final float n2) {
        addSmoke(n, n2, 0.65f);
    }
    
    public static void addSmoke(final float n, final float n2, final float scale) {
        if (n < 100.0f) {
            return;
        }
        final EffectSmoke effectSmoke = Pools.obtain(EffectSmoke.class);
        effectSmoke.setScale(scale);
        effectSmoke.setPosition(n - effectSmoke.getOriginX(), n2 - effectSmoke.getOriginY());
        effectSmoke.setStart();
        Global.groupEffectSmoke.addActor(effectSmoke);
    }
    
    public static void setArrRegion(final Array<TextureAtlas.AtlasRegion> arrRegionClick) {
        Effect.arrRegionClick = arrRegionClick;
    }
    
    public static class EffectAcid extends AnimationActor
    {
        public EffectAcid() {
            super(0.05f, Acid.getArrRegionBreak());
            this.setOrigin(100.0f, 45.0f);
            this.setScale(0.5f);
            this.isAutoFree = true;
        }
    }
    
    public static class EffectExplosion extends AnimationActor
    {
        public EffectExplosion() {
            super(0.06666667f, Assets.atlasBreak.getRegions());
            this.setOrigin(145.0f, 6.0f);
            this.setScale(1.0f);
            this.isAutoFree = true;
        }
    }
    
    public static class EffectFlame extends AnimationActor
    {
        public EffectFlame() {
            super(0.05f, Flame.getArrRegionBreak());
            this.setOrigin(125.0f, 7.5f);
            this.setScale(0.4f);
            this.isAutoFree = true;
        }
    }
    
    public static class EffectPipeClick extends AnimationActor
    {
        public EffectPipeClick() {
            super(0.04f, Effect.arrRegionClick);
            this.setOrigin(97.0f, 45.0f);
            this.setScale(0.5f);
            this.isAutoFree = true;
        }
    }
    
    public static class EffectSmoke extends AnimationActor
    {
        public EffectSmoke() {
            super(0.05f, Assets.atlasSmoke.getRegions());
            this.setOrigin(125.0f, 34.0f);
            this.setScale(1.0f);
            this.isAutoFree = true;
        }
    }
}
