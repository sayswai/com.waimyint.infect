package Player;


import java.util.Random;

import UI.ArrowKeys;

import com.jogamp.nativewindow.util.Rectangle;

import Animation.*;
import Camera.Camera;
import Controllers.TGAController;

public class PowerUp {

	public class AnimationData implements AData{


		public AnimationDef def;
		boolean initiated = false;
		int counter;
		float deltaTime, delta;
		
		
		public void update() {
			if(!initiated)
			{
				initiated();
			}
			delta = System.nanoTime() - deltaTime;
			if(delta >= def.getFrameTime(counter))
			{
				if(counter == def.frames.length - 1)
				{
					counter = 0;
				}else{
					counter++;
				}
				deltaTime = System.nanoTime();
			}
		}

		public void initiated() {
			if(!initiated)
			{
				counter = 0;
				deltaTime = System.nanoTime();
				initiated = true;		
			}
		}

		public void draw() {
			TGAController.glDrawSprite(def.getFrameTex(counter), rPos[0], rPos[1], def.getTexWidth(counter), def.getTexHeight(counter));
		}

		public int getFrameWidth() {
			return def.getTexWidth(counter);
		}

		public int getFrameHeight() {
			return def.getTexHeight(counter);
		}
		
	}
	int[] rPos = new int[2];
	public int[] Pos = new int[2];
	int value, type;// 0  = wasd, 1 = speed up
	boolean onScreen = false;
	public AnimationData a;
	public Rectangle shape;
	public ArrowKeys arrows;
	public float duration;
	
	public PowerUp(int value, int type)
	{
		this.value = value;
		this.type = type;
		if(type == 0)
		{
			duration = (float)(value * 1000000000.0);
		}else{
			duration = (float)5000000000.0;
		}
		generatePosition();
		a = new AnimationData();
	}

	public void updateRelativePosition()
	{
		rPos[0] = Pos[0] - Camera.x;
		rPos[1] = Pos[1] - Camera.y;
	}
	
	public void generatePosition()
	{
		Random r = new Random();
		Pos[0] = r.nextInt(1600) + 1;
		Pos[1] = r.nextInt(350) + 1;

		updateRelativePosition();
	}
	public void draw()
	{
		updateRelativePosition();
		a.update();
		a.draw();
	}
	
	public void setShape()
	{
		shape = new Rectangle(Pos[0], Pos[1], getWidth(), getHeight());
	}
	public boolean overlap(Rectangle s)
	{//using AABB check
		boolean retrn = true;
		//is box1 left of box 2
		if((shape.getX()+shape.getWidth()) < s.getX())
		{
			retrn = false;
		}
		//is box1 right of box 2
		if(shape.getX() > (s.getX()+s.getWidth()))
		{
			retrn = false;
		}
		//is box1 above box2
		if((shape.getY()+shape.getHeight()) < s.getY())
		{
			retrn = false;
		}
		//is box1 below box2
		if(shape.getY() > (s.getY()+s.getHeight()))
		{
			retrn = false;
		}
		return retrn;
	}
	public int getWidth() {
		return a.getFrameWidth();
	}
	public int getHeight() {
		return a.getFrameHeight();
	}
	
	public void finalize() throws Throwable {
        try{
            System.out.println("Finalize of Sub Class");
            //release resources, perform cleanup ;
        }catch(Throwable t){
            throw t;
        }finally{
            System.out.println("Calling finalize of Super Class");
            super.finalize();
        }
      
    }

	public void keyboard(ArrowKeys arrows) {
		this.arrows = arrows;
	}

}
