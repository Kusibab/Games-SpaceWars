package com.Kusibab;

public class Monster extends Actor {
    protected int vx;
    protected static final double FIRING_FREQUENCY = 0.01;




    public Monster(Stage stage){
        super(stage);

        setSpriteName("alien.gif");



    }

    public void fire(){
        Laser m = new Laser(stage);
        m.setX(x+getWidth()/2);
        m.setY(y+getHeight());
        stage.addActor(m);
    }

    public void act(){
        x+=vx;
        if (x<1 || x>Stage.WINDOW_WIDTH -1-getWidth())
            vx=-vx;
        if (x<0){
            x=0;
        }
        else if (x>Stage.WINDOW_WIDTH -getWidth()){
            x=Stage.WINDOW_WIDTH -getWidth();
        }
        if (Math.random()<FIRING_FREQUENCY){
            fire();
        }
    }

    public int getVx() {return vx;}
    public void setVx(int i){vx=i;}

    public void collision(Actor a){
        if (a instanceof Bullet || a instanceof Bomb){
            remove();

            spawn();
            stage.getPlayer().addScore(10);
            }


        }

    public void spawn(){
        Monster m = new Monster(stage);
        m.setX((int)(Math.random()*Stage.WINDOW_WIDTH));
        m.setY((int)(Math.random()*Stage.GAME_HEIGHT /2));
        m.setVx((int)(Math.random()*20-10)+10);
        stage.addActor(m);
    }





}

