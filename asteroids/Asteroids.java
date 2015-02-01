package asteroids;

import javax.swing.JFrame;

/**
 * The main class. Contains main Game Loop. 
 * 
 * @author AP
 */
public class Asteroids extends JFrame{
    
    public final double GAME_HERTZ = 30.0;
    public final double TIME_BETWEEN_UPDATES = 1000000000 / GAME_HERTZ;
    public final int MAX_UPDATES_BEFORE_RENDER = 5;
    public final double TARGET_FPS = 60;
    public final double TARGET_TIME_BETWEEN_RENDERS = 1000000000 / TARGET_FPS;
    public final int WIN_SIZE_X = 1024;
    public final int WIN_SIZE_Y = 768;
    
    private boolean running = true;
    public boolean paused = false;
    
    //Class is a Singleton, created at the beginning.
    public final static Asteroids ASTEROIDS = new Asteroids();

    public int fps = 60;
    public int frameCount = 0;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        ASTEROIDS.createFrame();
        ASTEROIDS.runGameLoop();
    }
    
    private Asteroids() {}
    
    /**
     * Creates a game window.
     */
    public void createFrame() {
        setSize(WIN_SIZE_X, WIN_SIZE_Y);
        setTitle("SpaceRace");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setIgnoreRepaint(true);
        setResizable(false);
        setLocationRelativeTo(null);
        add(GamePanel.PANEL);
        setVisible(true);
    }
    
    /**
     * Creates and starts a new Thread for the game loop.
     */
    public void runGameLoop() {
        Thread loop = new Thread() {
            @Override
            public void run() {
                gameLoop();
            }
        };
        loop.start();
    }
   
  
    private void gameLoop() {
        double lastUpdateTime = System.nanoTime();
        double lastRenderTime = System.nanoTime();
        int lastSecondTime = (int)(lastUpdateTime / 1000000000);
      
        while (running) {
            double currentTime = System.nanoTime();
            int updateCount = 0;
         
            if (!paused) {
                while(currentTime - lastUpdateTime > TIME_BETWEEN_UPDATES && updateCount < MAX_UPDATES_BEFORE_RENDER) {
                    updateGame();
                    lastUpdateTime += TIME_BETWEEN_UPDATES;
                    updateCount++;
                }   
                if (currentTime - lastUpdateTime > TIME_BETWEEN_UPDATES) {
                lastUpdateTime = currentTime - TIME_BETWEEN_UPDATES;
            }
         
            double interpolation = Math.min(1.0f, (currentTime - lastUpdateTime) / TIME_BETWEEN_UPDATES);
            drawGame(interpolation);
            lastRenderTime = currentTime;
         
            int thisSecond = (int) (lastUpdateTime / 1000000000);
            if (thisSecond > lastSecondTime) {
                fps = frameCount;
                frameCount = 0;
                lastSecondTime = thisSecond;
            }
         
            while (currentTime - lastRenderTime < TARGET_TIME_BETWEEN_RENDERS && 
                    currentTime - lastUpdateTime < TIME_BETWEEN_UPDATES) {
                
                Thread.yield();
            
                try {Thread.sleep(1);
                } catch(Exception e) {} 
            
                currentTime = System.nanoTime();
            }
         }
      }
   }
    
   private void updateGame() {
      ObjectsController.POOL.updateObjects();
      GamePanel.PANEL.update();
   }
   
   private void drawGame(double interpolation) {
      GamePanel.PANEL.setInterpolation(interpolation);
      GamePanel.PANEL.repaint();
   }
   
}
