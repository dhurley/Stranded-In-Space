package net.djhurley.strandedinspace.screens;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;

import net.djhurley.strandedinspace.StrandedInSpace;
import net.djhurley.strandedinspace.common.Assets;
import net.djhurley.strandedinspace.common.Constants;
import net.djhurley.strandedinspace.game.WorldRenderer;

/**
 * Created by DJHURLEY on 25/01/2015.
 */
public class GameScreen implements Screen {
    private static final String TAG = GameScreen.class.getName();
    private final StrandedInSpace game;

    private WorldRenderer worldRenderer;

    private boolean isSoundOn;
    private boolean paused;

    public GameScreen(StrandedInSpace game, boolean isSoundOn){
        this.game = game;
        this.isSoundOn = isSoundOn;
    }

    @Override
    public void show() {
        worldRenderer = new WorldRenderer(isSoundOn);
        paused = false;
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        worldRenderer.render();
        if(worldRenderer.isGameOver()){
            if(game.getActionResolver() != null) {
                if (game.getActionResolver().getSignedInGPGS()) {
                    int score = worldRenderer.getScore();
                    game.getActionResolver().submitScoreGPGS(score);

                    if (score >= 10)
                        game.getActionResolver().unlockAchievementGPGS(Constants.ACHIEVEMENT_10_ID);
                    if (score >= 50)
                        game.getActionResolver().unlockAchievementGPGS(Constants.ACHIEVEMENT_50_ID);
                    if (score >= 100)
                        game.getActionResolver().unlockAchievementGPGS(Constants.ACHIEVEMENT_100_ID);
                    if (score >= 200)
                        game.getActionResolver().unlockAchievementGPGS(Constants.ACHIEVEMENT_200_ID);
                    if (score >= 500)
                        game.getActionResolver().unlockAchievementGPGS(Constants.ACHIEVEMENT_500_ID);
                }
            }

            dispose();
            game.setScreen(new GameOverScreen(game, isSoundOn, worldRenderer.getScore()));
        }
    }

    @Override
    public void resize(int width, int height) {
        worldRenderer.resize(width, height);
    }

    @Override
    public void pause() {
        paused = true;
    }

    @Override
    public void resume() {
        Assets.instance.init(new AssetManager());
        paused = false;
    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        worldRenderer.dispose();
    }

}
