package gui;

import mummymaze.MummyMazeEvent;
import mummymaze.MummyMazeListener;
import mummymaze.MummyMazeState;
import javax.swing.table.AbstractTableModel;

public class MummyMazeTableModel extends AbstractTableModel implements MummyMazeListener{

    private MummyMazeState maze;

    public MummyMazeTableModel(MummyMazeState maze) {
        if(maze == null){
            throw new NullPointerException("Maze cannot be null");
        }
        this.maze = maze;
        this.maze.addListener(this);
    }

    @Override
    public int getColumnCount() {
        return maze.getNumLines();
    }

    @Override
    public int getRowCount() {
        return maze.getNumColumns();
    }

    @Override
    public Object getValueAt(int row, int col) {
        return maze.getTileValue(row, col);
    }

    @Override
    public void puzzleChanged(MummyMazeEvent pe){
        fireTableDataChanged();
        try{
            Thread.sleep(500);
        }catch(InterruptedException ignore){
        }
    }

    public void setMaze(MummyMazeState maze){
        if(maze == null){
          throw new NullPointerException("Puzzle cannot be null");
        }
        this.maze.removeListener(this);
        this.maze = maze;
        maze.addListener(this);
        fireTableDataChanged();
    }
}
