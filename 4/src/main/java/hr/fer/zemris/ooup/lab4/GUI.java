package hr.fer.zemris.ooup.lab4;

import hr.fer.zemris.ooup.lab4.model.DocumentModel;
import hr.fer.zemris.ooup.lab4.model.GraphicalObject;
import hr.fer.zemris.ooup.lab4.renderer.G2DRenderer;
import hr.fer.zemris.ooup.lab4.renderer.Renderer;
import hr.fer.zemris.ooup.lab4.state.IdleState;
import hr.fer.zemris.ooup.lab4.state.State;
import hr.fer.zemris.ooup.lab4.util.Point;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class GUI extends JFrame {

    private final List<GraphicalObject> objects;
    private final DocumentModel model;
    private final Canvas canvas;
    private State currentState;

    public GUI(List<GraphicalObject> objects) {
        this.objects = objects;
        this.model = new DocumentModel();
        this.currentState = new IdleState();
        this.canvas = new Canvas(model, currentState);

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
        for (GraphicalObject object : objects) {
            Action a = new AbstractAction() {
                {
                    putValue(NAME, object.getShapeName());
                }

                @Override
                public void actionPerformed(ActionEvent e) {

                }
            };
            toolBar.add(a);
        }
        return toolBar;
    }

    private static class Canvas extends JComponent {
        private final DocumentModel model;
        private State currentState;

        private Canvas(DocumentModel model, State currentState) {
            this.model = model;
            this.currentState = currentState;
            registerKeyListener();
            registerMouseListener();
            registerMouseMotionListener();
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
        }
    }
}
