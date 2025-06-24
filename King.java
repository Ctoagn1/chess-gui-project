public class King extends Piece {
    public King(boolean isWhite, int row, int col){
        super(isWhite, row, col);
    }
    public String returnType(){
        return "King";
    }
    public Piece clone(){
        Piece p = new King(this.getColor(), this.getRow(), this.getCol());
        if(this.hasMoved()){
            move();
        }
        if(this.isPassantable()){
            passantToggle();
        }
        return p;
        
    }

    public boolean isValidMove(Piece[][] board,int newRow, int newCol){
        boolean canBeMoved = false;
        boolean inBounds = false;
        if(!GameState.notOccupied(board, newRow, newCol)){
            if(board[newRow][newCol].getColor() == this.getColor()){
                return false;
            }
        }
        if(!this.hasMoved() && startRow-newRow == 0){
            if(startCol-newCol ==-2 && !GameState.attacked(board, !getColor(), startRow, startCol) && !GameState.notOccupied(board, startRow, 7) && board[startRow][7].returnType().equals("Rook") && !board[startRow][7].hasMoved()){
                if(!GameState.attacked(board, !getColor(), startRow, 5) && !GameState.attacked(board, !getColor(), startRow, 6)&& !GameState.attacked(board, !getColor(), startRow, 4)){
                    if(GameState.notOccupied(board, startRow, 5) && GameState.notOccupied(board, startRow, 6)&& GameState.notOccupied(board, startRow, 4)){
                        return true;
                    }
                }
            }
            if(startCol-newCol ==2 && !GameState.notOccupied(board, startRow, 0) && board[startRow][0].returnType().equals("Rook") && !board[startRow][0].hasMoved()){
                if(!GameState.attacked(board, !getColor(), startRow, 2) && !GameState.attacked(board, !getColor(), startRow, 1)){
                    if(GameState.notOccupied(board, startRow, 2) && GameState.notOccupied(board, startRow, 1)){
                        return true;
                    }
                }
            }
        }
        
        if(newCol >= 0 && newCol <= 7 && newRow >= 0 && newRow <= 7){
            inBounds = true;
        }
        if(Math.abs(startCol-newCol) < 2 && Math.abs(startRow-newRow) < 2 && !(startCol == newCol && startRow == newRow)){
            if(GameState.attacked(board, !getColor(), newRow, newCol)){
                return false;
            }
            canBeMoved = true;
        }
        if(inBounds && canBeMoved){
            return true;
        }
        return false;
    }
}
