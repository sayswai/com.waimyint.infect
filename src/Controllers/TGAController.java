package Controllers;
	/*Functions glTexImageTGAFile() & glDrawSprite() provided
	 * by Profesor Michael Finder
	 * from SJSU
	 */
	// Load a file into an OpenGL texture and return that texture.


import java.io.DataInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

import com.jogamp.opengl.GL2;




	/*Functions glTexImageTGAFile() & glDrawSprite() provided
	 * by Profesor Michael Finder
	 * from SJSU
	 */
	// Load a file into an OpenGL texture and return that texture.



import java.io.FileInputStream;
import java.io.FileNotFoundException;



public class TGAController{
// Load a file into an OpenGL texture and return that texture.
public static int glTexImageTGAFile(String filename, int[] out_size) {
	GL2 gl = Window.gl;
    final int BPP = 4;
    

    DataInputStream file = null;
    	   try {
        // Open the file.
    	/*file = new DataInputStream(ResourceLoader.Load(filename));
    	 * 
    	 */

        file = new DataInputStream(new FileInputStream(filename));
    } catch (FileNotFoundException ex) {
        System.err.println("File: "+filename+" -- Could not open for reading.");
        return 0;
    }

    try {
        // Skip first two bytes of data we don't need.
        file.skipBytes(2);

        // Read in the image type.  For our purposes the image type
        // should be either a 2 or a 3.
        int imageTypeCode = file.readByte();
        if (imageTypeCode != 2 && imageTypeCode != 3) {
            file.close();
            //System.err.format("File: %s -- Unsupported TGA type: %d", filename, imageTypeCode);
            System.err.println("File: "+filename+" -- Could not open for reading.");
            return 0;
        }

        // Skip 9 bytes of data we don't need.
        file.skipBytes(9);

        int imageWidth = Short.reverseBytes(file.readShort());
        int imageHeight = Short.reverseBytes(file.readShort());
        int bitCount = file.readByte();
        file.skipBytes(1);

        // Allocate space for the image data and read it in.
        byte[] bytes = new byte[imageWidth * imageHeight * BPP];

        // Read in data.
        if (bitCount == 32) {
            for (int it = 0; it < imageWidth * imageHeight; ++it) {
                bytes[it * BPP + 0] = file.readByte();
                bytes[it * BPP + 1] = file.readByte();
                bytes[it * BPP + 2] = file.readByte();
                bytes[it * BPP + 3] = file.readByte();
            }
        } else {
            for (int it = 0; it < imageWidth * imageHeight; ++it) {
                bytes[it * BPP + 0] = file.readByte();
                bytes[it * BPP + 1] = file.readByte();
                bytes[it * BPP + 2] = file.readByte();
                bytes[it * BPP + 3] = -1;
            }
        }

        file.close();

        // Load into OpenGL
        int[] texArray = new int[1];
        gl.glGenTextures(1, texArray, 0);
        int tex = texArray[0];
        gl.glBindTexture(GL2.GL_TEXTURE_2D, tex);
        gl.glTexImage2D(
                GL2.GL_TEXTURE_2D, 0, GL2.GL_RGBA, imageWidth, imageHeight, 0,
                GL2.GL_BGRA, GL2.GL_UNSIGNED_BYTE, ByteBuffer.wrap(bytes));
        gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_MIN_FILTER, GL2.GL_NEAREST);
        gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_MAG_FILTER, GL2.GL_NEAREST);

        out_size[0] = imageWidth;
        out_size[1] = imageHeight;
        return tex;
    }
    catch (IOException ex) {
        //System.err.format("File: %s -- Unexpected end of file.", filename);
        System.err.println("File: "+filename+" -- Could not open for reading.");
        return 0;
    }
}

public static void glDrawSprite(int tex, int x, int y, int w, int h) {
    GL2 gl = Window.gl;
	gl.glBindTexture(GL2.GL_TEXTURE_2D, tex);
    gl.glBegin(GL2.GL_QUADS);
    {
        gl.glColor3ub((byte)-1, (byte)-1, (byte)-1);
        gl.glTexCoord2f(0, 1);
        gl.glVertex2i(x, y);
        gl.glTexCoord2f(1, 1);
        gl.glVertex2i(x + w, y);
        gl.glTexCoord2f(1, 0);
        gl.glVertex2i(x + w, y + h);
        gl.glTexCoord2f(0, 0);
        gl.glVertex2i(x, y + h);
    }
    gl.glEnd();
}

}


