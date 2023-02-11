package control;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.util.ArrayList;

public class MouseInput extends MouseAdapter {
	private static ArrayList<Integer> mouseEvents = new ArrayList<Integer>();
	private static ArrayList<Integer> mouseEventsLatest = new ArrayList<Integer>();
	private static final ArrayList<Integer> mouseEventsLocked = new ArrayList<Integer>();

	public static enum MouseWheelMovement {
		STILL, UP, DOWN
	}

	static MouseWheelMovement mouseWheelMovement = MouseWheelMovement.STILL;
	static MouseWheelMovement mouseWheelMovementLatest = MouseWheelMovement.STILL;

	private static enum MouseWheelStatus {
		RECENTLY_MOVED, MOVED, STAY_STILL
	}

	private static MouseWheelStatus mouseWheelStatus = MouseWheelStatus.STAY_STILL;

	@Override
	public final void mousePressed(MouseEvent e) {
		mouseEvents.add(MouseEvent.MOUSE_PRESSED);
		mouseEvents.add((MouseEvent.MOUSE_PRESSED + 1) * (e.getButton() + 1));
		if (!mouseEventsLocked.contains(e.getButton()))
			mouseEventsLocked.add(e.getButton());
	}

	@Override
	public final void mouseReleased(MouseEvent e) {
		mouseEvents.add(MouseEvent.MOUSE_RELEASED);
		mouseEvents.add((MouseEvent.MOUSE_RELEASED + 1) * (e.getButton() + 1));
		mouseEventsLocked.remove(Integer.valueOf(e.getButton()));
	}

	@Override
	public final void mouseWheelMoved(MouseWheelEvent e) {
		if (e.getWheelRotation() < 0)
			mouseWheelMovement = MouseWheelMovement.UP;
		else if (e.getWheelRotation() > 0)
			mouseWheelMovement = MouseWheelMovement.DOWN;
		mouseWheelStatus = MouseWheelStatus.RECENTLY_MOVED;
	}

	static final void loop() {
		switch (mouseWheelStatus) {
		case RECENTLY_MOVED:
			mouseWheelStatus = MouseWheelStatus.MOVED;
			break;
		case MOVED:
			mouseWheelStatus = MouseWheelStatus.STAY_STILL;
			break;
		case STAY_STILL:
			mouseWheelMovement = MouseWheelMovement.STILL;
			break;
		}
		mouseWheelMovementLatest = mouseWheelMovement;
		mouseEventsLatest = mouseEvents;
		mouseEvents = new ArrayList<Integer>();
	}

	public static final boolean detect(int mouseID, int mouseButton) {
		return mouseEventsLatest.contains((mouseID + 1) * (mouseButton + 1));
	}

	public static final boolean detect(int mouseID) {
		return mouseEventsLatest.contains(mouseID);
	}

	public static final boolean detect(MouseWheelMovement mouseWheelMovement) {
		if (mouseWheelMovement == null)
			throw new IllegalArgumentException("mouseWheelMovement cannot be null");
		return mouseWheelMovementLatest == mouseWheelMovement;
	}

	public static final boolean detectStuck(int mouseButton) {
		return mouseEventsLocked.contains(mouseButton);
	}
}