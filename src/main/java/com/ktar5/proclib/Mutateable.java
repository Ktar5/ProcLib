package com.ktar5.proclib;

import com.badlogic.gdx.graphics.Pixmap;
import com.ktar5.proclib.mutators.*;
import com.ktar5.proclib.mutators.Connect.ConnectionType;
import com.ktar5.proclib.mutators.Transform.Axis;
import com.ktar5.proclib.mutators.Transform.CircularDirection;
import com.ktar5.proclib.mutators.blob.Blob;
import com.ktar5.proclib.mutators.blob.BlobData;
import com.ktar5.proclib.mutators.smooth.Neighborhood;
import com.ktar5.proclib.mutators.smooth.Smooth;
import com.ktar5.proclib.util.Pair;

import java.util.List;

public abstract class Mutateable {
    //Generally an array of ones and zeroes
    //public abstract int[/*z*/][/*y*/][/*x*/] getData();
    public abstract ProceduralData getProc();

    public void rotateCounterClockwise() {
        Transform.rotate(getProc(), CircularDirection.COUNTER_CLOCKWISE);
    }

    public void rotateClockwise() {
        Transform.rotate(getProc(), CircularDirection.CLOCKWISE);
    }

    public void flipOnHorizontal() {
        Transform.flipOnAxis(getProc(), Axis.HORIZONTAL);
    }

    public void flipOnVertical() {
        Transform.flipOnAxis(getProc(), Axis.VERTICAL);
    }

    public void paint(ProceduralData painter, int paint, Pair offset) {
        Overlay.paint(getProc(), painter, paint, offset);
    }

    public void paint(ProceduralData painter, int paint) {
        this.paint(painter, paint, new Pair(0, 0));
    }

    public void overlay(ProceduralData overlay, Overlay.OverlayType overlayType, Pair pair) {
        Overlay.overlay(getProc(), overlayType, overlay, pair);
    }

    //TODO should overrite?....
    public void stretch(float scale) {
        Resize.stretch(getProc(), scale, scale);
    }

    public void stretch(float scaleX, float scaleY) {
        Resize.stretch(getProc(), scaleX, scaleY);
    }

    public void overlay(ProceduralData overlay, Overlay.OverlayType overlayType) {
        this.overlay(overlay, overlayType, new Pair(0, 0));
    }

    //Goes positive to negative
    //Right to left
    public void mirrorHorizontal() {
        Transform.mirror(getProc(), Axis.HORIZONTAL);
    }

    public void mirrorVertical() {
        Transform.mirror(getProc(), Axis.VERTICAL);
    }

    public void invert() {
        Transform.invert(getProc());
    }

    public void noise(float chance, int color) {
        Produce.noise(getProc(), chance, color);
    }

    public List<Blob> findBlobs(BlobData filter) {
        return Blob.findBlobs(getProc(), filter);
    }

    public List<Blob> findBlobs() {
        return Blob.findBlobs(getProc(), new BlobData());
    }

    public void connectTunnel(List<Blob> blobs, float connectivity, int parameter) {
        Connect.connect(getProc(), ConnectionType.TUNNEL, connectivity, parameter, blobs);
    }

    public void partition(Partition.PartitionShape shape, int maxArea, float shapeVariability, float shapePrintChance) {
        Partition.partition(getProc(), maxArea, shapeVariability, shapePrintChance, shape);
    }

    public void connectElbow(List<Blob> blobs, float connectivity) {
        Connect.connect(getProc(), ConnectionType.ELBOW, connectivity, 0, blobs);
    }

    public Pixmap render() {
        return Display.render(getProc());
    }

    public void smooth(Neighborhood neighborhood, int parameter, int iterations, int threshold) {
        Smooth.smooth(getProc(), neighborhood, parameter, iterations, threshold);
    }

    public void generate(Produce.GenerateMethod generateMethod) {
        Produce.generate(getProc(), generateMethod);
    }

    public void printSeparator() {
        System.out.println();
        System.out.println("----------------------------------------");
        System.out.println();
    }

    public void print(Display.PrintMethod printMethod) {
        Display.print(getProc(), printMethod);
    }

}
