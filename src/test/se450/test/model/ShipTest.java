package test.se450.test.model;

import static org.junit.Assert.*;

import java.awt.Color;

import org.junit.Test;

import main.se450.interfaces.IStrategy;
import main.se450.model.Ship;
import main.se450.singletons.ShapeList;
import main.se450.strategies.PassThruStrategy;

public class ShipTest 
{
	@Test
	public void testGetMidpointX1X2WithZero() 
	{
		Color color = Color.BLUE;
		IStrategy iStrategy = new PassThruStrategy();
		Ship ship = new Ship(0,0,0,0,0,0,0,color,iStrategy);
		assertTrue(ship.getMidpointX1X2() == 0);
	}

	@Test
	public void testDestroy() {
		Color color = Color.BLUE;
		IStrategy iStrategy = new PassThruStrategy();
		Ship ship = new Ship(0,0,0,0,0,0,0,color,iStrategy);
		ship.destroy();
		//Four lines created
		assertTrue(ShapeList.getShapeList().getShapes().size() == 4);
	}
}
