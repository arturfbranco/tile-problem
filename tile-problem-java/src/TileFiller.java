import javax.swing.event.ListDataListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class TileFiller {

    public List<List<Integer>> solveTileProblem( Integer n, Integer iMissing, Integer jMissing ) throws Exception {
        List<List<Integer>> matrix = new ArrayList<>();
        for(int i = 0; i < n; i++){
            List<Integer> line = new ArrayList<>();
            for(int j = 0; j < n; j++){
                line.add(0);
            }
            matrix.add(line);
        }
        List<List<Integer>> solvedMatrix = this.fillMatrix(matrix, new ArrayList<>(Arrays.asList(iMissing, jMissing)));
        Counter.reset();
        return solvedMatrix;
    }

    private List<List<Integer>> fillMatrix(List<List<Integer>> matrix, List<Integer> missingPosition) throws Exception {
        Integer n = matrix.size();
        List<Integer> realMissingPosition = this.processRealMissing(n, missingPosition);
        if(n.equals(2)){
            return this.fillBaseCase(realMissingPosition);
        }
        MissingQuadrants newMissing = this.getMissingPositions(n, realMissingPosition.get(0), realMissingPosition.get(1));
        List<List<Integer>> q1 = this.fillMatrix(getSubMatrix(matrix, SubMatrix.Q1), newMissing.getQ1() != null ? newMissing.getQ1() : realMissingPosition);
        List<List<Integer>> q2 = this.fillMatrix(getSubMatrix(matrix, SubMatrix.Q2), newMissing.getQ2() != null ? newMissing.getQ2() : realMissingPosition);
        List<List<Integer>> q3 = this.fillMatrix(getSubMatrix(matrix, SubMatrix.Q3), newMissing.getQ3() != null ? newMissing.getQ3() : realMissingPosition);
        List<List<Integer>> q4 = this.fillMatrix(getSubMatrix(matrix, SubMatrix.Q4), newMissing.getQ4() != null ? newMissing.getQ4() : realMissingPosition);

        List<List<Integer>> filledMatrix = mergeMatrixes(q1, q2, q3, q4);
        Integer value = Counter.next();
        if(newMissing.getQ1() != null){
            filledMatrix.get(newMissing.getQ1().get(0)).set(newMissing.getQ1().get(1), value);
        }
        if(newMissing.getQ2() != null){
            filledMatrix.get(newMissing.getQ2().get(0)).set(newMissing.getQ2().get(1), value);
        }
        if(newMissing.getQ3() != null){
            filledMatrix.get(newMissing.getQ3().get(0)).set(newMissing.getQ3().get(1), value);
        }
        if(newMissing.getQ4() != null){
            filledMatrix.get(newMissing.getQ4().get(0)).set(newMissing.getQ4().get(1), value);
        }
        return filledMatrix;
    }


    private List<List<Integer>> mergeMatrixes(List<List<Integer>> q1, List<List<Integer>> q2, List<List<Integer>> q3, List<List<Integer>> q4){
        List<List<Integer>> upperMatrix = new ArrayList<>();
        for(int i = 0; i < q1.size(); i++){
            List<Integer> line = new ArrayList<>(q1.get(i));
            line.addAll(q2.get(i));
            upperMatrix.add(line);
        }
        List<List<Integer>> lowerMatrix = new ArrayList<>();
        for(int i = 0; i < q3.size(); i++){
            List<Integer> line = new ArrayList<>(q3.get(i));
            line.addAll(q4.get(i));
            lowerMatrix.add(line);
        }

        List<List<Integer>> mergedMatrix = new ArrayList<>();
        mergedMatrix.addAll(upperMatrix);
        mergedMatrix.addAll(lowerMatrix);
        return mergedMatrix;
    }


    private List<List<Integer>> getSubMatrix(List<List<Integer>> matrix, SubMatrix quadrant) throws Exception {
        Coordinates coordinates = new Coordinates(matrix.size());
        switch (quadrant){
            case Q1:
                return this.buildSubMatrix(matrix, new ArrayList<>(Arrays.asList(coordinates.getOuterCorners().getUpLeft(), coordinates.getInnerCorners().getUpLeft())));
            case Q2:
                List<Integer> startQ2 = new ArrayList<>(Arrays.asList(0, coordinates.getInnerCorners().getUpRight().get(1)));
                List<Integer> endQ2 = new ArrayList<>(Arrays.asList(coordinates.getInnerCorners().getUpRight().get(0), coordinates.getOuterCorners().getUpRight().get(1)));
                return this.buildSubMatrix(matrix, new ArrayList<>(Arrays.asList(startQ2, endQ2)));
            case Q3:
                List<Integer> startQ3 = new ArrayList<>(Arrays.asList(coordinates.getInnerCorners().getBottomLeft().get(0), 0));
                List<Integer> endQ3 = new ArrayList<>(Arrays.asList(coordinates.getOuterCorners().getBottomLeft().get(0), coordinates.getInnerCorners().getBottomLeft().get(1)));
                return this.buildSubMatrix(matrix, new ArrayList<>(Arrays.asList(startQ3, endQ3)));
            case Q4:
                return this.buildSubMatrix(matrix, new ArrayList<>(Arrays.asList(coordinates.getInnerCorners().getBottomRight(), coordinates.getOuterCorners().getBottomRight())));
            default:
                throw new Exception("Quadrant doesn't exist.");
        }
    }

    private List<List<Integer>> buildSubMatrix(List<List<Integer>> matrix, List<List<Integer>> coordinates){

        List<List<Integer>> subMatrix = new ArrayList<>();
        int iStart = coordinates.get(0).get(0);
        int jStart = coordinates.get(0).get(1);
        int iEnd = coordinates.get(1).get(0);
        int jEnd = coordinates.get(1).get(1);
        for(int i = iStart; i <= iEnd; i++){
            List<Integer> line = new ArrayList<>();
            for(int j = jStart; j <= jEnd; j++){
                line.add(matrix.get(i).get(j));
            }
            subMatrix.add(line);
        }
        return subMatrix;
    }
    private MissingQuadrants getMissingPositions(Integer n, Integer iMiss, Integer jMiss){
        Coordinates coordinates = new Coordinates(n);
        MissingQuadrants result = new MissingQuadrants(
                coordinates.getInnerCorners().getUpLeft(),
                coordinates.getInnerCorners().getUpRight(),
                coordinates.getInnerCorners().getBottomLeft(),
                coordinates.getInnerCorners().getBottomRight()
        );
        if(iMiss < n / 2){
            if(jMiss < n / 2){
                result.setQ1(null);
            } else {
                result.setQ2(null);
            }
        } else {
            if(jMiss < n / 2){
                result.setQ3(null);
            } else {
                result.setQ4(null);
            }
        }
        return result;
    }

    private List<List<Integer>> fillBaseCase(List<Integer> missing){
        Integer iMiss = missing.get(0);
        Integer jMiss = missing.get(1);
        Integer value = Counter.next();
        List<Integer> firstLine = new ArrayList<>();
        firstLine.add(!(iMiss.equals(0) && jMiss.equals(0)) ? value : -1);
        firstLine.add(!(iMiss.equals(0) && jMiss.equals(1)) ? value : -1);

        List<Integer> secondLine = new ArrayList<>();
        secondLine.add(!(iMiss.equals(1) && jMiss.equals(0)) ? value : -1);
        secondLine.add(!(iMiss.equals(1) && jMiss.equals(1)) ? value : -1);

        List<List<Integer>> matrix = new ArrayList<>();
        matrix.add(firstLine);
        matrix.add(secondLine);
        return matrix;
    }

    private List<Integer> processRealMissing(Integer n, List<Integer> missing){

        // Deep copy original
        List<Integer> clone = new ArrayList<>(missing);
        while (clone.get(0) >= n){
            clone.set(0, clone.get(0) - n);
        }
        while (clone.get(1) >= n){
            clone.set(1, clone.get(1) - n);
        }
        return clone;
    }
}
