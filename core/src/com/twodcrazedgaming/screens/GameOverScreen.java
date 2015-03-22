package com.twodcrazedgaming.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;
import com.twodcrazedgaming.common.Assets;

/**
 * Created by DJHURLEY on 21/02/2015.
 */
public class GameOverScreen implements Screen {
    private static final String TAG = GameOverScreen.class.getName();
    private final Game game;

    private SpriteBatch batch;
    private Sprite gameOverSprite;
    private BitmapFont font;

    private Sprite homeSprite;
    private Sprite replaySprite;
    private Sprite rateSprite;
    private Sprite leaderboardSprite;

    private int screenWidth;
    private int screenHeight;
    private boolean isSoundOn;
    private long score = 0;

    public GameOverScreen(Game game, boolean isSoundOn, long score) {
        this.game = game;
        this.isSoundOn = isSoundOn;
        this.score = score;
    }

    @Override
    public void show() {
        batch = new SpriteBatch();

        screenWidth = Gdx.graphics.getWidth();
        screenHeight = Gdx.graphics.getHeight();

        initializeGameOverSprite();
        font = Assets.instance.getDroidSansFont();

        int iconWidth = screenWidth / 5;
        int iconHeight = screenWidth / 5;

        homeSprite = new Sprite(Assets.instance.getHomeTexture());
        homeSprite.setSize(iconWidth, iconHeight);
        homeSprite.setPosition(screenWidth * 0.04f, iconHeight);

        replaySprite = new Sprite(Assets.instance.getReplayTexture());
        replaySprite.setSize(iconWidth, iconHeight);
        replaySprite.setPosition(screenWidth * 0.04f * 2 + iconWidth, iconHeight);

        rateSprite = new Sprite(Assets.instance.getRateTexture());
        rateSprite.setSize(iconWidth, iconHeight);
        rateSprite.setPosition(screenWidth * 0.04f * 3 + iconWidth * 2, iconHeight);

        leaderboardSprite = new Sprite(Assets.instance.getLeaderboardTexture());
        leaderboardSprite.setSize(iconWidth, iconHeight);
        leaderboardSprite.setPosition(screenWidth * 0.04f * 4 + iconWidth * 3, iconHeight);

        Gdx.input.setInputProcessor(new GestureDetector(new GameOverGestureListener()));
    }

    private void initializeGameOverSprite() {
        gameOverSprite = new Sprite(Assets.instance.getGameOverTexture());
        gameOverSprite.setPosition(0, 3 * screenHeight / 4);
        gameOverSprite.setSize(screenWidth, screenHeight / 4);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        gameOverSprite.draw(batch);
        font.draw(batch, "You are stranded in space forever.", (screenWidth / 5), 2 * screenHeight / 3);
        font.draw(batch, "Score: " + score, (3 * screenWidth / 7), 3 * screenHeight / 5);
        homeSprite.draw(batch);
        replaySprite.draw(batch);
        rateSprite.draw(batch);
        leaderboardSprite.draw(batch);

        batch.end();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        batch.dispose();
    }

    private class GameOverGestureListener implements GestureDetector.GestureListener {

        @Override
        public boolean touchDown(float x, float y, int pointer, int button) {
            if (isButtonPressed(homeSprite, x, y)) {
                game.setScreen(new MainMenuScreen(game, isSoundOn));
                return true;
            } else if (isButtonPressed(replaySprite, x, y)) {
                game.setScreen(new GameScreen(game, isSoundOn));
                return true;
            } else if (isButtonPressed(rateSprite, x, y)) {

                return true;
            } else if (isButtonPressed(leaderboardSprite, x, y)) {

                return true;
            }

            return false;
        }

        @Override
        public boolean tap(float x, float y, int count, int button) {
            return false;
        }

        @Override
        public boolean longPress(float x, float y) {
            return false;
        }

        @Override
        public boolean fling(float velocityX, float velocityY, int button) {
            return false;
        }

        @Override
        public boolean pan(float x, float y, float deltaX, float deltaY) {
            return false;
        }

        @Override
        public boolean panStop(float x, float y, int pointer, int button) {
            return false;
        }

        @Override
        public boolean zoom(float initialDistance, float distance) {
            return false;
        }

        @Override
        public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2) {
            return false;
        }

        private boolean isButtonPressed(Sprite sprite, float x, float y) {
            return x >= sprite.getX() &&
                    x <= sprite.getX() + sprite.getWidth() &&
                    y <= screenHeight - sprite.getY() &&
                    y >= screenHeight - (sprite.getY() + sprite.getHeight());
        }
    }
}
