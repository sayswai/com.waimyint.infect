package UI;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import Controllers.*;
import Player.Player;
import Text.*;

import com.jogamp.newt.event.KeyEvent;

import Controllers.TGAController;

public class ArrowKeys {

	public class Button{
		int arrow, pressed, xpos, ypos;
		int[] dim = new int[2];
		public boolean down = false;
		
		public Button(String arrow, String pressed)
		{
			this.arrow = TGAController.glTexImageTGAFile(arrow, dim);
			this.pressed = TGAController.glTexImageTGAFile(pressed, dim);
		}
		
		public void draw()
		{
			if(down){
				TGAController.glDrawSprite(pressed, xpos, ypos, dim[0], dim[1]);
			}else{
				TGAController.glDrawSprite(arrow, xpos, ypos, dim[0], dim[1]);
			}
		}
	}
	
	public boolean wasd = false;
	public Button up, down, right, left;
	char[] keys0 = new char[4];
	Text upT, downT, rightT, leftT;
	
	Random r = new Random();
	char c = '0';
	
	public ArrowKeys()
	{
		int x = 25;
		up = new Button("uArrow.tga", "uPressed.tga"); up.xpos = x; x+=125;up.ypos=455;
		down = new Button("dArrow.tga", "dPressed.tga");down.xpos = x; x+=125;down.ypos=455;

		left = new Button("lArrow.tga", "lPressed.tga");left.xpos = x; x+=125; left.ypos=455;
		right = new Button("rArrow.tga", "rPressed.tga");right.xpos = x; x+=125;right.ypos=455;
		int count = 0;
		
		while(count < 4)
		{
			 c = (char)(r.nextInt(26) + 'a');
			if(!contains(c))
			{
				keys0[count] = c;
				count++;
			}
		}
		
		x = 70;
		upT = new Text(String.valueOf(keys0[0]).toUpperCase());  upT.setY(170); upT.setX(x); x+=125;
		downT = new Text(String.valueOf(keys0[1]).toUpperCase()); downT.setY(170); downT.setX(x); x+=125;
		leftT = new Text(String.valueOf(keys0[3]).toUpperCase()); leftT.setY(170); leftT.setX(x); x+=125;
		rightT = new Text(String.valueOf(keys0[2]).toUpperCase()); rightT.setY(170); rightT.setX(x); x+=125;
		left.down = false;
		right.down = false;
		up.down = false;
		down.down = false;
	}
	public void activateWASD()
	{
		System.out.println("WASD activated");
		wasd = true;
		updateKeys(0);
	}
	public void deactivateWASD()
	{
		System.out.println("WASD deactivated");
		wasd = false;
		updateKeys(0);
		updateKeys(1);
		updateKeys(2);
		updateKeys(3);
	}
	public void update()
	{
		
		if(Keyboard.getKbState()[KeyEvent.VK_1])
		{
			if(Keyboard.getKbState()[KeyEvent.VK_3])
			{
				if(Keyboard.getKbState()[KeyEvent.VK_4])
				{
					if(!wasd){
						wasd = true;
						updateKeys(0);
					}else{
						wasd = false;
						updateKeys(0);
						updateKeys(1);
						updateKeys(2);
						updateKeys(3);
					}
				}
			}
		}
		
		if(wasd)
		{
		   if (Keyboard.getKbState()[KeyEvent.VK_W]) {//up
	    	  up.down = true;
	       }
	       if (Keyboard.getKbState()[KeyEvent.VK_S]) {//down
	          down.down = true;
	       }
	       if (Keyboard.getKbState()[KeyEvent.VK_D]) {//right
	    	   right.down = true;
	       }
	       if (Keyboard.getKbState()[KeyEvent.VK_A]){//left
				left.down = true;
	       }
		}else{
			if(Keyboard.pressed.contains(String.valueOf(keys0[0])))//up
			{
				up.down = true;
			}
			if(Keyboard.pressed.contains(String.valueOf(keys0[1])))//down
			{
				down.down = true;
			}
			if(Keyboard.pressed.contains(String.valueOf(keys0[2])))//right
			{
				right.down = true;
			}
			if(Keyboard.pressed.contains(String.valueOf(keys0[3])))//left
			{
				left.down = true;
			}
	
		}
	}

	public void updateLetters() {
		
		if(!wasd)
		{
			if(up.down)
			{
				if(!Keyboard.pressed.contains(String.valueOf(keys0[0])))
				{
					updateKeys(0);
					up.down = false;
				}
			}if(down.down)
			{
				if(!Keyboard.pressed.contains(String.valueOf(keys0[1])))
				{
					updateKeys(1);
					down.down = false;
				}
			} if(right.down)
			{
				if(!Keyboard.pressed.contains(String.valueOf(keys0[2])))
				{
					updateKeys(2);
					right.down = false;
				}
			} if(left.down){
				if(!Keyboard.pressed.contains(String.valueOf(keys0[3])))
				{
					updateKeys(3);
					left.down = false;
				}
			}
		}else{
			if(up.down)
			{
				if (!Keyboard.getKbState()[KeyEvent.VK_W])
				{
					up.down = false;
				}
			}
			if(down.down)
			{
				if (!Keyboard.getKbState()[KeyEvent.VK_S])
				{
					down.down = false;
				}
			}
			if(right.down)
			{
				if (!Keyboard.getKbState()[KeyEvent.VK_D])
				{
					right.down = false;
				}
			}
			if(left.down)
			{
				if (!Keyboard.getKbState()[KeyEvent.VK_A])
				{
					left.down = false;
				}
			}
			
		}
			
	}

	public void updateKeys(int key)
	{
		if(!wasd)
		{
			do{
			c = (char)(r.nextInt(26) + 'a');
			}while(contains(c));
			keys0[key] = c;
			switch(key)
			{
			case 0: upT.setText(String.valueOf(keys0[key]).toUpperCase());
					break;	
			case 1: downT.setText(String.valueOf(keys0[key]).toUpperCase());
					break;
			case 2: rightT.setText(String.valueOf(keys0[key]).toUpperCase());	
					break;
			case 3: leftT.setText(String.valueOf(keys0[key]).toUpperCase());
			}
		}else{
			upT.setText("W");
			downT.setText("S");
			rightT.setText("D");
			leftT.setText("A");
		}
		
		
	}
	
	private boolean contains(char c)
	{
		for(int i = 0; i < 4; i++)
		{
			if(keys0[i] == c)
			{
				return true;
			}
		}
		return false;
	}
	
	public void draw() {
		up.draw();  
		down.draw();  
		right.draw();  
		left.draw();  
		upT.draw();
		downT.draw();
		leftT.draw();
		rightT.draw();
		
	}
	
}
