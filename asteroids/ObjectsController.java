
package asteroids;

import java.awt.Graphics2D;
import java.util.ArrayList;

/**
 *
 * @author AP
 */
public class ObjectsController {
    
    protected static final int STARS_COUNT = 3;
    protected static final int MIST_COUNT = 3;
    protected static final int ASTEROID_COUNT = 8;
    protected static final String ASTEROID_NAME = "asteroid";
    protected static final String SHIP_NAME = "ship";
    protected static final String ROCKET_NAME = "rocket";
    protected static final String EXPLOSION_NAME = "explosion";
    protected static final String STARS_NAME = "stars";
    protected static final String MIST_NAME = "mist";
    
    protected boolean gameOver = false;
    protected ArrayList<Asteroid> asteroids;
    protected ArrayList<Explosion> explosions;
    protected ArrayList<Rocket> rockets;
    protected Ship ship;
    
     //Class is a Singleton, created at the beginning.
    public final static ObjectsController POOL = new ObjectsController();
    
    private ObjectsController() {
        explosions = new ArrayList<>();
        ship = new Ship(0);
        initAsteroids();
        initRockets();
    }
    
    private void initAsteroids() {
        asteroids = new ArrayList<>();
        for(int i = 0; i < ASTEROID_COUNT; i++) {
            asteroids.add(new Asteroid(i));
        }
    }
    
    private void initRockets() {
        rockets = new ArrayList<>();
    }
    
    public void updateObjects() {
        Stars.STARS.move(GamePanel.PANEL.acceleration);
        Mist.MIST.move(GamePanel.PANEL.acceleration);
        updateRockets();
        updateAsteroids();
        updateShip();
        updateExplosions();
    }
    
    private void updateShip() { 
        if(ship.alive) {
            for(int i = 0; i < asteroids.size(); i++) {
                if(ship.checkCollision(asteroids.get(i))) {
                    ship.reactOnCollision(asteroids.get(i));
                }
            }
            ship.move(GamePanel.PANEL.acceleration);
        } else if (!gameOver){
            explosions.add(new Explosion(64, ship.getPosX(), ship.getPosY()));
            gameOver = true;
        }
    }
    
    private void updateAsteroids() {
        for(int i = asteroids.size() - 1; i > 0; i--) {
            for(int j = 0; j < i; j++) {
                if(asteroids.get(i).checkCollision(asteroids.get(j))) {
                    asteroids.get(i).reactOnCollision(asteroids.get(j));
                }
            }
            asteroids.get(i).move(GamePanel.PANEL.acceleration);
            //If asteroid flies off the screen, it's being initiated with a new settings
            if(asteroids.get(i).offScreen()) {
                asteroids.get(i).init();
                
            }
        }
    }
    
    private void updateRockets() {
        for(int i = 0; i < rockets.size(); i++) {
            for(int j = 0; j < asteroids.size(); j++) {
                if (rockets.get(i).checkCollision(asteroids.get(j))) {
                    explosions.add(new Explosion(asteroids.get(j).getRadius(), 
                            asteroids.get(j).getPosX(), asteroids.get(j).getPosY()));
                    
                    rockets.get(i).kill();
                    asteroids.get(j).kill();
                }
            }
            if(rockets.get(i).alive) {
                rockets.get(i).move(GamePanel.PANEL.acceleration);
            } else {
                rockets.remove(i);
            }
        }
    }
    
    private void updateExplosions() {
        for(int i = 0; i < explosions.size(); i++) {
            if(!explosions.get(i).alive) { 
                explosions.remove(i);
            }
        }
    }
    
    public void shoot(Graphics2D g2, int x, int y) {
        rockets.add(new Rocket(rockets.size(), ship.getPosX(), ship.getPosY(), x, y));
   }
    
    public Asteroid getAsteroid(int num) {
        return asteroids.get(num);
    }
    
    public int getAsteroidCount() {
        return asteroids.size();
    }

    public Ship getShip() {
        return ship;
    }

    public ArrayList<Asteroid> getAsteroids() {
        return asteroids;
    }

    public ArrayList<Explosion> getExplosions() {
        return explosions;
    }
    
    public Explosion getExplosion(int num) {
        return explosions.get(num);
    }

    public ArrayList<Rocket> getRockets() {
        return rockets;
    }
}
