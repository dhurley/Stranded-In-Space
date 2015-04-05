package net.djhurley.strandedinspace;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import net.djhurley.strandedinspace.common.ActionResolver;
import net.djhurley.strandedinspace.common.Assets;
import net.djhurley.strandedinspace.screens.SplashScreen;

/**
 * Created by DJHURLEY on 25/01/2015.
 */
public class StrandedInSpace extends Game {
    private ActionResolver actionResolver;

    public StrandedInSpace(ActionResolver actionResolver) {
        this.actionResolver = actionResolver;
    }

    @Override
    public void create() {
        Gdx.app.setLogLevel(Application.LOG_DEBUG);
        Assets.instance.init(new AssetManager());
        setScreen(new SplashScreen(this, true));
    }

    public ActionResolver getActionResolver(){
        return actionResolver;
    }
}
