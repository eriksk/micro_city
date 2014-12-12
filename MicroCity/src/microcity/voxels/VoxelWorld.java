/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package microcity.voxels;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.lwjgl.opengl.GLContext;
import org.lwjgl.util.vector.Vector3f;

/**
 *
 * @author Erik
 */
public class VoxelWorld {

    private List<Chunk> chunks;
    public final int chunkSize = 64;
    public final float blockSize = 0.5f;
    private final Object chunkLock;
    private List<IVoxelWorldEventListener> listeners;

    public VoxelWorld() {
        chunks = new ArrayList<Chunk>();
        chunkLock = new Object();
        listeners = new ArrayList<>();
    }
    
    public void addListener(IVoxelWorldEventListener listener){
        listeners.add(listener);
    }

    public String getLogInfo() {
        return "World: ---\n"
                + "Chunks: " + chunks.size() + "\n"
                + "Blocks: " + getCubeCount();
    }

    public Chunk getChunk(int index) {
        return chunks.get(index);
    }

    public int getCubeCount() {
        int count = 0;
        for (Chunk chunk : chunks)
            count += chunk.getCubeCount();
        return count;
    }

    public void set(int x, int y, int z, int type) {
        Chunk chunk = getOrCreateChunk(x, y, z);

        chunk.set(x - chunk.voxelPosition.x * chunkSize, y - chunk.voxelPosition.y * chunkSize, z - chunk.voxelPosition.z * chunkSize, type);
    }

    private Chunk getOrCreateChunk(int x, int y, int z) {
        for (Chunk chunk : chunks) {
            if (chunk.containsWorldPosition(x * blockSize, y * blockSize, z * blockSize)) {
                return chunk;
            }
        }

        int chunkX = (int)(x >= 0 ? Math.floor(x / chunkSize) : Math.ceil((x - chunkSize) / chunkSize));
        int chunkY = (int)(y >= 0 ? Math.floor(y / chunkSize) : Math.ceil((y - chunkSize) / chunkSize));
        int chunkZ = (int)(z >= 0 ? Math.floor(z / chunkSize) : Math.ceil((z - chunkSize) / chunkSize));

        Vector3f chunkPosition = new Vector3f(chunkX * chunkSize * blockSize, chunkY * chunkSize * blockSize, chunkZ * chunkSize * blockSize);

        Chunk chunk = new Chunk(this, chunkPosition, new VoxelPosition(chunkX, chunkY, chunkZ));
        chunks.add(chunk);
        
        for (IVoxelWorldEventListener listener : listeners) {
            listener.chunkAdded(chunk);
        }
        
        return chunk;
    }
}
