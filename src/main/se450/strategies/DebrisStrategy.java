package main.se450.strategies;

import main.se450.interfaces.IStrategy;
import main.se450.model.Shape;

public class DebrisStrategy implements IStrategy {

	@Override
	public void execute(Shape shape) {
		new java.util.Timer().schedule( 
		        new java.util.TimerTask() {
		            @Override
		            public void run() {
		            	shape.expire();
		            	try{
		            	shape.getLineCollection().clear();
		            	} catch (NullPointerException e){}
		            }
		        }, 
		        1000 
		);

	}

}
