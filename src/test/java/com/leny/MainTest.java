package com.leny;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import com.leny.controller.DraftPhaseController;
import com.leny.controller.GamePhaseController;
import com.leny.controller.MenuPhaseController;
import com.leny.controller.PhaseController;
import com.leny.model.AppSettings;
import com.leny.model.ButtonFactory;
import com.leny.model.Champion;
import com.leny.model.Champion.Team;
import com.leny.model.DataOutput;
import com.leny.model.Draft;
import com.leny.model.Entity;
import com.leny.model.EntityFactory;
import com.leny.model.Loader;
import com.leny.model.MiniMap;
import com.leny.model.Vec2;
import com.leny.view.ChampImageBox;
import com.leny.view.DraftSideBar;
import com.leny.view.LabelFactory;

public class MainTest {

    /**
     * Complete művelet lefut
     */
    @Test
    public void Test1() {
        List<PhaseController> phases = new LinkedList<>();
        MenuPhaseController controller = new MenuPhaseController(phases, null);
        controller.complete();
    }

    /**
     * Ha phases null, akkor sincs error
     */
    @Test
    public void Test2() {
        MenuPhaseController controller = new MenuPhaseController(null, null);
        Assertions.assertDoesNotThrow(() -> {
            controller.complete();
        });
    }

    /**
     * Ha phases null, akkor sincs error
     */
    @Test
    public void Test3() {
        PhaseController controller = new GamePhaseController(null, null,new ArrayList<Champion>());
        Assertions.assertDoesNotThrow(() -> {
            controller.back();
        });
    }

    /**
     * Champion osztály tesztjei
     */
    @Test
    public void Test4() {
        Champion champ = new Champion("test");
        Champion emptyChamp = new Champion();

        assertNull("", emptyChamp.getName());
        assertEquals("test", champ.getName());
        champ.setGoldEarned(200);
        assertEquals(champ.getGoldEarned(),200);
        champ.setStrength(100);
        assertEquals(champ.getStrength(),100);
        champ.setLevel(5);
        assertEquals(champ.getLevel(),5);
        champ.setTeam(Team.BLUE);
        assertEquals(champ.getTeam(),Team.BLUE);
    }

    /**
     * Labelfactory teszt
     */
    @Test
    public void Test5() {
        JLabel label = LabelFactory.createDataLabel("test");

        assertEquals("test", label.getText());
        assertEquals(Color.white, label.getForeground());
        assertEquals(18, label.getFont().getSize());
    }

    /**
     * Loader image betöltés teszt
     */
    @Test
    public void Test6() {
        List<Champion> champs = Loader.getAllChamps();
        List<ChampImageBox> boxes = Loader.getAllChampsIcons(champs);
        assertEquals(champs.size(), boxes.size());
        assertTrue(champs.size() > 0);
        assertNotNull(boxes.get(0).getChamp());
    }

    /**
     * Loader image betöltés teszt
     */
    @Test
    public void Test7() {
        Assertions.assertNotNull(Loader.getMapIconImg("minion"));
        Assertions.assertNotNull(Loader.getMapIconImg("ward"));
        assertNull("The loader should be null", Loader.getMapIconImg("random szoveg"));
    }

    /**
     * Draftsidebar check
     */
    @Test
    public void Test8() {
        List<PhaseController> phases = new ArrayList<PhaseController>();
        PhaseController phaseController = new DraftPhaseController(phases,null);
        DraftSideBar sidebar = new DraftSideBar(phaseController);
    }

    /**
     * draft phase controller check
     */
    @Test
    public void Test9() {
        List<PhaseController> phases = new ArrayList<PhaseController>();
        DraftPhaseController phaseController = new DraftPhaseController(phases, null);
    }
    /**
     * buttonfactory check
     */
    @Test
    public void Test10() {
        JButton button = ButtonFactory.createMainMenuButton("test");
        assertEquals(button.getText(),"test");
    }
    /**
     * vec2 test
     */
    @Test
    public void Test11() {
        Vec2 v1 = new Vec2(2,5);
        Vec2 v2 = new Vec2(v1.X+4,v1.Y+6);
        assertEquals(v1.X,2);
        assertEquals(v1.Y,5);
        assertEquals(v2.X,6);
        assertEquals(v2.Y,11);
    }
    /**
     * minimap test
     */
    @Test
    public void Test12() {
        MiniMap map = new MiniMap();
        assertNotNull(map.getEntities());
        map.setMapImage(null);
    }
    /**
     * appsettings test
     */
    @Test
    public void Test13() {
        Point p = AppSettings.getWindowPosCentered(new Dimension(400,300));
        assertTrue(p.x > 0 && p.y > 0);
    }
    /**
     * dataoutput test
     */
    @Test
    public void Test14() {
        List<Champion> champs = new ArrayList<Champion>();
        List<Entity> entities = new ArrayList<Entity>();
        List<List<Point>> lines = new ArrayList<>();
        DataOutput output = new DataOutput(null,null,null);
        output.setChamps(champs);
        assertEquals(champs, output.getChamps());
        output.setEntities(entities);
        assertEquals(entities, output.getEntities());
        output.setLines(lines);
        assertEquals(lines, output.getLines());
    }
    /**
     * draft model check
     */
    @Test
    public void Test15() {
        Draft draft = new Draft(new DraftPhaseController(null, null));
        assertNotNull(draft.getAllChamps());
        assertNotNull(draft.getAllChampsIcons());
        assertNotNull(draft.getFinalChampList());
        assertNull(draft.findImageBoxByChamp(new Champion("test")));
        assertFalse(draft.isDone());
        draft.next(new Champion("myguy"));
    }
    /**
     * entity factory check
     */
    @Test
    public void Test16() {
        Entity minion = EntityFactory.createMinion(new Point(0,0));
        Entity ward = EntityFactory.createWard(new Point(200,150));
        assertNotNull(minion);
        assertNotNull(ward);
        assertEquals("Minion",minion.getName());
        assertEquals("Ward",ward.getName());
    }
     /**
     * entity test
     */
    @Test
    public void Test17() {
        Entity entity = new Entity("entity",Loader.getUnknownIcon(),new Point(0,0));
        assertNotNull(entity.getImg());
        entity.setLocation(new Point(356,199));
        assertEquals(356, entity.getLocation().x);
        assertEquals(199, entity.getLocation().y);
        assertEquals(0,entity.getLocation(150,15).x);
        assertEquals(0,entity.getLocation(150,15).y);
        entity.setGoldValue(10);
        assertEquals(10, entity.getGoldValue());
        entity.resizeImg(30);
    }
}
