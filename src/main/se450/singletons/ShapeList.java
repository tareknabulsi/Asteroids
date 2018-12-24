package main.se450.singletons;

import java.util.ArrayList;

import main.se450.interfaces.IShape;

public class ShapeList
{
	private static ShapeList shapeList = null;
	
	private ArrayList<IShape> iShapes = null;
	private ArrayList<IShape> iShapesDebris = null;
	private ArrayList<IShape> debris = null;
	
	static
	{
		shapeList = new ShapeList();
	}
	
    private ShapeList()
    {
    	iShapes = new ArrayList<IShape>();
    	iShapesDebris = new ArrayList<IShape>();
    	debris = new ArrayList<IShape>();
    }
    
	public final static ShapeList getShapeList() 
	{
		return shapeList;
	}
	
	public final ArrayList<IShape> getShapes()
	{
		return iShapes;
	}
	
	public final ArrayList<IShape> getShapesDebris()
	{
		return iShapesDebris;
	}
	
	public void addShapes(final ArrayList<IShape> iShapeList)
	{
		iShapes.addAll(iShapeList);
	}
	
	public void addShapesDebris(final IShape iShape)
	{
		iShapesDebris.add(iShape);
	}
	
	public void addDebrisToShapes(){
		iShapes.addAll(iShapesDebris);
		iShapesDebris.removeAll(iShapesDebris);
	}
	
	public void addDebris(final IShape iShape)
	{
		debris.add(iShape);
	}

	public ArrayList<IShape> getDebris() {
		return debris;
	}
}
      