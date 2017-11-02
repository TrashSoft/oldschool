package trash.oldschool.scifinovel.model;

import java.util.Map;
import java.util.TreeMap;

import trash.oldschool.scifinovel.data.starmap.v1.Star;

public class SciFiNovelModel {

	private Map<String, Object> objects = new TreeMap<>();
	private Map<String, Star> stars = new TreeMap<>();

	public SciFiNovelModel() {
	}

	public void addStar(Star star) {
		String id = star.getId();
		stars.put(id, star);
		objects.put(id, star);
	}

	public Iterable<Star> getStars() {
		return stars.values();
	}

}
