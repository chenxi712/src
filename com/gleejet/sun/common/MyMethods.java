package com.gleejet.sun.common;

import com.badlogic.gdx.scenes.scene2d.actions.*;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.utils.*;
import com.badlogic.gdx.math.*;
import com.badlogic.gdx.graphics.glutils.*;

public class MyMethods
{
    public static ICompare<Actor> cmpActor;
    
    static {
        MyMethods.cmpActor = (ICompare<Actor>)new ICompare<Actor>() {
            public <T extends Actor> int compare(final T t, final T t2) {
                final float n = t.getX() - t2.getX();
                if (n > 0.0f) {
                    return 1;
                }
                if (n < 0.0f) {
                    return -1;
                }
                return 0;
            }
        };
    }
    
    public static void centerInStage(final Actor actor, final Stage stage) {
        actor.setPosition((stage.getWidth() - actor.getWidth()) / 2.0f, (stage.getHeight() - actor.getHeight()) / 2.0f);
    }
    
    public static void centerInX(final Actor actor, final float n) {
        actor.setX((n - actor.getWidth()) / 2.0f);
    }
    
    public static void centerInY(final Actor actor, final float n) {
        actor.setY((n - actor.getHeight()) / 2.0f);
    }
    
    public static <T> void clearPool(final Class<T> clazz) {
        Pools.get(clazz).clear();
    }
    
    public static float[] copyArray(final float[] array) {
        final float[] array2 = new float[array.length];
        for (int i = 0; i < array2.length; ++i) {
            array2[i] = array[i];
        }
        return array2;
    }
    
    public static void delayRun(final Actor actor, final Runnable runnable, final float n) {
        actor.addAction(Actions.sequence(Actions.delay(n), Actions.run(runnable)));
    }
    
    public static <T> int findIndex(final T[] array, final T t, final boolean b) {
        for (int i = 0; i < array.length; ++i) {
            int equals;
            if (b) {
                if (array[i] == t) {
                    equals = 1;
                }
                else {
                    equals = 0;
                }
            }
            else {
                equals = (array[i].equals(t) ? 1 : 0);
            }
            if (equals != 0) {
                return i;
            }
        }
        return -1;
    }
    
    public static int findIndex(final String[] array, final String s) {
        for (int i = 0; i < array.length; ++i) {
            if (array[i].equals(s)) {
                return i;
            }
        }
        return -1;
    }
    
    public static void foreverAction(final Actor actor, final Action... array) {
        actor.addAction(Actions.forever(Actions.sequence(array)));
    }
    
    public static float getDis(float n, float n2, final float n3, final float n4) {
        n -= n3;
        n2 -= n4;
        return (float)Math.sqrt(n * n + n2 * n2);
    }
    
    public static float getDis(Vector2 paramVector21, Vector2 paramVector22)
    {
    	float f1 = paramVector21.x - paramVector22.x;
    	float f2 = paramVector21.y - paramVector22.y;
    	return (float)Math.sqrt(f1 * f1 + f2 * f2);
    }
    
    public static float getRight(final Sprite sprite) {
        return sprite.getX() + sprite.getWidth();
    }
    
    public static <T> void initPoolNum(final Class<T> clazz, final int n) {
        final Pool<T> value = Pools.get(clazz);
        value.clear();
        if (n > 0) {
            final Array<T> array = new Array<T>();
            for (int i = 0; i < n; ++i) {
                array.add(value.obtain());
            }
            value.freeAll(array);
        }
    }
    
    public static boolean isPolyPolyOverlap(final Polygon polygon, final Polygon polygon2, final Vector2 vector2) {
        final float[] vertices = polygon.getVertices();
        final float x = polygon.getX();
        final float y = polygon.getY();
        final float[] vertices2 = polygon2.getVertices();
        final float x2 = polygon2.getX();
        final float y2 = polygon2.getY();
        float n = vertices[0];
        float n2 = vertices[1];
        float n3 = n;
        float n4 = n2;
        float n5;
        float n6;
        float n7;
        float n8;
        for (int i = 1; i < vertices.length / 2; ++i, n3 = n6, n4 = n8, n = n5, n2 = n7) {
            if (vertices[i * 2] < n) {
                n5 = vertices[i * 2];
                n6 = n3;
            }
            else {
                n6 = n3;
                n5 = n;
                if (vertices[i * 2] > n3) {
                    n6 = vertices[i * 2];
                    n5 = n;
                }
            }
            if (vertices[i * 2 + 1] < n2) {
                n7 = vertices[i * 2 + 1];
                n8 = n4;
            }
            else {
                n8 = n4;
                n7 = n2;
                if (vertices[i * 2 + 1] > n4) {
                    n8 = vertices[i * 2 + 1];
                    n7 = n2;
                }
            }
        }
        final float n9 = n3 + x;
        final float n10 = n4 + y;
        float n11 = vertices2[0];
        float n12 = vertices2[1];
        float n13 = n11;
        float n14 = n12;
        float n15;
        float n16;
        float n17;
        float n18;
        for (int j = 1; j < vertices2.length / 2; ++j, n13 = n16, n14 = n18, n11 = n15, n12 = n17) {
            if (vertices2[j * 2] < n11) {
                n15 = vertices2[j * 2];
                n16 = n13;
            }
            else {
                n16 = n13;
                n15 = n11;
                if (vertices2[j * 2] > n13) {
                    n16 = vertices2[j * 2];
                    n15 = n11;
                }
            }
            if (vertices2[j * 2 + 1] < n12) {
                n17 = vertices2[j * 2 + 1];
                n18 = n14;
            }
            else {
                n18 = n14;
                n17 = n12;
                if (vertices2[j * 2 + 1] > n14) {
                    n18 = vertices2[j * 2 + 1];
                    n17 = n12;
                }
            }
        }
        if (n + x >= n9 || n11 + x2 >= n9 || n2 + y >= n10 || n12 + y2 >= n10) {
            return false;
        }
        for (int k = 0; k < vertices.length / 2; ++k) {
            if (polygon2.contains(vertices[k * 2] + x, vertices[k * 2 + 1] + y)) {
                vector2.set(vertices[k * 2] + x, vertices[k * 2 + 1] + y);
                return true;
            }
        }
        for (int l = 0; l < vertices2.length / 2; ++l) {
            if (polygon.contains(vertices2[l * 2] + x2, vertices2[l * 2 + 1] + y2)) {
                vector2.set(vertices2[l * 2] + x2, vertices2[l * 2 + 1] + y2);
                return true;
            }
        }
        return false;
    }
    
    public static boolean isRectRectOverlap(final Rectangle rectangle, final Rectangle rectangle2) {
        final float x = rectangle.x;
        final float y = rectangle.y;
        final float width = rectangle.width;
        final float height = rectangle.height;
        final float x2 = rectangle2.x;
        final float y2 = rectangle2.y;
        final float width2 = rectangle2.width;
        final float height2 = rectangle2.height;
        return x < x2 + width2 && x2 < x + width && y < y2 + height2 && y2 < y + height;
    }
    
    public static boolean isSegPolyOverlap(final Vector2 v1, final Vector2 v2, final Polygon polygon, final Vector2 vector3) {
        final Vector2 vector4 = new Vector2();
        final Vector2 vector5 = new Vector2();
        final Vector2 vector6 = new Vector2();
        final float x = polygon.getX();
        final float y = polygon.getY();
        boolean b = false;
        final float[] vertices = polygon.getVertices();
        boolean b2;
        for (int i = 0; i < vertices.length / 2; ++i, b = b2) {
            vector4.set(vertices[i * 2] + x, vertices[i * 2 + 1] + y);
            vector5.set(vertices[(i * 2 + 2) % vertices.length] + x, vertices[(i * 2 + 3) % vertices.length] + y);
            b2 = b;
            if (Intersector.intersectSegments(v1, v2, vector4, vector5, vector6)) {
                if (!b) {
                    b2 = true;
                    vector3.set(vector6);
                }
                else {
                    b2 = b;
                    if (vector6.x < vector3.x) {
                        vector3.set(vector6);
                        b2 = b;
                    }
                }
            }
        }
        return b;
    }
    
    public static boolean isSegRectOverlap(final Vector2 v1, final Vector2 v2, final Rectangle rectangle, final Vector2 vector3) {
        final float x = rectangle.x;
        final float y = rectangle.y;
        final float width = rectangle.width;
        final float height = rectangle.height;
        final Vector2 vector4 = new Vector2();
        final Vector2 vector5 = new Vector2();
        final Vector2 vector6 = new Vector2();
        boolean b = false;
        final float[] array = { x, y, x + width, y, x + width, y + height, x, y + height };
        boolean b2;
        for (int i = 0; i < array.length / 2; ++i, b = b2) {
            vector4.set(array[i * 2], array[i * 2 + 1]);
            vector5.set(array[(i * 2 + 2) % array.length], array[(i * 2 + 3) % array.length]);
            b2 = b;
            if (Intersector.intersectSegments(v1, v2, vector4, vector5, vector6)) {
                if (!b) {
                    b2 = true;
                    vector3.set(vector6);
                }
                else {
                    b2 = b;
                    if (vector6.x < vector3.x) {
                        vector3.set(vector6);
                        b2 = b;
                    }
                }
            }
        }
        return b;
    }
    
    public static void sequenceAction(final Actor actor, final Action... array) {
        actor.addAction(Actions.sequence(array));
    }
    
    public static void setActorOrigin(final Actor actor, final float n, final float n2) {
        actor.setOrigin(actor.getWidth() * n, actor.getHeight() * n2);
    }
    
    public static void setCenter(final Actor actor, final float n, final float n2) {
        actor.setPosition(n - actor.getWidth() / 2.0f, n2 - actor.getHeight() / 2.0f);
    }
    
    public static void showEdge(final ShapeRenderer shapeRenderer, final Rectangle rectangle) {
        shapeRenderer.rect(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
    }
    
    public static void showEdge(final ShapeRenderer shapeRenderer, final Actor actor) {
        shapeRenderer.rect(actor.getX(), actor.getY(), actor.getWidth(), actor.getHeight());
    }
    
    public static void showPolygon(final ShapeRenderer shapeRenderer, final Polygon polygon) {
        final float[] vertices = polygon.getVertices();
        if (vertices.length >= 6) {
            final float x = polygon.getX();
            final float y = polygon.getY();
            for (int i = 0; i < vertices.length / 2; ++i) {
                shapeRenderer.line(vertices[i * 2] + x, vertices[i * 2 + 1] + y, vertices[(i * 2 + 2) % vertices.length] + x, vertices[(i * 2 + 3) % vertices.length] + y);
            }
        }
    }
    
    public static <T2 extends Actor, T1 extends T2> void sort(final Array<T1> array, final ICompare<T2> compare) {
        for (int i = 0; i < array.size - 1; ++i) {
            int n = i;
            for (int j = i + 1; j < array.size; ++j) {
                if (compare.compare(array.get(i), array.get(j)) > 0) {
                    n = j;
                }
            }
            if (n != i) {
                array.swap(n, i);
            }
        }
    }
    
    public static void sortArray(final Array<? extends Actor> array) {
        final boolean b = true;
        int n = 0;
        boolean b2;
        while (true) {
            b2 = b;
            if (n >= array.size - 1) {
                break;
            }
            if (((Actor)array.get(n + 1)).getX() < ((Actor)array.get(n)).getX()) {
                b2 = false;
                break;
            }
            ++n;
        }
        if (!b2) {
            for (int i = 0; i < array.size - 1; ++i) {
                int n2 = i;
                for (int j = i + 1; j < array.size; ++j) {
                    if (((Actor)array.get(j)).getX() < ((Actor)array.get(n2)).getX()) {
                        n2 = j;
                    }
                }
                if (n2 != i) {
                    array.swap(n2, i);
                }
            }
        }
    }
    
    public interface ICompare<T0>
    {
         <T extends T0> int compare(T p0, T p1);
    }
}
