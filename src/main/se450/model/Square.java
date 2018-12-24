package main.se450.model;

import java.awt.Color;

import main.se450.collections.LineCollection;
import main.se450.interfaces.IShape;
import main.se450.interfaces.IStrategy;
import main.se450.main.FinalProject;
import main.se450.singletons.ShapeList;
import main.se450.singletons.SoundManager;

	/**
	 * <div>
	 * <img src ="http://media.istockphoto.com/photos/blank-box-picture-id518223207" alt="" style="width: 40%">
	 * </div>
	 * <p>
	 * The square class is similar to the other shape classes with the exception of one method -
	 * divide() - which is also used in the circle class too since both types of shapes are used
	 * as asteroids. The divide() method is what allows the shape to split into two smaller halves
	 * once it is collided with either the player ship or a shot. A new attribute "size" has been 
	 * added to the Shape class, which is a string that specifies the exact size of the shape.
	 * There are three kinds of sizes, Large, Medium, and Small. Large shapes move the slowest, and
	 * small shapes move the fastest. The two new shapes move in different directions from each
	 * other and the original parent shape. Once they are added to the ShapeList singleton class,
	 * the parent shape then calls the method expire() to be marked as expired and become deleted.
	 * </p>
	 * <p>
	 * This class also uses the addSides method which adds four new lines to the specified line
	 * collection that gets passed in as a parameter. This is used for the getLineCollection() method
	 * in the parent class that can be used to help determine if a collision has been made with another
	 * shape.
	 * </p>
	 */
public class Square extends ShapeDroid
{
	/**
	 * <p>
	 * The constructor is the same as all other shape classes, and the parameters are passed
	 * to the super constructor.
	 * </p>
	 * 
	 * @author Tarek Nabulsi
	 * 
	 * @param nLeft left point of shape
	 * @param nTop top point of shape
	 * @param nRight right point of shape
	 * @param nBottom bottom point of shape
	 * @param nX horizontal direction of movement
	 * @param nY vertical direction of movement
	 * @param nRotation shape's rotation
	 * @param cColor color specified by int value
	 * @param iStrategy how the shape behaves when hitting border
	 * 
	 */
	public Square(float nLeft, float nTop, float nRight, float nBottom, float nX, float nY, float nRotation, Color cColor, IStrategy iStrategy)
	{
		super(nLeft, nTop, nRight, nBottom, nX, nY, nRotation, cColor, iStrategy);
    }
	/**
	 * @param lineCollection A collection of lines that will take in all sides of this shape.
	 */
    public void addSides(LineCollection lineCollection)
    {
    	if (lineCollection != null)
    	{
	    	lineCollection.add(new Line(getX1(), getY1(), getX2(), getY2(), getX(), getY(), getRotation(), getColor(), getStrategy()));
	        lineCollection.add(new Line(getX2(), getY2(), getX3(), getY3(), getX(), getY(), getRotation(), getColor(), getStrategy()));
	        lineCollection.add(new Line(getX3(), getY3(), getX4(), getY4(), getX(), getY(), getRotation(), getColor(), getStrategy()));
	        lineCollection.add(new Line(getX4(), getY4(), getX1(), getY1(), getX(), getY(), getRotation(), getColor(), getStrategy()));
    	}
    }
    /**
     * Splits shape into two new shapes of smaller size as long as the shape is not small.
     * New shapes get added to ShapeList singleton and this shape expires.
     * 
     */
	@Override
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
			downSize = 45;
		}
		else if (getSize().equals("Small")){
			FinalProject.addScore(50);
			SoundManager.getSoundManager().smallExplosion();
			expire();
			return;
		}
			Shape shape1 = new Square(0, 0, 0, 0, 0, 0, getRotation() + 1.5f, getColor(), getStrategy());
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
			
			Shape shape2 = new Square(0, 0, 0, 0, 0, 0, getRotation() + 1.5f, getColor(), getStrategy());
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
    