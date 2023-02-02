package battleship;

import java.util.Scanner;

public class Main {

    static void printGrid(char[][] array) {
        System.out.println("  1 2 3 4 5 6 7 8 9 10");
        char letter = 'A';
        for (int i = 0; i < array.length; i++) {
            System.out.print(letter + " ");
            letter++;
            for (int j = 0; j < array[0].length; j++) {
                if (j == 9) {
                    System.out.print(array[i][j] + "\n");
                } else {
                    System.out.print(array[i][j] + " ");
                }
            }
        }
    }

    static void printFogOfWar(char[][] array) {
        System.out.println("  1 2 3 4 5 6 7 8 9 10");
        char letter = 'A';
        for (int i = 0; i < array.length; i++) {
            System.out.print(letter + " ");
            letter++;
            for (int j = 0; j < array[0].length; j++) {
                if (j == 9) {
                    if (checkShip(array, i, j)) {
                        System.out.print("~" + "\n");
                    } else {
                        System.out.print(array[i][j] + "\n");
                    }
                } else {
                    if (checkShip(array, i, j)) {
                        System.out.print("~" + " ");
                    } else {
                        System.out.print(array[i][j] + " ");
                    }
                }
            }
        }
    }

    static void printUserGrid(char[][] array) {
        System.out.println("  1 2 3 4 5 6 7 8 9 10");
        char letter = 'A';
        for (int i = 0; i < array.length; i++) {
            System.out.print(letter + " ");
            letter++;
            for (int j = 0; j < array[0].length; j++) {
                if (j == 9) {
                    if (checkShip(array, i, j)) {
                        System.out.print("O" + "\n");
                    } else {
                        System.out.print(array[i][j] + "\n");
                    }
                } else {
                    if (checkShip(array, i, j)) {
                        System.out.print("O" + " ");
                    } else {
                        System.out.print(array[i][j] + " ");
                    }
                }
            }
        }
    }

    static void placeShipOnGrid(char[][] array, int row1, int row2, int column1, int column2, char shipLetter) {
        if (row1 == row2 & column2 > column1) {
            for (int i = column1; i < column2 + 1; i++) {
                array[row1][i] = shipLetter;
            }
        } else if (row1 == row2 & column1 > column2) {
            for (int i = column2; i < column1 + 1; i++) {
                array[row1][i] = shipLetter;
            }
        } else if (column1 == column2 & row2 > row1) {
            for (int i = row1; i < row2 + 1; i++) {
                array[i][column1] = shipLetter;
            }
        } else {
            for (int i = row2; i < row1 + 1; i++) {
                array[i][column1] = shipLetter;
            }
        }
    }

    static boolean isShipTooClose(char[][] array, int row1, int row2, int column1, int column2) {
        int[] rowNbr = new int[]{0, -1, 0, 1};
        int[] colNbr = new int[]{-1, 0, 1, 0};
        boolean isClose = false;
        for (int i = 0; i < 4; i++) {
            if (row1 == 0 && column1 == 0 || row2 == 0 && column2 == 0 && (i == 0 || i == 1)) {
                continue;
            } else if (row1 == 0 && column1 == 9 || row2 == 0 && column2 == 9 && (i == 2 || i == 1)) {
                continue;
            } else if (row1 == 9 && column1 == 0 || row2 == 9 && column2 == 0 && (i == 3 || i == 0)) {
                continue;
            } else if (row1 == 9 && column1 == 9 || row2 == 9 && column2 == 9 && (i == 2 || i == 3)) {
                continue;
            } else if (row1 == 0 || row2 == 0 && i == 1) {
                continue;
            } else if (row1 == 9 || row2 == 9 && i == 3) {
                continue;
            } else if (column1 == 0 || column2 == 0 && i == 0) {
                continue;
            } else if (column1 == 9 || column2 == 9 && i == 2) {
                continue;
            }
            isClose = array[row1 + rowNbr[i]][column1 + colNbr[i]] != '~' || array[row2 + rowNbr[i]][column2 + colNbr[i]] != '~';
            if (isClose) {
                break;
            }
        }
        return isClose;
    }

    static String[] inputCoordinates() {
        Scanner sc = new Scanner(System.in);
        String firstCoordinates = sc.next();
        String secondCoordinates = sc.next();
        return new String[]{firstCoordinates, secondCoordinates};
    }

    static int[] getCoordinates(String firstCoordinates, String secondCoordinates) {
        String set = "ABCDEFGHIJ";
        int row1, row2, column1, column2;
        row1 = set.indexOf(firstCoordinates.charAt(0));
        row2 = set.indexOf(secondCoordinates.charAt(0));
        if (firstCoordinates.length() == 3) {
            column1 = Integer.parseInt(firstCoordinates.substring(1, 3)) - 1;
        } else {
            column1 = Character.getNumericValue(firstCoordinates.charAt(1)) - 1;
        }
        if (secondCoordinates.length() == 3) {
            column2 = Integer.parseInt(secondCoordinates.substring(1, 3)) - 1;
        } else {
            column2 = Character.getNumericValue(secondCoordinates.charAt(1)) - 1;
        }
        return new int[]{row1, row2, column1, column2};
    }

    static boolean checkCoordinates(char[][] array, String ship, int shipLength, int row1, int row2, int column1, int column2) {
        if (Math.abs(row1 - row2) != shipLength - 1 && Math.abs(column1 - column2) != shipLength - 1) {
            System.out.printf("Error! Wrong length of the %s! Try again:\n", ship);
            return true;
        } else if (column1 != column2 && row1 != row2) {
            System.out.println("Error! Wrong ship location! Try again:");
            return true;
        } else if (isShipTooClose(array, row1, row2, column1, column2)) {
            System.out.println("Error! You placed it too close to another one. Try again:");
            return true;
        }
        return false;
    }

    static void createArray(char[][] array) {
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[0].length; j++) {
                array[i][j] = '~';
            }
        }
    }

    static void shipPlacement(char[][] array, String ship, int shipLength, char shipLetter) {
        String[] strCoord;
        int[] coord;
        System.out.printf("Enter the coordinates of the %s (%d cells):\n", ship, shipLength);
        strCoord = inputCoordinates();
        coord = getCoordinates(strCoord[0], strCoord[1]);
        while (checkCoordinates(array, ship, shipLength, coord[0], coord[1], coord[2], coord[3])) {
            strCoord = inputCoordinates();
            coord = getCoordinates(strCoord[0], strCoord[1]);
        }
        placeShipOnGrid(array, coord[0], coord[1], coord[2], coord[3], shipLetter);
        printUserGrid(array);
    }

    static String inputShotCoord() {
        Scanner sc = new Scanner(System.in);
        return sc.next();
    }

    static int[] getShotCoord(String coord) {
        String set = "ABCDEFGHIJ";
        int row = set.indexOf(coord.charAt(0));
        int col;
        if (coord.length() == 3) {
            col = Integer.parseInt(coord.substring(1, 3)) - 1;
        } else {
            col = Character.getNumericValue(coord.charAt(1)) - 1;
        }
        return new int[]{row, col};
    }

    static boolean checkShip(char[][] array, int coord1, int coord2) {
        return array[coord1][coord2] == 'A' || array[coord1][coord2] == 'B' || array[coord1][coord2] == 'S' ||
                array[coord1][coord2] == 'C' || array[coord1][coord2] == 'D';
    }

    static void promptEnterKey() {
        System.out.print("Press Enter and pass the move to another player");
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
        System.out.println("...");
    }

    static boolean isAllShipDestroyed(char[][] array) {
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[0].length; j++) {
                if (checkShip(array, i, j)) {
                    return false;
                }
            }
        }
        return true;
    }

    static void decreaseShipHealth(char[][] array, int[] shipHealthArr, int coord1, int coord2) {
        if (array[coord1][coord2] == 'A') {
            shipHealthArr[0] -= 1;
        } else if (array[coord1][coord2] == 'B') {
            shipHealthArr[1] -= 1;
        } else if (array[coord1][coord2] == 'S') {
            shipHealthArr[2] -= 1;
        } else if (array[coord1][coord2] == 'C') {
            shipHealthArr[3] -= 1;
        } else if (array[coord1][coord2] == 'D') {
            shipHealthArr[4] -= 1;
        }
    }

    static boolean checkShipHealth(int[] shipHealthArr) {
        for (int i = 0; i < 4; i++) {
            if (shipHealthArr[i] == 0) {
                shipHealthArr[i] = -1;
                return true;
            }
        }
        return false;
    }

    static void shipCase(char[][] array) {
        printGrid(array);
        String ship;
        char shipLetter;
        int shipLength;
        for (int i = 1; i < 6; i++) {
            switch (i) {
                case 1:
                    ship = "Aircraft Carrier";
                    shipLength = 5;
                    shipLetter = 'A';
                    shipPlacement(array, ship, shipLength, shipLetter);
                    break;
                case 2:
                    ship = "Battleship";
                    shipLength = 4;
                    shipLetter = 'B';
                    shipPlacement(array, ship, shipLength, shipLetter);
                    break;
                case 3:
                    ship = "Submarine";
                    shipLength = 3;
                    shipLetter = 'S';
                    shipPlacement(array, ship, shipLength, shipLetter);
                    break;
                case 4:
                    ship = "Cruiser";
                    shipLength = 3;
                    shipLetter = 'C';
                    shipPlacement(array, ship, shipLength, shipLetter);
                    break;
                case 5:
                    ship = "Destroyer";
                    shipLetter = 'D';
                    shipLength = 2;
                    shipPlacement(array, ship, shipLength, shipLetter);
                    break;
            }
        }
    }

    static void shotAShip(char[][] arrayPlayer, int[] shipsHealth) {
        String strCoord = inputShotCoord();
        int[] coord = getShotCoord(strCoord);
        if (coord[0] < 0 || coord[1] < 0 || coord[1] > 9) {
            System.out.println("Error! You entered the wrong coordinates! Try again:");
            strCoord = inputShotCoord();
            coord = getShotCoord(strCoord);
        }
        if (checkShip(arrayPlayer, coord[0], coord[1]) || arrayPlayer[coord[0]][coord[1]] == 'X') {
            decreaseShipHealth(arrayPlayer, shipsHealth, coord[0], coord[1]);
            arrayPlayer[coord[0]][coord[1]] = 'X';
            if (isAllShipDestroyed(arrayPlayer)) {
                System.out.println("You sank the last ship. You won. Congratulations!");
            } else if (checkShipHealth(shipsHealth)) {
                System.out.println("You sank a ship!");
            } else {
                System.out.println("You hit a ship!");
            }
        } else if (!checkShip(arrayPlayer, coord[0], coord[1])) {
            arrayPlayer[coord[0]][coord[1]] = 'M';
            System.out.println("You missed!");
        }
    }

    public static void main(String[] args) {
        char[][] arrayPlayer1 = new char[10][10];
        char[][] arrayPlayer2 = new char[10][10];
        createArray(arrayPlayer1);
        createArray(arrayPlayer2);
        for (int i = 1; i < 3; i++) {
            switch (i) {
                case 1:
                    System.out.println("Player 1, place your ships on the game field");
                    shipCase(arrayPlayer1);
                    promptEnterKey();
                    break;
                case 2:
                    System.out.println("Player 2, place your ships on the game field");
                    shipCase(arrayPlayer2);
                    promptEnterKey();
                    break;
            }
        }
        int[] shipsHealthPlayer1 = {5, 4, 3, 3, 2};
        int[] shipsHealthPlayer2 = {5, 4, 3, 3, 2};
        int turn = 1;
        while (!isAllShipDestroyed(arrayPlayer1) && !isAllShipDestroyed(arrayPlayer2)) {
            if (turn % 2 != 0) {
                printFogOfWar(arrayPlayer2);
                System.out.println("---------------------");
                printUserGrid(arrayPlayer1);
                System.out.println("Player 1, it's your turn:");
                shotAShip(arrayPlayer2, shipsHealthPlayer2);
                promptEnterKey();
            } else {
                printFogOfWar(arrayPlayer1);
                System.out.println("---------------------");
                printUserGrid(arrayPlayer2);
                System.out.println("Player 2, it's your turn:");
                shotAShip(arrayPlayer1, shipsHealthPlayer1);
                promptEnterKey();
            }
            turn++;
        }
    }
}