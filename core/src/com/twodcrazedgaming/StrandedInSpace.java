package com.twodcrazedgaming;

import com.badlogic.gdx.Game;
import com.twodcrazedgaming.screens.GameScreen;

/**
 * Created by DJHURLEY on 25/01/2015.
 */
public class StrandedInSpace extends Game {

    @Override
    public void create() {
        setScreen(new GameScreen());
    }
}
