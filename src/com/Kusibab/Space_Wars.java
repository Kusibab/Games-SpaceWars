package com.Kusibab;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.ArrayList;



public class Space_Wars extends Canvas implements Stage, KeyListener {

    public long usedTime;
    public BufferStrategy strategy;
    private SpriteCache spriteCache;
    private ArrayList<Actor> actors;
    private boolean gameEnded = false;
    private Player player;
    private BufferedImage background, backgroundTile;
    private float backgroundY;
    private ArrayList<Actor> actorsToAdd;
    private int monstersKilled;

    public Space_Wars() {
        spriteCache = new SpriteCache();
        JFrame okno = new JFrame(".: SPACE WARS :.");
        okno.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        JPanel panel = (JPanel)okno.getContentPane();
        setBounds(0,0,Stage.WINDOW_WIDTH,Stage.WINDOW_HEIGHT);
        panel.setPreferredSize(new Dimension(Stage.WINDOW_WIDTH,Stage.WINDOW_HEIGHT));
        panel.setLayout(null);
        panel.add(this);
        okno.setBounds(0,0,Stage.WINDOW_WIDTH,Stage.WINDOW_HEIGHT);
        okno.setVisible(true);
        okno.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
            }
        });
        okno.setResizable(false);
        createBufferStrategy(2);
        strategy = getBufferStrategy();
        requestFocus();
        addKeyListener(this);
        BufferedImage cursor = spriteCache.createCompatible(10,10,Transparency.BITMASK);
        Toolkit t = Toolkit.getDefaultToolkit();
        Cursor c = t.createCustomCursor(cursor,new Point(5,5),"null");
        setCursor(c);
        setIgnoreRepaint(true);
        monstersKilled = 0;
    }


    private void initWorld(){
        actors = new ArrayList<>();
        actorsToAdd = new ArrayList<>();

        for (int i=0; i<10; i++){
            Monster m = new Monster(this);
            m.setX((int) (Math.random()*Stage.WINDOW_WIDTH -35));
            m.setY(i*20);
            m.setVx((int)(Math.random()*3)+1);
            actors.add(m);
        }

        player = new Player(this);
        player.setX(Stage.WINDOW_WIDTH /2);
        player.setY(Stage.WINDOW_HEIGHT -2*player.getHeight());
        actors.add(player);


        backgroundTile = spriteCache.getSprite("Tlo.png");
        background = spriteCache.createCompatible(
                Stage.WINDOW_WIDTH,
                Stage.WINDOW_HEIGHT +backgroundTile.getHeight(),
                Transparency.OPAQUE);

        Graphics2D g = (Graphics2D)background.getGraphics();
        g.setPaint(new TexturePaint(backgroundTile,new Rectangle(0,0,backgroundTile.getWidth(),backgroundTile.getHeight())));
        g.fillRect(0,0,background.getWidth(),background.getHeight());
        backgroundY=backgroundTile.getHeight();
    }

    public void paintWorld() {
        Graphics2D g = (Graphics2D) strategy.getDrawGraphics();
        g.drawImage(background
                ,0,0,Stage.WINDOW_WIDTH,Stage.WINDOW_HEIGHT,
                0,(int)backgroundY,Stage.WINDOW_WIDTH,(int)backgroundY+Stage.WINDOW_HEIGHT,this);
        for (int i = 0; i < actors.size(); i++) {
            Actor m = (Actor)actors.get(i);
            m.paint(g);
        }
        player.paint(g);
        paintStatus(g);
        strategy.show();
    }

    public Player getPlayer() {return player;}

    public void updateWorld(){
        int i = 0;

        while (i < actors.size()){
            Actor actor = (Actor)actors.get(i);

            if (actor.isMarkedForRemoval()){
                actors.remove(i);
            }
            else {
                actor.act();
                i++;
            }
        }

        player.act();
    }

    public SpriteCache getSpriteCache(){
        return spriteCache;
    }

    public void game() {
        usedTime = 1000;
        initWorld();

        long prevLoopTime = System.currentTimeMillis();

        while (isVisible() && !gameEnded){
            long loopStartTime = System.currentTimeMillis();

            float dt = (System.currentTimeMillis()-prevLoopTime)/1000f;
            prevLoopTime = System.currentTimeMillis();

            swipeBackground(dt);
            updateWorld();
            checkCollisions();
            updateActorsList();
            paintWorld();

            usedTime = System.currentTimeMillis()-loopStartTime;

            do {
                Thread.yield();
            }
            while (System.currentTimeMillis()-loopStartTime < 17);
        }

        paintGameOver();
    }

    private void updateActorsList() {
        for (int i = 0; i < actors.size(); i++) {
            if (actors.get(i).isMarkedForRemoval()) {
                actors.remove(i);
            }
        }

        actors.addAll(actorsToAdd);
        actorsToAdd.clear();
    }

    private void swipeBackground(float dt) {
        backgroundY -= 20*dt;

        if (backgroundY < 0){
            backgroundY = backgroundTile.getHeight();
        }
    }

    public void keyPressed(KeyEvent e){
        player.keyPressed(e);
    }
    public void keyReleased(KeyEvent e){
        player.keyReleased(e);
    }
    public void keyTyped(KeyEvent e) {}

    public void addActor(Actor a){
        actorsToAdd.add(a);
    }

    public void paintGameOver(){
        Graphics2D g = (Graphics2D) strategy.getDrawGraphics();
        g.setColor(Color.yellow);
        g.setFont(new Font("Arial",Font.BOLD,20));
        g.drawString("!!!GAME OVER!!!",Stage.WINDOW_WIDTH /2-50,Stage.WINDOW_HEIGHT /2);
        strategy.show();
    }

    public void paintShields(Graphics2D g){
        g.setPaint(Color.red);
        g.fillRect(280,Stage.GAME_HEIGHT,Player.MAX_SHIELDS,30);
        g.setPaint(Color.blue);
        g.fillRect(280+Player.MAX_SHIELDS-player.getShields(),Stage.GAME_HEIGHT,player.getShields(),30);
        g.setFont(new Font("Arial",Font.BOLD,20));
        g.setPaint(Color.green);
        g.drawString("Shields",170,Stage.GAME_HEIGHT +20);
    }

    public void paintScore(Graphics2D g){
        g.setFont(new Font("Arial",Font.BOLD,20));
        g.setPaint(Color.green);
        g.drawString("Score",20,Stage.GAME_HEIGHT +20);
        g.setPaint(Color.red);
        g.drawString(player.getScore()+" ",100,Stage.GAME_HEIGHT +20);
    }

    public void paintAmmo(Graphics2D g){
        int xBase = 280+Player.MAX_SHIELDS+10;
        for (int i=0;i<player.getClusterBombs();i++){
            BufferedImage bomb = spriteCache.getSprite("bomb.gif");
            g.drawImage(bomb,xBase+i*bomb.getWidth(),Stage.GAME_HEIGHT,this);
        }
    }

    public void paintfps(Graphics2D g){
        g.setFont(new Font("Arial",Font.BOLD,14));
        g.setColor(Color.yellow);
        if (usedTime>0){
            g.drawString(String.valueOf(1000/usedTime)+" fps",Stage.WINDOW_WIDTH -70,Stage.GAME_HEIGHT);
        }
        else
            g.drawString("--- fps",Stage.WINDOW_WIDTH -70,Stage.GAME_HEIGHT);
    }

    private void paintStatus(Graphics2D g){
        paintScore(g);
        paintShields(g);
        paintAmmo(g);
        paintfps(g);
    }

    private void checkCollisions(){
        for (int i = 0; i < actors.size(); i++){
            Actor actor1 = actors.get(i);
            Rectangle bounds1 = actor1.getBounds();

            for (int j = i + 1; j < actors.size(); j++){
                Actor actor2 = actors.get(j);
                Rectangle bounds2 = actor2.getBounds();

                if (!actor1.isMarkedForRemoval() && !actor2.isMarkedForRemoval() && bounds1.intersects(bounds2)){
                    actor1.collision(actor2);
                    actor2.collision(actor1);
                }
            }
        }
    }

    public void gameOver() {gameEnded = true;}

}