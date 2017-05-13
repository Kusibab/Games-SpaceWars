package com.Kusibab;

import java.awt.image.ImageObserver;

/**
 * Created by Kusibab on 2016-10-29.
 */
public interface Stage extends ImageObserver {
    int GAME_HEIGHT = 500;
    int WINDOW_WIDTH = 800;
    int WINDOW_HEIGHT = 600;
    int SZYBKOSC = 10;
    SpriteCache getSpriteCache();
    void addActor (Actor a);
    Player getPlayer();
    void gameOver();


}
