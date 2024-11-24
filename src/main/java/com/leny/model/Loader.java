package com.leny.model;

import java.awt.Image;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.imageio.ImageIO;

import org.json.JSONArray;

import com.leny.view.ChampImageBox;

public class Loader {

    public static List<Champion> getAllChamps() {
        List<Champion> allChamps = new ArrayList<>();
        Scanner scanner;
        String path = ClassLoader.getSystemResource("metadata/champs.json").getFile();
        try {
            scanner = new Scanner(new File(path));
        } catch (FileNotFoundException ex) {
            System.out.println("ERROR: FILE NOT FOUND");
            return new ArrayList<>();
        }
        StringBuilder jsonString = new StringBuilder();
        while (scanner.hasNextLine()) {
            jsonString.append(scanner.nextLine());
        }
        scanner.close();
        JSONArray jsonArray = new JSONArray(jsonString.toString());
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
}
