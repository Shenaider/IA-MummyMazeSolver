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
    boolean gameOver = false;
    private LinkedList<Coordinates> mumias = new LinkedList<>();
    private LinkedList<Coordinates> mumiasVermelhas = new LinkedList<>();
    private LinkedList<Coordinates> escorpioes = new LinkedList<>();
    private Coordinates chave = new Coordinates(0,0);
    private LinkedList<Coordinates> portas_abertas = new LinkedList<>();
    private LinkedList<Coordinates> portas_fechadas = new LinkedList<>();
    private LinkedList<Coordinates> armadilhas = new LinkedList<>();
    public Coordinates hero;
    public Coordinates exit;

    public char[][] getMatrix() {
        return matrix;
    }

    public MummyMazeState(char[][] matrix, int KeyLine, int KeyColumm, LinkedList<Coordinates> traps) {
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
                    case 'E':
                        escorpioes.add(new Coordinates(j,i));
                        break;
                    case '=':
                    case '"':
                        portas_fechadas.add(new Coordinates(j,i));
                        break;
                }
            }
        }
        armadilhas = traps;
        chave = new Coordinates(KeyLine,KeyColumm);
    }

    @Override
    public void executeAction(Action action) {
        action.execute(this);
        firePuzzleChanged(null);
    }

    public boolean canMoveUp() {
        return canMoveEntityUp(hero, true);
    }

    public boolean canMoveEntityUp(Coordinates entity, boolean isHero){
        int line = entity.getLine();
        int column = entity.getColumn();
        if(line == 1){
            if(matrix[line-1][column] == 'S' && isHero){
                hero.setLine(line-1);
            }
            return false;
        }
        return ((matrix[line-2][column] == '.' || matrix[line-2][column] == 'C' || (!isHero && (
                    matrix[line-2][column] == 'H'
                    || matrix[line-2][column] == 'A'
                    || matrix[line-2][column] == 'M'
                    || matrix[line-2][column] == 'V'
                    || matrix[line-2][column] == 'E')))
                && matrix[line-1][column] != '='
                && matrix[line-1][column] != '-');
    }

    public boolean canMoveRight() {
        return canMoveEntityRight(hero, true);
    }

    public boolean canMoveEntityRight(Coordinates entity, boolean isHero){
        int line = entity.getLine();
        int column = entity.getColumn();
        if(column == 11){
            if(matrix[line][column+1] == 'S' && isHero){
                hero.setColumn(column+1);
            }
            return false;
        }
        return ((matrix[line][column+2] == '.' || matrix[line][column+2] == 'C' || (!isHero && (
                    matrix[line][column+2] == 'H'
                    || matrix[line][column+2] == 'A'
                    || matrix[line][column+2] == 'M'
                    || matrix[line][column+2] == 'V'
                    || matrix[line][column+2] == 'E')))
                && matrix[line][column+1] != '"'
                && matrix[line][column+1] != '|');
    }

    public boolean canMoveDown() {
        return canMoveEntityDown(hero, true);
    }

    public boolean canMoveEntityDown(Coordinates entity, boolean isHero){
        int line = entity.getLine();
        int column = entity.getColumn();
        if(line == 11){
            if(matrix[line+1][column] == 'S' && isHero){
                hero.setLine(line+1);
            }
            return false;
        }
        return ((matrix[line+2][column] == '.' || matrix[line+2][column] == 'C' || (!isHero && (
                    matrix[line+2][column] == 'H'
                    || matrix[line+2][column] == 'A'
                    || matrix[line+2][column] == 'M'
                    || matrix[line+2][column] == 'V'
                    || matrix[line+2][column] == 'E')))
                && matrix[line+1][column] != '='
                && matrix[line+1][column] != '-');
    }

    public boolean canMoveLeft() {
        return canMoveEntityLeft(hero, true);
    }

    public boolean canMoveEntityLeft(Coordinates entity, boolean isHero){
        int line = entity.getLine();
        int column = entity.getColumn();
        if(column == 1){
            if(matrix[line][column-1] == 'S' && isHero){
                hero.setColumn(column-1);
            }
            return false;
        }
        return (
                (matrix[line][column-2] == '.' || matrix[line][column-2] == 'C' || (!isHero && (
                    matrix[line][column-2] == 'H'
                    || matrix[line][column-2] == 'A'
                    || matrix[line][column-2] == 'M'
                    || matrix[line][column-2] == 'V'
                    || matrix[line][column-2] == 'E')))
                && matrix[line][column-1] != '"'
                && matrix[line][column-1] != '|');
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
        detectColision(line-2, column, 'H');
        matrix[line-2][column] = 'H';
        matrix[line][column] = '.';
        replenishOverlays(line,column);
        hero.setLine(line-2);
        moveNPC();
    }

    public void moveRight() {
        int line = hero.getLine();
        int column = hero.getColumn();
        detectColision(line, column+2, 'H');
        matrix[line][column+2] = 'H';
        matrix[line][column] = '.';
        replenishOverlays(line,column);
        hero.setColumn(column+2);
        moveNPC();
    }

    public void moveDown() {
        int line = hero.getLine();
        int column = hero.getColumn();
        detectColision(line+2, column, 'H');
        matrix[line+2][column] = 'H';
        matrix[line][column] = '.';
        replenishOverlays(line,column);
        hero.setLine(line+2);
        moveNPC();
    }

    public void moveLeft() {
        int line = hero.getLine();
        int column = hero.getColumn();
        detectColision(line, column-2, 'H');
        matrix[line][column-2] = 'H';
        matrix[line][column] = '.';
        replenishOverlays(line,column);
        hero.setColumn(column-2);
        moveNPC();
    }

    public void stand() {
        moveNPC();
    }

    public void moveNPC(){
        if(!mumias.isEmpty()){
            moveMumiaEscorpiao(mumias, true);
            //firePuzzleChanged(null);
            moveMumiaEscorpiao(mumias, true);
        }
        if(!escorpioes.isEmpty()){
            moveMumiaEscorpiao(escorpioes, false);
        }
        if(!mumiasVermelhas.isEmpty()){
            moveMumiaVermelha();
            //firePuzzleChanged(null);
            moveMumiaVermelha();
        }
    }

    public void moveNPCUp(Coordinates npc, char code){
        int lineNpc = npc.getLine();
        int columnNpc = npc.getColumn();
        if(canMoveEntityUp(npc, false)){
            boolean removeSelf = detectColision(lineNpc-2, columnNpc, code);
            if(removeSelf){
                removeSelfNPC(lineNpc, columnNpc, code);
            } else {
                matrix[lineNpc-2][columnNpc] = code;
                npc.setLine(lineNpc-2);
            }
            replenishOverlays(lineNpc,columnNpc);
        }
    }

    public void moveNPCDown(Coordinates npc, char code){
        int lineNpc = npc.getLine();
        int columnNpc = npc.getColumn();
        if(canMoveEntityDown(npc, false)){
            boolean removeSelf = detectColision(lineNpc+2, columnNpc, code);
            if(removeSelf) {
                removeSelfNPC(lineNpc, columnNpc, code);
            } else {
                matrix[lineNpc + 2][columnNpc] = code;
                npc.setLine(lineNpc+2);
            }
            replenishOverlays(lineNpc,columnNpc);
        }
    }

    public void moveNPCLeft(Coordinates npc, char code){
        int lineNpc = npc.getLine();
        int columnNpc = npc.getColumn();
        if(canMoveEntityLeft(npc, false)){
            boolean removeSelf = detectColision(lineNpc, columnNpc-2, code);
            if(removeSelf){
                removeSelfNPC(lineNpc, columnNpc, code);
            } else {
                matrix[lineNpc][columnNpc-2] = code;
                npc.setColumn(columnNpc-2);
            }
            replenishOverlays(lineNpc,columnNpc);
        }
    }

    public void moveNPCRight(Coordinates npc, char code){
        int lineNpc = npc.getLine();
        int columnNpc = npc.getColumn();
        if(canMoveEntityRight(npc, false)){
            boolean removeSelf = detectColision(lineNpc, columnNpc+2, code);
            if(removeSelf) {
                removeSelfNPC(lineNpc, columnNpc, code);
            } else {
                matrix[lineNpc][columnNpc + 2] = code;
                npc.setColumn(columnNpc+2);
            }
            replenishOverlays(lineNpc,columnNpc);
        }
    }

    public void removeSelfNPC(int line, int column, char npc){
        switch (npc){
            case 'V':
                mumiasVermelhas.remove(new Coordinates(line, column));
                break;
            case 'E':
                escorpioes.remove(new Coordinates(line, column));
                break;
        }
    }

    public boolean detectColision(int line, int column, char npc){
        boolean isHero = (npc == 'H');
        char matrix_destination = matrix[line][column];
        switch(matrix_destination){
            case 'C':
                if(!portas_fechadas.isEmpty()){
                    portas_abertas = portas_fechadas;
                    portas_abertas.forEach((n) -> {
                        int lineDoor = n.getLine();
                        int columnDoor = n.getColumn();
                        if(matrix[lineDoor][columnDoor] == '='){
                            matrix[lineDoor][columnDoor] = '_';
                        } else {
                            matrix[lineDoor][columnDoor] = ')';
                        }
                    });
                    portas_fechadas = new LinkedList<>();
                } else {
                    portas_fechadas = portas_abertas;
                    portas_fechadas.forEach((n) -> {
                        int lineDoor = n.getLine();
                        int columnDoor = n.getColumn();
                        if(matrix[lineDoor][columnDoor] == '_'){
                            matrix[lineDoor][columnDoor] = '=';
                        } else {
                            matrix[lineDoor][columnDoor] = '"';
                        }
                    });
                    portas_abertas = new LinkedList<>();
                }
                break;
            case 'H':
                gameOver = true;
                //TODO: TRIGGER GAMEOVER!
                break;
            case 'M':
                if(isHero){
                    gameOver = true;
                    //TODO: TRIGGER GAMEOVER
                }
                if(npc == 'V'){
                    return true;
                } else if(npc == 'E') {
                    return true;
                } else {
                    mumias.remove(new Coordinates(line, column));
                }
                break;
            case 'V':
                if(isHero){
                    gameOver = true;
                    //TODO: TRIGGER GAMEOVER
                }
                if(npc == 'E') {
                    return true;
                } else {
                    mumiasVermelhas.remove(new Coordinates(line, column));
                }
                break;
            case 'E':
                if(isHero){
                    gameOver = true;
                    //TODO: TRIGGER GAMEOVER
                } else {
                    return true;
                }
                break;
            case 'A':
                if(isHero){
                    gameOver = true;
                    //TODO: TRIGGER GAMEOVER
                }
                break;
        }
        return false;
    }

    public void replenishOverlays(int line, int column){
        if(!armadilhas.isEmpty()){
            for (Coordinates armadilha: armadilhas){
                int armadilha_line=armadilha.getLine();
                int armadilha_column=armadilha.getColumn();
                if(armadilha_line == line && armadilha_column == column){
                    matrix[line][column] = 'A';
                }
            }
        } else if(chave.getColumn() == column && chave.getLine() == line){
            matrix[line][column] = 'C';
        } else {
            matrix[line][column] = '.';
        }
    }

    public void moveMumiaEscorpiao(LinkedList<Coordinates> npcs, boolean isMummy){
        for (Coordinates npc: npcs){
            char npc_char = (isMummy) ? 'M' : 'E';
            int lineNpc = npc.getLine();
            int columnNpc = npc.getColumn();
            int lineHero = hero.getLine();
            int columnHero = hero.getColumn();
            boolean moved = false;
            if(columnHero>columnNpc){
                if(canMoveEntityRight(npc, false)){
                    moveNPCRight(npc,npc_char);
                    moved = true;
                } else if(lineHero<lineNpc && canMoveEntityUp(npc,false)){
                    moveNPCUp(npc,npc_char);
                    moved = true;
                } else if(lineHero>lineNpc && canMoveEntityDown(npc, false)) {
                    moveNPCDown(npc, npc_char);
                    moved = true;
                }
            }

            if(columnHero<columnNpc && !moved){
                if(canMoveEntityLeft(npc, false)){
                    moveNPCLeft(npc,npc_char);
                    moved = true;
                } else if(lineHero<lineNpc && canMoveEntityUp(npc,false)){
                    moveNPCUp(npc,npc_char);
                    moved = true;
                } else if(lineHero>lineNpc && canMoveEntityDown(npc, false)) {
                    moveNPCDown(npc, npc_char);
                    moved = true;
                }
            }
            if(lineHero<lineNpc && !moved && columnHero==columnNpc){
                moveNPCUp(npc,npc_char);
            } else if(lineHero>lineNpc && !moved && columnHero==columnNpc){
                moveNPCDown(npc,npc_char);
            }
        }
    }

    public void moveMumiaVermelha(){
        for (Coordinates mummy: mumiasVermelhas){
            int lineMummy = mummy.getLine();
            int columnMummy = mummy.getColumn();
            int lineHero = hero.getLine();
            int columnHero = hero.getColumn();
            boolean moved = false;
            if(lineHero<lineMummy){
                if(canMoveEntityUp(mummy, false)){
                    moveNPCUp(mummy,'V');
                    moved = true;
                } else if(columnHero<columnMummy && canMoveEntityLeft(mummy,false)){
                    moveNPCLeft(mummy,'V');
                    moved = true;
                } else if(columnHero>columnMummy && canMoveEntityRight(mummy, false)) {
                    moveNPCRight(mummy, 'V');
                    moved = true;
                }
            }
            if(lineHero>lineMummy && !moved){
                if(canMoveEntityDown(mummy, false)){
                    moveNPCDown(mummy,'V');
                    moved = true;
                } else if(columnHero<columnMummy && canMoveEntityLeft(mummy,false)){
                    moveNPCLeft(mummy,'V');
                    moved = true;
                } else if(columnHero>columnMummy && canMoveEntityRight(mummy, false)) {
                    moveNPCRight(mummy, 'V');
                    moved = true;
                }
            }
            if(columnHero<columnMummy && !moved && lineHero==lineMummy){
                moveNPCLeft(mummy, 'V');
            } else if(columnHero>columnMummy && !moved && lineHero==lineMummy){
                moveNPCRight(mummy, 'V');
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
        return new MummyMazeState(matrix, chave.getLine(), chave.getColumn(), armadilhas);
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

        return (heroColumn==exitColumn && (heroLine+1==exitLine || heroLine-1==exitLine));
    }

    public boolean isGameOver(){
        return gameOver;
    }

    public double computeTilesOutOfPlace(MummyMazeState finalState) {
        double h = 0;
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                if (this.matrix[i][j] != 0 && this.matrix[i][j] != finalState.matrix[i][j]) {
                    h++;
                }
            }
        }
        return h;
    }

    public double computeGoalDistance(MummyMazeState state){
        return Math.abs(state.exit.getLine() -state.hero.getLine()) +Math.abs(state.exit.getColumn() -state.hero.getColumn());
    }

    public double computeTileDistances(MummyMazeState state) {
        double h = 0;
        if(state==null){
            return h;
        }
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                if (this.matrix[i][j] != 0) {
                }
            }
        }
        return h;
    }
}
