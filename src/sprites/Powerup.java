package sprites;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Antares Yee
 */
import com.golden.gamedev.engine.BaseLoader;
import com.golden.gamedev.object.Background;
import com.golden.gamedev.object.Sprite;
import com.golden.gamedev.object.SpriteGroup;
import com.golden.gamedev.object.Timer;


public class Powerup {
    Timer myPowerupsRate;
    SpriteGroup myBombs;
    SpriteGroup myDoubleshots;
    SpriteGroup mySpreadshots;
    
    BufferedImage myBombImage;
    BufferedImage myDoubleshotImage;
    BufferedImage mySpreadshotImage;
    
    public Powerup(Background b, BufferedImage bombImage, BufferedImage doubleshotImage, BufferedImage spreadshotImage) {
        myBombImage = bombImage;
        myDoubleshotImage = doubleshotImage;
        mySpreadshotImage = spreadshotImage;
        
        myBombs = new SpriteGroup("bombs");
        myBombs.setBackground(b);
        myDoubleshots = new SpriteGroup("doubleshots");
        myDoubleshots.setBackground(b);
        mySpreadshots = new SpriteGroup("spreadshots");
        myDoubleshots.setBackground(b);
        
        int randRate = 7000 + (int)(Math.random() * ((15000 - 7000) + 1)); //rand between 7000-15000
        myPowerupsRate = new Timer(randRate);
        
    }
    
    public void update(long elapsedTime) {
        //make powerup
        if (myPowerupsRate.action(elapsedTime)) {
            int randX = 0 + (int)(Math.random() * ((500 - 0) + 1)); //generates random num between 0, 500
            int powerupType = 1 + (int)(Math.random() * ((3 - 1) + 1));
            
            if (powerupType == 1) {
                Sprite bomb = new Sprite(myBombImage, randX, 0);
                myBombs.add(bomb);
                bomb.setVerticalSpeed(.07);
            }
            
            if (powerupType == 2) {
                Sprite doubleshot = new Sprite(myDoubleshotImage, randX, 0);
                myDoubleshots.add(doubleshot);
                doubleshot.setVerticalSpeed(.07);   
            }
            
            if (powerupType == 3) {
                Sprite spreadshot = new Sprite(mySpreadshotImage, randX, 0);
                mySpreadshots.add(spreadshot);
                spreadshot.setVerticalSpeed(.07);
            }
            
            //update timer
            int randRate = 7000 + (int)(Math.random() * ((15000 - 7000) + 1)); //random between 7000 15000
            myPowerupsRate = new Timer(randRate);
        }
        
        
    }
    
    public List<SpriteGroup> getGroup() {
        List<SpriteGroup> list = new ArrayList<SpriteGroup>();
        list.add(myBombs);
        list.add(myDoubleshots);
        list.add(mySpreadshots);
        return list;
    }
    
    
}
