package com.twodcrazedgaming.game.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Ellipse;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Shape2D;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;
import com.twodcrazedgaming.common.Assets;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by DJHURLEY on 20/01/2015.
 */
public class Spaceship implements Disposable {
    private static final String TAG = Spaceship.class.getName();

    private static final int REFUELING_TIME = 10000;

    private Texture spaceshipTexture = Assets.instance.getSpaceshipTexture();
    private Texture spaceshipWithBoostTexture = Assets.instance.getSpaceShipWithBoostTexture();
    private Sprite sprite = new Sprite(spaceshipTexture);
    private Sound boostSound = Assets.instance.getBoostSound();

    private Vector2 size;
    private Vector2 position;
    private float rotation;
    private Vector2 boost;

    private int boostSpeed = 2;
    private int fuelLevel;
    private long lastTimeSpaceshipRefueled = Calendar.getInstance().getTimeInMillis();;

    private final boolean isSoundOn;

    private ShapeRenderer shapeRenderer = new ShapeRenderer();

    public Spaceship(boolean isSoundOn) {
        this.isSoundOn = isSoundOn;
        size = new Vector2(Gdx.graphics.getWidth() / 5, Gdx.graphics.getWidth() / 5);
        position = new Vector2((Gdx.graphics.getWidth() / 2) - (size.y / 2), Gdx.graphics.getHeight() / 15);
        boost = new Vector2(0, 0);
        rotation = 0;
        fuelLevel = 100;

        sprite.setSize(size.x, size.y);
        sprite.setOrigin(size.x / 2, size.y / 2);
        setBoost(boostSpeed);
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
    }

    @Override
    public void dispose() {
        //boostSound.dispose();
    }

    public void startBoost() {
        if (fuelLevel != 0) {
            sprite.setTexture(spaceshipWithBoostTexture);
            setBoost(boostSpeed);
            if (isSoundOn) {
                playBoostSound();
            }
            useFuel();
        }
    }

    public void stopMovement(){
        setBoost(0);
    }

    public Vector2 getSize() {
        return size;
    }

    public Vector2 getPosition() {
        return position;
    }

    public int getFuelLevel() {
        return fuelLevel;
    }

    public List<Polygon> getPolygonShapes() {
        List<Polygon> shapes = new ArrayList<Polygon>();

        Polygon centreShape = new Polygon(new float[]{
                position.x + (size.x * 0.5f), position.y + size.y,
                position.x + (size.x * 0.5625f), position.y + (size.y * 0.93125f),
                position.x + (size.x * 0.59375f), position.y + (size.y * 0.8625f),
                position.x + (size.x * 0.625f), position.y + (size.y * 0.725f),
                position.x + (size.x * 0.625f), position.y + (size.y * 0.5875f),
                position.x + (size.x * 0.59375f), position.y + (size.y * 0.45f),
                position.x + (size.x * 0.5625f), position.y + (size.y * 0.38125f),
                position.x + (size.x * 0.5f), position.y + (size.y * 0.3125f),
                position.x + (size.x * 0.4375f), position.y + (size.y * 0.38125f),
                position.x + (size.x * 0.40625f), position.y + (size.y * 0.45f),
                position.x + (size.x * 0.375f), position.y + (size.y * 0.5875f),
                position.x + (size.x * 0.375f), position.y + (size.y * 0.725f),
                position.x + (size.x * 0.40625f), position.y + (size.y * 0.8625f),
                position.x + (size.x * 0.4375f), position.y + (size.y * 0.93125f),
        });

        Polygon leftEngineShape = new Polygon(new float[]{
                position.x + (size.x * 0.075f), position.y + (size.y * 0.6f),
                position.x + (size.x * 0.1125f), position.y + (size.y * 0.55625f),
                position.x + (size.x * 0.13125f), position.y + (size.y * 0.5125f),
                position.x + (size.x * 0.15f), position.y + (size.y * 0.425f),
                position.x + (size.x * 0.15f), position.y + (size.y * 0.3375f),
                position.x + (size.x * 0.13125f), position.y + (size.y * 0.25f),
                position.x + (size.x * 0.1125f), position.y + (size.y * 0.20625f),
                position.x + (size.x * 0.075f), position.y + (size.y * 0.1625f),
                position.x + (size.x * 0.0375f), position.y + (size.y * 0.20625f),
                position.x + (size.x * 0.01875f), position.y + (size.y * 0.25f),
                position.x, position.y + (size.y * 0.3375f),
                position.x, position.y + (size.y * 0.425f),
                position.x + (size.x * 0.01875f), position.y + (size.y * 0.5125f),
                position.x + (size.x * 0.0375f), position.y + (size.y * 0.55625f),
        });

        Polygon rightEngineShape = new Polygon(new float[]{
                position.x + (size.x * 0.925f), position.y + (size.y * 0.6f),
                position.x + (size.x * 0.9625f), position.y + (size.y * 0.55625f),
                position.x + (size.x * 0.98125f), position.y + (size.y * 0.5125f),
                position.x + size.x, position.y + (size.y * 0.425f),
                position.x + size.x, position.y + (size.y * 0.3375f),
                position.x + (size.x * 0.98125f), position.y + (size.y * 0.25f),
                position.x + (size.x * 0.9625f), position.y + (size.y * 0.20625f),
                position.x + (size.x * 0.925f), position.y + (size.y * 0.1625f),
                position.x + (size.x * 0.8875f), position.y + (size.y * 0.20625f),
                position.x + (size.x * 0.86875f), position.y + (size.y * 0.25f),
                position.x + (size.x * 0.85f), position.y + (size.y * 0.3375f),
                position.x + (size.x * 0.85f), position.y + (size.y * 0.425f),
                position.x + (size.x * 0.86875f), position.y + (size.y * 0.5125f),
                position.x + (size.x * 0.8875f), position.y + (size.y * 0.55625f),
        });

        Polygon leftArmShape = new Polygon(new float[]{
                position.x + (size.x * 0.15f), position.y + (size.y * 0.3375f),
                position.x + (size.x * 0.375f), position.y + (size.y * 0.425f),
                position.x + (size.x * 0.375f), position.y + (size.y * 0.6125f),
                position.x + (size.x * 0.15f), position.y + (size.y * 0.525f),
        });

        Polygon rightArmShape = new Polygon(new float[]{
                position.x + (size.x * 0.625f), position.y + (size.y * 0.425f),
                position.x + (size.x * 0.85f), position.y + (size.y * 0.3375f),
                position.x + (size.x * 0.85f), position.y + (size.y * 0.525f),
                position.x + (size.x * 0.625f), position.y + (size.y * 0.6125f),
        });

        centreShape.setOrigin(position.x + (size.x * 0.5f), position.y + (size.y * 0.5f));
        leftEngineShape.setOrigin(position.x + (size.x * 0.5f), position.y + (size.y * 0.5f));
        rightEngineShape.setOrigin(position.x + (size.x * 0.5f), position.y + (size.y * 0.5f));
        leftArmShape.setOrigin(position.x + (size.x * 0.5f), position.y + (size.y * 0.5f));
        rightArmShape.setOrigin(position.x + (size.x * 0.5f), position.y + (size.y * 0.5f));

        centreShape.rotate(rotation);
        leftEngineShape.rotate(rotation);
        rightEngineShape.rotate(rotation);
        leftArmShape.rotate(rotation);
        rightArmShape.rotate(rotation);

        shapes.add(centreShape);
        shapes.add(leftEngineShape);
        shapes.add(rightEngineShape);
        shapes.add(leftArmShape);
        shapes.add(rightArmShape);

        return shapes;
    }

    private void handleTurning() {
        rotation = rotation + 1;
        if (rotation == 361) {
            rotation = 1;
        }
    }

    private void handleDirection() {
        position.y = position.y + boost.y;
        position.x = position.x + boost.x;
    }

    private void handleRefueling() {
        long currentTime = Calendar.getInstance().getTimeInMillis();
        if (currentTime - lastTimeSpaceshipRefueled > REFUELING_TIME) {
            lastTimeSpaceshipRefueled = currentTime;
            refuel();
        }
    }

    private void setBoost(float speed) {
        Gdx.app.debug(TAG, "rotation: " + rotation);
        if (rotation >= 0 && rotation <= 90) {
            float tmp = (rotation * speed) / 90;
            boost.y = speed - tmp;
            boost.x = -tmp;
        } else if (rotation >= 90 && rotation <= 180) {
            float tmp = ((rotation - 90) * speed) / 90;
            boost.y = -tmp;
            boost.x = -(speed - tmp);
        } else if (rotation >= 180 && rotation <= 270) {
            float tmp = ((rotation - 180) * speed) / 90;
            boost.y = -(speed - tmp);
            boost.x = tmp;
        } else if (rotation >= 270 && rotation <= 360) {
            float tmp = ((rotation - 270) * speed) / 90;
            boost.y = tmp;
            boost.x = speed - tmp;
        }
    }

    private void playBoostSound() {
        long id = boostSound.play();
        boostSound.setVolume(id, 0.15f);
    }

    private void useFuel() {
        if (fuelLevel != 0) {
            fuelLevel = fuelLevel - 10;
        }

        Gdx.app.debug(TAG, "fuel used. fuel level is now: " + fuelLevel);
    }

    public void stopBoost() {
        sprite.setTexture(spaceshipTexture);
    }

    private void refuel() {
        if (fuelLevel != 100) {
            fuelLevel = fuelLevel + 10;
        }

        Gdx.app.debug(TAG, "refueling. fuel level is now: " + fuelLevel);
    }
}
