import java.util.LinkedList;
import java.util.NoSuchElementException;

public class BefungeInterpreter {
    private final int numOfLines = 25;
    private final int numOfColumns = 80;
    private char[][] field = new char[numOfLines][numOfColumns];
    LinkedList <Integer> stack = new LinkedList<>();
    private final int[] moveRight = new int[] {0, 1};
    private final int[] moveLeft = new int[] {0, -1};
    private final int[] moveUp = new int[] {-1, 0};
    private final int[] moveDown = new int[] {1, 0};
    private final StringBuilder resultString = new StringBuilder();
    private final int lineIndex = 0;
    private final int columnIndex = 1;
    int[] position = new int[] {0, 0};
    int[] moveDirection = moveRight;
    boolean stringMode = false;
    boolean endOfProgram = false;

    public String interpret(String code) {
        field = getCodesField(code);
        while (!endOfProgram) {
            char command = getCommand(position[lineIndex], position[columnIndex]);
            if (stringMode) {
                if (command != '"') pushAsASCII(command);
                else stringMode = false;
            } else {
                if (command >= '0' && command <= '9') {
                    stack.push(Character.getNumericValue(command));
                } else {
                    commandExecute(command);
                }
            }
            stepForward();
        }

        return resultString.toString();
    }

    private void commandExecute(char command) {
        switch (command) {
            case '+':
                addition();
                break;
            case '-':
                subtraction();
                break;
            case '*':
                multiplication();
                break;
            case '/':
                integerDivision();
                break;
            case '%':
                modulo();
                break;
            case '!':
                logicalNOT();
                break;
            case '`':
                greaterThan();
                break;
            case '>':
                moveDirection = moveRight;
                break;
            case '<':
                moveDirection = moveLeft;
                break;
            case '^':
                moveDirection = moveUp;
                break;
            case 'v':
                moveDirection = moveDown;
                break;
            case '?':
                moveDirection = getRandomDirection();
                break;
            case '_':
                moveDirection = popGorizontalDirection();
                break;
            case '|':
                moveDirection = popVerticalDirection();
                break;
            case '"':
                stringMode = true;
                break;
            case ':':
                duplicateTopOfStack();
                break;
            case '\\':
                swapTopOfStack();
                break;
            case '$':
                discardTopOfStack();
                break;
            case '.':
                outputAsInteger();
                break;
            case ',':
                outputAsASCII();
                break;
            case '#':
                skipNextCell();
                break;
            case 'p':
                putCall();
                break;
            case 'g':
                getCall();
                break;
            case '@':
                endOfProgram = true;
                break;
        }
    }

    private void pushAsASCII(char command) {
        stack.push((int) command);
    }

    private void getCall() {
        int line = stack.pop();
        int column = stack.pop();
        pushAsASCII(getCommand(line, column));
    }

    private void putCall() {
        int line = stack.pop();
        int column = stack.pop();
        int value = stack.pop();
        putCommand(line, column, (char) value);
    }

    private void skipNextCell() {
        stepForward();
    }

    private void stepForward() {
        position[lineIndex] += moveDirection[lineIndex];
        position[columnIndex] += moveDirection[columnIndex];
    }

    private void outputAsASCII() {
        resultString.append((char) ((int) stack.pop()));
    }

    private void outputAsInteger() {
        resultString.append(stack.pop());
    }

    private void discardTopOfStack() {
        stack.pop();
    }

    private void swapTopOfStack() {
        int a = stack.pop();
        int b;
        try {
            b = stack.pop();
            stack.push(a);
            stack.push(b);
        } catch (NoSuchElementException e) {
            stack.push(0);
        }
    }

    private void duplicateTopOfStack() {
        try {
            int a = stack.pop();
            stack.push(a);
            stack.push(a);
        } catch (NoSuchElementException e) {
            stack.push(0);
        }
    }

    private int[] popVerticalDirection() {
        return stack.pop() == 0 ? moveDown : moveUp;
    }

    private int[] popGorizontalDirection() {
        return stack.pop() == 0 ? moveRight : moveLeft;
    }

    private int[] getRandomDirection() {
        switch ((int) (Math.random() * 4)) {
            case 0:
                return moveRight;
            case 1:
                return moveLeft;
            case 2:
                return moveDown;
            default:
                return moveUp;
        }
    }

    private void greaterThan() {
        int a = stack.pop();
        int b = stack.pop();
        stack.push(b > a ? 1 : 0);
    }

    private char[][] getCodesField(String code) {
        String[] splitedCode = code.split("\\n");
        for (int i = 0; i < splitedCode.length; i++) {
            for (int j = 0; j < splitedCode[i].length(); j++) {
                field[i][j] = splitedCode[i].charAt(j);
            }
        }
        return field;
    }

    private char getCommand(int line, int column) {
        return field[line][column];
    }

    private void putCommand(int line, int column, char command) {
        field[line][column] = command;
    }

    private void addition() {
        int a =  stack.pop();
        int b =  stack.pop();
        stack.push(a + b);
    }

    private void subtraction() {
        int a =  stack.pop();
        int b =  stack.pop();
        stack.push(b - a);
    }

    private void multiplication() {
        int a =  stack.pop();
        int b =  stack.pop();
        stack.push(a * b);
    }

    private void integerDivision() {
        int a =  stack.pop();
        int b =  stack.pop();
        stack.push(a == 0 ? 0 : b / a);
    }

    private void modulo() {
        int a =  stack.pop();
        int b =  stack.pop();
        stack.push(a == 0 ? 0 : b % a);
    }

    private void logicalNOT() {
        int a = stack.pop();
        stack.push(a == 0 ? 1 : 0);
    }
}
