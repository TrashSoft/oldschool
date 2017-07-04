package trash.oldschool.scifinovel.model;

import java.awt.Point;

public abstract class SciFiNovelMoveable {

	public Point position;
	public Point target;
	public double delta;
	public boolean alive;

	public SciFiNovelMoveable(int x, int y) {
		this.position = new Point(x, y);
		this.target = new Point(x, y);
		this.delta = 0.0;
		this.alive = true;
	}

	public boolean isAllowedToMove() {
		return (delta < 0.00001);
	}

	public double currentX() {
		double x;

		if(isAllowedToMove()) {
			x = position.x;
		} else {
			x = (position.x * delta + target.x * (1.0 - delta));
		}

		return x;
	}

	public double currentY() {
		double y;

		if(isAllowedToMove()) {
			y = position.y;
		} else {
			y = (position.y * delta + target.y * (1.0 - delta));
		}

		return y;
	}

	public void move(int targetX, int targetY) {
		target.x = targetX;
		target.y = targetY;
		delta = 1.0;
	}

	public void reduceDelta(double time) {
		if(!isAllowedToMove()) {
			delta -= time;

			if(isAllowedToMove()) {
				delta = 0.0;
				position.x = target.x;
				position.y = target.y;
			}
		}
	}
}
