/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package microcity;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;


/**
 *
 * @author Erik
 */
public class FPSCameraController {

    private final Camera cam;

    float mouseSensitivity = 0.05f;
    float movementSpeed = 0.01f; //move 10 units per second

    /**
     *
     * @param cam
     */
    public FPSCameraController(Camera cam) {
        this.cam = cam;
    }

    public void update(float dt) {

        mouseSensitivity = 0.1f;
        movementSpeed = 0.01f; //move 10 units per second
        //hide the mouse
        //Mouse.setGrabbed(true);
        //distance in mouse movement from the last getDX() call.
        float dx = Mouse.getDX();
        //distance in mouse movement from the last getDY() call.
        float dy = Mouse.getDY();

        //controll camera yaw from x movement fromt the mouse
        cam.yaw(dx * mouseSensitivity);
        //controll camera pitch from y movement fromt the mouse
        cam.pitch(-dy * mouseSensitivity);
        
        
        if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT))
        {
            movementSpeed *= 8f;
        }
        
        //when passing in the distance to move
        //we times the movementSpeed with dt this is a time scale
        //so if its a slow frame u move more then a fast frame
        //so on a slow computer you move just as fast as on a fast computer
        
        if (Keyboard.isKeyDown(Keyboard.KEY_W))//move forward
        {
            cam.walkForward(movementSpeed * dt);
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_S))//move backwards
        {
            cam.walkBackwards(movementSpeed * dt);
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_A))//strafe left
        {
            cam.strafeLeft(movementSpeed * dt);
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_D))//strafe right
        {
            cam.strafeRight(movementSpeed * dt);
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_SPACE))
        {
            cam.moveUp(movementSpeed * dt);
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_LCONTROL))
        {
            cam.moveDown(movementSpeed * dt);
        }
    }
}
