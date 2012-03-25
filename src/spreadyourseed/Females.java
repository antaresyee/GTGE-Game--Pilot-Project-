package spreadyourseed;

import com.golden.gamedev.*;
import com.golden.gamedev.object.*;

public class Females {
    SpriteGroup myFemales;
    
    public Females() {
        myFemales = new SpriteGroup("females");
    }
    
    public void initialize(Background b) {
        //myFemales.add(new Sprite(getImage("female_sprite.jpg"),100,100));
        //myFemales.add(new Sprite(getImage("female_sprite.jpg"),100,400));
        //myFemales.add(new Sprite(getImage("female_sprite.jpg"),100,700));
        
        myFemales.setBackground(b);
    }
    
    public SpriteGroup getSpriteGroup() {
        return myFemales;
    }
}
