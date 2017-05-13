package com.Kusibab;


public class Bullet extends Actor{
    protected static final int BULLET_SPEED=5;

    public Bullet(Stage stage){
        super(stage);
        setSpriteName("missle.png");
    }

    public void act(){
        y-=BULLET_SPEED;
        if(y<0)
            remove();
    }
    public void collision(Actor a){
        if (a instanceof Monster)
            remove();

    }
}
