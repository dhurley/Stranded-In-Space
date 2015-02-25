package com.twodcrazedgaming;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.twodcrazedgaming.common.Assets;
import com.twodcrazedgaming.screens.GameScreen;
import com.twodcrazedgaming.screens.MainMenuScreen;

/**
 * Created by DJHURLEY on 25/01/2015.
 */
public class StrandedInSpace extends Game {

    @Override
    public void create() {
        Gdx.app.setLogLevel(Application.LOG_DEBUG);
        Assets.instance.init(new AssetManager());
        setScreen(new MainMenuScreen(this));
    }
}
