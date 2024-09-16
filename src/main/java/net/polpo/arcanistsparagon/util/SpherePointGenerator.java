package net.polpo.arcanistsparagon.util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SpherePointGenerator {

    private static final Random RANDOM = new Random();

    public static List<Point> generatePoints(double centerX, double centerY, double centerZ, double radius, int numberOfSamples) {
        List<Point> points = new ArrayList<>();

        for (int i = 0; i < numberOfSamples; i++) {
            double theta = RANDOM.nextDouble() * 2 * Math.PI;
            double phi = RANDOM.nextDouble() * Math.PI;
            double r = RANDOM.nextDouble() * radius; // Random radius within the sphere

            double x = centerX + r * Math.sin(phi) * Math.cos(theta);
            double y = centerY + r * Math.sin(phi) * Math.sin(theta);
            double z = centerZ + r * Math.cos(phi);
            points.add(new Point(x, y, z));
        }

        return points;
    }

    public static List<Vec3d> generatePointsVec3ds(double centerX, double centerY, double centerZ, double radius, int numberOfSamples) {
        List<Vec3d> points = new ArrayList<>();

        for (int i = 0; i < numberOfSamples; i++) {
            double theta = RANDOM.nextDouble() * 2 * Math.PI;
            double phi = RANDOM.nextDouble() * Math.PI;
            double r = RANDOM.nextDouble() * radius; // Random radius within the sphere

            double x = centerX + r * Math.sin(phi) * Math.cos(theta);
            double y = centerY + r * Math.sin(phi) * Math.sin(theta);
            double z = centerZ + r * Math.cos(phi);
            points.add(new Vec3d(x, y, z));
        }

        return points;
    }

    public static class Point {
        public final double x;
        public final double y;
        public final double z;

        public Point(double x, double y, double z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }

        @Override
        public String toString() {
            return String.format("Point{x=%f, y=%f, z=%f}", x, y, z);
        }
    }

    public static void main(String[] args) {
        List<Point> points = generatePoints(0, 0, 0, 10, 100);
        for (Point point : points) {
            System.out.println(point);
        }
    }
}