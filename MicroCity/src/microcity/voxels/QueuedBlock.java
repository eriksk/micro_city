/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package microcity.voxels;

/**
 *
 * @author Erik
 */
public class QueuedBlock {
    public int x, y, z, type;

    public QueuedBlock() {
    }

    public QueuedBlock(int x, int y, int z, int type) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.type = type;
    }
}