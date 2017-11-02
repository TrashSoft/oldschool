package trash.oldschool.scifinovel.data.starmap.v1;

import trash.oldschool.scifinovel.data.common.v1.Color;
import trash.oldschool.scifinovel.data.common.v1.Position;
import trash.oldschool.xml.XmlClass;
import trash.oldschool.xml.XmlField;

@XmlClass("star")
public class Star {

	public Star() {
	}

	public Star(String id, String name, Position position, Position orbitCenter, Position direction, double speed, double size, Color color) {
		this.id = id;
		this.position = position;
		this.orbitCenter = orbitCenter;
		this.direction = direction;
		this.color = color;
		this.name = name;
		this.size = size;
		this.speed = speed;
	}

	@XmlField("id")
	public String getId() {
		return id;
	}

	@XmlField("id")
	public void setId(String id) {
		this.id = id;
	}

	@XmlField("position")
	public Position getPosition() {
		return position;
	}

	@XmlField("position")
	public void setPosition(Position position) {
		this.position = position;
	}

	@XmlField("orbitCenter")
	public Position getOrbitCenter() {
		return orbitCenter;
	}

	@XmlField("orbitCenter")
	public void setOrbitCenter(Position orbitCenter) {
		this.orbitCenter = orbitCenter;
	}

	@XmlField("direction")
	public Position getDirection() {
		return direction;
	}

	@XmlField("direction")
	public void setDirection(Position direction) {
		this.direction = direction;
	}

	@XmlField("speed")
	public double getSpeed() {
		return speed;
	}

	@XmlField("speed")
	public void setSpeed(double speed) {
		this.speed = speed;
	}

	@XmlField("color")
	public Color getColor() {
		return color;
	}

	@XmlField("color")
	public void setColor(Color color) {
		this.color = color;
	}

	@XmlField("name")
	public String getName() {
		return name;
	}

	@XmlField("name")
	public void setName(String name) {
		this.name = name;
	}

	@XmlField("size")
	public double getSize() {
		return size;
	}

	@XmlField("size")
	public void setSize(double size) {
		this.size = size;
	}

	private String id;
	private String name;

	private Color color;
	private double size;

	private Position position;
	private Position orbitCenter;
	private Position direction;
	private double speed;

}
