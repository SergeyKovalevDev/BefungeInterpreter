import java.util.LinkedList;
import java.util.NoSuchElementException;

public class BefungeInterpreter {
    private char[][] field = new char[25][80];
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

    public String interpret(String code) {
        field = getCodesField(code);
        boolean stringMode = false;
        boolean endOfProgram = false;
        while (!endOfProgram) {
            char command = getCommand(position[lineIndex], position[columnIndex]);
            if (stringMode) {
                if (command != '"') pushASCII(command);
                else stringMode = false;
            } else {
                if (command >= '0' && command <= '9') {
                    stack.push(Character.getNumericValue(command));
                } else {
                    switch (command) {
                        case '+' -> addition();
                        case '-' -> subtraction();
                        case '*' -> multiplication();
                        case '/' -> integerDivision();
                        case '%' -> modulo();
                        case '!' -> logicalNOT();
                        case '`' -> greaterThan();
                        case '>' -> moveDirection = moveRight;
                        case '<' -> moveDirection = moveLeft;
                        case '^' -> moveDirection = moveUp;
                        case 'v' -> moveDirection = moveDown;
                        case '?' -> moveDirection = getRandomDirection();
                        case '_' -> moveDirection = popGorizontalDirection();
                        case '|' -> moveDirection = popVerticalDirection();
                        case '"' -> stringMode = true;
                        case ':' -> duplicateTopOfStack();
                        case '\\' -> swapTopOfStack();
                        case '$' -> discardTopOfStack();
                        case '.' -> outputAsInteger();
                        case ',' -> outputAsCharacter();
                        case '#' -> skipNextCell();
                        case 'p' -> putCall();
                        case 'g' -> getCall();
                        case '@' -> endOfProgram = true;
                    }
                }
            }
            stepForward();
        }

        return resultString.toString();
    }

    private void pushASCII(char command) {
        stack.push((int) command);
    }

    private void getCall() {
    }

    private void putCall() {
    }

    private void skipNextCell() {
        stepForward();
    }

    private void stepForward() {
        position[lineIndex] += moveDirection[lineIndex];
        position[columnIndex] += moveDirection[columnIndex];
    }

    private void outputAsCharacter() {
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
        return switch ((int) Math.round(Math.random() * 3.0)) {
            case 0 -> moveRight;
            case 1 -> moveLeft;
            case 2 -> moveDown;
            default -> moveUp;
        };
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
        try {
            stack.push(b / a);
        } catch (ArithmeticException e) {
            stack.push(0);
        }
    }

    private void modulo() {
        int a =  stack.pop();
        int b =  stack.pop();
        stack.push(b % a);
    }

    private void logicalNOT() {
        int a = stack.pop();
        stack.push(a == 1 ? 0 : 1);
    }

    public static void main(String[] args) {
        BefungeInterpreter b = new BefungeInterpreter();
        System.out.println(b.interpret(">987v>.v\nv456<  :\n>321 ^ _@"));
    }
}
