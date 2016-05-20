package Levels;
import Controllers.*;
import Camera.*;




public class TileBG {

	private int tileWidth;
	private int tileHeight;
	private int maxTilesX;
	private int maxTilesY;
	
	private int[] tileSize = new int[2];
	
	private Tiles tile;
	
	public TileBG(int tileWidth, int tileHeight)
	{
		this.tileHeight = tileHeight;
		this.tileWidth = tileWidth;
		maxTilesX = (Window.window.getWidth() / tileWidth) * 2; // the max amount of tiles in the map horizontally
		maxTilesY = (Window.window.getHeight() / tileHeight); // the max amount of tiles in the map vertically
		initiateTiles();
	}
	
	private void initiateTiles() {
		tile = new Tiles(maxTilesX, maxTilesY, tileWidth, tileHeight); 
		loadBGColor();
		//loadClouds();
		//loadCeiling();
		//loadFloor();
		//loadBricks();
	}

	private void loadClouds() {
		int cloudp1 = TGAController.glTexImageTGAFile("cloudv1p1.tga", tileSize);
		int cloudp2 = TGAController.glTexImageTGAFile("cloudv1p2.tga", tileSize);
		for(int i = 0; i < maxTilesX; i++)
		{
			tile.setTile(i, 2, cloudp1);
			tile.setTile(i+1, 2, cloudp2);
			
			i = i+4;
		}
		for(int i = 4; i < maxTilesX; i+=5)
		{
			tile.setTile(i, 6, cloudp1);
			tile.setTile(i+1, 6, cloudp2);
		}
		cloudp1 = TGAController.glTexImageTGAFile("cloudv2p1.tga", tileSize);
		cloudp2 = TGAController.glTexImageTGAFile("cloudv2p2.tga", tileSize);
		for(int i = 1; i < maxTilesX; i++)
		{
			tile.setTile(i, 4, cloudp1);
			tile.setTile(i+1, 4, cloudp2);
			i = i + 6;
		}
	}

	private void loadBGColor() {
		int bgColorTex = TGAController.glTexImageTGAFile("brick.tga", tileSize);
		for(int i = 0; i < maxTilesY; i++)
		{
			for(int j = 0; j < maxTilesX; j++)
			{
				tile.setTile(j, i, bgColorTex);
				tile.setPhysics(j, i, 0);
			}
		}
	}

	private void loadCeiling() {
		
	}

	private void loadFloor() {
		int grass = TGAController.glTexImageTGAFile("grass.tga", tileSize);
		
		for(int i = (maxTilesY - 4); i < maxTilesY; i++)
		{
			for(int x = 0; x < maxTilesX; x++)
			{
				tile.setTile(x, i, grass);
			}
		}
		
	}
	
	private void loadBricks(){
		int bricks = TGAController.glTexImageTGAFile("brick.tga", tileSize);
		
		for(int i = (maxTilesY - 4); i < maxTilesY-2; i++)
		{
			tile.setTile(6, i, bricks);
			tile.setPhysics(6, i, 1);
		}
		for(int i = 3; i < 6; i++)
		{
			tile.setTile(i, maxTilesY-3, bricks);
			tile.setPhysics(i, maxTilesY-3, 1);
		}
		
		
		
		for(int i = 15; i < 19; i++)
		{
			tile.setTile(i, maxTilesY-3, bricks);
			tile.setPhysics(i, maxTilesY-3, 1);
		}
		
		for(int i = maxTilesY-3; i < maxTilesY; i++)
		{
			tile.setTile(18, i, bricks);
			tile.setPhysics(18, i, 1);
		}
		
		for(int i = (maxTilesY - 4); i < maxTilesY - 2; i++)
		{
			tile.setTile(21, i, bricks);
			tile.setPhysics(21, i, 1);
		}
		
		for(int i = (maxTilesY-2); i < maxTilesY; i++)
		{
			tile.setTile(24, i, bricks);
			tile.setPhysics(24, i, 1);
		}
		
		for(int i = 27; i < maxTilesX; i++)
		{
			tile.setTile(i, maxTilesY-3, bricks);
			tile.setPhysics(i, maxTilesY-3, 1);
		}
	}
	
	/*
	public void drawLevel()
	{
		for(int y = 0; y < maxTilesY; y++)
		{
			for(int x = Camera.minbgX; x < Camera.maxbgX; x++)
			
			{
				TGAController.glDrawSprite(Window.gl, tile.getTile(x, y), ( x - (Camera.minbgX))*40, y*40, tileSize[0], tileSize[1]);
				//TGAController.glDrawSprite(Window.gl, tile.getTile(x, y), (x-31)*40, y*40, tileSize[0], tileSize[1]);
			}
		}
	}
	*/
	
	public void drawLevel()
	{
		for(int y = 0; y < maxTilesY; y++)
		{
			int startingXpos;
			int loopStart;
				if(Camera.x > 0){
					Camera.updateOffset(tileWidth);
					startingXpos = -(Camera.xOffset);
					loopStart = Math.floorDiv(Camera.x - Camera.xOffset, tileWidth);
				}else{
					startingXpos = 0;
					loopStart = 0;
				}
				
				for(int x = loopStart; (x-loopStart) < maxTilesX; x++)
				{
					TGAController.glDrawSprite(tile.getTile(x, y), startingXpos, y*40, tileSize[0], tileSize[1]);
					startingXpos += tileWidth;
				}
			
		}
	}
		

	

	public int getMaxTilesX() {
		return maxTilesX;
	}

	public void setMaxTilesX(int maxTilesX) {
		this.maxTilesX = maxTilesX;
	}

	public int getMaxTilesY() {
		return maxTilesY;
	}

	public void setMaxTilesY(int maxTilesY) {
		this.maxTilesY = maxTilesY;
	}
	
	public int retrieveTilePos(int x, int y)
	{
		return tile.getTile(x, y);
	}
	
	public int getTilePhysics(int x, int y)
	{
			return tile.getPhysics(x, y);
	}
	public Tiles getTile()
	{
		return tile;
	}
	
	public int totalTiles()
	{
		return tile.totalTiles();
	}

	public int getTileWidth() {
		return tileWidth;
	}

	public void setTileWidth(int tileWidth) {
		this.tileWidth = tileWidth;
	}

	public int getTileHeight() {
		return tileHeight;
	}

	public void setTileHeight(int tileHeight) {
		this.tileHeight = tileHeight;
	}
	



	
}
