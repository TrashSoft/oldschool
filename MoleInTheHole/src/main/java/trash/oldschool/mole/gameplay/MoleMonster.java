package trash.oldschool.mole.gameplay;

import java.awt.Point;

public class MoleMonster extends MoleMoveable {

	public Point direction;

	public MoleMonster(int x, int y) {
		super(x, y);
	}

	public void rotate() {
		if(direction.x == 1 && direction.y == 0)  {
			direction.x = 0;
			direction.y = 1;
			return;
		}

		if(direction.x == 0 && direction.y == 1)  {
			direction.x = -1;
			direction.y = 0;
			return;
		}

		if(direction.x == -1 && direction.y == 0)  {
			direction.x = 0;
			direction.y = -1;
			return;
		}

		direction.x = 1;
		direction.y = 0;
	}
}
