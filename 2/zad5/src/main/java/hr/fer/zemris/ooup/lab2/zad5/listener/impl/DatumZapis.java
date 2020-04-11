package hr.fer.zemris.ooup.lab2.zad5.listener.impl;

import hr.fer.zemris.ooup.lab2.zad5.SlijedBrojeva;
import hr.fer.zemris.ooup.lab2.zad5.listener.SlijedBrojevaListener;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.StringJoiner;

public class DatumZapis extends SlijedBrojevaListener {

    private static final SimpleDateFormat format = new SimpleDateFormat("[yyyy-MM-dd HH:mm:ss]");
    private static final String file = "./logs.txt";

    public DatumZapis(SlijedBrojeva slijedBrojeva) {
        super(slijedBrojeva);
    }

    @Override
    public void update() {
        Date now = Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant());
        StringJoiner sj = new StringJoiner(", ", "[", "]");
        slijedBrojeva.getIntegers().forEach(i -> sj.add(i.toString()));
        try (PrintWriter wr = new PrintWriter(new FileWriter(file, true))) {
            wr.println(format.format(now) + ": " + sj.toString());
        } catch (IOException ignored) {
        }
    }

}
