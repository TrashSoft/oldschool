package trash.oldschool.swing;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import trash.oldschool.engine.impl.GameEngine;
import trash.oldschool.engine.intf.GameCanvas;
import trash.oldschool.engine.intf.GameEngineStep;

public class SwingCanvas extends JPanel implements GameCanvas, MouseListener, MouseMotionListener {

	public static final Dimension PREFERRED_SIZE = new Dimension(800, 600);

	private GameEngine gameEngine;

	private BufferedImage buffer = null;
	private Dimension lastSize = new Dimension(PREFERRED_SIZE.width, PREFERRED_SIZE.height);

	@SuppressWarnings("unused")
	private boolean mouseLeftPressed = false;
	private boolean mouseRightPressed = false;
	@SuppressWarnings("unused")
	private Point mouseLeftAt = new Point(0, 0);
	private Point mouseRightAt = new Point(0, 0);
	private Point translate = new Point(0, 0);

	public SwingCanvas(GameEngine gameEngine) {
		this.gameEngine = gameEngine;
		setPreferredSize(PREFERRED_SIZE);
		addMouseListener(this);
		addMouseMotionListener(this);
	}

	public Component getRealCanvas() {
		return this;
	}

	@Override
	public void update(Graphics g) {
		paint(g);
	}

	@Override
	public void paint(Graphics g) {
		Dimension size = getSize();
		if(buffer == null || !lastSize.equals(size)) {
			buffer = new BufferedImage(size.width, size.height, BufferedImage.TYPE_INT_RGB);
			lastSize.width = size.width;
			lastSize.height = size.height;
		}

		Graphics2D g2d = (Graphics2D)buffer.getGraphics();
		g2d.setColor(Color.black);
		g2d.fillRect(0, 0, lastSize.width, lastSize.height);
		g2d.setColor(Color.white);

		paint2d(g2d);

		g.drawImage(buffer, 0, 0, null);
	}

	private void paint2d(Graphics2D g) {
		SwingGraphics graphics = new SwingGraphics(gameEngine, g, lastSize.width, lastSize.height);
		gameEngine.runStep(GameEngineStep.RENDER, graphics);
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		Point p = new Point(e.getX(), e.getY());

		if(mouseRightPressed) {
			translate.x += mouseRightAt.x - p.x;
			translate.y += mouseRightAt.y - p.y;

			mouseRightAt.x = p.x;
			mouseRightAt.y = p.y;
			repaint();
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		Point p = new Point(e.getX(), e.getY());

		if(mouseRightPressed) {
			translate.x += mouseRightAt.x - p.x;
			translate.y += mouseRightAt.y - p.y;

			mouseRightAt.x = p.x;
			mouseRightAt.y = p.y;
			repaint();
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if(e.getButton() == 1) {
			mouseLeftPressed = true;
			mouseLeftAt = new Point(e.getX(), e.getY());
		}
		if(e.getButton() == 3) {
			mouseRightPressed = true;
			mouseRightAt = new Point(e.getX(), e.getY());
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if(e.getButton() == 1) {
			mouseLeftPressed = false;
		}
		if(e.getButton() == 3) {
			mouseRightPressed = false;
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	private static final long serialVersionUID = 1L;
}
