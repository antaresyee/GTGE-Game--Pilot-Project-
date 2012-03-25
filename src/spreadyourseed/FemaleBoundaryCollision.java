package spreadyourseed;

import com.golden.gamedev.object.Background;
import com.golden.gamedev.object.Sprite;

public class FemaleBoundaryCollision extends com.golden.gamedev.object.collision.CollisionBounds {

    public FemaleBoundaryCollision(Background background) {
        super(background);
    }

    @Override
    public void collided(Sprite female) {
        if (isCollisionSide(LEFT_COLLISION)) {
            female.setLocation(750,female.getY());
        }
        else if (isCollisionSide(RIGHT_COLLISION)) {
            female.setLocation(0,female.getY());
        }
        
    }
    
}
