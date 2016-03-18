package Animation;


public class AnimationData {

	boolean initiated = false;
	AnimationDef def;
	int curFrame;
	float deltaTime;
	
	void update()
	{
		initiated();
			float delta = System.nanoTime() - deltaTime;
			if(delta >= def.getFrameTime(curFrame)){
				if(curFrame == def.frames.length - 1)
				{
					curFrame = 0;
				}else{
					curFrame++;
				}
			}
	}

	public int getFrameWidth() {
		initiated();
		return def.getTexWidth(curFrame);
	}

	public int getFrameHeight() {
		initiated();
		return def.getTexHeight(curFrame);
	}
	
	private void initiated()
	{
		if(!initiated)
		{
			this.curFrame = 0;
			deltaTime = System.nanoTime();
			initiated = true;
		}
	}

	
	
}
