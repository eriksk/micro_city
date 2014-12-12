/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package microcity.lighting;

import java.nio.FloatBuffer;
import microcity.utils.Color;
import microcity.utils.Utils;
import static org.lwjgl.opengl.GL11.GL_AMBIENT;
import static org.lwjgl.opengl.GL11.GL_DIFFUSE;
import static org.lwjgl.opengl.GL11.GL_LIGHT0;
import static org.lwjgl.opengl.GL11.GL_LIGHTING;
import static org.lwjgl.opengl.GL11.GL_LIGHT_MODEL_LOCAL_VIEWER;
import static org.lwjgl.opengl.GL11.GL_POSITION;
import static org.lwjgl.opengl.GL11.GL_SPECULAR;
import static org.lwjgl.opengl.GL11.GL_TRUE;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glLight;
import static org.lwjgl.opengl.GL11.glLightModeli;
import org.lwjgl.util.vector.Vector3f;

/**
 *
 * @author Erik
 */
public class GlobalLight {

    public Color ambient, specular, diffuse;
    public Vector3f position;
    
    public GlobalLight() {
        ambient = new Color();
        specular = new Color();
        diffuse = new Color();
        position = new Vector3f();
    }
    
    public void init(){
        glEnable(GL_LIGHTING);
        glLightModeli(GL_LIGHT_MODEL_LOCAL_VIEWER, GL_TRUE);
        glEnable(GL_LIGHT0);

        FloatBuffer ambientBuffer = Utils.createFloatBuffer(ambient.r,ambient.g, ambient.b, 1.0f);
        FloatBuffer diffuseBuffer = Utils.createFloatBuffer(diffuse.r,diffuse.g, diffuse.b, 1.0f);
        FloatBuffer specularBuffer = Utils.createFloatBuffer(specular.r,specular.g, specular.b, 1.0f);

        glLight(GL_LIGHT0, GL_AMBIENT, ambientBuffer);
        glLight(GL_LIGHT0, GL_DIFFUSE, diffuseBuffer);
        glLight(GL_LIGHT0, GL_SPECULAR, specularBuffer);
    }
    
    public void apply(){
        FloatBuffer qaLightPosition = Utils.createFloatBuffer(position.x, position.y, position.z, 1.0f);
        glLight(GL_LIGHT0, GL_POSITION, qaLightPosition);
    }
    
}
