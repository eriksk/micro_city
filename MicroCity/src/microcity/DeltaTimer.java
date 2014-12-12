/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package microcity;

import org.lwjgl.Sys;

/**
 *
 * @author Erik
 */
public class DeltaTimer {

    /**
     * time at last frame
     */
    long lastFrame;

    /**
     * frames per second
     */
    int fps;
    /**
     * last fps time
     */
    long lastFPS;
    long currentFps;

    public DeltaTimer() {
        lastFPS = getTime(); //initialise lastFPS by setting to current Time
    }

    /**
     * Get the time in milliseconds
     *
     * @return The system time in milliseconds
     */
    public long getTime() {
        return System.currentTimeMillis();
    }

    public int getDelta() {
        long time = getTime();
        int delta = (int) (time - lastFrame);
        lastFrame = time;

        return delta;
    }

    /**
     * Calculate the FPS and set it in the title bar
     */
    public void updateFPS() {
        if (getTime() - lastFPS > 1000) {
            currentFps = fps;
            fps = 0;
            lastFPS += 1000;
        }
        fps++;
    }

    public int getFps() {
        return (int)currentFps;
    }
}
