package spreadyourseed;

import com.golden.gamedev.object.Sprite;
import com.golden.gamedev.object.collision.BasicCollisionGroup;

public class BombPlayerCollision extends BasicCollisionGroup {
    Level myLevel;
    
    public BombPlayerCollision(Level level) {
        myLevel = level;
    }
    
    @Override
    public void collided(Sprite bomb, Sprite player) {
        bomb.setActive(false);
        myLevel.addBomb();
        
    }

}
