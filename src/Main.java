import java.util.Arrays;
import java.util.Scanner;

public class Main {

    private static MatrixManager matrixManager = new MatrixManager();

    public static void main(String[] args) {
        CommandProcessor commandProcessor = new CommandProcessor();
        Scanner scanner = new Scanner(System.in);

        /*
         ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓
         ---------------------------------************************---------------------------------
         ---------------------------------**COMMANDS DECLARATION**---------------------------------
         ---------------------------------↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓---------------------------------
         ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓
         */

        commandProcessor.registerCommand("exit", new Command() {
            @Override
            public String getInfo() {
                return "Exits the program without saving.";
            }

            @Override
            public void execute() {
                System.out.println("Exiting the program...");
                System.exit(0);
            }
        });

        commandProcessor.registerCommand("help", new Command() {
            @Override
            public String getInfo() {
                return "Displays available commands.";
            }

            @Override
            public void execute() {
                commandProcessor.displayCommands();
            }
        });

        commandProcessor.registerCommand("show", new Command() {
            @Override
            public String getInfo() {
                return "Displays current matrix, vector 'b' and 'e' on the screen.";
            }

            @Override
            public void execute() {
                displayMatrix();
            }
        });

        commandProcessor.registerCommand("file", new Command() {
            @Override
            public String getInfo() {
                return "Allows to initialize matrix with file data. File consists of 'e' then 'n' then matrix 'a' and then vector 'b'";
            }

            @Override
            public void execute() {
                System.out.println("Enter the file name:");
                String filename = scanner.nextLine();
                matrixManager.readMatrixFromFile(filename);
            }
        });

        commandProcessor.registerCommand("random", new Command() {
            @Override
            public String getInfo() {
                return "Generates random matrix of given size";
            }

            @Override
            public void execute() {
                matrixManager.generateRandomMatrix();
            }
        });

        commandProcessor.registerCommand("input", new Command() {
            @Override
            public String getInfo() {
                return "Allows to initialize matrix with input from the user.";
            }

            @Override
            public void execute() {
                matrixManager.readMatrixFromInput();
            }
        });

        commandProcessor.registerCommand("check_dominant", new Command() {
            @Override
            public String getInfo() {
                return "Checks if the current matrix is diagonally dominant";
            }

            @Override
            public void execute() {
                if (matrixManager.isDiagonallyDominant()) {
                    System.out.println("The matrix is diagonally dominant");
                } else {
                    System.out.println("The matrix is NOT diagonally dominant");
                }
            }
        });

        commandProcessor.registerCommand("make_dominant", new Command() {
            @Override
            public String getInfo() {
                return "Tries to make the matrix diagonally dominant.";
            }

            @Override
            public void execute() {
                matrixManager.makeDiagonallyDominant();
            }
        });

        commandProcessor.registerCommand("solve", new Command() {
            @Override
            public String getInfo() {
                return "Tries to solve the system using the Gauss–Seidel method";
            }

            @Override
            public void execute() {
                if (!matrixManager.isDiagonallyDominant()) {
                    System.out.println("The matrix is currently not diagonally dominant, so we might not be able to solve it...");
                    System.out.println("Make sure to try command 'make_dominant' to get better results");
                };
                Scanner scanner = new Scanner(System.in);
                System.out.println("What is the allowed amount of iterations (int)?: ");
                int m = scanner.nextInt();
                int size = matrixManager.getSize();
                double[] xi = new double[size];
                Arrays.fill(xi, 0);
                double[] x = performAlgorithm(
                        matrixManager.getSize(),
                        matrixManager.getMatrix(),
                        matrixManager.getB(),
                        xi,
                        matrixManager.getE(),
                        m
                );
                if (x != null) {
                    System.out.println("Solution:");
                    for (int i = 0; i < size; i++) {
                        System.out.printf("%2s \t", x[i]);
                    }
                    System.out.println();
                }
            }
        });

        /*
         ↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑
         ---------------------------------↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑---------------------------------
         ---------------------------------**COMMANDS DECLARATION**---------------------------------
         ---------------------------------************************---------------------------------
         ↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑
         */

        System.out.print("Enter a command: (help)\n");

        while (true) {
            System.out.print(">>> ");
            String input = scanner.nextLine().trim().toLowerCase();
            commandProcessor.processInput(input);
        }
    }


    private static void displayMatrix() {
        double[][] matrix = matrixManager.getMatrix();
        double[] b = matrixManager.getB();
        int size = matrixManager.getSize();

        if (matrix == null || size == 0) {
            System.out.println("Matrix is not initialized.");
            return;
        }

        System.out.println("Current Matrix:");
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                System.out.printf("%2s \t", matrix[i][j]);
            }
            System.out.print("|\t" + b[i]);
            System.out.println();
        }
        System.out.println("e = " + matrixManager.getE());
    }

    private static double[] performAlgorithm(int n, double[][] a, double[] b, double[] x, double e, int m) {
        System.out.println("max|x_i(k) - x_i(k-1)|");
        int k = 1;
        while (k <= m) {
            double sig = 0;

            for (int i = 0; i < n; i++) {
                double s = 0;
                for (int j = 0; j < n; j++) {
                    if (j != i) {
                        s += a[i][j] * x[j];
                    }
                }


                double xx = (b[i] - s) / a[i][i];
                double d = Math.abs(xx - x[i]);

                if (d > sig) {
                    sig = d;
                }
                x[i] = xx;
            }

            /*for (int i = 0; i < n; i++) {
                System.out.printf("%2s \t", x[i]);
            }
            System.out.println();*/
            System.out.println(sig);
            if (sig < e) break;

            k += 1;

        }

        boolean nan = false;
        for (int i = 0; i < n; i++) {
            if (Double.isNaN(x[i])) {
                nan = true;
                break;
            }
        }

        if (k > m || nan) {
            System.out.println("Couldn't solve with this amount of allowed iterations");
            return null;
        } else {
            System.out.println("Solved with " + k + " iterations");
            return x;
        }
    }

}