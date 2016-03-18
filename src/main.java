import java.awt.Color;
import java.awt.event.KeyEvent;

import Controllers.*;

public class main {

	private static boolean shouldExit;
	
	public static void main(String[] args)
	{
		Window.createWindow(640,  480, "Infect Nintendo Characters");
		
    	/*Initialize Keyboard*/
    	Keyboard.initializeKb();
    	
    	/*Initialize Objects*/
    	/*initialize Texts*/
    	/*Load Textures*/
    	/*Physics Implementation*/
    	int physicsDeltaMS = 10;
    	int lastPhysicsFrameMS = 0;
    	/*Extra*/
    	
    	
    	/*Game Loop*/
    	while(!shouldExit)
    	{
    		/*Copies the keystrokes to prev.keystroke array to compare*/
    		System.arraycopy(Keyboard.getKbState(), 0, Keyboard.getKbPrevState(), 0, Keyboard.getKbState().length);
    	
    		Window.window.display();
    		
    		/*Pressing ESC will exit the game or closing the window*/
    		if(Keyboard.getKbState()[KeyEvent.VK_ESCAPE]){
    			shouldExit = true;
    		}
    		if(!Window.window.isVisible())
    		{
    			shouldExit = true;
    			break;
    		}
    		
    		int curFrameMS = 100;
    		/*Physics Update*/
    		do{
    			
    		}while(lastPhysicsFrameMS + physicsDeltaMS < curFrameMS);
    		lastPhysicsFrameMS = 0;
    		
    		/*Clear Last Frame*/
    		Window.clearWindow();
    		
    		/*Draw*/
    	}
	}
}
