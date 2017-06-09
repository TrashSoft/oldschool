package trash.oldschool.engine;

import trash.oldschool.logging.Logger;
import trash.oldschool.logging.LoggerFactory;
import trash.oldschool.swing.SwingGameAdapter;

public class GameEngineBuilder {

	public GameEngineBuilder() {
	}

	public GameEngine create(String id, String title, String version, String description) {
		GameDescriptor descriptor = new GameDescriptor();
		GameAdapter adapter = new SwingGameAdapter();

		descriptor.setId(id);
		descriptor.setTitle(title);
		descriptor.setVersion(version);
		descriptor.setDescription(description);

		GameEngine engine = GameEngine.create(descriptor, adapter);

		logger.info("Engine created.\nID: " + id + "\nTitle: " + title + "\nVersion: " + version + "\nDescription: " + description);

		return engine;
	}

	private static final Logger logger = LoggerFactory.createLogger(GameEngineBuilder.class);
}
