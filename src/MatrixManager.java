import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class MatrixManager {

    private double[][] matrix;
    private int size;
    private double[] b;
    private double e;


    public MatrixManager() {
        this.size = 0;
    }


    public boolean isDiagonallyDominant() {
        if (matrix == null || size == 0) {
            System.out.println("Matrix is not initialized.");
            return false;
        }

        boolean isDominant = true;

        for (int i = 0; i < size; i++) {
            double diagonalValue = Math.abs(matrix[i][i]);
            double sumOfOffDiagonals = 0.0;

            for (int j = 0; j < size; j++) {
                if (j != i) {
                    sumOfOffDiagonals += Math.abs(matrix[i][j]);
                }
            }

            if (diagonalValue <= sumOfOffDiagonals) {
                isDominant = false;
                break;
            }
        }

        return isDominant;
    }


    public void makeDiagonallyDominant() {
        if (matrix == null || size == 0) {
            System.out.println("Matrix is not initialized.");
            return;
        }

        if (isDiagonallyDominant()) {
            System.out.println("Matrix is already diagonally dominant.");
            return;
        }

        System.out.println("Attempting to make the matrix diagonally dominant...");

        double[][] temp = matrix;

        List<Integer> columnIndices = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            columnIndices.add(i);
        }

        List<List<Integer>> permutations = new ArrayList<>();
        generatePermutations(columnIndices, 0, permutations);

        for (List<Integer> permutation : permutations) {
            if (tryPermutation(permutation)) {
                System.out.println("Matrix is now diagonally dominant after rearranging columns.");
                return;
            }
        }

        matrix = temp;

        System.out.println("Unable to make the matrix diagonally dominant by rearranging columns.");
    }

    private void generatePermutations(List<Integer> columns, int start, List<List<Integer>> result) {
        if (start == columns.size() - 1) {
            result.add(new ArrayList<>(columns));
        } else {
            for (int i = start; i < columns.size(); i++) {
                Collections.swap(columns, start, i);
                generatePermutations(columns, start + 1, result);
                Collections.swap(columns, start, i);
            }
        }
    }

    private boolean tryPermutation(List<Integer> permutation) {
        double[][] tempMatrix = new double[size][size];

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                tempMatrix[i][j] = matrix[i][permutation.get(j)];
            }
        }
        matrix = tempMatrix;
        return isDiagonallyDominant();
    }


    public void readMatrixFromFile(String fileName) {
        try {
            Scanner fileScanner = new Scanner(new File(fileName));
            readMatrixFromScanner(fileScanner, false);
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + fileName);
        }
    }


    public void readMatrixFromInput() {
        Scanner scanner = new Scanner(System.in);
        readMatrixFromScanner(scanner, true);
    }


    private void readMatrixFromScanner(Scanner scanner, boolean mesg) {
        try {
            if (mesg) {
                System.out.print("Enter the preciseness E: ");
            }
            e = readNextDouble(scanner);

            if (mesg) {
                System.out.print("Enter size of the square matrix (<=20): ");
            }
            size = scanner.nextInt();
            scanner.nextLine();
        } catch (Exception e) {
            System.out.println("Invalid input, try again");
            return;
        }


        if (size > 20 || size < 1) {
            System.out.println("Invalid matrix size. Size must be between 1 and 20.");
            return;
        }

        matrix = new double[size][size];
        b = new double[size];

        if (mesg) {
            System.out.println("Enter the elements of the matrix:");
        }
        try {
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    if (mesg) {
                        System.out.print((i + 1) + "_" + (j + 1) + ": ");
                    }
                    matrix[i][j] = readNextDouble(scanner);
                    ;
                }
            }

            if (mesg) {
                System.out.println("Enter the elements of the 'b' vector:");
            }
            for (int i = 0; i < size; i++) {
                if (mesg) {
                    System.out.print((i + 1) + ": ");
                }
                b[i] = readNextDouble(scanner);
            }

        } catch (Exception e) {
            System.out.println("Invalid input, try again");
            return;
        }
        System.out.println("Matrix is successfully created");
    }


    private double readNextDouble(Scanner scanner) throws Exception {
        String token = scanner.next();
        try {
            return Double.parseDouble(token);
        } catch (NumberFormatException e) {
            try {
                return Double.parseDouble(token.replace(',', '.'));
            } catch (NumberFormatException ex) {
                throw new Exception();
            }
        }
    }


    public void generateRandomMatrix() {
        System.out.println("Enter the integer size of the matrix (<=20)");
        Scanner scanner = new Scanner(System.in);
        int size = scanner.nextInt();

        if (size <= 0 || size > 20) {
            System.out.println("Invalid matrix size. Size must be between 1 and 20.");
            return;
        }

        this.size = size;
        matrix = new double[size][size];
        b = new double[size];
        Random random = new Random();

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                matrix[i][j] = random.nextDouble() * 100;
            }
        }

        for (int i = 0; i < size; i++) {
            b[i] = random.nextDouble() * 100;
        }

        e = 0.01;


        System.out.println("Random " + size + "x" + size + " matrix generated.");
    }


    public double[][] getMatrix() {
        return matrix;
    }

    public int getSize() {
        return size;
    }

    public double[] getB() {
        return b;
    }

    public double getE() {
        return e;
    }
}