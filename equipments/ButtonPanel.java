package equipments;

import java.util.ArrayList;

public class ButtonPanel {
	private ArrayList<MButton> connectedButtons = new ArrayList<MButton>();

	public final ArrayList<MButton> connectedButtons() {
		return connectedButtons;
	}

	private int maxTriggeredButtons = 1;

	public final int maxTriggeredButtons() {
		return maxTriggeredButtons;
	}

	public final void setMaxTriggeredButtons(int maxTriggeredButtons) {
		if (maxTriggeredButtons < 0)
			throw new IllegalArgumentException("maxTriggeredButtons cannot be negative");
		if (maxTriggeredButtons < minTriggeredButtons)
			throw new IllegalArgumentException("maxTriggeredButtons cannot be less than minTriggeredButtons");
		this.maxTriggeredButtons = maxTriggeredButtons;
	}

	private int minTriggeredButtons = 0;

	public final int minTriggeredButtons() {
		return minTriggeredButtons;
	}

	public final void setMinTriggeredButtons(int minTriggeredButtons) {
		if (minTriggeredButtons < 0)
			throw new IllegalArgumentException("minTriggeredButtons cannot be negative");
		if (minTriggeredButtons > maxTriggeredButtons)
			throw new IllegalArgumentException("minTriggeredButtons cannot be greater than maxTriggeredButtons");
		this.minTriggeredButtons = minTriggeredButtons;
	}

	public ButtonPanel(int maxTriggeredButton, int minTriggeredButton) {
		setMaxTriggeredButtons(maxTriggeredButton);
		setMinTriggeredButtons(minTriggeredButton);
	}

	public final void connect(MButton mButton) {
		if (mButton == null)
			throw new IllegalArgumentException("mButton cannot be null");
		if (connectedButtons.contains(mButton))
			throw new IllegalArgumentException("Cannot connect duplicated mButton");
		connectedButtons.add(mButton);
		mButton.buttonPanel = this;
	}
	
	public final void connect(TextInputBox textInputBox) {
		if (textInputBox == null)
			throw new IllegalArgumentException("textInputBox cannot be null");
		if (connectedButtons.contains(textInputBox.mButton()))
			throw new IllegalArgumentException("Cannot connect duplicated textInputBox");
		connectedButtons.add(textInputBox.mButton());
		textInputBox.mButton().buttonPanel = this;
	}

	public final void refresh(MButton mButton) {
		if (mButton == null)
			throw new IllegalArgumentException("mButton cannot be null");
		int triggeredAmount = 0;
		for (MButton b : connectedButtons)
			if (b.triggered)
				triggeredAmount++;
		if (triggeredAmount > maxTriggeredButtons)
			for (MButton b : connectedButtons)
				if (b.triggered)
					if (b != mButton) {
						b.triggered = false;
						triggeredAmount--;
						if (triggeredAmount == maxTriggeredButtons)
							break;
					}
		if (triggeredAmount < minTriggeredButtons)
			for (MButton b : connectedButtons)
				if (!b.triggered)
					if (b != mButton) {
						b.triggered = true;
						triggeredAmount++;
						if (triggeredAmount == minTriggeredButtons)
							break;
					}
	}
}
