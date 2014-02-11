package com.stuckinadrawer.dungeongame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.stuckinadrawer.dungeongame.actors.Player;
import com.stuckinadrawer.dungeongame.actors.enemies.Enemy;
import com.stuckinadrawer.dungeongame.tiles.Tile;

import java.util.ArrayList;
import java.util.HashMap;

public class Renderer {

    private Level level;

    private OrthographicCamera camera;


    private HashMap<String, AtlasRegion> regions;
    private TextureAtlas textureAtlas;
    private SpriteBatch batch;

    public Renderer(Level level, OrthographicCamera camera){
        this.level = level;
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
        renderPlayer();
        renderEnemies();
        batch.end();
    }

    private void renderPlayer() {
        Player player = level.getPlayer();
        float posX = player.getPosition().getX() * Constants.TILE_SIZE;
        float posY = player.getPosition().getY() * Constants.TILE_SIZE;
        AtlasRegion spriteRegion = regions.get(player.getSpriteName());
        batch.draw(spriteRegion, posX, posY, Constants.TILE_SIZE+1, Constants.TILE_SIZE+1);
    }

    private void renderEnemies() {
        ArrayList<Enemy> enemies = level.getEnemies();
        for(Enemy e: enemies){
            AtlasRegion spriteRegion = regions.get(e.getSpriteName());
            float posX = e.getPosition().getX() * Constants.TILE_SIZE;
            float posY = e.getPosition().getY() * Constants.TILE_SIZE;
            batch.draw(spriteRegion, posX, posY, Constants.TILE_SIZE+1, Constants.TILE_SIZE+1);
            if(!e.dead && e.currentHP < e.maxHP){
                posY += Constants.TILE_SIZE;
                batch.draw(regions.get("red"),posX, posY, Constants.TILE_SIZE, 5);
                int barPos  = calculatePixelFilled(e.currentHP, e.maxHP);
                batch.draw(regions.get("green"),posX, posY, barPos, 5);

            }

        }
    }

    private int calculatePixelFilled(int currentHealth, int maxHealth) {
        float percentFilled = 100f / (float)maxHealth * (float)currentHealth;
        float onePercent = (float)Constants.TILE_SIZE / 100f;
        float pixelFilled = onePercent * percentFilled;
        return (int)pixelFilled;
    }

    private void renderTiles() {
        Tile[][] levelData = level.getLevelData();
        for(int x = 0; x < levelData.length; x++){
            for(int y = 0; y < levelData[x].length; y++){
                Tile tile = levelData[x][y];
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
