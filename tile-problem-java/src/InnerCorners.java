import java.util.ArrayList;
import java.util.List;

public class InnerCorners {

    private final List<Integer> upLeft = new ArrayList<>();
    private final List<Integer> upRight = new ArrayList<>();
    private final List<Integer> bottomLeft = new ArrayList<>();
    private final List<Integer> bottomRight = new ArrayList<>();

    public InnerCorners(Integer n){
        upLeft.add(n/2 - 1);
        upLeft.add(n/2 - 1);
        upRight.add(n/2 - 1);
        upRight.add(n/2);
        bottomLeft.add(n/2);
        bottomLeft.add(n/2 - 1);
        bottomRight.add(n/2);
        bottomRight.add(n/2);
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
