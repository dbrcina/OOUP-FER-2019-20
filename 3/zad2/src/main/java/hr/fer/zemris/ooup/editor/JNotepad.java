package hr.fer.zemris.ooup.editor;

import hr.fer.zemris.ooup.editor.model.Location;
import hr.fer.zemris.ooup.editor.model.TextEditor;
import hr.fer.zemris.ooup.editor.observer.*;
import hr.fer.zemris.ooup.editor.singleton.UndoManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class JNotepad extends JFrame {

    private final TextEditor editor = new TextEditor();
    private final Timer timer = new Timer();
    private final StatusLabel statusLabel = new StatusLabel(editor);

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
        setJMenuBar(createMenuBar());
        getContentPane().add(createToolbar(), BorderLayout.PAGE_START);
        getContentPane().add(statusLabel, BorderLayout.PAGE_END);
    }

    private JMenuBar createMenuBar() {
        JMenuBar mb = new JMenuBar();
        mb.add(fileMenu());
        mb.add(editMenu());
        mb.add(moveMenu());
        mb.add(pluginsMenu());
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
        menu.add(new JMenuItem(deleteSelectionAction));
        menu.add(new JMenuItem(clearDocumentAction));
        return menu;
    }

    private JMenu moveMenu() {
        JMenu menu = new JMenu("Move");
        menu.add(new JMenuItem(cursorStartAction));
        menu.add(new JMenuItem(cursorEndAction));
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
    private final Action undoAction = new UndoAction(editor);
    private final Action redoAction = new RedoAction(editor);
    private final Action cutAction = new CutAction(editor);
    private final Action copyAction = new CopyAction(editor);
    private final Action pasteAction = new PasteAction(editor);
    private final Action pasteAndTakeAction = new PasteAndTakeAction(editor);
    private final Action deleteSelectionAction = new DeleteSelectionAction(editor);
    private final Action clearDocumentAction = new ClearDocumentAction(editor);
    private final Action cursorStartAction = new CursorStartAction(editor);
    private final Action cursorEndAction = new CursorEndAction(editor);

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
        private final TextEditor editor;
        private final UndoManager manager = UndoManager.getInstance();

        private UndoAction(TextEditor editor) {
            this.editor = editor;
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
            editor.requestFocus();
        }
    }

    private static class RedoAction extends AbstractAction implements UndoManagerObserver {
        private final TextEditor editor;
        private final UndoManager manager = UndoManager.getInstance();

        private RedoAction(TextEditor editor) {
            this.editor = editor;
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
            editor.requestFocus();
        }
    }

    private static class CutAction extends AbstractAction implements SelectionObserver {
        private final TextEditor editor;

        private CutAction(TextEditor editor) {
            this.editor = editor;
            putValue(Action.NAME, "Cut");
            putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control X"));
            setEnabled(false);
            editor.attachSelectionObs(this);
        }

        @Override
        public void selectionUpdated(SelectionState state) {
            setEnabled(state == SelectionState.NOT_EMPTY);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            editor.cut();
            editor.requestFocus();
        }
    }

    private static class CopyAction extends AbstractAction implements SelectionObserver {
        private final TextEditor editor;

        private CopyAction(TextEditor editor) {
            this.editor = editor;
            putValue(Action.NAME, "Copy");
            putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control C"));
            setEnabled(false);
            editor.attachSelectionObs(this);
        }

        @Override
        public void selectionUpdated(SelectionState state) {
            setEnabled(state == SelectionState.NOT_EMPTY);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            editor.copy();
            editor.requestFocus();
        }
    }

    private static class PasteAction extends AbstractAction implements ClipboardObserver {
        private final TextEditor editor;

        private PasteAction(TextEditor editor) {
            this.editor = editor;
            putValue(Action.NAME, "Paste");
            putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control V"));
            setEnabled(false);
            editor.attachClipboardObs(this);
        }

        @Override
        public void updateClipboard(ClipboardState state) {
            setEnabled(state == ClipboardState.STACK_NOT_EMPTY);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            editor.paste();
            editor.requestFocus();
        }
    }

    private static class PasteAndTakeAction extends AbstractAction implements ClipboardObserver {
        private final TextEditor editor;

        private PasteAndTakeAction(TextEditor editor) {
            this.editor = editor;
            putValue(Action.NAME, "Paste and Take");
            putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control shift V"));
            setEnabled(false);
            editor.attachClipboardObs(this);
        }

        @Override
        public void updateClipboard(ClipboardState state) {
            setEnabled(state == ClipboardState.STACK_NOT_EMPTY);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            editor.pasteAndTake();
            editor.requestFocus();
        }
    }

    private static class DeleteSelectionAction extends AbstractAction implements SelectionObserver {
        private final TextEditor editor;

        private DeleteSelectionAction(TextEditor editor) {
            this.editor = editor;
            putValue(Action.NAME, "Delete selection");
            putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control Q"));
            setEnabled(false);
            editor.attachSelectionObs(this);
        }

        @Override
        public void selectionUpdated(SelectionState state) {
            setEnabled(state == SelectionState.NOT_EMPTY);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            editor.deleteSelection();
            editor.requestFocus();
        }
    }

    private static class ClearDocumentAction extends AbstractAction {
        private final TextEditor editor;

        private ClearDocumentAction(TextEditor editor) {
            this.editor = editor;
            putValue(Action.NAME, "Clear document");
            putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control L"));
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            editor.reset(new ArrayList<>());
        }
    }

    private static class CursorStartAction extends AbstractAction {
        private final TextEditor editor;

        private CursorStartAction(TextEditor editor) {
            this.editor = editor;
            putValue(Action.NAME, "Cursor to document start");
            putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_HOME, 0));
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            editor.pageStart();
        }
    }

    private static class CursorEndAction extends AbstractAction {
        private final TextEditor editor;

        private CursorEndAction(TextEditor editor) {
            this.editor = editor;
            putValue(Action.NAME, "Cursor to document end");
            putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_END, 0));
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            editor.pageEnd();
        }
    }

    private static class StatusLabel extends JLabel implements CursorObserver, TextEditorObserver {
        private final TextEditor editor;

        private StatusLabel(TextEditor editor) {
            this.editor = editor;
            editor.attach(this);
            editor.attachCursorObs(this);
        }

        @Override
        public void updateCursorLocation(Location location) {
            StringBuilder sb = new StringBuilder();
            sb.append("Line: ").append(location.getRow() + 1).append(", ");
            sb.append("Column: ").append(location.getColumn() + 2);
            setText(sb.toString());
            SwingUtilities.invokeLater(this::repaint);
        }

        @Override
        public void componentShown(Location location) {
            updateCursorLocation(location);
        }

    }

}
