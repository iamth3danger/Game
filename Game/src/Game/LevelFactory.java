package Game;

public class LevelFactory {
	
	public static String levelMaker(CurrentLevel currentLevel) {
		switch(currentLevel) {
		case LEVEL1:
			return "level9.txt";
		case BOSSLEVEL:
			return "level7.txt";
		default:
			throw new IllegalArgumentException("Invalid entity type");
		}
	}
}
