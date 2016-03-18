package Animation;

import Controllers.TGAController;

public class AnimationDef {

	String name;
	FrameDef[] frames;
	
	public AnimationDef(String name, int numOfFrames, String[] tex)
	{
		frames = new FrameDef[numOfFrames];
		this.name = name;
		
		for(int i = 0; i < tex.length; i++)
		{
			frames[i] = new FrameDef(TGAController.glTexImageTGAFile(tex[i], frames[i].size));
		}
	}
	
	public int getTexWidth(int i)
	{
		return frames[i].size[0];
	}
	
	public int getTexHeight(int i)
	{
		return frames[i].size[1];
	}

	public int getFrameTex(int i) {
		return frames[i].image;
	}
	
	public int[] getSizes(int i)
	{
		return frames[i].size;
	}

	public float getFrameTime(int i) {
		return frames[i].frameTimeSecs;
	}


}
