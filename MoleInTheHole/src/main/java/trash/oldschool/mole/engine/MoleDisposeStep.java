package trash.oldschool.mole.engine;

import trash.oldschool.engine.GameEngineCallback;
import trash.oldschool.engine.GameEngineFacade;
import trash.oldschool.mole.model.MoleModel;

public class MoleDisposeStep implements GameEngineCallback {

	@Override
	public Object call(GameEngineFacade facade) {
		MoleModel model = (MoleModel) facade.model();
		model.map.clear();
		return null;
	}

}
