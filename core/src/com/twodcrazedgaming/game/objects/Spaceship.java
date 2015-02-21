package com.twodcrazedgaming.game.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.twodcrazedgaming.common.Assets;

import java.util.Calendar;

/**
 * Created by DJHURLEY on 20/01/2015.
 */
public class Spaceship {
    private static final String TAG = Spaceship.class.getName();

    private Texture spaceshipTexture = Assets.instance.getSpaceshipTexture();
    private Texture spaceshipWithBoostTexture = Assets.instance.getSpaceShipWithBoostTexture();
    private Sprite sprite = new Sprite(spaceshipTexture);
    private Vector2 size;
    private Vector2 position;
    private float rotation;

    private FuelBar fuelBar;
    private Vector2 boost;
    private Sound boostSound;
    private int fuelLevel;
    private long lastTimeSpaceshipRefueled;

    public Spaceship(){
        size = new Vector2(Gdx.graphics.getWidth() / 5, Gdx.graphics.getWidth() / 5);
        position = new Vector2((Gdx.graphics.getWidth()/2) - (size.y/2), Gdx.graphics.getHeight()/15);
        boost = new Vector2(0, 0);
        rotation = 0;

        boostSound = Assets.instance.getBoostSound();

        fuelBar = new FuelBar();
        fuelLevel = 100;

        sprite.setSize(size.x, size.y);
        sprite.setOrigin(size.x / 2, size.y / 2);
        setBoost(2);
    }

    public void render(SpriteBatch batch) {
        handleTurning();
        handleDirection();
        handleRefueling();

        sprite.setPosition(position.x, position.y);
        sprite.setRotation(rotation);

        batch.begin();
        sprite.draw(batch);
        batch.end();

        fuelBar.render(batch);
    }

    private void handleTurning() {
        rotation = rotation + 1;
        if(rotation==361){
            rotation = 1;
        }
    }

    private void handleDirection() {
        position.y = position.y + boost.y;
        position.x = position.x + boost.x;
    }

    private void handleRefueling() {
        long currentTime = Calendar.getInstance().getTimeInMillis();
        if(currentTime - lastTimeSpaceshipRefueled > 5000){
            lastTimeSpaceshipRefueled = currentTime;
            refuel();
        }
    }

    public void startBoost(){
        if(fuelLevel != 0) {
            sprite.setTexture(spaceshipWithBoostTexture);
            setBoost(2);
            playBoostSound();
            useFuel();
        }
    }

    private void setBoost(float speed) {
        Gdx.app.debug(TAG, "rotation: " + rotation);
        if(rotation >= 0 && rotation <= 90){
            float tmp = (rotation*speed)/90;
            boost.y = speed - tmp;
            boost.x = - tmp;
        }else if(rotation >= 90 && rotation <= 180){
            float tmp = ((rotation-90)*speed)/90;
            boost.y = - tmp;
            boost.x = - (speed - tmp);
        }else if(rotation >= 180 && rotation <= 270){
            float tmp = ((rotation-180)*speed)/90;
            boost.y = - (speed - tmp);
            boost.x = tmp;
        }else if(rotation >= 270 && rotation <= 360){
            float tmp = ((rotation-270)*speed)/90;
            boost.y = tmp;
            boost.x = speed - tmp;
        }
    }


    private void playBoostSound() {
        boostSound.play();
    }

    private void useFuel() {
        if(fuelLevel != 0) {
            fuelLevel = fuelLevel - 10;
            fuelBar.updateFuelBar(fuelLevel);
        }

        Gdx.app.debug(TAG, "fuel used. fuel level is now: " + fuelLevel);
    }

    public void startRefueling() {
        sprite.setTexture(spaceshipTexture);
        lastTimeSpaceshipRefueled = Calendar.getInstance().getTimeInMillis();
    }

    private void refuel(){
        if(fuelLevel != 100) {
            fuelLevel = fuelLevel + 10;
            fuelBar.updateFuelBar(fuelLevel);
        }

        Gdx.app.debug(TAG, "refueling. fuel level is now: " + fuelLevel);
    }
}
