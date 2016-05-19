package Player;

public class AI extends Player{

	private Player leader;
	
	public AI(String name, int speed, Player leader)
	{
		super(name, speed, false);
		this.leader = leader;
	}
}
