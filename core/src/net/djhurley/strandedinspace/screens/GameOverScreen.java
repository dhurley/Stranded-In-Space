package net.djhurley.strandedinspace.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;
import net.djhurley.strandedinspace.StrandedInSpace;
import net.djhurley.strandedinspace.common.Assets;
import net.djhurley.strandedinspace.common.Constants;
import net.djhurley.strandedinspace.tween.BitmapFontAccessor;
import net.djhurley.strandedinspace.tween.SpriteAccessor;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenManager;

/**
 * Created by DJHURLEY on 21/02/2015.
 */
public class GameOverScreen implements Screen {
    private static final String TAG = GameOverScreen.class.getName();
    private final StrandedInSpace game;

    private Sprite backgroundSprite;
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

    private TweenManager tweenManager;

    public GameOverScreen(StrandedInSpace game, boolean isSoundOn, long score) {
        this.game = game;
        this.isSoundOn = isSoundOn;
        this.score = score;
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        tweenManager = new TweenManager();
        Tween.registerAccessor(Sprite.class, new SpriteAccessor());
        Tween.registerAccessor(BitmapFont.class, new BitmapFontAccessor());

        screenWidth = Constants.getWidth();
        screenHeight = Constants.getHeight();

        initializeGameOverSprite();
        font = Assets.instance.getDroidSansFont();
        font.setColor(1, 1, 1, 1);

        int iconWidth = screenWidth / 5;
        int iconHeight = screenWidth / 5;

        initializeBackground();

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

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        tweenManager.update(delta);

        batch.begin();
        backgroundSprite.draw(batch);
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

    private void initializeGameOverSprite() {
        gameOverSprite = new Sprite(Assets.instance.getGameOverTexture());
        gameOverSprite.setPosition(0, screenHeight - screenWidth/5);
        gameOverSprite.setSize(screenWidth, screenWidth/5);
    }

    private void initializeBackground() {
        backgroundSprite = new Sprite(Assets.instance.getSpaceBackgroundTexture());
        backgroundSprite.setPosition(0, 0);
        backgroundSprite.setSize(Constants.getWidth(), Constants.getHeight());
    }

    private void fadeOutAndReturnToMainMenu() {
        Tween.set(font, BitmapFontAccessor.ALPHA).target(1).start(tweenManager);
        Tween.set(gameOverSprite, SpriteAccessor.ALPHA).target(1).start(tweenManager);
        Tween.set(homeSprite, SpriteAccessor.ALPHA).target(1).start(tweenManager);
        Tween.set(replaySprite, SpriteAccessor.ALPHA).target(1).start(tweenManager);
        Tween.set(rateSprite, SpriteAccessor.ALPHA).target(1).start(tweenManager);
        Tween.set(leaderboardSprite, SpriteAccessor.ALPHA).target(1).start(tweenManager);

        Tween.to(font, BitmapFontAccessor.ALPHA, 2).target(0).start(tweenManager);
        Tween.to(gameOverSprite, SpriteAccessor.ALPHA, 2).target(0).start(tweenManager);
        Tween.to(homeSprite, SpriteAccessor.ALPHA, 2).target(0).start(tweenManager);
        Tween.to(replaySprite, SpriteAccessor.ALPHA, 2).target(0).start(tweenManager);
        Tween.to(rateSprite, SpriteAccessor.ALPHA, 2).target(0).start(tweenManager);
        Tween.to(leaderboardSprite, SpriteAccessor.ALPHA, 2).target(0).setCallback(new TweenCallback() {
            @Override
            public void onEvent(int type, BaseTween<?> source) {
                game.setScreen(new MainMenuScreen(game, isSoundOn));
            }
        }).start(tweenManager);

        Gdx.input.setInputProcessor(null);
    }

    private void fadeOutAndStartGame() {
        Tween.set(font, BitmapFontAccessor.ALPHA).target(1).start(tweenManager);
        Tween.set(gameOverSprite, SpriteAccessor.ALPHA).target(1).start(tweenManager);
        Tween.set(homeSprite, SpriteAccessor.ALPHA).target(1).start(tweenManager);
        Tween.set(replaySprite, SpriteAccessor.ALPHA).target(1).start(tweenManager);
        Tween.set(rateSprite, SpriteAccessor.ALPHA).target(1).start(tweenManager);
        Tween.set(leaderboardSprite, SpriteAccessor.ALPHA).target(1).start(tweenManager);

        Tween.to(font, BitmapFontAccessor.ALPHA, 2).target(0).start(tweenManager);
        Tween.to(gameOverSprite, SpriteAccessor.ALPHA, 2).target(0).start(tweenManager);
        Tween.to(homeSprite, SpriteAccessor.ALPHA, 2).target(0).start(tweenManager);
        Tween.to(replaySprite, SpriteAccessor.ALPHA, 2).target(0).start(tweenManager);
        Tween.to(rateSprite, SpriteAccessor.ALPHA, 2).target(0).start(tweenManager);
        Tween.to(leaderboardSprite, SpriteAccessor.ALPHA, 2).target(0).setCallback(new TweenCallback() {
            @Override
            public void onEvent(int type, BaseTween<?> source) {
                game.setScreen(new GameScreen(game, isSoundOn));
            }
        }).start(tweenManager);

        Gdx.input.setInputProcessor(null);
    }

    private class GameOverGestureListener implements GestureDetector.GestureListener {

        @Override
        public boolean touchDown(float x, float y, int pointer, int button) {
            if (isButtonPressed(homeSprite, x, y)) {
                homeSprite.setTexture(Assets.instance.getHomePressedTexture());
                fadeOutAndReturnToMainMenu();
                return true;
            } else if (isButtonPressed(replaySprite, x, y)) {
                replaySprite.setTexture(Assets.instance.getReplayPressedTexture());
                fadeOutAndStartGame();
                return true;
            } else if (isButtonPressed(rateSprite, x, y)) {
                Gdx.net.openURI(Constants.GAME_URI);
                return true;
            } else if (isButtonPressed(leaderboardSprite, x, y)) {
                if (game.getActionResolver().getSignedInGPGS()){
                    game.getActionResolver().getLeaderboardGPGS();
                }else{
                    game.getActionResolver().loginGPGS();
                }
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
