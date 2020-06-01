package hr.fer.zemris.ooup.lab4;

import hr.fer.zemris.ooup.lab4.model.GraphicalObject;
import hr.fer.zemris.ooup.lab4.model.LineSegment;
import hr.fer.zemris.ooup.lab4.model.Oval;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class Demo {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            List<GraphicalObject> objects = new ArrayList<>();
            objects.add(new LineSegment());
            objects.add(new Oval());
            new GUI(objects).setVisible(true);
        });
    }

}
