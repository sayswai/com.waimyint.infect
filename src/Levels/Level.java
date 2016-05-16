package Levels;

public class Level {

	TileBG[] maps;
	int currentLevel;
	
	public Level(int numberOfLevels)
	{
		currentLevel = 0;
		initiateLevels(numberOfLevels);
	}
	
	void initiateLevels(int numberOfLevels)
	{
		maps = new TileBG[numberOfLevels];
		for(int i = 0; i < numberOfLevels; i++)
		{
			maps[i] = new TileBG(40,40);//TODO go over where to change tile height& width
		}
	}
	
	
	void draw()
	{
		maps[currentLevel].drawLevel();
	}
	
}
