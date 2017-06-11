package trash.oldschool.mole.engine;

import java.awt.Point;

import trash.oldschool.engine.GameControl;
import trash.oldschool.engine.GameEngineCallback;
import trash.oldschool.facade.Facade;
import trash.oldschool.mole.model.MoleMap;
import trash.oldschool.mole.model.MoleModel;
import trash.oldschool.mole.model.MoleMonster;
import trash.oldschool.mole.model.MoleMoveable;
import trash.oldschool.mole.model.MolePlayer;
import trash.oldschool.mole.model.MoleStone;

public class MoleModifyStep implements GameEngineCallback {

	private static final double GAME_SPEED = 0.2; // seconds

	@Override
	public Object call(Facade facade) {
		MoleModel model = (MoleModel) facade.model();
		MoleMap map = model.map;

		double delta = facade.timer().elapsedTime() * GAME_SPEED;

		// move monsters
		for(MoleMonster monster : map.getMonsters()) {
			if(monster.isAllowedToMove()) {
				Point p = monster.position;
				Point d = monster.direction;
				int targetX = p.x + d.x;
				int targetY = p.y + d.y;
				boolean stopped = false;

				char tile = map.getTile(p, d);
				if(tile != ' ') {
					stopped = true;
				}

				if(!stopped) {
					for(MoleStone stone : map.getStones()) {
						if(stone.position.x == targetX && stone.position.y == targetY) {
							stopped = true;
							break;
						}
						if(stone.target.x == targetX && stone.target.y == targetY) {
							stopped = true;
							break;
						}
					}
				}

				if(stopped) {
					monster.rotate();
				} else {
					monster.move(targetX, targetY);
				} // end if stopped
			} else {
				monster.reduceDelta(delta);
			} // end is allowed to move
		} // end for monster

		// move stones
		for(MoleStone stone : map.getStones()) {
			if(stone.isAllowedToMove()) {
				boolean stopped = true;

				// TODO stone movement

				if(!stopped) {
					// stone.move(targetX, targetY);
				} // end if stopped
			} else {
				stone.reduceDelta(delta);
			} // end is allowed to move
		} // end for monster

		// move players
		for(MolePlayer player : map.getPlayers()) {
			if(player.isAllowedToMove()) {

				GameControl control = facade.control();
				if(control.isLeftOn() || control.isDownOn() || control.isRightOn() || control.isUpOn()) {

					Point direction;
					if(control.isRightOn()) {
						direction = model.directions.get(0);
					} else if(control.isDownOn()) {
						direction = model.directions.get(1);
					} else if(control.isLeftOn()) {
						direction = model.directions.get(2);
					} else {
						direction = model.directions.get(3);
					}

					Point p = player.position;
					int targetX = p.x + direction.x;
					int targetY = p.y + direction.y;
					boolean stopped = false;

					char tile = map.getTile(p, direction);

					Point exitPosition = map.getExitPosition();
					int exitX = map.isReadyToExit() ? exitPosition.x : -1;
					int exitY = map.isReadyToExit() ? exitPosition.y : -1;

					if(tile == '#' && targetX != exitX && targetY != exitY) {
						stopped = true;
					}

					if(!stopped) {
						for(MoleStone stone : map.getStones()) {
							if(stone.position.x == targetX && stone.position.y == targetY) {
								stopped = true;
								break;
							}
							if(stone.target.x == targetX && stone.target.y == targetY) {
								stopped = true;
								break;
							}
						}
					}

					if(!stopped) {
						player.move(targetX, targetY);
						map.stepsOn(player, targetX, targetY);
					} // end if stopped

				}


			} else {
				player.reduceDelta(delta);
			} // end is allowed to move
		} // end for monster

		// check if player is touching with any monsters
		// if yes, player dies
		for(MoleMonster monster : map.getMonsters()) {
			for(MolePlayer player : map.getPlayers()) {
				if(areTouching(monster, player)) {
					player.alive = false;
				}
			}
		}



		return null;
	}

	private boolean areTouching(MoleMoveable m1, MoleMoveable m2) {
		double m1x = m1.currentX();
		double m1y = m1.currentY();
		double m2x = m2.currentX();
		double m2y = m2.currentY();

		boolean touching = false;

		double boxLeft = m1x - 0.5;
		double boxTop = m1y - 0.5;
		double boxRight = m1x + 1.5;
		double boxBottom = m1y + 1.5;

		if(m2x >= boxLeft && m2x <= boxRight && m2y >= boxTop && m2x <= boxBottom) {
			touching = true;
		}

		return touching;
	}

}
