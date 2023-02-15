package control;

import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class FileManager {
	private static final BufferedImage toCompatibleImage(BufferedImage image) {
		GraphicsConfiguration gfxConfig = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice()
				.getDefaultConfiguration();
		if (image.getColorModel().equals(gfxConfig.getColorModel()))
			return image;
		BufferedImage newImage = gfxConfig.createCompatibleImage(image.getWidth(), image.getHeight(),
				image.getTransparency());
		Graphics2D g2d = newImage.createGraphics();
		g2d.drawImage(image, 0, 0, null);
		g2d.dispose();
		return newImage;
	}
	
	public static final BufferedImage readImage(String image) throws IOException {
		return toCompatibleImage(ImageIO.read(new File(MPainter.imagePath() + image)));
	}
}
