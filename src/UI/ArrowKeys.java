package UI;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import Controllers.*;
import Text.*;

import com.jogamp.newt.event.KeyEvent;

import Controllers.TGAController;

public class ArrowKeys {

	class Button{
		int arrow, pressed;
		int[] dim = new int[2];
		boolean down = false;
		
		public Button(String arrow, String pressed)
		{
			this.arrow = TGAController.glTexImageTGAFile(arrow, dim);
			this.pressed = TGAController.glTexImageTGAFile(pressed, dim);
		}
		
		public void draw(int x, int y)
		{
			if(down){
				TGAController.glDrawSprite(pressed, x, y, dim[0], dim[1]);
			}else{
				TGAController.glDrawSprite(arrow, x, y, dim[0], dim[1]);
			}
		}
	}
	
	Button up, down, right, left;
	ArrayList<String> keys;
	char[] keys0 = new char[4];
	Text upT, downT, rightT, leftT;
	
	Random r = new Random();
	char c = '0';
	
	public ArrowKeys()
	{
		up = new Button("uArrow.tga", "uPressed.tga");
		down = new Button("dArrow.tga", "dPressed.tga");
		left = new Button("lArrow.tga", "lPressed.tga");
		right = new Button("rArrow.tga", "rPressed.tga");
		keys = new ArrayList<String>(); int count = 0;
		
		while(count < 4)
		{
			 c = (char)(r.nextInt(26) + 'a');
			if(!contains(c))
			{
				keys0[count] = c;
				count++;
			}
		}
		
		int x = 70;
		upT = new Text(String.valueOf(keys0[0]).toUpperCase());  upT.setY(40); upT.setX(x); x+=125;
		downT = new Text(String.valueOf(keys0[1]).toUpperCase()); downT.setY(40); downT.setX(x); x+=125;
		rightT = new Text(String.valueOf(keys0[2]).toUpperCase()); rightT.setY(40); rightT.setX(x); x+=125;
		leftT = new Text(String.valueOf(keys0[3]).toUpperCase()); leftT.setY(40); leftT.setX(x);
		left.down = false;
		right.down = false;
		up.down = false;
		down.down = false;
	}
	
	public void update()
	{
		

/*
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
       */
		if(Keyboard.pressed.contains(String.valueOf(keys0[0])))
		{
			up.down = true;
		}
		if(Keyboard.pressed.contains(String.valueOf(keys0[1])))
		{
			down.down = true;
			updateKeys(1);
		}
		if(Keyboard.pressed.contains(String.valueOf(keys0[2])))
		{
			right.down = true;
			updateKeys(2);
		}
		if(Keyboard.pressed.contains(String.valueOf(keys0[3])))
		{
			left.down = true;
			updateKeys(3);
		}

		updateLetters();
	}

	public void updateLetters() {
		if(up.down)
		{
			if(!Keyboard.pressed.contains(String.valueOf(keys0[0])))
			{
				updateKeys(0);
				up.down = false;
			}
		}else if(down.down)
		{
			updateKeys(1);
		}else if(right.down)
		{
			updateKeys(2);
		}else if(left.down){
			updateKeys(3);
		}
	}

	public void updateKeys(int key)
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
		int x = 25;
		up.draw(x, 425);  x+=125; 
		down.draw(x, 425);  x+=125;
		right.draw(x, 425);  x+=125;
		left.draw(x, 425);  x+=125;
		upT.draw();
		downT.draw();
		leftT.draw();
		rightT.draw();
		
	}
	
}
