/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package microcity.buffers;

import java.nio.FloatBuffer;
import java.util.List;
import microcity.utils.Color;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.util.vector.Vector3f;

/**
 *
 * @author Erik
 */
public class VertexBufferObject {

    int vertexBufferId;
    int colorBufferId;
    int normalBufferId;
    
    int cubeCount;

    public VertexBufferObject(List<VertexPositionNormalColor> vertices) {

        vertexBufferId = GL15.glGenBuffers();
        colorBufferId = GL15.glGenBuffers();
        normalBufferId = GL15.glGenBuffers();
        
        cubeCount = vertices.size();

        FloatBuffer vertexBuffer = BufferUtils.createFloatBuffer(vertices.size() * 3);
        FloatBuffer colorBuffer = BufferUtils.createFloatBuffer(vertices.size() * 3);
        FloatBuffer normalBuffer = BufferUtils.createFloatBuffer(vertices.size() * 3);
        
        // TODO: indices
        
        for (VertexPositionNormalColor vertex : vertices) {
            vertexBuffer.put(vertex.position.x).put(vertex.position.y).put(vertex.position.z);
            colorBuffer.put(vertex.color.r).put(vertex.color.g).put(vertex.color.b);
            normalBuffer.put(vertex.normal.x).put(vertex.normal.y).put(vertex.normal.z);
        }
        
        vertexBuffer.flip();
        colorBuffer.flip();
        normalBuffer.flip();
        
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vertexBufferId);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, vertexBuffer, GL15.GL_STATIC_DRAW);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, colorBufferId);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, colorBuffer, GL15.GL_STATIC_DRAW);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, normalBufferId);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, normalBuffer, GL15.GL_STATIC_DRAW);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
    }

    public void dispose() {
        GL15.glDeleteBuffers(vertexBufferId);
        GL15.glDeleteBuffers(colorBufferId);
        GL15.glDeleteBuffers(normalBufferId);
    }

    public void render(Vector3f position) {
        
        GL11.glPushMatrix();
        GL11.glTranslatef(position.x, position.y, position.z);
        
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vertexBufferId);
        GL11.glVertexPointer(3, GL11.GL_FLOAT, 0, 0L);
        
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, colorBufferId);
        GL11.glColorPointer(3, GL11.GL_FLOAT, 0, 0L);
        
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, normalBufferId);
        GL11.glNormalPointer(GL11.GL_FLOAT, 0, 0L);
          
        GL11.glDrawArrays(GL11.GL_QUADS, 0, cubeCount);
        GL11.glPopMatrix();
    }
}
