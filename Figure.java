package equipments;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.image.Raster;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import control.MPainter;

public class Figure {
	private ArrayList<Integer> pixels = new ArrayList<Integer>();

	public ArrayList<Integer> pixels() {
		return pixels;
	}

	private Dimension dimension;

	public Dimension dimension() {
		return dimension;
	}

	public Figure(String figureShape) {
		Raster raster = null;
		try {
			dimension = new Dimension(
					ImageIO.read(new File(MPainter.figurePath() + figureShape)).getRaster().getWidth(),
					ImageIO.read(new File(MPainter.figurePath() + figureShape)).getRaster().getHeight());
			raster = ImageIO.read(new File(MPainter.figurePath() + figureShape)).getRaster();
		} catch (IOException e) {
			e.printStackTrace();
		}
		int numBands = raster.getNumBands();
		int width = raster.getWidth();
		int height = raster.getHeight();
		int[] pixelRow = new int[width * numBands];
		ArrayList<ArrayList<Integer>> colorPixels = new ArrayList<ArrayList<Integer>>();
		for (int i = 0; i < height; i++) {
			colorPixels.add(new ArrayList<Integer>());
			for (double p : raster.getPixels(raster.getMinX(), raster.getMinY() + i, width, 1, pixelRow))
				colorPixels.get(i).add((int) p);
		}
		ArrayList<Boolean> blackPixels = new ArrayList<Boolean>();
		for (int h = 0; h < colorPixels.size(); h++) {
			if (numBands == 3)
				for (int w = 0; w < colorPixels.get(h).size(); w += 3) {
					double red = colorPixels.get(h).get(w);
					double green = colorPixels.get(h).get(w + 1);
					double blue = colorPixels.get(h).get(w + 2);
					if (red == 255 && green == 255 && blue == 255)
						blackPixels.add(false);
					else
						blackPixels.add(true);
				}
			else
				for (int w = 0; w < colorPixels.get(h).size(); w += 4) {
					double red = colorPixels.get(h).get(w);
					double green = colorPixels.get(h).get(w + 1);
					double blue = colorPixels.get(h).get(w + 2);
					if (red == 255 && green == 255 && blue == 255)
						blackPixels.add(false);
					else
						blackPixels.add(true);
				}
		}
		boolean show = false;
		int length = 0;
		for (Boolean b : blackPixels) {
			if (b && !show) {
				pixels.add(length);
				show = true;
				length = 0;
			} else if (!b && show) {
				pixels.add(length);
				show = false;
				length = 0;
			}
			length++;
		}
		if (show)
			pixels.add(length);
	}

	public static enum FigureShape {
		RECTANGLE
	}

	public Figure(FigureShape figureShape, int[] scale) {
		if (figureShape == null)
			throw new IllegalArgumentException("figureShape cannot be null");
		switch (figureShape) {
		case RECTANGLE:
			if (scale.length != 2)
				throw new IllegalArgumentException("scale length has to be 2");
			if (scale[0] < 0)
				throw new IllegalArgumentException("width cannot be negative");
			if (scale[1] < 0)
				throw new IllegalArgumentException("height cannot be negative");
			dimension = new Dimension(scale[0], scale[1]);
			pixels.add(0);
			pixels.add(scale[0] * scale[1]);
			break;
		}
	}

	public final boolean isTouched(Point point) {
		int mouseX = MouseInfo.getPointerInfo().getLocation().x;
		int mouseY = MouseInfo.getPointerInfo().getLocation().y;
		int h = 0;
		int w = 0;
		boolean show = false;
		loop: for (Integer p : pixels()) {
			int remaining = p;
			while (remaining > 0)
				if (w + remaining <= dimension.width) {
					if (show)
						if (point.x + w <= mouseX && mouseX <= point.x + w + remaining)
							if (point.y + h == mouseY)
								return true;
					w += remaining;
					remaining = 0;
				} else {
					if (show)
						if (point.x + w <= mouseX && mouseX <= point.x + dimension.width)
							if (point.y + h == mouseY)
								return true;
					remaining -= dimension.width - w;
					w = 0;
					h++;
					if (h > dimension.height)
						break loop;
				}
			show = !show;
		}
		return false;
	}

	public final void paint(Graphics g, Color color, Point point) {
		g.setColor(color);
		int h = 0;
		int w = 0;
		boolean show = false;
		loop: for (Integer p : pixels()) {
			int remaining = p;
			while (remaining > 0) {
				if (w + remaining <= dimension.width) {
					if (show)
						g.fillRect(point.x + w, point.y + h, remaining, 1);
					w += remaining;
					remaining = 0;
				} else {
					if (show)
						g.fillRect(point.x + w, point.y + h, dimension.width - w, 1);
					remaining -= dimension.width - w;
					w = 0;
					h++;
					if (h > dimension.height)
						break loop;
				}
			}
			show = !show;
		}
	}
}