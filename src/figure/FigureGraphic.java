package figure;
import java.awt.*;
import java.io.Serializable;

import ui.Env;

@SuppressWarnings("serial")
public abstract class FigureGraphic implements Figure, Serializable
{
    protected Color colorStroke, colorBackground;
	protected String name;
	
	/**
	 * Seuil de terminaison d'une figure en pixel
	 * Cela correspond au seuil de visibilite d'une figure / du rapprochement de deux points. 
	 * En dessous, on considere que c'est trop petit pour etre valide
	 */
	protected static final int THRESHOLD_BUILDING_PX = 8;
	
	/**
	 * A selected figure should display differently
	 */
	protected boolean selected = false;
	
	protected boolean transparent = false;
	
	protected boolean building = false; // L'objet est en train d'etre construit
	
	public boolean isBuilding() {
        return building;
    }

    public void setBuilding(boolean building) {
        this.building = building;
    }

    public FigureGraphic (String name, Color colorStroke, Color colorBackground) {
		this.colorStroke = colorStroke;	
		this.colorBackground = colorBackground;	
		this.name = name;
	}

    public FigureGraphic (String name) {
    	this(name, Color.black, Color.white);
	}
    
    public void setColors(Env env) {
    	setColors(env.getStrokeColor(), env.getBackgroundColor());
    }
	public void setColors(Color stroke, Color bg) {
		colorStroke = stroke;
    	colorBackground = bg;
	}

	public Color getStrokeColor()
	{
		return colorStroke;	
	}

	public Color getBackgroundColor()
	{
		return colorBackground;	
	}

	public void setStrokeColor(Color c) {
		colorStroke = c;
	}

	public void setBackgroundColor(Color c) {
		colorBackground = c;
	}

	public static double distance(Figure f1, Figure f2)
	{
		return Point_2D.distance(f1.getCenter(), f2.getCenter());
	}

	protected void drawCenter(Graphics g) {
		Point_2D c = getCenter();
		g.drawLine(c.x-1, c.y, c.x+1, c.y);
		g.drawLine(c.x, c.y-1, c.x, c.y+1);
	}
	
	protected void drawName(Graphics g) {
		Point_2D c = getCenter();
		g.drawString(name, c.x+2, c.y+12);
	}
	
	public abstract void draw(Graphics g);

	public void afterDraw(Graphics g) {
		if(isSelected()) {
			drawCenter(g);
			drawName(g);
		}
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		return getName();
	}

	public Color getStrokeForCurrentState() {
		Color c = colorStroke;
		return transparent || building ? new Color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()/2) : c;
	}
	public Color getBgForCurrentState() {
		Color c = colorBackground;
		return transparent || building ? new Color(c.getRed(), c.getGreen(), c.getBlue(),  (c.getAlpha()*2)/3) : c;
	}

	public void setSelected(boolean s) {
		selected = s;
	}
	public void setTransparent(boolean o) {
		transparent = o;
	}
	public boolean isSelected() {
		return selected;
	}
	
	public abstract void init(Env env, int x, int y);
	public abstract boolean canBeFinishedWithMouse();
	public abstract boolean canBeFinishedWithKey();
	public abstract void onFigureFinish();
	public abstract void onPressPoint(int x, int y);
	public abstract void onReleasePoint(int x, int y);
	public abstract void onMovePoint(int x, int y);

	public abstract String getShapeName();
}