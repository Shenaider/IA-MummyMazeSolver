package mummymaze;

import agent.Action;

public class ActionStand extends Action<MummyMazeState>{

    public ActionStand(){
        super(1);
    }

    @Override
    public void execute(MummyMazeState state){
        state.stand();
        state.setAction(this);
    }

    @Override
    public boolean isValid(MummyMazeState state){
        return true;
    }
}