package net.djhurley.strandedinspace.game;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.utils.Disposable;
import net.djhurley.strandedinspace.game.objects.Spaceship;

/**
 * Created by DJHURLEY on 24/01/2015.
 */
public class WorldController  implements InputProcessor, Disposable {
    private static final String TAG = WorldController.class.getName();

    private Spaceship spaceship;

    public WorldController(Spaceship spaceship){
        this.spaceship = spaceship;
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        spaceship.startBoost();
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        spaceship.stopBoost();
        return true;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }

    @Override
    public void dispose() {
        spaceship.dispose();
    }
}
