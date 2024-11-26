package com.leny.controller;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JFrame;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.leny.model.Champion;
import com.leny.model.DataOutput;
import com.leny.view.GameView;

public class GamePhaseController extends PhaseController {

    GameView view;
    GameState state;
    JFrame mainFrame;

    public enum GameState {
        DRAW,
        PLACE_MINION,
        PLACE_WARD,
        DELETE
    }

    public GamePhaseController(List<PhaseController> phases, JFrame mainFrame, List<Champion> champs) {
        super(phases);
        view = new GameView(this, mainFrame, champs);
        this.mainFrame = mainFrame;
        this.state = GameState.DRAW;
    }

    public GamePhaseController(List<PhaseController> phases, JFrame mainFrame, String filepath) {
        super(phases);
        loadGameData(filepath);
        this.mainFrame = mainFrame;
        this.state = GameState.DRAW;
    }

    @Override
    public void setupPhase() {
        view.show();
    }

    public void setState(GameState s) {
        state = s;
    }

    public GameState getState() {
        return state;
    }

    @Override
    public void back() {
        phases.clear();
        phases.add(new MenuPhaseController(phases, mainFrame));
    }

    public void loadGameData(String path) {
        DataOutput input = loadFromFile(path);
        view = new GameView(this, mainFrame, input.getChamps());
        view.setLines(input.getLines());
        view.loadEntities(input.getEntities());
    }

    public void saveState(String path) {
        if (!path.endsWith(".json")) {
            path += ".json";
        }
        DataOutput output = new DataOutput(view.getChamps(), view.getSaveableEntities(), view.getDrawnLines());
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try (Writer writer = new FileWriter(path)) {
            gson.toJson(output, writer);
            System.out.println("data saved successfully to: " + path);
        } catch (IOException ex) {
            System.out.println("ERROR: CANT WRITE THERE");
            System.out.println(ex.getCause());
        }
    }

    public static DataOutput loadFromFile(String filePath) {
        Gson gson = new Gson();
        try (FileReader reader = new FileReader(filePath)) {
            return gson.fromJson(reader, DataOutput.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String fileLoader() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File("."));
        fileChooser.setDialogType(JFileChooser.FILES_ONLY);

        int result = fileChooser.showSaveDialog(null);
        if (result == fileChooser.APPROVE_OPTION) {
            return fileChooser.getSelectedFile().getPath();
        }
        return null;
    }
}
