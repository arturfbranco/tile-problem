import java.util.List;

public class MissingQuadrants {
    private List<Integer> q1;
    private List<Integer> q2;
    private List<Integer> q3;
    private List<Integer> q4;

    public MissingQuadrants(List<Integer> q1, List<Integer> q2, List<Integer> q3, List<Integer> q4) {
        this.q1 = q1;
        this.q2 = q2;
        this.q3 = q3;
        this.q4 = q4;
    }

    public List<Integer> getQ1() {
        return q1;
    }

    public void setQ1(List<Integer> q1) {
        this.q1 = q1;
    }

    public List<Integer> getQ2() {
        return q2;
    }

    public void setQ2(List<Integer> q2) {
        this.q2 = q2;
    }

    public List<Integer> getQ3() {
        return q3;
    }

    public void setQ3(List<Integer> q3) {
        this.q3 = q3;
    }

    public List<Integer> getQ4() {
        return q4;
    }

    public void setQ4(List<Integer> q4) {
        this.q4 = q4;
    }
}
