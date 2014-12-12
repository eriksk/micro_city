/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package microcity.meshes;

import java.util.ArrayList;
import java.util.List;
import microcity.utils.Color;
import org.lwjgl.util.vector.Vector3f;

/**
 *
 * @author Erik
 */
public class MeshFactory {

    static List<Face> faces = new ArrayList<Face>();

    static {
        Face top = new Face(new Vector3f[]{
            new Vector3f(1.0f, 1.0f, -1.0f),
            new Vector3f(-1.0f, 1.0f, -1.0f),
            new Vector3f(-1.0f, 1.0f, 1.0f),
            new Vector3f(1.0f, 1.0f, 1.0f)
        }, new Vector3f(0, 1, 0), Face.SIDE_TOP);

        Face bottom = new Face(new Vector3f[]{
            new Vector3f(1.0f, -1.0f, 1.0f),
            new Vector3f(-1.0f, -1.0f, 1.0f),
            new Vector3f(-1.0f, -1.0f, -1.0f),
            new Vector3f(1.0f, -1.0f, -1.0f)},
                new Vector3f(0, -1, 0), Face.SIDE_BOTTOM);

        Face front = new Face(new Vector3f[]{
            new Vector3f(1.0f, 1.0f, 1.0f),
            new Vector3f(-1.0f, 1.0f, 1.0f),
            new Vector3f(-1.0f, -1.0f, 1.0f),
            new Vector3f(1.0f, -1.0f, 1.0f)},
                new Vector3f(0, 0, 1), Face.SIDE_FRONT);

        Face back = new Face(new Vector3f[]{
            new Vector3f(1.0f, -1.0f, -1.0f),
            new Vector3f(-1.0f, -1.0f, -1.0f),
            new Vector3f(-1.0f, 1.0f, -1.0f),
            new Vector3f(1.0f, 1.0f, -1.0f)},
                new Vector3f(0, 0, -1), Face.SIDE_BACK);

        Face left = new Face(new Vector3f[]{
            new Vector3f(-1.0f, 1.0f, 1.0f),
            new Vector3f(-1.0f, 1.0f, -1.0f),
            new Vector3f(-1.0f, -1.0f, -1.0f),
            new Vector3f(-1.0f, -1.0f, 1.0f)},
                new Vector3f(-1, 0, 0), Face.SIDE_LEFT);

        Face right = new Face(new Vector3f[]{
            new Vector3f(1.0f, 1.0f, -1.0f),
            new Vector3f(1.0f, 1.0f, 1.0f),
            new Vector3f(1.0f, -1.0f, 1.0f),
            new Vector3f(1.0f, -1.0f, -1.0f)},
                new Vector3f(1, 0, 0), Face.SIDE_RIGHT);
        
        faces.add(top);
        faces.add(bottom);
        faces.add(front);
        faces.add(back);
        faces.add(left);
        faces.add(right);
        
        for (Face face : faces) {
            for (Vector3f vertex : face.vertices) {
                vertex.x *= 0.5f * 0.5f;
                vertex.y *= 0.5f * 0.5f;
                vertex.z *= 0.5f * 0.5f;
            }
        }
    }
    
    private static List<Face> getFaces(){
        return faces;
    }

    public static List<Face> getCubeFaces() {
        return getFaces();
    }

    public static List<Vector3f> getVerts(float size, Vector3f position) {
        size /= 2f;
        List<Vector3f> vertices = new ArrayList<Vector3f>();

        vertices.add(new Vector3f(1.0f, 1.0f, -1.0f));
        vertices.add(new Vector3f(-1.0f, 1.0f, -1.0f));
        vertices.add(new Vector3f(-1.0f, 1.0f, 1.0f));
        vertices.add(new Vector3f(1.0f, 1.0f, 1.0f));

        vertices.add(new Vector3f(1.0f, -1.0f, 1.0f));
        vertices.add(new Vector3f(-1.0f, -1.0f, 1.0f));
        vertices.add(new Vector3f(-1.0f, -1.0f, -1.0f));
        vertices.add(new Vector3f(1.0f, -1.0f, -1.0f));

        vertices.add(new Vector3f(1.0f, 1.0f, 1.0f));
        vertices.add(new Vector3f(-1.0f, 1.0f, 1.0f));
        vertices.add(new Vector3f(-1.0f, -1.0f, 1.0f));
        vertices.add(new Vector3f(1.0f, -1.0f, 1.0f));

        vertices.add(new Vector3f(1.0f, -1.0f, -1.0f));
        vertices.add(new Vector3f(-1.0f, -1.0f, -1.0f));
        vertices.add(new Vector3f(-1.0f, 1.0f, -1.0f));
        vertices.add(new Vector3f(1.0f, 1.0f, -1.0f));

        vertices.add(new Vector3f(-1.0f, 1.0f, 1.0f));
        vertices.add(new Vector3f(-1.0f, 1.0f, -1.0f));
        vertices.add(new Vector3f(-1.0f, -1.0f, -1.0f));
        vertices.add(new Vector3f(-1.0f, -1.0f, 1.0f));

        vertices.add(new Vector3f(1.0f, 1.0f, -1.0f));
        vertices.add(new Vector3f(1.0f, 1.0f, 1.0f));
        vertices.add(new Vector3f(1.0f, -1.0f, 1.0f));
        vertices.add(new Vector3f(1.0f, -1.0f, -1.0f));

        for (Vector3f vertex : vertices) {
            vertex.x *= size;
            vertex.y *= size;
            vertex.z *= size;
            vertex.x += position.x;
            vertex.y += position.y;
            vertex.z += position.z;
        }

        return vertices;
    }

    public List<Vector3f> getCubeVertices(float size, Vector3f position) {
        List<Vector3f> vertices = new ArrayList<Vector3f>();

        vertices.add(new Vector3f(1.0f, 1.0f, -1.0f));
        vertices.add(new Vector3f(-1.0f, 1.0f, -1.0f));
        vertices.add(new Vector3f(-1.0f, 1.0f, 1.0f));
        vertices.add(new Vector3f(1.0f, 1.0f, 1.0f));
        vertices.add(new Vector3f(1.0f, -1.0f, 1.0f));
        vertices.add(new Vector3f(-1.0f, -1.0f, 1.0f));
        vertices.add(new Vector3f(-1.0f, -1.0f, -1.0f));
        vertices.add(new Vector3f(1.0f, -1.0f, -1.0f));
        vertices.add(new Vector3f(1.0f, 1.0f, 1.0f));
        vertices.add(new Vector3f(-1.0f, 1.0f, 1.0f));
        vertices.add(new Vector3f(-1.0f, -1.0f, 1.0f));
        vertices.add(new Vector3f(1.0f, -1.0f, 1.0f));
        vertices.add(new Vector3f(1.0f, -1.0f, -1.0f));
        vertices.add(new Vector3f(-1.0f, -1.0f, -1.0f));
        vertices.add(new Vector3f(-1.0f, 1.0f, -1.0f));
        vertices.add(new Vector3f(1.0f, 1.0f, -1.0f));
        vertices.add(new Vector3f(-1.0f, 1.0f, 1.0f));
        vertices.add(new Vector3f(-1.0f, 1.0f, -1.0f));
        vertices.add(new Vector3f(-1.0f, -1.0f, -1.0f));
        vertices.add(new Vector3f(-1.0f, -1.0f, 1.0f));
        vertices.add(new Vector3f(1.0f, 1.0f, -1.0f));
        vertices.add(new Vector3f(1.0f, 1.0f, 1.0f));
        vertices.add(new Vector3f(1.0f, -1.0f, 1.0f));
        vertices.add(new Vector3f(1.0f, -1.0f, -1.0f));

        for (Vector3f vertex : vertices) {
            vertex.x *= size;
            vertex.y *= size;
            vertex.z *= size;
            vertex.x += position.x;
            vertex.y += position.y;
            vertex.z += position.z;
        }

        return vertices;
    }

    public List<Color> getCubeColors(Color color) {
        List<Color> colors = new ArrayList<Color>();
        for (int i = 0; i < 24; i++) {
            colors.add(color);
        }
        return colors;
    }
}
