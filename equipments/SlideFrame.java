package equipments;

import java.awt.Graphics;
import java.awt.Point;

import control.MHandler;
import control.MPainter;
import equipments.Figure.FigureShape;
import equipments.MFrame.MovableDirection;
import stage.Script.MGroup;

public class SlideFrame extends MObject {
	private Point point = new Point(0, 0);
	public Point point() {
		return point;
	}
	
	public final void setPoint(Point point) {
		lastPoint = point;
	}
	
	public final SetPoint setPoint = new SetPoint();

	public final class SetPoint {
		public final void x(int x) {
			lastPoint.x = x;
		}

		public final void y(int y) {
			lastPoint.y = y;
		}
	}
	
	private MFrame mFrame;
	private Point lastPoint = new Point(0, 0);
	private Point relativePoint = new Point(0, 0);
	private boolean saved = true;

	public SlideFrame(MGroup groups) {
		super(groups);
		mFrame = new MFrame(groups, new Point(0, 0),
				new Figure(FigureShape.RECTANGLE, new int[] { MPainter.WIDTH, MPainter.HEIGHT }), true, false,
				MovableDirection.FREE);
		MHandler.add(mFrame);
	}

	@Override
	public final void loop(Graphics g) {
		point.x = lastPoint.x + relativePoint.x;
		point.y = lastPoint.y + relativePoint.y;
		if (mFrame.isDragged()) {
			saved = false;
			relativePoint = mFrame.point();
		} else if (!saved) {
			saved = true;
			lastPoint.x += relativePoint.x;
			lastPoint.y += relativePoint.y;
			relativePoint = new Point(0, 0);
			mFrame.setPoint(new Point(0, 0));
		}
	}
}
