/**
 * @author Antares Yee
 */

package spreadyourseed;

import com.golden.gamedev.object.Sprite;
import com.golden.gamedev.object.collision.BasicCollisionGroup;

public class PlayerFemaleShotCollision extends BasicCollisionGroup {
    Level myLevel;
    
    public PlayerFemaleShotCollision(Level level) {
        myLevel = level;
        }
    
    @Override
    public void collided(Sprite player, Sprite femaleShot) {
        myLevel.playerIsHit(player.getX(), player.getY());
        femaleShot.setActive(false);
        
        
    }

}
