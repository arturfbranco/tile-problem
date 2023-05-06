import java.util.ArrayList;
import java.util.List;

public class OuterCorners {
    private final List<Integer> upLeft = new ArrayList<>();
    private final List<Integer> upRight = new ArrayList<>();
    private final List<Integer> bottomLeft = new ArrayList<>();
    private final List<Integer> bottomRight = new ArrayList<>();

    public OuterCorners(Integer n){
       upLeft.add(0);
       upLeft.add(0);
       upRight.add(0);
       upRight.add(n - 1);
       bottomLeft.add(n-1);
       bottomLeft.add(0);
       bottomRight.add(n-1);
        bottomRight.add(n-1);
    }

    public List<Integer> getUpLeft() {
        return upLeft;
    }

    public List<Integer> getUpRight() {
        return upRight;
    }

    public List<Integer> getBottomLeft() {
        return bottomLeft;
    }

    public List<Integer> getBottomRight() {
        return bottomRight;
    }
}