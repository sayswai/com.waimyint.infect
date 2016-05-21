package Player;

import java.time.Clock;
import java.util.ArrayList;
import java.util.Random;

import UI.ArrowKeys;

import com.jogamp.nativewindow.util.Rectangle;

import Animation.*;
import Camera.*;
import Controllers.*;

public class Player {
	public class AnimationData {

		public boolean initiated = false;
		public boolean animate = true;
		public boolean firstRun = false;
		public AnimationDef def;
		int counter, curFrameTex;
		//int[] curFrameSize = new int[2];
		float deltaTime, delta;
		
		void update()
		{
			if(animate){//animating
				if(!initiated){
					initiated();
				}
					delta = System.nanoTime() - deltaTime;
					if(delta >= def.getFrameTime(counter)){
						if(counter == def.frames.length - 1)
						{
							counter = 0;
						}else{
							counter++;
							
						}
						//curFrameTex = def.getFrameTex(counter);
						//curFrameSize = def.getSizes(counter);
						if(counter == def.frames.length - 1 && !firstRun){
							firstRun = true;
						}
						deltaTime = System.nanoTime();
					}
					
			}else{//not animating
				if(!initiated)
				{
					counter = 0; 
					initiated = true;
				}
			}
		}

		public int getFrameWidth() {
			if(!initiated)
				initiated();
			return def.getTexWidth(counter);
		}

		public int getFrameHeight() {
			if(!initiated)
				initiated();
			return def.getTexHeight(counter);
		}
		
		private void initiated()
		{
			if(!initiated)
			{
				counter = 0;
				//curFrameTex = def.getFrameTex(counter);
				deltaTime = System.nanoTime();
				//curFrameSize = def.getSizes(counter);
				initiated = true;
			}
		}
		
		public void draw()
		{
			TGAController.glDrawSprite(def.getFrameTex(counter), rPos[0], rPos[1], def.getTexWidth(counter)+10, def.getTexHeight(counter)+10);
		}
	}
	
	public class BuffNode {

		public PowerUp skill;
		public float deltaTime;
		int type, value;
		public boolean on, exhausted;
		
		public BuffNode(PowerUp skill)
		{
			this.skill = skill;
			this.type = skill.type;
			this.value = skill.value;
			this.deltaTime = System.nanoTime();
			on = false;
			exhausted = false;
		}
		
		public boolean update()
		{
				if(skill.duration > System.nanoTime() - deltaTime)//buff hasn't worn out
				{
					if(!on)
					{
						on = true;
						if(skill.type == 0)//wasd 
						{
								skill.arrows.activateWASD();
								return false;
						}else if(skill.type == 1)//speedup
						{
							
								System.out.println("speed up activatd");
								speed += value;
								return false;
						}
					}
					return false;
				}else{//buff is worn out
					if(on)//turn off buff if it was previously on
					{
						on = false;
						if(skill.type == 0)
						{
							skill.arrows.deactivateWASD();
						}else if(skill.type == 1)
						{
							System.out.println("Speed up deactivated");
							speed -= value;
						}
					}
				}
			
			return true;
		}
	}
	/*Previous position*/
	int[] prevPos = new int[]{0, 0};
	/*Position of Sprite (x, y)*/
	public int[] Pos = new int[2];
	/*Position of Sprite relative to Camera*/
	int[] rPos = new int[2];
	
	/*Texture of Sprites*/
	public AnimationData idleLeft, idleRight, rightMove, leftMove, leftDead, rightDead, leftHit, rightHit, idleTex, zmLeftMove, zmRightMove, leftTransform, rightTransform;
	public AnimationData curr;
	
	/*Shape for collision checks*/
	public Rectangle shape;
	Random r;
	/*Player Stats*/
	String name;
	public static final short LEFT = 0;
	public static final short RIGHT = 1;
	public static final short UP = 2;
	public static final short DOWN = 3;
	short direction = LEFT;
	short vertical = UP;
	int speed;
	public int health;
	boolean isAi = false;
	boolean isAimoving = false;
	boolean onScreen = true;
	boolean inJump = false;
	boolean beginJump = false;
	boolean noClip = false;
	public boolean dead = false;
	public boolean dying = false;
	
	
	/*Buffs*/
	ArrayList<BuffNode> buffs;
	
	/* AI */
	public Player leader;
	public static final int IDLE = 1;
	public static final int AVOID = 2;
	public static final int AWAY = 3;
	public static final int ZOMBIE = 10;
	public short behavior = IDLE;//1 = idle, 2 = walk around, 3 = walk away from player, 4 = random location
	public float behaviorDelta, behaviorDuration, destChangeDuration, destChangeDelta;
	public int[] dest = new int[2];
	public int[] lastDest = {0,0};
	
	/*Zombie*/
	public boolean transformed = false;
	
	/*Frame Updates*/
	float lastFrameTime;
	public float lifeDuration;
	float lifeDelta;
	
	/*Player's Various Weapons*/
	Projectiles[] projectiles;

	public Player(String name, int speed, boolean isAi)
	{
		this.name = name;
		this.speed = speed; 
		this.isAi = isAi;
		//Set camera speed as the player's moving speed too if it's not Ai
		if(!isAi)
		{
			Camera.movingSpeed = this.speed;
		}
		this.health = 100;
		
		idleLeft = new AnimationData();
		idleRight = new AnimationData();
		rightMove = new AnimationData();
		leftMove = new AnimationData();
		leftDead = new AnimationData();
		rightDead = new AnimationData();
		//leftHit = new AnimationData();
		//rightHit = new AnimationData();
		leftTransform = new AnimationData();
		rightTransform = new AnimationData();
		zmLeftMove = new AnimationData();
		zmRightMove = new AnimationData();
		idleTex = new AnimationData();
		curr = idleLeft;
		
		buffs = new ArrayList<BuffNode>();
		lifeDelta = System.nanoTime();
		
	}
	
	public Player(String name, int speed, boolean isAi, Player leader)//ai constructor
	{
		this(name, speed, isAi);
		this.leader = leader;
		if(isAi)
		{
			r = new Random();
			if((r.nextInt(2)+1)==1){
				Pos[0] = r.nextInt(600) + 1;
			}else{
				Pos[0] = r.nextInt(1530) + 800;
			}
			Pos[1] = r.nextInt(350) + 1;
			setNewDest(); 
			destChangeDelta = System.nanoTime();
			behavior = (short)(r.nextInt(3) + 1);//set bot's behavior
			behaviorDelta = System.nanoTime();
			behaviorDuration = r.nextFloat() * (15000000000.0f - 6000000000.0f) + 6000000000.0f;//Random seconds from 10 - 1
			destChangeDuration = 599999999.0f; 
			System.out.println(name+"'s behavior "+behavior+". it'll change every "+(behaviorDuration/1000000000.0f)+" seconds");
			updateRelativePosition();
		}
	}
	public void restart() {
		leftTransform.firstRun = false;
		rightTransform.firstRun = false;
		dead = false;
		dying = false;
		transformed = false;
		if(isAi)//bot
		{	
			if(behavior == ZOMBIE)
			{
				speed--;
			}
			behavior = 2;
			behaviorDelta = System.nanoTime();
			r = new Random();
			if((r.nextInt(2)+1)==1){
				Pos[0] = r.nextInt(600) + 1;
			}else{
				Pos[0] = r.nextInt(1530) + 800;
			}
			Pos[1] = r.nextInt(350) + 1;
		}else{
			health = 100;
			Pos[0] = 700;
			Pos[1] = 200;
			buffs.clear();
		}
		updateRelativePosition();
	}
	public void zombie() {
		if(behavior != ZOMBIE)
		{
			behavior = ZOMBIE;
			speed++;
			System.out.println(name+" is now a zombie!!!!!");
			name.replace("name", "zombie");
		}

		transform();
	}
	private void transform() {
		if(!transformed){
			if(direction == LEFT)
			{
				if(!leftTransform.firstRun)
				{
					curr = leftTransform;
				}else{
					transformed = true;
				}
			}else{
				if(!rightTransform.firstRun)
				{
					curr = rightTransform;
				}else{
					transformed = true;
				}
			}
		}
	}
	public void updateMovement(ArrowKeys arrows) {
		//lastFrameTime = System.nanoTime();
		
		prevPos[0] = Pos[0];
		prevPos[1] = Pos[1];
		if(!isAi && !dying && !dead)//player update
		{
			/*save camera's last position*/
			Camera.prevX = Camera.x;
			Camera.prevY = Camera.y;
			
			/*update position*/
			if(arrows.left.down)//left
			{
				Pos[0] -= speed;
				//TODO update camera movement here
				if(Pos[0] < (Window.window.getWidth() * 1.5))
				{
					Camera.x -= speed;
				}
				direction = 0;
				curr = leftMove;
			}
			if(arrows.right.down)//right
			{
				Pos[0] += speed;
				if(Pos[0] > Window.window.getWidth() / 2)
				{
					Camera.x += speed;
				}
				direction = 1;
				curr = rightMove;
			}
			if(arrows.up.down)//up
			{
				Pos[1] -= speed;
				vertical = UP;
			}
			if(arrows.down.down)//down
			{
				Pos[1] += speed;
				vertical = DOWN;
			}
			updateRelativePosition();
			updateClock();
		}else if(dying && !dead)
		{
			die();
		}else if(dead)
		{
			Pos[0] = -500;
			Pos[1] = -500;
		}else if(isAi){//bot update
			updateAI();
			updateRelativePosition();
			shape.setX(Pos[0]); shape.setY(Pos[1]);
		}
		
		
	}

	private void die()
	{
		if(dying){
			if(direction == LEFT)
			{
				if(!leftDead.firstRun)
				{
					curr = leftDead;
				}else{
					dead = true;
				}
			}else{
				if(!rightDead.firstRun)
				{
					curr = rightDead;
				}else{
					dead = true;
				}
			}
		}
	}
	public void updateClock() {
		if(lifeDuration < System.nanoTime() - lifeDelta)
		{
			health -= 1;
			lifeDelta = System.nanoTime();
			if(health <= 0)
			{
				health = 0;
				dying = true;
				System.out.println("P1 has died.");
			}
		}
	}

	public void updateAI()
	{
		if(behavior != ZOMBIE)
		{
			if(behaviorDuration < System.nanoTime() - behaviorDelta)//update behaviors
			{
			
				r = new Random();
				behavior = (short)(r.nextInt(4) + 1);//set bot's to a random behavior
				if(leader.dead && behavior == AWAY)
				{
					behavior = AVOID;
				}
				if(behavior == IDLE || behavior == AVOID)
				{
					behaviorDuration = r.nextFloat() * (2000000000.0f - 900000000.0f) + 900000000.0f;//Random seconds from 4 - 2 for duration
				}
				if(behavior == AWAY)
				{
					behaviorDuration = r.nextFloat() * (15000000000.0f - 7000000000.0f) + 7000000000.0f;
				}
				behaviorDelta = System.nanoTime();
				setNewDest();
			}
		}else{//zombie behavior
				if(transformed)//transformation is complete
				{
					aiWalk();
				}else{
					transform();
				}
		}
		
		if(behavior == IDLE)
		{
			
			aiIdle();
		}
		else if(behavior == AWAY)
		{
			aiAway();
		}
		else if(behavior != ZOMBIE) 
		{
			aiAvoid();
		}
	}
	
	

	private void aiWalk() {
		if(dest[0] > Pos[0])
		{
			Pos[0] += speed;
			direction = RIGHT;
		}
		if(dest[0] < Pos[0])
		{
			Pos[0] -= speed;
			direction = LEFT;
		}
		
		
		if(dest[1] > Pos[1])
		{
			Pos[1] += speed;
		}
		if(dest[1] < Pos[1])
		{
			Pos[1] -= speed;
		}
		
		if(Pos[0] == dest[0] && Pos[1] == dest[1] || prevPos[0] == Pos[0] && prevPos[1] == Pos[1])
		{
			setNewDest();
		}else{
			if(direction == RIGHT)
			{
				curr = zmRightMove;
			}else{
				curr = zmLeftMove;
			}
		}
	}

	private void aiAway()
	{
		if(leader.Pos[0] >0 && leader.Pos[0] <400)
		{
			dest[0] = r.nextInt(1530)+1200;
		}else if(leader.Pos[0] >= 400 && leader.Pos[0] < 800)
		{
			dest[0] = r.nextInt(1530)+1200;
		}else if(leader.Pos[0] >= 800 && leader.Pos[0] < 1200)
		{
			dest[0] = r.nextInt(400) + 0;
		}else if(leader.Pos[0] >= 1200 && leader.Pos[0] < 1600)
		{
			dest[0] = r.nextInt(400) + 0;
		}
		moveX();
		
			if(((Pos[0] >= 1200 && Pos[0] < 1600) || (Pos[0] >= 800 && Pos[0] < 1200) && leader.Pos[0] > 0 && leader.Pos[0] < 400) || (((Pos[0] >= 0 && Pos[0] < 400)||(Pos[0] >= 400 && Pos[0] < 800)) && leader.Pos[0] >= 1200 && leader.Pos[0] < 1600)){
				if(leader.vertical == DOWN)
				{
					if((Pos[1] + speed) <= 320)
					{
						Pos[1] += speed;
					}
					
				}else if(leader.vertical == UP)
				{
					
					if((Pos[1] - speed) >= 0)
					{
						Pos[1] -= speed;
					}
				}
			}else{
				if(leader.vertical == DOWN){
					if((Pos[1] + speed) <= 320)
					{
						Pos[1] += speed;
					}
				}else if(leader.vertical == UP)
				{
					if((Pos[1] - speed) >= 0)
					{
						Pos[1] -= speed;
					}
				}
				
			}
		}
		
	
	private void moveX() {
		if(dest[0] > Pos[0])
		{
			Pos[0] += speed;
			direction = RIGHT;
		}
		if(dest[0] < Pos[0])
		{
			Pos[0] -= speed;
			direction = LEFT;
		}
		if(direction == RIGHT)
		{
			curr = rightMove;
		}else{
			curr = leftMove;
		}
	}

	private void aiAvoid() {
		if(aiIsNearLeader())
		{
			if(canIChangeDest())
			{
			setNewDest();
			}
		}
		aiWalkToDest();
	}

	private boolean aiIsNearLeader() {
		if(Pos[0] < (leader.Pos[0]+40+leader.getWidth()))
		{
			if(Pos[0] > (leader.Pos[0] - 40))
			{
				return true;
			}
		}
		if(Pos[1] < (leader.Pos[1]+20+leader.getHeight()))
		{
			if(Pos[1] > (leader.Pos[1]-20))
			{
				return true;
			}
		}
		return false;
	}

	private void aiIdle() {
		if(direction == LEFT)
		{
			curr = idleLeft;
		}else{
			curr = idleRight;
		}
	}

	
	private void setNewDest()
	{
				lastDest[0] = dest[0];
				lastDest[1] = dest[1];
				dest[0] = r.nextInt(1530) + 1;
				dest[1] = r.nextInt(325) + 5;
		
				
	}

	private boolean canIChangeDest()
	{
		if(destChangeDuration < System.nanoTime() - destChangeDelta)
		{
			destChangeDelta = System.nanoTime();
			return true;
		}
		return false;
	}
	private void aiWalkToDest() {
		
		if(dest[0] > Pos[0])
		{
			Pos[0] += speed;
			direction = RIGHT;
		}
		if(dest[0] < Pos[0])
		{
			Pos[0] -= speed;
			direction = LEFT;
		}
		
		
		if(dest[1] > Pos[1])
		{
			Pos[1] += speed;
		}
		if(dest[1] < Pos[1])
		{
			Pos[1] -= speed;
		}
		
		
		if(direction == RIGHT)
		{
			curr = rightMove;
		}else{
			curr = leftMove;
		}
			
		if(dest[0] == Pos[0] && dest[1] == Pos[1])
		{
			behaviorDuration = 1000000000.0f;
			behavior = IDLE;
			aiIdle();
		}
	}

	

	public boolean collisionCheck(Player player)
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
		rPos[0] = Pos[0] - Camera.x;
		rPos[1] = Pos[1] - Camera.y;
		/*Update onScreen value*/
		if(rPos[0] >= -70 && rPos[0] <= Window.WINDOW_WIDTH)
		{
			if(rPos[1] >= 0 && rPos[1] <= 400)
			{
				onScreen = true;
			}else{
				onScreen = false;
			}
		}else{
			onScreen = false;
		}
	}
	
	public boolean isNear(int[] pp)
	{
		if((Pos[0]-200) < pp[0] && (Pos[0]+200) > pp[0])
		{
			if((Pos[1]-50) < pp[1] && (Pos[1]+100) > pp[1])
			{
				return true;
			}
		}
		return false;
	}
	public void draw(ArrowKeys arrows)
	{
		if(onScreen)
		{
			if(!isAi)//player draw update
			{
				if(!dying && !dead){
					if(direction == LEFT && arrows.left.down)
					{
						leftMove.update();
						leftMove.draw();
					}else if(direction == RIGHT && arrows.right.down)
					{
						rightMove.update();
						rightMove.draw();
					}else if(direction == LEFT)
					{
						idleLeft.update();
						idleLeft.draw();
					}else{
						idleRight.update();
						idleRight.draw();
					}
				}else if(dying && !dead)
				{
						curr.update();
						curr.draw();
				}
			}else{//AI draw
				curr.update();
				curr.draw();
			}
		}
	}
	
	public void boundaryCheck() {
		if(!dead){
			/*Off-map check*/
			if(Pos[0] < 0 || Pos[0] > 1535)
			{
				Pos[0] = prevPos[0];
				if(!isAi){
				Camera.x = Camera.prevX;
				}
				updateRelativePosition();
			}
			if(isAi)
			{
				if(Pos[1] > 340)
				{
					Pos[1] = prevPos[1];
					updateRelativePosition();
				}
			}else{
				if(Pos[1] < 0 || Pos[1] > 350)
				{
					Pos[1] = prevPos[1];
					Camera.y = Camera.prevY;
					updateRelativePosition();
				}
			}
			
			
			
			/*update shape*/
			shape.setX(Pos[0]); shape.setY(Pos[1]);
		}
	}
	
	public void setShape()
	{
		//TODO hit squares, modify here
		shape = new Rectangle(getX(), getY(), getWidth(), getHeight());
		if(isAi){
		shape.setWidth(62); shape.setHeight(55);
		}
	}
	
	public void addPowerUps(PowerUp powerUp) {
		buffs.add(new BuffNode(powerUp));
	}
	
	public void buffUpdate() {
		if(!buffs.isEmpty())
		{
			for(int i = 0; i < buffs.size();)
			{
				if(buffs.get(i).update())
				{
					buffs.remove(i);
				}else{
					i++;
				}
				
			}
		}
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
		return curr.getFrameWidth();
	}
	public int getHeight() {
		return curr.getFrameHeight();
	}

	

	

	

	

	

	
}
