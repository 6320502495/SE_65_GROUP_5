package control;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.io.File;

import stage.Script;

public class MPainter extends Canvas {
	private static final long serialVersionUID = 1L;
	public static final int HEIGHT = 864; // 1080x1920 -> 841x1536
	public static final int WIDTH = 1536;
	private static int fps = -1;

	public static final int fps() {
		return fps;
	}

	private static BufferStrategy bs;
	private static Graphics g;
	private static Thread loopThread = null;
	private static boolean loopRunning = false;
	private static String imagePath = "";

	public static final String imagePath() {
		return imagePath;
	}

	private static String savePath = "";

	public static final String savePath() {
		return savePath;
	}

	private static String soundPath = "";

	public static final String soundPath() {
		return soundPath;
	}

	private static String figurePath = "";

	public static final String figurePath() {
		return figurePath;
	}

	MPainter(String projectName) {
		setPath();	
		
		new MHandler();
		new Window(new Dimension(WIDTH, HEIGHT), projectName, this);
		
		Script.load();
		MHandler.doneLoading();
		Script.setup();

		MouseInput mouseInput = new MouseInput();
		this.addMouseListener(mouseInput);
		this.addMouseWheelListener(mouseInput);

		this.addKeyListener(new KeyInput());
	}

	final void setPath() {
		String path = this.getClass().getClassLoader().getResource("control/MPainter.class").toString();
		String newPath = "";
		for (int i = 6; i < path.length() - 26; i++)
			newPath += path.toCharArray()[i];
		if (!new File(newPath + "resource/notRunnableJar.txt").canRead()) {
			newPath = "";
			for (int i = 10; i < path.length() - 30; i++) {
				if (path.toCharArray()[i] == '%')
					if (path.toCharArray()[i + 1] == '2')
						if (path.toCharArray()[i + 2] == '0') {
							newPath += ' ';
							i += 2;
							continue;
						}
				newPath += path.toCharArray()[i];
			}
		}
		imagePath = newPath + "resource/image/";
		savePath = newPath + "resource/save/";
		soundPath = newPath + "resource/sound/";
		figurePath = newPath + "resource/figure/";
	}

	final void start() {
		loopRunning = true;
		loopThread = new Thread(new Runnable() {
			public void run() {
				long timer = System.currentTimeMillis();
				int frame = 0;
				while (loopRunning) {
					render();
					frame++;
					if (System.currentTimeMillis() - timer >= 1000) {
						fps = frame;
						frame = 0;
						timer = System.currentTimeMillis();
					}
				}
				System.exit(0);
			}
		});
		loopThread.start();
	}

	public final static void stop() {
		loopRunning = false;
		try {
			loopThread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private final void render() {
		bs = this.getBufferStrategy();
		if (bs == null) {
			this.createBufferStrategy(3);
			return;
		}
		g = bs.getDrawGraphics();
		MHandler.loop(g);
		g.dispose();
		bs.show();
	}
}
