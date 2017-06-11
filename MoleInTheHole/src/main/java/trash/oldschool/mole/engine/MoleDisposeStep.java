package trash.oldschool.mole.engine;

import trash.oldschool.engine.GameEngineCallback;
import trash.oldschool.facade.Facade;
import trash.oldschool.mole.model.MoleModel;

public class MoleDisposeStep implements GameEngineCallback {

	@Override
	public Object call(Facade facade) {
		MoleModel model = (MoleModel) facade.model();
		model.map.clear();
		return null;
	}

}
