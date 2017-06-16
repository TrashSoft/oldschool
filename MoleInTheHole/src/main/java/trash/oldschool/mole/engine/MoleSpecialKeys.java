package trash.oldschool.mole.engine;

import trash.oldschool.engine.GameEngineFacade;
import trash.oldschool.engine.intf.GameKeyListener;
import trash.oldschool.mole.model.MoleModel;

public class MoleSpecialKeys implements GameKeyListener {

	@Override
	public void hit(GameEngineFacade facade, char key) {
		MoleModel model = (MoleModel) facade.model();

		if (27 == key) {
			if (model != null) {
				model.restartLevel();
			}
		}

		if ('n' == key || 'N' == key) {
			if (model != null) {
				model.nextLevel();
			}
		}
	}

}
