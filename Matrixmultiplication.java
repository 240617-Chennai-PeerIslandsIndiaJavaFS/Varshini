import java.util.Scanner;
public class Matrixmultiplication {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int m1 = sc.nextInt();
        int n1 = sc.nextInt();
        int[][] matrixA = new int[m1][n1];
        for (int i = 0; i < m1; i++) {
            for (int j = 0; j < n1; j++) {
                matrixA[i][j] = sc.nextInt();
            }
        }
        int m2 = sc.nextInt();
        int n2 = sc.nextInt();
        int[][] matrixB = new int[m2][n2];
        for (int i = 0; i < m2; i++) {
            for (int j = 0; j < n2; j++) {
                matrixB[i][j] = sc.nextInt();
            }
        }
        if (n1 != m2) {
            return;
        }
        int[][] matrixC = new int[m1][n2];
        for (int i = 0; i < m1; i++) {
            for (int j = 0; j < n2; j++) {
                for (int k = 0; k < n1; k++) {
                    matrixC[i][j] += matrixA[i][k] * matrixB[k][j];
                }
            }
        }
        for (int i = 0; i < m1; i++) {
            for (int j = 0; j < n2; j++) {
                System.out.print(matrixC[i][j] + " ");
            }
            System.out.println();
        }
    }
}


