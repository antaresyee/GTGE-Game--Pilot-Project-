package spreadyourseed;

public class PlayerData {
    
    //class used to transfer player data between levels
    
    int myNumLives;
    int myNumBombs;
    boolean myHasDoubleshot;
    boolean myHasSpreadshot;
    
    public PlayerData(int numLives, int numBombs, boolean hasDoubleshot, boolean hasSpreadshot) {
        myNumLives = numLives;
        myNumBombs = numBombs;
        myHasDoubleshot = hasDoubleshot;
        myHasSpreadshot = hasSpreadshot;
    }
    
    //get
    public int getNumLives() {
        return myNumLives;
    }
    
    public int getNumBombs() {
        return myNumBombs;
    }
    
    public boolean hasDoubleshot() {
        return myHasDoubleshot;
    }
    
    public boolean hasSpreadshot() {
        return myHasSpreadshot;
    }
    
    //set
    public void setNumLives(int numLives) {
        myNumLives = numLives;
    }
    
    public void setNumBombs(int numBombs) {
        myNumBombs = numBombs;
    }
    
    public void setHasDoubleshot(boolean hasDoubleshot) {
        myHasDoubleshot = hasDoubleshot;
    }
    
    public void setHasSpreadshot(boolean hasSpreadshot) {
        myHasSpreadshot = hasSpreadshot;
    }
}
