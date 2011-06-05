package figure;

import java.awt.Color;
import java.io.Serializable;

import ui.Env;

@SuppressWarnings("serial")
public class Triangle extends Polygon implements Serializable
{
    private static long nbOfTriangles = 0;
    
	public Triangle(String name, Color colorStroke, Color colorBackground, int x1, int y1, int x2, int y2, int x3, int y3) {
		super(name,colorStroke,colorBackground);
		addPoint(x1,y1);
		addPoint(x2,y2);
		addPoint(x3,y3);
		nbOfTriangles ++;
	}
	
    /**
     * Create by first click
     * @param env
     * @param x
     * @param y
     */
    public Triangle(Env env, int x, int y) {
        super("tri_"+(nbOfTriangles+1), env.getStrokeColor(), env.getBackgroundColor());
        addPoint(x, y);
        addPoint(x, y);
        setSelected(true);
        setBuilding(true);
        nbOfTriangles ++;
    }
    
    @Override
    public boolean isBuildingConvenientToBeFinished() {
        int size = points.size();
        return size==3;
    }
    
    @Override
    public void closePath() {
        setBuilding(false);
    }
    
    @Override
    protected boolean drawTerminaison() {
        return false;
    }
}