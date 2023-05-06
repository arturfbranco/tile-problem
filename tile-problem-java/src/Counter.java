public class Counter {
    private static Integer value = 1;
    public static Integer next(){
        return value++;
    }

    public static void reset(){
        value = 1;
    }
}
