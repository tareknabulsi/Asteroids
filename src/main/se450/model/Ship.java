package main.se450.model;

import java.awt.Color;
import java.util.ArrayList;

import main.se450.collections.LineCollection;
import main.se450.interfaces.IObservable;
import main.se450.interfaces.IShape;
import main.se450.interfaces.IStrategy;
import main.se450.singletons.Configurations;
import main.se450.singletons.ShapeList;
import main.se450.singletons.StarDefender;
import main.se450.strategies.EndStrategy;

public class Ship extends ShapeDroid implements IObservable
{ 
	public Ship(float nLeft, float nTop, float nRight, float nBottom, float nX, float nY, float nRotation, Color cColor, IStrategy iStrategy)
	{
		super(nLeft, nTop, nRight, nBottom, nX, nY, nRotation, cColor, iStrategy);
    }

    public void addSides(LineCollection lineCollection)
    {
    	if (lineCollection != null)
    	{
	        lineCollection.add(new Line(getX4(),           getY4(),           getMidpointX1X2(), getMidpointY1Y2(), getX(), getY(), getRotation(), getColor(), getStrategy()));
	        lineCollection.add(new Line(getMidpointX1X2(), getMidpointY1Y2(), getX3(),           getY3(), 			getX(), getY(), getRotation(), getColor(), getStrategy()));
	        lineCollection.add(new Line(getX3(),           getY3(),           getMidpointX1X3(), getMidpointY1Y3(), getX(), getY(), getRotation(), getColor(), getStrategy()));
	        lineCollection.add(new Line(getMidpointX1X3(), getMidpointY1Y3(), getX4(),  	     getY4(),        	getX(), getY(), getRotation(), getColor(), getStrategy()));
    	}
    }
	
	public float getMidpointX1X2()
	{
		return ((getX1() + getX2()) * 0.5f);
	}
	
	public float getMidpointY1Y2()
	{
		return ((getY1() + getY2()) * 0.5f);
	}
	
	public void destroy(){	
		
		Color 	  color		= Configurations.getConfigurations().getColor();
		IStrategy iStrategy = new EndStrategy();
		float midX	   = getMidpointX1X3();
		float midY	   = getMidpointY1Y3();
		
		ArrayList<IShape> shipDebris = new ArrayList<IShape>(4);
		shipDebris.add(new Line(0, 0, 0, 0,  3,-3, 12, color, iStrategy));
		shipDebris.add(new Line(0, 0, 0, 0, -3,-3, 12, color, iStrategy));
		shipDebris.add(new Line(0, 0, 0, 0,  3, 3, 12, color, iStrategy));
		shipDebris.add(new Line(0, 0, 0, 0, -3, 3, 12, color, iStrategy));
		
		for (int i = 0; i < 4; i++){
			((Shape) shipDebris.get(i)).setX1(midX);
			((Shape) shipDebris.get(i)).setX2(midX+15);
			((Shape) shipDebris.get(i)).setX3(midX+15);
			((Shape) shipDebris.get(i)).setX4(midX);
			((Shape) shipDebris.get(i)).setY1(midY);
			((Shape) shipDebris.get(i)).setY2(midY);
			((Shape) shipDebris.get(i)).setY3(midY+15);
			((Shape) shipDebris.get(i)).setY4(midY+15);
		}
	
	ShapeList.getShapeList().addShapes(shipDebris);
	
	StarDefender.getStarDefender().destroyStarDefender();
	}

	@Override
	public void divide() {}
}
    