package main.se450.strategies;

import main.se450.interfaces.IStrategy;
import main.se450.model.Shape;
import main.se450.singletons.DisplayManager;

public class EndStrategy implements IStrategy
{
	public EndStrategy()
	{ 
	}

	@Override
	public void execute(Shape shape)
	{
		//Check X's
		float maxX = shape.getMaxX();
		float minX = shape.getMinX();
		
		if (maxX < 0.0f)
		{
			shape.expire();
		}
		else if (minX > DisplayManager.getDisplayManager().getWidth())
		{
			shape.expire();
		}
		
		//Check Y's
		float maxY = shape.getMaxY();
		float minY = shape.getMinY();
		
		if (maxY < 0.0f)
		{
			shape.expire();
		}
		else if (minY > DisplayManager.getDisplayManager().getHeight())
		{
			shape.expire();
		}
	}
}