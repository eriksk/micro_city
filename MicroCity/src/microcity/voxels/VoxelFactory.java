/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package microcity.voxels;

import java.util.ArrayList;
import java.util.List;
import org.lwjgl.util.vector.Vector3f;

/**
 *
 * @author Erik
 */
public class VoxelFactory {

    public VoxelFactory() {
    }
    
    public List<Vector3f> createCubeVertices(){
        List<Vector3f> vertices = new ArrayList<>();
    
        // TODO: take face param
        
        
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
        
        return vertices;
    }
    
}
