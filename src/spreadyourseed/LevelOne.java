/**
 * @author Antares Yee
 */

package spreadyourseed;


import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;

import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import sprites.Powerup;

import com.golden.gamedev.GameEngine;
import com.golden.gamedev.GameObject;
import com.golden.gamedev.object.Background;
import com.golden.gamedev.object.PlayField;
import com.golden.gamedev.object.Sprite;
import com.golden.gamedev.object.SpriteGroup;
import com.golden.gamedev.object.Timer;
import com.golden.gamedev.object.background.ImageBackground;
import com.golden.gamedev.object.collision.CollisionBounds;
import com.golden.gamedev.object.font.SystemFont;

public class LevelOne extends Level {

    PlayField myPlayfield;
    //game
    PlayerData myPlayerData;
    static int myNumLives;
    int myWavesLeft;
    static SystemFont mySystemFont;
    static boolean myGameOver;
    //player and shots
    SpriteGroup myPlayers;
    static Sprite myPlayer;
    SpriteGroup myShots;
    Timer myFireRate;
    boolean myCanFire;
    //females
    SpriteGroup myFemales;
    Timer myFemaleMoveRate;
    SpriteGroup myFemaleShots;
    Timer myFemaleFireRate;
    int myEnemyLocation;
    int myNumEnemies;
    double myFemaleSpeed;
    Timer myFemaleRespawnTimer;
    SpriteGroup myExplosions;
    Timer myEndExplosionTimer;
    //powerups
    Powerup myPowerup;
    public static int myNumBombs;
    boolean myHasDoubleshot;
    boolean myHasSpreadshot;
    //background
    Background myBackground;
    
    public LevelOne(GameEngine gameEngine, PlayerData playerData) {
        super(gameEngine);
        myPlayerData = playerData;
    }
    
    @Override
    public void initResources() {
        myBackground = new ImageBackground(getImage("fat_squirrel.jpg"));
        
        //music
        playMusic("thriller.mid");
        //game
        myWavesLeft = 3;
        myNumLives = myPlayerData.getNumLives();
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        Font[] fonts = ge.getAllFonts(); 
        Font font = fonts[0].deriveFont((float) 30.00);
        mySystemFont = new SystemFont(font);
        myGameOver = false;
        
        //player
        myPlayer = new Sprite(getImage("squirrel_sprite.jpg"),450,470);
        myPlayer.setVerticalSpeed(-.1);
        myPlayers = new SpriteGroup("players");
        myPlayers.add(myPlayer);
       
        //shots
        myShots = new SpriteGroup("shots");
        myFireRate = new Timer(1000);
        myCanFire = true;
        
        //females
        myFemales = new SpriteGroup("females");
        myFemales.add(new Sprite(getImage("female_sprite_2.jpg"),150,10));
        myFemales.add(new Sprite(getImage("female_sprite_2.jpg"),300,10));
        myFemales.add(new Sprite(getImage("female_sprite_2.jpg"),470,10));
        myFemaleMoveRate = new Timer(3000);
        myFemaleSpeed = .05;
        myEnemyLocation = 150;
        myNumEnemies = 2;
        
        
        //female shots
        myFemaleShots = new SpriteGroup("female shots");
        myFemaleFireRate = new Timer(1500);
        
        //explosions
        myExplosions = new SpriteGroup("explosions");
        
        //powerups
        BufferedImage bombImage = getImage("bomb_sprite.jpg");
        BufferedImage doubleshotImage = getImage("doubleshot_sprite.png");
        BufferedImage spreadshotImage = getImage("spreadshot_sprite.jpeg");
        myPowerup = new Powerup(myBackground, bombImage, doubleshotImage, spreadshotImage);
        myHasDoubleshot = myPlayerData.hasDoubleshot();
        myHasSpreadshot = myPlayerData.hasSpreadshot();
        
        //set background
        myPlayers.setBackground(myBackground);
        myFemales.setBackground(myBackground);
        myShots.setBackground(myBackground);
        myFemaleShots.setBackground(myBackground);
        
        
        //set playfield
        myPlayfield = new PlayField(myBackground);
        myPlayfield.addGroup(myPlayers);
        myPlayfield.addGroup(myShots);
        myPlayfield.addGroup(myFemales);
        myPlayfield.addGroup(myFemaleShots);
        myPlayfield.addGroup(myExplosions);
        List<SpriteGroup> powerupGroups = myPowerup.getGroup();
        for (SpriteGroup sg : powerupGroups) {
            myPlayfield.addGroup(sg);
        }
        myPlayfield.addCollisionGroup(myPlayers, myFemales, new PlayerFemaleCollision(this));
        myPlayfield.addCollisionGroup(myFemales, myShots, new FemaleShotCollision(this));
        myPlayfield.addCollisionGroup(myPlayers, myFemaleShots, new PlayerFemaleShotCollision(this));
        myPlayfield.addCollisionGroup(powerupGroups.get(0), myPlayers, new BombPlayerCollision(this));
        myPlayfield.addCollisionGroup(powerupGroups.get(1), myPlayers, new DoubleshotPlayerCollision(this));
        myPlayfield.addCollisionGroup(powerupGroups.get(2), myPlayers, new PlayerSpreadshotCollision(this));
        myPlayfield.addCollisionGroup(myPlayers, null, new BackgroundBoundaryCollision(myBackground));
        
        
    }


    @Override
    public void render(Graphics2D g) {
        myPlayfield.render(g);
        drawGameOver(g);
        drawNumBombs(g);
        drawNumLives(g);
        drawLevel(g);
    }


    @Override
    public void update(long elapsedTime) {
        myPlayfield.update(elapsedTime);
        
        //myHero movement
        double speedX = 0, speedY = 0;
        if (keyDown(KeyEvent.VK_LEFT))  speedX = -0.2;
        if (keyDown(KeyEvent.VK_RIGHT)) speedX =  0.2;
        if (keyDown(KeyEvent.VK_UP))    speedY = -0.2;
        if (keyDown(KeyEvent.VK_DOWN))  speedY =  0.2;
        myPlayer.setSpeed(speedX, speedY);
        
        //powerup usage
        if (keyPressed(KeyEvent.VK_B) && myPlayer.isActive() && myNumBombs > 0) {
            for (Sprite female : myFemales.getSprites()) {
                if (female != null && female.isActive()) {
                    female.setActive(false);
                    femaleIsHit(female.getX(), female.getY());
                }
            }
            playSound("explosion.wav");
            myNumBombs--;
        }
        
        //shots
        if (myCanFire == false) {
            myCanFire = myFireRate.action(elapsedTime);
        }
        
      //spreadshot + doubleshot
        if (myCanFire && myPlayer.isActive() && myHasSpreadshot && myHasDoubleshot && keyDown(KeyEvent.VK_SPACE)) {
            Sprite shot1 = new Sprite(getImage("sperm_sprite.jpg"),myPlayer.getX() - 10, myPlayer.getY());
            Sprite shot2 = new Sprite(getImage("sperm_sprite.jpg"),myPlayer.getX() + 15, myPlayer.getY());
            Sprite shot3 = new Sprite(getImage("sperm_sprite.jpg"),myPlayer.getX() - 10, myPlayer.getY());
            Sprite shot4 = new Sprite(getImage("sperm_sprite.jpg"),myPlayer.getX() + 15, myPlayer.getY());
            shot1.setVerticalSpeed(-.35);
            shot2.setMovement(.35, 20);
            shot3.setMovement(.35, 340);
            shot4.setVerticalSpeed(-.35);
            myShots.add(shot1);
            myShots.add(shot2);
            myShots.add(shot3);
            myShots.add(shot4);
            playSound("shoot.wav");
            myCanFire = false;
        }
        
        //doubleshot
        else if (myCanFire && myPlayer.isActive() && myHasDoubleshot && keyDown(KeyEvent.VK_SPACE)) {
            Sprite shot1 = new Sprite(getImage("sperm_sprite.jpg"),myPlayer.getX() - 10, myPlayer.getY());
            Sprite shot2 = new Sprite(getImage("sperm_sprite.jpg"),myPlayer.getX() + 15, myPlayer.getY());
            shot1.setVerticalSpeed(-.35);
            shot2.setVerticalSpeed(-.35);
            myShots.add(shot1);
            myShots.add(shot2);
            playSound("shoot.wav");
            myCanFire = false;
        }
        
        //spreadshot
        else if (myCanFire && myPlayer.isActive() && myHasSpreadshot && keyDown(KeyEvent.VK_SPACE)) {
            Sprite shot1 = new Sprite(getImage("sperm_sprite.jpg"),myPlayer.getX(), myPlayer.getY());
            Sprite shot2 = new Sprite(getImage("sperm_sprite.jpg"),myPlayer.getX() + 15, myPlayer.getY());
            Sprite shot3 = new Sprite(getImage("sperm_sprite.jpg"),myPlayer.getX() - 15, myPlayer.getY());
            shot1.setVerticalSpeed(-.35);
            shot2.setMovement(.35, 20);
            shot3.setMovement(.35, 340);
            myShots.add(shot1);
            myShots.add(shot2);
            myShots.add(shot3);
            playSound("shoot.wav");
            myCanFire = false;
        }
        
        //normal shot
        else if (myCanFire && myPlayer.isActive() && keyDown(KeyEvent.VK_SPACE)) {
            Sprite shot = new Sprite(getImage("sperm_sprite.jpg"),myPlayer.getX(), myPlayer.getY());
            shot.setVerticalSpeed(-.35);
            myShots.add(shot);
            playSound("shoot.wav");
            myCanFire = false;
        }
        
        //female spawn and check if level won
        boolean allDead = true;
        for (Sprite female : myFemales.getSprites()) {
            if (female != null && female.isActive()) allDead = false;
        }
        if (allDead && myWavesLeft > 0) {
            for (int i=0; i<myNumEnemies; i++) {
                Sprite newFemale = new Sprite(getImage("female_sprite_2.jpg"),myEnemyLocation ,-100);
                myFemales.add(newFemale);
                newFemale.setVerticalSpeed(.05);
                myEnemyLocation += 117;
            }
            //respawn
            Sprite f1 = new Sprite(getImage("female_sprite_2.jpg"),150,-100);
            Sprite f2 = new Sprite(getImage("female_sprite_2.jpg"),300,-100);
            Sprite f3 = new Sprite(getImage("female_sprite_2.jpg"),470,-100);
            myFemales.add(f1);
            myFemales.add(f2);
            myFemales.add(f3);
            f1.setVerticalSpeed(.05);
            f2.setVerticalSpeed(.05);
            f3.setVerticalSpeed(.05);
            
            myFemaleRespawnTimer = new Timer(4000);
            myEnemyLocation = 150;
            myNumEnemies++;
            myWavesLeft--;
        }
        //if won level
        else if (allDead && myWavesLeft == 0) {
            //update data for next level
            myPlayerData.setNumLives(myNumLives);
            myPlayerData.setNumBombs(myNumBombs);
            myPlayerData.setHasDoubleshot(myHasDoubleshot);
            myPlayerData.setHasSpreadshot(myHasSpreadshot);
            
            //got to next level
            parent.nextGameID = 1;
            finish();
        }
        
        //stop moving down after respawn
        if (myFemaleRespawnTimer != null && myFemaleRespawnTimer.action(elapsedTime)) {
            for (Sprite female : myFemales.getSprites()) {
                if (female != null && female.isActive()) {
                    female.setVerticalSpeed(0);
                }
            }
            myFemaleRespawnTimer = null;
        }
        
        
        
        //myFemales movement
        if (myFemaleMoveRate.action(elapsedTime)) {
            for (Sprite female : myFemales.getSprites()) {
                if (female != null && female.isActive()) {
                    female.setHorizontalSpeed(myFemaleSpeed);
                }
            }
            myFemaleSpeed *= -1;
        }
        
        //female shots
        if (myFemaleFireRate.action(elapsedTime)) {
            for (Sprite female : myFemales.getSprites()) {
                if (female != null && female.isActive()) {
                    Sprite femaleShot = new Sprite(getImage("acorn_sprite.png"),female.getX(), female.getY());
                    myFemaleShots.add(femaleShot);
                    femaleShot.setVerticalSpeed(.15);
                }
            }
        }
        
        //female explosions
        if (myEndExplosionTimer != null && myEndExplosionTimer.action(elapsedTime)) {
            for (Sprite explosion : myExplosions.getSprites()) {
                if (explosion != null && explosion.isActive()) {
                    explosion.setActive(false);
                }
            }
        }
        
        //powerups
        myPowerup.update(elapsedTime);
        
   
    }
    
    public void addBomb() {
        myNumBombs++;
    }
    
    public void addDoubleshot() {
        myHasDoubleshot = true;
    }
    
    public void addSpreadshot() {
        myHasSpreadshot = true;
    }
    
    public void playerIsHit(double x, double y) {
        Sprite explosion = new Sprite(getImage("explosion.png"), x, y);
        myExplosions.add(explosion);
        myEndExplosionTimer = new Timer(1000);
        if (myNumLives > 0) {
            myPlayer.setLocation(450,470);
            myHasDoubleshot = false;
            myHasSpreadshot = false;
            myNumLives--;
            return;
        }
        else {
            myPlayer.setActive(false);
            myGameOver = true;
        }
    }
    
    public void femaleIsHit(double x, double y) {
        Sprite explosion = new Sprite(getImage("inception_sprite_2.jpeg"), x, y);
        myExplosions.add(explosion);
        myEndExplosionTimer = new Timer(1000);
        
    }
    
    //no boss
    public void bossIsHit(double x, double y) {
        return;
    }
    
    public void drawGameOver(Graphics2D g) {
        if (myGameOver == true) {
            playSound("gameover.wav");
            mySystemFont.drawString(g, "game over", 300, 300);
        }
    }
    
    public void drawNumBombs(Graphics2D g) {
        mySystemFont.drawString(g, "Bombs: " + Integer.toString(myNumBombs), 0, 60);
        
    }
    
    
    public void drawNumLives(Graphics2D g) {
        mySystemFont.drawString(g, "Lives: " + Integer.toString(myNumLives), 0, 30);
        
    }
    
    public void drawLevel(Graphics2D g) {
        mySystemFont.drawString(g, "Level: " + "1", 0, 0);
    }

}
