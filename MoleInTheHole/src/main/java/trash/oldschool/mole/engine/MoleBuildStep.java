package trash.oldschool.mole.engine;

import trash.oldschool.engine.GameEngineCallback;
import trash.oldschool.engine.GameEngineFacade;
import trash.oldschool.mole.model.MoleModel;

public class MoleBuildStep implements GameEngineCallback {

	private static final String MAP_FILENAME = "maps/test.txt";

	@Override
	public Object call(GameEngineFacade facade) {
		MoleModel model = (MoleModel) facade.model();
		model.map.loadFrom(MAP_FILENAME);
		facade.logger().info("Map loaded: " + MAP_FILENAME);
		return null;
	}

}
