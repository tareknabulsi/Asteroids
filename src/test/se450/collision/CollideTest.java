package test.se450.collision;

import static org.junit.Assert.*;

import java.awt.Color;

import org.junit.Test;

import main.se450.collision.Collide;
import main.se450.interfaces.IStrategy;
import main.se450.model.Circle;
import main.se450.strategies.PassThruStrategy;

public class CollideTest {

	@Test
	public void testCollidedWithSameValues() {
		Color color = Color.BLUE;
		IStrategy iStrategy = new PassThruStrategy();
		Circle circle1 = new Circle(0,0,0,0,0,0,0,color,iStrategy);
		Circle circle2 = new Circle(0,0,0,0,0,0,0,color,iStrategy);
		assertTrue(Collide.collided(circle1.getLineCollection(), circle2.getLineCollection()));
	}
	
	@Test
	public void testCollidedWithOneTouchingEdge() {
		Color color = Color.BLUE;
		IStrategy iStrategy = new PassThruStrategy();
		Circle circle1 = new Circle(0,-10,10,10,0,0,0,color,iStrategy);
		Circle circle2 = new Circle(0,-10,10,-30,0,0,0,color,iStrategy);
		assertTrue(Collide.collided(circle1.getLineCollection(), circle2.getLineCollection()));
	}

}
