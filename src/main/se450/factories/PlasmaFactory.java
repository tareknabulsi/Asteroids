package main.se450.factories;

import java.awt.Color;
import main.se450.interfaces.IStrategy;
import main.se450.model.Shot;
import main.se450.singletons.Configurations;
import main.se450.singletons.StarDefender;
import main.se450.strategies.EndStrategy;

public class PlasmaFactory
{
	private static float speed = Configurations.getConfigurations().getShotSpeed();
	private static float size  = Configurations.getConfigurations().getShotDiameter();
	
	private static IStrategy iStrategy = new EndStrategy();
	private static Color color = Color.CYAN;
	
	private PlasmaFactory()
	{
	}
	
	public static Shot makeShot(){
		float midX  = StarDefender.getStarDefender().getPlayerShip().getMidpointX1X2();
		float midY  = StarDefender.getStarDefender().getPlayerShip().getMidpointY1Y2();
		
		Shot shot = new Shot(0, 0, 0, 0, 0, 0, 0, color, iStrategy);
		shot.setX1(midX);
		shot.setX2(midX + size);
		shot.setX3(midX + size);
		shot.setX4(midX);
		shot.setY1(midY);
		shot.setY2(midY);
		shot.setY3(midY + size + size);
		shot.setY4(midY + size + size);
		shot.setShotSpeed(speed);
		return shot;
	}
}	