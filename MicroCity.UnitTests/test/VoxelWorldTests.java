/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import microcity.voxels.VoxelWorld;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Erik
 */
public class VoxelWorldTests {

    @Test
    public void hello() {
        VoxelWorld world = new VoxelWorld();
        world.set(0, 0, 0, 0);
        
        assertNotNull(world.getChunk(0));
    }
}
