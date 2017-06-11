package trash.oldschool.mole.model;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class MoleModel {

	public MoleMap map = new MoleMap();
	public List<Point> directions = new ArrayList<>();

	public MoleModel() {
		directions.add(new Point(1, 0));
		directions.add(new Point(0, 1));
		directions.add(new Point(-1, 0));
		directions.add(new Point(0, -1));
	}
}
