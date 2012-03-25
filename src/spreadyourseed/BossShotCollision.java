package spreadyourseed;

import com.golden.gamedev.object.Sprite;
import com.golden.gamedev.object.collision.BasicCollisionGroup;

public class BossShotCollision extends BasicCollisionGroup {
    
    Level myLevel;
    
    public BossShotCollision(Level level) {
        myLevel = level;
    }
    
    @Override
    public void collided(Sprite boss, Sprite shot) {
        shot.setActive(false);
        myLevel.bossIsHit(boss.getX(), boss.getY());
        
    }

}
