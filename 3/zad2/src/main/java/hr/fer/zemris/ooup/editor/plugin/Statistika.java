package hr.fer.zemris.ooup.editor.plugin;

import hr.fer.zemris.ooup.editor.model.ClipboardStack;
import hr.fer.zemris.ooup.editor.model.TextEditorModel;
import hr.fer.zemris.ooup.editor.singleton.UndoManager;

import javax.swing.*;

public class Statistika implements Plugin {

    private final String name;

    public Statistika(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDescription() {
        return "Broji koliko ima redaka, rijeci i slova u dokumentu.";
    }

    @Override
    public void execute(
            TextEditorModel model, UndoManager undoManager, ClipboardStack<String> clipboardStack) {
        int rows = 0;
        int words = 0;
        int letters = 0;
        for (String line : model.getLines()) {
            rows++;
            String[] parts = line.split("\\s+");
            words = parts.length;
            for (String part : parts) {
                for (char c : part.toCharArray()) {
                    if (Character.isLetter(c)) letters++;
                }
            }
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Broj redaka: ").append(rows).append(", ");
        sb.append("Broj rijeci: ").append(words).append(", ");
        sb.append("Broj slova: ").append(letters).append(", ");
        JOptionPane.showMessageDialog(null, sb.toString());
    }
}
