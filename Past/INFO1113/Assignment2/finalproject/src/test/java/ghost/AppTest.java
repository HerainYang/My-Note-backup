/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package ghost;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.json.simple.parser.ParseException;
import processing.core.PApplet;
import processing.event.KeyEvent;

import javax.xml.crypto.dsig.keyinfo.KeyValue;
import java.awt.*;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class AppTest {
    @Test 
    public void simpleTest() {
        App classUnderTest = new App();
        assertNotNull(classUnderTest);
    }

    @Test
    public void testGhosts() {
        App app = new App();
        assertNotNull(app);

        //test ghost walk around
        Chaser ghost = new Chaser(app, 0, 0, 0);
        ghost.currentDirection = app.UP;
        ghost.walk();
        assert(ghost.currentDirection == app.UP);
        ghost.currentDirection = app.DOWN;
        ghost.walk();
        assert(ghost.currentDirection == app.DOWN);
        ghost.turnBack();
        ghost.intersection();
        ghost.currentDirection = app.RIGHT;
        ghost.walk();
        assert(ghost.currentDirection == app.RIGHT);
        ghost.currentDirection = app.LEFT;
        ghost.walk();
        assert(ghost.currentDirection == app.LEFT);
        ghost.turnBack();
        ghost.intersection();


        ghost.setGhost_Type(1);
        ghost.countingDistance(new Message(1, 1, 1, null), new Message(1, 1, 1, null));
        ghost.escape(60);
        ghost.predict(39);
        ghost.predict(40);
        ghost.judgement(new Message(1, 1, 1, null));

        //test ghost find its target in different mode
        //ScatterMode
        assertNotNull(ghost.findTarget(Ghosts.SCATTERMODE, new Message(1, 1, 1, null)));
        ghost = new Chaser(app, -1, -1, 0);
        assertNotNull(ghost.findTarget(Ghosts.SCATTERMODE, new Message(1, 1, 1, null)));
        ghost = new Chaser(app, 10000, 10000, 0);
        assertNotNull(ghost.findTarget(Ghosts.SCATTERMODE, new Message(1, 1, 1, null)));
        ghost = new Chaser(app, 10000, -10000, 0);
        assertNotNull(ghost.findTarget(Ghosts.SCATTERMODE, new Message(1, 1, 1, null)));
        ghost = new Chaser(app, -10000, 10000, 0);
        assertNotNull(ghost.findTarget(Ghosts.SCATTERMODE, new Message(1, 1, 1, null)));

        //mix mode test
        Ignorant ignorant = new Ignorant(app, 0, 0, 0);
        assertNotNull(ignorant.findTarget(Ghosts.CHASEMODE, new Message(1, 1, 1, null)));
        assertNotNull(ignorant.findTarget(Ghosts.SCATTERMODE, new Message(1, 1, 1, null)));
        assertNotNull(ignorant.findTarget(Ghosts.CHASEMODE, new Message(1, 1, 0, null)));
        assertNotNull(ignorant.findTarget(Ghosts.CHASEMODE, new Message(1000, 1000, 1, null)));

        //test Whim find target
        Whim whim = new Whim(app, 0, 0, 0);
        assertNotNull(whim.chaser_Position = new Message(1, 1, 1, null));
        assertNotNull(whim.findTarget(Ghosts.CHASEMODE, new Message(1, 1, 1, null)));
        whim.chaser_Position = new Message(1, 1, 1, null);
        assertNotNull(whim.findTarget(Ghosts.CHASEMODE, new Message(1, 1, 0, null)));
        whim.chaser_Position = null;
        assertNotNull(whim.findTarget(Ghosts.CHASEMODE, new Message(1, 1, 1, null)));
        assertNotNull(whim.findTarget(Ghosts.SCATTERMODE, new Message(1, 1, 0, null)));
        whim.doubleVector(1,1,1,1);
        whim.passChaserPosition(new Message(1, 1, 0, null));

        //test chaser find target
        Chaser chaser = new Chaser(app, 0, 0, 0);
        assertNotNull(chaser.findTarget(Ghosts.CHASEMODE, new Message(1, 1, 1, null)));
        assertNotNull(chaser.findTarget(Ghosts.SCATTERMODE, new Message(1, 1, 1, null)));
        assertNotNull(chaser.findTarget(Ghosts.SCATTERMODE, new Message(1, 1, 0, null)));

        //test ambusher find target
        Ambusher ambusher = new Ambusher(app, 0, 0, 0);
        assertNotNull(ambusher.findTarget(Ghosts.CHASEMODE, new Message(1, 1, 0, null)));
        assertNotNull(ambusher.findTarget(Ghosts.SCATTERMODE, new Message(1, 1, 1, null)));

        //test ambusher chaseMode
        assertNotNull(ambusher.findTarget(Ghosts.CHASEMODE, new Message(1, 10000, 40, null)));
        assertNotNull(ambusher.findTarget(Ghosts.CHASEMODE, new Message(1, 10000, 39, null)));
        assertNotNull(ambusher.findTarget(Ghosts.CHASEMODE, new Message(-10000, 1, 40, null)));
        assertNotNull(ambusher.findTarget(Ghosts.CHASEMODE, new Message(-10000, 1, 39, null)));


    }

    @Test
    public void testPlayer() {
        App app = new App();
        Player player = new Player(app, 0, 0, 0);

        //test player walk around and test collider
        //1, not opposite
        app.keyCode = 39;
        player.currentDirection = 39;
        player.run();
        player.predict(0);
        player.previousDirection = 39;
        player.currentDirection = 38;
        assertFalse(player.isOpposite());

        //2, is opposite
        player.previousDirection = 39;
        player.currentDirection = 37;
        assertTrue(player.isOpposite());

        //3, not opposite
        player.previousDirection = 38;
        player.currentDirection = 37;
        player.isOpposite();
        assertFalse(player.isOpposite());

        //4, is opposite
        player.previousDirection = 38;
        player.currentDirection = 40;
        player.isOpposite();
        assertTrue(player.isOpposite());

        //player work around
        player.currentDirection = 38;
        player.run();
        player.walk(app.UP);
        player.walk(app.DOWN);
        player.walk(app.LEFT);
        player.walk(app.RIGHT);


        //test is there is a wall in front of player
        app.gameMaps[0][0] = 0;
        player.isWall(0, 0);
        assertFalse(player.isWall(0, 0));


        app.gameMaps[0][0] = 7;
        player.isWall(0, 0);
        assertFalse(player.isWall(0, 0));

        app.gameMaps[0][0] = 8;
        player.isWall(0, 0);
        assertFalse(player.isWall(0, 0));
        app.gameMaps[0][0] = 9;
        player.isWall(0, 0);
        assertFalse(player.isWall(0, 0));
        app.gameMaps[0][0] = 1;
        player.isWall(0, 0);
        assertTrue(player.isWall(0, 0));

        //test all behaviour of player
        player.waitingDirection = -1;
        player.currentDirection = 39;
        player.previousDirection = 37;
        player.run();

        player.waitingDirection = 1;
        player.X = 0;
        player.Y = 0;
        player.nextMatrixX = 0;
        player.nextMatrixY = 0;
        app.gameMaps[1][0] = 1;
        app.gameMaps[0][1] = 1;
        app.keyCode = 40;
        player.currentDirection = 40;
        player.previousDirection = 39;
        player.run();

        player.waitingDirection = 1;
        player.X = 0;
        player.Y = 0;
        player.nextMatrixX = 0;
        player.nextMatrixY = 0;
        app.gameMaps[1][0] = 1;
        app.gameMaps[0][1] = 1;
        app.keyCode = 39;
        player.currentDirection = 39;
        player.previousDirection = 40;
        player.run();

        player.X = 1;
        player.Y = 1;
        player.run();
    }

    @Test
    public void testConfigReader() throws IOException, ParseException {
        ConfigReader configReader = new ConfigReader();
        configReader.getLives();
        configReader.getFrightenedLength();
        configReader.getGhostMode();
        configReader.getMapFile();
        configReader.getSpeed();
    }

    @Test
    public void testGhost(){
        App classUnderTest = new App();
        Chaser ghost = new Chaser(classUnderTest, 1, 1, 1);
        ghost.getGhost_Type();
        ghost.findTarget(Ghosts.CHASEMODE, new Message(1, 1, classUnderTest.UP, null));
        ghost.findTarget(Ghosts.SCATTERMODE, new Message(1, 1, classUnderTest.UP, null));

        ghost.currentDirection = 0;
        ghost.randomDirection(0);
        ghost.randomDirection(1);



        ghost.X = 16;
        ghost.Y = 16;
        ghost.currentDirection = App.RIGHT;
        classUnderTest.gameMaps[0][1] = 1;
        classUnderTest.gameMaps[2][1] = 1;
        classUnderTest.gameMaps[1][2] = 1;
        ghost.run(Ghosts.CHASEMODE, new Message(1, 1, classUnderTest.UP, null));
        classUnderTest.gameMaps[0][1] = 1;
        classUnderTest.gameMaps[2][1] = 1;
        classUnderTest.gameMaps[1][2] = 0;
        ghost.run(Ghosts.CHASEMODE, new Message(1, 1, classUnderTest.UP, null));
        ghost.escape(0);


    }

    @Test void testJudgement(){
        //test which way ghost will go to
        App classUnderTest = new App();
        Chaser ghost = new Chaser(classUnderTest, 1, 1, 1);
        ghost.currentDirection = PApplet.LEFT;
        assert(ghost.judgement(new Message(1, 1, 1, null)) == PApplet.LEFT);

        ghost.currentDirection = PApplet.UP;
        ghost.judgement(new Message(1, 1, 1, null));
        assert(ghost.judgement(new Message(1, 1, 1, null)) == PApplet.UP);

        ghost.currentDirection = PApplet.DOWN;
        ghost.judgement(new Message(1, 1, 1, null));
        assert(ghost.judgement(new Message(1, 1, 1, null)) == PApplet.LEFT);

        ghost.currentDirection = PApplet.RIGHT;
        ghost.judgement(new Message(1, 1, 1, null));
        assert(ghost.judgement(new Message(1, 1, 1, null)) == PApplet.UP);
    }

    @Test
    public void testMessage(){
        Message message = new Message(1, 1, App.UP, null);
        Message.copy(message, message);
    }

    @Test
    public void testGameParser(){
        App app = new App();
        assertNotNull(app);

        GameParser gameParser = new GameParser(app);
        assertNotNull(gameParser);

        gameParser.runNewGame();
        gameParser.frame = 0;
        gameParser.sleep(1);
        gameParser.frame = 100;
        gameParser.sleep(1);
        gameParser.refreshData();


        gameParser.mapRender(true);
        gameParser.mapRender(false);

        app.gameMaps[0][0] = 7;
        gameParser.playerEatSomething(new Message(0, 0, 1, null));

        app.gameMaps[0][0] = 8;
        gameParser.playerEatSomething(new Message(0, 0, 1, null));

        app.gameMaps[0][0] = 9;
        gameParser.playerEatSomething(new Message(0, 0, 1, null));

        gameParser.gameInit();

        //add ghost
        Message message = new Message(0, 0, 0, null);
        message.ghostTypes = Ghosts.AMBUSHER;
        gameParser.addGhostStartPoint(message);
        message = new Message(0, 0, 0, null);
        message.ghostTypes = Ghosts.CHASER;
        gameParser.addGhostStartPoint(message);
        message = new Message(0, 0, 0, null);
        message.ghostTypes = Ghosts.WHIM;
        gameParser.addGhostStartPoint(message);
        message = new Message(0, 0, 0, null);
        message.ghostTypes = Ghosts.IGNORANT;
        gameParser.addGhostStartPoint(message);
        message = new Message(0, 0, 0, null);
        message.ghostTypes = Ghosts.NORMAL;
        gameParser.addGhostStartPoint(message);
        gameParser.gameInit();


        //test render map
        gameParser.mapRender(true);

        //test ghost mode change
        gameParser.setFrightenedModeOn(true);
        gameParser.frame = 6000000;
        gameParser.updateGhostMode();

        //test two target at the same place
        assertTrue(gameParser.catchTarget(new Message(1, 1, 1, null), new Message(1, 1, 1, null)));
        assertTrue(gameParser.catchTarget(new Message(1, 1, 1, null), new Message(0, 1, 1, null)));
    }


    //simulate a game
    @Test
    public void testEveryThing(){
        try {
            App app = new App();
            assertNotNull(app);
            PApplet.runSketch(new String[]{"something"}, app);
            for(int i = 0; i < 1000; i ++){
                app.gameParser.runEachFrame();
                if(i > 100)
                    app.keyCode = PApplet.LEFT;
                if(i > 400){
                    app.gameParser.debugMode = true;
                }
            }
        } catch (NullPointerException e){
        }
    }

    //test keypressdown
    @Test
    public void testKeyPress(){
        App app = new App();
        assertNotNull(app);
        app.keyPressed(new KeyEvent(null, 1, 1, 1, (char) 1, 1, true));
        app.keyPressed(new KeyEvent(null, 1, 1, 1, (char) 1, 0x20, true));
    }

    @Test
    public void testCheating(){

    }

}
