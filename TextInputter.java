package equipments;

import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.util.LinkedList;

import control.KeyInput;
import stage.Script.MGroup;

public class TextInputter extends MObject {
	public boolean reading = false;

	private LinkedList<Integer> pressedKeys = new LinkedList<Integer>();

	private String read = "";

	private String addedChars = "";

	public String read() {
		return read;
	}
	
	public void reset() {
		read = "";
	}

	public enum Mode {
		TEXT, NUMBER, INT, DOUBLE
	}

	private Mode mode;

	public Mode mode() {
		return mode;
	}

	public void setMode(Mode mode) {
		if (mode == null)
			throw new IllegalArgumentException("mode cannot be null");
		read = "";
		this.mode = mode;
	}

	public TextInputter(MGroup mGroup, Mode mode) {
		super(mGroup);
		setMode(mode);
	}

	private void detect(int keyCode, char keyChar) {
		if (KeyInput.detectStuck(keyCode)) {
			if (!pressedKeys.contains(keyCode)) {
				pressedKeys.add(keyCode);
				addedChars += keyChar;
			}
		} else if (pressedKeys.contains(keyCode))
			pressedKeys.remove(Integer.valueOf(keyCode));
	}

	private boolean detect(int keyCode) {
		if (KeyInput.detectStuck(keyCode)) {
			if (!pressedKeys.contains(keyCode)) {
				pressedKeys.add(keyCode);
				return true;
			}
		} else if (pressedKeys.contains(keyCode))
			pressedKeys.remove(Integer.valueOf(keyCode));
		return false;
	}

	private void deleteFirst() {
		String newAddedChars = "";
		for (int i = 1; i < addedChars.length(); i++)
			newAddedChars += addedChars.charAt(i);
		addedChars = newAddedChars;
	}

	@Override
	public void loop(Graphics g) {
		if (!reading || !KeyInput.updated())
			return;
		if (detect(KeyEvent.VK_DELETE)) {
			read = "";
			return;
		}
		boolean shifted = KeyInput.detectStuck(KeyEvent.VK_SHIFT);
		boolean capLocked = Toolkit.getDefaultToolkit().getLockingKeyState(KeyEvent.VK_CAPS_LOCK);
		addedChars = "";
		if (shifted && !capLocked || !shifted && capLocked) {
			detect(KeyEvent.VK_1, '!');
			detect(KeyEvent.VK_2, '@');
			detect(KeyEvent.VK_3, '#');
			detect(KeyEvent.VK_4, '$');
			detect(KeyEvent.VK_5, '%');
			detect(KeyEvent.VK_6, '^');
			detect(KeyEvent.VK_7, '&');
			detect(KeyEvent.VK_8, '*');
			detect(KeyEvent.VK_9, '(');
			detect(KeyEvent.VK_0, ')');
			detect(KeyEvent.VK_MINUS, '_');
			detect(KeyEvent.VK_EQUALS, '+');
			detect(KeyEvent.VK_Q, 'Q');
			detect(KeyEvent.VK_W, 'W');
			detect(KeyEvent.VK_E, 'E');
			detect(KeyEvent.VK_R, 'R');
			detect(KeyEvent.VK_T, 'T');
			detect(KeyEvent.VK_Y, 'Y');
			detect(KeyEvent.VK_U, 'U');
			detect(KeyEvent.VK_I, 'I');
			detect(KeyEvent.VK_O, 'O');
			detect(KeyEvent.VK_P, 'P');
			detect(KeyEvent.VK_OPEN_BRACKET, '{');
			detect(KeyEvent.VK_CLOSE_BRACKET, '}');
			detect(KeyEvent.VK_BACK_SLASH, '|');
			detect(KeyEvent.VK_A, 'A');
			detect(KeyEvent.VK_S, 'S');
			detect(KeyEvent.VK_D, 'D');
			detect(KeyEvent.VK_F, 'F');
			detect(KeyEvent.VK_G, 'G');
			detect(KeyEvent.VK_H, 'H');
			detect(KeyEvent.VK_J, 'J');
			detect(KeyEvent.VK_K, 'K');
			detect(KeyEvent.VK_L, 'L');
			detect(KeyEvent.VK_SEMICOLON, ':');
			detect(KeyEvent.VK_QUOTE, '\"');
			detect(KeyEvent.VK_Z, 'Z');
			detect(KeyEvent.VK_X, 'X');
			detect(KeyEvent.VK_C, 'C');
			detect(KeyEvent.VK_V, 'V');
			detect(KeyEvent.VK_B, 'B');
			detect(KeyEvent.VK_N, 'N');
			detect(KeyEvent.VK_M, 'M');
			detect(KeyEvent.VK_COMMA, '<');
			detect(KeyEvent.VK_PERIOD, '>');
			detect(KeyEvent.VK_SLASH, '?');
		} else {
			detect(KeyEvent.VK_1, '1');
			detect(KeyEvent.VK_2, '2');
			detect(KeyEvent.VK_3, '3');
			detect(KeyEvent.VK_4, '4');
			detect(KeyEvent.VK_5, '5');
			detect(KeyEvent.VK_6, '6');
			detect(KeyEvent.VK_7, '7');
			detect(KeyEvent.VK_8, '8');
			detect(KeyEvent.VK_9, '9');
			detect(KeyEvent.VK_0, '0');
			detect(KeyEvent.VK_MINUS, '-');
			detect(KeyEvent.VK_EQUALS, '=');
			detect(KeyEvent.VK_Q, 'q');
			detect(KeyEvent.VK_W, 'w');
			detect(KeyEvent.VK_E, 'e');
			detect(KeyEvent.VK_R, 'r');
			detect(KeyEvent.VK_T, 't');
			detect(KeyEvent.VK_Y, 'y');
			detect(KeyEvent.VK_U, 'u');
			detect(KeyEvent.VK_I, 'i');
			detect(KeyEvent.VK_O, 'o');
			detect(KeyEvent.VK_P, 'p');
			detect(KeyEvent.VK_OPEN_BRACKET, '[');
			detect(KeyEvent.VK_CLOSE_BRACKET, ']');
			detect(KeyEvent.VK_BACK_SLASH, '\\');
			detect(KeyEvent.VK_A, 'a');
			detect(KeyEvent.VK_S, 's');
			detect(KeyEvent.VK_D, 'd');
			detect(KeyEvent.VK_F, 'f');
			detect(KeyEvent.VK_G, 'g');
			detect(KeyEvent.VK_H, 'h');
			detect(KeyEvent.VK_J, 'j');
			detect(KeyEvent.VK_K, 'k');
			detect(KeyEvent.VK_L, 'l');
			detect(KeyEvent.VK_SEMICOLON, ';');
			detect(KeyEvent.VK_QUOTE, '\'');
			detect(KeyEvent.VK_Z, 'z');
			detect(KeyEvent.VK_X, 'x');
			detect(KeyEvent.VK_C, 'c');
			detect(KeyEvent.VK_V, 'v');
			detect(KeyEvent.VK_B, 'b');
			detect(KeyEvent.VK_N, 'n');
			detect(KeyEvent.VK_M, 'm');
			detect(KeyEvent.VK_COMMA, ',');
			detect(KeyEvent.VK_PERIOD, '.');
			detect(KeyEvent.VK_SLASH, '/');
		}
		detect(KeyEvent.VK_SPACE, ' ');
		if (detect(KeyEvent.VK_BACK_SPACE)) {
			if (addedChars.length() > 0) {
				String newAddedChars = "";
				for (int i = 0; i < addedChars.length() - 1; i++)
					newAddedChars += addedChars.charAt(i);
				addedChars = newAddedChars;
			} else if (read.length() > 0) {
				String newRead = "";
				for (int i = 0; i < read.length() - 1; i++)
					newRead += read.charAt(i);
				read = newRead;
			}
		}
		if (read.length() > 0)
			while (addedChars.length() > 0) {
				switch (mode) {
				case TEXT:
					break;
				case DOUBLE:
					switch (addedChars.charAt(0)) {
					case '0':
						if (read.charAt(0) == '0')
							deleteFirst();
						break;
					case '1':
					case '2':
					case '3':
					case '4':
					case '5':
					case '6':
					case '7':
					case '8':
					case '9':
						if (read.charAt(0) == '0')
							if (read.charAt(1) != '.')
								deleteFirst();
						break;
					case '.':
						if (read.contains("."))
							deleteFirst();
						break;
					default:
						deleteFirst();
						break;
					}
					break;
				case INT:
					switch (addedChars.charAt(0)) {
					case '0':
					case '1':
					case '2':
					case '3':
					case '4':
					case '5':
					case '6':
					case '7':
					case '8':
					case '9':
						if (read.charAt(0) == '0')
							deleteFirst();
						break;
					default:
						deleteFirst();
						break;
					}
					break;
				case NUMBER:
					switch (addedChars.charAt(0)) {
					case '0':
					case '1':
					case '2':
					case '3':
					case '4':
					case '5':
					case '6':
					case '7':
					case '8':
					case '9':
						break;
					default:
						deleteFirst();
						break;
					}
					break;
				}
				break;
			}
		read += addedChars;
	}
}
