/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package microcity.voxels;

import microcity.utils.Color;

/**
 *
 * @author Erik
 */
public class Block {
    public int type;

    public static final int TYPE_AIR = 0;
    public static final int TYPE_STONE = 1;
    
    private static final Color[] colors = new Color[]{
        Color.Gray(),
        Color.Brown(),
        Color.Green(),
        Color.Gray(),
        Color.White()
    };
    
    public Block() {
    }

    public Block(int type) {
        this.type = type;
    }
    
    public Color getColor(){
        return colors[type - 1];
    }
}
