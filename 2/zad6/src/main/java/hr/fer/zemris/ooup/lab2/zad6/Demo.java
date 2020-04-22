package hr.fer.zemris.ooup.lab2.zad6;

public class Demo {

    public static void main(String[] args) {
        Sheet s = new Sheet(5, 5);

        s.set("A1", "2");
        s.set("A2", "5");
        s.set("A3", "A1+A2");
        s.set("B1", "A1+A3");
        s.set("E2", "A2+B1");
        System.out.println(s);

        System.out.println("----------------------");

        s.set("A1", "4");
        s.set("A4", "A1+A3");
        System.out.println(s);

        System.out.println("----------------------");

        try {
            s.set("A1", "A3");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("----------------------");
        }

        try {
            s.set("A3", "E2");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("----------------------");
        }

        s.set("E2", "10");
        s.set("A2", "6");
        System.out.println(s);
    }

}
