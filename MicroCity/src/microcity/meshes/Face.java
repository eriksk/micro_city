/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package microcity.meshes;

import microcity.utils.Color;
import org.lwjgl.util.vector.Vector3f;

/**
 *
 * @author Erik
 */
public class Face {
    
    public Vector3f[] vertices;
    public Vector3f normal;
    public int side;
    
    public static final int SIDE_LEFT = 0;
    public static final int SIDE_RIGHT  = 1;
    public static final int SIDE_TOP = 2;
    public static final int SIDE_BOTTOM = 3;
    public static final int SIDE_FRONT = 4;
    public static final int SIDE_BACK = 5;

    public Face() {
    }

    public Face(Vector3f[] vertices, Vector3f normal, int side) {
        this.vertices = vertices;
        this.normal = normal;
        this.side = side;
    }
    
    public Face clone(){
        Vector3f[] verts = new Vector3f[vertices.length];
        for (int i = 0; i < verts.length; i++) {
            verts[i] = vertices[i];
        }
        return new Face(verts, new Vector3f(normal.x, normal.y, normal.z), side);
    }
}
