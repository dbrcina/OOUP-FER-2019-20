package hr.fer.zemris.ooup.lab2.zad5;

import hr.fer.zemris.ooup.lab2.zad5.izvor.IzvorBrojeva;
import hr.fer.zemris.ooup.lab2.zad5.listener.SlijedBrojevaListener;

import java.util.Collection;
import java.util.LinkedList;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.function.Consumer;

public class SlijedBrojeva {

    private final Collection<Integer> integers = new LinkedList<>();
    private final Collection<SlijedBrojevaListener> listeners = new ConcurrentLinkedDeque<>();
    private final IzvorBrojeva source;

    public SlijedBrojeva(IzvorBrojeva source) {
        this.source = source;
    }

    public Collection<Integer> getIntegers() {
        return integers;
    }

    public boolean addListener(SlijedBrojevaListener l) {
        return listeners.add(l);
    }

    public boolean removeListener(SlijedBrojevaListener l) {
        return listeners.remove(l);
    }

    private void notifyListeners(Consumer<SlijedBrojevaListener> action) {
        listeners.forEach(action);
    }

    public void kreni() throws Exception {
        while (true) {
            int integer = source.nextInt();
            if (integer < 0) break;
            integers.add(integer);
            System.out.println(integers);
            notifyListeners(SlijedBrojevaListener::update);
            try {
                Thread.sleep(1_000);
            } catch (InterruptedException ignored) {
            }
        }
        source.close();
        notifyListeners(SlijedBrojevaListener::done);
    }

}
