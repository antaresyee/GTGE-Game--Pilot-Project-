package spreadyourseed;

import com.golden.gamedev.object.Sprite;
import com.golden.gamedev.object.collision.BasicCollisionGroup;

public class BossPlayerCollision extends BasicCollisionGroup {
    Level myLevel;
    
    public BossPlayerCollision(Level level) {
        myLevel = level;
    }
    
    @Override
    public void collided(Sprite boss, Sprite player) {
        myLevel.playerIsHit(player.getX(), player.getY());
        
    }
}
