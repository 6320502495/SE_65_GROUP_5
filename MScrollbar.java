package equipments;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;

import control.MHandler;
import equipments.Figure.FigureShape;
import equipments.MFrame.MovableDirection;
import stage.Script.MGroup;

public class MScrollbar extends Sprite {
	public enum ScrollbarType {
		HORIZONTAL, VERTICAL
	}

	private ScrollbarType scrollbarType;

	public final ScrollbarType scrollbarType() {
		return scrollbarType;
	}

	private int currentValue = 0;

	public final int currentValue() {
		return currentValue;
	}

	public final void setCurrentValue(int currentValue) {
		if (currentValue < 0)
			throw new IllegalArgumentException("Current value cannot be negative");
		if (currentValue > maxValue)
			throw new IllegalArgumentException("Current value cannot be greater than max value");
		if (scrollbarType == ScrollbarType.HORIZONTAL)
			mFrame.setPoint.x(point.x + currentValue + 5);
		else
			mFrame.setPoint.y(point.y + currentValue + 5);
	}

	public final int changeCurrentValue(int change) {
		int exceed = 0;
		currentValue += change;
		if (currentValue < 0) {
			exceed = currentValue;
			currentValue = 0;
		} else if (currentValue > maxValue) {
			exceed = currentValue - maxValue;
			currentValue = maxValue;
		}
		if (scrollbarType == ScrollbarType.HORIZONTAL)
			mFrame.setPoint.x(point.x + currentValue + 5);
		else
			mFrame.setPoint.y(point.y + currentValue + 5);
		return exceed;
	}

	private int maxValue = 0;

	public final int maxValue() {
		return maxValue;
	}

	public final void setMaxValue(int maxValue) {
		if (maxValue < 0)
			throw new IllegalArgumentException("Max value cannot be negative");
		if (maxValue < currentValue)
			throw new IllegalArgumentException("Max value cannot be less than current value");
		if (scrollbarType == ScrollbarType.HORIZONTAL) {
			if (maxValue > dimension.width - 20)
				throw new IllegalArgumentException("Max value must greater than width - 20");
		} else if (maxValue > dimension.height - 20)
			throw new IllegalArgumentException("Max value must greater than height - 20");
		this.maxValue = maxValue;
	}

	private MFrame mFrame = null;

	public MScrollbar(MGroup mGroup, Point point, Dimension dimension, ScrollbarType scrollbarType, int currentValue,
			int maxValue) {
		super(mGroup, point, dimension);
		if (dimension.width < 20)
			throw new IllegalArgumentException("Width cannot be less than 20");
		if (dimension.height < 20)
			throw new IllegalArgumentException("Height cannot be less than 20");
		if (scrollbarType == null)
			throw new IllegalArgumentException("Scrollbar type cannot be null");
		this.scrollbarType = scrollbarType;
		setMaxValue(maxValue);
		if (currentValue < 0)
			throw new IllegalArgumentException("Current value cannot be negative");
		if (currentValue > maxValue)
			throw new IllegalArgumentException("Current value cannot be greater than max value");
		this.currentValue = currentValue;
		if (scrollbarType == ScrollbarType.HORIZONTAL) {
			mFrame = new MFrame(mGroup, new Point(point.x + currentValue + 5, point.y + 5),
					new Figure(FigureShape.RECTANGLE,
							new int[] { dimension.width - 10 - maxValue, dimension.height - 10 }),
					true, false, MovableDirection.HORIZONTAL);
		} else {
			mFrame = new MFrame(mGroup, new Point(point.x + 5, point.y + currentValue + 5),
					new Figure(FigureShape.RECTANGLE,
							new int[] { dimension.width - 10, dimension.height - 10 - maxValue }),
					true, false, MovableDirection.VERTICAL);
		}
		MHandler.add(mFrame);
	}

	@Override
	public final void loop(Graphics g) {
		if (scrollbarType == ScrollbarType.HORIZONTAL) {
			if (mFrame.point().x < point().x + 5)
				mFrame.bounceX(mFrame.point().x, point().x + 5);
			else if (mFrame.point().x > point().x + maxValue + 5) {
				mFrame.bounceX(mFrame.point().x, point().x + maxValue + 5);
			}
			currentValue = mFrame.point().x - point().x - 5;
		} else {
			if (mFrame.point().y < point().y + 5)
				mFrame.bounceY(mFrame.point().y, point().y + 5);
			else if (mFrame.point().y > point().y + maxValue + 5) {
				mFrame.bounceY(mFrame.point().y, point().y + maxValue + 5);
			}
			currentValue = mFrame.point().y - point().y - 5;
		}
		if (mFrame.isTouched() || mFrame.isDragged())
			g.setColor(Color.DARK_GRAY);
		else
			g.setColor(Color.GRAY);
		if (mFrame.isDragged())
			g.fillRect(mFrame.point().x - 5, mFrame.point().y - 5, mFrame.dimension().width + 10,
					mFrame.dimension().height + 10);
		else
			g.fillRect(mFrame.point().x, mFrame.point().y, mFrame.dimension().width, mFrame.dimension().height);
		g.setColor(Color.WHITE);
		g.drawRect(point.x, point.y, dimension.width, dimension.height);
	}
}
