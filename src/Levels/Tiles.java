package Levels;

import java.awt.Shape;

public class Tiles {
	
	int width;
	int height;
	
	int maxX;
	int maxY;
	int[] tiles;
	int[] physics;//can't go through == 1; can go through == 0
	int totalTiles;
	
	Shape shape;
	
	Tiles(int maxX, int maxY, int width, int height)
	{
		this.width = width;
		this.height = height;
		this.maxX = maxX;
		this.maxY = maxY;
		tiles = new int[maxY * width + maxX];
		this.totalTiles = tiles.length;
		physics = new int[totalTiles];
	}
	
	int getTile(int x, int y)
	{
		return tiles[y*width + x];
	}
	
	void setTile(int x, int y, int tex)
	{
		tiles[y*width + x] = tex;
	}
	
	void setPhysics(int x, int y, int value)
	{
		physics[y*width + x] = value;
	}

}
