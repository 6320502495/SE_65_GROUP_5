package equipments;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import control.MHandler;
import control.MPainter;
import control.MouseInput;
import equipments.MFrame.MovableDirection;
import stage.Script.MGroup;

public class MButton extends Sprite {

	public final MFrame mFrame;

	protected String[] images;

	public final String[] images() {
		return images;
	}

	private final BufferedImage[] bufferedImages = new BufferedImage[4];

	public void setImages(String[] images) throws IOException {
		if (images.length != 4)
			throw new IllegalArgumentException("images's length must be 4");
		for (int i = 0; i < 4; i++)
			if (images[i] == null)
				throw new IllegalArgumentException("images cannot be null");
		this.images = images;
		for (int i = 0; i < 4; i++)
			bufferedImages[i] = ImageIO.read(new File(MPainter.imagePath() + images[i]));
	}

	public boolean triggerable = false;

	public boolean triggered = false;

	public ButtonPanel buttonPanel = null;

	protected boolean refreshingNeed = false;

	public MButton(MGroup mGroup, Point point, Figure figure, String[] images, boolean triggerable, boolean triggered)
			throws IOException {
		super(mGroup, point, figure.dimension());
		setImages(images);
		mFrame = new MFrame(mGroup, point, figure, false, false, MovableDirection.FREE) {
			public final boolean isPressed(int mouseButton) {
				if (isTouched() && MouseInput.detect(MouseEvent.MOUSE_PRESSED, mouseButton)) {
					refreshingNeed = true;
					return true;
				}
				return false;
			}
		};
		MHandler.add(mFrame);
		this.triggerable = triggerable;
		this.triggered = triggered;
	}

	@Override
	public void loop(Graphics g) {
		if (triggerable)
			if (mFrame.isPressed(MouseEvent.BUTTON1))
				triggered = !triggered;
		if (buttonPanel != null && refreshingNeed) {
			buttonPanel.refresh(this);
			refreshingNeed = false;
		}
		try {
			if (mFrame.isTouched())
				if (triggered)
					g.drawImage(bufferedImages[3], mFrame.point().x, mFrame.point().y, null);
				else
					g.drawImage(bufferedImages[1], mFrame.point().x, mFrame.point().y, null);
			else if (triggered)
				g.drawImage(bufferedImages[2], mFrame.point().x, mFrame.point().y, null);
			else
				g.drawImage(bufferedImages[0], mFrame.point().x, mFrame.point().y, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
