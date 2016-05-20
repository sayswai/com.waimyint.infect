package Camera;


import java.awt.Shape;
import Controllers.*;

public class Camera {

	public static int x = 0;
	public static int y = 0;
	public static int xOffset = 0;	
	public static int movingSpeed = 0;
	public static Shape shape;
	public static int prevX;
	public static int prevY;
	public static float deltaTime;
	public static float deltaDuration;
	public static short direction = 1; // 1= RIGHT/ 2= LEFT
	
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
