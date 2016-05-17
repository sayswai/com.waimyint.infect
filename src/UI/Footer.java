package UI;


import java.util.LinkedList;

import Controllers.*;

public class Footer {

	
	
	int[] dimension;
	int tex;
	
	public Footer(String src)
	{
		dimension = new int[2];
		tex = TGAController.glTexImageTGAFile(src, dimension);
	}
	
	public void draw()
	{
		TGAController.glDrawSprite(tex, 0, Window.window.getHeight()-dimension[1], dimension[0], dimension[1]);
	}

	
}
