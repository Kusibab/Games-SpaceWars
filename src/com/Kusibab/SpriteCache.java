package com.Kusibab;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.net.URL;
import java.util.HashMap;

/**
 * Created by Kusibab on 2016-10-29.
 */
public class SpriteCache extends ResourceCache implements ImageObserver{


    protected Object loadResource(URL url){

        try{
            return ImageIO.read(url);
        } catch (Exception e){
            System.out.println("ERROR " + url);
            System.out.println("In : " + e.getClass().getName()+" " + e.getMessage());
            System.exit(0);
            return null;
        }
    }

    public BufferedImage createCompatible(int width, int height, int transparency){
        GraphicsConfiguration gc= GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
        return gc.createCompatibleImage(width,height,transparency);
    }

    public BufferedImage getSprite(String name){
        BufferedImage loaded = (BufferedImage)getResource("img/"+name);
        BufferedImage compatible = createCompatible(loaded.getWidth(),loaded.getHeight(),Transparency.BITMASK);
        Graphics g = compatible.getGraphics();
        g.drawImage(loaded,0,0,this);
        return compatible;
    }
    public boolean imageUpdate(Image img,int infoflags,int x,int y, int w, int h){
        return (infoflags & (ALLBITS|ABORT)) == 0;
    }
}



