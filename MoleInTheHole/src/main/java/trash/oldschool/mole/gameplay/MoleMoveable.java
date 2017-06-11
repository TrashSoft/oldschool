package trash.oldschool.mole.gameplay;

import java.awt.Point;

public abstract class MoleMoveable {

	public Point position;
	public Point target;
	public double delta;
	public boolean alive;

	public MoleMoveable(int x, int y) {
		this.position = new Point(x, y);
		this.target = new Point(x, y);
		this.delta = 0.0;
		this.alive = true;
	}

	public boolean isAllowedToMove() {
		return (delta < 0.00001);
	}

	public int currentX(int tileWidth) {
		double x;

		if(isAllowedToMove()) {
			x = position.x * tileWidth;
		} else {
			x = (position.x * (1.0 - delta) + target.x * delta) * tileWidth;
		}

		return (int) x;
	}

	public int currentY(int tileHeight) {
		double y;

		if(isAllowedToMove()) {
			y = position.y * tileHeight;
		} else {
			y = (position.y * (1.0 - delta) + target.y * delta) * tileHeight;
		}

		return (int)y;
	}
}
