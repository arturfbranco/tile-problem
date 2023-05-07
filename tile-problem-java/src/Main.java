import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        System.out.println("Give us a N for matrix side size, and the coordinates for the missing position. All separeted by spaces:");
        Scanner scanner = new Scanner(System.in);
        String entry = scanner.nextLine();
        String[] arguments = entry.split(" ");
        Integer n = Integer.valueOf(arguments[0]);
        Integer iMissing = Integer.valueOf(arguments[1]);
        Integer jMissing = Integer.valueOf(arguments[2]);

        TileFiller tileFiller = new TileFiller();
        List<List<Integer>> finalBoard = tileFiller.solveTileProblem(n, iMissing, jMissing);
    }
}