package com.ktar5.proclib.mutators;

import com.ktar5.proclib.ProceduralData;
import com.ktar5.proclib.mutators.blob.Blob;
import com.ktar5.proclib.util.Direction;
import com.ktar5.proclib.util.Pair;

import java.util.*;

public class Connect {

    //TODO TEST
    public static void connect(ProceduralData proc, ConnectionType type, float connectivity, int parameter, List<Blob> blobs) {
        Map<Integer, Pair> randomPoints = new HashMap<>();

        for (int i = 0; i < blobs.size(); i++) {
            Blob blob = blobs.get(i);
            randomPoints.put(i, blob.pairs.get(ProceduralData.random.nextInt(blob.pairs.size())));
        }
        Set<Pair> connections = new HashSet<>();
        //not actually connections?
        Pair a, b, c = new Pair(0, 0);
        for (Map.Entry<Integer, Pair> entryA : randomPoints.entrySet()) {
            a = entryA.getValue();
            for (Map.Entry<Integer, Pair> entryB : randomPoints.entrySet()) {
                if (!entryB.getKey().equals(entryA.getKey())) {
                    if(!connections.contains(new Pair(entryA.getKey(), entryB.getKey()))){
                        boolean connected = true;
                        b = entryB.getValue();
                        for (Map.Entry<Integer, Pair> entryC : randomPoints.entrySet()) {
                            if (!entryC.getKey().equals(entryA.getKey()) && !entryC.getKey().equals(entryB.getKey())) { //Line 139, might need to change connection list
                                c = entryC.getValue();
                                if (distSquared(a, c) * connectivity < distSquared(a, b) && distSquared(b, c) * connectivity < distSquared(a, b)) {
                                    connected = false;
                                    connections.add(new Pair(entryA.getKey(), entryB.getKey()));
                                    connections.add(new Pair(entryB.getKey(), entryA.getKey()));
                                }
                            }
                        }
                        if (connected) {
                            connections.add(new Pair(entryA.getKey(), entryB.getKey()));
                            connections.add(new Pair(entryB.getKey(), entryA.getKey()));
                            if (type == ConnectionType.ELBOW) {
                                //TODO Ask about this
                                c = new Pair(ProceduralData.random.nextInt(2), c.y);
                                for (int i = Math.min(a.y, b.y); i <= Math.max(a.y, b.y); i++) {
                                    if (proc.data()[i][c.x == 0 ? a.x : b.x] == 0) {
                                        proc.data()[i][c.x == 0 ? a.x : b.x] = 1;
                                    }
                                }

                                for (int i = Math.min(a.x, b.x); i <= Math.max(a.x, b.x); i++) {
                                    if (proc.data()[c.x == 0 ? b.y : a.y][i] == 0) {
                                        proc.data()[c.x == 0 ? b.y : a.y][i] = 1;
                                    }
                                }
                            } else if (type == ConnectionType.TUNNEL) {
                                Pair tunneler = Pair.of(a.x, a.y);
                                List<Direction> directions = new ArrayList<>();
                                while (!tunneler.equals(b)) {
                                    directions.clear();
                                    if (tunneler.x < b.x + parameter && tunneler.x + 1 >= 0 && tunneler.x + 1 <= proc.getColumns() - 1) {
                                        directions.add(Direction.E);
                                    }
                                    if (tunneler.x > b.x - parameter && tunneler.x - 1 >= 0 && tunneler.x - 1 <= proc.getColumns() - 1) {
                                        directions.add(Direction.W);
                                    }
                                    if (tunneler.y > b.y - parameter && tunneler.y - 1 >= 0 && tunneler.y - 1 <= proc.getRows() - 1) {
                                        directions.add(Direction.S);
                                    }
                                    if (tunneler.y < b.y + parameter && tunneler.y + 1 >= 0 && tunneler.y + 1 <= proc.getRows() - 1) {
                                        directions.add(Direction.N);
                                    }
                                    Direction dir = directions.get(ProceduralData.random.nextInt(directions.size()));
                                    tunneler = new Pair(tunneler.x + dir.x, tunneler.y + dir.y);
                                    if(proc.isInRange(tunneler.y, tunneler.x)){
                                        proc.data()[tunneler.y][tunneler.x] = 1;
                                        try {
                                            Thread.sleep(1000);
                                        } catch (InterruptedException e) {
                                            e.printStackTrace();
                                        }
                                        proc.print(Display.PrintMethod.SYMBOLS);
                                        System.out.println();

                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public static int distSquared(Pair p1, Pair p2) {
        return ((p1.x - p2.x) * (p1.x - p2.x) + ((p1.y - p2.y) * (p1.y - p2.y)));
    }

    public enum ConnectionType {
        ELBOW,
        TUNNEL //requires N
    }

}
