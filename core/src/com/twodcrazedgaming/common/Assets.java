package com.twodcrazedgaming.common;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetErrorListener;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Disposable;

/**
 * Created by DJHURLEY on 20/01/2015.
 */
public class Assets implements Disposable, AssetErrorListener {

    private static final String TAG = Assets.class.getName();
    private AssetManager assetManager;

    public static final Assets instance = new Assets();

    private Assets() {}

    public void init(AssetManager assetManager) {
        this.assetManager = assetManager;
        assetManager.setErrorListener(this);
        assetManager.load(Constants.SPACESHIP_PNG, Texture.class);
        assetManager.load(Constants.SPACESHIP_WITH_BOOST_PNG, Texture.class);
        assetManager.load(Constants.FUEL_BARS_PNG, Texture.class);
        assetManager.load(Constants.SPACE_BACKGROUND_PNG, Texture.class);
        assetManager.load(Constants.SPACE_OGG, Music.class);
        assetManager.load(Constants.BOOST_OGG, Sound.class);
        assetManager.finishLoading();

        Gdx.app.debug(TAG, "# of assets loaded: " + assetManager.getAssetNames().size);
        for (String assetName : assetManager.getAssetNames()) {
            Gdx.app.debug(TAG, "asset: " + assetName);
        }
    }

    @Override
    public void dispose() {
        assetManager.dispose();
    }

    @Override
    public void error(AssetDescriptor asset, Throwable throwable) {
        Gdx.app.error(TAG, "cannot load: " + asset.fileName, (Exception)throwable);
    }

    public Texture getSpaceshipTexture(){
        return assetManager.get(Constants.SPACESHIP_PNG);
    }

    public Texture getSpaceShipWithBoostTexture(){
        return assetManager.get(Constants.SPACESHIP_WITH_BOOST_PNG);
    }

    public TextureRegion getFuelBarTexture(int level) {
        Texture sheet = assetManager.get(Constants.FUEL_BARS_PNG);
        TextureRegion[][] tmp = TextureRegion.split(sheet, sheet.getWidth() / 3, sheet.getHeight() / 4);
        TextureRegion fuelBar = null;
        switch (level){
            case 0:  fuelBar = tmp[0][0];
                     break;
            case 10:  fuelBar = tmp[0][1];
                      break;
            case 20:  fuelBar = tmp[0][2];
                      break;
            case 30:  fuelBar = tmp[1][0];
                      break;
            case 40:  fuelBar = tmp[1][1];
                      break;
            case 50:  fuelBar = tmp[1][1];
                      break;
            case 60:  fuelBar = tmp[2][0];
                      break;
            case 70:  fuelBar = tmp[2][1];
                      break;
            case 80:  fuelBar = tmp[2][2];
                      break;
            case 90:  fuelBar = tmp[3][0];
                      break;
            case 100:  fuelBar = tmp[3][1];
                       break;
        }

        return fuelBar;
    }

    public Texture getSpaceBackgroundTexture(){
        return assetManager.get(Constants.SPACE_BACKGROUND_PNG);
    }

    public Sound getBoostSound(){
        return assetManager.get(Constants.BOOST_OGG, Sound.class);
    }

    public Music getSpaceSound() {
        return assetManager.get(Constants.SPACE_OGG, Music.class);
    }
}
