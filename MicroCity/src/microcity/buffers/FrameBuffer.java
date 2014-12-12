/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package microcity.buffers;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;
import org.lwjgl.opengl.EXTFramebufferObject;
import static org.lwjgl.opengl.GL11.*;
import org.lwjgl.opengl.GLContext;
import org.lwjgl.opengl.GL14;
import org.lwjgl.util.glu.GLU;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.EXTFramebufferObject.*;
import org.lwjgl.opengl.GL12;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;

/**
 *
 * @author Erik
 */
public class FrameBuffer {

    int framebufferID;
    int colorTextureID;
    int depthRenderBufferID;
    
    int depthTextureID;
    int depthFrameBufferID;

    private final int width;
    private final int height;

    public FrameBuffer(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public void load() {

        framebufferID = glGenFramebuffersEXT();                                         // create a new framebuffer
        colorTextureID = glGenTextures();                                               // and a new texture used as a color buffer
        depthRenderBufferID = glGenRenderbuffersEXT();                                  // And finally a new depthbuffer
        depthTextureID = glGenTextures();
        depthFrameBufferID = glGenFramebuffersEXT();
        
        glBindFramebufferEXT(GL_FRAMEBUFFER_EXT, framebufferID);                        // switch to the new framebuffer

        // initialize color texture
        glBindTexture(GL_TEXTURE_2D, colorTextureID);                                   // Bind the colorbuffer texture
        glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);               // make it linear filterd
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA16, width, height, 0, GL_RGBA, GL_INT, (java.nio.ByteBuffer) null);  // Create the texture data
        glFramebufferTexture2DEXT(GL_FRAMEBUFFER_EXT, GL_COLOR_ATTACHMENT0_EXT, GL_TEXTURE_2D, colorTextureID, 0); // attach it to the framebuffer

        // initialize depth renderbuffer
        glBindRenderbufferEXT(GL_RENDERBUFFER_EXT, depthRenderBufferID);                // bind the depth renderbuffer
        glRenderbufferStorageEXT(GL_RENDERBUFFER_EXT, GL14.GL_DEPTH_COMPONENT24, width, height); // get the data space for it
        glFramebufferRenderbufferEXT(GL_FRAMEBUFFER_EXT, GL_DEPTH_ATTACHMENT_EXT, GL_RENDERBUFFER_EXT, depthRenderBufferID); // bind it to the renderbuffer

        glBindFramebufferEXT(GL_FRAMEBUFFER_EXT, 0);

        // depth texture
        glBindFramebufferEXT(GL_FRAMEBUFFER_EXT, depthFrameBufferID);
        glBindRenderbufferEXT(GL_RENDERBUFFER_EXT, depthRenderBufferID);  
        glRenderbufferStorageEXT(GL_RENDERBUFFER_EXT, GL14.GL_DEPTH_COMPONENT24, width, height); // get the data space for it
        glFramebufferRenderbufferEXT(GL_FRAMEBUFFER_EXT, GL_DEPTH_ATTACHMENT_EXT, GL_RENDERBUFFER_EXT, depthRenderBufferID); // bind it to the renderbuffer
    
        // initialize depth renderbuffer
        glBindTexture(GL_TEXTURE_2D, depthTextureID);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL12.GL_CLAMP_TO_EDGE);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL12.GL_CLAMP_TO_EDGE);
        glTexImage2D(GL_TEXTURE_2D, 0, GL14.GL_DEPTH_COMPONENT32, width, height, 0, GL_DEPTH_COMPONENT, GL_FLOAT, (java.nio.ByteBuffer) null);
        glFramebufferTexture2DEXT(GL_FRAMEBUFFER_EXT, GL_COLOR_ATTACHMENT0_EXT, GL_TEXTURE_2D, depthTextureID, 0); // attach it to the framebuffer

        glBindFramebufferEXT(GL_FRAMEBUFFER_EXT, 0);
    }

    public void begin() {

        glViewport(0, 0, width, height);                                    // set The Current Viewport to the fbo size

        glBindTexture(GL_TEXTURE_2D, 0);                                // unlink textures because if we dont it all is gonna fail
        glBindFramebufferEXT(GL_FRAMEBUFFER_EXT, framebufferID);        // switch to rendering on our FBO

        //  glClearColor (0.0f, 0.0f, 0.0f, 0.5f);
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);            // Clear Screen And Depth Buffer on the fbo to red
        glLoadIdentity();                                              // Reset The Modelview Matrix

    }

    public void render(int shader) {
        
        glBindFramebufferEXT(GL_FRAMEBUFFER_EXT, depthFrameBufferID);                    // switch to rendering on the framebuffer
        glBindTexture(GL_TEXTURE_2D, depthTextureID);
        glCopyTexImage2D(GL_TEXTURE_2D, 0, GL_DEPTH_COMPONENT, 0, 0, width, height, 0);

        glBindFramebufferEXT(GL_FRAMEBUFFER_EXT, 0);

        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);            // Clear Screen And Depth Buffer on the framebuffer to black

        
        int t1Location = GL20.glGetUniformLocation(shader, "texture1");
        int t2Location = GL20.glGetUniformLocation(shader, "depthTex");

        GL20.glUniform1i(t1Location, 0);
        GL20.glUniform1i(t2Location, 1);
        
        GL13.glActiveTexture(GL13.GL_TEXTURE0);
        glBindTexture(GL_TEXTURE_2D, colorTextureID);
        
        GL13.glActiveTexture(GL13.GL_TEXTURE1);
        glBindTexture(GL_TEXTURE_2D, depthTextureID);
        
        
        GL13.glActiveTexture(GL13.GL_TEXTURE0);
        
        
        glViewport(0, 0, width, height);                                    // set The Current Viewport

        glLoadIdentity();                                              // Reset The Modelview Matrix
        glTranslatef(0.0f, 0.0f, -1000f);                               // Translate 6 Units Into The Screen and then rotate
        drawBox();                                                      // draw the box

        
        glFlush();
    }

    public void drawBox() {
        // this func just draws a perfectly normal box with some texture coordinates
        glBegin(GL_QUADS);
        // Front Face
        glTexCoord2f(0.0f, 0.0f);
        glVertex3f(-width, -height, 1.0f);  // Bottom Left Of The Texture and Quad
        glTexCoord2f(1.0f, 0.0f);
        glVertex3f(width, -height, 1.0f);  // Bottom Right Of The Texture and Quad
        glTexCoord2f(1.0f, 1.0f);
        glVertex3f(width, height, 1.0f);  // Top Right Of The Texture and Quad
        glTexCoord2f(0.0f, 1.0f);
        glVertex3f(-width, height, 1.0f);  // Top Left Of The Texture and Quad
        // Back Face
        glTexCoord2f(1.0f, 0.0f);
        glVertex3f(-width, -height, -1.0f);  // Bottom Right Of The Texture and Quad
        glTexCoord2f(1.0f, 1.0f);
        glVertex3f(-width, height, -1.0f);  // Top Right Of The Texture and Quad
        glTexCoord2f(0.0f, 1.0f);
        glVertex3f(width, height, -1.0f);  // Top Left Of The Texture and Quad
        glTexCoord2f(0.0f, 0.0f);
        glVertex3f(width, -height, -1.0f);  // Bottom Left Of The Texture and Quad
        // Top Face
        glTexCoord2f(0.0f, 1.0f);
        glVertex3f(-width, height, -1.0f);  // Top Left Of The Texture and Quad
        glTexCoord2f(0.0f, 0.0f);
        glVertex3f(-width, height, 1.0f);  // Bottom Left Of The Texture and Quad
        glTexCoord2f(1.0f, 0.0f);
        glVertex3f(width, height, 1.0f);  // Bottom Right Of The Texture and Quad
        glTexCoord2f(1.0f, 1.0f);
        glVertex3f(width, height, -1.0f);  // Top Right Of The Texture and Quad
        // Bottom Face
        glTexCoord2f(1.0f, 1.0f);
        glVertex3f(-width, -height, -1.0f);  // Top Right Of The Texture and Quad
        glTexCoord2f(0.0f, 1.0f);
        glVertex3f(width, -height, -1.0f);  // Top Left Of The Texture and Quad
        glTexCoord2f(0.0f, 0.0f);
        glVertex3f(width, -height, 1.0f);  // Bottom Left Of The Texture and Quad
        glTexCoord2f(1.0f, 0.0f);
        glVertex3f(-width, -height, 1.0f);  // Bottom Right Of The Texture and Quad
        // Right face
        glTexCoord2f(1.0f, 0.0f);
        glVertex3f(width, -height, -1.0f);  // Bottom Right Of The Texture and Quad
        glTexCoord2f(1.0f, 1.0f);
        glVertex3f(width, height, -1.0f);  // Top Right Of The Texture and Quad
        glTexCoord2f(0.0f, 1.0f);
        glVertex3f(width, height, 1.0f);  // Top Left Of The Texture and Quad
        glTexCoord2f(0.0f, 0.0f);
        glVertex3f(width, -height, 1.0f);  // Bottom Left Of The Texture and Quad
        // Left Face
        glTexCoord2f(0.0f, 0.0f);
        glVertex3f(-width, -height, -1.0f);  // Bottom Left Of The Texture and Quad
        glTexCoord2f(1.0f, 0.0f);
        glVertex3f(-width, -height, 1.0f);  // Bottom Right Of The Texture and Quad
        glTexCoord2f(1.0f, 1.0f);
        glVertex3f(-width, height, 1.0f);  // Top Right Of The Texture and Quad
        glTexCoord2f(0.0f, 1.0f);
        glVertex3f(-width, height, -1.0f);  // Top Left Of The Texture and Quad
        glEnd();
    }

}
