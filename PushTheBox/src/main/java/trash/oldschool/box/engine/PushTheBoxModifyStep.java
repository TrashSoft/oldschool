package trash.oldschool.box.engine;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

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

		// move boxes
		List<PushTheBoxModelBox> boxesToRemove = new ArrayList<>();
		for(PushTheBoxModelBox box : map.getBoxes()) {
			moveBox(delta, box, map, boxesToRemove);
		} // end for monster

		if(!boxesToRemove.isEmpty()) {
			map.removeBoxes(boxesToRemove);
		}

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
						for(PushTheBoxModelBox boxModel : map.getBoxes()) {
							if(boxModel.position.x == targetX && boxModel.position.y == targetY) {
								if(boxModel.isAllowedToMove() && summarizedMap.free(boxModel.position, direction)) {
									// pushing box
									boxModel.move(targetX + direction.x, targetY + direction.y);
								} else {
									stopped = true;
								}
								break;
							} else if(boxModel.target.x == targetX && boxModel.target.y == targetY) {
								// TODO I don't know.... if allowed to move, then position should be same as target
								if(boxModel.isAllowedToMove() && summarizedMap.free(boxModel.position, direction)) {
									// pushing box
									boxModel.move(targetX + direction.x, targetY + direction.y);
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

		// check if player is touching with any monsters
		// if yes, player dies
		for(PushTheBoxMonster monster : map.getMonsters()) {
			if(monster.isAllowedToMove()) {
				moveMonster(summarizedMap, monster);
			} else {
				if(monster.alive) {
					monster.reduceDelta(delta);
				}
			} // end is allowed to move

			if(!over) {
				for(PushTheBoxPlayer player : map.getPlayers()) {
					if(areTouching(monster, player)) {
						player.alive = false;
					}
				}
			}
		}

		// check if there is a player alive
		if(!over) {

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

		double boxLeft = m1x - 0.4;
		double boxTop = m1y - 0.4;
		double boxRight = m1x + 1.4;
		double boxBottom = m1y + 1.4;

		double px = m2x + 0.4;
		double py = m2y + 0.4;

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

	private void moveBox(double delta, PushTheBoxModelBox box, PushTheBoxMap map, List<PushTheBoxModelBox> boxesToRemove) {
		if(!box.isAllowedToMove()) {
			box.reduceDelta(delta);

			if(box.isAllowedToMove() && box.merging) {
				boxesToRemove.add(box);
				return;
			}

			if(box.isAllowedToMove() && !box.merging) {
				boolean merging = false;
				for(PushTheBoxModelBox other: map.getBoxes()) {
					if(box != other && box.type == other.type && nextToEachOther(box, other) && !other.merging) {
						other.move(box.position.x, box.position.y);
						other.merging = true;
						merging = true;
					}
				}

				if(merging) {
					box.merging = true;
					box.move(box.position.x, box.position.y);
				}
			}
		}
	}

	private boolean nextToEachOther(PushTheBoxModelBox box1, PushTheBoxModelBox box2) {
		if(box1.position.x == box2.position.x) {
			int diff = box1.position.y - box2.position.y;
			if(-1 <= diff && diff <= 1) {
				return true;
			}
		}

		if(box1.position.y == box2.position.y) {
			int diff = box1.position.x - box2.position.x;
			if(-1 <= diff && diff <= 1) {
				return true;
			}
		}

		return false;
	}
}
