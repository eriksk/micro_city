/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package microcity.voxels;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import microcity.metrics.Stopwatch;

/**
 *
 * @author Erik
 */
public class VoxelRenderer {

    List<ChunkRenderer> chunkRenderers;
    volatile List<ChunkRenderer> dirtyChunks;

    private final VoxelWorld world;

    public VoxelRenderer(final VoxelWorld world) {
        chunkRenderers = new ArrayList<>();
        this.world = world;
        dirtyChunks = Collections.synchronizedList(new ArrayList<ChunkRenderer>());

        world.addListener(new IVoxelWorldEventListener() {
            @Override
            public void chunkAdded(Chunk chunk) {
                synchronized (chunkRenderers) {
                    // TODO: add to queue instead...
                    chunkRenderers.add(new ChunkRenderer(world, chunk));
                }
            }
        });
        new Thread(new Runnable() {
            @Override
            public void run() {
                preRebuild();
            }
        }).start();
    }

    public void update(float dt) {
        for (ChunkRenderer renderer : chunkRenderers) {
            if (renderer.isDirty() && !dirtyChunks.contains(renderer)) {
                dirtyChunks.add(renderer);
            }
        }
    }

    public void preRebuild() {
        while (true) {
            while (dirtyChunks.size() > 0) {
                dirtyChunks.get(0).prebuild();
                dirtyChunks.remove(0);
                break;
            }
            try {
                Thread.sleep(10);
            } catch (InterruptedException ex) {
            }
        }
    }

    public void render() {
        for (ChunkRenderer renderer : chunkRenderers) {
            renderer.rebuildIfNeeded();
        }

        for (ChunkRenderer renderer : chunkRenderers) {
            renderer.render();
        }
    }
}
