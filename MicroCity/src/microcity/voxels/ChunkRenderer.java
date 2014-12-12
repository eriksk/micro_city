/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package microcity.voxels;

import java.util.ArrayList;
import java.util.List;
import microcity.buffers.VertexBufferObject;
import microcity.buffers.VertexPositionNormalColor;
import microcity.meshes.Face;
import microcity.meshes.MeshFactory;
import microcity.metrics.Stopwatch;
import org.lwjgl.util.vector.Vector3f;

/**
 *
 * @author Erik
 */
public class ChunkRenderer {

    private final VoxelWorld world;
    private final Chunk chunk;
    private VertexBufferObject mesh;
    private List<VertexPositionNormalColor> vertices;
    
    public ChunkRenderer(VoxelWorld world, Chunk chunk) {
        this.world = world;
        this.chunk = chunk;
    }

    private void rebuild() {
        if (mesh != null) {
            mesh.dispose();
        }
        
        Stopwatch stopwatch = new Stopwatch();
        stopwatch.start();

        mesh = new VertexBufferObject(vertices);
        stopwatch.end();
        System.out.println("Building mesh took: " + stopwatch.time() / 1000f + " seconds.");
        
        vertices = null;
    }

    private List<VertexPositionNormalColor> getVertices() {

        Stopwatch stopwatch = new Stopwatch();
        stopwatch.start();

        List<VertexPositionNormalColor> vertices = new ArrayList<>();

        List<Face> faces = MeshFactory.getCubeFaces();

        for (int z = 0; z < world.chunkSize; z++) {
            for (int y = 0; y < world.chunkSize; y++) {
                for (int x = 0; x < world.chunkSize; x++) {
                    Block block = chunk.get(x, y, z);
                    if (block.type != Block.TYPE_AIR) {
                        for (Face face : faces) {
                            if (faceVisible(chunk, x, y, z, face.side)) {
                                // TODO: only add visible faces
                                for (Vector3f vertex : face.vertices) {
                                    VertexPositionNormalColor v = new VertexPositionNormalColor();
                                    v.color = block.getColor();
                                    v.position = new Vector3f(vertex.x + x * world.blockSize, vertex.y + y * world.blockSize, vertex.z + z * world.blockSize);
                                    v.normal = face.normal;
                                    vertices.add(v);
                                }
                            }
                        }
                    }
                }
            }
        }

        stopwatch.end();
        System.out.println("Get verts took: " + stopwatch.time() / 1000f + " seconds.");

        return vertices;
    }

    private boolean faceVisible(Chunk chunk, int x, int y, int z, int side) {
        switch (side) {
            case Face.SIDE_LEFT:
                return chunk.getSafe(x - 1, y, z).type == Block.TYPE_AIR;
            case Face.SIDE_RIGHT:
                return chunk.getSafe(x + 1, y, z).type == Block.TYPE_AIR;
            case Face.SIDE_TOP:
                return chunk.getSafe(x, y + 1, z).type == Block.TYPE_AIR;
            case Face.SIDE_BOTTOM:
                return chunk.getSafe(x, y - 1, z).type == Block.TYPE_AIR;
            case Face.SIDE_FRONT:
                return chunk.getSafe(x, y, z + 1).type == Block.TYPE_AIR;
            case Face.SIDE_BACK:
                return chunk.getSafe(x, y, z - 1).type == Block.TYPE_AIR;
        }

        return true;
    }

    public boolean isDirty(){
        return chunk.isDirty();
    }
    
    public void prebuild(){
        vertices = getVertices();
        chunk.setDirty(false);
    }
    
    public void rebuildIfNeeded(){
        if (vertices != null && !chunk.isDirty()) {
            rebuild();
        }
    }

    public void render() {
        if (mesh != null) {
            mesh.render(chunk.position);
        }
    }
}
