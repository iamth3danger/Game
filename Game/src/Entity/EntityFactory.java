package Entity;
import Creature.FlyingEye;
import Creature.Goblin;
import Creature.Mushroom;
import Creature.Reaper;
import Creature.Skeleton;
import Living.Door;
import Living.FireBall;
import Tile.GreenTile;
import Tile.Platform;
import Tile.RedTile;
import Tile.Spike;
import Tile.Tile;

public class EntityFactory {

    public static Entity createEntity(EntityType type, int x, int y, int[] blockDimensions) {
        switch (type) {
            case PLATFORM:
                return new Platform(x, y);
            case SPIKE:
                return new Spike(x, y);
            case TILE:
                return new Tile(x, y);
            case GREENTILE:
                return new GreenTile(x, y);
            case REDTILE:
                return new RedTile(x, y);
            case MUSHROOM:
                return new Mushroom(x, y, blockDimensions);
            case REAPER:
                return new Reaper(x, y, blockDimensions);
            case GOBLIN:
                return new Goblin(x, y, blockDimensions);
            case FLYINGEYE:
                return new FlyingEye(x, y, blockDimensions);
            case SKELETON:
                return new Skeleton(x, y, blockDimensions);
            case DOOR:
            	return new Door(x, y);
            case FIREBALL:
            	return new FireBall(x, y);
            default:
                throw new IllegalArgumentException("Invalid entity type");
        }
    }
    
    
    public static Entity createEntity(EntityType type, int x, int y) {
        switch (type) {
            case PLATFORM:
                return new Platform(x, y);
            case SPIKE:
                return new Spike(x, y);
            case TILE:
                return new Tile(x, y);
            case GREENTILE:
                return new GreenTile(x, y);
            case REDTILE:
                return new RedTile(x, y);
            case MUSHROOM:
                return new Mushroom(x, y, null);
            case REAPER:
                return new Reaper(x, y, null);
            case GOBLIN:
                return new Goblin(x, y, null);
            case FLYINGEYE:
                return new FlyingEye(x, y, null);
            case SKELETON:
                return new Skeleton(x, y, null);
            case DOOR:
            	return new Door(x, y);
            case FIREBALL:
            	return new FireBall(x, y);
            default:
                throw new IllegalArgumentException("Invalid entity type");
        }
    }
    
    
    
    
    public static Entity createEntity(String symbol, int x, int y, int[] blockDimensions) {
        switch (symbol) {
            case "#":
                return new Platform(x, y);
            case "$":
                return new Spike(x, y);
            case "*":
                return new Tile(x, y);
            case "%":
                return new GreenTile(x, y);
            case "^":
                return new RedTile(x, y);
            case "M":
                return new Mushroom(x, y, blockDimensions);
            case "R":
                return new Reaper(x, y, blockDimensions);
            case "G":
                return new Goblin(x, y, blockDimensions);
            case "E":
                return new FlyingEye(x, y, null);
            case "S":
                return new Skeleton(x, y, blockDimensions);
            case "D":
            	return new Door(x, y);
            default:
                throw new IllegalArgumentException("Invalid entity symbol");
        }
    }
}