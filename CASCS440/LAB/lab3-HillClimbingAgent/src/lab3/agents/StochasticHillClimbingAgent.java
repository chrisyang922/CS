package src.lab3.agents;


import edu.bu.lab3.graph.Vertex;
import edu.bu.lab3.distance.DistanceMetric;


import edu.cwru.sepia.action.Action;
import edu.cwru.sepia.agent.Agent;
import edu.cwru.sepia.environment.model.history.History.HistoryView;
import edu.cwru.sepia.environment.model.state.ResourceNode.ResourceView;
import edu.cwru.sepia.environment.model.state.State.StateView;
import edu.cwru.sepia.environment.model.state.Unit.UnitView;
import edu.cwru.sepia.util.Direction;


import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Random;


public class StochasticHillClimbingAgent
	extends Agent
{

    private int myUnitID;                   // the unit we control
    private int enemyTargetUnitID;          // the target enemy unit (who we want to destroy)
    private Set<Integer> otherEnemyUnitIDs; // all other enemies on the map (we are alone)
    private Vertex nextVertexToMoveTo;      // the next coordinate to move to
    private boolean isStuck;                // are we stuck?


    //////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////// Constructor is **required** if extending sepia Agent type //////////////////////
    public StochasticHillClimbingAgent(int playerNum)
    {
        super(playerNum);                   // call Agent constructor (required)
        this.myUnitID = -1;                 // invalid state
        this.enemyTargetUnitID = -1;        // invalid state
        this.otherEnemyUnitIDs = null;      // invalid state
        this.nextVertexToMoveTo = null;     // invalid state
        this.isStuck = false;               // not currently stuck
    }
    //////////////////////////////////////////////////////////////////////////////////////////////////////////


    //////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////// Getters and Setters ///////////////////////////////////////////////
    public int getMyUnitID() { return this.myUnitID; }
    public int getEnemyTargetUnitID() { return this.enemyTargetUnitID; }
    public final Set<Integer> getOtherEnemyUnitIDs() { return this.otherEnemyUnitIDs; }
    public final Vertex getNextVertexToMoveTo() { return this.nextVertexToMoveTo; }
    public boolean getIsStuck() { return this.isStuck; }

    private void setMyUnitID(int unitID) { this.myUnitID = unitID; }
    private void setEnemyTargetUnitID(int unitID) { this.enemyTargetUnitID = unitID; }
    private void setOtherEnemyUnitIDs(Set<Integer> unitIDs) { this.otherEnemyUnitIDs = unitIDs; }
    private void setNextVertexToMoveTo(Vertex v) { this.nextVertexToMoveTo = v; }
    private void setIsStuck(boolean isStuck) { this.isStuck = isStuck; }
    //////////////////////////////////////////////////////////////////////////////////////////////////////////


    //////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////// These are the five abstract methods that you MUST implement  if extending Agent ///////////\

    // This method is called only once for the very first turn. We use it to discover units / resources
    // and any other data from the state that we want. In this case, we want to discover who our unit is,
    // the enemy target and all other enemies present. We also want to do some error checking since we expect
    // there to only be a single unit on our team and a single townhall present on the enemy's team(s).

    // This method produces a Map where the key is a unit id and the value is a sepia Action that we want
    // that unit to perform. It is customary that an Agent give an action to every unit that it controls,
    // and only one action per unit that it controls.
    @Override
    public Map<Integer, Action> initialStep(StateView state,
                                            HistoryView history) {
        // first find out which units are mine and which units aren't
        Set<Integer> myUnitIDs = new HashSet<Integer>();
        for(Integer unitID : state.getUnitIds(this.getPlayerNumber()))
        {
            myUnitIDs.add(unitID);
        }

        // should only be one unit controlled by me
        if(myUnitIDs.size() != 1)
        {
            System.err.println("ERROR: should only be 1 unit controlled by player=" +
                this.getPlayerNumber() + " but found " + myUnitIDs.size() + " units");
            System.exit(-1);
        } else
        {
            this.setMyUnitID(myUnitIDs.iterator().next()); // get the one unit id
        }


        // there can be as many other players as we want, and they can controll as many units as they want,
        // but there should be only ONE enemy townhall unit
        Set<Integer> enemyTownhallUnitIDs = new HashSet<Integer>();
        Set<Integer> otherEnemyUnitIDs = new HashSet<Integer>();
        for(Integer playerNum : state.getPlayerNumbers())
        {
            if(playerNum != this.getPlayerNumber())
            {
                for(Integer unitID : state.getUnitIds(playerNum))
                {
                    if(state.getUnit(unitID).getTemplateView().getName().toLowerCase().equals("townhall"))
                    {
                        enemyTownhallUnitIDs.add(unitID);
                    } else
                    {
                        otherEnemyUnitIDs.add(unitID);
                    }
                }
            }
        }

        // should only be one unit controlled by me
        if(enemyTownhallUnitIDs.size() != 1)
        {
            System.err.println("ERROR: should only be 1 enemy townhall unit present on the map but found "
                + enemyTownhallUnitIDs.size() + " such units");
            System.exit(-1);
        } else
        {
            this.setEnemyTargetUnitID(enemyTownhallUnitIDs.iterator().next()); // get the one unit id
            this.setOtherEnemyUnitIDs(otherEnemyUnitIDs);
        }

        // common to do stuff like this
        return this.middleStep(state, history);
    }

    // this method is called by sepia every turn of the game, and this method's job is to provide
    // actions for every unit that this Agent controls.
    @Override
    public Map<Integer, Action> middleStep(StateView state,
                                           HistoryView history)
    {
        
        Map<Integer, Action> actions = new HashMap<Integer, Action>();

        UnitView myUnitView = state.getUnit(this.getMyUnitID());
        UnitView enemyTargetUnitView = state.getUnit(this.getEnemyTargetUnitID());

        if(this.getIsStuck())
        {
        	// agent will kill itself if it becomes stuck (so the game will end)
        	actions.put(this.getMyUnitID(), Action.createPrimitiveAttack(this.getMyUnitID(),
                                                                         this.getMyUnitID()));
        }
        else if(enemyTargetUnitView != null) // still alive
        {

            Vertex myUnitCoordinate = new Vertex(myUnitView.getXPosition(),
                                                 myUnitView.getYPosition());
            Vertex enemyTargetCoordinate = new Vertex(enemyTargetUnitView.getXPosition(),
                                                      enemyTargetUnitView.getYPosition());

	        // if we're adjacent to the townhall attack it!
	        if(DistanceMetric.chebyshevDistance(myUnitCoordinate, enemyTargetCoordinate) <= 1)
            {
                System.out.println("Attacking TownHall");
                // if no more movements in the planned path then attack
                actions.put(this.getMyUnitID(), Action.createPrimitiveAttack(this.getMyUnitID(),
                                                                             this.getEnemyTargetUnitID()));
            } else //not adjacent to townhall and townhall is still alive...try to get there
            {
                // if we don't have a place to go to yet OR we've just arrived at the place we want to go to
            	if(this.getNextVertexToMoveTo() == null || this.getNextVertexToMoveTo().equals(myUnitCoordinate))
            	{
            		this.setNextVertexToMoveTo(this.getNextPosition(myUnitCoordinate,
                                                                    enemyTargetCoordinate,
                                                                    state));
            	}

                // go there
                System.out.println("moving to " + this.getNextVertexToMoveTo());
    
				if(this.getIsStuck())
				{
					// agent will kill itself if it becomes stuck (so the game will end)
					actions.put(this.getMyUnitID(), Action.createPrimitiveAttack(this.getMyUnitID(),
																				 this.getMyUnitID()));
				} else
				{
					// figure out the direction the footman needs to move in
					Direction nextDirection = this.getDirectionToMoveTo(myUnitCoordinate,
																		this.getNextVertexToMoveTo());			
					actions.put(this.getMyUnitID(), Action.createPrimitiveMove(this.getMyUnitID(),
																			   nextDirection));
				}
            }
        }

        return actions;
    }

    // this method will be called once the game is over (and will only be called once)
    // sometimes it is a good idea to print out some stats and stuff using this method
    @Override
    public void terminalStep(StateView state,
                             HistoryView history) {}

    // this method is useful if you want to save your agent to disk
    // but we don't have anything to save yet
    @Override
    public void savePlayerData(OutputStream os) {}

    // this method is useful if you want to load an agent's contents from disk
    // but we don't have anything to load yet
    @Override
    public void loadPlayerData(InputStream is) {}
    //////////////////////////////////////////////////////////////////////////////////////////////////////////

    public List<Double> utilitiesToProbabilities(List<Float> utilities)
    {
    	// want lower utility values to correspond to higher probabilities
    	// first I'll invert the utility values and then map them to probabilities using a l_1 norm
    	double sum = 0.0;
    	for(float utility : utilities)
    	{
    		sum += (1.0 / utility);
    	}

    	List<Double> probabilities = new ArrayList<Double>(utilities.size());
    	for(float utility : utilities)
    	{
    		probabilities.add((1.0 / utility) / sum);
    	}
    	return probabilities;
    }
    
    public Vertex pickRandomChild(List<Vertex> children,
                                  final List<Double> probs)
    {
    	Vertex child = null;
    	if(children.size() == 1)
    	{
    		child = children.get(0);
    	} else if (children.size() > 1)
    	{
	    	// need to argsort probs...this is ok since it is O(8) worst case
	    	List<Integer> idxs = new ArrayList<Integer>(children.size());
	    	for(int i = 0; i < children.size(); ++i)
	    	{
	    		idxs.add(i);
	    	}
	
	    	idxs.sort(new Comparator<Integer>()
	    		{
	    			public int compare(final Integer idx1, final Integer idx2)
	    			{
	    				return -1 * Double.compare(probs.get(idx1), probs.get(idx2)); // sort descendingly
	    			}
	    		});
	    	
	    	// convert sorted probs into cdf so we can make the random choice
	    	List<Double> cdf = new ArrayList<Double>(probs.size());
	    	cdf.add(probs.get(idxs.get(0)));
	    	for(int i = 1; i < probs.size(); ++i)
	    	{
	    		cdf.add(cdf.get(i-1) + probs.get(idxs.get(i)));
	    	}
	    	
	    	// get random double and find the largest index j such that randValue <= cdf[j+1]
	    	double randValue = Math.random();
	    	int j = 0;
	    	while(j < cdf.size() && cdf.get(j) <= randValue)
	    	{
	    		j++;
	    	}
	    	
	    	if(j == cdf.size()-1 || j == 0)
	    	{
	    		; // do nothing if we reached the end of the cdf...j is the proper position (edge case)
	    	}
	    	else
	    	{
	    		j -= 1; // go back one to get the proper idx
	    	}
	    	
	    	child = children.get(idxs.get(j));
    	}
    	return child;
    }

    /**
     * TODO: calculate the Vertex of the square to move to. Our code will automatically take control
     * 	     once you are adjacent to the townhall and will kill it.
     * @param pt: the current vertex
     * @param goal: the desired vertex
     * @param state: the current state of the world
     * @return
     */
    public Vertex getNextPosition(Vertex src,
                                  Vertex goal,
                                  StateView state)
    {
        this.setIsStuck(true); // TODO: complete me!
        int xCoordinate = src.getXCoordinate();
        int yCoordinate = src.getYCoordinate();


        
        Set<Vertex> neighborsneighbor = new HashSet<Vertex>();

        neighborsneighbor.add(new Vertex(xCoordinate - 1, yCoordinate));
        neighborsneighbor.add(new Vertex(xCoordinate + 1, yCoordinate));
        neighborsneighbor.add(new Vertex(xCoordinate, yCoordinate - 1));
        neighborsneighbor.add(new Vertex(xCoordinate, yCoordinate + 1));
        neighborsneighbor.add(new Vertex(xCoordinate - 1, yCoordinate + 1));
        neighborsneighbor.add(new Vertex(xCoordinate - 1, yCoordinate - 1));
        neighborsneighbor.add(new Vertex(xCoordinate + 1, yCoordinate + 1));
        neighborsneighbor.add(new Vertex(xCoordinate + 1, yCoordinate - 1));

        List<Vertex> equalNeighbors = new ArrayList<Vertex>();
        List<Double> probability = new ArrayList<Double>();

        float maxDistance = DistanceMetric.chebyshevDistance(src, goal);
        Vertex returnVertex = src;

        for (Vertex neighbor : neighborsneighbor)
        {
            int newXCoor = neighbor.getXCoordinate();
            int newYCoor = neighbor.getYCoordinate();
            if(state.inBounds(newXCoor, newYCoor) && !state.isResourceAt(newXCoor, newYCoor))
            {
                if (maxDistance <= DistanceMetric.chebyshevDistance(goal, neighbor)){
                    continue;
                }
                else{
                    maxDistance = DistanceMetric.chebyshevDistance(goal, neighbor);
                    returnVertex = neighbor;
                }
                if (maxDistance == DistanceMetric.chebyshevDistance(goal, neighbor)){
                    equalNeighbors.add(new Vertex(newXCoor, newYCoor));
                    Random prob = new Random();
                    double randomDouble = prob.nextDouble();
                    probability.add(randomDouble);
                }
            }
            
        }
        if (returnVertex.equals(src)){
            Vertex answer = pickRandomChild(equalNeighbors, probability);
            if (answer.equals(src))
            {
                this.setIsStuck(true); // TODO: complete me!
            }
            else{
                return answer;
            }
        }
        
            return returnVertex; // TODO: complete me!
        
    }

    protected Direction getDirectionToMoveTo(Vertex src,
                                             Vertex dst)
    {
        int xDiff = dst.getXCoordinate() - src.getXCoordinate();
        int yDiff = dst.getYCoordinate() - src.getYCoordinate();

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
            System.err.println("ERROR: cannot go from src=" + src + " to dst=" + dst + " in one move.");
        }

        return dirToGo;
    }
}
