package com.gleejet.sun.flash;

import java.util.*;

import com.badlogic.gdx.utils.*;
import com.badlogic.gdx.*;
import com.gleejet.sun.utils.*;

public class Fanimation2
{
    private static HashMap<String, FlashElements> animationManager;
    private static String name;
    
    static {
        Fanimation2.animationManager = new HashMap<String, FlashElements>();
    }
    
    public static FlashElements creatFanimation(final String s) {
        FlashElements flashElements;
        if ((flashElements = Fanimation2.animationManager.get(s)) == null) {
            final FlashElements flashElements2 = createFlashElements(s);
            if ((flashElements = flashElements2) != null) {
                Fanimation2.animationManager.put(s, flashElements2);
                flashElements = flashElements2;
            }
        }
        return flashElements;
    }
    
    public static void creatFanimation(final String[] array) {
        for (int i = 0; i < array.length; ++i) {
            creatFanimation(array[i]);
        }
    }
    
    private static FlashElements createFlashElements(final String s) {
        try {
            return flashParse(new XmlReader().parse(Gdx.files.internal(s)));
        }
        catch (Exception ex) {
            return null;
        }
    }
    
    private static Element elementParse(final XmlReader.Element element) {
        final Vertex4 vertex4 = new Vertex4();
        final String value = StrHandle.get(Fanimation2.name.substring(0, Fanimation2.name.indexOf("_")), Fanimation2.name.substring(Fanimation2.name.lastIndexOf("_"), Fanimation2.name.length()), '_', element.getAttribute("textureName"));
        vertex4.x0 = element.getFloat("x0", 0.0f);
        vertex4.y0 = element.getFloat("y0", 0.0f);
        vertex4.x1 = element.getFloat("x1", 0.0f);
        vertex4.y1 = element.getFloat("y1", 0.0f);
        vertex4.x2 = element.getFloat("x2", 0.0f);
        vertex4.y2 = element.getFloat("y2", 0.0f);
        vertex4.x3 = vertex4.x0 + (vertex4.x2 - vertex4.x1);
        vertex4.y3 = vertex4.y0 + (vertex4.y2 - vertex4.y1);
        return new Element(value, vertex4, element.getFloat("alphaMultiplier", 1.0f));
    }
    
    private static FlashElements flashParse(final XmlReader.Element element) {
        final FlashElements flashElements = new FlashElements(element.getInt("layerNumber", 1), element.getFloat("width", 0.0f), element.getFloat("height", 0.0f), element.getFloat("endTime", 0.0f), element.getAttribute("flashName"));
        Fanimation2.name = flashElements.name;
        for (int i = 0; i < flashElements.layersNumer; ++i) {
            flashElements.layers[i] = layerParser(element.getChild(i));
        }
        return flashElements;
    }
    
    private static Frame frameParse(final XmlReader.Element element, final float n) {
        final Frame frame = new Frame(n, element.getFloat("endTime", 0.0f), element.getInt("elementsNumber", 1));
        for (int i = 0; i < frame.elementsNumber; ++i) {
            frame.elements[i] = elementParse(element.getChild(i));
        }
        return frame;
    }
    
    public static FlashElements getFanimation(final String s) {
        return creatFanimation(s);
    }
    
    private static Layer layerParser(final XmlReader.Element element) {
        final Layer layer = new Layer(element.getAttribute("layerName"), element.getInt("framesNumber", 1));
        for (int i = 0; i < layer.framesNumber; ++i) {
            if (i == 0) {
                layer.frames[i] = frameParse(element.getChild(i), 0.0f);
            }
            else {
                layer.frames[i] = frameParse(element.getChild(i), layer.frames[i - 1].endTime);
            }
        }
        return layer;
    }
}
