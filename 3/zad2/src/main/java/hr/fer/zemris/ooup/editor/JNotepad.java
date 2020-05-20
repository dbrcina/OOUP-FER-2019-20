package hr.fer.zemris.ooup.editor;

import hr.fer.zemris.ooup.editor.model.TextEditor;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Timer;
import java.util.TimerTask;

public class JNotepad extends JFrame {

    private final TextEditor editor = new TextEditor();

    public JNotepad() {
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setTitle("Notepad");
        initGUI();
        pack();
        setLocationRelativeTo(null);

        // timer for making cursor blink
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            public void run() {
                editor.toggleBlinkCursor();
                SwingUtilities.invokeLater(editor::repaint);
            }
        }, 1_000, 500);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                e.getWindow().dispose();
                timer.cancel();
                System.exit(0);
            }
        });
    }

    private void initGUI() {
        getContentPane().add(editor);
        setJMenuBar(createMenuBar());
    }

    private JMenuBar createMenuBar() {
        JMenuBar mb = new JMenuBar();
        JMenu file = new JMenu("File");
        JMenu edit = new JMenu("Edit");
        mb.add(file);
        mb.add(edit);
        return mb;
    }

}
