/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package microcity.content;

import java.io.IOException;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

/**
 *
 * @author Erik
 */
public class ContentManager {
 
    public static Texture load(String name){
        try {
            return TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("content/gfx/" + name + ".png"));
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
}
