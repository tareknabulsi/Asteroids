package main.se450.model;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;

import main.se450.collections.LineCollection;
import main.se450.interfaces.IShape;
import main.se450.interfaces.IStrategy;
import main.se450.main.FinalProject;
import main.se450.singletons.ShapeList;
import main.se450.singletons.SoundManager;

public class Circle extends ShapeDroid
{
	private Line2D    line   = new Line2D.Float(0.0f,0.0f,0.0f,0.0f);
	private	Ellipse2D circle = new Ellipse2D.Float(0.0f,0.0f,0.0f,0.0f);
	
	public Circle(float nLeft, float nTop, float nRight, float nBottom, float nX, float nY, float nRotation, Color cColor, IStrategy iStrategy)
	{
		super(nLeft, nTop, nRight, nBottom, nX, nY, nRotation, cColor, iStrategy);
	}
	
	@Override
	public void draw(Graphics graphics) 
	{
		line.setLine(getCenterX(), getCenterY(), getX1(), getY1());
  		circle.setFrame(getMinX(), getMinY(), getWidth(), getHeight());
  		
  		Graphics2D g2d = (Graphics2D)(graphics);
  		
  		g2d.setColor(getColor());
  		g2d.draw(circle);
  		g2d.draw(line);
	}

	@Override
	public void addSides(LineCollection lineCollection)
    {
    	if (lineCollection != null)
    	{
	    	lineCollection.add(new Line(getX1(), getY1(), getX2(), getY2(), getX(), getY(), getRotation(), Color.BLACK, getStrategy()));
	        lineCollection.add(new Line(getX2(), getY2(), getX3(), getY3(), getX(), getY(), getRotation(), Color.BLACK, getStrategy()));
	        lineCollection.add(new Line(getX3(), getY3(), getX4(), getY4(), getX(), getY(), getRotation(), Color.BLACK, getStrategy()));
	        lineCollection.add(new Line(getX4(), getY4(), getX1(), getY1(), getX(), getY(), getRotation(), Color.BLACK, getStrategy()));
    	}
    }
	
	@Override
	public float getMinX() 
	{
		return getCenterX() - getRadius();
	}

	@Override
	public float getMinY() 
	{
		return getCenterY() - getRadius();
	}

	@Override
	public float getMaxX() 
	{
		return getCenterX() + getRadius();
	}

	@Override
	public float getMaxY() 
	{
		return getCenterY() + getRadius();
	}
	
	public float getCenterX()
	{
		return getMidpointX1X3();
	}

	public float getCenterY()
	{
		return getMidpointY1Y3();
	}

	public float getRadius()
	{
		return getWidth() * 0.5f; //getWidth == getHeight for circle
	}
	
	public void divide(){
		
		debris();
		String newSize = null;
		float downSize = 0;
	
		if (getSize().equals("Large")){
			FinalProject.addScore(10);
			SoundManager.getSoundManager().bigExplosion();
			newSize  = "Medium";
			downSize = 75;
		}
		else if (getSize().equals("Medium")){
			FinalProject.addScore(20);
			SoundManager.getSoundManager().medExplosion();
			newSize  = "Small";
			downSize = 40;
		}
		else if (getSize().equals("Small")){
			FinalProject.addScore(20);
			SoundManager.getSoundManager().smallExplosion();
			expire();
			return;
		}
			Shape shape1 = new Circle(0, 0, 0, 0, 0, 0, getRotation() + 1.5f, getColor(), getStrategy());
			shape1.setX1(getMidpointX1X2());
			shape1.setX2(getMidpointX1X2() + downSize);
			shape1.setX3(getMidpointX1X2() + downSize);
			shape1.setX4(getMidpointX1X2());
			shape1.setY1(getMidpointY1Y2());
			shape1.setY2(getMidpointY1Y2());
			shape1.setY3(getMidpointY1Y2() + downSize);
			shape1.setY4(getMidpointY1Y2() + downSize);
			shape1.setX(-getX() + 1.5f);
			shape1.setY(-getY() + 1.5f);
			shape1.setSize(newSize);
			
			Shape shape2 = new Circle(0, 0, 0, 0, 0, 0, getRotation() + 1.5f, getColor(), getStrategy());
			shape2.setX1(getMidpointX1X2());
			shape2.setX2(getMidpointX1X2() + downSize);
			shape2.setX3(getMidpointX1X2() + downSize);
			shape2.setX4(getMidpointX1X2());
			shape2.setY1(getMidpointY1Y2());
			shape2.setY2(getMidpointY1Y2());
			shape2.setY3(getMidpointY1Y2() + downSize);
			shape2.setY4(getMidpointY1Y2() + downSize);
			shape2.setX(-getX() + 1.5f);
			shape2.setY( getY() + 1.5f);
			shape2.setSize(newSize);

			ShapeList.getShapeList().addShapesDebris((IShape)shape1);
			ShapeList.getShapeList().addShapesDebris((IShape)shape2);
			expire();
		}
}
      