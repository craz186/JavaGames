package com.gibbons.chessmate.controller;
import java.util.*;
/*	
* AI class reponds to users moves and moves each AI piece according to the
* decision of the minimax search.  
*
*
*/
class AI
{
    Random r = new Random();
    Piece user;
    Tile tempUser;
    Piece currentPiece;
    Tile oldUser;
    int depth;
    LinkedList<Piece> aiPieces; 

    AI()
    {
        this.aiPieces= new LinkedList<>();
        this.depth=4;//change later
    }

    //general getters and setters.

    boolean isEmpty()
    {
        return aiPieces.isEmpty();
    }
    void removePiece(Tile t)
    {
        aiPieces.remove(t);
    }
    void addPiece(Piece piece)//adds ai piece to the AI mum
    {
            aiPieces.add(piece);
    }
    void addUser(Piece user)
    {
        this.user=user;
    }
    void updateUser(Piece user)
    {
        oldUser= new Tile(this.user);
        this.user=user;
        updateGoal(user);
        tempUser= new Tile(this.user);
    }
    void removeUser()
    {
        this.user= null;
    }
    Piece getUser()
    {
        return this.user;
    }
    //update the goal for each ai
    void updateGoal(Tile user)
    {
        for(Piece p:aiPieces)
        {
            p.setGoal(user);
        }
    }
    //general A* finds the shortest path to the goal of whichever piece
    //is sent to it.
    public LinkedList<Tile> getPath(Tile[][] tiles, Piece piece)
    {
        currentPiece= piece;
        Tile tempGoal=piece.getGoal();
        LinkedList<Tile> closedset = new LinkedList<>();    // The set of nodes already evaluated.
        LinkedList<Tile> openset = new LinkedList<>();	// The set of tentative nodes to be evaluated, initially containing the start node
        HashMap<Tile,Tile> came_from = new HashMap<>();
        piece.setG(0);   // Cost from start along best known path.
        // Estimated total cost from start to goal through y.
        piece.setF(heuristic_cost_estimate(piece, tempGoal));
        openset.add(piece);
        while(!openset.isEmpty())
        {
            Tile current = getLowestF(openset);//lowest f_score 

            LinkedList<Tile> neighbours;
            if(current.equals(tempGoal))
            {
                return reconstruct_path(came_from,current);
            }
            openset.remove(current);
            closedset.add(current);
            neighbours= getNeighbours(current,tiles);
            //
            for(Tile neighbour:neighbours)
            {
                if(closedset.contains(neighbour))
                        continue;
                int tentative_g_score = current.getG() + dist_between(current,neighbour,tempGoal,tiles);

                if(!openset.contains(neighbour)||tentative_g_score < neighbour.getG())
                { //next goal in the path
                        came_from.put(neighbour,current);
                        neighbour.setG(tentative_g_score);
                        neighbour.setF(neighbour.getG() + heuristic_cost_estimate(neighbour, tempGoal));
                        if(!openset.contains(neighbour))
                        {
                                openset.add(neighbour);
                        }
                }
            }
        }

        return null;
    }
    //direction from last tile to current tile
    int getDirection(Tile lastCurrent, Tile current)
    {
        int direction= lastCurrent.checkOrth(current);
        if(direction==0)
            return lastCurrent.checkDiag(current);
        else 
            return direction;
    }
    //cost from one to tile to it's neighbour
    int dist_between(Tile current, Tile neighbour, Tile goal, Tile[][] tiles)
    {
        int penalty= 15;
        //penalise a change in direction
        //if we are on the same x or y we don't want to penalise the path
        if((currentPiece.getName().equals("Rook")||currentPiece.getName().equals("Queen"))
                &&current.checkRoute(goal,getDirection(current,goal),tiles)!=null)
        {
            penalty= 5;
        }
        if((currentPiece.getName().equals("Bishop")||currentPiece.getName().equals("Queen"))
                &&current.checkRouteDiag(goal,getDirection(current,goal),tiles)!=null)
        {
            penalty= 5;
        }
        return penalty;
    }
    //the cost from the current tile to the goal
    int heuristic_cost_estimate(Tile start,Tile goal)
    {
        return((Math.abs(start.getX() - goal.getX())+ Math.abs(start.getY() - goal.getY()))*10);
    }
	
    //gets the neighbours for the current piece
    LinkedList<Tile> getNeighbours(Tile current, Tile[][] tiles)
    {

        LinkedList<Tile> neighbours;
        Tile previous = new Tile(currentPiece);
        tiles =currentPiece.changePos(current,tiles);
        neighbours= currentPiece.getMoves(tiles,1);
        currentPiece.changePos(previous,tiles);
        return neighbours;
    }
    //gets the smallest F score for the A* search
    Tile getLowestF(LinkedList<Tile> list)
    {
        Tile min= null;
        for(Tile t:list)
        {
            if(min==null)
            {
                min=t;
            }
            else if(t.getF()<min.getF())
            {
                min=t;
            }
        }
        return min;
    }
    //used to find the actual shortest path by backtracking
    LinkedList<Tile> reconstruct_path(HashMap<Tile,Tile> came_from,Tile current)
    {
        LinkedList<Tile> total_path = new LinkedList<>();
        total_path.add(current);
        while(came_from.get(current)!=null)
        {
                current= came_from.get(current);
                total_path.add(current);
        }
        Collections.reverse(total_path);
        return total_path;
    }
    //moves the ai
    Tile[][] decision(Tile[][] tiles)
    {
        //each piece make a decision
        for(Piece p:aiPieces)
        {
            Tile check=null;
            String name= p.getName();
            //can I directly take a piece?
            if(!name.equals("Bishop"))
                check = p.checkRoute(user,p.checkOrth(user),tiles);
            if(check==null||check.equals(p)&&(name.equals("Bishop")||name.equals("Queen")))
                check = p.checkRouteDiag(user,p.checkDiag(user),tiles);
            if(check!=null&&check.equals(user))//take the user
            {
                tiles= p.move(user,tiles,this);
            }
            else
            {
                Node tempMin= minimax(depth,user,p,tiles);
                //my current position is best?
                if(tempMin.getTile()==null||tempMin.getTile().equals(p)) {
                    continue;
                }
                //move the ai to the decision
                tiles= (p.move(tempMin.getTile(),tiles,this));
                p.setMoved();
            }
            if(user==null)
                break;
        }
        //this is incase a piece thinks it is protected but isn't because 
        //another piece has not moved yet
        for(Piece p:aiPieces)
            p.setMoved();
        return tiles;
    }
        
    //has a piece reached it's goal?
    boolean gameOver(Piece piece)
    {
        return(user.equals(user.getGoal())||piece.equals(piece.getGoal()));
    }
    //checks if the given piece can be taken by any of the ai pieces
    boolean isProtected(Piece piece, Tile[][] tiles)
    {	
        for(Piece p:aiPieces)
        {
                String name = p.getName();
                if(p.equals(piece))
                        continue;
                if(p.getMoved()&&!(name.equals("Bishop"))
                        &&p.checkRoute(piece,getDirection(p,piece),tiles).equals(piece))
                {
                        return true;
                }
                else if(p.getMoved()&&name.equals("Queen")
                        ||p.checkRouteDiag(piece,getDirection(p,piece),tiles).equals(piece))
                {
                        return true;
                }
        }
        return false;
    }
    Node minimax(int depth,Piece user,Piece piece, Tile[][] tiles)
    {
        //start
        return(min(depth, user, piece,tiles));
    }
    //maximises the AIs move 
    Node max(int depth, Piece user, Piece piece, Tile[][] tiles)
    {
        //are we done searching?
        if(depth <= 0||gameOver(piece))
        {
            return new Node(evaluation(user,piece,tiles));
        }
	LinkedList<Tile> moves = user.getMoves(tiles,1);
        Node best = new Node((int)(Double.NEGATIVE_INFINITY));
        //search through each move for best given the ais response
	for(Tile t:moves) {
            Tile temp = new Tile(user);
            tiles = user.changePos(t, tiles);
            Node val = min(depth - 1, user, piece, tiles);
            val.addTile(t);
            tiles = user.changePos(temp, tiles);
            if(val.getValue()>best.getValue())
                best=val;
        }
	return best;
    }
    //minimises the users move
    Node min(int depth, Piece user, Piece piece, Tile[][] tiles)
    {
        //are we done searching?
        if(depth <= 0||gameOver(piece))
        {
            return new Node(evaluation(user,piece,tiles));
        }
        LinkedList<Tile> moves = piece.getMoves(tiles,1);
        Node best = new Node((int)(Double.POSITIVE_INFINITY));
        //search through each move for best given the users response
        for(Tile t:moves)
        {
            Tile temp= new Tile(piece);
            tiles= piece.changePos(t,tiles);
            Node val = max(depth - 1, user, piece,tiles);
            val.addTile(t);
            tiles= piece.changePos(temp,tiles);
            if(val.getValue()<best.getValue())
                best=val;

        }
        return best;
    }
    //Evaluation function for the minimax search.
    int evaluation(Piece user, Piece piece,Tile[][] tiles)
    {
        int score=0;
        //am I protected?
        if(isProtected(piece, tiles))
            score-=10;
        //can the user take me?
        if(isProtected(user,tiles))
            score+=100;
        //did I reach my goal?
        if(piece.equals(piece.getGoal()))
            score=Integer.MIN_VALUE;
        //did the user reach his/her goal?
        if(user.equals(user.getGoal()))
            score=Integer.MAX_VALUE;

        //checks if piece is blocking users path
        LinkedList<Tile> path =getPath(tiles,user);
        if(path!=null) {
            for (Tile t : path) {
                for (Piece p : aiPieces) {
                    if (p.equals(t)) {
                        if (isProtected(p, tiles))
                            score -= 100;
                        score -= 5;
                    }
                }
            }
        }
        return score;
    }
}
