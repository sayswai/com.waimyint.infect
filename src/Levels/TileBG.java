package Levels;


import Controllers.*;
import Camera.*;

public class TileBG {

	int tileWidth;
	int tileHeight;
	int maxTilesX;
	int maxTilesY;
	
	Tiles tile;
	
	TileBG(int tileWidth, int tileHeight)
	{
		this.tileHeight = tileHeight;
		this.tileWidth = tileWidth;
		maxTilesX = (Window.window.getWidth() / tileWidth);
		maxTilesY = (Window.window.getHeight() / tileHeight);
		
	}
	
	void initiateTiles()
	{
		//TODO find ways to initiate different maps
	}
	
	void drawLevel()
	{
		for(int y = 0; y < maxTilesY; y++)
		{
			int startingX;
			int loopStart;
				if(Camera.x > 0)
				{
					Camera.updateOffset(tileWidth);
					startingX =  -(Camera.xOffset);
					loopStart = Math.floorDiv(Camera.x - Camera.xOffset, tileWidth);
				}else{
					startingX = 0;
					loopStart = 0;
				}
				
				for(int x = loopStart; (x-loopStart) < maxTilesX; x++)
				{
					TGAController.glDrawSprite(tile.getTile(x, y), startingX, y*40, tileWidth, tileHeight);
					startingX += tileWidth;
				}
			
		}
	}
}
