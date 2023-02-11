package equipments;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;

import stage.Script.MGroup;

public abstract class Sprite extends MObject {
	protected Point point;
	protected Point previousPoint = new Point(0, 0);

	public final Point point() {
		return point;
	}

	public final void setPoint(Point point) {
		if (point == null)
			throw new IllegalArgumentException("Point cannot be null");
		this.point = point;
	}

	public final SetPoint setPoint = new SetPoint();

	public final class SetPoint {
		public final void x(int x) {
			point.x = x;
		}

		public final void y(int y) {
			point.y = y;
		}
	}
	
	public final ChangePoint changePoint = new ChangePoint();

	public final class ChangePoint {
		public final void x(int x) {
			point.x += x;
		}

		public final void y(int y) {
			point.y += y;
		}
	}

	protected Dimension dimension;
	protected Dimension previousDimension = new Dimension(0, 0);

	public final Dimension dimension() {
		return dimension;
	}

	public final void setDimension(Dimension dimension) {
		if (dimension == null)
			throw new IllegalArgumentException("dimension cannot be null");
		this.dimension = dimension;
	}

	public final SetDimension setDimension = new SetDimension();

	public final class SetDimension {
		public final void width(int width) {
			dimension.width = width;
		}

		public final void height(int height) {
			dimension.height = height;
		}
	}
	
	public final ChangeDimension changeDimension = new ChangeDimension();

	public final class ChangeDimension {
		public final void width(int width) {
			dimension.width += width;
		}

		public final void height(int height) {
			dimension.height += height;
		}
	}

	protected ArrayList<Integer[]> kdTreeNearSprites = null;

	public ArrayList<Integer[]> kdTreeNearSprites() {
		return kdTreeNearSprites;
	}

	protected Sprite(MGroup mGroup, Point point, Dimension dimension) {
		super(mGroup);
		setPoint(point);
		previousPoint.x = point.x;
		previousPoint.y = point.y;
		setDimension(dimension);
		previousDimension.width = dimension.width;
		previousDimension.height = dimension.height;
	}

	public abstract void loop(Graphics g);
}
