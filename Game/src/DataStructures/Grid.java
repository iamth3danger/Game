package DataStructures;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import Entity.Entity;

public class Grid {

    private int cellSize;
    private int width;
    private int height;
    private HashMap<Cell, List<Entity>> gridCells = new HashMap<>();

    public Grid(int width, int height, int cellSize) {
        this.width = width;
        this.height = height;
        this.cellSize = cellSize;
        initializeGrid();
    }

    private void initializeGrid() {
        for (int x = 0; x < width; x += cellSize) {
            for (int y = 0; y < height; y += cellSize) {
                gridCells.put(new Cell(x, y), new ArrayList<>());
            }
        }
    }

    // Method to add an entity to the appropriate grid cell
    public void addEntity(Entity entity) {
        int cellX = (int) (entity.getX() / cellSize);
        int cellY = (int) (entity.getY() / cellSize);
        Cell cellPosition = new Cell(cellX, cellY);
        gridCells.get(cellPosition).add(entity);
    }

    // Method to get all entities within the specified cell
    public List<Entity> getEntitiesAt(int x, int y) {
        int cellX = (int) (x / cellSize);
        int cellY = (int) (y / cellSize);
        Cell cellPosition = new Cell(cellX, cellY);
        return gridCells.get(cellPosition);  // Might return null if the cell is empty
    }

    // Helper class to represent a grid cell
    private static class Cell {
        private int x;
        private int y;

        public Cell(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Cell cell = (Cell) o;
            return x == cell.x && y == cell.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }
    }
}
