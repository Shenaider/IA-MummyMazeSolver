package mummymaze;

import agent.Action;
import agent.State;
import gui.GameArea;
import utils.Coordinates;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

public class MummyMazeState extends State implements Cloneable {

    public static final int SIZE = 13;
    private char[][] matrix;
    private LinkedList<Coordinates> mumias = new LinkedList<>();
    private LinkedList<Coordinates> mumiasVermelhas = new LinkedList<>();
    private LinkedList<Coordinates> escorpioes = new LinkedList<>();
    private LinkedList<Coordinates> chaves = new LinkedList<>();
    private LinkedList<Coordinates> portas = new LinkedList<>();
    private LinkedList<Coordinates> armadilhas = new LinkedList<>();
    private Coordinates hero;
    private Coordinates exit;

    public char[][] getMatrix() {
        return matrix;
    }

    public MummyMazeState(char[][] matrix) {
        String state = "";
        this.matrix = new char[matrix.length][matrix.length];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                this.matrix[i][j] = matrix[i][j];
                switch (matrix[i][j]){
                    case 'H':
                        hero = new Coordinates(j,i);
                        break;
                    case 'S':
                        exit = new Coordinates(j,i);
                        break;
                    case 'M':
                        mumias.add(new Coordinates(j,i));
                        break;
                    case 'V':
                        mumiasVermelhas.add(new Coordinates(j,i));
                        break;
                    case 'A':
                        armadilhas.add(new Coordinates(j,i));
                        break;
                    case 'E':
                        escorpioes.add(new Coordinates(j,i));
                        break;
                    case 'C':
                        chaves.add(new Coordinates(j,i));
                        break;
                    case '=':
                    case '"':
                        portas.add(new Coordinates(j,i));
                        break;
                }
            }
        }
    }

    @Override
    public void executeAction(Action action) {
        action.execute(this);
        firePuzzleChanged(null);
    }

    public boolean canMoveUp() {
        return canMoveEntityUp(hero, true);
    }

    public boolean canMoveEntityUp(Coordinates entity, boolean hero){
        int line = entity.getLine();
        int column = entity.getColumn();
        if(line == 1){
            if(matrix[line-1][column] == 'S' && hero){
                return true;
            }
            return false;
        }
        return (matrix[line-2][column] == '.' && matrix[line-1][column] != '=' && matrix[line-1][column] != '-');
    }

    public boolean canMoveRight() {
        return canMoveEntityRight(hero, true);
    }

    public boolean canMoveEntityRight(Coordinates entity, boolean hero){
        int line = entity.getLine();
        int column = entity.getColumn();
        if(column == 11){
            if(matrix[line][column+1] == 'S' && hero){
                return true;
            }
            return false;
        }
        return (matrix[line][column+2] == '.' && matrix[line][column+1] != '"' && matrix[line][column+1] != '|');
    }

    public boolean canMoveDown() {
        return canMoveEntityDown(hero, true);
    }

    public boolean canMoveEntityDown(Coordinates entity, boolean hero){
        int line = entity.getLine();
        int column = entity.getColumn();
        if(line == 11){
            if(matrix[line+1][column] == 'S' && hero){
                return true;
            }
            return false;
        }
        return (matrix[line+2][column] == '.' && matrix[line+1][column] != '=' && matrix[line+1][column] != '-');
    }

    public boolean canMoveLeft() {
        return canMoveEntityLeft(hero, true);
    }

    public boolean canMoveEntityLeft(Coordinates entity, boolean hero){
        int line = entity.getLine();
        int column = entity.getColumn();
        if(column == 1){
            if(matrix[line][column-1] == 'S' && hero){
                return true;
            }
            return false;
        }
        return (matrix[line][column-2] == '.' && matrix[line][column-1] != '"' && matrix[line][column-1] != '|');
    }

    /*
     * In the next four methods we don't verify if the actions are valid.
     * This is done in method executeActions in class MummyMazeProblem.
     * Doing the verification in these methods would imply that a clone of the
     * state was created whether the operation could be executed or not.
     */
    public void moveUp() {
        int line = hero.getLine();
        int column = hero.getColumn();
        matrix[line-2][column] = 'H';
        matrix[line][column] = '.';
        hero.setLine(line-2);
        moveNPC("up");
    }

    public void moveRight() {
        int line = hero.getLine();
        int column = hero.getColumn();
        matrix[line][column+2] = 'H';
        matrix[line][column] = '.';
        hero.setColumn(column+2);
        moveNPC("right");
    }

    public void moveDown() {
        int line = hero.getLine();
        int column = hero.getColumn();
        matrix[line+2][column] = 'H';
        matrix[line][column] = '.';
        hero.setLine(line+2);
        moveNPC("down");
    }

    public void moveLeft() {
        int line = hero.getLine();
        int column = hero.getColumn();
        matrix[line][column-2] = 'H';
        matrix[line][column] = '.';
        hero.setColumn(column-2);
        moveNPC("left");
    }

    public void stand() {
        moveNPC("stand");
    }

    public void moveNPC(String direction){
        if(!mumias.isEmpty()){
            moveMumiaEscorpiao(direction, mumias);
            moveMumiaEscorpiao(direction, mumias);
        }
        if(!escorpioes.isEmpty()){
            moveMumiaEscorpiao(direction, escorpioes);
        }
        if(!mumiasVermelhas.isEmpty()){
            moveMumiaVermelha(direction);
            moveMumiaVermelha(direction);
        }
    }

    public void moveMumiaEscorpiao(String direction, LinkedList<Coordinates> npcs){
        for (Coordinates npc: npcs){
            int lineNpc = npc.getLine();
            int columnNpc = npc.getColumn();
            int lineHero = hero.getLine();
            int columnHero = hero.getColumn();
            switch(direction){
                case "up":
                    if(lineHero<lineNpc){
                        if(canMoveEntityUp(npc, false)){
                            matrix[lineNpc-2][columnNpc] = 'M';
                            matrix[lineNpc][columnNpc] = '.';
                            npc.setLine(lineNpc-2);
                        }
                    } else if(columnHero>columnNpc){
                        if(canMoveEntityRight(npc, false)){
                            matrix[lineNpc][columnNpc+2] = 'M';
                            matrix[lineNpc][columnNpc] = '.';
                            npc.setColumn(columnNpc+2);
                        }
                    } else if(columnHero<lineNpc){
                        if(canMoveEntityLeft(npc, false)){
                            matrix[lineNpc][columnNpc-2] = 'M';
                            matrix[lineNpc][columnNpc] = '.';
                            npc.setColumn(columnNpc-2);
                        }
                    }
                    break;
                case "down":
                    if(lineHero>lineNpc){
                        if(canMoveEntityDown(npc, false)){
                            matrix[lineNpc+2][columnNpc] = 'M';
                            matrix[lineNpc][columnNpc] = '.';
                            npc.setLine(lineNpc+2);
                        }
                    } else if(columnHero>columnNpc){
                        if(canMoveEntityRight(npc, false)){
                            matrix[lineNpc][columnNpc+2] = 'M';
                            matrix[lineNpc][columnNpc] = '.';
                            npc.setColumn(columnNpc+2);
                        }
                    } else if(columnHero<lineNpc){
                        if(canMoveEntityLeft(npc, false)){
                            matrix[lineNpc][columnNpc-2] = 'M';
                            matrix[lineNpc][columnNpc] = '.';
                            npc.setColumn(columnNpc-2);
                        }
                    }
                    break;
                case "left":
                    if(columnHero<lineNpc){
                        if(canMoveEntityLeft(npc, false)){
                            matrix[lineNpc][columnNpc-2] = 'M';
                            matrix[lineNpc][columnNpc] = '.';
                            npc.setColumn(columnNpc-2);
                        }
                    } else if(lineHero<lineNpc){
                        if(canMoveEntityUp(npc, false)){
                            matrix[lineNpc-2][columnNpc] = 'M';
                            matrix[lineNpc][columnNpc] = '.';
                            npc.setLine(lineNpc-2);
                        }
                    } else if(lineHero>lineNpc){
                        if(canMoveEntityDown(npc, false)){
                            matrix[lineNpc+2][columnNpc] = 'M';
                            matrix[lineNpc][columnNpc] = '.';
                            npc.setLine(lineNpc+2);
                        }
                    }
                    break;
                case "right":
                    if(columnHero>columnNpc){
                        if(canMoveEntityRight(npc, false)){
                            matrix[lineNpc][columnNpc+2] = 'M';
                            matrix[lineNpc][columnNpc] = '.';
                            npc.setColumn(columnNpc+2);
                        }
                    } else if(lineHero<lineNpc){
                        if(canMoveEntityUp(npc, false)){
                            matrix[lineNpc-2][columnNpc] = 'M';
                            matrix[lineNpc][columnNpc] = '.';
                            npc.setLine(lineNpc-2);
                        }
                    } else if(lineHero>lineNpc){
                        if(canMoveEntityDown(npc, false)){
                            matrix[lineNpc+2][columnNpc] = 'M';
                            matrix[lineNpc][columnNpc] = '.';
                            npc.setLine(lineNpc+2);
                        }
                    }
                    break;
                case "stand":
                    if(columnHero<columnNpc){
                        if(canMoveEntityLeft(npc, false)){
                            matrix[lineNpc][columnNpc-2] = 'M';
                            matrix[lineNpc][columnNpc] = '.';
                            npc.setColumn(columnNpc-2);
                        }
                    } else if(columnHero>columnNpc){
                        if(canMoveEntityRight(npc, false)){
                            matrix[lineNpc][columnNpc+2] = 'M';
                            matrix[lineNpc][columnNpc] = '.';
                            npc.setColumn(columnNpc+2);
                        }
                    } else if(lineHero>lineNpc){
                        if(canMoveEntityDown(npc, false)){
                            matrix[lineNpc+2][columnNpc] = 'M';
                            matrix[lineNpc][columnNpc] = '.';
                            npc.setLine(lineNpc+2);
                        }
                    } else if(lineHero<lineNpc){
                        if(canMoveEntityUp(npc, false)){
                            matrix[lineNpc-2][columnNpc] = 'M';
                            matrix[lineNpc][columnNpc] = '.';
                            npc.setLine(lineNpc-2);
                        }
                    }
                    break;
            }
        }
    }

    public void moveMumiaVermelha(String direction){
        for (Coordinates mummy: mumiasVermelhas){
            int lineMummy = mummy.getLine();
            int columnMummy = mummy.getColumn();
            int lineHero = hero.getLine();
            int columnHero = hero.getColumn();
            switch(direction){
                case "up":
                    if(lineHero<lineMummy){
                        if(canMoveEntityUp(mummy, false)){
                            matrix[lineMummy-2][columnMummy] = 'V';
                            matrix[lineMummy][columnMummy] = '.';
                            mummy.setLine(lineMummy-2);
                        }
                    }
                    break;
                case "down":
                    if(lineHero>lineMummy){
                        if(canMoveEntityDown(mummy, false)){
                            matrix[lineMummy+2][columnMummy] = 'V';
                            matrix[lineMummy][columnMummy] = '.';
                            mummy.setLine(lineMummy+2);
                        }
                    }
                    break;
                case "left":
                    if(columnHero<columnMummy){
                        if(canMoveEntityLeft(mummy, false)){
                            matrix[lineMummy][columnMummy-2] = 'V';
                            matrix[lineMummy][columnMummy] = '.';
                            mummy.setColumn(columnMummy-2);
                        }
                    }
                    break;
                case "right":
                    if(columnHero>columnMummy){
                        if(canMoveEntityRight(mummy, false)){
                            matrix[lineMummy][columnMummy+2] = 'V';
                            matrix[lineMummy][columnMummy] = '.';
                            mummy.setColumn(columnMummy+2);
                        }
                    }
                    break;
                case "stand":
                    if(lineHero>lineMummy){
                        if(canMoveEntityDown(mummy, false)){
                            matrix[lineMummy+2][columnMummy] = 'V';
                            matrix[lineMummy][columnMummy] = '.';
                            mummy.setLine(lineMummy+2);
                        }
                    } else if(lineHero<lineMummy){
                        if(canMoveEntityUp(mummy, false)){
                            matrix[lineMummy-2][columnMummy] = 'V';
                            matrix[lineMummy][columnMummy] = '.';
                            mummy.setLine(lineMummy-2);
                        }
                    } else if(columnHero<columnMummy){
                        if(canMoveEntityLeft(mummy, false)){
                            matrix[lineMummy][columnMummy-2] = 'V';
                            matrix[lineMummy][columnMummy] = '.';
                            mummy.setColumn(columnMummy-2);
                        }
                    } else if(columnHero>columnMummy){
                        if(canMoveEntityRight(mummy, false)){
                            matrix[lineMummy][columnMummy+2] = 'V';
                            matrix[lineMummy][columnMummy] = '.';
                            mummy.setColumn(columnMummy+2);
                        }
                    }
                    break;
            }
        }
    }

    public int getNumLines() {
        return matrix.length;
    }

    public int getNumColumns() {
        return matrix[0].length;
    }

    public int getTileValue(int line, int column) {
        if (!isValidPosition(line, column)) {
            throw new IndexOutOfBoundsException("Invalid position!");
        }
        return matrix[line][column];
    }

    public boolean isValidPosition(int line, int column) {
        return line >= 0 && line < matrix.length && column >= 0 && column < matrix[0].length;
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof MummyMazeState)) {
            return false;
        }

        MummyMazeState o = (MummyMazeState) other;
        if (matrix.length != o.matrix.length) {
            return false;
        }

        return Arrays.deepEquals(matrix, o.matrix);
    }

    @Override
    public int hashCode() {
        return 97 * 7 + Arrays.deepHashCode(this.matrix);
    }

    @Override
    public String toString() {
        StringBuilder buffer = new StringBuilder();
        for (int i = 0; i < matrix.length; i++) {
            buffer.append('\n');
            for (int j = 0; j < matrix.length; j++) {
                buffer.append(matrix[i][j]);
                buffer.append(' ');
            }
        }
        return buffer.toString();
    }

    @Override
    public MummyMazeState clone() {
        return new MummyMazeState(matrix);
    }
    //Listeners
    private transient ArrayList<MummyMazeListener> listeners = new ArrayList<MummyMazeListener>(3);

    public synchronized void removeListener(MummyMazeListener l) {
        if (listeners != null && listeners.contains(l)) {
            listeners.remove(l);
        }
    }

    public synchronized void addListener(MummyMazeListener l) {
        if (!listeners.contains(l)) {
            listeners.add(l);
        }
    }

    public void firePuzzleChanged(MummyMazeEvent pe) {
        for (MummyMazeListener listener : listeners) {
            listener.puzzleChanged(null);
        }
    }

    public boolean checkGoal() {
        int exitLine = exit.getLine();
        int exitColumn = exit.getColumn();
        int heroLine = hero.getLine();
        int heroColumn = hero.getColumn();
        if(heroLine==exitLine && (heroColumn+1==exitColumn || heroColumn-1==exitColumn)){
            return true;
        }
        if(heroColumn==exitColumn && (heroLine+1==exitLine || heroLine-1==exitLine)){
            return true;
        }
        return false;
    }
}
