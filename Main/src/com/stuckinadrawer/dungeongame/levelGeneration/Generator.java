package com.stuckinadrawer.dungeongame.levelGeneration;

public abstract class Generator {

    static TileEnum[][] level;

    int levelWidth;
    int levelHeight;

    public Generator(int levelWidth, int levelHeight){
        this.levelWidth = levelWidth;
        this.levelHeight = levelHeight;
    }

    public abstract TileEnum[][] generate();

    public void initializeEmptyLevel() {
        level = new TileEnum[levelWidth][levelHeight];

        for (int x = 0; x < levelWidth; x++) {
            for (int y = 0; y < levelHeight; y++) {
                level[x][y] = TileEnum.EMPTY;
            }
        }
    }

    void buildWalls() {
        for (int x = 0; x < levelWidth; x++) {
            for (int y = 0; y < levelHeight; y++) {
                if (level[x][y] == TileEnum.ROOM || level[x][y] == TileEnum.CORRIDOR) {
                    for (int xx = x - 1; xx <= x + 1; xx++) {
                        for (int yy = y - 1; yy <= y + 1; yy++) {
                            if (isInLevelBounds(xx, yy) && level[xx][yy] == TileEnum.EMPTY) {
                                level[xx][yy] = TileEnum.WALL;
                            }
                        }
                    }
                }
            }
        }
    }

    boolean isInLevelBounds(int x, int y){
        return !(x < 0 || y < 0 || x >= levelWidth || y >= levelHeight );

    }

}
