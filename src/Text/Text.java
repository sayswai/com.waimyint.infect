package Text;
import java.awt.Color;
import java.awt.Font;
import Controllers.*;


public class Text {

	private String text;
	private int fontSize;
	private int[] position = new int[2];
	private Color color;
	private Font font;
	private int length;
	
	public Text(String text)
	{
		this.text = text;
		this.fontSize = 22;
		this.color = Color.white;
		this.font = new Font("Verdana", Font.BOLD, fontSize);
		updateLength();
		position[0] = 10; 
		position[1] = Window.window.getHeight()-fontSize-50;
	}
	
	public Text(String text, int[] pos, int fontSize)
	{
		this(text);
		this.fontSize = fontSize;
		position = pos;
	}
	
	public Text(String text, int[] pos, int fontSize, Color color)
	{
		this(text, pos, fontSize);
		this.color = color;
	}
	
	public void centerX()
	{
		position[0] = Window.window.getWidth()/2;
	}
	
	public Font getFont() {
		return font;
	}

	public void setFont(Font font) {
		this.font = font;
	}

	public void setSize(int size)
	{
		this.fontSize = size;
		this.font = new Font("Verdana", Font.BOLD, fontSize);
	}
	
	public int getSize()
	{
		return fontSize;
	}
	
	public void setColor(Color color)
	{
		this.color = color;
	}
	
	public Color getColor()
	{
		return color;
	}
	
	public void setText(String text)
	{
		this.text = text;
		updateLength();
	}
	
	public void addText(String text)
	{
		this.text += text;
		updateLength();
	}
	
	public void setX(int x)
	{
		position[0] = x;
	}
	
	public void setY(int y)
	{
		position[1] = y;
	}
	
	public void setPosition(int[] pos)
	{
		position = pos;
	}
	
	public int getX()
	{
		return position[0];
	}
	
	public int getY()
	{
		return position[1];
	}
	
	public int[] getPosition()
	{
		return position;
	}
	
	public void updateLength()
	{
		this.length = this.text.length();
	}
	
	public void draw()
	{
		TextController.glWrite(color, position[0], position[1], text, font);
	}
	
	
	
}
