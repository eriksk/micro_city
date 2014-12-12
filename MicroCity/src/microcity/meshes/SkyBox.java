/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package microcity.meshes;

import microcity.Camera;
import microcity.content.ContentManager;
import static org.lwjgl.opengl.GL11.*;
import org.newdawn.slick.opengl.Texture;

/**
 *
 * @author Erik
 */
public class SkyBox {

    Texture[] textures;

    public SkyBox() {
        textures = new Texture[]{
            ContentManager.load("skybox_back"),
            ContentManager.load("skybox_right"),
            ContentManager.load("skybox_front"),
            ContentManager.load("skybox_left"),
            ContentManager.load("skybox_bottom"),
            ContentManager.load("skybox_top")
        };
        
        for (Texture texture : textures) {
            texture.setTextureFilter(GL_LINEAR);
        }
        

    }

    public void render(Camera cam) {

        glDisable(GL_DEPTH_TEST);
        
        
        glPushMatrix();
        glTranslatef(-cam.position.x, -cam.position.y, -cam.position.z);
        
        glScalef(100, 100, 100);
        glColor3f(1,1, 1);
        glRotatef(180, 0, 0, 1f);
        
    // Render the front quad
   textures[0].bind();
   glBegin(GL_QUADS);
       glTexCoord2f(0, 0); glVertex3f(  0.5f, -0.5f, -0.5f );
       glTexCoord2f(1, 0); glVertex3f( -0.5f, -0.5f, -0.5f );
       glTexCoord2f(1, 1); glVertex3f( -0.5f,  0.5f, -0.5f );
       glTexCoord2f(0, 1); glVertex3f(  0.5f,  0.5f, -0.5f );
   glEnd();
   // Render the left quad
   textures[1].bind();
   glBegin(GL_QUADS);
       glTexCoord2f(0, 0); glVertex3f(  0.5f, -0.5f,  0.5f );
       glTexCoord2f(1, 0); glVertex3f(  0.5f, -0.5f, -0.5f );
       glTexCoord2f(1, 1); glVertex3f(  0.5f,  0.5f, -0.5f );
       glTexCoord2f(0, 1); glVertex3f(  0.5f,  0.5f,  0.5f );
   glEnd();
   // Render the back quad
   textures[2].bind();
   glBegin(GL_QUADS);
       glTexCoord2f(0, 0); glVertex3f( -0.5f, -0.5f,  0.5f );
       glTexCoord2f(1, 0); glVertex3f(  0.5f, -0.5f,  0.5f );
       glTexCoord2f(1, 1); glVertex3f(  0.5f,  0.5f,  0.5f );
       glTexCoord2f(0, 1); glVertex3f( -0.5f,  0.5f,  0.5f );
   glEnd();
   // Render the right quad
   textures[3].bind();
   glBegin(GL_QUADS);
       glTexCoord2f(0, 0); glVertex3f( -0.5f, -0.5f, -0.5f );
       glTexCoord2f(1, 0); glVertex3f( -0.5f, -0.5f,  0.5f );
       glTexCoord2f(1, 1); glVertex3f( -0.5f,  0.5f,  0.5f );
       glTexCoord2f(0, 1); glVertex3f( -0.5f,  0.5f, -0.5f );
   glEnd();
   // Render the top quad
   textures[4].bind();
   glBegin(GL_QUADS);
       glTexCoord2f(0, 1); glVertex3f( -0.5f,  0.5f, -0.5f );
       glTexCoord2f(0, 0); glVertex3f( -0.5f,  0.5f,  0.5f );
       glTexCoord2f(1, 0); glVertex3f(  0.5f,  0.5f,  0.5f );
       glTexCoord2f(1, 1); glVertex3f(  0.5f,  0.5f, -0.5f );
   glEnd();
   // Render the bottom quad
   textures[5].bind();
   glBegin(GL_QUADS);
       glTexCoord2f(0, 0); glVertex3f( -0.5f, -0.5f, -0.5f );
       glTexCoord2f(0, 1); glVertex3f( -0.5f, -0.5f,  0.5f );
       glTexCoord2f(1, 1); glVertex3f(  0.5f, -0.5f,  0.5f );
       glTexCoord2f(1, 0); glVertex3f(  0.5f, -0.5f, -0.5f );
   glEnd();
        glPopMatrix();
        
        
        glEnable(GL_DEPTH_TEST);
        
    }

}
