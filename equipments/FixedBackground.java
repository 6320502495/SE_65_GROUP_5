package equipments;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import control.FileManager;
import control.MPainter;

public class FixedBackground extends Background {
	private BufferedImage bufferedImage;

	public FixedBackground(String image) throws IOException {
		if (image == null)
			throw new IllegalArgumentException("image cannot be null");
		bufferedImage = FileManager.readImage(image);
	}

	@Override
	public void loop(Graphics g) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, MPainter.WIDTH, MPainter.HEIGHT);
		g.drawImage(bufferedImage, 0, 0, null);
	}
}
