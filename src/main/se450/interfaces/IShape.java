package main.se450.interfaces;

import java.awt.Graphics;

import main.se450.collections.LineCollection;

public interface IShape 
{
	void update();
	
	void draw(Graphics g);
	
	float getMinX();
	
	float getMinY();
	
	float getMaxX();
	
	float getMaxY();
	
	LineCollection getLineCollection();

	void expire();
	
	boolean isExpired();

	String getSize();
	
	void setSize(String string);
	
	void divide();
}
      