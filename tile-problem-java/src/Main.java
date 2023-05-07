import java.util.List;
import java.util.Scanner;

public class Main {
    public static void printMatrix(List<List<Integer>> matrix) {
        int rows = matrix.size();
        int cols = matrix.get(0).size();

        int[][] array = new int[rows][cols];
        for (int i = 0; i < rows; i++) {
            List<Integer> row = matrix.get(i);
            for (int j = 0; j < cols; j++) {
                array[i][j] = row.get(j);
            }
        }

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                System.out.print(array[i][j] + " ");
            }
            System.out.println();
        }
    }

    public static boolean isPowerOfTwo(int number) {
        if (number <= 0) return false;
        while (number > 1) {
            if (number % 2 != 0) return false;
            number /= 2;
        }
        return true;
    }
    public static void main(String[] args) throws Exception {
        Scanner in = new Scanner(System.in);
        Integer board = 0;
        boolean valid = false;
        while (!valid) {
            System.out.print("Entre o valor de uma potência de dois para criar o tabuleiro: ");
            board = in.nextInt();
            if (isPowerOfTwo(board) == false) System.out.println(board + " Não é uma potência de dois.");
            valid = isPowerOfTwo(board);
        }
        System.out.print("Entre o valor x e para a posição inativa: ");
        Integer x = in.nextInt();
        System.out.print("Entre o valor de y para a posição inativa: ");
        Integer y = in.nextInt();
        printMatrix(TileFiller.solveTileProblem(board, x, y));
    }
}