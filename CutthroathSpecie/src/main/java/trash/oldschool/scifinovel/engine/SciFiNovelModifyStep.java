package trash.oldschool.scifinovel.engine;

import trash.oldschool.engine.GameEngineCallback;
import trash.oldschool.engine.GameEngineFacade;
import trash.oldschool.scifinovel.data.common.v1.Position;
import trash.oldschool.scifinovel.data.starmap.v1.Star;
import trash.oldschool.scifinovel.model.SciFiNovelModel;

public class SciFiNovelModifyStep implements GameEngineCallback {

	private static final double GAME_SPEED = 5.0; // seconds
	// private static final Random RANDOM = new Random(System.currentTimeMillis());

	@Override
	public Object call(GameEngineFacade facade) {
		SciFiNovelModel model = (SciFiNovelModel) facade.model();

		for(Star star : model.getStars()) {
			Position p = star.getPosition();
			Position d = star.getDirection();
			double sp = star.getSpeed();
			p.setX(p.getX() + d.getX() * sp);
			p.setY(p.getY() + d.getY() * sp);
			p.setZ(p.getZ() + d.getZ() * sp);
		}

		return null;
	}
}
