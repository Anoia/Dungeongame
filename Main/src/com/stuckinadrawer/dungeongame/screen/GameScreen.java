package com.stuckinadrawer.dungeongame.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.stuckinadrawer.dungeongame.*;
import com.stuckinadrawer.dungeongame.actors.Player;
import com.stuckinadrawer.dungeongame.actors.enemies.Enemy;
import com.stuckinadrawer.dungeongame.items.Item;
import com.stuckinadrawer.dungeongame.items.LootGenerator;
import com.stuckinadrawer.dungeongame.items.Weapon;
import com.stuckinadrawer.dungeongame.items.WeaponGenerator;
import com.stuckinadrawer.dungeongame.levelGeneration.LevelCreator;
import com.stuckinadrawer.dungeongame.render.Renderer;
import com.stuckinadrawer.dungeongame.tiles.Tile;
import com.stuckinadrawer.dungeongame.tiles.WallTile;
import com.stuckinadrawer.dungeongame.ui.HUD;
import com.stuckinadrawer.dungeongame.util.Constants;
import com.stuckinadrawer.dungeongame.util.GestureDetection;
import com.stuckinadrawer.dungeongame.util.Position;
import com.stuckinadrawer.dungeongame.util.Utils;

import java.util.LinkedList;

import static java.lang.Math.abs;

public class GameScreen extends AbstractScreen {

    private OrthographicCamera camera;
    private Renderer renderer;

    private Level level;
    private Player player;

    private HUD hud;

    Stage stage;

    LootGenerator lootGenerator;

    private int elapsedTimeInAP = 0;


    /**
     * Initialize ALL THE THINGS!
     * @param gameContainer
     */

    public GameScreen(GameContainer gameContainer, Player player) {
        super(gameContainer);
        lootGenerator = new LootGenerator();
        LevelCreator levelCreator = new LevelCreator();
        level = levelCreator.getNewLevel(player);   //Player ohne Pos in den level gen
        this.player = level.getPlayer();            //player mit pos aus level
        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        renderer = new Renderer(level, camera, fontBig);
        camera.position.set(player.getPosition().getX()* Constants.TILE_SIZE, player.getPosition().getY()*Constants.TILE_SIZE, 0);

        player.setEquippedWeapon(lootGenerator.generateWeapon());
        player.addToInventory(lootGenerator.generateLoot());
        player.addToInventory(lootGenerator.generateLoot());
        player.addToInventory(lootGenerator.generateLoot());


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

    /**
     * The games main update method, is called every render
     * @param delta deltaTime
     */

    private void update(float delta){
        if(player.isMoving != -1){
            player.updateRenderPosition(delta);
            camera.position.set(player.renderPosition.getX(), player.renderPosition.getY(), 0);
        }else{
            if(playerAction()){
                processTurn();
            }


        }
    }

    public boolean playerAction(){
        if(player.movementPath != null && !player.movementPath.isEmpty()){
            Position newPos = player.movementPath.pop();
            Enemy e = level.getEnemyOnPos(newPos);
            if(e != null){
                //attackEnemy(e);

            } else{
                player.moveToPosition(newPos);
                level.updateFOV();
                elapsedTimeInAP = 0;
                if(player.movementPath.isEmpty()){
                    Tile t = level.getTile(newPos.getX(), newPos.getY());
                    if(t.getItem()!= null){
                        player.addToInventory(t.pickUpItem());
                        elapsedTimeInAP+=50;
                    }
                }
                elapsedTimeInAP+=100;
                System.out.println("ElapsedTimeMove: "+elapsedTimeInAP);
            }

            return true;
        }else{
            return false;
        }
    }


    /**
     * makes things happen after the player had a turn, moves enemies etc
     */
    public void processTurn() {
        for(Enemy enemy: level.getEnemies()){
            enemy.modifyActionPoints(elapsedTimeInAP);

            while (enemy.getActionPoints()>0){
                if(level.isTileNextToPlayer(enemy.getPosition().getX(), enemy.getPosition().getY())){
                    enemy.movementPath = null;
                    player.movementPath = null;
                    int requiredAP = 100/enemy.getSpeed();
                    if (enemy.getActionPoints() >= requiredAP){
                        attackPlayer(enemy);
                        enemy.modifyActionPoints(-requiredAP);
                        System.out.println(enemy.getSpriteName()+" has "+enemy.getActionPoints()+" AP left after attack ");
                    }else{
                        System.out.println(enemy.getSpriteName()+" has not enough AP to attack ");
                        break;
                    }



                }else if(level.isInLOS(enemy.getPosition(), player.getPosition(), enemy.viewDistance)){
                    boolean moved = false;
                    level.findPathForActor(enemy, player.getPosition());
                    if(enemy.movementPath!= null && !enemy.movementPath.isEmpty()){
                        Position newPos = enemy.movementPath.pop();
                        if(level.isWalkable(newPos.getX(), newPos.getY()) && !newPos.equals(player.getPosition())){

                            int requiredAP = 100/enemy.getSpeed();
                            if(enemy.getActionPoints()>=requiredAP){
                                System.out.println(enemy.getSpriteName() + " moving from "+enemy.getPosition().getX()+" "+enemy.getPosition().getY()+" to "+newPos.getX()+" "+newPos.getY());
                                enemy.moveToPosition(newPos);
                                enemy.modifyActionPoints(-100/enemy.getSpeed());
                                moved = true;
                                System.out.println(enemy.getSpriteName()+" has "+enemy.getActionPoints()+" AP left after movement ");
                            }else{
                                System.out.println(enemy.getSpriteName()+" has not enough AP to move ");
                                break;
                            }


                        }
                    }
                    if(!moved) enemy.waitTurn();
                }else{
                    //wait turn
                    enemy.waitTurn();
                }
            }

        }


    }

    public void handleClickOnTile(int x, int y){
        Tile t  = level.getTile(x, y);
        if(t instanceof WallTile){
            System.out.println("WALL: value " + ((WallTile) t).neighbourValue);
        }
        if(level.isOccupiedByActor(x, y) && level.isInLOS(player.getPosition(), level.getEnemyOnPos(new Position(x,y)).getPosition(), player.getEquippedWeapon().getRange())){
            //attack!
            Enemy e = level.getEnemyOnPos(new Position(x,y));
            attackEnemy(e);
            elapsedTimeInAP = 100 / player.getEquippedWeapon().getSpeed();
           // elapsedTimeInAP = 100;
            System.out.println("ElapsedTimeAttack: "+elapsedTimeInAP);
            processTurn();


        }else if(t!=null && !level.isSolid(x, y) && !level.isOccupiedByObject(x, y)&& t.hasSeen){
            if(x == player.getPosition().getX() && y == player.getPosition().getY()){
                //waitTurn();
                elapsedTimeInAP = 100;
                System.out.println("ElapsedTimeWait: "+elapsedTimeInAP);
                processTurn();

            }else{
                level.findPathForActor(player, new Position(x, y));
                elapsedTimeInAP = 0;
            }

        }
    }


    /**
     * is called when the Player attacks and enemy, calculates the fight
     * @param enemy the enemy being attacked by the Player
     */
    private void attackEnemy(Enemy enemy){
        int dmg = player.getAttackDamage();
        boolean enemyIsDead = enemy.takeDmg(dmg);
        renderer.newTextAnimationOnEnemy(dmg, enemy.getPosition());
        if(enemyIsDead){
            level.removeEnemy(enemy);
            if(player.earnXP(enemy.XPRewarded)){
                player.levelUP();
            }
            createLoot(enemy);
        }
        updateHUD();
    }

    /**
     * is called when an Enemy attacks the player, calculates the Fight
     * @param enemy the enemy attacking the player
     */
    private void attackPlayer(Enemy enemy){
        int dmg = enemy.getAttackDamage();
        System.out.println(player.getSpriteName() + " taking "+dmg + " damage from "+enemy.getSpriteName());
        boolean playerIsDead = player.takeDmg(dmg);
        renderer.newTextAnimationOnPlayer(dmg, player.getPosition());
        if(playerIsDead){
            System.out.println("GAME OVER");
        }

        updateHUD();

    }

    public void createLoot(Enemy e){
        if(Utils.random()>0.25){
            Item item = lootGenerator.generateLoot();
            Tile t = level.getTile(e.getPosition().getX(), e.getPosition().getY());
            t.setItem(item);
        }
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

    public void updateHUD(){
        hud.updateHUD();
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

    public Renderer getRenderer(){
        return renderer;
    }
}
