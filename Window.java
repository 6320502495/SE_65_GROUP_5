package control;

import java.awt.Dimension;

import javax.swing.JFrame;

class Window {

	Window(Dimension dimension, String title, MPainter mPainter) {
		JFrame frame = new JFrame(title);

		frame.setPreferredSize(dimension);
		frame.setMaximumSize(dimension);
		frame.setMinimumSize(dimension);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.add(mPainter);
		frame.setUndecorated(true);
		frame.setVisible(true);
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
	}

	public static final void main(String[] args) throws Exception {
		MPainter mPainter = new MPainter("M2");	
		mPainter.start();
	}
}
