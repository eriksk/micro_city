package microcity;
 
import microcity.utils.Color;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import static org.lwjgl.opengl.GL11.*;

public abstract class GameBase {
     
    DeltaTimer delta;
    protected final int width;
    protected final int height;
    private final boolean isFullScreen;
    private final String title;
    private boolean closeRequested;

    /**
     *
     * @param width
     * @param height
     * @param isFullScreen
     * @param title
     */
    public GameBase(int width, int height, boolean isFullScreen, String title) {
        this.width = width;
        this.height = height;
        this.isFullScreen = isFullScreen;
        this.title = title;
        closeRequested = false;
    }
 
    public void run() { 
        init();
        loop();
    }
    
    public void exit(){
        closeRequested = true;
    }
    
    private void init() {
        delta = new DeltaTimer();
        try {
            Display.setDisplayMode(new DisplayMode(width, height));
            Display.create();
        } catch (LWJGLException e) {
            e.printStackTrace();
            System.exit(0);
        }
        load();
    }
 
    private void loop() {
         
        while (!(closeRequested || Display.isCloseRequested())) {
            delta.updateFPS();
            float dt = delta.getDelta();
            Display.setTitle(title + " " + delta.getFps());
            
            update(dt);
             
            render();
            Display.update();
        }
         
        Display.destroy();
    }
    
    public void clear(Color color){
        glClearColor(color.r, color.g, color.b, color.a);
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        
    }

    public abstract void load();
    public abstract void update(float dt);
    public abstract void render();
}
