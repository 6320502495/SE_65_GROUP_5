package equipments;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.MouseEvent;

import control.MHandler;
import control.MouseInput;
import stage.Script.MGroup;

public class MFrame extends Sprite implements KDTree {
	public boolean draggable = false;

	public boolean showing = false;

	private Figure figure;

	public Figure figure() {
		return figure;
	}

	public void setFigure(Figure figure) {
		if (figure == null)
			throw new IllegalArgumentException();
		this.figure = figure;
	}

	protected Point relativePoint = new Point(0, 0);

	protected boolean dragged = false;

	protected boolean isTouched = false;

	public enum MovableDirection {
		FREE, HORIZONTAL, VERTICAL
	}
	
	private MovableDirection movableDirection;
	
	public MovableDirection movableDirection() {
		return movableDirection;
	}
	
	public void setMovableDirection(MovableDirection movableDirection) {
		if (movableDirection == null)
			throw new IllegalArgumentException("Movable direction cannot be null");
		this.movableDirection = movableDirection;
	}
	
	public void bounceX(int before, int after) {
		relativePoint.x += before - after;
		this.point.x = after;
	}
	
	public void bounceY(int before, int after) {
		relativePoint.y += before - after;
		this.point.y = after;
	}

	public MFrame(MGroup mGroup, Point point, Figure figure, boolean draggable, boolean showing,
			MovableDirection movableDirection) {
		super(mGroup, point, figure.dimension());
		setFigure(figure);
		this.draggable = draggable;
		this.showing = showing;
		setMovableDirection(movableDirection);
	}

	public boolean isIntersected(MFrame mFrame) {
		return false;
	}

	public boolean isTouched() {
		if (kdTreeNearSprites != null)
			return isTouched;
		return figure.isTouched(point);
	}

	public boolean isPressed(int mouseButton) {
		return isTouched() && MouseInput.detect(MouseEvent.MOUSE_PRESSED, mouseButton);
	}

	public boolean isStuck(int mouseButton) {
		return isTouched() && MouseInput.detectStuck(mouseButton);
	}

	public boolean isDragged() {
		return dragged;
	}

	@Override
	public void loop(Graphics g) {
		if (draggable)
			if (isPressed(MouseEvent.BUTTON1))
				dragged = true;
			else if (!MouseInput.detectStuck(MouseEvent.BUTTON1))
				dragged = false;
		if (dragged) {
			if (movableDirection != MovableDirection.VERTICAL)
				point.x = MouseInfo.getPointerInfo().getLocation().x - relativePoint.x;
			if (movableDirection != MovableDirection.HORIZONTAL)
				point.y = MouseInfo.getPointerInfo().getLocation().y - relativePoint.y;
		} else {
			if (movableDirection != MovableDirection.VERTICAL)
				relativePoint.x = MouseInfo.getPointerInfo().getLocation().x - point.x;
			if (movableDirection != MovableDirection.HORIZONTAL)
				relativePoint.y = MouseInfo.getPointerInfo().getLocation().y - point.y;
		}
		if (showing)
			if (MHandler.kdTreeCurrentLatest().contains(this))
				if (isTouched())
					figure.paint(g, Color.RED, point);
				else
					figure.paint(g, Color.GREEN, point);
			else if (isTouched())
				figure.paint(g, Color.YELLOW, point);
			else
				figure.paint(g, Color.BLUE, point);
		if (point.x != previousPoint.x || point.y != previousPoint.y || dimension.width != previousDimension.width
				|| dimension.height != previousDimension.height) {
			if (kdTreeNearSprites != null) {
				MHandler.removeKDTree(this);
				kdTreeNearSprites = null;
			}
		} else if (kdTreeNearSprites == null)
			kdTreeNearSprites = MHandler.updateKDTree(this);
		previousPoint.x = point.x;
		previousPoint.y = point.y;
		previousDimension.width = dimension.width;
		previousDimension.height = dimension.height;
	}

	@Override
	public void kdTreeReset() {
		isTouched = false;
	}

	@Override
	public void kdTreeLoop(Graphics g) {
		isTouched = figure.isTouched(point);
	}
}
