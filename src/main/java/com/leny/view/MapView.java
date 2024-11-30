package com.leny.view;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;

import static com.leny.model.AppSettings.windowSize;
import com.leny.model.Entity;
import static com.leny.view.Colors.BG_COLOR;
/**
 * The UI for the minimap component
 */
public class MapView extends JLayeredPane {

    JLabel mapLabel;
    GameDataBar gameDataBar;

    public MapView(Image img, GameDataBar gameDataBar) {
        this.gameDataBar = gameDataBar;
        double padding = 0.1 * windowSize.getHeight();
        double height = windowSize.getHeight() - padding;
        double ratio = height / windowSize.width;
        int finalWidth = (int) (windowSize.width * ratio);
        int finalHeight = (int) height;
        img = img.getScaledInstance(finalWidth, finalHeight, Image.SCALE_SMOOTH);
        mapLabel = new JLabel(new ImageIcon(img));
        this.setVisible(true);
        this.setLayout(null);
        mapLabel.setBounds(0, 0, finalWidth, finalHeight);
        this.setPreferredSize(new Dimension(finalWidth, finalHeight));
        this.setBackground(BG_COLOR);
        this.add(mapLabel, JLayeredPane.DEFAULT_LAYER);
    }

    public void setup(List<Entity> entities) {
        int w = mapLabel.getWidth();
        int h = mapLabel.getHeight();
        for (Entity e : entities) {
            Point p = e.getLocation(w, h);
            p.x += this.getLocation().x;
            p.y += this.getLocation().y;
            int r = 30;
            e.resizeImg(r);
            EntityImageBox b = new EntityImageBox(e);
            b.setBounds(p.x, p.y, r, r);
            b.setLocation(p);
            this.add(b, JLayeredPane.PALETTE_LAYER);
            b.addMouseListener(new ChampDataDisplayer());
        }
        this.revalidate();
        this.updateUI();
    }

    public Queue<Point> getChampPoints() {
        Queue<Point> positions = new LinkedList<>();
        int w = mapLabel.getWidth();
        int h = mapLabel.getHeight();
        int padding = 70;
        // team blue
        positions.add(new Point(padding, 2 * padding));
        positions.add(new Point(w / 4, h / 3 + padding));
        positions.add(new Point(w / 2 - padding, w / 2));
        int botpad = 30;
        positions.add(new Point(w - 2 * padding - botpad, h - 2 * padding + botpad));
        positions.add(new Point(w - 3 * padding - botpad, h - 3 * padding + botpad));
        // team red
        positions.add(new Point(2 * padding, padding));
        positions.add(new Point(w - w / 4, h - h / 2));
        positions.add(new Point(w / 2 + padding, w / 2 - padding));
        positions.add(new Point(w - 2 * padding, h - 3 * padding));
        positions.add(new Point(w - padding, h - 2 * padding));
        return positions;
    }

    public void updateImage(ImageIcon icon) {
        System.out.println("UPDATE IMAGE");
        mapLabel.setIcon(icon);
        mapLabel.updateUI();
    }
    private class ChampDataDisplayer extends MouseAdapter {

        @Override
        public void mousePressed(MouseEvent e) {
            EntityImageBox box = (EntityImageBox) e.getSource();
            gameDataBar.update(box.getEntity());
        }
    }
}
