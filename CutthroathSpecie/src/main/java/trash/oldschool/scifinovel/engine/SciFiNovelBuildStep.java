package trash.oldschool.scifinovel.engine;

import trash.oldschool.engine.GameEngineCallback;
import trash.oldschool.engine.GameEngineFacade;
import trash.oldschool.scifinovel.model.SciFiNovelModel;

public class SciFiNovelBuildStep implements GameEngineCallback {

	@Override
	public Object call(GameEngineFacade facade) {
		SciFiNovelModel model = (SciFiNovelModel) facade.model();
		String mapFileName = model.getMapFileName();
		model.map.loadFrom(mapFileName);
		facade.logger().info("Map loaded: " + mapFileName);
		model.clearOver();
		return null;
	}

}
