package trash.oldschool.mole.engine;

import java.awt.Point;
import java.util.Random;

import trash.oldschool.engine.GameEngineCallback;
import trash.oldschool.engine.GameEngineFacade;
import trash.oldschool.engine.intf.GameControl;
import trash.oldschool.mole.engine.helper.MoleSummarizedMap;
import trash.oldschool.mole.model.MoleMap;
import trash.oldschool.mole.model.MoleModel;
import trash.oldschool.mole.model.MoleMonster;
import trash.oldschool.mole.model.MoleMoveable;
import trash.oldschool.mole.model.MolePlayer;
import trash.oldschool.mole.model.MoleStone;

public class MoleModifyStep implements GameEngineCallback {

	private static final double GAME_SPEED = 5.0; // seconds
	private static final Random RANDOM = new Random(System.currentTimeMillis());

	@Override
	public Object call(GameEngineFacade facade) {
		MoleModel model = (MoleModel) facade.model();
		MoleMap map = model.map;

		double delta = facade.timer().elapsedTime() * GAME_SPEED;

		// first we summorize everything we have into a small
		// map created specially for the monsters
		MoleSummarizedMap summarizedMap = new MoleSummarizedMap(map);

		// move monsters
		for(MoleMonster monster : map.getMonsters()) {
			if(monster.isAllowedToMove()) {
				moveMonster(summarizedMap, monster);
			} else {
				if(monster.alive)
					monster.reduceDelta(delta);
			} // end is allowed to move
		} // end for monster

		// move stones
		for(MoleStone stone : map.getStones()) {
			if(stone.isAllowedToMove()) {
				boolean stopped = true;

				boolean allowedToFallLeft = true;
				boolean allowedToFallDown = true;
				boolean allowedToFallRight = true;

				char tileDown = map.getTile(stone.position.x, stone.position.y + 1);
				if(tileDown == '#' || tileDown == '.') {
					allowedToFallLeft = false;
					allowedToFallDown = false;
					allowedToFallRight = false;
				} else if(tileDown == '*') {
					allowedToFallDown = false;
				}

				char tileLeft = map.getTile(stone.position.x - 1, stone.position.y);
				if(tileLeft != ' ') {
					allowedToFallLeft = false;
				}

				char tileDownLeft = map.getTile(stone.position.x - 1, stone.position.y + 1);
				if(tileDownLeft != ' ') {
					allowedToFallLeft = false;
				}

				char tileRight = map.getTile(stone.position.x + 1, stone.position.y);
				if(tileRight != ' ') {
					allowedToFallRight = false;
				}

				char tileDownRight = map.getTile(stone.position.x + 1, stone.position.y + 1);
				if(tileDownRight != ' ') {
					allowedToFallRight = false;
				}

				for(MoleStone s2 : map.getStones()) {
					if(stone == s2) {
						continue;
					}

					if(s2.position.x == stone.position.x && s2.position.y == stone.position.y + 1) {
						allowedToFallDown = false;
					}

					if(s2.position.x == stone.position.x - 1 && (s2.position.y == stone.position.y || s2.position.y == stone.position.y + 1)) {
						allowedToFallLeft = false;
					}

					if(s2.position.x == stone.position.x + 1 && (s2.position.y == stone.position.y || s2.position.y == stone.position.y + 1)) {
						allowedToFallRight = false;
					}

					if(s2.target.x == stone.position.x && s2.target.y == stone.position.y + 1) {
						allowedToFallDown = false;
					}

					if(s2.target.x == stone.position.x - 1 && (s2.target.y == stone.position.y || s2.target.y == stone.position.y + 1)) {
						allowedToFallLeft = false;
					}

					if(s2.target.x == stone.position.x + 1 && (s2.target.y == stone.position.y || s2.target.y == stone.position.y + 1)) {
						allowedToFallRight = false;
					}
				}

				for(MolePlayer player : map.getPlayers()) {
					if(player.position.x == stone.position.x && player.position.y == stone.position.y + 1) {
						allowedToFallLeft = false;
						allowedToFallDown = false;
						allowedToFallRight = false;
					}

					if(player.position.x == stone.position.x - 1 && (player.position.y == stone.position.y || player.position.y == stone.position.y + 1)) {
						allowedToFallLeft = false;
					}

					if(player.position.x == stone.position.x + 1 && (player.position.y == stone.position.y || player.position.y == stone.position.y + 1)) {
						allowedToFallRight = false;
					}

					if(player.target.x == stone.position.x && player.target.y == stone.position.y + 1) {
						allowedToFallLeft = false;
						allowedToFallDown = false;
						allowedToFallRight = false;
					}

					if(player.target.x == stone.position.x - 1 && (player.target.y == stone.position.y || player.target.y == stone.position.y + 1)) {
						allowedToFallLeft = false;
					}

					if(player.target.x == stone.position.x + 1 && (player.target.y == stone.position.y || player.target.y == stone.position.y + 1)) {
						allowedToFallRight = false;
					}
				}

				if(allowedToFallDown) {

					allowedToFallLeft = false;
					allowedToFallRight = false;
					stone.move(stone.position.x, stone.position.y + 1, MoleStoneFall.DOWN_FALL);

				} else if(allowedToFallLeft && allowedToFallRight) {
					if(RANDOM.nextBoolean()) {
						allowedToFallLeft = false;
					} else {
						allowedToFallRight = false;
					}
				}

				if(allowedToFallLeft) {

					stone.move(stone.position.x - 1, stone.position.y + 1, MoleStoneFall.SIDE_FALL);

				} else if(allowedToFallRight) {

					stone.move(stone.position.x + 1, stone.position.y + 1, MoleStoneFall.SIDE_FALL);

				}

				if(!stopped) {
					// stone.move(targetX, targetY);
				} // end if stopped
			} else {
				stone.reduceDelta(delta);
			} // end is allowed to move
		} // end for monster

		// move players
		for(MolePlayer player : map.getPlayers()) {
			if(!player.alive)
				continue;

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

					if(tile == '#' && (targetX != exitX || targetY != exitY)) {
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

	boolean areTouching(MoleMoveable m1, MoleMoveable m2) {
		double m1x = m1.currentX();
		double m1y = m1.currentY();
		double m2x = m2.currentX();
		double m2y = m2.currentY();

		boolean touching = false;

		double boxLeft = m1x - 0.5;
		double boxTop = m1y - 0.5;
		double boxRight = m1x + 1.5;
		double boxBottom = m1y + 1.5;

		double px = m2x + 0.5;
		double py = m2y + 0.5;

		if(px >= boxLeft && px <= boxRight && py >= boxTop && py <= boxBottom) {
			touching = true;
		}

		return touching;
	}

	/**
	 * Monster algorithm: always follow the wall on the left.
	 */
	private void moveMonster(MoleSummarizedMap map, MoleMonster monster) {
		Point p = monster.position;
		Point d = monster.direction;

		// if there are no walls around the monster; just go forward!
		if(!map.wallAround(p, null)) {
			monster.move(p.x + d.x, p.y + d.y);
			return;
		}


		// if there are walls around, then stick to them
		d = monster.rotateLeft();
		for(int i = 0; i < 4; i++) {
			if(map.free(p, d) && map.wallAround(p, d)) {
				monster.moveForward();
				return;
			} else {
				d = monster.rotateRight();
			}
		}

		// if no better, just look for a free passage
		for(int i = 0; i < 4; i++) {
			if(map.free(p, d)) {
				monster.moveForward();
				return;
			} else {
				d = monster.rotateRight();
			}
		}
	}

}
