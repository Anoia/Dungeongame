package com.stuckinadrawer.dungeongame.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.stuckinadrawer.dungeongame.*;
import com.stuckinadrawer.dungeongame.actors.Player;
import com.stuckinadrawer.dungeongame.actors.enemies.Enemy;
import com.stuckinadrawer.dungeongame.items.WeaponGenerator;
import com.stuckinadrawer.dungeongame.levelGeneration.LevelCreator;
import com.stuckinadrawer.dungeongame.render.Renderer;
import com.stuckinadrawer.dungeongame.tiles.Tile;
import com.stuckinadrawer.dungeongame.util.Constants;
import com.stuckinadrawer.dungeongame.util.GestureDetection;
import com.stuckinadrawer.dungeongame.util.Position;

import java.util.ArrayList;

import static java.lang.Math.abs;

public class GameScreen extends AbstractScreen {

    private OrthographicCamera camera;
    private Renderer renderer;

    private Level level;
    private Player player;

    private HUD hud;

    Stage stage;

    float movementTimer = 0;

    WeaponGenerator weaponGenerator;


    /**
     * Initialize ALL THE THINGS!
     * @param gameContainer
     */

    public GameScreen(GameContainer gameContainer, Player player) {
        super(gameContainer);
        weaponGenerator = new WeaponGenerator();
        LevelCreator levelCreator = new LevelCreator();
        level = levelCreator.getNewLevel(player);   //Player ohne Pos in den level gen
        this.player = level.getPlayer();            //player mit pos aus level
        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        renderer = new Renderer(level, camera, fontBig);
        camera.position.set(player.getPosition().getX()* Constants.TILE_SIZE, player.getPosition().getY()*Constants.TILE_SIZE, 0);

        player.setWeapon(weaponGenerator.createNewWeapon(1));


    }

    public GameScreen(GameContainer gameContainer){
        this(gameContainer, new Player());
    }



    /**
     * set up UI, gesture detection, inital fov
     */
    @Override
    public void show() {
        // Create a new Stage
       // stage = new Stage(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);
        stage = new Stage();
        // Create and set InputProcessors
        GestureDetector gd = new GestureDetector(new GestureDetection(this));
        InputMultiplexer im = new InputMultiplexer(stage, gd);
        Gdx.input.setInputProcessor(im);

        // Create a HUD
        hud = new HUD(this);

        // Give player inital FOV
        level.updateFOV();
}

    @Override
    public void dispose(){
        stage.dispose();
    }

    private void update(float delta){
        movementTimer +=delta;
        if(player.isMoving != -1){
            player.updateRenderPosition(delta);
            camera.position.set(player.renderPosition.getX(), player.renderPosition.getY(), 0);
        }else{
            if(movementTimer >0.1){
                //moves player if his movement queue is not empty
                if(player.action()){
                    System.out.println("process enemy");
                    processTurn();
                }
                movementTimer = 0;
            }
        }
    }


    /**
     * makes things happen after the player had a turn, moves enemies etc
     */
    public void processTurn() {

        ArrayList<Enemy> dead = new ArrayList<Enemy>();
        for(Enemy e: level.getEnemies()){
            if(e.dead){
                //level.removeEnemy(e);
                Tile t = level.getTile(e.getPosition().getX(), e.getPosition().getY());
                t.effect = "effect_blood";
                dead.add(e);
            }else{
                e.doTurn(level);
            }
        }
        level.getEnemies().removeAll(dead);

    }

    public void handleClickOnTile(int x, int y){
        Tile t  = level.getTile(x, y);
        if(isTileNextToPlayer(x, y) && level.isOccupiedByActor(x, y)){
            //attack!
            Enemy e = level.getEnemyOnPos(new Position(x,y));
            player.attack(e);
            processTurn();
        }else if(t!=null && !level.isSolid(x, y) && !level.isOccupiedByObject(x, y)&& t.hasSeen){
            if(x == player.getPosition().getX() && y == player.getPosition().getY()){
                level.waitTurn();
            }else{
                level.findPath(player, new Position(x, y));
            }

        }
    }

    private boolean isTileNextToPlayer(int x, int y){
        int distance = abs(player.getPosition().getX() - x) + abs(player.getPosition().getY()-y);
        return (distance == 1);
    }

    /**
     * this is called by Libgdx every frame,
     * has to call the update() method for stuff to happen
     * @param delta
     */
    @Override
    public void render(float delta) {
        super.render(delta);
        update(delta);
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));

        camera.update();
        renderer.update(delta);
        stage.draw();

    }

    public Player getPlayer() {
        return player;
    }

    public Skin getSkin(){
        return skin;
    }

    public Stage getStage(){
        return stage;
    }

    public OrthographicCamera getCamera() {
        return camera;
    }

    public Level getLevel() {
        return level;
    }
}
