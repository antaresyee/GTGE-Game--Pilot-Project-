package spreadyourseed;

import com.golden.gamedev.GameObject;
import com.golden.gamedev.object.Sprite;
import com.golden.gamedev.object.collision.BasicCollisionGroup;

public class DoubleshotPlayerCollision extends BasicCollisionGroup {
    
    Level myLevel;
    
    DoubleshotPlayerCollision(Level level) {
        myLevel = level;
    }
    
    @Override
    public void collided(Sprite doubleshot, Sprite player) {
        doubleshot.setActive(false);
        myLevel.addDoubleshot();
        
    }

}
