package trash.oldschool.mole.gameplay;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class MoleMap {
	
	private List<MoleMonster> monsters;
	private List<MoleStone> stones;
	private List<MolePlayer> players;
	private Point exitPosition;
	private boolean readyToExit;
	private int numberOfDiamonds;
	
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
				if(Character.isWhitespace(c)) {
					map[x][y] = ' ';
				} else if(c == '#' || c == 'x' || c == 'X') {
					map[x][y] = '#';
					if(c != '#') {
						exitPosition = new Point(x, y);
					}
				} else if(c == '*' || c == '.') {
					map[x][y] = c;
					if(c == '*') {
						numberOfDiamonds++;
					}
				} else {
					map[x][y] = ' ';
				}
				
				if(c == 'p' || c == 'P') {
					players.add(new MolePlayer(x, y));
				}
				
				if(c == 'o' || c == 'O') {
					stones.add(new MoleStone(x, y));
				}
				
				if(c == 'm' || c == 'M') {
					monsters.add(new MoleMonster(x, y));
				}
			}
		}
		
		if(exitPosition == null) {
			throw new RuntimeException("Exit position is missing from map!");
		}
		
		if(players.isEmpty()) {
			throw new RuntimeException("Player position is missing from map!");
		}
		
		readyToExit = (numberOfDiamonds == 0);
	}

	public void clear() {
		width = 0;
		height = 0;
		
		monsters = new ArrayList<>();
		stones = new ArrayList<>();
		players = new ArrayList<>();
		exitPosition = null;
		readyToExit = false;
		numberOfDiamonds = 0;
	}

	public List<MoleMonster> getMonsters() {
		return monsters;
	}

	public void setMonsters(List<MoleMonster> monsters) {
		this.monsters = monsters;
	}

	public List<MoleStone> getStones() {
		return stones;
	}

	public void setStones(List<MoleStone> stones) {
		this.stones = stones;
	}

	public List<MolePlayer> getPlayers() {
		return players;
	}

	public void setPlayers(List<MolePlayer> players) {
		this.players = players;
	}

	public Point getExitPosition() {
		return exitPosition;
	}

	public void setExitPosition(Point exitPosition) {
		this.exitPosition = exitPosition;
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

	public int getNumberOfDiamonds() {
		return numberOfDiamonds;
	}

	public void setNumberOfDiamonds(int numberOfDiamonds) {
		this.numberOfDiamonds = numberOfDiamonds;
	}
	
	
}
