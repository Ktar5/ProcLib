package com.ktar5.proclib.mutators.blob;

import com.ktar5.proclib.ProceduralData;
import com.ktar5.proclib.util.Direction;
import com.ktar5.proclib.util.Pair;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
public class Blob {
    public final List<Pair> pairs = new ArrayList<>();

    public int getBlobArea() {
        return pairs.size();
    }

    //TODO test heatmap & apply dirty fix?
    public static List<Blob> findBlobs(ProceduralData proc, BlobData blobData) {
        List<Blob> blobs = new ArrayList<>();
        Blob current;
        Pair temp;
        //Needed for optimizing only checking the exterior of the blob
        Set<Pair> nextSearch = new HashSet<>();
        Set<Pair> currentSearch = new HashSet<>();
        proc.refreshBuffer();
        for (int row = 0; row < proc.data().length; row++) {
            for (int col = 0; col < proc.data()[0].length; col++) {
                if (proc.buffer()[row][col] != 1)
                    continue;

                //Start new blob
                current = new Blob();
                temp = new Pair(col, row);
                current.pairs.add(temp);
                nextSearch.add(temp);

                //Begin searching
                do {
                    //Next search becomes the current search
                    currentSearch.clear();
                    currentSearch.addAll(nextSearch);
                    nextSearch.clear();
                    for (Pair pair : currentSearch) {
                        //Check all 4 sides of each pair in the current search so we can detect the whole blob
                        for (int side = 0; side < 4; side++) {
                            //N -> E -> S -> W
                            temp = new Pair(pair.x + Direction.CARDINAL[side].x, pair.y + Direction.CARDINAL[side].y);
                            if (!proc.isInRange(temp.y, temp.x))
                                continue;
                            if (!(proc.buffer()[temp.y][temp.x] != 0 && !current.pairs.contains(temp)))
                                continue;
                            current.pairs.add(temp);
                            nextSearch.add(temp);
                        }
                    }
                } while (!nextSearch.isEmpty());
                //End searching

                blobs.add(current);
                for (Pair pair : current.pairs) {
                    proc.buffer()[pair.y][pair.x] = 0;
                }
            }
        }
        //proc.print(Display.PrintMethod.VALUES);
        if (blobData.isMaskThenFilter()) {
            blobs = maskBlobs(blobData, blobs);
            blobs = filterBlobs(proc, blobData, blobs);
        } else {
            blobs = filterBlobs(proc, blobData, blobs);
            blobs = maskBlobs(blobData, blobs);
        }
        renderBlob(proc, blobData, blobs);

        if (blobData.isOverwriteGiven()) {
            proc.flushBuffer();
        } else {
            new ProceduralData(proc, proc.name + ".blob").store();
        }
        return blobs;
    }

    public static List<Blob> maskBlobs(BlobData data, List<Blob> blobs) {
        if (data.getMask() != null) {
            blobs.removeIf(blob -> {
                for (Pair pair : blob.pairs) {
                    if (data.getMask()[pair.y][pair.x] == 1) {
                        return false;
                    }
                }
                return true;
            });
        }
        return blobs;
    }

    public static List<Blob> filterBlobs(ProceduralData proc, BlobData data, List<Blob> blobs) {
        switch (data.getFilter()) {
            case LARGEST:
                int area = 0;
                Blob largest = null;
                for (Blob blob : blobs) {
                    if (blob.getBlobArea() > area) {
                        largest = blob;
                    }
                }
                blobs.clear();
                if (largest != null) {
                    blobs.add(largest);
                }
                break;
            case ABOVE:
                blobs.removeIf(blob -> blob.getBlobArea() <= data.getParameter());
                break;
            case ALL:
                //Do nothing.
                break;
        }
        return blobs;
    }

    public static void renderBlob(ProceduralData proc, BlobData data, List<Blob> blobs) {
        switch (data.getRender()) {
            case DIJKSTRA_MAP:
                proc.refreshBuffer();
                Set<Pair> allSearches = new HashSet<>();
                Set<Pair> nextSearch = new HashSet<>();
                Set<Pair> currentSearch = new HashSet<>();
                Pair temp;
                for (Blob blob : blobs) {
                    //Dijska map heatmap algorithm mapping -- start with random point
                    nextSearch.clear();
                    temp = blob.pairs.get(ProceduralData.random.nextInt(blob.pairs.size()));
                    nextSearch.add(temp);
                    allSearches.add(temp);
                    int i = 1;
                    do {
                        currentSearch.clear();
                        currentSearch.addAll(nextSearch);
                        nextSearch.clear();
                        for (Pair pair : currentSearch) {
                            for (int side = 0; side < 4; side++) {
                                temp = new Pair(pair.x + Direction.CARDINAL[side].x, pair.y + Direction.CARDINAL[side].y);
                                if (!proc.isInRange(temp.y, temp.x))
                                    continue;
                                if (!blob.pairs.contains(temp) || proc.buffer()[temp.y][temp.x] == 0 || allSearches.contains(temp))
                                    continue;
                                allSearches.add(temp);
                                nextSearch.add(temp);
                                proc.buffer()[temp.y][temp.x] = i;
                            }
                        }
                        i++;
                    } while (!nextSearch.isEmpty());
                }
                break;
            case BLOB_ID:
                proc.clearBuffer(0);
                int i = 3;
                for (Blob blob : blobs) {
                    for (Pair pair : blob.pairs) {
                        proc.buffer()[pair.y][pair.x] = i;
                    }
                    i++;
                }
                break;
            case ONE:
                for (Blob blob : blobs)
                    for (Pair pair : blob.pairs)
                        proc.buffer()[pair.y][pair.x] = 1;
                break;
        }

    }

}
