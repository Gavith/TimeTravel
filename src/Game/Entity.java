package Game;

public class Entity {
    //This array holds a whole bunch of x and y coordinates regarding the entity's position. To get the position in the nth timezone, the indexes are n*2-2 and n*2-1
    //positions[0], positions[1] is the location of the entity in the first timezone. In the 4th timezone, positions[6] and positions[7] are the relevant indexes.
    public static float[] positions;
    public float xSpeed;
    public float ySpeed;

    public static void init(float startX, float startY, int amountOfTimezones) {
        positions = new float[amountOfTimezones];
        //Fill array with starting positions
        for (int i = 0; i < amountOfTimezones*2; i +=2) {
            positions[i]=startX;
            positions[i+1]=startY;
        }
    }

}
