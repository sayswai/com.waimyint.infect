package Player;

public class AI extends Sprite{

	private Sprite leader;
	
	public AI(String name, int speed, Sprite leader)
	{
		super(name, speed, false);
		this.leader = leader;
	}
}
