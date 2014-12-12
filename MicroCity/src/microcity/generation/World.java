/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package microcity.generation;

import java.util.Random;
import org.lwjgl.util.vector.Vector3f;

/**
 *
 * @author Erik
 */
public class World {
    
    private Random random;
    private Vector3f position;
    private Lod lodNear, lodMedium, lodFar;
    
    public World(int seed) {
        position = new Vector3f();
        random = new Random(seed);
        lodNear = new Lod();
        lodMedium = new Lod();
        lodFar = new Lod();
    }
    
    public void move(float x, float y, float z){
        float distance = (float)Math.sqrt(
                (float)Math.pow(position.x - x, 2.0) +
                (float)Math.pow(position.y - y, 2.0) +
                (float)Math.pow(position.z - z, 2.0));
    
        position.x = x;
        position.y = y;
        position.z = z;
        
        lodNear.set(distance / 1f);
        lodMedium.set(distance / 10f);
        lodFar.set(distance / 100f);
    }
    
    public void updateGeneration(float dt){
        updateNear(dt);
        updateMedium(dt);
        updateFar(dt);
    }

    private void updateNear(float dt) {
        // TODO: gene
    }

    private void updateMedium(float dt) {
    }

    private void updateFar(float dt) {
    }
}
