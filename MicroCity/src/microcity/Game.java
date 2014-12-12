/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package microcity;

import java.nio.FloatBuffer;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import microcity.buffers.FrameBuffer;
import microcity.lighting.GlobalLight;
import microcity.meshes.SkyBox;
import microcity.metrics.Stopwatch;
import microcity.noise.Noise;
import microcity.shaders.Shader;
import microcity.utils.Color;
import microcity.utils.Utils;
import microcity.voxels.VoxelRenderer;
import microcity.voxels.VoxelWorld;
import org.lwjgl.BufferUtils;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import static org.lwjgl.opengl.GL11.*;
import org.lwjgl.util.glu.GLU;
import org.lwjgl.util.vector.Vector3f;

/**
 *
 * @author Erik
 */
public class Game extends GameBase {

    VoxelWorld world;
    VoxelRenderer renderer;
    SkyBox skybox;

    GlobalLight globalLight;

    Camera cam;
    FPSCameraController camController;

    Shader lightShader;
    Shader dofShader;
    FrameBuffer frameBuffer;

    public Game(int width, int height, boolean isFullScreen, String title) {
        super(width, height, isFullScreen, title);
    }

    @Override
    public void load() {
        cam = new Camera(0, -34, -64);
        // cam.pitch(30f);
        // cam.yaw(135);
        camController = new FPSCameraController(cam);
        Mouse.setGrabbed(true);

        initGl();

        globalLight = new GlobalLight();
        globalLight.position.x = -200;
        globalLight.position.z = -200;
        globalLight.position.y = 700;
        globalLight.ambient = new Color(1f, 1f, 1f, 1f);
        globalLight.specular = new Color(1f, 1f, 1f, 1f);
        globalLight.diffuse = new Color(0.6f, 0.6f, 0.6f, 1f);

        globalLight.init();

        world = new VoxelWorld();
        renderer = new VoxelRenderer(world);

        lightShader = new Shader("SSAO.vertex", "SSAO.fragment");
        lightShader.load();
        dofShader = new Shader("dof.vertex", "dof.fragment");
        dofShader.load();

        frameBuffer = new FrameBuffer(width, height);
        frameBuffer.load();

        skybox = new SkyBox();

        generateWorld();
    }

    private void initGl() {
        glEnable(GL_DEPTH_TEST);
        glEnable(GL_STENCIL_TEST);

        glDepthFunc(GL_LEQUAL);
        glEnableClientState(GL_VERTEX_ARRAY);
        glEnableClientState(GL_COLOR_ARRAY);
        glEnableClientState(GL_NORMAL_ARRAY);
        glShadeModel(GL_SMOOTH);
        glEnable(GL_COLOR_MATERIAL);
        glHint(GL_PERSPECTIVE_CORRECTION_HINT, GL_NICEST);         // Set Perspective Calculations To Most Accurate

        glEnable(GL_TEXTURE_2D);
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

        //glEnable(GL_CULL_FACE);
    }

    private void generateWorld() {



        generateIsland(0, 0, 0);
        
        
        
        /*
         new Thread(new Runnable() {

         @Override
         public void run() {
         int size = 512;
         float[][] noise = Noise.noise(size, size);

         Stopwatch stopwatch = new Stopwatch();
         stopwatch.start();

         Random r = new Random(System.currentTimeMillis());

         for (int i = 0; i < size; i++) {
         for (int j = 0; j < size; j++) {
         int height = (int) Utils.lerp(0, 128, noise[i][j] * 0.4f);

         for (int h = 0; h < height; h++) {
         int type = 1;
         if (h > 10) {
         type++;
         }
         if (h > 20 + r.nextInt(6)) {
         type++;
         }
         if (h > 30 + r.nextInt(6)) {
         type++;
         }
         if (h > 60 + r.nextInt(10)) {
         type++;
         }
         world.set(i, h, j, type);
         }
         }
         }

         stopwatch.end();

         System.out.println("Generation took: " + stopwatch.time() / 1000f + " seconds.");
         System.out.println("Total cubes: " + world.getCubeCount());
         /*
         }
         }).start();
         */
    }

    private void generateIsland(int islandX, int islandY, int islandZ) {
        Random r = new Random(System.currentTimeMillis());

        int islandWidth = 32 + r.nextInt(32);
        int islandHeight = 32 + r.nextInt(32);

        float maxHeight = 96;
        int overlapHeight = 12;

        float[][] noise = Noise.noise(islandWidth, islandHeight);

        for (int x = 0; x < islandWidth; x++) {
            for (int z = 0; z < islandHeight; z++) {
                float n = (noise[x][z] - 0.2f) * 1.5f;
                int height = (int) (n * maxHeight);

                for (int i = 0; i < height; i++) {

                    int type = 0;
                    if (i + r.nextInt(overlapHeight) > (maxHeight * 0.2f)) {
                        type++;
                    }
                    if (i + r.nextInt(overlapHeight) > (maxHeight * 0.4f)) {
                        type++;
                    }
                    if (i + r.nextInt(overlapHeight) > (maxHeight * 0.6f)) {
                        type++;
                    }
                    if (i + r.nextInt(overlapHeight) > (maxHeight * 0.8f)) {
                        type++;
                    }
                    world.set(islandX + x, islandY + i, islandZ + z, type);
                }
            }
        }

        /*
        int removeHeight = (int) (maxHeight / 3);

        for (int currentHeight = removeHeight; currentHeight >= 0; currentHeight--) {
            float progress = (float) currentHeight / (float) removeHeight;

            for (int x = 0; x < islandWidth; x++) {
                for (int z = 0; z < islandHeight; z++) {
                    if (r.nextFloat() > progress && r.nextBoolean()) {
                        world.set(islandX + x, islandY + currentHeight, islandZ + z, 0);
                    }
                }
            }
        }*/

    }

    boolean keyreturn, oldKeyReturn;

    @Override
    public void update(float dt) {
        oldKeyReturn = keyreturn;
        keyreturn = Keyboard.isKeyDown(Keyboard.KEY_RETURN);

        camController.update(dt);

        if (Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
            exit();
        }

        if (keyreturn && !oldKeyReturn) {
            //System.out.println(world.getLogInfo());
            try {
                dofShader.load();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            /*
             int x = -(int)(cam.position.x / world.blockSize);
             int y = -(int)(cam.position.y / world.blockSize);
             int z = -(int)(cam.position.z / world.blockSize);
         
             world.set(x, y, z, 3);
             */
            //generateWorld();
        }

        renderer.update(dt);
    }

    @Override
    public void render() {
        clear(Color.CornflowerBlue());

        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        gluPerspective(60.0f, (float) width / (float) height, 0.1f, 1000f);

        glMatrixMode(GL_MODELVIEW);
        glLoadIdentity();

        frameBuffer.begin();

        cam.lookThrough();

        globalLight.apply();

        skybox.render(cam);

        lightShader.begin();
        renderer.render();
        lightShader.end();

        dofShader.begin();
        frameBuffer.render(dofShader.getProgram());
        dofShader.end();
    }

    void gluPerspective(float fovy, float aspect, float zNear, float zFar) {
        float xmin, xmax, ymin, ymax;

        ymax = zNear * (float) Math.tan(fovy * (float) Math.PI / 360.0);
        ymin = -ymax;
        xmin = ymin * aspect;
        xmax = ymax * aspect;

        glFrustum(xmin, xmax, ymin, ymax, zNear, zFar);
    }
}
