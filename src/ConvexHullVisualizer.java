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

        void run() throws InterruptedException {
            this.hull.push(this.getLow());
            this.search.remove(this.getLow());
            this.search.sort(Comparator.comparingDouble(this::getAngle));
            this.run = true;
            Thread.sleep(this.del);
            this.repaint();

            while (this.search.size() > 0) {
                Point top;
                Point nextToTop;
                Point p = this.search.pop();
                do {
                    top = this.hull.pop();
                    nextToTop = this.hull.peek();
                } while (nextToTop != null && this.getCrossProd(nextToTop, top, p) < 0);
                this.hull.push(top);
                this.hull.push(p);

                Thread.sleep(this.del);
                this.repaint();
            }
        }

        void drawPoint (Graphics g, Color c, Point p) {
            g.setColor(c);
            g.fillOval(p.x, this.getHeight() - p.y, this.rad, this.rad);
        }

        // Draws the line between p1 and p2.
        void drawLine (Graphics g, Color c, Point p1, Point p2) {
            g.setColor(Color.BLACK);
            g.drawLine(p1.x, this.getHeight() - p1.y, p2.x, this.getHeight() - p2.y);
        }

        @Override
        public void paint(Graphics g) {
            // Draw all points to ensure that non-hull points are drawn
            for (Point p : this.p) {
                this.drawPoint(g, Color.BLACK, p);
            }

            // Draw every point in the intermediate hull
            Point last = null;
            for (Point p : this.hull) {
                if (last != null) {
                    drawLine(g, Color.BLACK, p, last);
                }
                this.drawPoint(g, Color.GREEN, p);
                last = p;
            }

            if (this.search.isEmpty()) {
                // Draw final line to connect cycle
                if (last != null && !this.hull.isEmpty()) {
                    drawLine(g, Color.BLACK, this.hull.peekFirst(), last);
                }
            } else if (this.run) {
                // Pick the next point to look at
                drawPoint(g, Color.ORANGE, this.search.peek());
            }
        }
    }

    public static void main(String args[]) {

    }
}
