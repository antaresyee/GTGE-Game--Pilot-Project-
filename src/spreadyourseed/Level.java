package spreadyourseed;

import com.golden.gamedev.GameEngine;
import com.golden.gamedev.GameObject;


public abstract class Level extends GameObject {
    
    
    public Level(GameEngine gameEngine) {
        super(gameEngine);
    }

    public abstract void addBomb();
    
    public abstract void addDoubleshot();
    
    public abstract void addSpreadshot();
    
    public abstract void playerIsHit(double x, double y);
    
    public abstract void femaleIsHit(double x, double y);
    
    public abstract void bossIsHit(double x, double y);
    
}
