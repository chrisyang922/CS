package src.pa2.moveorder;


import edu.bu.chess.game.history.History;
// SYSTEM IMPORTS
import edu.bu.chess.search.DFSTreeNode;

import java.util.LinkedList;
import java.util.List;


// JAVA PROJECT IMPORTS

public class CustomMoveOrderer
    extends Object
{

	/**
	 * TODO: implement me!
	 * This method should perform move ordering. Remember, move ordering is how alpha-beta pruning gets part of its power from.
	 * You want to see nodes which are beneficial FIRST so you can prune as much as possible during the search (i.e. be faster)
	 * @param nodes. The nodes to order (these are children of a DFSTreeNode) that we are about to consider in the search.
	 * @return The ordered nodes.
	 */
	public static List<DFSTreeNode> order(List<DFSTreeNode> nodes)
	{

		List<DFSTreeNode> captureNodes = new LinkedList<DFSTreeNode>();
		List<DFSTreeNode> movementNodes = new LinkedList<DFSTreeNode>();
		List<DFSTreeNode> promoteNodes = new LinkedList<DFSTreeNode>();
		List<DFSTreeNode> otherNodes = new LinkedList<DFSTreeNode>();
		

		for(DFSTreeNode node : nodes)
		{	
			if(node.getMove() != null)
			{
				node.getGame().getBoard()
				switch(node.getMove().getType())
				{
				case CAPTUREMOVE:
					captureNodes.add(node);
					break;
				case PROMOTEPAWNMOVE:
					promoteNodes.add(node);
					break;
				case MOVEMENTMOVE:
					movementNodes.add(node);
					break;
				default:
					otherNodes.add(node);
					break;
				}
			} 
			else
			{
				otherNodes.add(node);
			}
		}

		captureNodes.addAll(promoteNodes);
		captureNodes.addAll(movementNodes);
		captureNodes.addAll(otherNodes);
		return captureNodes;
	}

	public static List<DFSTreeNode> orderTwo(List<DFSTreeNode> nodes)
	{

		List<DFSTreeNode> captureNodes = new LinkedList<DFSTreeNode>();
		List<DFSTreeNode> movementNodes = new LinkedList<DFSTreeNode>();
		List<DFSTreeNode> promoteNodes = new LinkedList<DFSTreeNode>();
		List<DFSTreeNode> otherNodes = new LinkedList<DFSTreeNode>();


		for(DFSTreeNode node : nodes)
		{	
			if(node.getMove() != null)
			{
				switch(node.getMove().getType())
				{
				case CAPTUREMOVE:
					captureNodes.add(node);
					break;
				case PROMOTEPAWNMOVE:
					promoteNodes.add(node);
					break;
				case MOVEMENTMOVE:
					movementNodes.add(node);
					break;
				default:
					otherNodes.add(node);
					break;
				}
			} 
			else
			{
				otherNodes.add(node);
			}
		}

		otherNodes.addAll(movementNodes);
		otherNodes.addAll(promoteNodes);
		otherNodes.addAll(captureNodes);
		return otherNodes;
	}

}
