/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package microcity.utils;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import org.lwjgl.BufferUtils;

/**
 *
 * @author Erik
 */
public class Utils {
    public static float lerp(float x, float y, float a){
        return x + (y - x) * a;
    }
    

    public static FloatBuffer createFloatBuffer(float x, float y, float z, float u) {
        FloatBuffer buffer = BufferUtils.createFloatBuffer(4);
        buffer.rewind();
        buffer.put(x);
        buffer.put(y);
        buffer.put(z);
        buffer.put(u);
        buffer.rewind();
        return buffer;
    }

    public static String readFileAsString(String fileName) throws Exception {
        StringBuilder source = new StringBuilder();
         
        FileInputStream in = new FileInputStream(fileName);
         
        Exception exception = null;
         
        BufferedReader reader;
        try{
            reader = new BufferedReader(new InputStreamReader(in,"UTF-8"));
             
            Exception innerExc= null;
            try {
                String line;
                while((line = reader.readLine()) != null)
                    source.append(line).append('\n');
            }
            catch(Exception exc) {
                exception = exc;
            }
            finally {
                try {
                    reader.close();
                }
                catch(Exception exc) {
                    if(innerExc == null)
                        innerExc = exc;
                    else
                        exc.printStackTrace();
                }
            }
             
            if(innerExc != null)
                throw innerExc;
        }
        catch(Exception exc) {
            exception = exc;
        }
        finally {
            try {
                in.close();
            }
            catch(Exception exc) {
                if(exception == null)
                    exception = exc;
                else
                    exc.printStackTrace();
            }
             
            if(exception != null)
                throw exception;
        }
         
        return source.toString();
    }

}
