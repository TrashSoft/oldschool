package trash.oldschool.mole.engine;

import trash.oldschool.engine.GameEngineCallback;
import trash.oldschool.engine.GameEngineFacade;
import trash.oldschool.mole.model.MoleModel;

public class MoleBuildStep implements GameEngineCallback {

	@Override
	public Object call(GameEngineFacade facade) {
		MoleModel model = (MoleModel) facade.model();
		String mapFileName = model.getMapFileName();
		model.map.loadFrom(mapFileName);
		facade.logger().info("Map loaded: " + mapFileName);
		model.clearOver();
		return null;
	}

}
