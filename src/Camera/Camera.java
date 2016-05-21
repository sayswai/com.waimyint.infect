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
		if(x >= 800){
			x = 800;
		}
	}
	
	public static void scrollCamera()
	{
		if(deltaDuration < (System.nanoTime() - deltaTime))
		{
			if(direction == 1)
			{
				if((x + 1) < 800)
				{
					x += 1;
				}else{
					direction = 0;
				}
			}else{
				if((x -1 ) >= 0)
				{
					x -= 1;
				}else{
					direction = 1;
				}
			}
			deltaTime = System.nanoTime();
		}
	}
	
}
