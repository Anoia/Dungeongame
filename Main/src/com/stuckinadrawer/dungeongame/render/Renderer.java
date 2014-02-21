package com.stuckinadrawer.dungeongame.render;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.stuckinadrawer.dungeongame.Constants;
import com.stuckinadrawer.dungeongame.Level;
import com.stuckinadrawer.dungeongame.actors.Player;
import com.stuckinadrawer.dungeongame.actors.enemies.Enemy;
import com.stuckinadrawer.dungeongame.tiles.Tile;

import java.util.ArrayList;
import java.util.HashMap;

public class Renderer {

    private Level level;

    private OrthographicCamera camera;
    private BitmapFont font;


    private HashMap<String, AtlasRegion> regions;
    private TextureAtlas textureAtlas;
    private SpriteBatch batch;

    private Animation playerAnimation;
    float stateTime;

    public Renderer(Level level, OrthographicCamera camera, BitmapFont font){
        this.level = level;
        this.camera  = camera;
        this.font = font;
        initialize();

    }

    private void initialize(){
        regions = new HashMap<String, AtlasRegion>();
        textureAtlas = new TextureAtlas(Gdx.files.internal("textures/pack"), Gdx.files.internal("textures"));
        for(AtlasRegion region: textureAtlas.getRegions()){
            regions.put(region.name, region);
        }
        batch = new SpriteBatch();
        playerAnimation = new Animation(0.25f, textureAtlas.findRegions("char_player"));

        stateTime = 0f;
    }

    public void update(float delta){
        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        renderTiles();
        renderPlayer(delta);
        renderEnemies(delta);
        renderTextAnimations(delta);
        //font.draw(batch, "FPS: "+Gdx.graphics.getFramesPerSecond(), 10, 10);

        batch.end();
    }

    private void renderTextAnimations(float delta) {
        ArrayList<TextAnimation> toRemove = new ArrayList<TextAnimation>();
        for(TextAnimation tA: level.textAnimations){
            tA.animate(delta);
            font.setColor(tA.color);
            font.draw(batch, tA.text, tA.x, tA.y);
            if(tA.count > 1){
                toRemove.add(tA);
            }
            font.setColor(Color.WHITE);
        }
        level.textAnimations.removeAll(toRemove);

    }

    private void renderPlayer(float delta) {
        stateTime += Gdx.graphics.getDeltaTime();
        Player player = level.getPlayer();
        float posX = player.renderPosition.getX();
        float posY = player.renderPosition.getY();
        //AtlasRegion spriteRegion = regions.get(player.getSpriteName());
        TextureRegion spriteRegion;
        if(player.isMoving == -1){
            spriteRegion = regions.get(player.getSpriteName());
        }else{
            spriteRegion = playerAnimation.getKeyFrame(stateTime, true);
        }
        batch.draw(spriteRegion, posX, posY, Constants.TILE_SIZE+1, Constants.TILE_SIZE+1);


    }

    private void renderEnemies(float delta) {
        ArrayList<Enemy> enemies = level.getEnemies();
        for(Enemy e: enemies){
            Tile t = level.getTile(e.getPosition().getX(), e.getPosition().getY());
            if(t.inLOS){
                // TODO move this somewhere else! no logic update in render pos?
                if(e.isMoving !=-1){
                    e.updateRenderPosition(delta);
                }
                AtlasRegion spriteRegion = regions.get(e.getSpriteName());
                float posX = e.renderPosition.getX();
                float posY = e.renderPosition.getY();
                batch.draw(spriteRegion, posX, posY, Constants.TILE_SIZE+1, Constants.TILE_SIZE+1);
                /* Draw the Healthbar */
                if(!e.dead && e.currentHP < e.maxHP){
                    posY += Constants.TILE_SIZE;
                    batch.draw(regions.get("darkGrey"),posX, posY, Constants.TILE_SIZE, 5);
                    int barPos  = calculatePixelFilled(e.currentHP, e.maxHP);
                    batch.draw(regions.get("green"),posX, posY, barPos, 5);

                }
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
                if(tile.getSpriteName() != null && tile.hasSeen){
                    AtlasRegion spriteRegion = regions.get(tile.getSpriteName());
                    float posX = tile.getPosition().getX() * Constants.TILE_SIZE;
                    float posY = tile.getPosition().getY() * Constants.TILE_SIZE;

                    batch.draw(spriteRegion, posX, posY, Constants.TILE_SIZE+1, Constants.TILE_SIZE+1);
                    if(tile.object!=null){
                        spriteRegion = regions.get(tile.object);
                        batch.draw(spriteRegion, posX, posY, Constants.TILE_SIZE+1, Constants.TILE_SIZE+1);
                    }

                    if(!tile.inLOS){
                        Color c = batch.getColor();
                        batch.setColor(1f, 1f, 1f, 0.6f);
                        batch.draw(regions.get("black"), posX, posY, Constants.TILE_SIZE+1, Constants.TILE_SIZE+1 );
                        batch.setColor(c);
                    }
                }
            }
        }
    }



}
