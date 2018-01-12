package com.ktar5.proclib.mutators.blob;


import lombok.Getter;

@Getter
public class BlobData {
    private int[][] mask;
    private boolean overwriteGiven, maskThenFilter;
    private int parameter; //Note, for "above" selection
    private BlobFilter filter;
    private BlobRender render;

    public BlobData() {
        this(null, true, true, 0, BlobFilter.ALL, BlobRender.ONE);
    }

    private BlobData(int[][] mask, boolean overwriteGiven, boolean maskThenFilter, int parameter,
                     BlobFilter filter, BlobRender render) {
        this.mask = mask;
        this.overwriteGiven = overwriteGiven;
        this.maskThenFilter = maskThenFilter;
        this.parameter = parameter;
        this.filter = filter;
        this.render = render;
    }

    public BlobData filter(BlobFilter filter) {
        if (filter == BlobFilter.ABOVE) {
            throw new RuntimeException("You must use BlobData.filter(BlobFilter, int) for the 'ABOVE' blob filter type");
        }
        this.filter = filter;
        return this;
    }

    public BlobData filter(BlobFilter filter, int parameter) {
        if (parameter < 2 && filter == BlobFilter.ABOVE) {
            throw new RuntimeException("You must specify a larger filter for the 'ABOVE' blob filter type");
        }
        this.filter = filter;
        this.parameter = parameter;
        return this;
    }

    public BlobData overwriteGiven(boolean overwriteGiven) {
        this.overwriteGiven = overwriteGiven;
        return this;
    }

    public BlobData maskThenFilter(boolean maskThenFilter) {
        this.maskThenFilter = maskThenFilter;
        return this;
    }

    public BlobData render(BlobRender render){
        this.render = render;
        return this;
    }

    public BlobData mask(int[][] mask) {
        this.mask = mask;
        return this;
    }

    public enum BlobRender {
        DIJKSTRA_MAP,
        BLOB_ID,
        ONE
    }

    public enum BlobFilter {
        LARGEST,
        ABOVE,
        ALL
    }

}
