package main.se450.keyboard;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.KeyStroke;

import main.se450.constants.Constants;
import main.se450.factories.NamedThreadFactory;
import main.se450.factories.PlasmaFactory;
import main.se450.gui.ShapeDisplay;
import main.se450.main.FinalProject;
import main.se450.model.Ship;
import main.se450.observable.Motion;
import main.se450.singletons.Configurations;
import main.se450.singletons.PlasmaShot;
import main.se450.singletons.SoundManager;
import main.se450.singletons.StarDefender;

/*
 * Name     : 
 * Depaul#  : 
 * Class    : SE 450
 * Project  : Final
 * Due Date : xx/xx/2017
 *
 * class Keyboard
 *
 */

public class Keyboard implements Runnable
{
	private static Keyboard keyboard = new Keyboard();
    
	private static final int START_GAME_KEY     = KeyEvent.VK_1;
    private static final int QUIT_KEY           = KeyEvent.VK_Q;
    private static final int FIRE_KEY           = KeyEvent.VK_SPACE;
    private static final int SHIELD_KEY         = KeyEvent.VK_V;
    private static final int HYPERSPACE_KEY     = KeyEvent.VK_B;
    private static final int LEFT_KEY           = KeyEvent.VK_LEFT;
    private static final int RIGHT_KEY          = KeyEvent.VK_RIGHT;
    private static final int FORWARD_THRUST_KEY = KeyEvent.VK_UP;
    private static final int REVERSE_THRUST_KEY = KeyEvent.VK_DOWN;
    private static final int TOGGLE_KEY         = KeyEvent.VK_T;
    private static final int STOP_KEY           = KeyEvent.VK_S;
    
    private Keystate keyState = new Keystate();
    
    Ship starDefender = StarDefender.getStarDefender().getPlayerShip();
    Configurations configurations = Configurations.getConfigurations();
    Random rand = new Random();
    
	private ScheduledThreadPoolExecutor scheduler = null;
	
	private int keyStates = Keymask.NO_KEY;
	
	private Keyboard()
	{
		startRepeat();
	}

	@Override
	public void finalize()
	{
		stopRepeat();
	}
	
	public static void bindGameKeys(final ShapeDisplay shapeDisplay)
	{
		if (keyboard != null)
		{
			keyboard.bindInputMap (shapeDisplay);
			keyboard.bindActionMap(shapeDisplay);
		}
	}
	
	public static void unbindGameKeys(final ShapeDisplay shapeDisplay)
	{
		if (keyboard != null)
		{
			keyboard.unbindInputMap (shapeDisplay);
			keyboard.unbindActionMap(shapeDisplay);
		}
	}
	
	private void bindInputMap(final ShapeDisplay shapeDisplay)
	{
		unbindInputMap(shapeDisplay);
		
		if (shapeDisplay != null)
		{
			ArrayList<InputMap> inputMaps = new ArrayList<InputMap>();
			
			inputMaps.add(shapeDisplay.getInputMap(ShapeDisplay.WHEN_FOCUSED));
			inputMaps.add(shapeDisplay.getInputMap(ShapeDisplay.WHEN_IN_FOCUSED_WINDOW));
			inputMaps.add(shapeDisplay.getInputMap(ShapeDisplay.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT));

			Iterator<InputMap> iiInputMap = inputMaps.iterator();
			while (iiInputMap.hasNext())
			{
				InputMap inputMap = iiInputMap.next();
			    if (inputMap != null)
			    {
			    	inputMap.clear();
			    	
			    	//key down only events / ie non-repeat
				    inputMap.put(KeyStroke.getKeyStroke(START_GAME_KEY,     0), Constants.START_GAME);
				    inputMap.put(KeyStroke.getKeyStroke(QUIT_KEY,           0), Constants.QUIT      );
				    inputMap.put(KeyStroke.getKeyStroke(SHIELD_KEY,         0), Constants.SHIELD    );
				    inputMap.put(KeyStroke.getKeyStroke(HYPERSPACE_KEY,     0), Constants.HYPERSPACE);
				    inputMap.put(KeyStroke.getKeyStroke(TOGGLE_KEY,         0), Constants.TOGGLE    );
				    inputMap.put(KeyStroke.getKeyStroke(STOP_KEY,           0), Constants.STOP      );
				    inputMap.put(KeyStroke.getKeyStroke(FIRE_KEY,           0), Constants.FIRE      );
				    
				    //key down events
				    inputMap.put(KeyStroke.getKeyStroke(LEFT_KEY,           0), Constants.LEFT_PRESSED          );
				    inputMap.put(KeyStroke.getKeyStroke(RIGHT_KEY,          0), Constants.RIGHT_PRESSED         );
				    inputMap.put(KeyStroke.getKeyStroke(FORWARD_THRUST_KEY, 0), Constants.FORWARD_THRUST_PRESSED);
				    inputMap.put(KeyStroke.getKeyStroke(REVERSE_THRUST_KEY, 0), Constants.REVERSE_THRUST_PRESSED);
				    
				    //key up events
				    inputMap.put(KeyStroke.getKeyStroke(LEFT_KEY,           0, true), Constants.LEFT_RELEASED          );
				    inputMap.put(KeyStroke.getKeyStroke(RIGHT_KEY,          0, true), Constants.RIGHT_RELEASED         );
				    inputMap.put(KeyStroke.getKeyStroke(FORWARD_THRUST_KEY, 0, true), Constants.FORWARD_THRUST_RELEASED);
				    inputMap.put(KeyStroke.getKeyStroke(REVERSE_THRUST_KEY, 0, true), Constants.REVERSE_THRUST_RELEASED);
			    }
			}
		}
	}
	
	@SuppressWarnings("serial")
	private void bindActionMap(final ShapeDisplay shapeDisplay)
	{
		unbindActionMap(shapeDisplay);
		
		if (shapeDisplay != null)
		{	   
			ActionMap actionMap = shapeDisplay.getActionMap();
		    if (actionMap != null)
		    {
			    actionMap.put(Constants.START_GAME, new AbstractAction(Constants.START_GAME)
				{
			        public void actionPerformed(ActionEvent arg0) 
			        {
			        	clearMultiKeyEvent();

			        	System.out.println("Start");
			        	Motion.startObserving(FinalProject.getShapeOutput());
			        }
			    });		    	
			    
			    actionMap.put(Constants.QUIT, new AbstractAction(Constants.QUIT)
				{
			        public void actionPerformed(ActionEvent arg0) 
			        {
			        	clearMultiKeyEvent();

			        	System.out.println("Quitting, but my baby did not crash!");
			        	
			        	System.exit(0);
			        }
			    });
			    		    	
			    actionMap.put(Constants.FIRE, new AbstractAction(Constants.FIRE)
				{
			        public void actionPerformed(ActionEvent arg0) 
			        {
			        	System.out.println("Fire");
			        	PlasmaShot.getPlasmaShot().addShot(PlasmaFactory.makeShot());
			        }
			    });
			    
			    actionMap.put(Constants.SHIELD, new AbstractAction(Constants.SHIELD)
				{
			        public void actionPerformed(ActionEvent arg0) 
			        {
			        	clearMultiKeyEvent();
			        	
			        	if (StarDefender.getStarDefender().getPlasmaShield() == null){
			        		System.out.println("Shield");
			        		StarDefender.getStarDefender().createPlasmaShield();
			        		new java.util.Timer().schedule( 
			        		        new java.util.TimerTask() {
			        		            @Override
			        		            public void run() {
			        		            	System.out.println("Shield depleted");
			        		            	StarDefender.getStarDefender().depletePlasmaShield();
			        		            }
			        		        }, 
			        		        5000 
			        		);
			        	}
			        }
			    });
			    
			    actionMap.put(Constants.HYPERSPACE, new AbstractAction(Constants.HYPERSPACE)
				{
			        public void actionPerformed(ActionEvent arg0) 
			        {
			        	clearMultiKeyEvent();
			        	
			        	System.out.println("Hyperspace");
			        	int maxHeight = 1130;
			        	int minHeight = 160;
			        	int maxWidth = 2300;
			        	int minWidth = 40;
			        	int  x = rand.nextInt(maxWidth - minWidth) + minWidth;
			        	int  y = rand.nextInt(maxHeight - minHeight) + minHeight;
			        	configurations.setWidth(x);
			        	configurations.setHeight(y);
			        	starDefender = StarDefender.getStarDefender().createStarDefender();
			        	if (StarDefender.getStarDefender().getPlasmaShield() != null)
			        		StarDefender.getStarDefender().createPlasmaShield();
			        }
			    });
			    
			    actionMap.put(Constants.LEFT_PRESSED, new AbstractAction(Constants.LEFT_PRESSED)
				{
			        public void actionPerformed(ActionEvent arg0) 
			        {
			        	keyState.setKeyDown(Keymask.LEFT_KEY);
			        	starDefender.setRotation(-(configurations.getLeftRight()));
			        }
			    });
			    
			    actionMap.put(Constants.LEFT_RELEASED, new AbstractAction(Constants.LEFT_RELEASED)
				{
			        public void actionPerformed(ActionEvent arg0) 
			        {
			        	keyState.setKeyUp(Keymask.LEFT_KEY);
			        	starDefender.setRotation(0);
			        }
			    });
			    
			    actionMap.put(Constants.RIGHT_PRESSED, new AbstractAction(Constants.RIGHT_PRESSED)
				{
			        public void actionPerformed(ActionEvent arg0) 
			        {
			        	keyState.setKeyDown(Keymask.RIGHT_KEY);
						starDefender.setRotation(configurations.getLeftRight());
			        }
			    });
			    
			    actionMap.put(Constants.RIGHT_RELEASED, new AbstractAction(Constants.RIGHT_RELEASED)
				{
			        public void actionPerformed(ActionEvent arg0) 
			        {
			        	keyState.setKeyUp(Keymask.RIGHT_KEY);
			        	starDefender.setRotation(0);
			        }
			    });
			    
			    actionMap.put(Constants.FORWARD_THRUST_PRESSED, new AbstractAction(Constants.FORWARD_THRUST_PRESSED)
				{
			        public void actionPerformed(ActionEvent arg0) 
			        {
			        	keyState.setKeyDown(Keymask.FORWARD_THRUST_KEY);
			        }
				});
			    
			    actionMap.put(Constants.FORWARD_THRUST_RELEASED, new AbstractAction(Constants.FORWARD_THRUST_RELEASED)
				{
			        public void actionPerformed(ActionEvent arg0) 
			        {
			        	keyState.setKeyUp(Keymask.FORWARD_THRUST_KEY);
			        }
			    });
			    
			    actionMap.put(Constants.REVERSE_THRUST_PRESSED, new AbstractAction(Constants.REVERSE_THRUST_PRESSED)
				{
			        public void actionPerformed(ActionEvent arg0) 
			        {
			        	keyState.setKeyDown(Keymask.REVERSE_THRUST_KEY);
			        }
			    });
			    
			    actionMap.put(Constants.REVERSE_THRUST_RELEASED, new AbstractAction(Constants.REVERSE_THRUST_RELEASED)
				{
			        public void actionPerformed(ActionEvent arg0) 
			        {
			        	keyState.setKeyUp(Keymask.REVERSE_THRUST_KEY);
			        }
			    });
			    
			    actionMap.put(Constants.TOGGLE, new AbstractAction(Constants.TOGGLE)
				{
			        public void actionPerformed(ActionEvent arg0) 
			        {
			        	clearMultiKeyEvent();
			        	
			    		Motion.toggleObserving();
			        }
			    });
			    
			    actionMap.put(Constants.STOP, new AbstractAction(Constants.STOP)
				{
			        public void actionPerformed(ActionEvent arg0) 
			        {
			        	clearMultiKeyEvent();
			        	System.out.println("Stop");
			        	//StarDefender.getStarDefender().destroyStarDefender();
			        }
				});
			}
		}
	}
	
	private void clearMultiKeyEvent()
	{
		keyStates = Keymask.NO_KEY;
    	
    	keyState.clear();    	
	}
	
	// - - - Controls are set up here - - - 
	private void processEvent()
	{
		if (Keystate.isAnyKeyPressed(keyStates))
		{
			//left and right key together should use one or the other not both
			if (Keystate.isPressed(keyStates, Keymask.LEFT_KEY))
			{
	        	System.out.println("Left");
			}
			else if (Keystate.isPressed(keyStates, Keymask.RIGHT_KEY))
			{
				System.out.println("Right");
			}
	
			//forward thrust and reverse thrust key together should use one or the other not both
			if (Keystate.isPressed(keyStates, Keymask.FORWARD_THRUST_KEY))
			{	
				System.out.println("ForwardThrust");
	        	starDefender.fooVelocity(configurations.getForwardThrust());

				SoundManager.getSoundManager().forwardThrust();
			}
			else if (Keystate.isPressed(keyStates, Keymask.REVERSE_THRUST_KEY))
			{
				System.out.println("ReverseThrust");
				starDefender.fooVelocity(configurations.getReverseThrust());
			}
		}
		
		if (!Keystate.isPressed(keyStates, Keymask.FORWARD_THRUST_KEY) && 
				!Keystate.isPressed(keyStates, Keymask.REVERSE_THRUST_KEY))
			starDefender.applyFriction(configurations.getFriction());
	}
	
	
	private void unbindInputMap(final ShapeDisplay shapeDisplay)
	{
		if (shapeDisplay != null)
		{
			ArrayList<InputMap> inputMaps = new ArrayList<InputMap>();
			
			inputMaps.add(shapeDisplay.getInputMap(ShapeDisplay.WHEN_IN_FOCUSED_WINDOW));
			inputMaps.add(shapeDisplay.getInputMap(ShapeDisplay.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT));
			inputMaps.add(shapeDisplay.getInputMap(ShapeDisplay.WHEN_IN_FOCUSED_WINDOW));

			Iterator<InputMap> iiInputMap = inputMaps.iterator();
			while (iiInputMap.hasNext())
			{
				InputMap inputMap = iiInputMap.next();
			    if (inputMap != null)
			    {
			    	inputMap.clear();
			    }
			}
		}
	}
	
	private void unbindActionMap(final ShapeDisplay shapeDisplay)
	{
		if (shapeDisplay != null)
		{	   
			ActionMap actionMap = shapeDisplay.getActionMap();
		    if (actionMap != null)
		    {
		    	actionMap.clear();
		    }
		}
	}

	private void startRepeat()
	{
		int repeatspeed = Configurations.getConfigurations().getRepeatKeySpeed();
		if (repeatspeed > 0)
		{
			if (scheduler == null)
			{
				NamedThreadFactory keyboardThread = new NamedThreadFactory("Keyboard");
				
				scheduler = new ScheduledThreadPoolExecutor(1, keyboardThread);
				if (scheduler != null)
				{
					scheduler.scheduleAtFixedRate(this, 0, repeatspeed, TimeUnit.MILLISECONDS);
				}
			}
		}
	}

	private void stopRepeat()
	{
		if (scheduler != null)
		{
			scheduler.shutdown();
			
			scheduler = null;
		}
	}
	
	public void run()
	{
		processEvent();
		
		keyStates = keyState.allKeyPressedStates();
	}
}
