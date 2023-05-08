import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class TileFiller {
    private static int CURRENT_VALUE = 1;

    public static List<List<Integer>> solveTileProblem(int n, int iMissing, int jMissing) throws Exception {
        List<List<Integer>> matrix = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            List<Integer> line = new ArrayList<>();
            for (int j = 0; j < n; j++) {
                line.add(0);
            }
            matrix.add(line);
        }
        List<List<Integer>> solvedMatrix = fillMatrix(matrix, new ArrayList<>(Arrays.asList(iMissing, jMissing)));
        Counter.reset();
        return solvedMatrix;
    }

    private static List<List<Integer>> fillMatrix(List<List<Integer>> matrix, List<Integer> missingPosition) throws Exception {
        int n = matrix.size();
        List<Integer> realMissingPosition = processRealMissing(n, missingPosition);

        if (n == 2) {
            return fillBaseCase(realMissingPosition);
        }

        MissingQuadrants missing = getMissingPositions(n, realMissingPosition.get(0), realMissingPosition.get(1));

        List<List<Integer>> q1 = fillMatrix(getSubMatrix(matrix, SubMatrix.Q1), missing.getQ1() != null ? missing.getQ1() : realMissingPosition);
        List<List<Integer>> q2 = fillMatrix(getSubMatrix(matrix, SubMatrix.Q2), missing.getQ2() != null ? missing.getQ2() : realMissingPosition);
        List<List<Integer>> q3 = fillMatrix(getSubMatrix(matrix, SubMatrix.Q3), missing.getQ3() != null ? missing.getQ3() : realMissingPosition);
        List<List<Integer>> q4 = fillMatrix(getSubMatrix(matrix, SubMatrix.Q4), missing.getQ4() != null ? missing.getQ4() : realMissingPosition);

        List<List<Integer>> filledMatrix = mergeMatrixes(q1, q2, q3, q4);

        List<List<Integer>> updatedMatrix = new ArrayList<>(filledMatrix);
        for (int i = 0; i < filledMatrix.size(); i++) {
            updatedMatrix.set(i, new ArrayList<>(filledMatrix.get(i)));
        }

        if (missing.getQ1() != null) {
            updatedMatrix.get(missing.getQ1().get(0)).set(missing.getQ1().get(1), CURRENT_VALUE);
        }
        if (missing.getQ2() != null) {
            updatedMatrix.get(missing.getQ2().get(0)).set(missing.getQ2().get(1), CURRENT_VALUE);
        }
        if (missing.getQ3() != null) {
            updatedMatrix.get(missing.getQ3().get(0)).set(missing.getQ3().get(1), CURRENT_VALUE);
        }
        if (missing.getQ4() != null) {
            updatedMatrix.get(missing.getQ4().get(0)).set(missing.getQ4().get(1), CURRENT_VALUE);
        }
        CURRENT_VALUE++;

        return updatedMatrix;
    }


    private static List<List<Integer>> mergeMatrixes(List<List<Integer>> q1, List<List<Integer>> q2, List<List<Integer>> q3, List<List<Integer>> q4) {
        int n = q1.size() * 2; // Tamanho total da matriz
        List<List<Integer>> mergedMatrix = new ArrayList<>();

        // Mesclar quadrantes Q1 e Q2
        for (int i = 0; i < q1.size(); i++) {
            List<Integer> row = new ArrayList<>(q1.get(i));
            row.addAll(q2.get(i));
            mergedMatrix.add(row);
        }

        // Mesclar quadrantes Q3 e Q4
        for (int i = 0; i < q3.size(); i++) {
            List<Integer> row = new ArrayList<>(q3.get(i));
            row.addAll(q4.get(i));
            mergedMatrix.add(row);
        }
        return mergedMatrix;
    }

    private static List<List<Integer>> getSubMatrix(List<List<Integer>> matrix, SubMatrix quadrant) throws Exception {
        Coordinates coordinates = new Coordinates(matrix.size());
        switch (quadrant) {
            case Q1:
                return buildSubMatrix(matrix, Arrays.asList(coordinates.getOuterCorners().getUpLeft(), coordinates.getInnerCorners().getUpLeft()));
            case Q2:
                List<Integer> startQ2 = Arrays.asList(0, coordinates.getInnerCorners().getUpRight().get(1));
                List<Integer> endQ2 = Arrays.asList(coordinates.getInnerCorners().getUpRight().get(0), coordinates.getOuterCorners().getUpRight().get(1));
                return buildSubMatrix(matrix, Arrays.asList(startQ2, endQ2));
            case Q3:
                List<Integer> startQ3 = Arrays.asList(coordinates.getInnerCorners().getBottomLeft().get(0), 0);
                List<Integer> endQ3 = Arrays.asList(coordinates.getOuterCorners().getBottomLeft().get(0), coordinates.getInnerCorners().getBottomLeft().get(1));
                return buildSubMatrix(matrix, Arrays.asList(startQ3, endQ3));
            case Q4:
                return buildSubMatrix(matrix, Arrays.asList(coordinates.getInnerCorners().getBottomRight(), coordinates.getOuterCorners().getBottomRight()));
            default:
                throw new Exception("Quadrant doesn't exist.");
        }
    }

    private static List<List<Integer>> buildSubMatrix(List<List<Integer>> matrix, List<List<Integer>> coordinates) {
        List<List<Integer>> subMatrix = new ArrayList<>();
        int iStart = coordinates.get(0).get(0);
        int jStart = coordinates.get(0).get(1);
        int iEnd = coordinates.get(1).get(0);
        int jEnd = coordinates.get(1).get(1);
        for (int i = iStart; i <= iEnd; i++) {
            List<Integer> line = new ArrayList<>();
            for (int j = jStart; j <= jEnd; j++) {
                line.add(matrix.get(i).get(j));
            }
            subMatrix.add(line);
        }
        return subMatrix;
    }

    private static MissingQuadrants getMissingPositions(int n, int iMiss, int jMiss) {
        Coordinates coordinates = new Coordinates(n);
        MissingQuadrants result = new MissingQuadrants(
                coordinates.getInnerCorners().getUpLeft(),
                coordinates.getInnerCorners().getUpRight(),
                coordinates.getInnerCorners().getBottomLeft(),
                coordinates.getInnerCorners().getBottomRight()
        );
        if (iMiss < n / 2) {
            if (jMiss < n / 2) {
                result.setQ1(null);
            } else {
                result.setQ2(null);
            }
        } else {
            if (jMiss < n / 2) {
                result.setQ3(null);
            } else {
                result.setQ4(null);
            }
        }
        return result;
    }


    private static List<List<Integer>> fillBaseCase(List<Integer> missing) {
        int n = missing.size();
        int value = CURRENT_VALUE;

        List<List<Integer>> matrix = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            List<Integer> row = new ArrayList<>();
            for (int j = 0; j < n; j++) {
                if (i == missing.get(0) && j == missing.get(1)) {
                    row.add(-1);  // Valor para a posição ausente
                } else {
                    row.add(value);  // Valor para as outras posições
                }
            }
            matrix.add(row);
        }

        return matrix;
    }

    private static List<Integer> processRealMissing(int n, List<Integer> missing) {

        // Deep copy original
        List<Integer> clone = new ArrayList<>(missing);
        while (clone.get(0) >= n) {
            clone.set(0, clone.get(0) - n);
        }
        while (clone.get(1) >= n) {
            clone.set(1, clone.get(1) - n);
        }
        return clone;
    }
}
