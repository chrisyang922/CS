package src.lab2.agents;

// SYSTEM IMPORTS
import edu.bu.lab2.agents.MazeAgent;
import edu.bu.lab2.graph.Vertex;
import edu.bu.lab2.graph.Path;


import edu.cwru.sepia.environment.model.state.State.StateView;


import java.util.HashSet;   // will need for dfs
import java.util.Stack;     // will need for dfs
import java.util.Set;       // will need for dfs


// JAVA PROJECT IMPORTS


public class DFSMazeAgent
    extends MazeAgent
{

    public DFSMazeAgent(int playerNum)
    {
        super(playerNum);
    }

    @Override
    public Path search(Vertex src,
                       Vertex goal,
                       StateView state)
    {
        Stack<Vertex> vertexStack = new Stack<>();
        Set<Vertex> visited = new HashSet<>();
        Stack<Path> path = new Stack<> ();

        vertexStack.push(src);
        path.push(new Path(src));

        while (!vertexStack.isEmpty()) 
        {
            Vertex vv = vertexStack.pop();
            Path pp = path.pop();

            if (vv.equals(goal)) {
                return pp.getParentPath();
            }

            Set<Vertex> getNeighbors = neighbors(vv, state);
            for (Vertex ver : getNeighbors)
            {
                
                if(!visited.contains(ver))
                {
                    vertexStack.push(ver);
                    path.push(new Path(ver, 1f, pp));
                    visited.add(ver);
                }
            }
        }
        return null;
    }

    private Set<Vertex> neighbors(Vertex vertex, StateView state)
    {
        int xCoordinate = vertex.getXCoordinate();
        int yCoordinate = vertex.getYCoordinate();

        
        Set<Vertex> neighborsneighbor = new HashSet<Vertex>();

        neighborsneighbor.add(new Vertex(xCoordinate - 1, yCoordinate));
        neighborsneighbor.add(new Vertex(xCoordinate + 1, yCoordinate));
        neighborsneighbor.add(new Vertex(xCoordinate, yCoordinate - 1));
        neighborsneighbor.add(new Vertex(xCoordinate, yCoordinate + 1));
        neighborsneighbor.add(new Vertex(xCoordinate - 1, yCoordinate + 1));
        neighborsneighbor.add(new Vertex(xCoordinate - 1, yCoordinate - 1));
        neighborsneighbor.add(new Vertex(xCoordinate + 1, yCoordinate + 1));
        neighborsneighbor.add(new Vertex(xCoordinate + 1, yCoordinate - 1));

        Set<Vertex> validNeighbors = new HashSet<Vertex>();

        for (Vertex neighbor : neighborsneighbor)
        {
            int newXCoor = neighbor.getXCoordinate();
            int newYCoor = neighbor.getYCoordinate();
            if(state.inBounds(newXCoor, newYCoor) && !state.isResourceAt(newXCoor, newYCoor))
            {
                validNeighbors.add(new Vertex(newXCoor, newYCoor));
            }
            
        }
        return validNeighbors;

        

    }

    @Override
    public boolean shouldReplacePlan(StateView state)
    {
        return false;
    }

}
