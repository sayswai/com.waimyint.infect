package Controllers;
import com.jogamp.newt.event.KeyEvent;
import com.jogamp.newt.event.KeyListener;

public class Keyboard {

	 // The previous frame's keyboard state.
    private static boolean kbPrevState[] = new boolean[256];

    // The current frame's keyboard state.
    private static boolean kbState[] = new boolean[256];
    
	
	

	public static void initializeKb() {
		
		Window.window.addKeyListener(new KeyListener() {
            public void keyPressed(KeyEvent keyEvent) {
                kbState[keyEvent.getKeyCode()] = true;
            }

            public void keyReleased(KeyEvent keyEvent) {
                kbState[keyEvent.getKeyCode()] = false;
            }
            
        });
	}
	
	

	public static boolean[] getKbPrevState() {
		return kbPrevState;
	}

	
public static boolean[] getKbState() {
		return kbState;
	}

	

	

}
