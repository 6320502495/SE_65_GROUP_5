package equipments;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;

import control.MHandler;
import equipments.TextInputter.Mode;
import stage.Script.MGroup;

public class TextInputBox extends MObject {
	private MButton mButton;
	private TextInputter textInputter;
	private Point offset;
	private Color color;
	private Font font;
	public boolean accepting = false;
	
	public boolean receiving() {
		return mButton.triggered;
	}
	
	public void reset() {
		textInputter.reset();
	}

	public TextInputBox(MGroup mGroup, Point point, Figure figure, String[] images, Mode mode, int max, Point offset,
			Font font) {
		super(mGroup);
		if (offset == null)
			throw new IllegalArgumentException("offset cannot be null");
		if (font == null)
			throw new IllegalArgumentException("font cannot be null");
		mButton = new MButton(mGroup, point, figure, images, false, false);
		textInputter = new TextInputter(mGroup, mode, max);
		this.offset = offset;
		this.font = font;
		MHandler.add(mButton);
		MHandler.add(textInputter);
	}

	@Override
	public void loop(Graphics g) {
		mButton.triggerable = accepting;
		textInputter.reading = mButton.triggered;
		g.setColor(color);
		g.setFont(font);
		g.drawString("" + textInputter.read(), mButton.point.x + offset.x, mButton.point.y + offset.y);
	}
}
