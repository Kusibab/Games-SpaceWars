package com.Kusibab;

import java.awt.event.KeyEvent;


public class Player extends Actor {
    protected static final int PLAYER_SPEED = 4;
    protected int clusterBombs = 5;
    protected int vx;
    protected int vy;
    private boolean up,down,left,right;
    public static final int MAX_SHIELDS = 200;
    public static final int MAX_BOMBS = 5;
    private int score;
    private int shields;


    public Player(Stage stage){
        super(stage);
        setSpriteName("spaceship.gif");
        clusterBombs=MAX_BOMBS;
        shields=MAX_SHIELDS;
    }

    public void act(){
        x+=vx;
        y+=vy;
        if (x < 1){
            x = 1;
        }
        else
        if (x>Stage.WINDOW_WIDTH -1-getWidth()){
            x = Stage.WINDOW_WIDTH -1-getWidth();
        }

        if (y < 0){
            y=0;
        }
        else
        if (y>Stage.GAME_HEIGHT -getHeight()){
            y=Stage.GAME_HEIGHT -getHeight();
        }
    }
    public int getVx() {return vx;}
    public void setVx(int i) {vx=i;}
    public int getVy() {return vy;}
    public void setVy(int i){vy=i;}
    protected void updateSpeed(){
        vx=0;
        vy=0;
        if (down) vy=PLAYER_SPEED;
        if (up) vy = - PLAYER_SPEED;
        if (left) vx = - PLAYER_SPEED;
        if (right) vx = PLAYER_SPEED;
    }

    public void fire(){

            Bullet b = new Bullet(stage);
            b.setX(x + b.getWidth());
            b.setY(y - b.getHeight());
            stage.addActor(b);

    }
    public void fireCluster(){
        if (clusterBombs==0)
            return;

        clusterBombs--;
        stage.addActor(new Bomb(stage,Bomb.UP_LEFT,x,y));
        stage.addActor(new Bomb(stage,Bomb.UP,x,y));
        stage.addActor(new Bomb(stage,Bomb.UP_RIGHT,x,y));
        stage.addActor(new Bomb(stage,Bomb.DOWN,x,y));
        stage.addActor(new Bomb(stage,Bomb.DOWN_LEFT,x,y));
        stage.addActor(new Bomb(stage,Bomb.DOWN_RIGHT,x,y));
        stage.addActor(new Bomb(stage,Bomb.LEFT,x,y));
        stage.addActor(new Bomb(stage,Bomb.RIGHT,x,y));

    }

    public void keyReleased(KeyEvent e){
        switch (e.getKeyCode()){
            case KeyEvent.VK_DOWN : down = false; break;
            case KeyEvent.VK_UP : up = false; break;
            case KeyEvent.VK_LEFT : left = false; break;
            case KeyEvent.VK_RIGHT : right = false; break;
        }
        updateSpeed();
    }

    public void keyPressed(KeyEvent e){
        switch (e.getKeyCode()){
            case KeyEvent.VK_DOWN : down = true; break;
            case KeyEvent.VK_UP : up = true; break;
            case KeyEvent.VK_LEFT : left = true; break;
            case KeyEvent.VK_RIGHT : right = true; break;
            case KeyEvent.VK_SPACE : fire(); break;
            case KeyEvent.VK_B : fireCluster(); break;
        }
        updateSpeed();
    }
    public int getScore() { return score;}
    public void setScore(int i) {score = i;}
    public int getShields() {return shields;}
    public void setShields(int i) {shields = i;}
    public int getClusterBombs() {return clusterBombs;}
    public void setClusterBombs(int i) {clusterBombs = i;}

    public void addScore(int i) {score+=i;}
    public void addShields(int i) {shields+=i;}

    public void collision(Actor a){
        if (a instanceof Monster){
            a.remove();
            addShields(-50);
            }
        if (a instanceof Laser){
            a.remove();
            addShields(-25);
        }
        if (getShields()<0){
            stage.gameOver();
        }
    }
}
