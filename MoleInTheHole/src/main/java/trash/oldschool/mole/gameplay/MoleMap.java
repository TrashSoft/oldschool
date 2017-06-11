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
	private Point playerPosition;
	private Point exitPosition;
	private char[][] map;

	public void loadFrom(String filename) {
		int width = 0;
		int height = 0;
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
		for(int y = 0; y < width; y++) {
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
				} else {
					map[x][y] = ' ';
				}
				
				if(c == 'p' || c == 'P') {
					playerPosition = new Point(x, y);
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
		
		if(playerPosition == null) {
			throw new RuntimeException("Player position is missing from map!");
		}
	}

	public void clear() {
		monsters = new ArrayList<>();
		stones = new ArrayList<>();
		playerPosition = null;
		exitPosition = null;
	}
}
