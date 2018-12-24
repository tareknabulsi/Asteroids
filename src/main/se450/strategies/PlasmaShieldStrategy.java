package main.se450.strategies;

import main.se450.interfaces.IStrategy;
import main.se450.model.Shape;
import main.se450.singletons.DisplayManager;
import main.se450.singletons.StarDefender;

/*
 * If shapes from ShapeList Singleton (aka asteroids) collide with shield,
 * they should bounce back as in ReboundStrategy
 */
public class PlasmaShieldStrategy implements IStrategy{
	@Override
	public void execute(Shape shape)
	{
		//Check X's
		float maxX = shape.getMaxX();
		float minX = shape.getMinX();
		
		if (maxX < 0.0f)
		{
			StarDefender.getStarDefender().createPlasmaShield();
		}
		else if (minX > DisplayManager.getDisplayManager().getWidth())
		{
			StarDefender.getStarDefender().createPlasmaShield();
		}
		
		//Check Y's
		float maxY = shape.getMaxY();
		float minY = shape.getMinY();
		
		if (maxY < 0.0f)
		{
			StarDefender.getStarDefender().createPlasmaShield();
		}
		else if (minY > DisplayManager.getDisplayManager().getHeight())
		{
			StarDefender.getStarDefender().createPlasmaShield();
		}
	}
}
