
package asteroids;

import java.awt.Graphics2D;
import java.util.ArrayList;

/**
 *
 * @author AP
 */
public class ObjectsController {
    
    public static final int STARS_COUNT = 3;
    public static final int MIST_COUNT = 3;
    public static final int ASTEROID_COUNT = 8;
    public static final String ASTEROID_NAME = "asteroid";
    public static final String SHIP_NAME = "ship";
    public static final String ROCKET_NAME = "rocket";
    public static final String EXPLOSION_NAME = "explosion";
    public static final String STARS_NAME = "stars";
    public static final String MIST_NAME = "mist";
    
    public boolean gameOver = false;
    public ArrayList<Asteroid> asteroids;
    public ArrayList<Explosion> explosions;
    public ArrayList<Rocket> rockets;
    public Ship ship;
    
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
