import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Random;

import com.jogamp.nativewindow.util.Rectangle;

import Levels.Level;
import Player.*;
import Text.Text;
import UI.*;
import Animation.AnimationDef;
import Camera.Camera;
import Controllers.*;

public class main {

	
	private static boolean shouldExit;
	private static boolean loose = false;
	private static boolean won = false;
	private static boolean gameWin = false;
	private static boolean afterDeath = false;
	public static float afterDeathDuration = 1000000000.0f;
	public static float afterDeathDelta = 0.0f;
	public static int countDown = 10;
	public static int level = 1;
	
	public static class menu
	{
		static void run()
		{
			boolean cont = true;
			float t = (float)1000000000.0; float delta = System.nanoTime(); short ct = 0;
			Text enter = new Text("Press C to Play..");
			Text enter1 = new Text("INFECT by Wai Myint"); enter1.setColor(Color.red); enter1.setSize(32); enter1.setX(Window.window.getX()/2);
			Text CS134 = new Text("CS 134"); CS134.setColor(Color.red); CS134.setSize(32); CS134.setX(Window.window.getX()/2);
			CS134.setY(200-42);
			Text esc = new Text("Press ESC to quit anytime, but why would you want to?");
			esc.setX(Window.window.getX()/2); esc.setY(200-42-32);
			esc.setSize(20);
			esc.setColor(Color.red);
			
			enter1.setY(242);
			enter.setColor(Color.white);
			enter.setSize(42);
			enter.setX(Window.window.getX()/2);
			enter.setY(200);
			while(cont || !shouldExit){

	    		System.arraycopy(Keyboard.getKbState(), 0, Keyboard.getKbPrevState(), 0, Keyboard.getKbState().length);
				Window.window.display();
				if(Keyboard.getKbState()[KeyEvent.VK_ESCAPE]){
	    			shouldExit = true;
	    		}
	    		if(!Window.window.isVisible())
	    		{
	    			shouldExit = true;
	    			System.exit(0);
	    			break;
	    		}
	    		
	    		if(Keyboard.getKbPrevState()[KeyEvent.VK_C])
	    		{
	    			cont = false;
	    			shouldExit = true;
	    		}
				if(t < System.nanoTime() - delta)
				{
					if(ct == 0)
					{
						enter.setText("Press C to Play...");
						ct = 1;
					}else{
						enter.setText("Press C to Play..");
						ct = 0;
					}
					delta = System.nanoTime();
				}
				Window.clearWindow();
				enter.draw();
				enter1.draw();
				CS134.draw();
				esc.draw();
			}
			enter = null;
			enter1 = null;
			CS134 = null;
			esc = null;
			
		}
	}
	
	public static void main(String[] args)
	{
    	/*Physics Implementation*/
    	int physicsDeltaMs = 10;
    	int lastPhysicsFrameMS = 0;
		int curFrameMs = 100;
		
		
		Window.createWindow(800, 600, "Infect");
		
    	/*Initialize Keyboard*/
    	Keyboard.initializeKb();

    	menu.run();
    	
    	/*Initialize UI*/System.out.println("initializing ui..");
    	Footer footerUI = new Footer("uiBg.tga");
    	ArrowKeys arrows = new ArrowKeys();

    	
    	/*Initialize Objects*/System.out.println("initializing objects..");
    	/*Map*/
    	Level map = new Level(1);
    	
    	/*Main Player*/
    	Player p1 = new Player("snail", 1, false);
    	p1.lifeDuration  = 199999999.0f;
    	
    	/*Bots*/
    	Player b0 = new Player("bot0", 1, true, p1);
    	Player b1 = new Player("bot1", 2, true, p1);
    	Player b2 = new Player("bot2", 1, true, p1);
    	Player b3 = new Player("bot3", 2, true, p1);
    	Player b4 = new Player("bot4", 1, true, p1);
    	Player b5 = new Player("bot5", 1, true, p1);
    	Player b6 = new Player("bot6", 1, true, p1);
    	Player b7 = new Player("bot7", 1, true, p1);
    	Player b8 = new Player("bot8", 1, true, p1);
    	Player b9 = new Player("bot9", 1, true, p1);
    	Player b10 = new Player("bot10", 1, true, p1);
    	ArrayList<Player> bots = new ArrayList<Player>();
    	ArrayList<Player> zombies = new ArrayList<Player>();
    	
    	
    	//bots.add(b0);
    	bots.add(b1);
    	//bots.add(b2);
    	bots.add(b2);
    	bots.add(b3);
    	bots.add(b4);
    	bots.add(b5);
    	bots.add(b6);
    	bots.add(b7);
    	bots.add(b8);
    	//bots.add(b9);
    	//bots.add(b10);
    	
    	/*powerups*/
    	PowerUp wasd = new PowerUp(10, 0); wasd.keyboard(arrows);
    	PowerUp wasd1 = new PowerUp(10, 0); wasd1.keyboard(arrows);
    	PowerUp wasd2 = new PowerUp(10, 0); wasd2.keyboard(arrows);
    	PowerUp speedUp = new PowerUp(3, 1);
    	ArrayList<PowerUp> powerups = new ArrayList<PowerUp>();
    	powerups.add(wasd);
    	powerups.add(wasd1);
    	powerups.add(wasd2);
    	powerups.add(speedUp);
    	
    	
    	//sets p1 initial position
    	p1.Pos[0] = 700; p1.Pos[1] = 200;
    	Camera.x = 300;

    	
    	/*initialize Texts*/
    	ArrayList<Text> texts = new ArrayList<Text>();
    	Text youLoose = new Text("You LOOSE"); youLoose.setColor(Color.red); youLoose.setSize(65);
    	youLoose.setX(Window.window.getWidth()/2-100); youLoose.setY(Window.window.getHeight()/2);
    	
    	Text levelT = new Text("Level 1"); levelT.setColor(Color.white); levelT.setSize(32);
    	levelT.setX(535); levelT.setY(152);
    	Text timeLeft = new Text("HP Left: "); timeLeft.setColor(Color.white); timeLeft.setSize(30);
    	timeLeft.setX(535); timeLeft.setY(120);
    	Text time = new Text("100"); time.setColor(Color.white); time.setSize(30);
    	time.setX(720);	time.setY(120);
    	Text botT = new Text("Bots Left: "); botT.setColor(Color.white); botT.setSize(30);
    	botT.setX(535); botT.setY(90);
    	Text botN = new Text(bots.size()+""); botN.setColor(Color.white); botN.setSize(30);
    	botN.setX(720); botN.setY(90);
    	Text zomb = new Text("Zombies : "); zomb.setColor(Color.white); zomb.setSize(30);
    	zomb.setX(535);	zomb.setY(60);
    	Text zombN = new Text(zombies.size()+""); zombN.setColor(Color.white); zombN.setSize(30);
    	zombN.setX(720); zombN.setY(60);
    	Text enterToAdv = new Text("Press C to advance levels"); enterToAdv.setColor(Color.red); enterToAdv.setSize(30);
    	enterToAdv.setX(Window.window.getWidth()/2-100);enterToAdv.setY(200);
    	texts.add(zombN);
    	texts.add(zomb);
    	texts.add(botN);
    	texts.add(botT);
    	texts.add(timeLeft);
    	texts.add(time);
    	texts.add(levelT);
    	
    	
    	/*Load Textures*/System.out.println("initializing textures..");
    	AnimationDef mainIdleLeft = new AnimationDef("mainIdleLeft", 1, new String[]{"mainLI0.tga"});
    	AnimationDef mainIdleRight = new AnimationDef("mainIdleRight", 1, new String[]{"mainRI0.tga"});
    	AnimationDef mainMoveLeft = new AnimationDef("mainMoveLeft", 4, new String[]{"mainLM0.tga","mainLM1.tga","mainLM2.tga","mainLM3.tga"});
    	AnimationDef mainMoveRight = new AnimationDef("mainMoveRight", 4, new String[]{"mainRM0.tga","mainRM1.tga","mainRM2.tga","mainRM3.tga"});
    	AnimationDef mainLeftDead = new AnimationDef("mainLeftDead", 4, new String[]{"mainLH0.tga","mainLD0.tga","mainLD1.tga","mainLD2.tga"});
    	AnimationDef mainRightDead = new AnimationDef("mainDeadRight", 4, new String[]{"mainRH0.tga","mainRD0.tga","mainRD1.tga","mainRD2.tga"});
        AnimationDef powerUpDef = new AnimationDef("powerUp", 4, new String[]{"powerUp0.tga","powerUp1.tga","powerUp2.tga","powerUp3.tga"});
        AnimationDef bmIdleLeft = new AnimationDef("bmIdleLeft", 2, new String[]{"bmLI0.tga","bmLI1.tga"});
        AnimationDef bmIdleRight = new AnimationDef("bmIdleRight", 2, new String[]{"bmRI0.tga","bmRI1.tga"});
        AnimationDef bmLeftDeath = new AnimationDef("bmLeftdeath", 4, new String[]{"bmLH0.tga","bmLD0.tga","bmLD1.tga","bmLD2.tga"});
        AnimationDef bmRightDeath = new AnimationDef("bmRightDeath", 4, new String[]{"bmRH0.tga","bmRD0.tga","bmRD1.tga","bmRD2.tga"});
        AnimationDef bmLeftJMove = new AnimationDef("bmLeftJMove", 3, new String[]{"bmLJM0.tga","bmLJM1.tga","bmLJM2.tga"});
        AnimationDef bmRightJMove = new AnimationDef("bmRightMove", 3, new String[]{"bmRJM0.tga","bmRJM1.tga","bmRJM2.tga"});
        AnimationDef zmLeftMove = new AnimationDef("zmLeftMove", 4, new String[]{"zmLM0.tga","zmLM1.tga","zmLM2.tga","zmLM3.tga"});
        AnimationDef zmRightMove = new AnimationDef("zmRightMove", 4, new String[]{"zmRM0.tga","zmRM1.tga","zmRM2.tga","zmRM3.tga"});
        AnimationDef leftTransform = new AnimationDef("leftTransofmr", 6, new String[]{"bmLD0.tga","bmLD1.tga","bmLD2.tga","zmLD0.tga","zmLD1.tga","zmLD2.tga"});
        AnimationDef rightTransform = new AnimationDef("rightTransform", 6, new String[]{"bmRD0.tga","bmRD1.tga","bmRD2.tga","zmRD0.tga","zmRD1.tga","zmRD2.tga"});
        
    	p1.idleLeft.def = mainIdleLeft; p1.idleLeft.animate = false;
    	p1.idleRight.def = mainIdleRight; p1.idleRight.animate = false;
    	p1.rightMove.def = mainMoveRight;
    	p1.leftMove.def = mainMoveLeft;
    	p1.rightDead.def = mainRightDead;
    	p1.leftDead.def = mainLeftDead;
		p1.setShape();p1.shape.setWidth(35); p1.shape.setHeight(35);
		
		
		/*bots*/
		for(int i = 0; i < bots.size(); i++)
		{
			bots.get(i).idleLeft.def = bmIdleLeft;
			bots.get(i).idleRight.def = bmIdleRight;
			bots.get(i).rightMove.def = bmRightJMove;
			bots.get(i).leftMove.def = bmLeftJMove;
			bots.get(i).rightDead.def = bmRightDeath;
			bots.get(i).leftDead.def = bmLeftDeath;
			bots.get(i).leftTransform.def = leftTransform;
			bots.get(i).rightTransform.def = rightTransform;
			bots.get(i).zmLeftMove.def = zmLeftMove;
			bots.get(i).zmRightMove.def = zmRightMove;
			bots.get(i).setShape();
		}
		
		powerUpDef.setFrameTime(88888888);
		wasd.a.def = powerUpDef;
		wasd1.a.def = powerUpDef;
		wasd2.a.def = powerUpDef;
		speedUp.a.def = powerUpDef;
		wasd.setShape();
		wasd1.setShape();
		wasd2.setShape();
		speedUp.setShape();
    	
    	/*Extra*/
    	System.out.println("Opening window size: " +Window.window.getWidth()+" x "+Window.window.getHeight());
    	
    	for(int i = 0 ; i < powerups.size(); i++)
    	{
    		while(p1.isNear(powerups.get(i).Pos))
    		{
    			powerups.get(i).generatePosition();
    		}
    	}

		Camera.deltaDuration = 300000.0f;
		Camera.deltaTime = System.nanoTime();
    	/*Game Loop*/
    	shouldExit = false;
    	while(!shouldExit && !gameWin)
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
    		
    		/*updates*/
    		arrows.update();//arrows
    		p1.updateMovement(arrows);//player1
    		
    		if(p1.dead){//camera scroll if p1 is dead
    			Camera.scrollCamera();
    		}
    		
    		if(!bots.isEmpty()){
	    		for(int i = 0; i < bots.size(); i++)//bots
	    		{
	    			bots.get(i).updateMovement(arrows);
	    		}
    		}
    		if(!zombies.isEmpty())//zombies
    		{
    			for(int i = 0 ; i < zombies.size(); i++)
    			{
    				zombies.get(i).updateMovement(arrows);
    			}
    		}
    		
    		
    		
    		/*Physics Update*/
    		
    		do{
    			Camera.boundaryUpdate();
    			p1.boundaryCheck();
    			
    		
    		
    			
    			/*check powerup collision*/
    		
    			if(!p1.dead){
	    			for(int i = 0 ; i < powerups.size(); )
	    			{
	    				if(p1.isNear(powerups.get(i).Pos))
	    				{
		    				if(p1.collisionCheck(powerups.get(i).shape))
		    				{
		    					p1.addPowerUps(powerups.get(i));
		    					powerups.remove(i);
		    				}
	    				}
	    				i++;
	    			}
    			
    			
	    			/*bot hit*/
	    		
	    			for(int i = 0 ;i < bots.size(); )
	    				{
		    				if(p1.isNear(bots.get(i).Pos))
		    				{
			    				if(bots.get(i).collisionCheck(p1.shape))
			    				{
			    					p1.health += 5;
			    					bots.get(i).zombie();
			    					zombies.add(bots.get(i));
			    					bots.remove(i);
			    					i--;
			    					}
			    				}
		    				i++;
		    				}
	    			}
    		
    			
    		
    			/*bot to bot*/
    		
    			if(!bots.isEmpty() && !zombies.isEmpty())
    			{
    				for(int i = 0 ; i < zombies.size(); i++)
    				{
    					for(int j = 0; j < bots.size(); )
    					{
    						if(zombies.get(i).isNear(bots.get(j).Pos))
    						{
	    						if(zombies.get(i).collisionCheck(bots.get(j).shape))
	    						{
	    							if(zombies.get(i).transformed){
		    							bots.get(j).zombie();
		    							zombies.add(bots.get(j));
		    							bots.remove(j);
		    							j--;
	    							}
	    						}
    						}
    							j++;
    					}
    				}
    			}
    			lastPhysicsFrameMS += physicsDeltaMs;
    		}while(lastPhysicsFrameMS + physicsDeltaMs < curFrameMs);
    		lastPhysicsFrameMS = 0;
    		
    		/*Other updates*/
    		p1.buffUpdate();
			
    		/*extra*/
    		if(p1.dead && !won && !loose){//p1 is dead and winning or loosing isn't decided
    			afterDeath = true;
    			countdown();
	    		
	    		if(countDown <= 0)
	    		{
	    			if(!bots.isEmpty())
	    			{
	    				loose = true;
	    				won = false;
	    			}else{
	    				loose = false;
	    				won = true;
	    			}
	    		}
    		}else if(!won && !loose){
    			if(bots.isEmpty())
    			{
    				won = true;
    				loose = false;
    			}
    		}
    		
    		
    		/*Clear Last Frame*/
    		Window.clearWindow();
    		
    		/*Draw*/
    		map.draw();
    		
    		if(!powerups.isEmpty())
    		{
	    		for(int i = 0; i < powerups.size(); i++)//powerups
	    		{
	    			powerups.get(i).draw();
	    		}
    		}
    		
    		if(!bots.isEmpty())
    		{
	    		for(int i = 0 ;i < bots.size(); i++)//bots
	    		{
	    			bots.get(i).draw(arrows);
	    		}
    		}
    		if(!zombies.isEmpty())
    		{
    			for(int i = 0 ;i < zombies.size(); i++)
    			{
    				zombies.get(i).draw(arrows);
    			}
    		}
    		
    		p1.draw(arrows);//player
    		footerUI.draw();//footer ui
    		arrows.draw();//arrow keys
    		
    		time.setText(p1.getHealth()+"");
    		botN.setText(bots.size()+"");
    		zombN.setText(zombies.size()+"");
    		levelT.setText("Level "+level);
    		
    		
    		
    		if(!texts.isEmpty())//texts
    		{
    			for(int i = 0 ;i < texts.size(); i++)
    			{
    				texts.get(i).draw();
    			}
    		}
    		
    		if(loose && !won)
    		{
    			youLoose.setText("YOU LOOSE!!!");
    			youLoose.setColor(Color.pink);
    			enterToAdv.setColor(Color.white);
    			enterToAdv.setText("Press R to restart | Q to quit");
    			enterToAdv.draw();
    			youLoose.draw(); 
    			
    			if(Keyboard.getKbState()[KeyEvent.VK_R])//restart level
    			{
    				Camera.x = 0;    				
    				p1.restart();
    				if(!zombies.isEmpty()){//restart zombie array
    					for(int i = 0 ;i < zombies.size(); i++)
    					{
    						bots.add(zombies.get(i));
    					}
    					zombies.clear();
    				}
    				for(int i = 0 ; i < bots.size(); i++)
    				{
    					bots.get(i).restart();
    				}
    				//powerups
    				powerups.clear();
    				powerups.add(wasd);
    				powerups.add(wasd1);
    				powerups.add(wasd2);
    				powerups.add(speedUp);
    				for(int i = 0 ;i < powerups.size(); i++)
    				{
    					powerups.get(i).generatePosition();
    					powerups.get(i).setShape();
    				}
    				countDown = 10;
    				loose = false;
    				won = false;
    				afterDeath = false;
    				arrows.deactivateWASD();
    				System.out.println("Level restarted");
    			}else if(Keyboard.getKbState()[KeyEvent.VK_Q])
    			{
    				System.exit(0);
    			}
    		}else if(won && !loose){
    			youLoose.setText("YOU WIN!!!");
    			youLoose.setColor(Color.green);
    			enterToAdv.setColor(Color.white);
    			enterToAdv.setText("Press C to advance");
    			enterToAdv.draw();
    			youLoose.draw();
    			
    			if(Keyboard.getKbState()[KeyEvent.VK_C])//continue levels
    			{
    				Camera.x = 0;
    				p1.restart();
    				p1.lifeDuration  -= 8999999.0f;
    				for(int i = 0; i < zombies.size()-4; i++)
    				{
    					zombies.get(i).restart();
    					bots.add(zombies.get(i));
    					
    				}
    				zombies.clear();
    				//powerups
    				powerups.clear();
    				powerups.add(wasd);
    				powerups.add(wasd1);
    				powerups.add(wasd2);
    				powerups.add(speedUp);
    				for(int i = 0 ;i < powerups.size(); i++)
    				{
    					while(p1.isNear(powerups.get(i).Pos))
    					{
	    					powerups.get(i).generatePosition();
	    					powerups.get(i).setShape();
    					}
    				}
    				countDown = 10;
        			level++;
    				loose = false;
    				won = false;
    				afterDeath = false;
    				arrows.deactivateWASD();
    				System.out.println("Advancing to Level "+level);
    			}
    		}
    		else if(afterDeath && !loose && !won)
    		{
    			youLoose.setText(""+countDown);
    			youLoose.setColor(Color.green);
    			youLoose.draw();
    		}
    		/*Update Arrows*/
    		arrows.updateLetters();
    		
    		if(bots.isEmpty() && zombies.isEmpty())
    		{
    			gameWin = true;
    		}
    
    		
    	
    	}
    	
    	while(gameWin)
    	{
    		System.arraycopy(Keyboard.getKbState(), 0, Keyboard.getKbPrevState(), 0, Keyboard.getKbState().length);
			Window.window.display();
			if(!Window.window.isVisible())
    		{
				gameWin = false;
				System.exit(0);
    		}
			youLoose.setText("YOU WIN");
			youLoose.setX(400);
			youLoose.setColor(Color.white);
			
			Window.clearWindow();
			youLoose.draw();
    	}
    	System.exit(0);
	}

	

	private static void countdown() {
		if(afterDeathDelta == 0.0f)
		{
			afterDeathDelta = System.nanoTime();
		}
		
		if(afterDeathDuration < System.nanoTime() - afterDeathDelta)
		{
			countDown--;
			afterDeathDelta = System.nanoTime();
		}
	}

	
}
