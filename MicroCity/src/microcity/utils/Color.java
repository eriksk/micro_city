/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package microcity.utils;

import org.lwjgl.opengl.GL11;

/**
 *
 * @author Erik
 */
public class Color {

    public float r, g, b, a;

    public Color() {
        r = 1f;
        g = 1f;
        b = 1f;
        a = 1f;
    }

    public Color(float r, float g, float b, float a) {
        this.r = r;
        this.g = g;
        this.b = b;
        this.a = a;
    }

    public static Color Black() {
        return new Color(0f, 0f, 0f, 1f);
    }

    public static Color White() {
        return new Color(1f, 1f, 1f, 1f);
    }

    public static Color Red() {
        return new Color(1f, 0f, 0f, 1f);
    }

    public static Color Green() {
        return new Color(0.2f, 0.8f, 0.2f, 1f);
    }

    public static Color Blue() {
        return new Color(0.3f, 0.3f, 1f, 1f);
    }

    public static Color CornflowerBlue() {
        return new Color(100f / 255f, 149f / 255f, 237f / 255f, 1f);
    }

    public static Color Orange() {
        return new Color(255f / 255f, 157 / 255f, 0f, 1f);
    }

    public static Color Yellow() {
        return new Color(1f, 1f, 0f, 1f);
    }

    public static Color Brown() {
        return new Color(158f / 255f, 102f / 255f, 46f / 255f, 1f);
    }

    public static Color Gray() {
        return new Color(0.78f, 0.78f, 0.78f, 1f);
    }

    public void bind() {
        GL11.glColor4f(r, g, b, a);
    }
}
