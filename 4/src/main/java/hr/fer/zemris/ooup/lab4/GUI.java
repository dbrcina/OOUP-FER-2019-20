package hr.fer.zemris.ooup.lab4;

import hr.fer.zemris.ooup.lab4.model.CompositeShape;
import hr.fer.zemris.ooup.lab4.model.DocumentModel;
import hr.fer.zemris.ooup.lab4.model.GraphicalObject;
import hr.fer.zemris.ooup.lab4.renderer.G2DRenderer;
import hr.fer.zemris.ooup.lab4.renderer.Renderer;
import hr.fer.zemris.ooup.lab4.renderer.SVGRenderer;
import hr.fer.zemris.ooup.lab4.state.*;
import hr.fer.zemris.ooup.lab4.util.Point;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.*;

public class GUI extends JFrame {

    private final List<GraphicalObject> objects;
    private final DocumentModel model;
    private final Canvas canvas;
    private State currentState;

    private final Map<String, GraphicalObject> prototypes;

    public GUI(List<GraphicalObject> objects) {
        this.objects = objects;
        this.model = new DocumentModel();
        this.currentState = new IdleState();
        this.canvas = new Canvas();
        this.prototypes = new HashMap<>();

        objects.forEach(obj -> prototypes.put(obj.getShapeID(), obj));
        GraphicalObject composite = new CompositeShape(new ArrayList<>(), false);
        prototypes.put(composite.getShapeID(), composite);

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setTitle("Program za uređivanje vektorskih crteža");
        setSize(900, 700);
        setLocationRelativeTo(null);

        initGUI();
    }

    private void initGUI() {
        Container pane = getContentPane();
        pane.setLayout(new BorderLayout());

        pane.add(createToolbar(), BorderLayout.PAGE_START);
        pane.add(canvas);
    }

    private Component createToolbar() {
        JToolBar toolBar = new JToolBar();
        Action openAction = new AbstractAction() {
            {
                putValue(NAME, "Učitaj");
            }

            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser jfc = new JFileChooser(".");
                jfc.setDialogTitle("Učitaj");
                if (jfc.showOpenDialog(GUI.this) != JFileChooser.APPROVE_OPTION) return;
                Path file = jfc.getSelectedFile().toPath();
                try {
                    List<String> lines = Files.readAllLines(file);
                    Stack<GraphicalObject> stack = new Stack<>();
                    for (String line : lines) {
                        String[] parts = line.split(" ", 2);
                        prototypes.get(parts[0]).load(stack, parts[1]);
                    }
                    List<GraphicalObject> beforeObjs = new ArrayList<>(model.list());
                    beforeObjs.forEach(model::removeGraphicalObject);
                    stack.forEach(model::addGraphicalObject);
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        };
        toolBar.add(openAction);
        Action saveAction = new AbstractAction() {
            {
                putValue(NAME, "Pohrani");
            }

            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser jfc = new JFileChooser(".");
                jfc.setDialogTitle("Pohrani");
                if (jfc.showOpenDialog(GUI.this) != JFileChooser.APPROVE_OPTION) return;
                Path file = jfc.getSelectedFile().toPath();
                List<String> rows = new ArrayList<>();
                model.list().forEach(obj -> obj.save(rows));
                try {
                    Files.write(file, rows);
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        };
        toolBar.add(saveAction);
        Action exportAction = new AbstractAction() {
            {
                putValue(NAME, "SVG Export");
            }

            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser jfc = new JFileChooser(".");
                jfc.setDialogTitle("Export");
                if (jfc.showOpenDialog(GUI.this) != JFileChooser.APPROVE_OPTION) return;
                Path file = jfc.getSelectedFile().toPath();
                SVGRenderer r = new SVGRenderer(file.toString());
                model.list().forEach(obj -> obj.render(r));
                try {
                    r.close();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        };
        toolBar.add(exportAction);
        for (GraphicalObject object : objects) {
            Action a = new AbstractAction() {
                {
                    putValue(NAME, object.getShapeName());
                }

                @Override
                public void actionPerformed(ActionEvent e) {
                    String shapeName = (String) getValue(NAME);
                    GraphicalObject object = objects.stream()
                            .filter(obj -> obj.getShapeName().equals(shapeName))
                            .findFirst().get();
                    currentState.onLeaving();
                    currentState = new AddShapeState(model, object);
                }
            };
            toolBar.add(a);
        }
        Action selectionAction = new AbstractAction() {
            {
                putValue(NAME, "Selektiraj");
            }

            @Override
            public void actionPerformed(ActionEvent e) {
                currentState.onLeaving();
                currentState = new SelectShapeState(model);
            }
        };
        toolBar.add(selectionAction);
        Action eraserAction = new AbstractAction() {
            {
                putValue(NAME, "Brisalo");
            }

            @Override
            public void actionPerformed(ActionEvent e) {
                currentState.onLeaving();
                currentState = new EraserState(model);
            }
        };
        toolBar.add(eraserAction);
        return toolBar;
    }

    private class Canvas extends JComponent {
        private Canvas() {
            model.addDocumentModelListener(this::repaint);
            registerKeyListener();
            registerMouseListener();
            registerMouseMotionListener();
            requestFocus();
        }

        private void registerMouseListener() {
            addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    currentState.mouseDown(
                            new Point(e.getX(), e.getY()),
                            e.isShiftDown(),
                            e.isControlDown());
                    e.consume();
                }

                @Override
                public void mouseReleased(MouseEvent e) {
                    currentState.mouseUp(
                            new Point(e.getX(), e.getY()),
                            e.isShiftDown(),
                            e.isControlDown());
                    e.consume();
                }
            });
        }

        private void registerMouseMotionListener() {
            addMouseMotionListener(new MouseMotionAdapter() {
                @Override
                public void mouseDragged(MouseEvent e) {
                    currentState.mouseDragged(new Point(e.getX(), e.getY()));
                    e.consume();
                }
            });
        }

        private void registerKeyListener() {
            addKeyListener(new KeyAdapter() {
                @Override
                public void keyPressed(KeyEvent e) {
                    int code = e.getKeyCode();
                    if (code == KeyEvent.VK_ESCAPE) {
                        currentState.onLeaving();
                        currentState = new IdleState();
                    } else {
                        currentState.keyPressed(e.getKeyCode());
                    }
                    e.consume();
                }
            });
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.clearRect(0, 0, getWidth(), getHeight());
            Renderer r = new G2DRenderer(g2d);
            model.list().forEach(obj -> {
                obj.render(r);
                currentState.afterDraw(r, obj);
            });
            currentState.afterDraw(r);
            requestFocus();
        }
    }

}
