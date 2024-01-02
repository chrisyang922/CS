package src.pa1.agents;

/*
 * Partner / Group : Jeong Yong Yang, Junho Son
 */

// SYSTEM IMPORTS
import edu.cwru.sepia.action.Action;
import edu.cwru.sepia.action.ActionFeedback;
import edu.cwru.sepia.action.ActionResult;
import edu.cwru.sepia.agent.Agent;
import edu.cwru.sepia.environment.model.history.DamageLog;
import edu.cwru.sepia.environment.model.history.DeathLog;
import edu.cwru.sepia.environment.model.history.History.HistoryView;
import edu.cwru.sepia.environment.model.state.ResourceNode;
import edu.cwru.sepia.environment.model.state.ResourceNode.ResourceView;
import edu.cwru.sepia.environment.model.state.State.StateView;
import edu.cwru.sepia.environment.model.state.Unit.UnitView;
import edu.cwru.sepia.util.Direction;
import edu.cwru.sepia.util.Pair;

import java.io.InputStream;
import java.util.Queue;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Random;
import java.util.Set;
import java.util.Stack;


// JAVA PROJECT IMPORTS
import edu.bu.pa1.distance.DistanceMetric;
import edu.bu.pa1.graph.Vertex;
import edu.bu.pa1.graph.Path;


public class StealthAgent
    extends Agent
{

    // Fields of this class
    // TODO: add your fields here! For instance, it might be a good idea to
    // know when you've killed the enemy townhall so you know when to escape!
    // TODO: implement the state machine for following a path once we calculate it
    //       this will for sure adding your own fields.

    private int myUnitID;
    private int enemyTownhallUnitID;
    private Set<Integer> otherEnemyUnitIDs;
    private boolean firstTime;
    private final int enemyUnitSightRadius;
    private Stack<Action> actionsToTake;
    private boolean foundBase;
    private int initialPosX;
    private int initialPosY;
    private boolean destroyedBase;
    private boolean backFirstTime;
    private boolean cameBackToBase;
    private Set<Integer> edgeWeightCheck;

    public StealthAgent(int playerNum, String[] args)
    {
        super(playerNum);

        // set these fields to some invalid state and populate them in initialStep()
        this.myUnitID = -1;
        this.enemyTownhallUnitID = -1;
        this.otherEnemyUnitIDs = null;
        this.firstTime = false;
        // TODO: make sure to initialize your fields (to some invalid state) here!


        int enemyUnitSightRadius = -1;
        if(args.length == 2)
        {
            try
            {
                enemyUnitSightRadius = Integer.parseInt(args[1]);

                if(enemyUnitSightRadius <= 0)
                {
                    throw new Exception("ERROR");
                }
            } catch(Exception e)
            {
                System.err.println("ERROR: [StealthAgent.StealthAgent]: error parsing second arg=" + args[1]
                    + " which should be a positive integer");
            }
        } else
        {
            System.err.println("ERROR [StealthAgent.StealthAgent]: need to provide a second arg <enemyUnitSightRadius>");
            System.exit(-1);
        }

        this.enemyUnitSightRadius = enemyUnitSightRadius;
        this.actionsToTake = new Stack<Action>();
        this.foundBase = false;
        this.initialPosX = 0;
        this.initialPosY = 0;
        this.destroyedBase = false;
        this.backFirstTime = false;
        this.cameBackToBase = false;
        this.edgeWeightCheck = new HashSet<>();
    }

    // TODO: add some getter methods for your fields! Thats the java way to do things!
    public int getMyUnitID() { return this.myUnitID; }
    public int getEnemyTownhallUnitID() { return this.enemyTownhallUnitID; }
    public final Set<Integer> getOtherEnemyUnitIDs() { return this.otherEnemyUnitIDs; }
    public final int getEnemyUnitSightRadius() { return this.enemyUnitSightRadius; }
    public boolean getFirstTime(){ return this.firstTime;}
    public Action getActionsToTake() { return this.actionsToTake.pop();}
    public Stack<Action> getFullStack() {return this.actionsToTake;}
    public boolean getFoundBase(){return this.foundBase;}
    public int getInitialPosX() {return this.initialPosX;}
    public int getInitialPosY() {return this.initialPosY;}
    public boolean getDestroyedBase() {return this.destroyedBase;}
    public boolean getBackFirstTime() {return this.backFirstTime;}
    public boolean getCameBackToBase() {return this.cameBackToBase;}
    public Set<Integer> getEdgeWeightCheck() {return this.edgeWeightCheck;}

    // TODO: add some setter methods for your fields if they need them! Thats the java way to do things!
    private void setMyUnitID(int id) { this.myUnitID = id; }
    private void setEnemyTownhallUnitID(int id) { this.enemyTownhallUnitID = id; }
    private void setOtherEnemyUnitIDs(Set<Integer> s) { this.otherEnemyUnitIDs = s; }
    private void setFirstTime(boolean b) {this.firstTime = b;}
    private void setActionsToTake(Action s) {this.actionsToTake.add(s);}
    private void nullActions() {this.actionsToTake = new Stack<Action>();}
    private void setFoundBase(boolean b) {this.foundBase = b;}
    private void setInitialPosX(int i) {this.initialPosX = i;}
    private void setInitialPosY(int i) {this.initialPosY = i;}
    private void setDestroyedBase(boolean b) {this.destroyedBase = b;}
    private void setBackFirstTime(boolean b) {this.backFirstTime = b;}
    private void setCameBackToBase(boolean b) {this.cameBackToBase = b;}
    private void setEdgeWeight(int i){this.edgeWeightCheck.add(i);}
    private void nullEdges(){this.edgeWeightCheck = new HashSet<>();}




    /**
        TODO: if you add any fields to this class it might be a good idea to initialize them here
              if they need sepia information!
     */
    @Override
    public Map<Integer, Action> initialStep(StateView state,
                                            HistoryView history)
    {
        // this method is typically used to discover the units in the game.
        // any units we want to pay attention to we probably want to store in some fields

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
            this.setEnemyTownhallUnitID(enemyTownhallUnitIDs.iterator().next()); // get the one unit id
            this.setOtherEnemyUnitIDs(otherEnemyUnitIDs);
        }
        this.setFirstTime(true);
        this.setBackFirstTime(true);
        return this.middleStep(state, history);
    }

    /**
        TODO: implement me! This is the method that will be called every turn of the game.
              This method is responsible for assigning actions to all units that you control
              (which should only be a single footman in this game)
     */
    @Override
    public Map<Integer, Action> middleStep(StateView state,
                                           HistoryView history)
    {
        Map<Integer, Action> actions = new HashMap<Integer, Action>();
        UnitView townHall = state.getUnit(this.getEnemyTownhallUnitID());
        if (townHall == null)
        {
            this.setDestroyedBase(true);
            
        }
        if (this.getDestroyedBase() == true)
        {
            
            Vertex newDestination = new Vertex(this.getInitialPosX(), this.getInitialPosY());
            UnitView player = state.getUnit(this.getMyUnitID());
            int xPosPlayer = player.getXPosition();
            int yPosPlyaer = player.getYPosition();
            Vertex playerVertex = new Vertex(xPosPlayer, yPosPlyaer);
            Set<Vertex> initialPositionNeighbors = this.getOutgoingNeighbors(newDestination, state);
            
            if (this.getCameBackToBase() == false)
            {
                for (Vertex neighborsOfInitial: initialPositionNeighbors)
                {
                    if (playerVertex.equals(neighborsOfInitial))
                    {
                        this.setCameBackToBase(true);
                        break;
                    }
                }
            }

            if(this.cameBackToBase == true)
            {
                actions.put(this.getMyUnitID(), Action.createPrimitiveMove(this.getMyUnitID(), this.getDirection(playerVertex, new Vertex(xPosPlayer + 1, yPosPlyaer))));
                return actions;
            }
            

            if (this.getBackFirstTime() == true)
            {
                this.nullActions();
                StateView currentState = state;
                Path path = search(playerVertex, newDestination, currentState);
                Stack<Path> stack = new Stack<>();
                Path finalPath = path;
                Path copyPath = path;
                Stack<Vertex> inOrderVertex = new Stack<Vertex>();
                Stack<Direction> inOrderDirection = new Stack<Direction>();
                if (path != null)
                {
                    while(path.getParentPath() != null)
                    {
                        path = path.getParentPath();
                        
                        stack.add(path);
                        
                    }
                    while(!stack.isEmpty())
                    {
                        Vertex one = stack.pop().getDestination();
                        if (!stack.isEmpty())
                        {
                            Vertex two = stack.peek().getDestination();
                            
                            Direction theDirectionToMove = this.getDirection(one, two);
                            inOrderDirection.add(theDirectionToMove);
                            inOrderVertex.add(two);
                            System.out.println("Direction is " + theDirectionToMove);
                            System.out.println("The coordinates are " + two.getXCoordinate() + " " + two.getYCoordinate());
                        } 
                        else
                        {
                            Vertex two = finalPath.getDestination();
                            Vertex vvvv = new Vertex(two.getXCoordinate(), two.getYCoordinate());
                            Direction theDirectionToMove = getDirection(one, vvvv);
                            inOrderDirection.add(theDirectionToMove);
                            inOrderVertex.add(two);
                            System.out.println(theDirectionToMove);
                            System.out.println("The coordinates are " + two.getXCoordinate() + " " + two.getYCoordinate());
                        }
                        
                        
                    }
                    
                    
                }  
                if (copyPath != null)
                {
                    while(copyPath.getParentPath() != null)
                    {
                        copyPath = copyPath.getParentPath();

                        stack.add(copyPath);
                        
                    }
                    while(!stack.isEmpty())
                    {
                        Vertex one = stack.pop().getDestination();
                        if (!stack.isEmpty())
                        {
                            Vertex two = inOrderVertex.pop();
                            
                            Direction theDirectionToMove = inOrderDirection.pop();
                            Action action = Action.createPrimitiveMove(this.getMyUnitID(), theDirectionToMove);
                            actions.put(this.getMyUnitID(), action);
                            this.setActionsToTake(action);
                        } 
                        else
                        {
                            Vertex two = finalPath.getDestination();
                            Vertex vvvv = new Vertex(two.getXCoordinate(), two.getYCoordinate());
                            Direction theDirectionToMove = inOrderDirection.pop();
                            Action action = Action.createPrimitiveMove(this.getMyUnitID(), theDirectionToMove);
                            actions.put(this.getMyUnitID(), action);
                        }
                        
                        
                    }
                    
                    
                }  
                this.setBackFirstTime(false);
            }
            else
            {
                if (this.shouldReplacePlan(state) == false)
                {
                    
                    if (!this.getFullStack().isEmpty()){
                        actions.put(this.getMyUnitID(),this.getActionsToTake());
                    }
                }
                else
                {
                    System.out.println("change of plan - recalculate");
                    this.nullActions();

                    StateView currentState = state;
                
                    Path path = search(playerVertex, newDestination, currentState);
                    Stack<Path> stack = new Stack<>();
                    Path finalPath = path;
                    Path copyPath = path;
                    Stack<Vertex> inOrderVertex = new Stack<Vertex>();
                    Stack<Direction> inOrderDirection = new Stack<Direction>();
                    if (path != null)
                    {
                        while(path.getParentPath() != null)
                        {
                            path = path.getParentPath();
                            
                            stack.add(path);
                            
                        }
                        while(!stack.isEmpty())
                        {
                            Vertex one = stack.pop().getDestination();
                            if (!stack.isEmpty())
                            {
                                Vertex two = stack.peek().getDestination();
                                
                                Direction theDirectionToMove = this.getDirection(one, two);
                                inOrderDirection.add(theDirectionToMove);
                                inOrderVertex.add(two);
                                //Action action = Action.createPrimitiveMove(this.getMyUnitID(), theDirectionToMove);
                                //actions.put(this.getMyUnitID(), action);
                                //this.setActionsToTake(action);
                            } 
                            else
                            {
                                Vertex two = finalPath.getDestination();
                                Vertex vvvv = new Vertex(two.getXCoordinate(), two.getYCoordinate());
                                Direction theDirectionToMove = getDirection(one, vvvv);
                                inOrderDirection.add(theDirectionToMove);
                                inOrderVertex.add(two);
                                //Action action = Action.createPrimitiveMove(this.getMyUnitID(), theDirectionToMove);
                                //actions.put(this.getMyUnitID(), action);
                            }
                            
                            
                        }
                        
                        
                    }  
                    if (copyPath != null)
                    {
                        while(copyPath.getParentPath() != null)
                        {
                            copyPath = copyPath.getParentPath();
                            
                            stack.add(copyPath);
                            
                        }
                        while(!stack.isEmpty())
                        {
                            Vertex one = stack.pop().getDestination();
                            if (!stack.isEmpty())
                            {
                                Vertex two = inOrderVertex.pop();
                                
                                Direction theDirectionToMove = inOrderDirection.pop();
                                Action action = Action.createPrimitiveMove(this.getMyUnitID(), theDirectionToMove);
                                actions.put(this.getMyUnitID(), action);
                                this.setActionsToTake(action);
                            } 
                            else
                            {
                                Vertex two = finalPath.getDestination();
                                Vertex vvvv = new Vertex(two.getXCoordinate(), two.getYCoordinate());
                                Direction theDirectionToMove = inOrderDirection.pop();
                                Action action = Action.createPrimitiveMove(this.getMyUnitID(), theDirectionToMove);
                                actions.put(this.getMyUnitID(), action);
                            }
                            
                            
                        }
                        
                        
                    }  
                }
                
            
            }
            
            

            return actions;
        }







        int xPosTownHall = townHall.getXPosition();
        int yPosTownHall = townHall.getYPosition();
        Vertex townHallVertex = new Vertex(xPosTownHall, yPosTownHall);

        Set<Vertex> nearNeighbors = this.getOutgoingNeighbors(townHallVertex, state);

        UnitView player = state.getUnit(this.getMyUnitID());
        int xPosPlayer = player.getXPosition();
        int yPosPlyaer = player.getYPosition();
        Vertex playerVertex = new Vertex(xPosPlayer, yPosPlyaer);

        for (Vertex endVertex: nearNeighbors)
        {
            if (playerVertex.equals(endVertex)){
                this.setFoundBase(true);
                break;
            }
        }
        if (this.getFoundBase())
        {
            Action action = Action.createPrimitiveAttack(this.getMyUnitID(), this.getEnemyTownhallUnitID());
            actions.put(this.getMyUnitID(), action);
        }
        else
        {
            if (this.getFirstTime() == true){
                this.setInitialPosX(xPosPlayer);
                this.setInitialPosY(yPosPlyaer);

                StateView currentState = state;
                Path path = search(playerVertex, townHallVertex, currentState);
                Stack<Path> stack = new Stack<>();
                Path finalPath = path;
                Path copyPath = path;
                Stack<Vertex> inOrderVertex = new Stack<Vertex>();
                Stack<Direction> inOrderDirection = new Stack<Direction>();
                if (path != null)
                {
                    while(path.getParentPath() != null)
                    {
                        path = path.getParentPath();
                        
                        stack.add(path);
                        
                    }
                    while(!stack.isEmpty())
                    {
                        Vertex one = stack.pop().getDestination();
                        if (!stack.isEmpty())
                        {
                            Vertex two = stack.peek().getDestination();
                            
                            Direction theDirectionToMove = this.getDirection(one, two);
                            inOrderDirection.add(theDirectionToMove);
                            inOrderVertex.add(two);
                            System.out.println("Direction is " + theDirectionToMove);
                            //Action action = Action.createPrimitiveMove(this.getMyUnitID(), theDirectionToMove);
                            //actions.put(this.getMyUnitID(), action);
                            //this.setActionsToTake(action);
                            System.out.println("The coordinates are " + two.getXCoordinate() + " " + two.getYCoordinate());
                        } 
                        else
                        {
                            Vertex two = finalPath.getDestination();
                            Vertex vvvv = new Vertex(two.getXCoordinate(), two.getYCoordinate());
                            Direction theDirectionToMove = getDirection(one, vvvv);
                            inOrderDirection.add(theDirectionToMove);
                            inOrderVertex.add(two);
                            //Action action = Action.createPrimitiveMove(this.getMyUnitID(), theDirectionToMove);
                            System.out.println(theDirectionToMove);
                            //actions.put(this.getMyUnitID(), action);
                            System.out.println("The coordinates are " + two.getXCoordinate() + " " + two.getYCoordinate());
                        }
                        
                        
                    }
                    
                    
                }  
                if (copyPath != null)
                {
                    while(copyPath.getParentPath() != null)
                    {
                        copyPath = copyPath.getParentPath();
                        
                        stack.add(copyPath);
                        
                    }
                    while(!stack.isEmpty())
                    {
                        Vertex one = stack.pop().getDestination();
                        if (!stack.isEmpty())
                        {
                            Vertex two = inOrderVertex.pop();
                            
                            Direction theDirectionToMove = inOrderDirection.pop();
                            Action action = Action.createPrimitiveMove(this.getMyUnitID(), theDirectionToMove);
                            actions.put(this.getMyUnitID(), action);
                            this.setActionsToTake(action);
                        } 
                        else
                        {
                            Vertex two = finalPath.getDestination();
                            Vertex vvvv = new Vertex(two.getXCoordinate(), two.getYCoordinate());
                            Direction theDirectionToMove = inOrderDirection.pop();
                            Action action = Action.createPrimitiveMove(this.getMyUnitID(), theDirectionToMove);
                            actions.put(this.getMyUnitID(), action);
                        }
                        
                        
                    }
                    
                    
                }  
                this.setFirstTime(false);
            }
            else
            {
                if (this.shouldReplacePlan(state) == false)
                {
                    
                    if (!this.getFullStack().isEmpty()){
                        actions.put(this.getMyUnitID(),this.getActionsToTake());
                    }
                }
                else
                {
                    System.out.println("change of plan - recalculate");
                    this.nullActions();

                    StateView currentState = state;
                
                    Path path = search(playerVertex, townHallVertex, currentState);
                    Stack<Path> stack = new Stack<>();
                    Path finalPath = path;
                    Path copyPath = path;
                    Stack<Vertex> inOrderVertex = new Stack<Vertex>();
                    Stack<Direction> inOrderDirection = new Stack<Direction>();
                    if (path != null)
                    {
                        while(path.getParentPath() != null)
                        {
                            path = path.getParentPath();
                            stack.add(path);    
                        }
                        while(!stack.isEmpty())
                        {
                            Vertex one = stack.pop().getDestination();
                            if (!stack.isEmpty())
                            {
                                Vertex two = stack.peek().getDestination();
                            
                                Direction theDirectionToMove = this.getDirection(one, two);
                                inOrderDirection.add(theDirectionToMove);
                                inOrderVertex.add(two);
                            //Action action = Action.createPrimitiveMove(this.getMyUnitID(), theDirectionToMove);
                            //actions.put(this.getMyUnitID(), action);
                            //this.setActionsToTake(action);
                            } 
                            else
                            {
                                Vertex two = finalPath.getDestination();
                                Vertex vvvv = new Vertex(two.getXCoordinate(), two.getYCoordinate());
                                Direction theDirectionToMove = getDirection(one, vvvv);
                                inOrderDirection.add(theDirectionToMove);
                                inOrderVertex.add(two);
                            //Action action = Action.createPrimitiveMove(this.getMyUnitID(), theDirectionToMove);
                            //actions.put(this.getMyUnitID(), action);
                            }
                        
                        
                        }
                    
                    
                    }  
                    if (copyPath != null)
                    {
                        while(copyPath.getParentPath() != null)
                        {
                            copyPath = copyPath.getParentPath();
                            stack.add(copyPath);
                        }
                        while(!stack.isEmpty())
                        {
                            Vertex one = stack.pop().getDestination();
                            if (!stack.isEmpty())
                            {
                                Vertex two = inOrderVertex.pop();
                            
                                Direction theDirectionToMove = inOrderDirection.pop();
                                Action action = Action.createPrimitiveMove(this.getMyUnitID(), theDirectionToMove);
                                actions.put(this.getMyUnitID(), action);
                                this.setActionsToTake(action);
                            } 
                            else
                            {
                                Vertex two = finalPath.getDestination();
                                Vertex vvvv = new Vertex(two.getXCoordinate(), two.getYCoordinate());
                                Direction theDirectionToMove = inOrderDirection.pop();
                                Action action = Action.createPrimitiveMove(this.getMyUnitID(), theDirectionToMove);
                                actions.put(this.getMyUnitID(), action);
                            }
                        
                        
                        }
                    
                    
                    }  
                }
                
            }
        }
        
        
        /**
            I would suggest implementing a state machine here to calculate a path when neccessary.
            For instance beginning with something like:

            if(this.shouldReplacePlan(state))
            {
                // recalculate the plan
            }

            then after this, worry about how you will follow this path by submitting sepia actions
            the trouble is that we don't want to move on from a point on the path until we reach it
            so be sure to take that into account in your design

            once you have this working I would worry about trying to detect when you kill the townhall
            so that you implement escaping
         */
        return actions;
    }

    

    // Please don't mess with this
    @Override
    public void terminalStep(StateView state,
                             HistoryView history)
    {
        boolean isMyUnitDead = state.getUnit(this.getMyUnitID()) == null;
        boolean isEnemyTownhallDead = state.getUnit(this.getEnemyTownhallUnitID()) == null;

        if(isMyUnitDead)
        {
            System.out.println("mission failed");
        } else if(isEnemyTownhallDead)
        {
            System.out.println("mission success");
        } else
        {
            System.out.println("how did we get here? Both my unit and the enemy townhall are both alive?");
        }
    }

    // You probably dont need to mess with this: we dont need to save our agent
    @Override
    public void savePlayerData(OutputStream os) {}

    // You probably dont need to mess with this: we dont need to load our agent from disk
    @Override
    public void loadPlayerData(InputStream is) {}


    /**
        TODO: implement me! This method should return "true" WHEN the current plan is bad,
              and return "false" when the path is still valid. I would recommend including
              figuring out when:
                    - the path you created is not blocked by another unit on the map (that has moved)
                    - you are getting too close to an enemy unit that is NOT the townhall
                        Remember, if you get too close to the enemy units they will kill you!
                        An enemy will see you if you get within a chebyshev distance of this.getEnemyUnitSightRadius()
                        squares away
     */
    public boolean shouldReplacePlan(StateView state)
    {
        StateView currentState = state;
        boolean isPlanInvalid = false;

        UnitView playerUnit = currentState.getUnit(this.getMyUnitID());

        
        
        int playerPosX = playerUnit.getXPosition();
        int playerPosY = playerUnit.getYPosition();


        Set<Integer> allEnemy = this.getOtherEnemyUnitIDs();
        for (Integer val : allEnemy) {
            UnitView enemyUnit = currentState.getUnit(val);
            if (enemyUnit != null)
            {
                Integer x = enemyUnit.getXPosition();
                Integer y = enemyUnit.getYPosition();
                if (x - this.getEnemyUnitSightRadius() - 2 <= playerPosX && playerPosX <= x + this.getEnemyUnitSightRadius() + 2){
                    if (y - this.getEnemyUnitSightRadius() - 2 <= playerPosY && playerPosY <= y + this.getEnemyUnitSightRadius() + 2){
                        isPlanInvalid = true;
                    }
                }
            }
            
            
        }

        return isPlanInvalid;
    }

    /**
        TODO: implement me! a helper function to get the outgoing neighbors of a vertex.
     */
    public Set<Vertex> getOutgoingNeighbors(Vertex src,
                                            StateView state)
    {
        Set<Vertex> outgoingNeighbors = new HashSet<Vertex>();
        Vertex vertex = src;
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

        for (Vertex neighbor : neighborsneighbor)
        {
            int newXCoor = neighbor.getXCoordinate();
            int newYCoor = neighbor.getYCoordinate();
            if(state.inBounds(newXCoor, newYCoor) && !state.isResourceAt(newXCoor, newYCoor))
            {
                outgoingNeighbors.add(new Vertex(newXCoor, newYCoor));
            }
            
        }
        return outgoingNeighbors;
    }

    /**
        TODO: implement me! a helper function to get the edge weight of going from "src" to "dst"
              I would recommend discouraging your agent from getting near an enemy by producing
              really large edge costs for going to a vertex that is within the sight of an enemy
     */
    public float getEdgeWeight(StateView state,
                               Vertex src,
                               Vertex dst)
    {
        float edgeCost = 1f;
        Vertex player = src;
        Vertex destinationNode = dst;
        StateView currentState = state; 

        int playerX = player.getXCoordinate();
        int playerY = player.getYCoordinate();

        int destinationX = destinationNode.getXCoordinate();
        int destinationY = destinationNode.getYCoordinate();

        Set<Vertex> neighborsOfPlayerPos = this.getOutgoingNeighbors(player, currentState);

        boolean checkAllNeighbors99999 = true;
        boolean checkedFirst = false;

        Set<Integer> allEnemy = this.getOtherEnemyUnitIDs();
        for (Integer val : allEnemy) {
            UnitView enemyUnit = currentState.getUnit(val);
            if (enemyUnit != null)
            {
                Integer x = enemyUnit.getXPosition();
                Integer y = enemyUnit.getYPosition();
                Set<Vertex> neighborsOfEnemy = this.getOutgoingNeighbors(new Vertex(x,y), currentState);

                
                /*
                if (x - this.getEnemyUnitSightRadius() - 1 <= playerPosX && playerPosX <= x + this.getEnemyUnitSightRadius() + 1){
                    if (y - this.getEnemyUnitSightRadius() - 1 <= playerPosY && playerPosY <= y + this.getEnemyUnitSightRadius() + 1){
                        edgeCost = 99999f;
                        return edgeCost;
                    }
                }
                */

                if(playerX < x - this.getEnemyUnitSightRadius() - 2 || playerX > x + this.getEnemyUnitSightRadius() + 2)
                {
                    this.setEdgeWeight(val);
                    continue;
                }
                if(playerY < y - this.getEnemyUnitSightRadius() - 2 || playerY > y + this.getEnemyUnitSightRadius() + 2)
                {
                    this.setEdgeWeight(val);
                    continue;
                }

                if (x - this.getEnemyUnitSightRadius() - 1 <= playerX && playerX <= x + this.getEnemyUnitSightRadius() + 1)
                {
                    if (y - this.getEnemyUnitSightRadius() - 1 <= playerY && playerY <= y + this.getEnemyUnitSightRadius() + 1)
                    {
                        for (Vertex enemyNeighbor : neighborsOfEnemy)
                        {
                            int count = 0;
                            if (enemyNeighbor.getXCoordinate() - this.getEnemyUnitSightRadius() <= destinationX && destinationX <= enemyNeighbor.getXCoordinate() + this.getEnemyUnitSightRadius())
                            {
                                if (enemyNeighbor.getYCoordinate() - this.getEnemyUnitSightRadius() <= destinationY && destinationY <= enemyNeighbor.getYCoordinate() + this.getEnemyUnitSightRadius())
                                {
                                    if (count == 0)
                                    {
                                        //System.out.println("The vertex is " + destinationX + " " + destinationY);
                                    }
                                    count+=1;
                                    //System.out.println("I am not fine");
                                    if(this.getEdgeWeightCheck().contains(val))
                                    {
                                        edgeCost = 1f;
                                    }
                                    else{
                                        edgeCost = 99999f;
                                    }
                                    for(Vertex neighborPlayer: neighborsOfPlayerPos)
                                    {
                                        if (this.getEdgeWeightHelper(currentState, player, neighborPlayer) <= 99998f)
                                        {
                                            checkAllNeighbors99999 = false;
                                            break;
                                        }   
                                    }
                                    if (checkAllNeighbors99999 == true)
                                    {
                                        float maxDistance = -1;
                                        for(Vertex neighborPlayer: neighborsOfPlayerPos)
                                        {
                                            float currentDistance = DistanceMetric.manhattanDistance(new Vertex(x,y), neighborPlayer);
                                            if (currentDistance > maxDistance)
                                            {
                                                maxDistance = currentDistance;
                                            }
                                        }
                                        for (Vertex neighborPlayer: neighborsOfPlayerPos)
                                        {
                                            float currentDistance = DistanceMetric.manhattanDistance(new Vertex(x,y), neighborPlayer);
                                            if (maxDistance == currentDistance)
                                            {
                                                if (neighborPlayer.equals(destinationNode))
                                                {
                                                    edgeCost = 9999f;
                                                    break;
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        /* 
                        if (x - this.getEnemyUnitSightRadius() - 1 <= destinationX && destinationX <= x + this.getEnemyUnitSightRadius() + 1)
                        {
                            if (y - this.getEnemyUnitSightRadius() - 1 <= destinationY && destinationY <= y + this.getEnemyUnitSightRadius() + 1)
                            {
                                edgeCost = 99999f;
                                for(Vertex neighborPlayer: neighborsOfPlayerPos)
                                {
                                    if (this.getEdgeWeightHelper(currentState, player, neighborPlayer) <= 99998f)
                                    {
                                        checkAllNeighbors99999 = false;
                                        break;
                                    }   
                                }
                                if (checkAllNeighbors99999 == true)
                                {
                                    float maxDistance = -1;
                                    for(Vertex neighborPlayer: neighborsOfPlayerPos)
                                    {
                                        float currentDistance = DistanceMetric.manhattanDistance(new Vertex(x,y), neighborPlayer);
                                        if (currentDistance > maxDistance)
                                        {
                                            maxDistance = currentDistance;
                                        }
                                    }
                                    for (Vertex neighborPlayer: neighborsOfPlayerPos)
                                    {
                                        float currentDistance = DistanceMetric.manhattanDistance(new Vertex(x,y), neighborPlayer);
                                        if (maxDistance == currentDistance)
                                        {
                                            if (neighborPlayer.equals(destinationNode))
                                            {
                                                edgeCost = 9999f;
                                                break;
                                            }
                                        }
                                    }
                                }
                                else
                                {
                                    edgeCost = 99999f;
                                }
                                return edgeCost;
                            }
                        }
                        */
                    }
                }
                if (edgeCost >= 9000f)
                {
                    return edgeCost;
                }

                if (x - this.getEnemyUnitSightRadius() - 2 <= playerX && playerX <= x + this.getEnemyUnitSightRadius() + 2)
                {
                    if (y - this.getEnemyUnitSightRadius() - 2 <= playerY && playerY <= y + this.getEnemyUnitSightRadius() + 2)
                    {
                        for (Vertex enemyNeighbor : neighborsOfEnemy)
                        {
                            if (enemyNeighbor.getXCoordinate() - this.getEnemyUnitSightRadius() <= destinationX && destinationX <= enemyNeighbor.getXCoordinate() + this.getEnemyUnitSightRadius())
                            {
                                if (enemyNeighbor.getYCoordinate() - this.getEnemyUnitSightRadius() <= destinationY && destinationY <= enemyNeighbor.getYCoordinate() + this.getEnemyUnitSightRadius())
                                {
                                    if(this.getEdgeWeightCheck().contains(val))
                                    {
                                        edgeCost = 50f;
                                    }
                                    else{
                                        edgeCost = 99999f;
                                    }
                                    for(Vertex neighborPlayer: neighborsOfPlayerPos)
                                    {
                                        if (this.getEdgeWeightHelper(currentState, player, neighborPlayer) <= 99998f)
                                        {
                                            checkAllNeighbors99999 = false;
                                            break;
                                        }   
                                    }
                                    if (checkAllNeighbors99999 == true)
                                    {
                                        float maxDistance = -1;
                                        for(Vertex neighborPlayer: neighborsOfPlayerPos)
                                        {
                                            float currentDistance = DistanceMetric.manhattanDistance(new Vertex(x,y), neighborPlayer);
                                            if (currentDistance > maxDistance)
                                            {
                                                maxDistance = currentDistance;
                                            }
                                        }
                                        for (Vertex neighborPlayer: neighborsOfPlayerPos)
                                        {
                                            float currentDistance = DistanceMetric.manhattanDistance(new Vertex(x,y), neighborPlayer);
                                            if (maxDistance == currentDistance)
                                            {
                                                if (neighborPlayer.equals(destinationNode))
                                                {
                                                    edgeCost = 9999f;
                                                    break;
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        /*
                        if (x - this.getEnemyUnitSightRadius() - 1 <= destinationX && destinationX <= x + this.getEnemyUnitSightRadius() + 1)
                        {
                            if (y - this.getEnemyUnitSightRadius() - 1 <= destinationY && destinationY <= y + this.getEnemyUnitSightRadius() + 1)
                            {
                                edgeCost = 99999f;
                                for(Vertex neighborPlayer: neighborsOfPlayerPos)
                                {
                                    if (this.getEdgeWeightHelper(currentState, player, neighborPlayer) <= 99998f)
                                    {
                                        checkAllNeighbors99999 = false;
                                        break;
                                    }   
                                }
                                if (checkAllNeighbors99999 == true)
                                {
                                    float maxDistance = -1;
                                    for(Vertex neighborPlayer: neighborsOfPlayerPos)
                                    {
                                        float currentDistance = DistanceMetric.manhattanDistance(new Vertex(x,y), neighborPlayer);
                                        if (currentDistance > maxDistance)
                                        {
                                            maxDistance = currentDistance;
                                        }
                                    }
                                    for (Vertex neighborPlayer: neighborsOfPlayerPos)
                                    {
                                        float currentDistance = DistanceMetric.manhattanDistance(new Vertex(x,y), neighborPlayer);
                                        if (maxDistance == currentDistance)
                                        {
                                            if (neighborPlayer.equals(destinationNode))
                                            {
                                                edgeCost = 9999f;
                                                break;
                                            }
                                        }
                                    }
                                }
                                else
                                {
                                    edgeCost = 99999f;
                                }
                                return edgeCost;
                            }
                        }
                        */
                    }
                }
            }

            /*
            if (x - this.getEnemyTownhallUnitID() - 1 <= destinationX && destinationX <= x + this.getEnemyUnitSightRadius() + 1)
            {
                if (y - this.getEnemyUnitSightRadius() - 1 <= destinationY && destinationY <= y + this.getEnemyUnitSightRadius() + 1)
                {
                    edgeCost = 99999f;
                    return edgeCost;
                }
            } 
            else if (x - this.getEnemyTownhallUnitID() - 2 <= destinationX && destinationX <= x + this.getEnemyUnitSightRadius() + 2)
            {
                if (y - this.getEnemyUnitSightRadius() - 2 <= destinationY && destinationY <= y + this.getEnemyUnitSightRadius() + 2)
                {
                    edgeCost = 99999f;
                    return edgeCost;
                }
            } 
            */
        }
            
        
        return edgeCost;
    }

    public float getEdgeWeightHelper(StateView state,
                               Vertex src,
                               Vertex dst)
    {
        float edgeCost = 1f;
        Vertex player = src;
        Vertex destinationNode = dst;
        StateView currentState = state; 

        int playerX = player.getXCoordinate();
        int playerY = player.getYCoordinate();

        int destinationX = destinationNode.getXCoordinate();
        int destinationY = destinationNode.getYCoordinate();


        Set<Integer> allEnemy = this.getOtherEnemyUnitIDs();
        for (Integer val : allEnemy) {
            UnitView enemyUnit = currentState.getUnit(val);
            if(enemyUnit != null)
            {
                Integer x = enemyUnit.getXPosition();
                Integer y = enemyUnit.getYPosition();
                /*
                if (x - this.getEnemyUnitSightRadius() - 1 <= playerPosX && playerPosX <= x + this.getEnemyUnitSightRadius() + 1){
                    if (y - this.getEnemyUnitSightRadius() - 1 <= playerPosY && playerPosY <= y + this.getEnemyUnitSightRadius() + 1){
                        edgeCost = 99999f;
                        return edgeCost;
                    }
                }
                */

                if (x - this.getEnemyUnitSightRadius() - 1 <= playerX && playerX <= x + this.getEnemyUnitSightRadius() + 1)
                {
                    if (y - this.getEnemyUnitSightRadius() - 1 <= playerY && playerY <= y + this.getEnemyUnitSightRadius() + 1)
                    {
                        if (x - this.getEnemyUnitSightRadius() - 1 <= destinationX && destinationX <= x + this.getEnemyUnitSightRadius() + 1)
                        {
                            if (y - this.getEnemyUnitSightRadius() - 1 <= destinationY && destinationY <= y + this.getEnemyUnitSightRadius() + 1)
                            {
                                edgeCost = 99999f;
                                return edgeCost;
                            }
                        }
                    }
                }

                if (x - this.getEnemyUnitSightRadius() - 2 <= playerX && playerX <= x + this.getEnemyUnitSightRadius() + 2)
                {
                    if (y - this.getEnemyUnitSightRadius() - 2 <= playerY && playerY <= y + this.getEnemyUnitSightRadius() + 2)
                    {
                        if (x - this.getEnemyUnitSightRadius() - 1 <= destinationX && destinationX <= x + this.getEnemyUnitSightRadius() + 1)
                        {
                            if (y - this.getEnemyUnitSightRadius() - 1 <= destinationY && destinationY <= y + this.getEnemyUnitSightRadius() + 1)
                            {
                                edgeCost = 99999f;
                                return edgeCost;
                            }
                        }
                    }
                }
            }

            /*
            if (x - this.getEnemyTownhallUnitID() - 1 <= destinationX && destinationX <= x + this.getEnemyUnitSightRadius() + 1)
            {
                if (y - this.getEnemyUnitSightRadius() - 1 <= destinationY && destinationY <= y + this.getEnemyUnitSightRadius() + 1)
                {
                    edgeCost = 99999f;
                    return edgeCost;
                }
            } 
            else if (x - this.getEnemyTownhallUnitID() - 2 <= destinationX && destinationX <= x + this.getEnemyUnitSightRadius() + 2)
            {
                if (y - this.getEnemyUnitSightRadius() - 2 <= destinationY && destinationY <= y + this.getEnemyUnitSightRadius() + 2)
                {
                    edgeCost = 99999f;
                    return edgeCost;
                }
            } 
            */
        }
            
        
        return edgeCost;
    }

    /**
        TODO: implement me! This method should implement your A* search algorithm, which is very close
              to how dijkstra's algorithm works, but instead uses an estimated total cost to the goal
              to sort rather than the known cost
     */
    public Path search(Vertex src,
                       Vertex goal,
                       StateView state)
    {
        Path pathToTake = null;
        Vertex currentVertex = src;
        Vertex goalVertex = goal;
        PriorityQueue<Path> vertexQueue = new PriorityQueue<Path>(
            new Comparator<Path>() 
            {
                public int compare(Path a, Path b)
                {
                    return Float.compare(a.getTrueCost() + a.getEstimatedPathCostToGoal(), b.getTrueCost() + b.getEstimatedPathCostToGoal());
                }
            }
        );
        Set <Vertex> visited = new HashSet<>();
        Path firstNode = new Path(src, 0, DistanceMetric.manhattanDistance(currentVertex, goalVertex), null); //edit
        vertexQueue.add(firstNode);
        
        this.nullEdges();
        while(!vertexQueue.isEmpty())
        {
            Path currentPP = vertexQueue.poll();
            Path copyReturn = currentPP;
            visited.add(currentPP.getDestination());
            if (currentPP.getDestination().equals(goalVertex))
            {
                while(copyReturn.getParentPath() != null)
                {
                    copyReturn = copyReturn.getParentPath();
                }
                return currentPP.getParentPath();
            }
            
        
            
            Set<Vertex> neighborsOfCurrentPP = getOutgoingNeighbors(currentPP.getDestination(), state);
            for (Vertex v: neighborsOfCurrentPP)
            {
                if (!visited.contains(v))
                {
                    float newScore = currentPP.getTrueCost() + this.getEdgeWeight(state, currentPP.getDestination(), v);
                    if (v.getXCoordinate() == 12 && v.getYCoordinate() == 19)
                    {
                        System.out.println("I am at " + v.getXCoordinate() + " " + v.getYCoordinate() + " and the edge cost is " + newScore);
                    }
                    if (v.getXCoordinate() == 10 && v.getYCoordinate() == 19)
                    {
                        System.out.println("I am at " + v.getXCoordinate() + " " + v.getYCoordinate() + " and the edge cost is " + newScore);
                    }

                    boolean alreadyInQueue = false;
                    Path neighborPath = null;
                    

                    for (Path allNode: vertexQueue)
                    {
                        if(allNode.getDestination().equals(v))
                        {
                            alreadyInQueue = true;
                            neighborPath = allNode;
                            break;
                        }
                    }
                    

                    
                    if (alreadyInQueue) {
                        if (neighborPath.getTrueCost() > newScore)
                        {
                            vertexQueue.remove(neighborPath);
                            Path finalPath = new Path(v, newScore, DistanceMetric.manhattanDistance(v, goalVertex), currentPP); 
                            vertexQueue.add(finalPath);
                        } 
                    } 
                    else
                    {
                        Path finalPath = new Path(v, newScore, DistanceMetric.manhattanDistance(v, goalVertex), currentPP); 
                        vertexQueue.add(finalPath);
                    }
                }
            }
        }


        return pathToTake;
    }


    /**
        A helper method to get the direction we will need to go in order to go from src to an adjacent
        vertex dst. Knowing this direction is necessary in order to create primitive moves in Sepia which uses
        the following factory method:
            Action.createPrimitiveMove(<unitIDToMove>, <directionToMove>);
     */
    protected Direction getDirection(Vertex src,
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
            System.err.println("ERROR: src=" + src + " and dst=" + dst + " are not adjacent vertices");
        }

        return dirToGo;
    }

}

