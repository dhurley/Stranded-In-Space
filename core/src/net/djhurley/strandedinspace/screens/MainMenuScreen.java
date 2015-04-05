package net.djhurley.strandedinspace.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;
import net.djhurley.strandedinspace.StrandedInSpace;
import net.djhurley.strandedinspace.common.Assets;
import net.djhurley.strandedinspace.common.Constants;
import net.djhurley.strandedinspace.tween.SpriteAccessor;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenManager;

/**
 * Created by DJHURLEY on 22/02/2015.
 */
public class MainMenuScreen implements Screen {
    private static final String TAG = GameOverScreen.class.getName();
    private final StrandedInSpace game;

    private SpriteBatch spriteBatch;

    private Sprite backgroundSprite;
    private Sprite titleSprite;
    private Sprite spaceshipSprite;
    private Sprite playSprite;
    private Sprite rateSprite;
    private Sprite leaderboardSprite;
    private Sprite speakerSprite;

    private int screenWidth;
    private int screenHeight;

    private Music spaceMusic;
    private boolean isSoundOn;

    private TweenManager tweenManager;

    public MainMenuScreen(StrandedInSpace game, boolean isSoundOn) {
        this.game = game;
        this.isSoundOn = isSoundOn;
    }

    @Override
    public void show() {
        spriteBatch = new SpriteBatch();
        screenWidth = Gdx.graphics.getWidth();
        screenHeight = Gdx.graphics.getHeight();

        int iconWidth = screenWidth/5;
        int iconHeight = screenWidth/5;

        tweenManager = new TweenManager();
        Tween.registerAccessor(Sprite.class, new SpriteAccessor());

        initializeBackground();

        titleSprite = new Sprite(Assets.instance.getTitleTexture());
        titleSprite.setSize(screenWidth, iconHeight);
        titleSprite.setPosition(0, screenHeight - iconHeight);

        spaceshipSprite = new Sprite(Assets.instance.getSpaceshipTexture());
        spaceshipSprite.setSize(screenWidth/5, screenWidth/5);
        spaceshipSprite.setPosition(screenWidth/2 - spaceshipSprite.getHeight()/2, screenHeight/2);

        playSprite = new Sprite(Assets.instance.getPlayTexture());
        playSprite.setSize(iconWidth, iconHeight);
        playSprite.setPosition(screenWidth*0.04f, iconHeight);

        rateSprite = new Sprite(Assets.instance.getRateTexture());
        rateSprite.setSize(iconWidth, iconHeight);
        rateSprite.setPosition(screenWidth*0.04f*2 + iconWidth, iconHeight);

        leaderboardSprite = new Sprite(Assets.instance.getLeaderboardTexture());
        leaderboardSprite.setSize(iconWidth, iconHeight);
        leaderboardSprite.setPosition(screenWidth*0.04f*3 + iconWidth*2, iconHeight);

        if(isSoundOn) {
            speakerSprite = new Sprite(Assets.instance.getSpeakerOnTexture());
        }else{
            speakerSprite = new Sprite(Assets.instance.getSpeakerOffTexture());
        }
        speakerSprite.setSize(iconWidth, iconHeight);
        speakerSprite.setPosition(screenWidth*0.04f*4 + iconWidth*3, iconHeight);

        if(spaceMusic == null) {
            spaceMusic = Assets.instance.getSpaceSound();
            spaceMusic.setLooping(true);
        }
        if(isSoundOn) {
            spaceMusic.play();
        }else{
            spaceMusic.stop();
        }

        Gdx.input.setInputProcessor(new GestureDetector(new MainMenuGestureListener()));
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1,1,1,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        tweenManager.update(delta);

        spriteBatch.begin();
        backgroundSprite.draw(spriteBatch);
        titleSprite.draw(spriteBatch);

        spaceshipSprite.draw(spriteBatch);
        spaceshipSprite.setOriginCenter();
        spaceshipSprite.setRotation(spaceshipSprite.getRotation() + 1);

        playSprite.draw(spriteBatch);
        rateSprite.draw(spriteBatch);
        leaderboardSprite.draw(spriteBatch);
        speakerSprite.draw(spriteBatch);
        spriteBatch.end();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void hide() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {
        spriteBatch.dispose();
        spaceMusic.dispose();
    }

    private void initializeBackground() {
        backgroundSprite = new Sprite(Assets.instance.getSpaceBackgroundTexture());
        backgroundSprite.setPosition(0, 0);
        backgroundSprite.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    private void fadeOutAndStartGame() {
        Tween.set(titleSprite, SpriteAccessor.ALPHA).target(1).start(tweenManager);
        Tween.set(spaceshipSprite, SpriteAccessor.ALPHA).target(1).start(tweenManager);
        Tween.set(playSprite, SpriteAccessor.ALPHA).target(1).start(tweenManager);
        Tween.set(rateSprite, SpriteAccessor.ALPHA).target(1).start(tweenManager);
        Tween.set(leaderboardSprite, SpriteAccessor.ALPHA).target(1).start(tweenManager);
        Tween.set(speakerSprite, SpriteAccessor.ALPHA).target(1).start(tweenManager);

        Tween.to(titleSprite, SpriteAccessor.ALPHA, 2).target(0).start(tweenManager);
        Tween.to(spaceshipSprite, SpriteAccessor.ALPHA, 2).target(0).start(tweenManager);
        Tween.to(playSprite, SpriteAccessor.ALPHA, 2).target(0).start(tweenManager);
        Tween.to(rateSprite, SpriteAccessor.ALPHA, 2).target(0).start(tweenManager);
        Tween.to(leaderboardSprite, SpriteAccessor.ALPHA, 2).target(0).start(tweenManager);
        Tween.to(speakerSprite, SpriteAccessor.ALPHA, 2).target(0).setCallback(new TweenCallback() {
            @Override
            public void onEvent(int type, BaseTween<?> source) {
                game.setScreen(new GameScreen(game, isSoundOn));
            }
        }).start(tweenManager);

        Gdx.input.setInputProcessor(null);
    }

    private class MainMenuGestureListener implements GestureDetector.GestureListener {

        @Override
        public boolean touchDown(float x, float y, int pointer, int button) {
            if(isButtonPressed(playSprite, x, y)){
                fadeOutAndStartGame();
                return true;
            }else if(isButtonPressed(rateSprite, x, y)){
                Gdx.net.openURI(Constants.GAME_URI);
                return true;
            }else if(isButtonPressed(leaderboardSprite, x, y)){
                if (game.getActionResolver().getSignedInGPGS()){
                    game.getActionResolver().getLeaderboardGPGS();
                }else{
                    game.getActionResolver().loginGPGS();
                }
                return true;
            }else if(isButtonPressed(speakerSprite, x, y)){
                if(isSoundOn){
                    speakerSprite.setTexture(Assets.instance.getSpeakerOffTexture());
                    spaceMusic.stop();
                    isSoundOn = false;
                }else{
                    speakerSprite.setTexture(Assets.instance.getSpeakerOnTexture());
                    spaceMusic.play();
                    isSoundOn = true;
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