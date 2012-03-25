package spreadyourseed;

import com.golden.gamedev.object.Sprite;
import com.golden.gamedev.object.collision.BasicCollisionGroup;

public class PlayerSpreadshotCollision extends BasicCollisionGroup {
    Level myLevel;
    
    public PlayerSpreadshotCollision(Level level) {
        myLevel = level;
    }
    
    public void collided(Sprite spreadshot, Sprite player) {
        spreadshot.setActive(false);
        myLevel.addSpreadshot();
        
    }
}
