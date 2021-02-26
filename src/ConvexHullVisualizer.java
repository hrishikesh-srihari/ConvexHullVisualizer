import java.awt.*;
import javax.swing.*;
import java.util.*;

public class ConvexHullVisualizer {
    private static int width = 700;
    private static int height = 700;

    static class CHull extends JComponent {
        int del;
        int rad;
        boolean run;

        Set<Point> p;
        LinkedList<Point> search;
        LinkedList<Point> hull;

        CHull() {
            Random rand = new Random();
            this.p = new HashSet<>();

            //Creates 10 equally distributed points on the plane
            while (this.p.size() < 15) {
                int x = ConvexHullVisualizer.width/20 + rand.nextInt(ConvexHullVisualizer.width - ConvexHullVisualizer.width/10);
                int y = ConvexHullVisualizer.height/20 + rand.nextInt(ConvexHullVisualizer.height - ConvexHullVisualizer.height/10);
            }

            this.search = new LinkedList<>(p);
            this.hull = new LinkedList<>();

            this.run = false;
            this.del = 1000; //Delay is 1000 ms
            this.rad = 15; //Radius is 15
        }

    }

    public static void main(String args[]) {

    }
}
