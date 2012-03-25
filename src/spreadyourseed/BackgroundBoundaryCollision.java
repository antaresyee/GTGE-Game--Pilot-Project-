/**
 * @author Antares Yee
 */

package spreadyourseed;

import com.golden.gamedev.object.Background;
import com.golden.gamedev.object.Sprite;
import com.golden.gamedev.object.collision.CollisionBounds;

public class BackgroundBoundaryCollision extends CollisionBounds {

    public BackgroundBoundaryCollision(Background background) {
        super(background);
    }

    @Override
    public void collided(Sprite sprite) {
        revertPosition1();
        
    }

}
