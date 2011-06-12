package figure;

import java.awt.Color;
import java.io.Serializable;

@SuppressWarnings("serial")
public class Triangle extends Polygon implements Serializable
{
    private static long nbOfTriangles = 0;

	public Triangle(String name) {
		super(name);
		nbOfTriangles ++;
	}
	public Triangle() {
		this("tri_"+(nbOfTriangles+1));
	}
    
    public Triangle(String name, Color stroke, Color bg, int x1, int y1, int x2, int y2, int x3, int y3) {
		this(name);
		setColors(stroke, bg);
		addPoint(x1, y1);
		addPoint(x2, y2);
		addPoint(x3, y3);
	}

    public boolean canBeFinished() {
        return points.size()==4;
    }
    public boolean canBeFinishedWithKey() {
    	return canBeFinished();
    }
    public boolean canBeFinishedWithMouse() {
    	return canBeFinished();
    }
    
    @Override
    protected boolean drawTerminaisonEnabled() {
        return false;
    }
}