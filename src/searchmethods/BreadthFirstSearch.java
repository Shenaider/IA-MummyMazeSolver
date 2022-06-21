package searchmethods;

import agent.Action;
import agent.Problem;
import agent.Solution;
import agent.State;
import java.util.List;
import utils.NodeLinkedList;

public class BreadthFirstSearch extends GraphSearch<NodeLinkedList> {

    public BreadthFirstSearch() {
        frontier = new NodeLinkedList();
    }

    @Override
    public void addSuccessorToFrontier(State successor, Node parent) {
        if (!(frontier.containsState(successor) || explored.contains(successor))) {
            frontier.addLast(new Node(successor, parent));
        }
    }

    @Override
    public String toString() {
        return "Breadth first search";
    }
}