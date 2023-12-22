package src.lab14.agents;


// SYSTEM IMPORTS
import edu.cwru.sepia.action.Action;
import edu.cwru.sepia.agent.Agent;
import edu.cwru.sepia.environment.model.history.History.HistoryView;
import edu.cwru.sepia.environment.model.state.Unit.UnitView;
import edu.cwru.sepia.environment.model.state.State.StateView;
import edu.cwru.sepia.util.Direction;


import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;


// JAVA PROJECT IMPORTS
import edu.bu.lab14.agents.StochasticAgent;
import edu.bu.lab14.agents.StochasticAgent.RewardFunction;
import edu.bu.lab14.agents.StochasticAgent.TransitionModel;
import edu.bu.lab14.utilities.Coordinate;
import edu.bu.lab14.utilities.Pair;



public class ValueIterationAgent
    extends StochasticAgent
{
    public static final double GAMMA = 1; // feel free to change this around!
    public static final double EPSILON = 1e-6; // don't change this though

    private Map<Coordinate, Double> utilities;

	public ValueIterationAgent(int playerNum)
	{
		super(playerNum);
        this.utilities = null;
	}

    public Map<Coordinate, Double> getUtilities() { return this.utilities; }
    private void setUtilities(Map<Coordinate, Double> u) { this.utilities = u; }

    public boolean isTerminalState(Coordinate c)
    {
        return c.equals(StochasticAgent.POSITIVE_TERMINAL_STATE)
            || c.equals(StochasticAgent.NEGATIVE_TERMINAL_STATE);
    }

    /**
     * A method to get an initial utility map where every coordinate is mapped to the utility 0.0
     */
    public Map<Coordinate, Double> getZeroMap(StateView state)
    {
        Map<Coordinate, Double> m = new HashMap<Coordinate, Double>();
        for(int x = 0; x < state.getXExtent(); ++x)
        {
            for(int y = 0; y < state.getYExtent(); ++y)
            {
                if(!state.isResourceAt(x, y))
                {
                    // we can go here
                    m.put(new Coordinate(x, y), 0.0);
                }
            }
        }
        return m;
    }

    public void valueIteration(StateView state)
    {
        this.setUtilities(null);
        double gammaVal = GAMMA;
        double change = 0.0;
        boolean condition = true;
        while(condition)
        {
            for(int x = 0; x < state.getXExtent(); ++x)
            {
                for (int y = 0; y < state.getYExtent(); ++y)
                {
                    if(!state.isResourceAt(x,y))
                    {
                        double max = 0.0;
                        
                        Map<Coordinate, Double> newUtil = new HashMap<Coordinate, Double>();
                        if (this.getUtilities() == null)
                        {
                            continue;
                        }
                        for (Map.Entry<Coordinate, Double> entry : this.getUtilities().entrySet()) 
                        {
                            double currentValue = 0.0;
                            Coordinate key = entry.getKey();
                            Double value = entry.getValue();
                            if(Math.abs(key.getXCoordinate() - x) > 1 || Math.abs(key.getYCoordinate() - y) > 1)
                            {
                                continue;
                            }
                            java.util.Set<Pair<Coordinate, java.lang.Double>> transitionProbs = TransitionModel.getTransitionProbs(state,key,this.getDirection(new Coordinate(x,y), key));
                            if (currentValue > max)
                            {
                                max = currentValue;
                            }
                            newUtil.put(new Coordinate(key.getXCoordinate(), key.getYCoordinate()), currentValue);
                        }
                        double util = RewardFunction.getReward(new Coordinate(x,y)) + max * gammaVal;
                        this.setUtilities(newUtil);
                        double checkUtil = 0.0;
                        for (Map.Entry<Coordinate, Double> entry: this.getUtilities().entrySet())
                        {
                            Double value = entry.getValue();
                            if(value > checkUtil)
                            {
                                checkUtil = value;
                            }
                        }
                        if (Math.abs(util - checkUtil) > change)
                        {
                            change = Math.abs(util-checkUtil);
                        }
                        double theValueToStop = EPSILON * (1-gammaVal)/gammaVal;
                        System.out.println(theValueToStop);
                        if (change <= theValueToStop)
                        {
                            condition = false;
                            System.out.println(x,y);
                        }
                    }
                }
            }
        
        }
        System.out.println(gammaVal);
    }

    @Override
    public void computePolicy(StateView state,
                              HistoryView history)
    {
        // compute the utilities
        this.valueIteration(state);

        // compute the policy from the utilities
        Map<Coordinate, Direction> policy = new HashMap<Coordinate, Direction>();

        for(Coordinate c : this.getUtilities().keySet())
        {
            // figure out what to do when in this state
            double maxActionUtility = Double.NEGATIVE_INFINITY;
            Direction bestDirection = null;

            // go over every action
            for(Direction d : TransitionModel.CARDINAL_DIRECTIONS)
            {

                // measure how good this action is as a weighted combination of future state's utilities
                double thisActionUtility = 0.0;
                for(Pair<Coordinate, Double> transition : TransitionModel.getTransitionProbs(state, c, d))
                {
                    thisActionUtility += transition.getSecond() * this.getUtilities().get(transition.getFirst());
                }

                // keep the best one!
                if(thisActionUtility > maxActionUtility)
                {
                    maxActionUtility = thisActionUtility;
                    bestDirection = d;
                }
            }

            // policy recommends the best action for every state
            policy.put(c, bestDirection);
        }

        this.setPolicy(policy);
    }

    protected Direction getDirection(Coordinate initial,
                                     Coordinate last)
    {
        int xDiff = initial.getXCoordinate() - last.getXCoordinate();
        int yDiff = initial.getYCoordinate() - last.getYCoordinate();

        Direction dirToGo = null;

        if(xDiff == 1 && yDiff == 1)
        {
            dirToGo = Direction.SOUTHEAST;
        }
        else if(xDiff == 1 && yDiff == 0)
        {
            dirToGo = Direction.EAST;
        }
        else if(xDiff == 1 && yDiff == -1)
        {
            dirToGo = Direction.NORTHEAST;
        }
        else if(xDiff == 0 && yDiff == 1)
        {
            dirToGo = Direction.SOUTH;
        }
        else if(xDiff == 0 && yDiff == -1)
        {
            dirToGo = Direction.NORTH;
        }
        else if(xDiff == -1 && yDiff == 1)
        {
            dirToGo = Direction.SOUTHWEST;
        }
        else if(xDiff == -1 && yDiff == 0)
        {
            dirToGo = Direction.WEST;
        }
        else if(xDiff == -1 && yDiff == -1)
        {
            dirToGo = Direction.NORTHWEST;
        } else
        {
            System.err.println("ERROR: src=" + initial + " and dst=" + last + " are not adjacent vertices");
        }

        return dirToGo;
    }
}
