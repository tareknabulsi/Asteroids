package main.se450.model;

import java.awt.Color;

import main.se450.interfaces.IStrategy;
import main.se450.singletons.StarDefender;

public class Shot extends Circle{

	public Shot(float nLeft, float nTop, float nRight, float nBottom, float nX, float nY, float nRotation, Color cColor, IStrategy iStrategy) {
		super(nLeft, nTop, nRight, nBottom, nX, nY, nRotation, cColor, iStrategy);
	}
	
	public void setShotSpeed(float fVal)	
	{
			float xB = StarDefender.getStarDefender().getPlayerShip().getMidpointX1X2();//x tip of ship
			float xA = StarDefender.getStarDefender().getPlayerShip().getMidpointX1X3();//x bottom of ship
			float yB = StarDefender.getStarDefender().getPlayerShip().getMidpointY1Y2();//y tip of ship
			float yA = StarDefender.getStarDefender().getPlayerShip().getMidpointY1Y3();//y bottom of ship
			
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
			setX(x);
			setY(y);
	}
}
