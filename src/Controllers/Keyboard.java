package Controllers;
import java.util.ArrayList;

import com.jogamp.newt.event.KeyEvent;
import com.jogamp.newt.event.KeyListener;

public class Keyboard {

	 // The previous frame's keyboard state.
    private static boolean kbPrevState[] = new boolean[256];
    public static ArrayList<String> pressed = new ArrayList<String>();

    // The current frame's keyboard state.
    private static boolean kbState[] = new boolean[256];
    
	
	

	public static void initializeKb() {
		
		Window.window.addKeyListener(new KeyListener() {
            public void keyPressed(KeyEvent keyEvent) {
               if(keyEvent.isAutoRepeat()){
            	   return;
               }
            	kbState[keyEvent.getKeyCode()] = true;
            	pressed.add(String.valueOf(keyEvent.getKeyChar()));
             }

            public void keyReleased(KeyEvent keyEvent) {
                if(keyEvent.isAutoRepeat()){
                	return;
                }
            	kbState[keyEvent.getKeyCode()] = false;
            	pressed.remove(String.valueOf(keyEvent.getKeyChar()));
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
