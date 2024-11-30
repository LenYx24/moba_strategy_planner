package com.leny.model;

import java.awt.Image;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import javax.imageio.ImageIO;

import org.json.JSONArray;
import org.json.JSONObject;

import com.leny.view.ChampImageBox;

public class Loader {

    private static String getJsonString(String filepath) throws IOException {
        Scanner scanner;
        InputStream inp = ClassLoader.getSystemResource(filepath).openStream();
        scanner = new Scanner(inp);
        StringBuilder jsonString = new StringBuilder();
        while (scanner.hasNextLine()) {
            jsonString.append(scanner.nextLine());
        }
        scanner.close();
        return jsonString.toString();
    }

    public static List<Champion> getAllChamps() {
        List<Champion> allChamps = new ArrayList<>();
        String jsonString = "";
        try {
            jsonString = getJsonString("metadata/champs.json");
        } catch (IOException ex) {
            System.out.println("ERROR: FILE NOT FOUND: champs");
            System.out.println(ex.getMessage());
            return new ArrayList<>();
        }
        JSONArray jsonArray = new JSONArray(jsonString);
        for (int i = 0; i < jsonArray.length(); i++) {
            String name = jsonArray.get(i).toString();
            if (name.equals("unknown")) {
                continue;
            }
            allChamps.add(new Champion(name));
        }
        return allChamps;
    }

    public static Image getUnknownIcon() {
        Image img = null;
        try {
            img = ImageIO.read(ClassLoader.getSystemResource("champs/unknown.png"));
        } catch (IOException ex) {
            System.out.println("ERROR: Icon missing");
        }
        return img;
    }

    public static Image getMapIconImg(String name) {
        Image img = null;
        try {
            img = ImageIO.read(ClassLoader.getSystemResource("mapIcons/" + name + ".png"));
        } catch (IOException ex) {
            System.out.println("ERROR: Icon missing");
        }
        return img;
    }

    public static List<ChampImageBox> getAllChampsIcons(List<Champion> champs) {
        List<ChampImageBox> list = new ArrayList<>();
        Image unknownIcon = getUnknownIcon();
        for (Champion champ : champs) {
            String name = champ.getName();
            Image img;
            String path = "champs/" + name + ".png";
            try {
                img = ImageIO.read(ClassLoader.getSystemResource(path));
                img = img.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
            } catch (IOException ex) {
                img = unknownIcon;
            }
            list.add(new ChampImageBox(img, champ));
        }
        return list;
    }

    public static Image getMapImage() {
        Image img;
        try {
            img = ImageIO.read(ClassLoader.getSystemResource("map.jpg"));
        } catch (IOException ex) {
            img = null;
            System.out.println("COULDNT LOAD MAP");
        }
        return img;
    }

    public static List<Entity> getMapElements() {
        List<Entity> entities = new LinkedList<>();
        String jsonString = "";
        try {
            jsonString = getJsonString("metadata/mapElements.json");
        } catch (FileNotFoundException ex) {
            System.out.println("ERROR: FILE NOT FOUND");
            return new ArrayList<>();
        }
        JSONArray jsonArray = new JSONArray(jsonString);
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject entity = jsonArray.getJSONObject(i);
            String name = entity.getString("name");
            String path = entity.getString("filepath");

            double xp = entity.getDouble("xp");
            double yp = entity.getDouble("yp");
            Image img;
            try {
                String fullpath = "mapIcons/" + path;
                img = ImageIO.read(ClassLoader.getSystemResource(fullpath));
            } catch (IOException ex) {
                img = null;
                System.out.println("ERROR: COULDNT LOAD MAP ENTITY ICON");
            }
            entities.add(new Entity(name, img, xp, yp));
        }
        return entities;
    }
}
