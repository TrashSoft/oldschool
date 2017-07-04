package trash.oldschool.box.engine;

import trash.oldschool.box.model.PushTheBoxModel;
import trash.oldschool.engine.GameEngineFacade;
import trash.oldschool.engine.intf.GameKeyListener;

public class PushTheBoxSpecialKeys implements GameKeyListener {

	@Override
	public void hit(GameEngineFacade facade, char key) {
		PushTheBoxModel model = (PushTheBoxModel) facade.model();

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
