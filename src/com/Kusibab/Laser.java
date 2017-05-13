package com.Kusibab;


public class Laser extends Actor{
    protected static final int BULLET_SPEED = 3;

    public Laser(Stage stage){
        super(stage);
        setSpriteName("Laser.png");
    }
    public void act(){
        y+=BULLET_SPEED;
        if(y>Stage.GAME_HEIGHT)
            remove();
    }
}
