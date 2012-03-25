/**
 * @author Antares Yee
 */

package spreadyourseed;

import com.golden.gamedev.object.Sprite;
import com.golden.gamedev.object.collision.BasicCollisionGroup;


public class PlayerFemaleCollision extends BasicCollisionGroup {
    Level myLevel;
    
    public PlayerFemaleCollision(Level level) {
        myLevel = level;
    }
    
    @Override
    public void collided(Sprite player, Sprite female) {
        myLevel.playerIsHit(player.getX(), player.getY());
        
    }

}
