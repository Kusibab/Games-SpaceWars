package com.Kusibab;

/**
 * Created by Kusibab on 2016-10-30.
 */
public class Bomb extends Actor {
    public static final int UP_LEFT =0;
    public static final int UP = 1;
    public static final int UP_RIGHT = 2;
    public static final int RIGHT =3;
    public static final int DOWN_RIGHT = 4;
    public static final int DOWN =5;
    public static final int DOWN_LEFT = 6;
    public static final int LEFT = 7;

    private static final String BOMB_URL = "bomb.gif";

    protected static final int BOMB_SPEED = 3;
    protected int vx;
    protected int vy;

    public Bomb(Stage stage,int heading, int x, int y){
        super(stage);
        this.x=x;
        this.y = y;
        String sprite = "";

        switch (heading){
            case UP_LEFT : vx=-BOMB_SPEED; vy=-BOMB_SPEED; sprite = BOMB_URL; break;
            case UP: vx = 0; vy = -BOMB_SPEED; sprite = BOMB_URL; break;
            case UP_RIGHT : vx = BOMB_SPEED; vy = -BOMB_SPEED; sprite = BOMB_URL; break;
            case LEFT : vx = -BOMB_SPEED; vy =0; sprite= BOMB_URL; break;
            case RIGHT: vx = BOMB_SPEED; vy=0; sprite =BOMB_URL; break;
            case DOWN_LEFT: vx= - BOMB_SPEED; vy= BOMB_SPEED; sprite = BOMB_URL; break;
            case DOWN : vx=0; vy= BOMB_SPEED; sprite = BOMB_URL; break;
            case DOWN_RIGHT : vx = BOMB_SPEED; vy = BOMB_SPEED; sprite = BOMB_URL; break;
        }

        setSpriteName(sprite);
    }
    public void act(){
        y+=vy;
        x+=vx;
        if (y<0||y>Stage.WINDOW_HEIGHT ||x<0||x>Stage.WINDOW_WIDTH)
            remove();
    }
}
