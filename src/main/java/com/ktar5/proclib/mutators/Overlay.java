package com.ktar5.proclib.mutators;

import com.ktar5.proclib.ProceduralData;
import com.ktar5.proclib.util.MathUtils;
import com.ktar5.proclib.util.Pair;

public class Overlay {
    public static void paint(ProceduralData proc, ProceduralData painter, int paint, Pair offset) {
        proc.forAll((row, col) -> {
            if (row + offset.y < painter.getRows() && col + offset.x < painter.getColumns()) {
                if (painter.data()[row + offset.y][col + offset.x] == 1) {
                    proc.data()[row][col] = paint;
                }
            }
        });
    }

    public static void overlay(ProceduralData proc, OverlayType overlayType, ProceduralData overlay, Pair offset) {
        proc.forAll((row, col) -> {
            if (row + offset.y < overlay.getRows() && col + offset.x < overlay.getColumns()) {
                switch (overlayType) {
                    case ADD:
                        proc.data()[row][col] = MathUtils.clamp(
                                overlay.data()[row + offset.y][col + offset.x] + proc.data()[row][col],
                                0, 1);
                        break;
                    case SUBTRACT:
                        proc.data()[row][col] = MathUtils.clamp(
                                overlay.data()[row + offset.y][col + offset.x] - proc.data()[row][col],
                                0, 4096);
                        break;
                    case DIFFERENCE:
                        proc.data()[row][col] = MathUtils.clamp(Math.abs(
                                overlay.data()[row + offset.y][col + offset.x] - proc.data()[row][col]),
                                0, 4096);
                        break;
                    case MULTIPLY:
                        proc.data()[row][col] = MathUtils.clamp(
                                overlay.data()[row + offset.y][col + offset.x] * proc.data()[row][col],
                                0, 4096);
                        break;
                    case EQUAL:
                        proc.data()[row][col] =
                                overlay.data()[row + offset.y][col + offset.x] == proc.data()[row][col] ? 1 : 0;
                        break;
                }
            }
        });

    }

    public enum OverlayType {
        ADD,
        SUBTRACT,
        DIFFERENCE,
        MULTIPLY,
        EQUAL
    }
}
