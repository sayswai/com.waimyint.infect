package Controllers;
import com.jogamp.nativewindow.WindowClosingProtocol.WindowClosingMode;
import com.jogamp.newt.opengl.GLWindow;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLException;
import com.jogamp.opengl.GLProfile;


public class Window {
	public static GLProfile gl2Profile;
	public static GLWindow window;
	public static GL2 gl;
	public static final int WINDOW_WIDTH = 800;

public static void createWindow(int width, int height, String title)
{

    try {
        // Make sure we have a recent version of OpenGL
        gl2Profile = GLProfile.get(GLProfile.GL2);
    }
    catch (GLException ex) {
        System.out.println("OpenGL max supported version is too low.");
        System.exit(1);
        return;
    }
    
    // Create the window and OpenGL context.
    window = GLWindow.create(new GLCapabilities(gl2Profile));
    window.setSize(width, height);
    window.setTitle(title);
    window.setVisible(true);
    window.setDefaultCloseOperation(WindowClosingMode.DISPOSE_ON_CLOSE);
    // Setup OpenGL state.
    window.getContext().makeCurrent();
    gl = window.getGL().getGL2();
    gl.glViewport(0, 0, width, height);
    gl.glMatrixMode(GL2.GL_PROJECTION);
   // gl.glOrtho(0, 640, 480, 0, 0, 100);
    gl.glOrtho(0, 800, 600, 0, 0, 100);
    gl.glEnable(GL2.GL_TEXTURE_2D);
    gl.glEnable(GL2.GL_BLEND);
    gl.glBlendFunc(GL2.GL_SRC_ALPHA, GL2.GL_ONE_MINUS_SRC_ALPHA);

}

public static void clearWindow()
{
	 gl.glClearColor(0, 0, 0, 1);
     gl.glClear(GL2.GL_COLOR_BUFFER_BIT);
}



}
