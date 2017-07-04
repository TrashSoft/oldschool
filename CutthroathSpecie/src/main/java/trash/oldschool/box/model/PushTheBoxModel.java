package trash.oldschool.box.model;

import java.awt.Point;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class PushTheBoxModel {

	public PushTheBoxMap map = new PushTheBoxMap();
	public List<Point> directions = new ArrayList<>();
	public int mapIndex = 1;

	public boolean over = false;
	public double countDown = 0.0;

	public PushTheBoxModel() {
		directions.add(new Point(1, 0));
		directions.add(new Point(0, 1));
		directions.add(new Point(-1, 0));
		directions.add(new Point(0, -1));
	}

	public boolean isOver() {
		return over;
	}

	public void clearOver() {
		over = false;
	}

	public void startCountDown() {
		over = true;
		countDown = 10.0;
	}

	public void reduceCountDown(double delta) {
		countDown -= delta;
	}

	public boolean readyToLeaveLevel() {
		return over && countDown <= 0.0;
	}

	public void restartLevel() {
		over = true;
		startCountDown();
	}

	public void nextLevel() {
		mapIndex++;
		File f = new File(getMapFileName());
		if(!f.exists()) {
			mapIndex = 1;
		}

		over = true;
		startCountDown();
	}

	public String getMapFileName() {
		return "maps/map" + mapIndex + ".txt";
	}

}
