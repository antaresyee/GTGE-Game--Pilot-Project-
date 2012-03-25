package spreadyourseed;

import com.golden.gamedev.object.Sprite;
import com.golden.gamedev.object.collision.BasicCollisionGroup;


public class FemaleShotCollision extends BasicCollisionGroup {
    Level myLevel;
    
    public FemaleShotCollision(Level level) {
        myLevel = level;
    }
    @Override
    public void collided(Sprite female, Sprite shot) {
        female.setActive(false);
        shot.setActive(false);
        myLevel.femaleIsHit(female.getX(), female.getY());
        
        
    }

}
