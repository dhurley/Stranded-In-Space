package net.djhurley.strandedinspace.common;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetErrorListener;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGeneratorLoader;
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader;
import com.badlogic.gdx.math.MathUtils;
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

        assetManager.load(Constants.LEADERBOARD_PNG, Texture.class);
        assetManager.load(Constants.PLAY_PNG, Texture.class);
        assetManager.load(Constants.PLAY_PRESS_PNG, Texture.class);
        assetManager.load(Constants.RATE_PNG, Texture.class);
        assetManager.load(Constants.SPEAKER_ON_PNG, Texture.class);
        assetManager.load(Constants.SPEAKER_OFF_PNG, Texture.class);
        assetManager.load(Constants.HOME_PNG, Texture.class);
        assetManager.load(Constants.HOME_PRESSED_PNG, Texture.class);
        assetManager.load(Constants.REPLAY_PNG, Texture.class);
        assetManager.load(Constants.REPLAY_PRESSED_PNG, Texture.class);

        assetManager.load(Constants.SPACESHIP_PNG, Texture.class);
        assetManager.load(Constants.SPACESHIP_WITH_FULL_BOOST_PNG, Texture.class);
        assetManager.load(Constants.SPACESHIP_WITH_LEFT_BOOST_PNG, Texture.class);
        assetManager.load(Constants.SPACESHIP_WITH_RIGHT_BOOST_PNG, Texture.class);
        assetManager.load(Constants.SPACE_BACKGROUND_PNG, Texture.class);
        assetManager.load(Constants.ASTEROID_PNG, Texture.class);
        assetManager.load(Constants.EXPLOSION_PNG, Texture.class);

        assetManager.load(Constants.TITLE_PNG, Texture.class);
        assetManager.load(Constants.GAME_OVER_PNG, Texture.class);
        assetManager.load(Constants.SPLASH_SCREEN_PNG, Texture.class);

        assetManager.load(Constants.SPACE_OGG, Music.class);
        assetManager.load(Constants.BOOST_OGG, Sound.class);
        assetManager.load(Constants.EXPLOSION_OGG, Sound.class);

        loadDroidSansFont();

        assetManager.finishLoading();

        Gdx.app.debug(TAG, "# of assets loaded: " + assetManager.getAssetNames().size);
        for (String assetName : assetManager.getAssetNames()) {
            Gdx.app.debug(TAG, "asset: " + assetName);
        }
    }

    private void loadDroidSansFont() {
        FileHandleResolver resolver = new InternalFileHandleResolver();
        this.assetManager.setLoader(FreeTypeFontGenerator.class, new FreeTypeFontGeneratorLoader(resolver));
        this.assetManager.setLoader(BitmapFont.class, ".ttf", new FreetypeFontLoader(resolver));
        FreetypeFontLoader.FreeTypeFontLoaderParameter param = new FreetypeFontLoader.FreeTypeFontLoaderParameter();
        param.fontFileName = Constants.DROID_SANS_TTF;

        float size = 30.5f * Gdx.graphics.getDensity() * (float) ((double) Constants.getWidth() / (double) Constants.getHeight());
        Gdx.app.debug(TAG, "Screen height: " + Constants.getHeight());
        Gdx.app.debug(TAG, "Screen width: " + Constants.getWidth());
        Gdx.app.debug(TAG, "Density: " + Gdx.graphics.getDensity());
        Gdx.app.debug(TAG, "Font size: " + MathUtils.round(size));

        param.fontParameters.size = MathUtils.round(size);
        this.assetManager.load(Constants.DROID_SANS_TTF, BitmapFont.class, param);
    }

    @Override
    public void dispose() {
        assetManager.dispose();
    }

    @Override
    public void error(AssetDescriptor asset, Throwable throwable) {
        Gdx.app.error(TAG, "cannot load: " + asset.fileName, (Exception)throwable);
    }

    public Texture getLeaderboardTexture(){
        return assetManager.get(Constants.LEADERBOARD_PNG);
    }

    public Texture getPlayTexture(){
        return assetManager.get(Constants.PLAY_PNG);
    }

    public Texture getPlayPressedTexture(){
        return assetManager.get(Constants.PLAY_PRESS_PNG);
    }

    public Texture getRateTexture(){
        return assetManager.get(Constants.RATE_PNG);
    }

    public Texture getSpeakerOnTexture(){
        return assetManager.get(Constants.SPEAKER_ON_PNG);
    }

    public Texture getSpeakerOffTexture(){
        return assetManager.get(Constants.SPEAKER_OFF_PNG);
    }

    public Texture getHomeTexture(){
        return assetManager.get(Constants.HOME_PNG);
    }

    public Texture getHomePressedTexture(){
        return assetManager.get(Constants.HOME_PRESSED_PNG);
    }

    public Texture getReplayTexture(){
        return assetManager.get(Constants.REPLAY_PNG);
    }

    public Texture getReplayPressedTexture(){
        return assetManager.get(Constants.REPLAY_PRESSED_PNG);
    }

    public Texture getSpaceshipTexture(){
        return assetManager.get(Constants.SPACESHIP_PNG);
    }

    public Texture getSpaceShipWithFullBoostTexture(){
        return assetManager.get(Constants.SPACESHIP_WITH_FULL_BOOST_PNG);
    }

    public Texture getSpaceShipWithLeftBoostTexture(){
        return assetManager.get(Constants.SPACESHIP_WITH_LEFT_BOOST_PNG);
    }

    public Texture getSpaceShipWithRightBoostTexture(){
        return assetManager.get(Constants.SPACESHIP_WITH_RIGHT_BOOST_PNG);
    }

    public Texture getAsteroidTexture(){
        return assetManager.get(Constants.ASTEROID_PNG);
    }

    public Texture getExplosionTexture(){
        return assetManager.get(Constants.EXPLOSION_PNG);
    }

    public Texture getSpaceBackgroundTexture(){
        return assetManager.get(Constants.SPACE_BACKGROUND_PNG);
    }

    public Texture getTitleTexture() {
        return assetManager.get(Constants.TITLE_PNG);
    }

    public Texture getGameOverTexture() {
        return assetManager.get(Constants.GAME_OVER_PNG);
    }

    public Texture getSplashScreenTexture() {
        return assetManager.get(Constants.SPLASH_SCREEN_PNG);
    }

    public Sound getBoostSound(){
        return assetManager.get(Constants.BOOST_OGG, Sound.class);
    }

    public Music getSpaceSound() {
        return assetManager.get(Constants.SPACE_OGG, Music.class);
    }

    public Sound getExplosionSound() {
        return assetManager.get(Constants.EXPLOSION_OGG, Sound.class);
    }

    public BitmapFont getDroidSansFont(){
        return assetManager.get(Constants.DROID_SANS_TTF, BitmapFont.class);
    }
}
