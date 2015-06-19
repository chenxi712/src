package com.fxb.razor.utils.loader;

import com.badlogic.gdx.assets.loaders.*;
import com.badlogic.gdx.files.*;
import com.badlogic.gdx.utils.*;
import com.badlogic.gdx.assets.*;
import com.fxb.razor.common.*;

public class EnemyLoader extends AsynchronousAssetLoader<ArrayEnemy, EnemyParameter>
{
    private ArrayEnemy arrayEnemy;
    
    public EnemyLoader(final FileHandleResolver fileHandleResolver) {
        super(fileHandleResolver);
    }
    
    @Override
    public Array<AssetDescriptor> getDependencies(final String s, final FileHandle fileHandle, final com.fxb.razor.utils.loader.EnemyParameter enemyParameter) {
        return null;
    }
    
    @Override
    public void loadAsync(final AssetManager assetManager, final String s, final FileHandle fileHandle, final com.fxb.razor.utils.loader.EnemyParameter enemyParameter) {
        this.arrayEnemy = new ArrayEnemy();
        Control.levelClear();
    }
    
    @Override
    public ArrayEnemy loadSync(final AssetManager assetManager, final String s, final FileHandle fileHandle, final com.fxb.razor.utils.loader.EnemyParameter enemyParameter) {
        return this.arrayEnemy;
    }
}
