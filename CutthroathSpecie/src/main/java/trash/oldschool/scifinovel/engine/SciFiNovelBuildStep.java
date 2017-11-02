package trash.oldschool.scifinovel.engine;

import trash.oldschool.engine.GameEngineCallback;
import trash.oldschool.engine.GameEngineFacade;
import trash.oldschool.scifinovel.data.common.v1.Color;
import trash.oldschool.scifinovel.data.common.v1.Position;
import trash.oldschool.scifinovel.data.starmap.v1.Star;
import trash.oldschool.scifinovel.model.SciFiNovelModel;

public class SciFiNovelBuildStep implements GameEngineCallback {

	@Override
	public Object call(GameEngineFacade facade) {
		SciFiNovelModel model = (SciFiNovelModel) facade.model();

		Color color = new Color(255, 255, 120);
		Position position = new Position(100, 0, 0);
		Position direction = new Position(0, 100, 0);
		Position orbitCenter = new Position(0, 0, 0);
		Star star1 = new Star("star-1", "Star-1", position, orbitCenter, direction, 0.01, 20.0, color);
		model.addStar(star1);

		return null;
	}

}
