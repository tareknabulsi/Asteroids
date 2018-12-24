package main.se450.factories;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import main.se450.singletons.Configurations;
import main.se450.utilities.Extractor;

public class JSONFileConfigurationsFactory
{
	private static int 	   framesPerSecond = 0;
	private static int 	   repeatKeySpeed  = 0;
	private static int 	   width 		   = 0;
	private static int 	   height      	   = 0;
	private static int	   shipWidth 	   = 0;
	private static int	   shipHeight	   = 0;
	private static float   shotSpeed 	   = 0.0f;
	private static float   shotDiameter    = 0.0f;
	private static int 	   shotLifeTime    = 0;
	private static float   forwardThrust   = 0.0f;
	private static float   reverseThrust   = 0.0f;
	private static float   friction 	   = 0.0f;
	private static float   leftRight 	   = 0.0f;
	private static int 	   color 		   = 0;
	private static String  border 		   = null;
	
	private JSONFileConfigurationsFactory()
	{
	}
	
	public static void setConfigurations(String fileName)
	{
        
        
		try
		{ 
	        JSONParser parser = new JSONParser();
	 
	        Object obj = parser.parse(new FileReader(fileName));
	 
	        JSONObject jsonObject = (JSONObject) obj;
	   	 
	        JSONArray jsonArray = (JSONArray) jsonObject.get("game");
	        
	        Iterator<?> jsonIterator = jsonArray.iterator();
	        while (jsonIterator.hasNext())
	        {
	        	JSONObject config = (JSONObject)jsonIterator.next();
    			try
    			{
	        		framesPerSecond = Extractor.extractInteger(config.get("framespersecond"));
	        		repeatKeySpeed  = Extractor.extractInteger(config.get("repeatkeyspeed"));
	        		width           = Extractor.extractInteger(config.get("width"));
	        		height          = Extractor.extractInteger(config.get("height"));
	        		shipWidth	    = Extractor.extractInteger(config.get("shipwidth"));
	        		shipHeight	    = Extractor.extractInteger(config.get("shipheight"));
			        shotSpeed	    = Extractor.extractFloat  (config.get("shotspeed"));
			        shotDiameter	= Extractor.extractFloat  (config.get("shotdiameter"));
			        shotLifeTime	= Extractor.extractInteger(config.get("shotlifetime"));
			        forwardThrust	= Extractor.extractFloat  (config.get("forwardthrust"));
			        reverseThrust	= Extractor.extractFloat  (config.get("reversethrust"));
			        friction		= Extractor.extractFloat  (config.get("friction"));
			        leftRight		= Extractor.extractFloat  (config.get("leftright"));
			        color	     	= Extractor.extractInteger(config.get("color"));
			        border 			= config.get("borders").toString();
				}
				catch (Exception e)
				{
	    			System.out.println(e);
				}
        	}
		}
        catch(FileNotFoundException eFileNotFound)
        {
        }
        catch(IOException eIOException)
        {
        	
        }
        catch(ParseException eParseException)
        {
        }
		Configurations.getConfigurations().setFramesPerSecond(framesPerSecond);
		Configurations.getConfigurations().setRepeatKeySpeed(repeatKeySpeed);
		Configurations.getConfigurations().setWidth(width);
		Configurations.getConfigurations().setHeight(height);
		Configurations.getConfigurations().setShipWidth(shipWidth);
		Configurations.getConfigurations().setShipHeight(shipHeight);
		Configurations.getConfigurations().setShotSpeed(shotSpeed);
		Configurations.getConfigurations().setShotDiameter(shotDiameter);
		Configurations.getConfigurations().setShotLifeTime(shotLifeTime);
		Configurations.getConfigurations().setForwardThrust(forwardThrust);
		Configurations.getConfigurations().setReverseThrust(reverseThrust);
		Configurations.getConfigurations().setFricition(friction);
		Configurations.getConfigurations().setLeftRight(leftRight);
		Configurations.getConfigurations().setColor(color);
		Configurations.getConfigurations().setBorder(border);
	}
}
      