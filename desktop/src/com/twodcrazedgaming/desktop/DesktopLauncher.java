package com.twodcrazedgaming.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.twodcrazedgaming.StrandedInSpace;
import com.twodcrazedgaming.common.Constants;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.title = Constants.TITLE;
        config.height = 600;
        config.width = 400;
		new LwjglApplication(new StrandedInSpace(), config);
	}
}
