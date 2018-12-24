package main.se450.singletons;

import java.util.HashMap;

import main.se450.constants.Constants;
import main.se450.interfaces.ISound;
import main.se450.sound.BigExplosion;
import main.se450.sound.Fire;
import main.se450.sound.ForwardThrust;
import main.se450.sound.MedExplosion;
import main.se450.sound.ReverseThrust;
import main.se450.sound.SmallExplosion;

public class SoundManager
{
	private static SoundManager soundManager = null;
	
	private HashMap<String,ISound> sounds = null;
	
	static
	{
		soundManager = new SoundManager();
	}
	
    private SoundManager()
    {
    	sounds = new HashMap<String,ISound>();
    	
    	sounds.put(Constants.FIRE,                   new Fire());
    	sounds.put(Constants.FORWARD_THRUST_PRESSED, new ForwardThrust());
    	sounds.put(Constants.REVERSE_THRUST_PRESSED, new ReverseThrust());
    	sounds.put("Big Explosion", new BigExplosion());
    	sounds.put("Medium Explosion", new MedExplosion());
    	sounds.put("Small Explosion", new SmallExplosion());
    }
    
	public final static SoundManager getSoundManager() 
	{
		return soundManager;
	}
	
	public void fire()
	{
		sounds.get(Constants.FIRE).play();
	}
	
	public void forwardThrust()
	{
		sounds.get(Constants.FORWARD_THRUST_PRESSED).play();
	}
	
	public void reverseThrust()
	{
		sounds.get(Constants.REVERSE_THRUST_PRESSED).play();
	}
	
	public void bigExplosion()
	{
		sounds.get("Big Explosion").play();
	}
	
	public void medExplosion()
	{
		sounds.get("Medium Explosion").play();
	}
	
	public void smallExplosion()
	{
		sounds.get("Small Explosion").play();
	}
}
      