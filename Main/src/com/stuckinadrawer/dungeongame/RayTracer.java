package com.stuckinadrawer.dungeongame;

import com.stuckinadrawer.dungeongame.tiles.Tile;

public class RayTracer {
    private Level level;

    public RayTracer(Level level){

        this.level = level;
    }

    public void calculatePlayerFOV(){
        initAsNotVisible();
        traceRay(new Position(0, 0), new Position(20, 10));

    }

    private void initAsNotVisible() {
        Tile[][] levelData = level.getLevelData();
        for(int x = 0; x < levelData.length; x++){
            for(int y = 0; y < levelData[x].length; y++){
                Tile t = levelData[x][y];
                t.inLOS = false;
            }
        }

    }

    private void traceRay(Position start, Position end){
        Tile[][] levelData = level.getLevelData();

        int x0 = start.getX();
        int y0 = start.getY();

        int x1 = end.getX();
        int y1 = end.getY();

        int dx = x1 - x0;
        int dy = y1 - y0;


        if(dx >= dy){
            //X is fast
            int fast = dx;
            int slow = dy;

            double error = dx/2;
            levelData[x0][y0].inLOS = true;
            levelData[x0][y0].hasSeen = true;
            while(x0 < x1){
                x0++;
                error = error - dy;
                if(error < 0){
                    y0++;
                    error = error + dx;
                }
                levelData[x0][y0].inLOS = true;
                levelData[x0][y0].hasSeen = true;
            }
            levelData[x1][y1].inLOS = true;
            levelData[x1][y1].hasSeen = true;


        }else{
            //Y is fast
            double error = dy/2;
        }



    }


}
