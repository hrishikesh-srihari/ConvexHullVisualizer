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

        Point getLow() {
            if (this.hull.isEmpty()) {
                return Collections.min(this.search, (p1, p2) -> {
                   if (p1.y == p2.y) return Integer.compare(p1.x, p2.x);
                   else return Integer.compare(p1.y, p2.y);
                });
            }
            return this.hull.peekFirst();
        }

        double getAngle(Point p) {
            Point low = this.getLow();
            return Math.atan2(p.y - low.y, p.x - low.x);
        }

        double getCrossProd(Point p1, Point p2, Point p3) {
            return (p2.x - p1.x) * (p3.y - p1.y) - (p2.y - p1.y) * (p3.x - p1.x);
        }
    }

    public static void main(String args[]) {

    }
}
