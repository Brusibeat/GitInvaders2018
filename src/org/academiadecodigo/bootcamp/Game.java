package org.academiadecodigo.bootcamp;
import org.academiadecodigo.bootcamp.GameEngine.Direction.Directions;
import org.academiadecodigo.bootcamp.GameEngine.EngineFactory;
import org.academiadecodigo.bootcamp.GameEngine.Objects.Score;
import org.academiadecodigo.bootcamp.GameObjects.*;
import org.academiadecodigo.bootcamp.GameObjects.Enemy.Ship;
import org.academiadecodigo.bootcamp.music.Music;
import org.academiadecodigo.simplegraphics.keyboard.Keyboard;
import org.academiadecodigo.simplegraphics.keyboard.KeyboardEvent;
import org.academiadecodigo.simplegraphics.keyboard.KeyboardEventType;
import org.academiadecodigo.simplegraphics.keyboard.KeyboardHandler;

public class Game implements KeyboardHandler {

    private Player player;
    private final int NUMBER_OF_ENEMIES = 20;
    private Ship[] enemies = new Ship[NUMBER_OF_ENEMIES];
    private Keyboard keyboard;
    private int scores = 1;
    private Score score = new Score(scores);

    public Game(){

        player = new Player();
        this.keyboard = new Keyboard(this);
        implementKeys();
        createEnemies();
    }

    private void createEnemies(){
        for( int i = 0; i < NUMBER_OF_ENEMIES; i++){
            this.enemies[i] = new Ship();
        }
    }

    public void start() throws Exception{

        Music m = new Music("back");
        m.startMusic(true);

        while(true){

            Thread.sleep(10);
            player.moveBullet();

            for(  Ship enemy : enemies){

                if(enemy.isDestroyed()){
                    continue;
                }
                enemy.move();
                enemy.moveBullet();
            }

            checkCollisions();
            if(player.isDestroyed()){

                Music dead = new Music("player_dead");
                dead.startMusic(true);

                Thread.sleep(5000);
                System.exit(0);
            }

            if( checkEnemies() ){
                createEnemies();
            }

        }
    }

    public boolean checkEnemies(){
        for( Ship enemy : enemies){
            if( !enemy.isDestroyed()){
                return false;
            }
        }
        scores++;
        score.update(scores);
        return true;
    }

    public void checkCollisions(){

        for(Ship enemy : enemies){

            if( enemy.isDestroyed()){
                continue;
            }
            if(enemy.getGraphics().getPo().getCol() == 0){
                enemy.hit();
                player.hit();
            }

            for(Bullet bullet : player.getBullets()){
                if( bullet != null){
                    if(bullet.isDestroyed()){
                        continue;
                    }

                    if( compareEnemy(enemy,bullet) ){
                        enemy.hit();
                        bullet.hit();
                    }
                }
            }
        }

        for(Ship enemy : enemies){
            if( enemy.isDestroyed()){
                continue;
            }

            for(Bullet bullet : enemy.getBullets()) {
                if( bullet != null){
                    if(bullet.isDestroyed()){
                        continue;
                    }

                    if( comparePlayer(player,bullet)) {
                        player.hit();
                        bullet.hit();
                    }
                }
            }
        }
    }

    public boolean compareEnemy(Ship enemy, Bullet bullet){
        return (enemy.getGraphics().getPo().getCol() == bullet.getGraphics().getPo().getCol() &&
                enemy.getGraphics().getPo().getRow() == bullet.getGraphics().getPo().getRow());
    }

    public boolean comparePlayer(Player player, Bullet bullet){
        return (player.getGraphics().getPo().getCol() == bullet.getGraphics().getPo().getCol() &&
                player.getGraphics().getPo().getRow() == bullet.getGraphics().getPo().getRow());
    }

    private void implementKeys(){

        KeyboardEvent up = new KeyboardEvent();
        up.setKey(KeyboardEvent.KEY_UP);
        up.setKeyboardEventType(KeyboardEventType.KEY_PRESSED);

        KeyboardEvent down = new KeyboardEvent();
        down.setKey(KeyboardEvent.KEY_DOWN);
        down.setKeyboardEventType(KeyboardEventType.KEY_PRESSED);

        KeyboardEvent space = new KeyboardEvent();
        space.setKey(KeyboardEvent.KEY_SPACE);
        space.setKeyboardEventType(KeyboardEventType.KEY_PRESSED);

        this.keyboard.addEventListener(up);
        this.keyboard.addEventListener(down);
        this.keyboard.addEventListener(space);
    }

    public void keyPressed(KeyboardEvent e){
        switch (e.getKey()){
            case KeyboardEvent.KEY_UP: player.move(Directions.UP);
                break;
            case KeyboardEvent.KEY_DOWN: player.move(Directions.DOWN);
                break;
            case KeyboardEvent.KEY_SPACE:
                Music m = new Music("bullet");
                m.startMusic(true);
                player.shoot();
                break;
        }

    }

    public void keyReleased(KeyboardEvent e){

    }
}
