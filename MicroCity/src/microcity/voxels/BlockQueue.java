/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package microcity.voxels;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Erik
 */
public class BlockQueue {

    List<QueuedBlock> queue;

    public BlockQueue() {
        queue = new ArrayList<>();
    }

    public void put(int x, int y, int z, int type) {
        queue.add(new QueuedBlock(x, y, z, type));
    }

    public QueuedBlock get() {
        if (queue.size() > 0) {
            QueuedBlock b = queue.get(0);
            queue.remove(0);
            return b;
        }
        return null;
    }
}
