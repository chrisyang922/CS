package src.pa2.heuristics;


// SYSTEM IMPORTS
import edu.cwru.sepia.util.Direction;
import edu.bu.chess.game.move.PromotePawnMove;
import edu.bu.chess.game.piece.Piece;
import edu.bu.chess.game.piece.PieceType;
import edu.bu.chess.game.player.Player;
import edu.bu.chess.search.DFSTreeNode;
import edu.bu.chess.utils.Coordinate;
import java.util.Set;
import java.util.HashSet;

import edu.bu.chess.game.history.History;
import edu.bu.chess.game.move.Move;
import java.util.List;





public class CustomHeuristics
    extends Object
{
	public static final int queenVal = 9;
	public static final int rookVal = 5;
	public static final int knightVal = 3;
	public static final int bishopVal = 3;
	public static final int pawnVal = 1;
	public static final int kingVal = 999999;
	
	public static final Coordinate importantCoorOne = new Coordinate(4,4);
	public static final Coordinate importantCoorTwo = new Coordinate(4,5);
	public static final Coordinate importantCoorThree = new Coordinate(5,4);
	public static final Coordinate importantCoorFour = new Coordinate(5,5);
	public static boolean goUp;
	public static boolean goDown; 

	public static Set<Coordinate> allCoordinate()
	{
		Set<Coordinate> all = new HashSet<Coordinate>();
		for (int x = 1; x < 9; x++)
		{
			for (int y = 1; y < 9; y++)
			{
				all.add(new Coordinate(x,y));
			}
		}
		return all;
	}

	public static final Set<Coordinate> allCoor = allCoordinate();
	

	public static Player getMaxPlayer(DFSTreeNode node)
	{
		return node.getMaxPlayer();
	}

	public static Player getMinPlayer(DFSTreeNode node)
	{
		return CustomHeuristics.getMaxPlayer(node).equals(node.getGame().getCurrentPlayer()) ? node.getGame().getOtherPlayer() : node.getGame().getCurrentPlayer(); 
	}

	


	/**
	 * TODO: implement me! The heuristics that I wrote are useful, but not very good for a good chessbot.
	 * Please use this class to add your heuristics here! I recommend taking a look at the ones I provided for you
	 * in DefaultHeuristics.java (which is in the same directory as this file)
	 */

	
	 //good things for us, which will be added
	 public static class OffensiveHeuristics extends Object
	 {


		//how many players/pieces can we attack -- Good Thing!
		//each unit is worth 0.5 points
		public static double getNumberOfPiecesMaxPlayerIsThreatening(DFSTreeNode node)
		{
			 double numPiecesMaxPlayerIsThreatening = 0.0;
			 for(Piece piece : node.getGame().getBoard().getPieces(CustomHeuristics.getMaxPlayer(node)))
			 {
				 numPiecesMaxPlayerIsThreatening += piece.getAllCaptureMoves(node.getGame()).size() * 0.5;
			 }
			 numPiecesMaxPlayerIsThreatening = Math.max(numPiecesMaxPlayerIsThreatening, 0.0);
			 return numPiecesMaxPlayerIsThreatening;
		}

		 //how many players/pieces can we attack with their associated values -- Good Thing!
		 // each move is 0.5 * the weight of the pieces
		public static double getWeightedNumberOfPiecesMaxPlayerIsThreatening(DFSTreeNode node)
		{
			
			double weightedVal = 0; 
			Set<Piece> enemy = node.getGame().getBoard().getPieces(CustomHeuristics.getMinPlayer(node));
			Set<Coordinate> allCoordinatesEnemyCanReach = CustomHeuristics.allCoordinate();
			for(Piece e: enemy)
			{
				List<Move> allMoves = e.getAllMoves(node.getGame());
				for(Move m: allMoves)
				{
					Coordinate coordReach = node.getGame().getBoard().applyMove(m).getPiecePosition(e);
					allCoordinatesEnemyCanReach.add(coordReach);
				}
			}
			for(Piece piece : node.getGame().getBoard().getPieces(CustomHeuristics.getMaxPlayer(node)))
			{
				List<Move> captureMoves = piece.getAllCaptureMoves(node.getGame());
				for(Move cm: captureMoves)
				{
					Coordinate attack = node.getGame().getBoard().applyMove(cm).getPiecePosition(piece);
					PieceType thePieceThere = node.getGame().getBoard().getPieceAtPosition(attack).getType();
					if(thePieceThere.equals(PieceType.QUEEN))
					{
						if(allCoordinatesEnemyCanReach.contains(attack))
						{
							if(piece.getType().equals(PieceType.KING))
							{
								weightedVal += (queenVal - kingVal) * 0.5;
							}
							else if (piece.getType().equals(PieceType.QUEEN))
							{
								weightedVal += (queenVal - queenVal) * 0.5;
							}
							else if (piece.getType().equals(PieceType.ROOK))
							{
								weightedVal += (queenVal - rookVal) * 0.5;
							}
							else if (piece.getType().equals(PieceType.KNIGHT))
							{
								weightedVal += (queenVal - knightVal) * 0.5;
							}
							else if (piece.getType().equals(PieceType.BISHOP))
							{
								weightedVal += (queenVal - bishopVal) * 0.5;
							}
							else
							{
								weightedVal += (queenVal - pawnVal) * 0.5;
							}
						}
						else
						{
							weightedVal += queenVal * 0.5;
						}
					}
					else if(thePieceThere.equals(PieceType.ROOK))
					{
						if(allCoordinatesEnemyCanReach.contains(attack))
						{
							if(piece.getType().equals(PieceType.KING))
							{
								weightedVal += (rookVal - kingVal) * 0.5;
							}
							else if (piece.getType().equals(PieceType.QUEEN))
							{
								weightedVal += (rookVal - queenVal) * 0.5;
							}
							else if (piece.getType().equals(PieceType.ROOK))
							{
								weightedVal += (rookVal - rookVal) * 0.5;
							}
							else if (piece.getType().equals(PieceType.KNIGHT))
							{
								weightedVal += (rookVal - knightVal) * 0.5;
							}
							else if (piece.getType().equals(PieceType.BISHOP))
							{
								weightedVal += (rookVal - bishopVal) * 0.5;
							}
							else
							{
								weightedVal += (rookVal - pawnVal) * 0.5;
							}
						}
						else
						{
							weightedVal += rookVal * 0.5;
						}
					}
					else if(thePieceThere.equals(PieceType.KNIGHT))
					{
						if(allCoordinatesEnemyCanReach.contains(attack))
						{
							if(piece.getType().equals(PieceType.KING))
							{
								weightedVal += (knightVal - kingVal) * 0.5;
							}
							else if (piece.getType().equals(PieceType.QUEEN))
							{
								weightedVal += (knightVal - queenVal) * 0.5;
							}
							else if (piece.getType().equals(PieceType.ROOK))
							{
								weightedVal += (knightVal - rookVal) * 0.5;
							}
							else if (piece.getType().equals(PieceType.KNIGHT))
							{
								weightedVal += (knightVal - knightVal) * 0.5;
							}
							else if (piece.getType().equals(PieceType.BISHOP))
							{
								weightedVal += (knightVal - bishopVal) * 0.5;
							}
							else
							{
								weightedVal += (knightVal - pawnVal) * 0.5;
							}
						}
						else
						{
							weightedVal += knightVal * 0.5;
						}
					}
					
					else if(thePieceThere.equals(PieceType.BISHOP))
					{
						if(allCoordinatesEnemyCanReach.contains(attack))
						{
							if(piece.getType().equals(PieceType.KING))
							{
								weightedVal += (bishopVal - kingVal) * 0.5;
							}
							else if (piece.getType().equals(PieceType.QUEEN))
							{
								weightedVal += (bishopVal - queenVal) * 0.5;
							}
							else if (piece.getType().equals(PieceType.ROOK))
							{
								weightedVal += (bishopVal - rookVal) * 0.5;
							}
							else if (piece.getType().equals(PieceType.KNIGHT))
							{
								weightedVal += (bishopVal - knightVal) * 0.5;
							}
							else if (piece.getType().equals(PieceType.BISHOP))
							{
								weightedVal += (bishopVal - bishopVal) * 0.5;
							}
							else
							{
								weightedVal += (bishopVal - pawnVal) * 0.5;
							}
						}
						else
						{
							weightedVal += bishopVal * 0.5;
						}
					}
					else if(thePieceThere.equals(PieceType.PAWN))
					{
						if(allCoordinatesEnemyCanReach.contains(attack))
						{
							if(piece.getType().equals(PieceType.KING))
							{
								weightedVal += (pawnVal - kingVal) * 0.5;
							}
							else if (piece.getType().equals(PieceType.QUEEN))
							{
								weightedVal += (pawnVal - queenVal) * 0.5;
							}
							else if (piece.getType().equals(PieceType.ROOK))
							{
								weightedVal += (pawnVal - rookVal) * 0.5;
							}
							else if (piece.getType().equals(PieceType.KNIGHT))
							{
								weightedVal += (pawnVal - knightVal) * 0.5;
							}
							else if (piece.getType().equals(PieceType.BISHOP))
							{
								weightedVal += (pawnVal - bishopVal) * 0.5;
							}
							else
							{
								weightedVal += (pawnVal - pawnVal) * 0.5;
							}
						}
						else
						{
							weightedVal += pawnVal * 0.5;
						}
					}
					else
					{
						weightedVal += kingVal;
					}
				}

			}
			weightedVal = Math.max(weightedVal*2, 0.0);
			return weightedVal;
		}

		//how many pieces of the board can I move to -- Good Thing!
		// each coordinate is worth 0.1 points
		public static double howManyBoards(DFSTreeNode node)
		{
			Set<Coordinate> checkDuplicate = new HashSet<Coordinate>();
			double howMany = 0;
			for(Piece piece : node.getGame().getBoard().getPieces(CustomHeuristics.getMaxPlayer(node)))
			{
				List<Move> movesList = piece.getAllMoves(node.getGame());
				for(Move m: movesList)
				{
					Coordinate coord = node.getGame().getBoard().applyMove(m).getPiecePosition(piece);
					for(Coordinate c: allCoor)
					{
						if(coord.equals(c) && !checkDuplicate.contains(c))
						{
							howMany += 0.1;
							checkDuplicate.add(c);
						}
					}
				}
			}
			howMany = Math.max(howMany, 0.0);
			return howMany;
		}
		

		// Check how many of my pieces are at the center -- Good Thing!
		// each is worth one point
		public static int howManyPiecesOnImportantCoordinates(DFSTreeNode node)
		{
			int howMany = 0;
			boolean coorOne = node.getGame().getBoard().isPositionOccupied(importantCoorOne);
			boolean coorTwo = node.getGame().getBoard().isPositionOccupied(importantCoorTwo);
			boolean coorThree = node.getGame().getBoard().isPositionOccupied(importantCoorThree);
			boolean coorFour = node.getGame().getBoard().isPositionOccupied(importantCoorFour);
			Set<Piece> checkMyColor = node.getGame().getBoard().getPieces(CustomHeuristics.getMaxPlayer(node));
			boolean black = false;
			boolean white = false;
			for(Piece p: checkMyColor)
			{
				if (p.getPlayer().equals(node.getGame().getBlackPlayer()))
				{
					black = true;
				}
				else
				{
					white = true;
				}
				break;
			}

			if (coorOne == true)
			{
				Piece coordPiece = node.getGame().getBoard().getPieceAtPosition(importantCoorOne);
				if (black == true && coordPiece.getPlayer().equals(node.getGame().getBlackPlayer()))
				{
					howMany += 1;
				}
				else if (white == true && coordPiece.getPlayer().equals(node.getGame().getWhitePlayer()))
				{
					howMany += 1;
				}

			}
			if (coorTwo == true)
			{
				Piece coordPiece = node.getGame().getBoard().getPieceAtPosition(importantCoorTwo);
				if (black == true && coordPiece.getPlayer().equals(node.getGame().getBlackPlayer()))
				{
					howMany += 1;
				}
				else if (white == true && coordPiece.getPlayer().equals(node.getGame().getWhitePlayer()))
				{
					howMany += 1;
				}
			}
			if (coorThree == true)
			{
				Piece coordPiece = node.getGame().getBoard().getPieceAtPosition(importantCoorThree);
				if (black == true && coordPiece.getPlayer().equals(node.getGame().getBlackPlayer()))
				{
					howMany += 1;
				}
				else if (white == true && coordPiece.getPlayer().equals(node.getGame().getWhitePlayer()))
				{
					howMany += 1;
				}
			}
			if (coorFour == true)
			{
				Piece coordPiece = node.getGame().getBoard().getPieceAtPosition(importantCoorFour);
				if (black == true && coordPiece.getPlayer().equals(node.getGame().getBlackPlayer()))
				{
					howMany += 1;
				}
				else if (white == true && coordPiece.getPlayer().equals(node.getGame().getWhitePlayer()))
				{
					howMany += 1;
				}
			}
			howMany = Math.max(howMany, 0);
			return howMany;
		}

		// pawn structure that is good -- diagonal pawns -- Good Thing!
		// each good pawn is worth 0.5 points
		public static double goodDiagonalPawn(DFSTreeNode node)
		{
			double count = 0.0;
			Set<Piece> pawnPiece = node.getGame().getBoard().getPieces(CustomHeuristics.getMaxPlayer(node), PieceType.PAWN);
			
			for(Piece piece : pawnPiece)
			{

					Coordinate pawnPos = piece.getCurrentPosition(node.getGame().getBoard());
					
					
					int xCoor = pawnPos.getXPosition();
					int yCoor = pawnPos.getYPosition();
					Coordinate movePawnOne = new Coordinate(xCoor+1, yCoor+1);
					Coordinate movePawnTwo = new Coordinate(xCoor+1, yCoor-1);
					Coordinate movePawnThree = new Coordinate(xCoor-1, yCoor+1);
					Coordinate movePawnFour = new Coordinate(xCoor-1, yCoor-1);

					
						if (node.getGame().getBoard().isInbounds(movePawnOne))
						{
							if(node.getGame().getBoard().isPositionOccupied(movePawnOne))
							{
								if(node.getGame().getBoard().getPieceAtPosition(movePawnOne).getType().equals(PieceType.PAWN)
								&& !piece.isEnemyPiece(node.getGame().getBoard().getPieceAtPosition(movePawnOne)))
								{
									count += 0.5;
									continue;
								}
							}
						}
						if (node.getGame().getBoard().isInbounds(movePawnTwo))
						{
							if(node.getGame().getBoard().isPositionOccupied(movePawnTwo))
							{
								if(node.getGame().getBoard().getPieceAtPosition(movePawnTwo).getType().equals(PieceType.PAWN)
								&& !piece.isEnemyPiece(node.getGame().getBoard().getPieceAtPosition(movePawnTwo)))
								{
									count += 0.5;
									continue;
								}
							}
						}
						if (node.getGame().getBoard().isInbounds(movePawnThree))
						{
							if(node.getGame().getBoard().isPositionOccupied(movePawnThree))
							{
								if(node.getGame().getBoard().getPieceAtPosition(movePawnThree).getType().equals(PieceType.PAWN)
								&& !piece.isEnemyPiece(node.getGame().getBoard().getPieceAtPosition(movePawnThree)))
								{
									count += 0.5;
									continue;
								}
							}
						}
						if (node.getGame().getBoard().isInbounds(movePawnFour))
						{
							if(node.getGame().getBoard().isPositionOccupied(movePawnFour))
							{
								if(node.getGame().getBoard().getPieceAtPosition(movePawnFour).getType().equals(PieceType.PAWN)
								&& !piece.isEnemyPiece(node.getGame().getBoard().getPieceAtPosition(movePawnFour)))
								{
									count += 0.5;
									continue;
								}
							}
						}
					}


					
				
			count = Math.max(count,0.0);
			return count;
		}
		
		//pawn Structure that is bad -- adjacent pawns -- Bad Thing!
		// each bad Pawn is worth -0.5 points
		public static double badAdjacentPawn(DFSTreeNode node)
		{
			double howMany = 0.0;
			Set<Piece> pawnPiece = node.getGame().getBoard().getPieces(CustomHeuristics.getMaxPlayer(node), PieceType.PAWN);

			for(Piece piece : pawnPiece)
			{

					Coordinate pawnPos = piece.getCurrentPosition(node.getGame().getBoard());
			
					int xCoor = pawnPos.getXPosition();
					int yCoor = pawnPos.getYPosition();
					Coordinate movePawnOne = new Coordinate(xCoor, yCoor+1);
					Coordinate movePawnFour = new Coordinate(xCoor, yCoor-1);


						if (node.getGame().getBoard().isInbounds(movePawnOne))
						{
							if(node.getGame().getBoard().isPositionOccupied(movePawnOne))
							{
								if(node.getGame().getBoard().getPieceAtPosition(movePawnOne).getType().equals(PieceType.PAWN)
								&& !piece.isEnemyPiece(node.getGame().getBoard().getPieceAtPosition(movePawnOne)))
								{
									howMany += 0.5;
									continue;
								}
							}
						}
						if (node.getGame().getBoard().isInbounds(movePawnFour))
						{
							if(node.getGame().getBoard().isPositionOccupied(movePawnFour))
							{
								if(node.getGame().getBoard().getPieceAtPosition(movePawnFour).getType().equals(PieceType.PAWN)
								&& !piece.isEnemyPiece(node.getGame().getBoard().getPieceAtPosition(movePawnFour)))
								{
									howMany += 0.5;
									continue;
								}
							}
						}
					}
			howMany = Math.max(howMany,0.0);
			return howMany;
		}

		//pawn that can reach the end to transform -- Good Thing!
		// if it is above halfpoint, add 1.
		// if it is above halfpoint and at 6th or 7th (or 1st or 2nd depending on the color) row, add 2
		public static int pawnTransform(DFSTreeNode node)
		{
			int specialPawn = 0;
			Set<Piece> pawnPiece = node.getGame().getBoard().getPieces(CustomHeuristics.getMaxPlayer(node), PieceType.PAWN);

			for(Piece piece : pawnPiece)
			{

					if(piece.getPlayer().equals(node.getGame().getBlackPlayer()))
					{
						Coordinate coord = piece.getCurrentPosition(node.getGame().getBoard());
						int xcoor = coord.getXPosition();
						int ycoor = coord.getYPosition();
						if(ycoor == 5 || ycoor == 6 || ycoor == 7)
						{
							if (node.getGame().getBoard().isPositionAvailable(new Coordinate(xcoor, ycoor+1))
							|| piece.getAllCaptureMoves(node.getGame()).size() > 0)
							{
								if (ycoor == 5)
								{
									specialPawn += 1;
								}
								else if(ycoor == 6 || ycoor == 7)
								{
									specialPawn += 2;
								}
								
							}
						}
					}
					else
					{
						Coordinate coord = piece.getCurrentPosition(node.getGame().getBoard());
						int xcoor = coord.getXPosition();
						int ycoor = coord.getYPosition();
						if(ycoor == 4 || ycoor == 3 || ycoor == 2)
						{
							if (node.getGame().getBoard().isPositionAvailable(new Coordinate(xcoor, ycoor-1))
							|| piece.getAllCaptureMoves(node.getGame()).size() > 0)
							{
								if(ycoor == 4)
								{
									specialPawn += 1;
								}
								else if (ycoor == 3 || ycoor == 2)
								{
									specialPawn += 2;
								}
							}
						}
					}
				}
			
			specialPawn = Math.max(specialPawn, 0);
			return specialPawn;
		}


		//knight that is other half of the square and cannot be attacked by enemy pawns -- Good Thing!
		//each knight is worth 1.5 points 
		public static double knightAttackOnly(DFSTreeNode node)
		{
			double specialKnight = 0.0;
			Set<Piece> knightPiece = node.getGame().getBoard().getPieces(CustomHeuristics.getMaxPlayer(node), PieceType.PAWN);
			Set<Piece> pawnPieceOfEnemy = node.getGame().getBoard().getPieces(CustomHeuristics.getMinPlayer(node),PieceType.PAWN);

			for(Piece piece : knightPiece)
			{
					if(piece.getPlayer().equals(node.getGame().getBlackPlayer()))
					{
						Coordinate coord = piece.getCurrentPosition(node.getGame().getBoard());
						int ycoor = coord.getYPosition();
						boolean canAttack = false;
						if(ycoor >= 5)
						{
							for (Piece enemyPiece: pawnPieceOfEnemy)
							{
								List<Move> enemyPawnCapture = enemyPiece.getAllCaptureMoves(node.getGame());
								for (Move m: enemyPawnCapture)
								{
									Coordinate coordinateToCheck = node.getGame().getBoard().applyMove(m).getPiecePosition(piece);
									if(coordinateToCheck != null && coordinateToCheck.equals(coord))
									{
										canAttack = true;
										break;
									}
								}
								if(canAttack == false)
								{
									specialKnight += 1.5;
								}
							}
						}
					}
					else
					{
						Coordinate coord = piece.getCurrentPosition(node.getGame().getBoard());
						int ycoor = coord.getYPosition();
						boolean canAttack = false;
						if(ycoor <= 4)
						{
							for (Piece enemyPiece: pawnPieceOfEnemy)
							{
								List<Move> enemyPawnCapture = enemyPiece.getAllCaptureMoves(node.getGame());
								for (Move m: enemyPawnCapture)
								{
									Coordinate coordinateToCheck = node.getGame().getBoard().applyMove(m).getPiecePosition(piece);
									
									if(coordinateToCheck != null && coordinateToCheck.equals(coord))
									{
										canAttack = true;
										break;
									}
								}
								if(canAttack == false)
								{
									specialKnight += 1.5;
								}
							}
						}
					}
				}
			specialKnight = Math.max(0.0, specialKnight);
			return specialKnight;
		}

		//rook that does not have any enemy/ally in the path -- Good Thing!
		// for each rook, if ally exists, 0
		// for each rook, if enemy exists, +0.5
		// for each rook, if none exists, + 1
		// doubled if there is no ally on both rook
		public static double emptyRook(DFSTreeNode node)
		{
			double specialRook = 0.0;
			Set<Piece> rookPiece = node.getGame().getBoard().getPieces(DefaultHeuristics.getMaxPlayer(node), PieceType.ROOK);
			int enemyOnly = 0;
			int ally = 0;
			boolean shouldIDouble = true;
			for(Piece rp: rookPiece)
			{
				enemyOnly = 0;
				ally = 0;
				Coordinate rookPosition = node.getGame().getCurrentPosition(rp);
				int xcoor = rookPosition.getXPosition();
				int ycoor = rookPosition.getYPosition();
				for (int x = 1; x < 9; x++)
				{
					if (x == xcoor)
					{
						continue;
					}
					Piece pieceOnWay = node.getGame().getBoard().getPieceAtPosition(new Coordinate(x, ycoor));
					if (pieceOnWay != null && rp.isEnemyPiece(pieceOnWay))
					{
						enemyOnly += 1;
					}
					if (pieceOnWay != null && !rp.isEnemyPiece(pieceOnWay))
					{
						ally += 1;
						break;
					}
				}
				if(ally == 0)
				{
					shouldIDouble = false;
					if(enemyOnly == 0)
					{
						specialRook += 1.0;
					}
					else
					{
						specialRook += 0.5;
					}
				}
			}
			if(shouldIDouble)
			{
				specialRook *= 2;
			}
			specialRook = Math.max(0.0, specialRook);
			return specialRook;
		}

		//units that are defendable by our pawns -- Good Thing!
		public static double defendableByPawn(DFSTreeNode node)
		{
			double defend = 0.0;
			Set<Piece> allPieces = node.getGame().getBoard().getPieces(CustomHeuristics.getMaxPlayer(node));

			for (Piece piece: allPieces)
			{
				if(piece.getType().equals(PieceType.PAWN))
				{
					continue;
				}
				Coordinate pawnCoord = node.getGame().getBoard().getPiecePosition(piece);
				int xcoor = pawnCoord.getXPosition();
				int ycoor = pawnCoord.getYPosition();
				if(piece.getPlayer().equals(node.getGame().getBlackPlayer()))
				{
					Coordinate checkPawn = new Coordinate(xcoor-1, ycoor-1);
					Coordinate checkPawnTwo = new Coordinate(xcoor+1, ycoor-1);
					if(node.getGame().getBoard().isInbounds(checkPawn) && node.getGame().getBoard().isPositionOccupied(checkPawn))
					{
						if(node.getGame().getBoard().getPieceAtPosition(checkPawn).getType().equals(PieceType.PAWN)
						&& !node.getGame().getBoard().getPieceAtPosition(checkPawn).isEnemyPiece(piece))
						{
							defend += 1.0;
							continue;
						}
					}
					else if (node.getGame().getBoard().isInbounds(checkPawnTwo) && node.getGame().getBoard().isPositionOccupied(checkPawnTwo))
					{
						if(node.getGame().getBoard().getPieceAtPosition(checkPawnTwo).getType().equals(PieceType.PAWN)
						&& !node.getGame().getBoard().getPieceAtPosition(checkPawnTwo).isEnemyPiece(piece))
						{
							defend += 1.0;
							continue;
						}
					}
				}
				else
				{
					Coordinate checkPawn = new Coordinate(xcoor-1, ycoor+1);
					Coordinate checkPawnTwo = new Coordinate(xcoor+1, ycoor+1);
					if(node.getGame().getBoard().isInbounds(checkPawn) && node.getGame().getBoard().isPositionOccupied(checkPawn))
					{
						if(node.getGame().getBoard().getPieceAtPosition(checkPawn).getType().equals(PieceType.PAWN)
						&& !node.getGame().getBoard().getPieceAtPosition(checkPawn).isEnemyPiece(piece))
						{
							defend += 1.0;
							continue;
						}
					}
					else if (node.getGame().getBoard().isInbounds(checkPawnTwo) && node.getGame().getBoard().isPositionOccupied(checkPawnTwo))
					{
						if(node.getGame().getBoard().getPieceAtPosition(checkPawnTwo).getType().equals(PieceType.PAWN)
						&& !node.getGame().getBoard().getPieceAtPosition(checkPawnTwo).isEnemyPiece(piece))
						{
							defend += 1.0;
							continue;
						}
					}
				}
			
			}
			defend = Math.max(0.0, defend);
			return defend;	
		}

		//rook or queen located on 7th row (black) or 2nd row (white) -- Good Thing!
		// 2 points for each rook or queen
		public static int seventhRow(DFSTreeNode node)
		{
			int value = 0;
			Set<Piece> queenPieces = node.getGame().getBoard().getPieces(CustomHeuristics.getMaxPlayer(node), PieceType.QUEEN);
			Set<Piece> rookPieces = node.getGame().getBoard().getPieces(CustomHeuristics.getMaxPlayer(node), PieceType.ROOK);
			for(Piece q: queenPieces)
			{
				if (q.getPlayer().equals(node.getGame().getBlackPlayer()))
				{
					int yCoor = node.getGame().getBoard().getPiecePosition(q).getYPosition();
					if(yCoor == 7)
					{
						value += 2;
					}
				}
				else
				{
					int yCoor = node.getGame().getBoard().getPiecePosition(q).getYPosition();
					if (yCoor == 2)
					{
						value += 2;
					}
				}
			}

			for(Piece r: rookPieces)
			{
				if (r.getPlayer().equals(node.getGame().getBlackPlayer()))
				{
					int yCoor = node.getGame().getBoard().getPiecePosition(r).getYPosition();
					if(yCoor == 7)
					{
						value += 2;
					}
				}
				else
				{
					int yCoor = node.getGame().getBoard().getPiecePosition(r).getYPosition();
					if (yCoor == 2)
					{
						value += 2;
					}
				}
			}
			value = Math.max(0, value);
			return value;
		}

		//from professor -- more units are better
		public static double getNonlinearPieceCombinationMaxPlayerHeuristicValue(DFSTreeNode node)
		{
		// both bishops are worth more together than a single bishop alone
		// same with knights...we want to encourage keeping pairs of elements
			double multiPieceValueTotal = 0.0;

			double exponent = 1.5; // f(numberOfKnights) = (numberOfKnights)^exponent

		// go over all the piece types that have more than one copy in the game (including pawn promotion)
			for(PieceType pieceType : new PieceType[] {PieceType.BISHOP, PieceType.KNIGHT, PieceType.ROOK, PieceType.QUEEN})
			{
				multiPieceValueTotal += Math.pow(node.getGame().getNumberOfAlivePieces(DefaultHeuristics.getMaxPlayer(node), pieceType), exponent);
			}
			multiPieceValueTotal = Math.max(multiPieceValueTotal, 0.0);
			return multiPieceValueTotal;
		}

		//number of pieces surround my king -- Good Thing!
		public static int getClampedPieceValueTotalSurroundingMaxPlayersKing(DFSTreeNode node)
		{
			// what is the state of the pieces next to the king? add up the values of the neighboring pieces
			// positive value for friendly pieces and negative value for enemy pieces (will clamp at 0)
			int maxPlayerKingSurroundingPiecesValueTotal = 0;

			Piece kingPiece = node.getGame().getBoard().getPieces(CustomHeuristics.getMaxPlayer(node), PieceType.KING).iterator().next();
			Coordinate kingPosition = node.getGame().getCurrentPosition(kingPiece);
			for(Direction direction : Direction.values())
			{
				Coordinate neightborPosition = kingPosition.getNeighbor(direction);
				if(node.getGame().getBoard().isInbounds(neightborPosition) && node.getGame().getBoard().isPositionOccupied(neightborPosition))
				{
					Piece piece = node.getGame().getBoard().getPieceAtPosition(neightborPosition);
					int pieceValue = Piece.getPointValue(piece.getType());
					if(piece != null && kingPiece.isEnemyPiece(piece))
					{
						maxPlayerKingSurroundingPiecesValueTotal -= pieceValue;
					} else if(piece != null && !kingPiece.isEnemyPiece(piece))
					{
						maxPlayerKingSurroundingPiecesValueTotal += pieceValue;
					}
				}
			}
			// kingSurroundingPiecesValueTotal cannot be < 0 b/c the utility of losing a game is 0, so all of our utility values should be at least 0
			maxPlayerKingSurroundingPiecesValueTotal = Math.max(maxPlayerKingSurroundingPiecesValueTotal, 0);
			return maxPlayerKingSurroundingPiecesValueTotal;
		}
	}


























	public static class DefensiveHeuristics extends Object
	{
		//how many players/pieces can the enemy attack -- Bad Thing!
		//each unit is worth 0.5 points
		public static double getNumberOfPiecesMinPlayerIsThreatening(DFSTreeNode node)
		{
 
			 double numPiecesMinPlayerIsThreatening = 0.0;
			 for(Piece piece : node.getGame().getBoard().getPieces(CustomHeuristics.getMinPlayer(node)))
			 {
				 numPiecesMinPlayerIsThreatening += piece.getAllCaptureMoves(node.getGame()).size() * 0.5;
			 }
			 numPiecesMinPlayerIsThreatening = Math.max(numPiecesMinPlayerIsThreatening, 0.0);
			 return numPiecesMinPlayerIsThreatening;
		}

		 //how many players/pieces can the enemy attack with their associated values -- Bad Thing!
		 // each move is 0.5 * the weight of the pieces
		public static double getWeightedNumberOfPiecesMinPlayerIsThreatening(DFSTreeNode node)
		{
			
			double weightedVal = 0; 
			Set<Piece> enemy = node.getGame().getBoard().getPieces(CustomHeuristics.getMaxPlayer(node));
			Set<Coordinate> allCoordinatesEnemyCanReach = new HashSet<Coordinate>();
			for(Piece e: enemy)
			{
				List<Move> allMoves = e.getAllMoves(node.getGame());
				for(Move m: allMoves)
				{
					Coordinate coordReach = node.getGame().getBoard().applyMove(m).getPiecePosition(e);
					allCoordinatesEnemyCanReach.add(coordReach);
				}
			}
			for(Piece piece : node.getGame().getBoard().getPieces(CustomHeuristics.getMinPlayer(node)))
			{
				List<Move> captureMoves = piece.getAllCaptureMoves(node.getGame());
				for(Move cm: captureMoves)
				{
					Coordinate attack = node.getGame().getBoard().applyMove(cm).getPiecePosition(piece);
					PieceType thePieceThere = node.getGame().getBoard().getPieceAtPosition(attack).getType();
					if(thePieceThere.equals(PieceType.QUEEN))
					{
						if(allCoordinatesEnemyCanReach.contains(attack))
						{
							if(piece.getType().equals(PieceType.KING))
							{
								weightedVal += (queenVal - kingVal) * 0.5;
							}
							else if (piece.getType().equals(PieceType.QUEEN))
							{
								weightedVal += (queenVal - queenVal) * 0.5;
							}
							else if (piece.getType().equals(PieceType.ROOK))
							{
								weightedVal += (queenVal - rookVal) * 0.5;
							}
							else if (piece.getType().equals(PieceType.KNIGHT))
							{
								weightedVal += (queenVal - knightVal) * 0.5;
							}
							else if (piece.getType().equals(PieceType.BISHOP))
							{
								weightedVal += (queenVal - bishopVal) * 0.5;
							}
							else
							{
								weightedVal += (queenVal - pawnVal) * 0.5;
							}
						}
						else
						{
							weightedVal += queenVal * 0.5;
						}
					}
					else if(thePieceThere.equals(PieceType.ROOK))
					{
						if(allCoordinatesEnemyCanReach.contains(attack))
						{
							if(piece.getType().equals(PieceType.KING))
							{
								weightedVal += (rookVal - kingVal) * 0.5;
							}
							else if (piece.getType().equals(PieceType.QUEEN))
							{
								weightedVal += (rookVal - queenVal) * 0.5;
							}
							else if (piece.getType().equals(PieceType.ROOK))
							{
								weightedVal += (rookVal - rookVal) * 0.5;
							}
							else if (piece.getType().equals(PieceType.KNIGHT))
							{
								weightedVal += (rookVal - knightVal) * 0.5;
							}
							else if (piece.getType().equals(PieceType.BISHOP))
							{
								weightedVal += (rookVal - bishopVal) * 0.5;
							}
							else
							{
								weightedVal += (rookVal - pawnVal) * 0.5;
							}
						}
						else
						{
							weightedVal += rookVal * 0.5;
						}
					}
					else if(thePieceThere.equals(PieceType.KNIGHT))
					{
						if(allCoordinatesEnemyCanReach.contains(attack))
						{
							if(piece.getType().equals(PieceType.KING))
							{
								weightedVal += (knightVal - kingVal) * 0.5;
							}
							else if (piece.getType().equals(PieceType.QUEEN))
							{
								weightedVal += (knightVal - queenVal) * 0.5;
							}
							else if (piece.getType().equals(PieceType.ROOK))
							{
								weightedVal += (knightVal - rookVal) * 0.5;
							}
							else if (piece.getType().equals(PieceType.KNIGHT))
							{
								weightedVal += (knightVal - knightVal) * 0.5;
							}
							else if (piece.getType().equals(PieceType.BISHOP))
							{
								weightedVal += (knightVal - bishopVal) * 0.5;
							}
							else
							{
								weightedVal += (knightVal - pawnVal) * 0.5;
							}
						}
						else
						{
							weightedVal += knightVal * 0.5;
						}
					}
					
					else if(thePieceThere.equals(PieceType.BISHOP))
					{
						if(allCoordinatesEnemyCanReach.contains(attack))
						{
							if(piece.getType().equals(PieceType.KING))
							{
								weightedVal += (bishopVal - kingVal) * 0.5;
							}
							else if (piece.getType().equals(PieceType.QUEEN))
							{
								weightedVal += (bishopVal - queenVal) * 0.5;
							}
							else if (piece.getType().equals(PieceType.ROOK))
							{
								weightedVal += (bishopVal - rookVal) * 0.5;
							}
							else if (piece.getType().equals(PieceType.KNIGHT))
							{
								weightedVal += (bishopVal - knightVal) * 0.5;
							}
							else if (piece.getType().equals(PieceType.BISHOP))
							{
								weightedVal += (bishopVal - bishopVal) * 0.5;
							}
							else
							{
								weightedVal += (bishopVal - pawnVal) * 0.5;
							}
						}
						else
						{
							weightedVal += bishopVal * 0.5;
						}
					}
					else if(thePieceThere.equals(PieceType.PAWN))
					{
						if(allCoordinatesEnemyCanReach.contains(attack))
						{
							if(piece.getType().equals(PieceType.KING))
							{
								weightedVal += (pawnVal - kingVal) * 0.5;
							}
							else if (piece.getType().equals(PieceType.QUEEN))
							{
								weightedVal += (pawnVal - queenVal) * 0.5;
							}
							else if (piece.getType().equals(PieceType.ROOK))
							{
								weightedVal += (pawnVal - rookVal) * 0.5;
							}
							else if (piece.getType().equals(PieceType.KNIGHT))
							{
								weightedVal += (pawnVal - knightVal) * 0.5;
							}
							else if (piece.getType().equals(PieceType.BISHOP))
							{
								weightedVal += (pawnVal - bishopVal) * 0.5;
							}
							else
							{
								weightedVal += (pawnVal - pawnVal) * 0.5;
							}
						}
						else
						{
							weightedVal += pawnVal * 0.5;
						}
					}
					else
					{
						weightedVal += kingVal;
					}
				}

			}
			weightedVal = Math.max(weightedVal * 2, 0.0);
			return weightedVal;
		}

		//how many pieces of the board can the enemy move to -- Bad Thing!
		// each coordinate is worth 0.1 points
		public static double howManyBoards(DFSTreeNode node)
		{
			Set<Coordinate> checkDuplicate = new HashSet<Coordinate>();
			double howMany = 0;
			for(Piece piece : node.getGame().getBoard().getPieces(CustomHeuristics.getMinPlayer(node)))
			{
				List<Move> movesList = piece.getAllMoves(node.getGame());
				for(Move m: movesList)
				{
					Coordinate coord = node.getGame().getBoard().applyMove(m).getPiecePosition(piece);
					for(Coordinate c: allCoor)
					{
						if(coord.equals(c) && !checkDuplicate.contains(c))
						{
							howMany += 0.1;
							checkDuplicate.add(c);
						}
					}
				}
			}
			howMany = Math.max(howMany, 0.0);
			return howMany;
		}
		

		// Check how many of enemy pieces are at the center -- Bad Thing!
		// each is worth one point
		public static int howManyPiecesOnImportantCoordinates(DFSTreeNode node)
		{
			int howMany = 0;
			boolean coorOne = node.getGame().getBoard().isPositionOccupied(importantCoorOne);
			boolean coorTwo = node.getGame().getBoard().isPositionOccupied(importantCoorTwo);
			boolean coorThree = node.getGame().getBoard().isPositionOccupied(importantCoorThree);
			boolean coorFour = node.getGame().getBoard().isPositionOccupied(importantCoorFour);
			Set<Piece> checkMyColor = node.getGame().getBoard().getPieces(CustomHeuristics.getMinPlayer(node));
			boolean black = false;
			boolean white = false;
			for(Piece p: checkMyColor)
			{
				if (p.getPlayer().equals(node.getGame().getBlackPlayer()))
				{
					black = true;
				}
				else
				{
					white = true;
				}
				break;
			}

			if (coorOne == true)
			{
				Piece coordPiece = node.getGame().getBoard().getPieceAtPosition(importantCoorOne);
				if (black == true && coordPiece.getPlayer().equals(node.getGame().getBlackPlayer()))
				{
					howMany += 1;
				}
				else if (white == true && coordPiece.getPlayer().equals(node.getGame().getWhitePlayer()))
				{
					howMany += 1;
				}

			}
			if (coorTwo == true)
			{
				Piece coordPiece = node.getGame().getBoard().getPieceAtPosition(importantCoorTwo);
				if (black == true && coordPiece.getPlayer().equals(node.getGame().getBlackPlayer()))
				{
					howMany += 1;
				}
				else if (white == true && coordPiece.getPlayer().equals(node.getGame().getWhitePlayer()))
				{
					howMany += 1;
				}
			}
			if (coorThree == true)
			{
				Piece coordPiece = node.getGame().getBoard().getPieceAtPosition(importantCoorThree);
				if (black == true && coordPiece.getPlayer().equals(node.getGame().getBlackPlayer()))
				{
					howMany += 1;
				}
				else if (white == true && coordPiece.getPlayer().equals(node.getGame().getWhitePlayer()))
				{
					howMany += 1;
				}
			}
			if (coorFour == true)
			{
				Piece coordPiece = node.getGame().getBoard().getPieceAtPosition(importantCoorFour);
				if (black == true && coordPiece.getPlayer().equals(node.getGame().getBlackPlayer()))
				{
					howMany += 1;
				}
				else if (white == true && coordPiece.getPlayer().equals(node.getGame().getWhitePlayer()))
				{
					howMany += 1;
				}
			}
			howMany = Math.max(howMany, 0);
			return howMany;
		}

		// pawn structure that is good for enemy-- diagonal pawns -- Bad Thing!
		// each good pawn is worth 0.5 points
		public static double goodDiagonalPawn(DFSTreeNode node)
		{
			double count = 0.0;
			Set<Piece> pawnPiece = node.getGame().getBoard().getPieces(CustomHeuristics.getMinPlayer(node), PieceType.PAWN);
			
			for(Piece piece : pawnPiece)
			{

					Coordinate pawnPos = piece.getCurrentPosition(node.getGame().getBoard());
					
					
					int xCoor = pawnPos.getXPosition();
					int yCoor = pawnPos.getYPosition();
					Coordinate movePawnOne = new Coordinate(xCoor+1, yCoor+1);
					Coordinate movePawnTwo = new Coordinate(xCoor+1, yCoor-1);
					Coordinate movePawnThree = new Coordinate(xCoor-1, yCoor+1);
					Coordinate movePawnFour = new Coordinate(xCoor-1, yCoor-1);

					
						if (node.getGame().getBoard().isInbounds(movePawnOne))
						{
							if(node.getGame().getBoard().isPositionOccupied(movePawnOne))
							{
								if(node.getGame().getBoard().getPieceAtPosition(movePawnOne).getType().equals(PieceType.PAWN)
								&& !piece.isEnemyPiece(node.getGame().getBoard().getPieceAtPosition(movePawnOne)))
								{
									count += 0.5;
									continue;
								}
							}
						}
						if (node.getGame().getBoard().isInbounds(movePawnTwo))
						{
							if(node.getGame().getBoard().isPositionOccupied(movePawnTwo))
							{
								if(node.getGame().getBoard().getPieceAtPosition(movePawnTwo).getType().equals(PieceType.PAWN)
								&& !piece.isEnemyPiece(node.getGame().getBoard().getPieceAtPosition(movePawnTwo)))
								{
									count += 0.5;
									continue;
								}
							}
						}
						if (node.getGame().getBoard().isInbounds(movePawnThree))
						{
							if(node.getGame().getBoard().isPositionOccupied(movePawnThree))
							{
								if(node.getGame().getBoard().getPieceAtPosition(movePawnThree).getType().equals(PieceType.PAWN)
								&& !piece.isEnemyPiece(node.getGame().getBoard().getPieceAtPosition(movePawnThree)))
								{
									count += 0.5;
									continue;
								}
							}
						}
						if (node.getGame().getBoard().isInbounds(movePawnFour))
						{
							if(node.getGame().getBoard().isPositionOccupied(movePawnFour))
							{
								if(node.getGame().getBoard().getPieceAtPosition(movePawnFour).getType().equals(PieceType.PAWN)
								&& !piece.isEnemyPiece(node.getGame().getBoard().getPieceAtPosition(movePawnFour)))
								{
									count += 0.5;
									continue;
								}
							}
						}
					}


					
				
			count = Math.max(count, 0.0);
			return count;
		}
		
		//pawn Structure that is bad for enemy -- adjacent pawns -- Good Thing!
		// each bad Pawn is worth -0.5 points
		public static double badAdjacentPawn(DFSTreeNode node)
		{
			double howMany = 0.0;
			Set<Piece> pawnPiece = node.getGame().getBoard().getPieces(CustomHeuristics.getMinPlayer(node), PieceType.PAWN);

			for(Piece piece : pawnPiece)
			{

					Coordinate pawnPos = piece.getCurrentPosition(node.getGame().getBoard());
			
					int xCoor = pawnPos.getXPosition();
					int yCoor = pawnPos.getYPosition();
					Coordinate movePawnOne = new Coordinate(xCoor, yCoor+1);
					Coordinate movePawnFour = new Coordinate(xCoor, yCoor-1);


						if (node.getGame().getBoard().isInbounds(movePawnOne))
						{
							if(node.getGame().getBoard().isPositionOccupied(movePawnOne))
							{
								if(node.getGame().getBoard().getPieceAtPosition(movePawnOne).getType().equals(PieceType.PAWN)
								&& !piece.isEnemyPiece(node.getGame().getBoard().getPieceAtPosition(movePawnOne)))
								{
									howMany += 0.5;
									continue;
								}
							}
						}
						if (node.getGame().getBoard().isInbounds(movePawnFour))
						{
							if(node.getGame().getBoard().isPositionOccupied(movePawnFour))
							{
								if(node.getGame().getBoard().getPieceAtPosition(movePawnFour).getType().equals(PieceType.PAWN)
								&& !piece.isEnemyPiece(node.getGame().getBoard().getPieceAtPosition(movePawnFour)))
								{
									howMany += 0.5;
									continue;
								}
							}
						}
					}
			howMany = Math.max(howMany, 0.0);
			return howMany;
		}

		//pawn that can reach the end to transform for enemy -- Bad Thing!
		// if it is above halfpoint, add 1.
		// if it is above halfpoint and at 6th or 7th (or 1st or 2nd depending on the color) row, add 2
		public static int pawnTransform(DFSTreeNode node)
		{
			int specialPawn = 0;
			Set<Piece> pawnPiece = node.getGame().getBoard().getPieces(CustomHeuristics.getMinPlayer(node), PieceType.PAWN);

			for(Piece piece : pawnPiece)
			{

					if(piece.getPlayer().equals(node.getGame().getBlackPlayer()))
					{
						Coordinate coord = piece.getCurrentPosition(node.getGame().getBoard());
						int xcoor = coord.getXPosition();
						int ycoor = coord.getYPosition();
						if(ycoor == 5 || ycoor == 6 || ycoor == 7)
						{
							if (node.getGame().getBoard().isPositionAvailable(new Coordinate(xcoor, ycoor+1))
							|| piece.getAllCaptureMoves(node.getGame()).size() > 0)
							{
								if (ycoor == 5)
								{
									specialPawn += 1;
								}
								else if(ycoor == 6 || ycoor == 7)
								{
									specialPawn += 2;
								}
								
							}
						}
					}
					else
					{
						Coordinate coord = piece.getCurrentPosition(node.getGame().getBoard());
						int xcoor = coord.getXPosition();
						int ycoor = coord.getYPosition();
						if(ycoor == 4 || ycoor == 3 || ycoor == 2)
						{
							if (node.getGame().getBoard().isPositionAvailable(new Coordinate(xcoor, ycoor-1))
							|| piece.getAllCaptureMoves(node.getGame()).size() > 0)
							{
								if(ycoor == 4)
								{
									specialPawn += 1;
								}
								else if (ycoor == 3 || ycoor == 2)
								{
									specialPawn += 2;
								}
							}
						}
					}
				}
			
			specialPawn = Math.max(specialPawn, 0);
			return specialPawn;
		}


		//knight that is other half of the square and cannot be attacked by our pawns -- Bad Thing!
		//each knight is worth 1.5 points 
		public static double knightAttackOnly(DFSTreeNode node)
		{
			double specialKnight = 0.0;
			Set<Piece> knightPiece = node.getGame().getBoard().getPieces(CustomHeuristics.getMinPlayer(node), PieceType.PAWN);
			Set<Piece> pawnPieceOfEnemy = node.getGame().getBoard().getPieces(CustomHeuristics.getMaxPlayer(node),PieceType.PAWN);

			for(Piece piece : knightPiece)
			{
					if(piece.getPlayer().equals(node.getGame().getBlackPlayer()))
					{
						Coordinate coord = piece.getCurrentPosition(node.getGame().getBoard());
						int ycoor = coord.getYPosition();
						boolean canAttack = false;
						if(ycoor >= 5)
						{
							for (Piece enemyPiece: pawnPieceOfEnemy)
							{
								List<Move> enemyPawnCapture = enemyPiece.getAllCaptureMoves(node.getGame());
								for (Move m: enemyPawnCapture)
								{
									Coordinate coordinateToCheck = node.getGame().getBoard().applyMove(m).getPiecePosition(piece);
									if(coordinateToCheck != null && coordinateToCheck.equals(coord))
									{
										canAttack = true;
										break;
									}
								}
								if(canAttack == false)
								{
									specialKnight += 1.5;
								}
							}
						}
					}
					else
					{
						Coordinate coord = piece.getCurrentPosition(node.getGame().getBoard());
						int ycoor = coord.getYPosition();
						boolean canAttack = false;
						if(ycoor <= 4)
						{
							for (Piece enemyPiece: pawnPieceOfEnemy)
							{
								List<Move> enemyPawnCapture = enemyPiece.getAllCaptureMoves(node.getGame());
								for (Move m: enemyPawnCapture)
								{
									Coordinate coordinateToCheck = node.getGame().getBoard().applyMove(m).getPiecePosition(piece);
									if(coordinateToCheck != null && coordinateToCheck.equals(coord))
									{
										canAttack = true;
										break;
									}
								}
								if(canAttack == false)
								{
									specialKnight += 1.5;
								}
							}
						}
					}
				}
			specialKnight = Math.max(specialKnight, 0.0);
			return specialKnight;
		}

		//rook that does not have any enemy/ally in the path -- Bad Thing!
		// for each rook, if ally exists, 0
		// for each rook, if enemy exists, +0.5
		// for each rook, if none exists, + 1
		// doubled if there is no ally on both rook
		public static double emptyRook(DFSTreeNode node)
		{
			double specialRook = 0.0;
			Set<Piece> rookPiece = node.getGame().getBoard().getPieces(DefaultHeuristics.getMinPlayer(node), PieceType.ROOK);
			int enemyOnly = 0;
			int ally = 0;
			boolean shouldIDouble = true;
			for(Piece rp: rookPiece)
			{
				enemyOnly = 0;
				ally = 0;
				Coordinate rookPosition = node.getGame().getCurrentPosition(rp);
				int xcoor = rookPosition.getXPosition();
				int ycoor = rookPosition.getYPosition();
				for (int x = 1; x < 9; x++)
				{
					if (x == xcoor)
					{
						continue;
					}
					Piece pieceOnWay = node.getGame().getBoard().getPieceAtPosition(new Coordinate(x, ycoor));
					if (pieceOnWay != null && rp.isEnemyPiece(pieceOnWay))
					{
						enemyOnly += 1;
					}
					if (pieceOnWay != null && !rp.isEnemyPiece(pieceOnWay))
					{
						ally += 1;
						break;
					}
				}
				if(ally == 0)
				{
					shouldIDouble = false;
					if(enemyOnly == 0)
					{
						specialRook += 1.0;
					}
					else
					{
						specialRook += 0.5;
					}
				}
			}
			if(shouldIDouble)
			{
				specialRook *= 2;
			}
			specialRook = Math.max(specialRook, 0.0);
			return specialRook;
		}

		//units that are defendable by the enemy pawns -- Bad Thing!
		public static double defendableByPawn(DFSTreeNode node)
		{
			double defend = 0.0;
			Set<Piece> allPieces = node.getGame().getBoard().getPieces(CustomHeuristics.getMinPlayer(node));

			for (Piece piece: allPieces)
			{
				if(piece.getType().equals(PieceType.PAWN))
				{
					continue;
				}
				Coordinate pawnCoord = node.getGame().getBoard().getPiecePosition(piece);
				int xcoor = pawnCoord.getXPosition();
				int ycoor = pawnCoord.getYPosition();
				if(piece.getPlayer().equals(node.getGame().getBlackPlayer()))
				{
					Coordinate checkPawn = new Coordinate(xcoor-1, ycoor-1);
					Coordinate checkPawnTwo = new Coordinate(xcoor+1, ycoor-1);
					if(node.getGame().getBoard().isInbounds(checkPawn) && node.getGame().getBoard().isPositionOccupied(checkPawn))
					{
						if(node.getGame().getBoard().getPieceAtPosition(checkPawn).getType().equals(PieceType.PAWN)
						&& !node.getGame().getBoard().getPieceAtPosition(checkPawn).isEnemyPiece(piece))
						{
							defend += 1.0;
							continue;
						}
					}
					else if (node.getGame().getBoard().isInbounds(checkPawnTwo) && node.getGame().getBoard().isPositionOccupied(checkPawnTwo))
					{
						if(node.getGame().getBoard().getPieceAtPosition(checkPawnTwo).getType().equals(PieceType.PAWN)
						&& !node.getGame().getBoard().getPieceAtPosition(checkPawnTwo).isEnemyPiece(piece))
						{
							defend += 1.0;
							continue;
						}
					}
				}
				else
				{
					Coordinate checkPawn = new Coordinate(xcoor-1, ycoor+1);
					Coordinate checkPawnTwo = new Coordinate(xcoor+1, ycoor+1);
					if(node.getGame().getBoard().isInbounds(checkPawn) && node.getGame().getBoard().isPositionOccupied(checkPawn))
					{
						if(node.getGame().getBoard().getPieceAtPosition(checkPawn).getType().equals(PieceType.PAWN)
						&& !node.getGame().getBoard().getPieceAtPosition(checkPawn).isEnemyPiece(piece))
						{
							defend += 1.0;
							continue;
						}
					}
					else if (node.getGame().getBoard().isInbounds(checkPawnTwo) && node.getGame().getBoard().isPositionOccupied(checkPawnTwo))
					{
						if(node.getGame().getBoard().getPieceAtPosition(checkPawnTwo).getType().equals(PieceType.PAWN)
						&& !node.getGame().getBoard().getPieceAtPosition(checkPawnTwo).isEnemyPiece(piece))
						{
							defend += 1.0;
							continue;
						}
					}
				}
			
			}
			defend = Math.max(0.0, defend);
			return defend;	
		}

		//rook or queen located on 7th row (black) or 2nd row (white) for enemy -- Bad Thing!
		// 2 points for each rook or queen
		public static int seventhRow(DFSTreeNode node)
		{
			int value = 0;
			Set<Piece> queenPieces = node.getGame().getBoard().getPieces(CustomHeuristics.getMinPlayer(node), PieceType.QUEEN);
			Set<Piece> rookPieces = node.getGame().getBoard().getPieces(CustomHeuristics.getMinPlayer(node), PieceType.ROOK);
			for(Piece q: queenPieces)
			{
				if (q.getPlayer().equals(node.getGame().getBlackPlayer()))
				{
					int yCoor = node.getGame().getBoard().getPiecePosition(q).getYPosition();
					if(yCoor == 7)
					{
						value += 2;
					}
				}
				else
				{
					int yCoor = node.getGame().getBoard().getPiecePosition(q).getYPosition();
					if (yCoor == 2)
					{
						value += 2;
					}
				}
			}

			for(Piece r: rookPieces)
			{
				if (r.getPlayer().equals(node.getGame().getBlackPlayer()))
				{
					int yCoor = node.getGame().getBoard().getPiecePosition(r).getYPosition();
					if(yCoor == 7)
					{
						value += 2;
					}
				}
				else
				{
					int yCoor = node.getGame().getBoard().getPiecePosition(r).getYPosition();
					if (yCoor == 2)
					{
						value += 2;
					}
				}
			}
			value = Math.max(value, 0);
			return value;
		}

		//from professor -- more units are better
		public static double getNonlinearPieceCombinationMaxPlayerHeuristicValue(DFSTreeNode node)
		{
		// both bishops are worth more together than a single bishop alone
		// same with knights...we want to encourage keeping pairs of elements
			double multiPieceValueTotal = 0.0;

			double exponent = 1.5; // f(numberOfKnights) = (numberOfKnights)^exponent

		// go over all the piece types that have more than one copy in the game (including pawn promotion)
			for(PieceType pieceType : new PieceType[] {PieceType.BISHOP, PieceType.KNIGHT, PieceType.ROOK, PieceType.QUEEN})
			{
				multiPieceValueTotal += Math.pow(node.getGame().getNumberOfAlivePieces(DefaultHeuristics.getMaxPlayer(node), pieceType), exponent);
			}
			multiPieceValueTotal = Math.max(multiPieceValueTotal, 0.0);
			return multiPieceValueTotal;
		}

		//number of pieces surround enemy king -- Bad Thing!
		public static int getClampedPieceValueTotalSurroundingMaxPlayersKing(DFSTreeNode node)
		{
			// what is the state of the pieces next to the king? add up the values of the neighboring pieces
			// positive value for friendly pieces and negative value for enemy pieces (will clamp at 0)
			int maxPlayerKingSurroundingPiecesValueTotal = 0;

			Piece kingPiece = node.getGame().getBoard().getPieces(CustomHeuristics.getMinPlayer(node), PieceType.KING).iterator().next();
			Coordinate kingPosition = node.getGame().getCurrentPosition(kingPiece);
			for(Direction direction : Direction.values())
			{
				Coordinate neightborPosition = kingPosition.getNeighbor(direction);
				if(node.getGame().getBoard().isInbounds(neightborPosition) && node.getGame().getBoard().isPositionOccupied(neightborPosition))
				{
					Piece piece = node.getGame().getBoard().getPieceAtPosition(neightborPosition);
					int pieceValue = Piece.getPointValue(piece.getType());
					if(piece != null && kingPiece.isEnemyPiece(piece))
					{
						maxPlayerKingSurroundingPiecesValueTotal -= pieceValue;
					} else if(piece != null && !kingPiece.isEnemyPiece(piece))
					{
						maxPlayerKingSurroundingPiecesValueTotal += pieceValue;
					}
				}
			}
			// kingSurroundingPiecesValueTotal cannot be < 0 b/c the utility of losing a game is 0, so all of our utility values should be at least 0
			maxPlayerKingSurroundingPiecesValueTotal = Math.max(maxPlayerKingSurroundingPiecesValueTotal, 0);
			return maxPlayerKingSurroundingPiecesValueTotal;
		}
		

	}

















	public static double getOffensiveMaxPlayerHeuristicValue(DFSTreeNode node)
	{
		// remember the action has already taken affect at this point, so capture moves have already resolved
		// and the targeted piece will not exist inside the game anymore.
		// however this value was recorded in the amount of points that the player has earned in this node
		double damageDealtInThisNode = node.getGame().getBoard().getPointsEarned(CustomHeuristics.getMaxPlayer(node));

		switch(node.getMove().getType())
		{
		case PROMOTEPAWNMOVE:
			PromotePawnMove promoteMove = (PromotePawnMove)node.getMove();
			damageDealtInThisNode += Piece.getPointValue(promoteMove.getPromotedPieceType());
			break;
		default:
			break;
		}
		// offense can typically include the number of pieces that our pieces are currently threatening
		double totalVal = 0;
		totalVal += OffensiveHeuristics.getNumberOfPiecesMaxPlayerIsThreatening(node);
		totalVal += OffensiveHeuristics.getWeightedNumberOfPiecesMaxPlayerIsThreatening(node);
		totalVal += OffensiveHeuristics.howManyBoards(node);
		totalVal += OffensiveHeuristics.howManyPiecesOnImportantCoordinates(node);
		totalVal += OffensiveHeuristics.goodDiagonalPawn(node);
		totalVal -= OffensiveHeuristics.badAdjacentPawn(node);
		totalVal += OffensiveHeuristics.pawnTransform(node);
		totalVal += OffensiveHeuristics.knightAttackOnly(node);
		totalVal += OffensiveHeuristics.emptyRook(node);
		totalVal += OffensiveHeuristics.defendableByPawn(node);
		totalVal += OffensiveHeuristics.seventhRow(node);
		totalVal += OffensiveHeuristics.getNonlinearPieceCombinationMaxPlayerHeuristicValue(node);
		totalVal += OffensiveHeuristics.getClampedPieceValueTotalSurroundingMaxPlayersKing(node);

		return damageDealtInThisNode + totalVal;
	}
















	public static double getDefensiveMaxPlayerHeuristicValue(DFSTreeNode node)
	{
		double totalVal = 0;
		totalVal -= DefensiveHeuristics.getNumberOfPiecesMinPlayerIsThreatening(node);
		totalVal -= DefensiveHeuristics.getWeightedNumberOfPiecesMinPlayerIsThreatening(node);
		totalVal -= DefensiveHeuristics.howManyBoards(node);
		totalVal -= DefensiveHeuristics.howManyPiecesOnImportantCoordinates(node);
		totalVal -= DefensiveHeuristics.goodDiagonalPawn(node);
		totalVal += DefensiveHeuristics.badAdjacentPawn(node);
		totalVal -= DefensiveHeuristics.pawnTransform(node);
		totalVal -= DefensiveHeuristics.knightAttackOnly(node);
		totalVal -= DefensiveHeuristics.emptyRook(node);
		totalVal -= DefensiveHeuristics.defendableByPawn(node);
		totalVal -= DefensiveHeuristics.seventhRow(node);
		totalVal -= DefensiveHeuristics.getNonlinearPieceCombinationMaxPlayerHeuristicValue(node);
		totalVal -= DefensiveHeuristics.getClampedPieceValueTotalSurroundingMaxPlayersKing(node);

		return totalVal;
	}













	public static double getMaxPlayerHeuristicValue(DFSTreeNode node)
	{
		// please replace this!
		// return DefaultHeuristics.getMaxPlayerHeuristicValue(node);
		//return attack(node) + defense(node) + controlMiddle(node) + controllingPieces(node) - threatenedByEnemy(node);
		double heuristicVal = 0.0;
		heuristicVal += CustomHeuristics.getOffensiveMaxPlayerHeuristicValue(node);
		heuristicVal += CustomHeuristics.getDefensiveMaxPlayerHeuristicValue(node);
		
		
		return heuristicVal;
	}

}


	

