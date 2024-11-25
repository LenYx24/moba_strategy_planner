package com.leny.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.leny.controller.DraftPhaseController;
import com.leny.model.Champion.Team;
import com.leny.view.ChampImageBox;

public class Draft {

    public enum Group {
        BLUEPICK, REDPICK, BLUEBAN, REDBAN
    }
    List<Champion> allChamps;
    List<ChampImageBox> allChampsIcons;
    List<ChampsList> teamSelections;
    ChampsList teamBluePicks = new ChampsList(Group.BLUEPICK);
    ChampsList teamRedPicks = new ChampsList(Group.REDPICK);
    ChampsList teamBlueBans = new ChampsList(Group.BLUEBAN);
    ChampsList teamRedBans = new ChampsList(Group.REDBAN);
    List<ChampsList> order;
    int current;
    Map<Group, ChampsList> mapping;
    DraftPhaseController phaseController;

    public class ChampsList {

        List<Champion> champs;
        Group group;

        public ChampsList(Group group) {
            this.champs = new ArrayList<>();
            this.group = group;
        }

        public void add(Champion champ) {
            if (champs.size() < 5) {
                champs.add(champ);
            }
            phaseController.updateChampsPanel(group);
        }

        public int getLength() {
            return champs.size();
        }

        public Champion last() {
            if (champs.isEmpty()) {
                return null;
            }
            return champs.get(champs.size() - 1);
        }

        public List<Champion> getChampsList() {
            return champs;
        }
    }

    public Draft(DraftPhaseController phaseController) {
        this.phaseController = phaseController;
        allChamps = Loader.getAllChamps();
        allChampsIcons = Loader.getAllChampsIcons(allChamps);
        current = 0;
        order = Arrays.asList(teamBlueBans,
                teamRedBans,
                teamBlueBans,
                teamRedBans,
                teamBlueBans,
                teamRedBans,
                teamBluePicks,
                teamRedPicks,
                teamRedPicks,
                teamBluePicks,
                teamBluePicks,
                teamRedPicks,
                teamRedBans,
                teamBlueBans,
                teamBlueBans,
                teamRedBans,
                teamRedPicks,
                teamBluePicks,
                teamBluePicks,
                teamRedPicks
        );
        mapping = new HashMap<>();
        mapping.put(Group.BLUEPICK, teamBluePicks);
        mapping.put(Group.REDPICK, teamRedPicks);
        mapping.put(Group.BLUEBAN, teamBlueBans);
        mapping.put(Group.REDBAN, teamRedBans);
    }

    public List<Champion> getAllChamps() {
        return allChamps;
    }

    public List<ChampImageBox> getAllChampsIcons() {
        return allChampsIcons;
    }

    public void next(Champion champ) {
        if (current < order.size()) {
            order.get(current).add(champ);
            current++;
        }
    }

    public ChampsList getChampsGroups(Group group) {
        if (!mapping.containsKey(group)) {
            throw new Error("Wrong group");
        }
        return mapping.get(group);
    }

    public Group getCurrentGroup() {
        for (Entry<Group, ChampsList> entry : mapping.entrySet()) {
            if (order.get(current) == entry.getValue()) {
                return entry.getKey();
            }
        }
        return null;
    }

    public ChampImageBox findImageBoxByChamp(Champion champ) {
        String name = champ.getName();
        for (ChampImageBox box : allChampsIcons) {
            if (name.equals(box.getChamp().getName())) {
                return box;
            }
        }
        return null;
    }

    public boolean isDone() {
        return current == 20;
    }

    public List<Champion> getFinalChampList() {
        List<Champion> list = new ArrayList<>();
        for (Champion champ : teamBluePicks.getChampsList()) {
            list.add(champ);
            champ.setTeam(Team.Blue);
        }
        for (Champion champ : teamRedPicks.getChampsList()) {
            list.add(champ);
            champ.setTeam(Team.Red);
        }
        return list;
    }
}
