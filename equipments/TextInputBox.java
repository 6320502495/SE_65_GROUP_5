package equipments;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import control.MHandler;
import control.MPainter;
import equipments.TextInputter.Mode;
import stage.Script.MGroup;

public class TextInputBox extends MObject {
	private MButton mButton;

	public MButton mButton() {
		return mButton;
	}

	private TextInputter textInputter;
	private Point offset;
	private Color color;
	private Font font;
	public boolean accepting = false;
	public boolean legal = true;

	public final boolean receiving() {
		return mButton.triggered;
	}

	public final void reset() {
		textInputter.reset();
	}

	public TextInputBox(MGroup mGroup, Point point, Figure figure, String[] images, Mode mode, int max, Point offset,
			Font font, Color color) throws IOException {
		super(mGroup);
		if (offset == null)
			throw new IllegalArgumentException("offset cannot be null");
		if (font == null)
			throw new IllegalArgumentException("font cannot be null");
		if (color == null)
			throw new IllegalArgumentException("color cannot be null");
		mButton = new MButton(mGroup, point, figure, images, false, false) {
			private BufferedImage[] bufferedImages;

			public void setImages(String[] images) throws IOException {
				if (images.length != 8)
					throw new IllegalArgumentException("images's length must be 8");
				for (int i = 0; i < 8; i++)
					if (images[i] == null)
						throw new IllegalArgumentException("images cannot be null");
				this.images = images;
				bufferedImages = new BufferedImage[8];
				for (int i = 0; i < 8; i++)
					bufferedImages[i] = ImageIO.read(new File(MPainter.imagePath() + images[i]));
			}

			@Override
			public final void loop(Graphics g) {
				if (triggerable)
					if (mFrame.isPressed(MouseEvent.BUTTON1)) {
						if (triggered)
							check();
						triggered = !triggered;
					}
				if (buttonPanel != null && refreshingNeed) {
					buttonPanel.refresh(this);
					refreshingNeed = false;
				}
				try {
					if (mFrame.isTouched())
						if (triggered)
							g.drawImage(bufferedImages[3], mFrame.point().x, mFrame.point().y, null);
						else if (textInputter.read().compareTo("") == 0)
							g.drawImage(bufferedImages[1], mFrame.point().x, mFrame.point().y, null);
						else if (legal)
							g.drawImage(bufferedImages[5], mFrame.point().x, mFrame.point().y, null);
						else
							g.drawImage(bufferedImages[7], mFrame.point().x, mFrame.point().y, null);
					else if (triggered)
						g.drawImage(bufferedImages[2], mFrame.point().x, mFrame.point().y, null);
					else if (textInputter.read().compareTo("") == 0)
						g.drawImage(bufferedImages[0], mFrame.point().x, mFrame.point().y, null);
					else if (legal)
						g.drawImage(bufferedImages[4], mFrame.point().x, mFrame.point().y, null);
					else
						g.drawImage(bufferedImages[6], mFrame.point().x, mFrame.point().y, null);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};
		textInputter = new TextInputter(mGroup, mode, max);
		this.offset = offset;
		this.font = font;
		this.color = color;
		MHandler.add(mButton);
		MHandler.add(textInputter);
	}

	public void check() {
		// anything
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
