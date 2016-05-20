package Levels;




public class Level {

	private TileBG[] levels;
	private int currentLevel;
	
	public Level(int numberOfLevels)
	{
		currentLevel = 0;
		initiateLevels(numberOfLevels);
	}

	private void initiateLevels(int numberOfLevels) {
		levels = new TileBG[numberOfLevels];
		for(int i = 0; i< numberOfLevels; i++)
		{
			levels[i] = new TileBG(40, 40);
		}
	}
	/*
	public void initializeCeiling()
	{
		int maxX = levels[0].getMaxTilesX();
		for(int i = 0; i<levels.length; i++)
		{
			int[][] top = new int[2][maxX];
			for(int j = 0; )
		}
	}*/
	public void draw()
	{
		levels[currentLevel].drawLevel();
	}

	public TileBG getLevel(int i)
	{
		return levels[i];
	}
	
	public TileBG getCurrentLevel()
	{
		return levels[currentLevel];
	}

	public int getMaxTilesY() {
		return levels[currentLevel].getMaxTilesY();
	}
	
	public int getMaxTilesX(){
		return levels[currentLevel].getMaxTilesX();
	}
	public int getTileWidth()
	{
		return levels[currentLevel].getTileWidth();
	}

	public int getTileHeight() {
		return levels[currentLevel].getTileHeight();
	}
	
	
	
	
	
	
}
