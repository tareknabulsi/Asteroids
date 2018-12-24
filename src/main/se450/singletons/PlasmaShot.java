package main.se450.singletons;

import java.util.ArrayList;

import main.se450.interfaces.IShape;
import main.se450.model.Circle;

public class PlasmaShot
{
	private static PlasmaShot plasmaShot = null;
	
	private ArrayList<IShape> shots = null;
	
	static
	{
		plasmaShot = new PlasmaShot();
	}
	
    private PlasmaShot()
    {
    	shots = new ArrayList<IShape>();
    }
    
	public final static PlasmaShot getPlasmaShot() 
	{
		return plasmaShot;
	}
	
	public final ArrayList<IShape> getShots()
	{
		return shots;
	}
	
	public void addShot(Circle shot)
	{
		if (shots.size() < 3){
			SoundManager.getSoundManager().fire();
			shots.add(shot);
		}
	}
}