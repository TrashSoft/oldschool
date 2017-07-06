package trash.oldschool.pet.engine;

import trash.oldschool.engine.GameEngineFacade;
import trash.oldschool.engine.intf.GameKeyListener;
import trash.oldschool.pet.model.WebPetModel;

public class WebPetSpecialKeys implements GameKeyListener {

	@Override
	public void hit(GameEngineFacade facade, char key) {
		WebPetModel model = (WebPetModel) facade.model();

		if (27 == key) {
			if (model != null) {
				// ...
			}
		}

		if ('n' == key || 'N' == key) {
			if (model != null) {
				// ...
			}
		}
	}

}
