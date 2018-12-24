package main.se450.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JPanel;

import main.se450.collision.Collide;
import main.se450.interfaces.IObservable;
import main.se450.interfaces.IShape;
import main.se450.model.Circle;
import main.se450.model.Ship;
import main.se450.singletons.DisplayManager;
import main.se450.singletons.PlasmaShot;
import main.se450.singletons.ShapeList;
import main.se450.singletons.StarDefender;

/*
 * Name     : 
 * Depaul#  : 
 * Class    : SE 450
 * Project  : Final
 * Due Date : 03/13/2017
 *
 * class ShapeDisplay
 *
 */

public class ShapeDisplay extends JPanel implements IObservable
{
  	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public ShapeDisplay()
	{
	}
	
	@Override
	public void validateTree()
	{
		super.validateTree();

		Dimension dimension = getSize();
		
		DisplayManager.getDisplayManager().setDisplaySize(dimension.width, dimension.height);
	}
	
	public void paint(Graphics graphics) 
  	{
		graphics.setColor(Color.BLACK);
		graphics.fillRect(0, 0, DisplayManager.getDisplayManager().getWidth(), DisplayManager.getDisplayManager().getHeight());
		
		final Ship starDefender = StarDefender.getStarDefender().getPlayerShip();
		if (starDefender != null){
			starDefender.update();
			starDefender.draw(graphics);
		}
		
		final Circle plasmaShield = StarDefender.getStarDefender().getPlasmaShield();
		if (plasmaShield != null){
			plasmaShield.setX(starDefender.getX());
			plasmaShield.setY(starDefender.getY());
			plasmaShield.update();
			plasmaShield.draw(graphics);
		}
		
		final ArrayList<IShape> plasmaShots = PlasmaShot.getPlasmaShot().getShots();
		if (plasmaShots != null)
		{
			Iterator<IShape> iplasmaShots = plasmaShots.iterator();
			while (iplasmaShots.hasNext())
			{
				IShape shot = iplasmaShots.next();
				if (shot != null)
				{
					shot.update();
					shot.draw(graphics);
				}
			}
		} 
	
		final ArrayList<IShape> iShapeList = ShapeList.getShapeList().getShapes();
		if (iShapeList != null)
		{
			Iterator<IShape> iiShapes = iShapeList.iterator();
			while (iiShapes.hasNext())
			{
				IShape iShape = iiShapes.next();
				if (iShape != null)
				{
					iShape.update();
					iShape.draw(graphics);
				}
				if (starDefender != null && Collide.collided(iShape.getLineCollection(), starDefender.getLineCollection())
					&& StarDefender.getStarDefender().getPlasmaShield() == null){
	    			iShape.divide();
	    			starDefender.expire();
	    		}
				for (int i = 0; i < plasmaShots.size() - 1; i++){
		    		if (Collide.collided(iShape.getLineCollection(), plasmaShots.get(i).getLineCollection())){
		    			plasmaShots.get(i).expire();
		    			iShape.divide();
		    		}
		    	}
			}
		}
		
		final ArrayList<IShape> debris = ShapeList.getShapeList().getDebris();
		if (debris != null)
		{
			Iterator<IShape> idebris = debris.iterator();
			while (idebris.hasNext())
			{
				IShape deb = idebris.next();
				if (deb != null)
				{
					deb.update();
					deb.draw(graphics);
				}
			}
		} 
		
		int sizePlasma = plasmaShots.size() - 1;
		for (int i = 0; i < sizePlasma; i++)
		{
			if (plasmaShots.get(i).isExpired())
			{try{
            	plasmaShots.get(i).getLineCollection().clear();
            	} catch (NullPointerException e){}
				plasmaShots.remove(i);
			}
		}
		int sizeShapes = iShapeList.size() - 1;
		for (int i = 0; i < sizeShapes; i++)
		{
			if (iShapeList.get(i).isExpired())
			{try{
            	iShapeList.get(i).getLineCollection().clear();
            	} catch (NullPointerException e){}
				iShapeList.remove(i);
				ShapeList.getShapeList().addDebrisToShapes();
				sizeShapes = iShapeList.size() - 1;
			}
		}
		int sizeDebris = debris.size() - 1;
		for (int i = 0; i < sizeDebris; i++)
		{
			if (debris.get(i).isExpired())
			{try{
            	debris.get(i).getLineCollection().clear();
            	} catch (NullPointerException e){}
				debris.remove(i);
				ShapeList.getShapeList().getDebris().remove(i);
				sizeDebris = debris.size() - 1;
			}
		}
		if (starDefender != null && starDefender.isExpired()){
			starDefender.destroy();
		}
    }

	@Override
	public void update() 
	{
		repaint();
	}
}
      