package com.gleejet.sun.utils;

import com.badlogic.gdx.math.*;
import com.badlogic.gdx.*;

public class MeshMethod
{
    public static void addMeshVertices(final float[] array, final float n, final float n2) {
        for (int i = 0; i < array.length / 4; ++i) {
            final int n3 = i * 4;
            array[n3] += n;
            final int n4 = i * 4 + 1;
            array[n4] += n2;
        }
    }
    
    public static void addVectors(final Vector2[] array, final float n, final float n2) {
        for (int length = array.length, i = 0; i < length; ++i) {
            array[i].add(n, n2);
        }
    }
    
    public static void addVertices(final float[] array, final float n, final float n2) {
        for (int i = 0; i < array.length / 2; ++i) {
            final int n3 = i * 2;
            array[n3] += n;
            final int n4 = i * 2 + 1;
            array[n4] += n2;
        }
    }
    
    public static short[] createIndices(final int n) {
        final short[] array = new short[n];
        for (short n2 = 0; n2 < array.length; ++n2) {
            array[n2] = n2;
        }
        return array;
    }
    
    public static Vector2[] floatsToVectors(final float[] array) {
        final Vector2[] array2 = new Vector2[array.length / 2];
        for (int i = 0; i < array2.length; ++i) {
            array2[i] = new Vector2(array[i * 2], array[i * 2 + 1]);
        }
        return array2;
    }
    
    public static Vector2[] getBezierPoints(final Vector2[] array, final int n) {
        final Vector2[] array2 = new Vector2[array.length * 2 - 1];
        for (int i = 0; i < array2.length; ++i) {
            array2[i] = new Vector2();
        }
        for (int j = 0; j < array.length - 1; ++j) {
            array2[j * 2].set(array[j]);
            getMid(array[j], array[j + 1], array2[j * 2 + 1]);
        }
        array2[array2.length - 1] = array[array.length - 1];
        final int n2 = (array.length - 2) * n + 2;
        final Vector2[] array3 = new Vector2[n2];
        for (int k = 0; k < array3.length; ++k) {
            array3[k] = new Vector2();
        }
        array3[0].set(array[0]);
        int n3 = 0 + 1;
        int n4 = 0 + 1;
        for (int l = 1; l < array2.length - 2; l += 2) {
            getBezierThree(array2, n3, array3, n4, n);
            n3 += 2;
            n4 += n;
        }
        array3[n2 - 1].set(array[array.length - 1]);
        return array3;
    }
    
    public static void getBezierThree(final Vector2[] array, final int n, final Vector2[] array2, final int n2, final int n3) {
        for (int i = 0; i < n3; ++i) {
            getPoint(array[n], array[n + 1], array[n + 2], i / (n3 - 1), array2[i + n2]);
        }
    }
    
    public static void getMid(final Vector2 v1, final Vector2 v2, final Vector2 v3) {
        v3.set((v1.x + v2.x) / 2.0f, (v1.y + v2.y) / 2.0f);
    }
    
    public static void getPoint(final Vector2 v1, final Vector2 v2, final Vector2 v3, final float n, final Vector2 vector4) {
        if (n < 0.0f || n > 1.0f) {
            return;
        }
        vector4.x = (1.0f - n) * (1.0f - n) * v1.x + 2.0f * n * (1.0f - n) * v2.x + n * n * v3.x;
        vector4.y = (1.0f - n) * (1.0f - n) * v1.y + 2.0f * n * (1.0f - n) * v2.y + n * n * v3.y;
    }
    
    public static float[] readData(String string) {
        string = Gdx.files.internal(string).readString();
        final int index = string.indexOf("\r\n");
        final float[] array = new float[Integer.parseInt(string.substring(0, index)) * 2];
        int n = index + 2;
        for (int i = 0; i < array.length; ++i) {
            final int index2 = string.indexOf(" ", n);
            array[i] = Float.parseFloat(string.substring(n, index2));
            n = index2 + 1;
        }
        return array;
    }
    
    public static float[] readVertices(final String s, final float n, final float n2, final float n3, final float n4) {
        final String string = Gdx.files.internal(s).readString();
        final int index = string.indexOf("\r\n");
        final int n5 = Integer.parseInt(string.substring(0, index)) * 2;
        final float[] array = new float[n5];
        int n6 = index + 2;
        for (int i = 0; i < n5; ++i) {
            final int index2 = string.indexOf(" ", n6);
            if (i % 2 == 0) {
                array[i] = Float.parseFloat(string.substring(n6, index2));
            }
            else {
                array[i] = Float.parseFloat(string.substring(n6, index2));
            }
            n6 = index2 + 1;
        }
        final float[] array2 = new float[n5];
        final int n7 = 0;
        int n8 = n6;
        for (int j = n7; j < n5; ++j) {
            final int index3 = string.indexOf(" ", n8);
            if (j % 2 == 0) {
                array2[j] = Float.parseFloat(string.substring(n8, index3));
            }
            else {
                array2[j] = 1.0f - Float.parseFloat(string.substring(n8, index3));
            }
            n8 = index3 + 1;
        }
        final float[] array3 = new float[n5 * 2];
        for (int k = 0; k < n5 / 2; ++k) {
            array3[k * 4] = array[k * 2] + n;
            array3[k * 4 + 1] = array[k * 2 + 1] + n2;
            array3[k * 4 + 2] = array2[k * 2] * n3;
            array3[k * 4 + 3] = array2[k * 2 + 1] * n4;
        }
        return array3;
    }
    
    public static void scaleVertices(final float[] array, final float n) {
        for (int i = 0; i < array.length; ++i) {
            array[i] *= n;
        }
    }
}
