public class Pawn extends Piece {
    public Pawn(boolean isWhite, int row, int col){
        super(isWhite, row, col);
    }
    public String returnType(){
        return "Pawn";
    }
    public Piece clone(){
        Piece p = new Pawn(this.getColor(), this.getRow(), this.getCol());
        if(this.hasMoved()){
            p.move();
        }
        if(this.isPassantable()){
            p.passantToggle();
        }
        return p;
        
    }
    public boolean isValidMove(Piece[][] board,int newRow, int newCol){
        boolean sameCol= false;
        boolean canBeMoved = false;
        boolean inBounds = false;
        boolean capture = false;
        if(this.getColor() && Math.abs(newCol-startCol) == 1 && newRow-startRow==1 && !GameState.notOccupied(board, startRow, newCol)){
            if(board[startRow][newCol].isPassantable()){
                return true;
            }
        }
        if(!this.getColor() && Math.abs(newCol-startCol) == 1 && newRow-startRow ==-1 && !GameState.notOccupied(board, startRow, newCol)){
            if(board[startRow][newCol].isPassantable()){
                return true;
            }
        }
        if(newCol == startCol){
            sameCol = true;
        }
        
        if(this.getColor()){
            if((newRow == startRow + 1 && GameState.notOccupied(board, newRow, newCol))  || (newRow == startRow + 2 && hasMoved() == false && GameState.notOccupied(board, newRow, newCol) && GameState.notOccupied(board, newRow-1, newCol))){
            canBeMoved = true;
            }
            if(newRow == startRow+1 && (newCol == startCol+1 || newCol == startCol-1) && !GameState.notOccupied(board, newRow, newCol)){
                if(board[newRow][newCol].getColor() != this.getColor()){
                    capture = true;
                }
            }
        }

        if(!this.getColor()){
             if((newRow == startRow - 1 && GameState.notOccupied(board, newRow, newCol))  || (newRow == startRow - 2 && hasMoved() == false && GameState.notOccupied(board, newRow, newCol) && GameState.notOccupied(board, newRow+1, newCol))){
            canBeMoved = true;
            }
            if(newRow == startRow-1 && (newCol == startCol+1 || newCol == startCol-1) && !GameState.notOccupied(board, newRow, newCol)){
                if(board[newRow][newCol].getColor() != this.getColor()){
                    capture = true;
                }
            }
        }

        if(newCol >= 0 && newCol <= 7 && newRow >= 0 && newRow <= 7){
            inBounds = true;
        }
        if((inBounds && sameCol && canBeMoved) || (capture && inBounds)){
            return true;
        }
        return false;
    }

}
