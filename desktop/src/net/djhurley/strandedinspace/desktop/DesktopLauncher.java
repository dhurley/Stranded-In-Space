package net.djhurley.strandedinspace.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import net.djhurley.strandedinspace.StrandedInSpace;

public class DesktopLauncher {
    public static void main(String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.title = "Stranded In Space";
        config.height = 450;
        config.width = 260;
        new LwjglApplication(new StrandedInSpace(), config);
    }
}