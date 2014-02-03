package com.stuckinadrawer.dungeongame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.stuckinadrawer.dungeongame.actors.enemies.Enemy;
import com.stuckinadrawer.dungeongame.tiles.Tile;

import java.util.ArrayList;
import java.util.HashMap;

public class Renderer {

    private Tile[][] level;

    private ArrayList<Enemy> enemies;
    private OrthographicCamera camera;

    private HashMap<String, AtlasRegion> regions;
    private TextureAtlas textureAtlas;
    private SpriteBatch batch;

    public Renderer(Tile[][] level, ArrayList<Enemy> enemies, OrthographicCamera camera){
        this.level = level;
        this.enemies = enemies;
        this.camera  = camera;
        initialize();

    }

    private void initialize(){
        regions = new HashMap<String, AtlasRegion>();
        textureAtlas = new TextureAtlas(Gdx.files.internal("textures/pack"), Gdx.files.internal("textures"));
        for(AtlasRegion region: textureAtlas.getRegions()){
            regions.put(region.name, region);
        }
        batch = new SpriteBatch();

    }

    public void update(){
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        renderTiles();
        renderEnemies();
        batch.end();
    }

    private void renderEnemies() {
        for(Enemy e: enemies){
            AtlasRegion spriteRegion = regions.get(e.getSpriteName());
            float posX = e.getPosition().getX() * Constants.TILE_SIZE;
            float posY = e.getPosition().getY() * Constants.TILE_SIZE;
            batch.draw(spriteRegion, posX, posY, Constants.TILE_SIZE+1, Constants.TILE_SIZE+1);
        }
    }

    private void renderTiles() {
        for(int x = 0; x < level.length; x++){
            for(int y = 0; y < level[x].length; y++){
                Tile tile = level[x][y];
                if(tile.getSpriteName() != null){
                    AtlasRegion spriteRegion = regions.get(tile.getSpriteName());
                    float posX = tile.getPosition().getX() * Constants.TILE_SIZE;
                    float posY = tile.getPosition().getY() * Constants.TILE_SIZE;

                    batch.draw(spriteRegion, posX, posY, Constants.TILE_SIZE+1, Constants.TILE_SIZE+1);
                    if(tile.object!=null){
                        spriteRegion = regions.get(tile.object);
                        batch.draw(spriteRegion, posX, posY, Constants.TILE_SIZE+1, Constants.TILE_SIZE+1);
                    }
                }
            }
        }
    }


}
