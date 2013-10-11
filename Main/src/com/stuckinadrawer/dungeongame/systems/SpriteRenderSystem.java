package com.stuckinadrawer.dungeongame.systems;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.EntitySystem;
import com.artemis.annotations.Mapper;
import com.artemis.utils.Bag;
import com.artemis.utils.ImmutableBag;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.stuckinadrawer.dungeongame.Constants;
import com.stuckinadrawer.dungeongame.components.Position;
import com.stuckinadrawer.dungeongame.components.Sprite;

import java.util.*;

public class SpriteRenderSystem extends EntitySystem{
    @Mapper
    private
    ComponentMapper<Position> positionMapper;
    @Mapper
    private
    ComponentMapper<Sprite> spriteMapper;

    private HashMap<String, AtlasRegion> regions;
    private TextureAtlas textureAtlas;
    private SpriteBatch batch;
    private OrthographicCamera camera;

    private Bag<AtlasRegion> regionsByEntity;
    private List<Entity> sortedEntities;

    /**
     * Creates an entity system that uses the specified aspect as a matcher against entities.
     *
     * @param camera the orthographic camera
     */
    public SpriteRenderSystem(OrthographicCamera camera) {
        super(Aspect.getAspectForAll(Position.class, Sprite.class));
        this.camera = camera;
    }

    @Override
    public void initialize(){
        regions = new HashMap<String, AtlasRegion>();
        textureAtlas = new TextureAtlas(Gdx.files.internal("textures/pack"), Gdx.files.internal("textures"));
        for(AtlasRegion region : textureAtlas.getRegions()){
            regions.put(region.name, region);
        }
        regionsByEntity = new Bag<AtlasRegion>();
        batch = new SpriteBatch();
        sortedEntities = new ArrayList<Entity>();
    }

    @Override
    protected void begin(){
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
    }

    @Override
    protected boolean checkProcessing() {
        return true;
    }

    @Override
    protected void processEntities(ImmutableBag<Entity> entities) {

        for (Entity sortedEntity : sortedEntities) {
            process(sortedEntity);
        }
    }

    protected void process(Entity e){
        if(positionMapper.has(e)){
            Position position = positionMapper.get(e);

            AtlasRegion spriteRegion = regionsByEntity.get(e.getId());
            float posX = position.getX() * Constants.TILE_SIZE;
            float posY = position.getY() * Constants.TILE_SIZE;

            batch.draw(spriteRegion, posX, posY, Constants.TILE_SIZE+1, Constants.TILE_SIZE+1);


        }
    }

    @Override
    protected void end(){
        batch.end();

    }

    @Override
    protected void inserted(Entity e) {
        Sprite sprite = spriteMapper.get(e);
        regionsByEntity.set(e.getId(), regions.get(sprite.getName()));

        sortedEntities.add(e);

        Collections.sort(sortedEntities, new Comparator<Entity>() {
            @Override
            public int compare(Entity e1, Entity e2) {
                Sprite s1 = spriteMapper.get(e1);
                Sprite s2 = spriteMapper.get(e2);
                return s1.getLayer().compareTo(s2.getLayer());
            }
        });
    }

    @Override
    protected void removed(Entity e) {
        regionsByEntity.set(e.getId(), null);
        sortedEntities.remove(e);
    }
}
