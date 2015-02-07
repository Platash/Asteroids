package asteroids;

/**
 *
 * @author AP
 */
public class Mist extends BGElement {
    
    private static final int SPRITE_COUNT = 3;
    private static final int SPEED = 8;
    protected final static Mist MIST = new Mist();
    
    private Mist() {
        super(0, 0, 0, SPEED, ObjectsController.MIST_COUNT);
        setActiveImage1(random.nextInt(SPRITE_COUNT));
        setActiveImage2(random.nextInt(SPRITE_COUNT));
        images = Graphics.MISTS;
    }
}
