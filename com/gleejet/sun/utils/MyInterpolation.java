package com.gleejet.sun.utils;

import com.badlogic.gdx.math.*;

public class MyInterpolation
{
    public static final Interpolation shake1;
    public static final Interpolation shake2;
    
    static {
        shake1 = new Shake(0.2f, 0.15f);
        shake2 = new Shake(0.2f, 0.06f);
    }
    
    public static class Shake extends Interpolation
    {
        float limitRate;
        float valueRate;
        
        public Shake(final float limitRate, final float valueRate) {
            this.limitRate = limitRate;
            this.valueRate = valueRate;
        }
        
        @Override
        public float apply(final float n) {
            if (n <= this.limitRate) {
                return Interpolation.pow2In.apply(n / this.limitRate);
            }
            return MathUtils.sinDeg((n - this.limitRate) * 360.0f * 4.0f) * this.valueRate * (1.0f - n) + 1.0f;
        }
    }
}
