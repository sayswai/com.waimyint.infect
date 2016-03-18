package Controllers;
import java.awt.Color;
import java.awt.Font;

import com.jogamp.opengl.util.awt.TextRenderer;


public class TextController {

	public static void glWrite(Color color, int x, int y, String text, Font font) {

		TextRenderer render = new TextRenderer(font);
		
		render.beginRendering(Window.window.getWidth(), Window.window.getHeight());
		render.setColor(color);
		render.draw(text, x, y);
		render.endRendering();
		
	}
}
