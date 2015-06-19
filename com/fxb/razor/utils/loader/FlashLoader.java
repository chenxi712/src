package com.fxb.razor.utils.loader;

import com.badlogic.gdx.assets.loaders.*;
import com.fxb.razor.flash.*;
import com.badlogic.gdx.files.*;
import com.badlogic.gdx.utils.*;
import com.badlogic.gdx.assets.*;
import com.badlogic.gdx.*;

public class FlashLoader extends AsynchronousAssetLoader<FlashElements, com.fxb.razor.utils.loader.FlashParameter>
{
    private static String name;
    private static XmlReader xmlReader;
    private FlashElements elements;
    
    public FlashLoader(final FileHandleResolver fileHandleResolver) {
        super(fileHandleResolver);
        FlashLoader.xmlReader = new XmlReader();
    }
    
    private static Element elementParse(final XmlReader.Element element) {
        final Vertex4 vertex4 = new Vertex4();
        final String string = FlashLoader.name.substring(0, FlashLoader.name.indexOf("_")) + FlashLoader.name.substring(FlashLoader.name.lastIndexOf("_"), FlashLoader.name.length()) + "_" + element.getAttribute("textureName");
        vertex4.x0 = element.getFloat("x0", 0.0f);
        vertex4.y0 = element.getFloat("y0", 0.0f);
        vertex4.x1 = element.getFloat("x1", 0.0f);
        vertex4.y1 = element.getFloat("y1", 0.0f);
        vertex4.x2 = element.getFloat("x2", 0.0f);
        vertex4.y2 = element.getFloat("y2", 0.0f);
        vertex4.x3 = vertex4.x0 + (vertex4.x2 - vertex4.x1);
        vertex4.y3 = vertex4.y0 + (vertex4.y2 - vertex4.y1);
        return new Element(string, vertex4, element.getFloat("alphaMultiplier", 1.0f));
    }
    
    private static FlashElements flashParse(final XmlReader.Element element) {
        final FlashElements flashElements = new FlashElements(element.getInt("layerNumber", 1), element.getFloat("width", 0.0f), element.getFloat("height", 0.0f), element.getFloat("endTime", 0.0f), element.getAttribute("flashName"));
        FlashLoader.name = flashElements.name;
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
    
    @Override
    public Array<AssetDescriptor> getDependencies(final String s, final FileHandle fileHandle, final com.fxb.razor.utils.loader.FlashParameter flashParameter) {
        return null;
    }
    
    @Override
    public void loadAsync(final AssetManager assetManager, final String s, final FileHandle fileHandle, final com.fxb.razor.utils.loader.FlashParameter flashParameter) {
        try {
            this.elements = flashParse(FlashLoader.xmlReader.parse(Gdx.files.internal(s)));
        }
        catch (Exception ex) {}
    }
    
    @Override
    public FlashElements loadSync(final AssetManager assetManager, final String s, final FileHandle fileHandle, final com.fxb.razor.utils.loader.FlashParameter flashParameter) {
        return this.elements;
    }
    
    public static class FlashParameter extends AssetLoaderParameters<FlashElements>
    {
    }
}
