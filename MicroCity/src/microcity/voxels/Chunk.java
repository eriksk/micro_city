/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package microcity.voxels;

import java.util.List;
import microcity.buffers.VertexBufferObject;
import microcity.buffers.VertexPositionNormalColor;
import org.lwjgl.util.vector.Vector3f;

/**
 *
 * @author Erik
 */
public class Chunk {

    private final VoxelWorld world;
    private boolean dirty;
    public Vector3f position;
    Block[][][] blocks;
    Block nullBlock;

    VertexBufferObject mesh;
    List<VertexPositionNormalColor> builderData;
    public VoxelPosition voxelPosition;

    public Chunk(VoxelWorld world, Vector3f position, VoxelPosition voxelPosition) {
        this.world = world;
        this.position = position;
        this.voxelPosition = voxelPosition;
        nullBlock = new Block();
        dirty = true;
        blocks = new Block[world.chunkSize][world.chunkSize][world.chunkSize];
        for (int x = 0; x < world.chunkSize; x++) {
            for (int y = 0; y < world.chunkSize; y++) {
                for (int z = 0; z < world.chunkSize; z++) {
                    blocks[x][y][z] = new Block(); // could use flyweight here later.
                }
            }
        }
    }

    public int getCubeCount() {
        int sum = 0;
        for (int x = 0; x < world.chunkSize; x++) {
            for (int y = 0; y < world.chunkSize; y++) {
                for (int z = 0; z < world.chunkSize; z++) {
                    Block block = get(x, y, z);
                    if (block.type != Block.TYPE_AIR) {
                        sum++;
                    }
                }
            }
        }
        return sum;
    }

    public Vector3f getPosition() {
        return position;
    }

    public boolean isDirty() {
        return dirty;
    }

    public void setDirty(boolean dirty) {
        this.dirty = dirty;
    }

    public boolean containsWorldPosition(float x, float y, float z) {

        if (x < position.x) {
            return false;
        }
        if (y < position.y) {
            return false;
        }
        if (z < position.z) {
            return false;
        }

        if (x >= position.x + world.chunkSize * world.blockSize) {
            return false;
        }
        if (y >= position.y + world.chunkSize * world.blockSize) {
            return false;
        }
        if (z >= position.z + world.chunkSize * world.blockSize) {
            return false;
        }

        return true;
    }

    private boolean isBlockInvisible(int x, int y, int z) {
        if (getSafe(x - 1, y, z).type == Block.TYPE_AIR) {
            return false;
        }
        if (getSafe(x + 1, y, z).type == Block.TYPE_AIR) {
            return false;
        }
        if (getSafe(x, y - 1, z).type == Block.TYPE_AIR) {
            return false;
        }
        if (getSafe(x, y + 1, z).type == Block.TYPE_AIR) {
            return false;
        }
        if (getSafe(x, y, z - 1).type == Block.TYPE_AIR) {
            return false;
        }
        if (getSafe(x, y, z + 1).type == Block.TYPE_AIR) {
            return false;
        }
        return true;
    }

    public void set(int x, int y, int z, int type) {
        blocks[x][y][z].type = type;
        dirty = true;
    }

    public Block get(int x, int y, int z) {
        return blocks[x][y][z];
    }

    public Block getSafe(int x, int y, int z) {
        if (x < 0 || y < 0 || z < 0) {
            return nullBlock;
        }
        if (x > world.chunkSize - 1 || y > world.chunkSize - 1 || z > world.chunkSize - 1) {
            return nullBlock;
        }

        return blocks[x][y][z];
    }

    public void update(float dt) {
    }

    public void render() {
        /*
         if (mesh != null) {
         mesh.render();
         }*/
    }
}
