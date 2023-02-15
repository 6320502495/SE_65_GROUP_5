package equipments;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import control.MPainter;

public class FixedBackground extends Background {
	private BufferedImage bufferedImage;
	
	public FixedBackground(String image) throws IOException {
		if (image == null)
			throw new IllegalArgumentException("image cannot be null");
		bufferedImage = ImageIO.read(new File(MPainter.imagePath() + image));
	}

	@Override
	public void loop(Graphics g) {
		g.drawImage(bufferedImage, 0, 0, null);
	}
}
