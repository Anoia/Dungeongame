package com.stuckinadrawer.dungeongame.util;

import com.stuckinadrawer.dungeongame.Level;
import com.stuckinadrawer.dungeongame.tiles.Tile;

import java.util.HashSet;

public class RayTracer {
    private Level level;

    public RayTracer(Level level){

        this.level = level;
    }

    public void calculatePlayerFOV(){
        initAsNotVisible();
        int viewDistance = level.getPlayer().viewDistance;

        Position playerPos = level.getPlayer().getPosition();

        useRayTracing(playerPos, viewDistance);



    }

    private void useRayTracing(Position start, int range) {

        int dx = -range;
        int dy = -range;
        while(dx<range){
            dx++;
            Position end = new Position(start.getX()+dx, start.getY()+dy);
            castRay(start, end, range, true);
        }
        while(dy<range){
            dy++;
            Position end = new Position(start.getX()+dx, start.getY()+dy);
            castRay(start, end, range, true);
        }
        while(dx > -range){
            dx--;
            Position end = new Position(start.getX()+dx, start.getY()+dy);
            castRay(start, end, range, true);
        }
        while(dy > -range){
            dy--;
            Position end = new Position(start.getX()+dx, start.getY()+dy);
            castRay(start, end, range, true);
        }

    }

    public HashSet<Position> findCircle(Position start, int radius){
        int x0 = start.getX();
        int y0 = start.getY();
        HashSet<Position> tilesInCircle = new HashSet<Position>();

        int f = 1 - radius;
        int ddF_x = 0;
        int ddF_y = -2 * radius;
        int x = 0;
        int y = radius;

        tilesInCircle.add(new Position(x0, y0 + radius));
        tilesInCircle.add(new Position(x0, y0 - radius));
        tilesInCircle.add(new Position(x0 + radius, y0));
        tilesInCircle.add(new Position(x0 - radius, y0));

        while(x < y)
        {
            if(f >= 0)
            {
                y--;
                ddF_y += 2;
                f += ddF_y;
            }
            x++;
            ddF_x += 2;
            f += ddF_x + 1;

            tilesInCircle.add(new Position(x0 + x, y0 + y));
            tilesInCircle.add(new Position(x0 - x, y0 + y));
            tilesInCircle.add(new Position(x0 + x, y0 - y));
            tilesInCircle.add(new Position(x0 - x, y0 - y));
            tilesInCircle.add(new Position(x0 + y, y0 + x));
            tilesInCircle.add(new Position(x0 - y, y0 + x));
            tilesInCircle.add(new Position(x0 + y, y0 - x));
            tilesInCircle.add(new Position(x0 - y, y0 - x));
        }


        return tilesInCircle;
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

    public boolean castRay(Position start, Position end, int range, boolean markInMap){

        int x, y, t, dx, dy, incrementX, incrementY, pdx, pdy, ddx, ddy, es, el, err;

        /* Entfernung in beide Dimensionen berechnen */
        dx = end.getX() - start.getX();
        dy = end.getY() - start.getY();

        /* Vorzeichen des Inkrements bestimmen */
        incrementX = signum(dx);
        incrementY = signum(dy);

        if(dx < 0) dx = -dx;
        if(dy < 0) dy = -dy;

        /* Feststellen, welche Entfernung größer ist*/
        if(dx>dy){
             /* x ist schnelle Richtung */
            pdx=incrementX; pdy=0;          /* pd. ist Parallelschritt */
            ddx=incrementX; ddy=incrementY; /* dd. ist Diagonalschritt */
            es =dy;   el =dx;   /* Fehlerschritte schnell, langsam */
        }else{
            /* y ist schnelle Richtung */
            pdx=0;    pdy=incrementY; /* pd. ist Parallelschritt */
            ddx=incrementX; ddy=incrementY; /* dd. ist Diagonalschritt */
            es =dx;   el =dy;   /* Fehlerschritte schnell, langsam */
        }

        /* Initialisierungen vor Schleifenbeginn */
        x = start.getX();
        y = start.getY();
        err = el/2;
        setPixel(x, y, markInMap);

        /* Pixel berechnen */
        for(t=0; t < el; t++){ /* t zaehlt die Pixel, el ist auch Anzahl */
            /* Aktualisierung Fehlerterm */
            err -= es;
            if(err<0){
                /*Fehler wieder positiv machen */
                err += el;
                /*Schritt in langsame Richtung, Diagonalschritt*/
                x+=ddx;
                y+=ddy;
            } else{
                /* Schritt in schnelle Richtung, Parallelschritt */
                x += pdx;
                y += pdy;
            }
            int deltaX = x - start.getX();
            int deltaY = y - start.getY();
            if((deltaX*deltaX + deltaY*deltaY) > range*range){
                return false;
            }
            boolean success = setPixel(x, y, markInMap);
            if(!success){
                return false;
            }
        }

        return true;
    }



    int signum(int x){
        return (x > 0) ? 1 : (x < 0) ? -1 : 0;
    }


    boolean setPixel(int x, int y, boolean markInMap){

        Tile t = level.getTile(x, y);
        if(t != null){
            if(markInMap){
                t.inLOS = true;
                t.hasSeen = true;
            }
            return !level.isSolid(x, y);
        }else{
            return false;
        }

    }

}
