package Camera;


import java.awt.Shape;
import Controllers.*;

public class Camera {

	public static int x = 0;
	public static int y = 0;
	public static int xOffset = 0;	
	public static int movingSpeed = 0;
	public static Shape shape;
	
	public static void updateOffset(int tileWidth) {
		xOffset = x % tileWidth;
	}
	
	public static void boundaryUpdate(){
		if(x <= 0){
			x = 0;
		}
		if(x >= Window.window.getWidth()){
			x = Window.window.getWidth();
		}
	}
	
}
