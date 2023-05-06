import java.util.List;

public class Coordinates {

    private final OuterCorners outerCorners;
    private final InnerCorners innerCorners;

    public Coordinates (Integer n){
        this.outerCorners = new OuterCorners(n);
        this.innerCorners = new InnerCorners(n);
    }

    public OuterCorners getOuterCorners() {
        return outerCorners;
    }

    public InnerCorners getInnerCorners() {
        return innerCorners;
    }
}
