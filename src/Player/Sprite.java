package Player;
import com.jogamp.nativewindow.util.Rectangle;

import Animation.*;
import Camera.*;

public class Sprite {

	/*Position of Sprite (x, y)*/
	int[] Pos = new int[2];
	/*Position of Sprite relative to Camera*/
	int[] rPos = new int[2];
	
	/*Texture of Sprites*/
	/*
	 * 0 = idleLeft
	 * 1 = moveLeft
	 * 2 = idleRight
	 * 3 = moveRight
	 * 4 = idleStraight
	 * 5 = jump
	 */
	AnimationData[] animationFrames = new AnimationData[6];
	int curAnimationFrame;
	
	/*Shape for collision checks*/
	Rectangle shape;
	
	/*Player Stats*/
	String name;
	short direction = 0;
	int speed;
	int health;
	boolean isAi = false;
	boolean mainCharacter = false;
	boolean isAimoving = false;
	boolean onScreen = true;
	boolean inJump = false;
	boolean beginJump = false;
	boolean noClip = false;
	
	/*Player's Various Weapons*/
	Projectiles[] projectiles;

	public Sprite(String name, int speed, boolean mainCharacter)
	{
		this.name = name;
		this.speed = speed; 
		this.isAi = false;
		this.mainCharacter = mainCharacter;
		//Set camera speed as the player's moving speed too if it's not Ai
		if(mainCharacter)
		{
			Camera.movingSpeed = this.speed;
		}
		this.health = 100;
		
		for(int i = 0; i < animationFrames.length; i++)
		{
			animationFrames[i] = new AnimationData();
		}
		curAnimationFrame = 2;
		
		shape = new Rectangle(getX(), getY(), getWidth(), getHeight());
	}
	
	public boolean collisionCheck(Sprite player)
	{
		//uses AABB check
		Rectangle s = player.shape;
		return collisionCheck(s);
	}
	
	public boolean collisionCheck(Rectangle s)
	{
		//using AABB check
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

	public void updateRelativePosition()
	{
		rPos[0] = Pos[0]
	}
	/*Getters and setters*/
	public int[] getPos() {
		return Pos;
	}
	public void setPos(int[] pos) {
		Pos = pos;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setX(int x) {
		Pos[0] = x;
	}
	public void setY(int y){
		Pos[1] = y;
	}
	public int getY() {
		return Pos[1];
	}
	public int getX() {
		return Pos[0];
	}
	public int getHealth() {
		return health;
	}
	public void setHealth(int health) {
		this.health = health;
	}
	public int getWidth() {
		return animationFrames[curAnimationFrame].getFrameWidth();
	}
	public int getHeight() {
		return animationFrames[curAnimationFrame].getFrameHeight();
	}
}
