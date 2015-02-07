package asteroids;

/**
 * Background Stars class
 * @author AP
 */
public class Stars extends BGElement {
    
    private static final int SPRITE_COUNT = 3;
    private static final int SPEED = 4;
    protected final static Stars STARS = new Stars();
    
    private Stars() {
        super(0, 0, 0, SPEED, ObjectsController.STARS_COUNT);
        setActiveImage1(random.nextInt(SPRITE_COUNT));
        setActiveImage2(random.nextInt(SPRITE_COUNT));
        images = Graphics.STARS;
    }
}
