package main.se450.model;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import main.se450.interfaces.IShape;
import main.se450.interfaces.IStrategy;
import main.se450.singletons.ShapeList;
import main.se450.strategies.DebrisStrategy;

public abstract class Shape implements IShape
{
	private float     x1        = 0.0f;
	private float     y1        = 0.0f;
	private float     x2        = 0.0f;
	private float     y2        = 0.0f;
	private float     x3        = 0.0f;
	private float     y3        = 0.0f;
	private float     x4        = 0.0f;
	private float     y4        = 0.0f;
	private float     x         = 0.0f;
	private float     y         = 0.0f;
	private float     rotation  = 0.0f;
	private Color     color     = null;
	private IStrategy iStrategy = null;
	private boolean   alive  	= true;
	private String    size;
	
	//Read only pattern
	public Shape(float nLeft, float nTop, float nRight, float nBottom, float nX, float nY, float nRotation, Color cColor, final IStrategy iiStrategy)
	{
		x1        = nLeft;
		y1        = nTop;
		x2        = nRight;
		y2        = nTop;
		x3        = nRight;
		y3        = nBottom;
		x4        = nLeft;
		y4        = nBottom;
		x         = nX;
		y         = nY;
		rotation  = nRotation;
		color     = cColor;
		iStrategy = iiStrategy;
	}
	
	public float getX1()
	{
		return x1;
	}
	
	public float getY1()
	{
		return y1;
	}
	
	public float getX2()
	{
		return x2;
	}
	
	public float getY2()
	{
		return y2;
	}
	
	public float getX3()
	{
		return x3;
	}
	
	public float getY3()
	{
		return y3;
	}
	
	public float getX4()
	{
		return x4;
	}
	
	public float getY4()
	{
		return y4;
	}
	
	public float getX()
	{
		return x;
	}
	
	public float getY()
	{
		return y;
	}
	
	public float getRotation()
	{
		return rotation;
	}
	
	public Color getColor()
	{
		return color;
	}
	
	public final IStrategy getStrategy()
	{
		return iStrategy;
	}
	
	public float getWidth()
	{
		return (float) (Math.sqrt(Math.pow(x3 - x1, 2.0) + Math.pow(y3 - y1, 2.0)));
	}
	
	public float getHeight()
	{
		return (float) (Math.sqrt(Math.pow(x4 - x2, 2.0) + Math.pow(y4 - y2, 2.0)));
	}
	
	public void setX1(float nX1)
	{
		x1  = nX1;
	}
	
	public void setY1(float nY1)
	{
		y1  = nY1;
	}
	
	public void setX2(float nX2)
	{
		x2 = nX2;
	}
	
	public void setY2(float nY2)
	{
		y2 = nY2;
	}
	
	public void setX3(float nX3)
	{
		x3 = nX3;
	}
	
	public void setY3(float nY3)
	{
		y3 = nY3;
	}
	
	public void setX4(float nX4)
	{
		x4 = nX4;
	}
	
	public void setY4(float nY4)
	{
		y4 = nY4;
	}
	
	public void setX(float nX)
	{
		x = nX;
	}
	
	public void setY(float nY)
	{
		y = nY;
	}
	
	public void setRotation(float nRotation)
	{
		rotation = nRotation;
	}
	
	public void setColor(Color cColor)
	{
		color = cColor;
	}
	
	public void setStrategy(final IStrategy iiStrategy)
	{
		iStrategy = iiStrategy;
	}

    @Override
    public void update()
    {
    	transform();
    	
    	iStrategy.execute(this);
    }
    
	@Override
	public abstract void draw(Graphics g);
	
	@Override
	abstract public float getMinX();

	@Override
	abstract public float getMinY();
	
	@Override
	abstract public float getMaxX();
	
	@Override
	abstract public float getMaxY();
	
	public void translateX(float nX)
	{
		x1 += nX;
		x2 += nX;
		x3 += nX;
		x4 += nX;
	}
	
	public void translateY(float nY)
	{
		y1 += nY;
		y2 += nY;
		y3 += nY;
		y4 += nY;		
	}
	
	public void translateXY(float nX, float nY)
	{
		translateX(nX);
		translateY(nY);
	}

	private void transform() 
	{		
		//translate first
		translateXY(x,y);
		
		float midX = getMidpointX1X3();
		float midY = getMidpointY1Y3();
		
		translateXY(-midX,-midY);
		
		// then rotate
		float radians = (float) Math.toRadians(rotation);
		float sinR    = (float) Math.sin(radians);
		float cosR    = (float) Math.cos(radians);
		
		float xPrime1 = (float) ((x1 * cosR) - (y1 * sinR));
		float yPrime1 = (float) ((y1 * cosR) + (x1 * sinR));
		
		float xPrime2 = (float) ((x2 * cosR) - (y2 * sinR));
		float yPrime2 = (float) ((y2 * cosR) + (x2 * sinR));
		
		float xPrime3 = (float) ((x3 * cosR) - (y3 * sinR));
		float yPrime3 = (float) ((y3 * cosR) + (x3 * sinR));
		
		float xPrime4 = (float) ((x4 * cosR) - (y4 * sinR));
		float yPrime4 = (float) ((y4 * cosR) + (x4 * sinR));
		
		x1 = midX + xPrime1;
		x2 = midX + xPrime2;
		x3 = midX + xPrime3;
		x4 = midX + xPrime4;
		y1 = midY + yPrime1;
		y2 = midY + yPrime2;
		y3 = midY + yPrime3;
		y4 = midY + yPrime4;
	}	

	public float getMidpointX1X3()
	{
		return ((x1 + x3) * 0.5f);
	}
	
	public float getMidpointY1Y3()
	{
		return ((y1 + y3) * 0.5f);
	}
	
	public float getMidpointX1X2()
	{
		return ((x1 + x2) * 0.5f);
	}
	
	public float getMidpointY1Y2()
	{
		return ((y1 + y2) * 0.5f);
	}
	
	//use below method for shot/velocity computation
	////computes new X&Y velocities for shape
	////return T - you can what you want to return if anything
	
	///input - fVal -  could be forwardthrust, reversethrust or shotspeed
	
	//example: pass shotspeed            - updates x & y 'shot' velocity with new values
	//       : pass forward thrust value - updates x & y 'ship' velocity with new values
	public void fooVelocity(float fVal)	
	{
			float xB = getMidpointX1X2();//x tip of ship
			float xA = getMidpointX1X3();//x bottom of ship
			float yB = getMidpointY1Y2();//y tip of ship
			float yA = getMidpointY1Y3();//y bottom of ship
			
			float xDiff = xB - xA;
			float yDiff = yB - yA;
			
			float fX = 0.0f;
			float fY = 0.0f;
			if (xDiff == 0.0f)
			{
				fY += fVal * Math.signum(yDiff);
			}
			else if (yDiff == 0.0f)
			{
				fX += fVal * Math.signum(xDiff);
			}
			else
			{
				float ffDiff = Math.abs(xDiff) + Math.abs(yDiff);
				fX += fVal * Math.sin(Math.toRadians(90 * xDiff / ffDiff));
				fY += fVal * Math.sin(Math.toRadians(90 * yDiff / ffDiff));
			}
			
			float x = getX() + fX;
			float y = getY() + fY;
			//use fX & fY to update your existing X&Y velocity 
			//Gives ship max speed of at most 7.0
			if (Math.abs(x) <= 7.0f && Math.abs(y) <= 7.0f){
				setX(x);
				setY(y);
			}
	}
	
	public void applyFriction(float fVal){
		float x = getX();
		float y = getY();
		
		if (Math.abs(x) < fVal && Math.abs(y) < fVal){
			setY(0.0f);
			setX(0.0f);
		} else {
			if (x > fVal )
				setX(x - fVal);
			else if (x < -fVal)
				setX(x + fVal);
			
			if (y > fVal)
				setY(y - fVal);
			else if (y < -fVal)
				setY(y + fVal);	
		}
	}
	public void debris(){

		Color 	  color		= getColor();
		IStrategy iStrategy = new DebrisStrategy();
		float midX	   = getMidpointX1X3();
		float midY	   = getMidpointY1Y3();
		
		ArrayList<IShape> shapeDebris = new ArrayList<IShape>(3);
		shapeDebris.add(new Line(0, 0, 0, 0,  1,-1, 12, color, iStrategy));
		shapeDebris.add(new Line(0, 0, 0, 0, -1,-1, 12, color, iStrategy));
		shapeDebris.add(new Line(0, 0, 0, 0,  0, 1, 12, color, iStrategy));
		
		for (int i = 0; i < 3; i++){
			((Shape) shapeDebris.get(i)).setX1(midX);
			((Shape) shapeDebris.get(i)).setX2(midX+5);
			((Shape) shapeDebris.get(i)).setX3(midX+5);
			((Shape) shapeDebris.get(i)).setX4(midX);
			((Shape) shapeDebris.get(i)).setY1(midY);
			((Shape) shapeDebris.get(i)).setY2(midY);
			((Shape) shapeDebris.get(i)).setY3(midY+5);
			((Shape) shapeDebris.get(i)).setY4(midY+5);
			ShapeList.getShapeList().addDebris(shapeDebris.get(i));
		}
	}
	
	public String getSize(){
		return size;
	}
	
	public void setSize(String size){
		this.size = size;
	}
	
	public void expire(){
		alive = false;
	}
	public boolean isExpired(){
		return !alive;
	}
	
	@Override
	public abstract void divide();
}

      