package trash.oldschool.mole.engine;

import trash.oldschool.engine.GameEngineCallback;
import trash.oldschool.facade.Facade;
import trash.oldschool.mole.gameplay.MoleModel;

public class MoleInitStep implements GameEngineCallback {

	@Override
	public Object call(Facade facade) {
		facade.setModel(new MoleModel());
		return null;
	}

}
