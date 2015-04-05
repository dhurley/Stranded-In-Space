package net.djhurley.strandedinspace.tween;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import aurelienribon.tweenengine.TweenAccessor;

/**
 * Created by DJHURLEY on 04/04/2015.
 */
public class BitmapFontAccessor implements TweenAccessor<BitmapFont> {

    public static final int ALPHA = 0;

    @Override
    public int getValues(BitmapFont target, int tweenType, float[] returnValues) {
        switch(tweenType) {
            case ALPHA:
                returnValues[0] = target.getColor().a;
                return 1;
            default:
                assert false;
                return -1;
        }
    }

    @Override
    public void setValues(BitmapFont target, int tweenType, float[] newValues) {
        switch(tweenType) {
            case ALPHA:
                target.setColor(target.getColor().r, target.getColor().g, target.getColor().b, newValues[0]);
                break;
            default:
                assert false;
        }
    }
}
