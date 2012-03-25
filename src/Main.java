/**
 * @author Antares Yee
 */

import java.awt.Dimension;
import spreadyourseed.LevelOne;
import spreadyourseed.SpreadYourSeed;

import com.golden.gamedev.GameLoader;

/**
 * ***WARNING: MAD UGLY CODE***
 */

public class Main {
    public static void main (String[] args)
    {
        GameLoader game = new GameLoader();
        game.setup(new SpreadYourSeed(), new Dimension(800,600), false);
        game.start();
    }
    
    //TODO: implement Boss Music, implement boss shots, implement boss special attack, implement scrolling background
    //FINAL: fix num waves in level 2, fix hp of boss
    
    /*design: create Female class that includes myFemales and myFemaleShots sprite groups with
     * methods updateShot(), etc.  Collisions will be separate.
     */
}
