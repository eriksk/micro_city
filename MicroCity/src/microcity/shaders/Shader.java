/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package microcity.shaders;

import microcity.utils.Utils;
import org.lwjgl.opengl.ARBFragmentShader;
import org.lwjgl.opengl.ARBShaderObjects;
import org.lwjgl.opengl.ARBVertexShader;
import org.lwjgl.opengl.GL11;

/**
 *
 * @author Erik
 */
public class Shader {

    private final String vertexShader;
    private final String fragmentShader;

    int vertShader, fragShader;
    int program;

    public Shader(String vertexShader, String fragmentShader) {
        this.vertexShader = vertexShader;
        this.fragmentShader = fragmentShader;
    }
    
    public void load() {
        vertShader = createShader("src/content/shaders/" + vertexShader, ARBVertexShader.GL_VERTEX_SHADER_ARB);
        fragShader = createShader("src/content/shaders/" + fragmentShader, ARBFragmentShader.GL_FRAGMENT_SHADER_ARB);

        program = ARBShaderObjects.glCreateProgramObjectARB();

        ARBShaderObjects.glAttachObjectARB(program, vertShader);
        ARBShaderObjects.glAttachObjectARB(program, fragShader);

        ARBShaderObjects.glLinkProgramARB(program);
        if (ARBShaderObjects.glGetObjectParameteriARB(program, ARBShaderObjects.GL_OBJECT_LINK_STATUS_ARB) == GL11.GL_FALSE) {
            System.err.println(getLogInfo(program));
            return;
        }

        ARBShaderObjects.glValidateProgramARB(program);
        if (ARBShaderObjects.glGetObjectParameteriARB(program, ARBShaderObjects.GL_OBJECT_VALIDATE_STATUS_ARB) == GL11.GL_FALSE) {
            System.err.println(getLogInfo(program));
            return;
        }

    }

    public int getProgram() {
        return program;
    }

    public int getFragmentProgram() {
        return fragShader;
    }
    
    protected int createShader(String fileName, int shaderType) {
        int shader = 0;
        try {
            shader = ARBShaderObjects.glCreateShaderObjectARB(shaderType);

            if (shader == 0) {
                return 0;
            }

            ARBShaderObjects.glShaderSourceARB(shader, Utils.readFileAsString(fileName));
            ARBShaderObjects.glCompileShaderARB(shader);

            if (ARBShaderObjects.glGetObjectParameteriARB(shader, ARBShaderObjects.GL_OBJECT_COMPILE_STATUS_ARB) == GL11.GL_FALSE) {
                throw new RuntimeException("Error creating shader: " + getLogInfo(shader));
            }

            return shader;
        } catch (Exception exc) {
            ARBShaderObjects.glDeleteObjectARB(shader);
            throw new RuntimeException(exc);
        }
    }

    private String getLogInfo(int shader) {
        return ARBShaderObjects.glGetInfoLogARB(shader, ARBShaderObjects.glGetObjectParameteriARB(shader, ARBShaderObjects.GL_OBJECT_INFO_LOG_LENGTH_ARB));
    }

    public void begin() {
        ARBShaderObjects.glUseProgramObjectARB(program);
    }

    public void end() {
        ARBShaderObjects.glUseProgramObjectARB(0);
    }
}
