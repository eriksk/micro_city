/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package microcity.buffers;

import microcity.utils.Color;
import org.lwjgl.util.vector.Vector3f;

/**
 *
 * @author Erik
 */
public class VertexPositionNormalColor {
    
    public Vector3f position, normal;
    public Color color;

    public VertexPositionNormalColor() {
        color = new Color();
        position = new Vector3f();
        normal = new Vector3f();
    }

    public VertexPositionNormalColor setColor(Color color) {
        this.color = color;
        return this;
    }

    public VertexPositionNormalColor setNormal(Vector3f normal) {
        this.normal = normal;
        return this;
    }

    public VertexPositionNormalColor setPosition(Vector3f position) {
        this.position = position;
        return this;
    }
}
