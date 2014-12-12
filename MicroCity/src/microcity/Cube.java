/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package microcity;

import microcity.utils.Color;
import static org.lwjgl.opengl.GL11.*;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

/**
 *
 * @author Erik
 */
public class Cube {
    
    float size;
    public Color color;
    
    Vector3f[] vertices;
    Vector3f[] normals;
    
    public Vector3f position;

    public Cube(float size) {
        this.size = size;
        color = Color.Green();
        vertices = new Vector3f[4*6];
        normals = new Vector3f[6];
        position = new Vector3f();
        
        construct();
    }

    private void construct() {
        float half = size / 2f;
        // front
        vertices[0] = new Vector3f(-half, -half, half);
        vertices[1] = new Vector3f(half, -half, half);
        vertices[2] = new Vector3f(half, half, half);
        vertices[3] = new Vector3f(-half, half, half);
        normals[0] = new Vector3f(0, 0, -1);
                
        // back
        vertices[4] = new Vector3f(half, -half, -half);
        vertices[5] = new Vector3f(-half, -half, -half);
        vertices[6] = new Vector3f(-half, half, -half);
        vertices[7] = new Vector3f(half, half, -half);
        normals[1] = new Vector3f(0, 0, 1);
        
        // left
        vertices[8] = new Vector3f(-half, -half, -half);
        vertices[9] = new Vector3f(-half, -half, half);
        vertices[10] = new Vector3f(-half, half, half);
        vertices[11] = new Vector3f(-half, half, -half);
        normals[2] = new Vector3f(-1, 0, 0);
        
        // right
        vertices[12] = new Vector3f(half, -half, -half);
        vertices[13] = new Vector3f(half, -half, half);
        vertices[14] = new Vector3f(half, half, half);
        vertices[15] = new Vector3f(half, half, -half);
        normals[3] = new Vector3f(1, 0, 0);
        
        // bottom
        vertices[16] = new Vector3f(-half, -half, -half);
        vertices[17] = new Vector3f(half, -half, -half);
        vertices[18] = new Vector3f(half, -half, half);
        vertices[19] = new Vector3f(-half, -half, half);
        normals[4] = new Vector3f(0, 1, 0);
        
        // top
        vertices[20] = new Vector3f(-half, half, -half);
        vertices[21] = new Vector3f(half, half, -half);
        vertices[22] = new Vector3f(half, half, half);
        vertices[23] = new Vector3f(-half, half, half);
        normals[5] = new Vector3f(0, -1, 0);
    }
    
    public void render(){
        glPushMatrix();        
        color.bind();
        glTranslatef(position.x, position.y, position.z);
        for(int i = 0; i < vertices.length; i += 4){
            Vector3f normal = normals[i / 4];
            glNormal3f(normal.x, normal.y, normal.z);
            glBegin(GL_QUADS);
            for (int j = 0; j < 4; j++) {
                Vector3f vertex = vertices[i + j];
                glVertex3f(vertex.x, vertex.y, vertex.z);
            }
            glEnd();
        }
        
        glPopMatrix();
    }
}
