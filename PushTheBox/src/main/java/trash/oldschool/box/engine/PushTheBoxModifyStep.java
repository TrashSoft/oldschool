package trash.oldschool.box.engine;

import java.awt.Point;

import trash.oldschool.box.engine.helper.PushTheBoxSummarizedMap;
import trash.oldschool.box.model.PushTheBoxMap;
import trash.oldschool.box.model.PushTheBoxModel;
import trash.oldschool.box.model.PushTheBoxModelBox;
import trash.oldschool.box.model.PushTheBoxMonster;
import trash.oldschool.box.model.PushTheBoxMoveable;
import trash.oldschool.box.model.PushTheBoxPlayer;
import trash.oldschool.engine.GameEngineCallback;
import trash.oldschool.engine.GameEngineFacade;
import trash.oldschool.engine.intf.GameControl;

public class PushTheBoxModifyStep implements GameEngineCallback {

	private static final double GAME_SPEED = 5.0; // seconds
	// private static final Random RANDOM = new Random(System.currentTimeMillis());

	@Override
	public Object call(GameEngineFacade facade) {
		PushTheBoxModel model = (PushTheBoxModel) facade.model();
		PushTheBoxMap map = model.map;

		double delta = facade.timer().elapsedTime() * GAME_SPEED;
		if(delta > 0.5) {
			delta = 0.5;
		}

		boolean over = model.isOver();
		if(over) {
			model.reduceCountDown(delta);
		}

		if(model.readyToLeaveLevel()) {
			facade.requestRebuild();
		}

		// first we summorize everything we have into a small
		// map created specially for the monsters
		PushTheBoxSummarizedMap summarizedMap = new PushTheBoxSummarizedMap(map);

		// move monsters
		for(PushTheBoxMonster monster : map.getMonsters()) {
			if(monster.isAllowedToMove()) {
				moveMonster(summarizedMap, monster);
			} else {
				if(monster.alive) {
					monster.reduceDelta(delta);
				}
			} // end is allowed to move
		} // end for monster

		// move stones
		for(PushTheBoxModelBox stone : map.getStones()) {
			stone.reduceDelta(delta);
		} // end for monster

		// move players
		for(PushTheBoxPlayer player : map.getPlayers()) {
			if(!player.alive) {
				continue;
			}

			if(player.isAllowedToMove()) {

				if(over) {
					continue;
				}

				if(map.isReadyToExit()) {
					model.nextLevel();
				}

				GameControl control = facade.control();
				if(control.isLeftOn() || control.isDownOn() || control.isRightOn() || control.isUpOn()) {

					Point direction;
					if(control.isRightOn()) {
						direction = model.directions.get(0);
						player.animationDirection = PushTheBoxPlayer.ANIMATION_DIRECTION_RIGHT;
					} else if(control.isDownOn()) {
						direction = model.directions.get(1);
						player.animationDirection = PushTheBoxPlayer.ANIMATION_DIRECTION_DOWN;
					} else if(control.isLeftOn()) {
						direction = model.directions.get(2);
						player.animationDirection = PushTheBoxPlayer.ANIMATION_DIRECTION_LEFT;
					} else {
						direction = model.directions.get(3);
						player.animationDirection = PushTheBoxPlayer.ANIMATION_DIRECTION_UP;
					}

					Point p = player.position;
					int targetX = p.x + direction.x;
					int targetY = p.y + direction.y;
					boolean stopped = false;

					char tile = map.getTile(p, direction);

					if(tile == '#') {
						stopped = true;
					}

					if(!stopped) {
						for(PushTheBoxModelBox stone : map.getStones()) {
							if(stone.position.x == targetX && stone.position.y == targetY) {
								if(stone.isAllowedToMove() && summarizedMap.free(stone.position, direction)) {
									// pushing stone
									stone.move(targetX + direction.x, targetY + direction.y);
								} else {
									stopped = true;
								}
								break;
							} else if(stone.target.x == targetX && stone.target.y == targetY) {
								// TODO I don't know.... if allowed to move, then position should be same as target
								if(stone.isAllowedToMove() && summarizedMap.free(stone.position, direction)) {
									// pushing stone
									stone.move(targetX + direction.x, targetY + direction.y);
								} else {
									stopped = true;
								}
								break;
							}
						}
					}

					if(!stopped) {
						player.move(targetX, targetY);
					} // end if stopped

				}


			} else {
				player.reduceDelta(delta);
			} // end is allowed to move
		} // end for monster

		// check if there is a player alive
		if(!over) {

			// check if player is touching with any monsters
			// if yes, player dies
			for(PushTheBoxMonster monster : map.getMonsters()) {
				for(PushTheBoxPlayer player : map.getPlayers()) {
					if(areTouching(monster, player)) {
						player.alive = false;
					}
				}
			}
			boolean isAnyPlayerAlive = false;
			for(PushTheBoxPlayer player: map.getPlayers()) {
				if(player.alive) {
					isAnyPlayerAlive = true;
				}
			}

			if(!isAnyPlayerAlive) {
				model.restartLevel();
			}
		}

		return null;
	}

	boolean areTouching(PushTheBoxMoveable m1, PushTheBoxMoveable m2) {
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
	private void moveMonster(PushTheBoxSummarizedMap map, PushTheBoxMonster monster) {
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
