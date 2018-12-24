package main.se450.singletons;

import java.awt.Color;

import main.se450.exceptions.BadStrategyException;
import main.se450.factories.StrategyFactory;
import main.se450.interfaces.IStrategy;
import main.se450.model.Circle;
import main.se450.model.Ship;
import main.se450.strategies.PlasmaShieldStrategy;

/**
 * <div>
 * <img src ="http://media.istockphoto.com/photos/spaceship-entering-a-wormhole-picture-id494188960" alt="" style="width: 40%">
 * </div>
 * 
 * <p>
 * Star Defender... The one and only true ace pilot throughout the entire galaxy sworn to defend all
 * life among the stars from the relentless and merciless asteroids. You play as this widely
 * renowned hero and use your plasma fed technology and top gun flying skills to clean up the
 * seemingly endless debris in space. Such gallantry and dedication have taken the universe to 
 * calling this pilot, "Star Defender," and he is not willing to back down without a fight. If 
 * there is a threat that puts any life within the realm of the stars in jeopardy, you can count
 * on Star Defender being there to defend them. Join him to fight against the ruthless and 
 * ghastly threats that lurk in the deepest and darkest pits of the cosmos to maintain 
 * inter-galactic peace and prosperity. That is, as long as you are brave enough...
 * </p>
 * <p>
 * This class uses the singleton pattern to ensure the single instantiation of the player ship.
 * The class uses a create method that takes data from the configuration file in order to construct
 * the proper ship size. The ship is destroyed when it collides with an asteroid. The method
 * destroyStarDefender() is called in the destroy() method in the Ship class. That method creates
 * four lines that travel at 25 degree angles from the ships position until they reach the edge
 * of the screen. Once those lines are created, the destroyStarDefender() method is called and the
 * ship no longer exists. 
 * </p>
 * <p>
 * This class also uses another method called createPlasmaShield(). This method draws a circle
 * that surrounds the player ship and follows it wherever it moves. The game does not allow the
 * ship to be destroyed as long as this shield is drawn. It only lasts 5 seconds however, and 
 * is removed by the method, depletePlasmaShield().
 * </p>
 * 
 * @author Tarek Nabulsi
 *
 * 
 */
public class StarDefender
{
	private static StarDefender starDefender = null;
	
	Ship   playerShip;
	Circle plasmaShield;
	
	static
	{
		starDefender = new StarDefender();
	}
	
    private StarDefender(){}
    
	public final static StarDefender getStarDefender() 
	{
		if(starDefender == null){
			starDefender = new StarDefender();
		}
		return starDefender;
	}
	
	public Ship getPlayerShip(){
		return playerShip;
	}
	
	public Circle getPlasmaShield(){
		return plasmaShield;
	}
	
	public Ship createStarDefender(){
		IStrategy iStrategy = null;
		try {
			iStrategy = StrategyFactory.makeStrategy(Configurations.getConfigurations().getBorder());
		} catch (BadStrategyException e) {
			System.out.println(e);
		}
		
		float right		    = -20 + Configurations.getConfigurations().getWidth()/2;
		float left		    = right + Configurations.getConfigurations().getShipWidth();
		float top 		    = -80 + Configurations.getConfigurations().getHeight()/2;
		float bottom	    = top + Configurations.getConfigurations().getShipHeight();
		Color color		    = Configurations.getConfigurations().getColor();
		
		playerShip = new Ship(left, top, right, bottom, 0.0f, 0.0f, 0.0f, color, iStrategy);
		return playerShip;
	}
	/**
	 * Creates a shield that draws around the player ship.
	 */
	public void createPlasmaShield(){
		
		IStrategy iStrategy = new PlasmaShieldStrategy();
		
		Color color = Color.CYAN;
		float size 	   = Configurations.getConfigurations().getShotDiameter();
		
		float midX	   = playerShip.getMidpointX1X3();
		float midY	   = playerShip.getMidpointY1Y3();
		
		Circle shield = new Circle(0, 0, 0, 0, 0, 0, 100, color, iStrategy);
		shield.setX1(midX);
		shield.setX2(midX + size);
		shield.setX3(midX + size);
		shield.setX4(midX);
		shield.setY1(midY-40);
		shield.setY2(midY-40);
		shield.setY3(midY + size*20);
		shield.setY4(midY + size*20);
		plasmaShield = shield;
	}
	
	public void depletePlasmaShield(){
		plasmaShield = null;
	}
	
	public void destroyStarDefender(){	
	playerShip = null;
	}
}
