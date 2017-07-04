package trash.oldschool.box.engine;

import trash.oldschool.box.model.PushTheBoxModel;
import trash.oldschool.engine.GameEngineCallback;
import trash.oldschool.engine.GameEngineFacade;

public class PushTheBoxBuildStep implements GameEngineCallback {

	@Override
	public Object call(GameEngineFacade facade) {
		PushTheBoxModel model = (PushTheBoxModel) facade.model();
		String mapFileName = model.getMapFileName();
		model.map.loadFrom(mapFileName);
		facade.logger().info("Map loaded: " + mapFileName);
		model.clearOver();
		return null;
	}

}
