package custom;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.FocusManager;
import javax.swing.JTextField;

public class MyTextField extends JTextField{
	
	private String placeHolder = "";
	
	public MyTextField(String placeHolder) 
	{
		this.placeHolder = placeHolder;
	}
	
	@Override
	protected void paintComponent(Graphics g) {
	    super.paintComponent(g);
	
	    if(getText().isEmpty()){ //&& ! (FocusManager.getCurrentKeyboardFocusManager().getFocusOwner() == this)
	        Graphics2D g2 = (Graphics2D)g.create();
	        //g2.setBackground(Color.LIGHT_GRAY);
	        g2.setColor(Color.GRAY);
	        g2.setFont(new Font("Century Gothic", Font.ITALIC, 14));
	        g2.drawString(placeHolder, 6, 20); //figure out x, y from font's FontMetrics and size of component.
	        g2.dispose();
	    }
	  }

}
