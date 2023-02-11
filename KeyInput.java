package control;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class KeyInput extends KeyAdapter {
	private static ArrayList<Integer> keyEvents = new ArrayList<Integer>();
	private static ArrayList<Integer> keyEventsLatest = new ArrayList<Integer>();
	private static final ArrayList<Integer> keyEventsLocked = new ArrayList<Integer>();
	private static boolean updated = false;
	private static boolean updatedLatest = false;
	
	public static boolean updated() {
		return updatedLatest;
	}

	@Override
	public final void keyPressed(KeyEvent e) {
		keyEvents.add(KeyEvent.KEY_PRESSED);
		keyEvents.add((KeyEvent.KEY_PRESSED + 1) * e.getKeyCode());
		if (!keyEventsLocked.contains(e.getKeyCode())) {
			keyEventsLocked.add(e.getKeyCode());
			updated = true;
		}	
		if (!keyEventsLocked.contains(e.getExtendedKeyCode())) {
			keyEventsLocked.add(e.getExtendedKeyCode());
			updated = true;
		}
	}

	@Override
	public final void keyReleased(KeyEvent e) {
		keyEvents.add(KeyEvent.KEY_RELEASED);
		keyEvents.add((KeyEvent.KEY_RELEASED + 1) * e.getKeyCode());
		keyEvents.add((KeyEvent.KEY_RELEASED + 1) * e.getExtendedKeyCode());
		if (keyEventsLocked.contains((int) e.getKeyCode())) {
			updated = true;
			keyEventsLocked.remove(Integer.valueOf(e.getKeyCode()));
			keyEventsLocked.remove(Integer.valueOf(e.getExtendedKeyCode()));
		}
	}

	static final void loop() {
		keyEventsLatest = keyEvents;
		updatedLatest = updated;
		updated = false;
		keyEvents = new ArrayList<Integer>();
	}

	public static final boolean detect(int keyID, int keyCode) {
		return keyEventsLatest.contains((keyID + 1) * keyCode);
	}

	public static final boolean detect(int keyID) {
		return keyEventsLatest.contains(keyID);
	}

	public static final boolean detectStuck(int keyCode) {
		return keyEventsLocked.contains(keyCode);
	}
}
