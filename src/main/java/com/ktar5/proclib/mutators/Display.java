package com.ktar5.proclib.mutators;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.ktar5.proclib.ProceduralData;

import java.util.Arrays;

public class Display {

    //TODO fix
    public static Pixmap render(ProceduralData proc) {
        Pixmap pixmap = new Pixmap(proc.getColumns(), proc.getRows(), Pixmap.Format.RGB888);
        proc.forAll((row, col) -> {
            int value = proc.data()[row][col];
            if (value == 1) {
                pixmap.drawPixel(col, row, Color.argb8888(Color.WHITE));
            } else if (value == 0) {
                pixmap.drawPixel(col, row, Color.argb8888(Color.BLACK));
            } else {
                //TODO colors
                pixmap.drawPixel(col, row, Color.argb8888(Color.CORAL));
            }
        });
        return pixmap;
    }

    public static void print(ProceduralData proc, PrintMethod printMethod) {
        switch (printMethod) {
            case GENERIC:
                System.out.println(Arrays.deepToString(proc.data()));
                break;
            case SYMBOLS:
                for (int row = proc.data().length - 1; row >= 0; row--) {
                    for (int col = 0; col < proc.data()[0].length; col++) {
                        int num = proc.data()[row][col];
                        if (num == 1) {
                            System.out.print("O ");
                        } else {
                            System.out.print("- ");
                        }
                    }
                    System.out.println();
                }
                break;
            case VALUES:
                for (int row = proc.data().length - 1; row >= 0; row--) {
                    for (int col = 0; col < proc.data()[0].length; col++) {
                        int num = proc.data()[row][col];
                        if (num == 0) {
                            System.out.print("--  ");
                        } else {
                            System.out.print((num < 10 ? "0" : "") + proc.data()[row][col] + "  ");
                        }

                    }
                    System.out.println();
                }
                break;
        }
    }

    public enum PrintMethod {
        GENERIC,
        SYMBOLS,
        VALUES
    }

}
