package trash.oldschool.box.model;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class PushTheBoxMap {

	private List<PushTheBoxMonster> monsters;
	private List<PushTheBoxModelBox> boxes;
	private List<PushTheBoxPlayer> players;

	private boolean readyToExit;

	private char[][] map;
	private int width;
	private int height;

	public void loadFrom(String filename) {
		boolean startedReading = false;
		boolean finishedReading = false;

		clear();

		List<String> lines = new ArrayList<>();

		try(BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(filename), Charset.forName("UTF-8")))) {

			String line;
			while((line = reader.readLine()) != null) {
				int index = line.indexOf(";;");
				if(index >= 0) {
					line = line.substring(0, index);
				}

				line = line.trim();
				if(line.isEmpty() && startedReading && !finishedReading) {
					finishedReading = true;
				}

				if(!line.isEmpty() && !startedReading) {
					startedReading = true;
				}

				if(startedReading && !finishedReading) {
					width = Math.max(width, line.length());
					height++;
					lines.add(line);
				}
			}

		} catch(IOException ex) {
			throw new RuntimeException("Couldn't load map: " + filename, ex);
		}

		map = new char[width][height];
		for(int y = 0; y < height; y++) {
			String line = lines.get(y);
			for(int x = 0; x < width; x++) {

				char c = (x < line.length() ? line.charAt(x) : ' ');
				if(c == '#') {
					map[x][y] = '#';
				} else {
					map[x][y] = ' ';
				}

				if(c == 'p' || c == 'P') {
					players.add(new PushTheBoxPlayer(x, y));
				}

				if(c >= '0' && c <= '6') {
					int type = c - '0';
					boxes.add(new PushTheBoxModelBox(x, y, type));
				}

				if(c == 'm' || c == 'M') {
					monsters.add(new PushTheBoxMonster(x, y));
				}
			}
		}

		if(players.isEmpty()) {
			throw new RuntimeException("Player position is missing from map!");
		}

		readyToExit = false;
	}

	public void clear() {
		width = 0;
		height = 0;

		monsters = new ArrayList<>();
		boxes = new ArrayList<>();
		players = new ArrayList<>();
		readyToExit = false;
	}

	public char getTile(Point p, Point direction) {
		return getTile(p.x + direction.x, p.y + direction.y);
	}

	public char getTile(int x, int y, Point direction) {
		return getTile(x + direction.x, y + direction.y);
	}

	public char getTile(Point p) {
		return getTile(p.x, p.y);
	}

	public char getTile(int x, int y) {
		if(x < 0 || x >= width) {
			return '#';
		}

		if(y < 0 || y >= height) {
			return '#';
		}
		return map[x][y];
	}

	public List<PushTheBoxMonster> getMonsters() {
		return monsters;
	}

	public void setMonsters(List<PushTheBoxMonster> monsters) {
		this.monsters = monsters;
	}

	public List<PushTheBoxModelBox> getStones() {
		return boxes;
	}

	public void setStones(List<PushTheBoxModelBox> stones) {
		this.boxes = stones;
	}

	public List<PushTheBoxPlayer> getPlayers() {
		return players;
	}

	public void setPlayers(List<PushTheBoxPlayer> players) {
		this.players = players;
	}

	public char[][] getMap() {
		return map;
	}

	public void setMap(char[][] map) {
		this.map = map;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public boolean isReadyToExit() {
		return readyToExit;
	}

	public void setReadyToExit(boolean readyToExit) {
		this.readyToExit = readyToExit;
	}
}
