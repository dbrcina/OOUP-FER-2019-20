package hr.fer.zemris.ooup.editor.demo;

import hr.fer.zemris.ooup.editor.JNotepad;

import javax.swing.*;

public class Demo {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new JNotepad().setVisible(true));
    }

}
