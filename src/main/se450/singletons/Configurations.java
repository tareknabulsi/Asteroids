package main.se450.singletons;

import java.awt.Color;

//Singleton used to store all of the configurations data
public class Configurations
{
	private static Configurations configurations = null;
	
	private int    framesPerSecond = 0;
	private int    repeatKeySpeed  = 0;
	private int    width 		   = 0;
	private int    height      	   = 0;
	private int	   shipWidth 	   = 0;
	private int	   shipHeight	   = 0;
	private float  shotSpeed 	   = 0.0f;
	private float  shotDiameter    = 0.0f;
	private int    shotLifeTime    = 0;
	private float  forwardThrust   = 0.0f;
	private float  reverseThrust   = 0.0f;
	private float  friction 	   = 0.0f;
	private float  leftRight 	   = 0.0f;
	private int    color 		   = 0;
	private String border 		   = null;
	
	static
	{
		configurations = new Configurations();
	}
	
    private Configurations(){}
    
	public final static Configurations getConfigurations() 
	{
		if(configurations == null){
			configurations = new Configurations();
		}
		return configurations;
	}

	
	
	
	public int getFramesPerSecond() {
		return framesPerSecond;
	}

	public void setFramesPerSecond(int nFramesPerSecond) {
		framesPerSecond = nFramesPerSecond;
	}

	public int getRepeatKeySpeed() {
		return repeatKeySpeed;
	}

	public void setRepeatKeySpeed(int nRepeatKeySpeed) {
		repeatKeySpeed = nRepeatKeySpeed;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int nWidth) {
		width = nWidth;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int nHeight) {
		height = nHeight;
	}

	public int getShipWidth() {
		return shipWidth;
	}

	public void setShipWidth(int nShipWidth) {
		shipWidth = nShipWidth;
	}

	public int getShipHeight() {
		return shipHeight;
	}

	public void setShipHeight(int nShipHeight) {
		shipHeight = nShipHeight;
	}

	public float getShotSpeed() {
		return shotSpeed;
	}

	public void setShotSpeed(float nShotSpeed) {
		shotSpeed = nShotSpeed;
	}

	public float getShotDiameter() {
		return shotDiameter;
	}

	public void setShotDiameter(float nShotDiameter) {
		shotDiameter = nShotDiameter;
	}

	public int getShotLifeTime() {
		return shotLifeTime;
	}

	public void setShotLifeTime(int nShotLifeTime) {
		shotLifeTime = nShotLifeTime;
	}

	public float getForwardThrust() {
		return forwardThrust;
	}

	public void setForwardThrust(float nForwardThrust) {
		forwardThrust = nForwardThrust;
	}

	public float getReverseThrust() {
		return reverseThrust;
	}

	public void setReverseThrust(float nReverseThrust) {
		reverseThrust = nReverseThrust;
	}

	public float getFriction() {
		return friction;
	}

	public void setFricition(float nFriction) {
		friction = nFriction;
	}

	public float getLeftRight() {
		return leftRight;
	}

	public void setLeftRight(float nLeftRight) {
		leftRight = nLeftRight;
	}

	public Color getColor() {
		return new Color(color);
	}

	public void setColor(int nColor) {
		color = nColor;
	}

	public String getBorder() {
		return border;
	}

	public void setBorder(String nBorder) {
		border = nBorder;
	}
}
      