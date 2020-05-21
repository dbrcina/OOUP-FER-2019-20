package hr.fer.zemris.ooup.editor;

import hr.fer.zemris.ooup.editor.model.TextEditor;
import hr.fer.zemris.ooup.editor.observer.UndoManagerObserver;
import hr.fer.zemris.ooup.editor.observer.UndoManagerState;
import hr.fer.zemris.ooup.editor.singleton.UndoManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class JNotepad extends JFrame {

    private final TextEditor editor = new TextEditor();
    private final Timer timer = new Timer();

    public JNotepad() {
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setTitle("Notepad");
        initGUI();
        pack();
        setLocationRelativeTo(null);
        editor.requestFocus();

        // timer for making cursor blink
        timer.schedule(new TimerTask() {
            public void run() {
                editor.toggleBlinkCursor();
                SwingUtilities.invokeLater(editor::repaint);
            }
        }, 1_000, 500);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                exitAction.actionPerformed(null);
            }
        });
    }

    private void initGUI() {
        getContentPane().add(editor);
        initActions();
        setJMenuBar(createMenuBar());
        getContentPane().add(createToolbar(), BorderLayout.PAGE_START);
    }

    private void initActions() {
        cutAction.putValue(Action.NAME, "Cut");
        cutAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control X"));
        cutAction.setEnabled(false);
        copyAction.putValue(Action.NAME, "Copy");
        copyAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control C"));
        copyAction.setEnabled(false);
        pasteAction.putValue(Action.NAME, "Paste");
        pasteAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control V"));
        pasteAction.setEnabled(false);
        pasteAndTakeAction.putValue(Action.NAME, "Paste and Take");
        pasteAndTakeAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control shift V"));
    }

    private JMenuBar createMenuBar() {
        JMenuBar mb = new JMenuBar();
        mb.add(fileMenu());
        mb.add(editMenu());
        mb.add(moveMenu());
        return mb;
    }

    private JMenu fileMenu() {
        JMenu menu = new JMenu("File");
        menu.add(new JMenuItem(openAction));
        menu.add(new JMenuItem(saveAction));
        menu.add(new JMenuItem(exitAction));
        return menu;
    }

    private JMenu editMenu() {
        JMenu menu = new JMenu("Edit");
        menu.add(new JMenuItem(undoAction));
        menu.add(new JMenuItem(redoAction));
        menu.add(new JMenuItem(cutAction));
        menu.add(new JMenuItem(copyAction));
        menu.add(new JMenuItem(pasteAction));
        menu.add(new JMenuItem(pasteAndTakeAction));
        menu.add(new JMenuItem("Delete selection"));
        menu.add(new JMenuItem("Clear document"));
        return menu;
    }

    private JMenu moveMenu() {
        JMenu menu = new JMenu("Move");
        menu.add(new JMenuItem("Cursor to document start"));
        menu.add(new JMenuItem("Cursor to document end"));
        return menu;
    }

    private JToolBar createToolbar() {
        JToolBar tb = new JToolBar();
        tb.add(undoAction);
        tb.add(redoAction);
        tb.addSeparator();
        tb.add(cutAction);
        tb.add(copyAction);
        tb.add(pasteAction);
        return tb;
    }

    private final Action openAction = new OpenAction(this, editor);

    private final Action saveAction = new SaveAction(this, editor);

    private final Action exitAction = new ExitAction(this, timer);

    private final Action undoAction = new UndoAction();

    private final Action redoAction = new RedoAction();

    private static class OpenAction extends AbstractAction {
        private final JFrame frame;
        private final TextEditor editor;

        private OpenAction(JFrame frame, TextEditor editor) {
            this.frame = frame;
            this.editor = editor;
            putValue(Action.NAME, "Open");
            putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control O"));
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            JFileChooser jfc = new JFileChooser();
            jfc.setDialogTitle("Open");
            if (jfc.showOpenDialog(frame) != JFileChooser.APPROVE_OPTION) {
                return;
            }
            Path filePath = jfc.getSelectedFile().toPath();
            try {
                List<String> lines = Files.readAllLines(filePath);
                editor.reset(lines);
            } catch (IOException ioException) {
                jfc.showDialog(frame, "Error occurred while reading from a file");
            }
        }
    }

    private static class SaveAction extends AbstractAction {
        private final JFrame frame;
        private final TextEditor editor;

        private SaveAction(JFrame frame, TextEditor editor) {
            this.frame = frame;
            this.editor = editor;
            putValue(Action.NAME, "Save");
            putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control S"));
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            JFileChooser jfc = new JFileChooser();
            jfc.setDialogTitle("Save");
            if (jfc.showSaveDialog(frame) != JFileChooser.APPROVE_OPTION) {
                return;
            }
            Path destination = jfc.getSelectedFile().toPath();
            if (Files.exists(destination)) {
                int dialogResult = JOptionPane.showConfirmDialog(
                        frame, "Do you want to overwrite?", "Save",
                        JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (dialogResult != JOptionPane.YES_OPTION) {
                    return;
                }
            }
            try {
                editor.save(destination);
            } catch (IOException ioException) {
                jfc.showDialog(frame, "Error occurred while writing to a file");
            }
        }
    }

    private static class ExitAction extends AbstractAction {
        private final JFrame frame;
        private final Timer timer;

        private ExitAction(JFrame frame, Timer timer) {
            this.frame = frame;
            this.timer = timer;
            putValue(Action.NAME, "Exit");
            putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control W"));
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            timer.cancel();
            frame.dispose();
        }
    }

    private static class UndoAction extends AbstractAction implements UndoManagerObserver {
        private final UndoManager manager = UndoManager.getInstance();

        private UndoAction() {
            manager.attachUndoStackObserver(this);
            setEnabled(false);
            putValue(Action.NAME, "Undo");
            putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control Z"));
        }

        @Override
        public void updatedUndoManager(UndoManagerState state) {
            setEnabled(state == UndoManagerState.STACK_NOT_EMPTY);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            manager.undo();
        }
    }

    private static class RedoAction extends AbstractAction implements UndoManagerObserver {
        private final UndoManager manager = UndoManager.getInstance();

        private RedoAction() {
            manager.attachRedoStackObserver(this);
            setEnabled(false);
            putValue(Action.NAME, "Redo");
            putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control Y"));
        }

        @Override
        public void updatedUndoManager(UndoManagerState state) {
            setEnabled(state == UndoManagerState.STACK_NOT_EMPTY);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            manager.redo();
        }
    }

    private final Action cutAction = new AbstractAction() {
        public void actionPerformed(ActionEvent e) {
            editor.cut();
        }
    };

    private final Action copyAction = new AbstractAction() {
        public void actionPerformed(ActionEvent e) {
            editor.copy();
        }
    };

    private final Action pasteAction = new AbstractAction() {
        public void actionPerformed(ActionEvent e) {
            editor.paste();
        }
    };

    private final Action pasteAndTakeAction = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            editor.pasteAndTake();
        }
    };

}
