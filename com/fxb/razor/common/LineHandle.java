package com.fxb.razor.common;

import com.badlogic.gdx.math.*;

public class LineHandle
{
    public static Vector2[] bezierPoints;
    public static Vector2[] originPoints;
    public static final float[] rlines;
    
    static {
        rlines = new float[] { 1.06763f, 49.1281f, 71.1004f, 50.8996f, 141.133f, 56.0193f, 231.175f, 66.5787f, 411.26f, 93.3096f, 491.297f, 103.566f, 581.339f, 111.241f, 661.377f, 113.548f, 742.047f, 111.415f, 822.716f, 105.28f, 913.47f, 94.5585f, 1094.98f, 67.9325f, 1175.65f, 57.3518f, 1266.4f, 48.7314f, 1347.07f, 45.0385f, 1448.17f, 46.0275f, 1549.37f, 52.1222f, 1660.7f, 63.522f, 1883.35f, 92.3089f, 1974.44f, 102.497f, 2075.64f, 110.388f, 2176.85f, 113.522f, 2287.28f, 112.172f, 2407.65f, 107.091f, 2538.06f, 98.1648f, 2818.93f, 73.9923f, 2939.3f, 65.0624f, 3069.71f, 58.496f, 3200.11f, 56.1828f };
    }
    
    public static void dispose() {
    }
    
    public static void init() {
    }
}
