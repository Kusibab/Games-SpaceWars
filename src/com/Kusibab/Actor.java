package com.Kusibab;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.Rectangle;

/**
 * Created by Kusibab on 2016-10-29.
 */
public class Actor {
    protected int x,y;
    protected int width,heigth;
    protected String spriteName;
    protected Stage stage;
    protected SpriteCache spriteCache;
    protected boolean markedForRemoval;
  ;

    public Actor(Stage stage){
        this.stage = stage;
        spriteCache = stage.getSpriteCache();


    }

    public void paint(Graphics2D g){
        g.drawImage(spriteCache.getSprite(spriteName),x,y,stage);
    }

    public int getX(){return x;}
    public void setX(int i){x=i;}

    public int getY(){return y;}
    public void setY(int i){y=i;}

    public String getSpriteName() {return spriteName;}

    public void setSpriteName(String string){
        spriteName = string;
        BufferedImage image = spriteCache.getSprite(spriteName);
        heigth=image.getHeight();
        width=image.getWidth();
    }



    public int getHeight() { return heigth;}
    public int getWidth() {return width;}
    public void setHeigth(int i) {heigth = i;}
    public void setWidth(int i) {width = i;}

    public void act() {

    }

    public void remove(){
        markedForRemoval=true;
    }

    public boolean isMarkedForRemoval(){
        return markedForRemoval;
    }



    public Rectangle getBounds(){
        return new Rectangle(x,y,width,heigth);
    }
    public void collision(Actor a){}
}
