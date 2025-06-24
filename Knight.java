public class Knight extends Piece {
    public Knight(boolean isWhite, int row, int col){
        super(isWhite, row, col);
    }
    public String returnType(){
        return "Knight";
    }
    public Piece clone(){
        Piece p = new Knight(this.getColor(), this.getRow(), this.getCol());
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
        if(newCol >= 0 && newCol <= 7 && newRow >= 0 && newRow <= 7){
            inBounds = true;
        }
        if((Math.abs(startRow-newRow) == 1 && Math.abs(startCol-newCol) == 2) || (Math.abs(startRow-newRow) == 2 && Math.abs(startCol-newCol) == 1)){
            canBeMoved = true;
            if(!GameState.notOccupied(board, newRow, newCol)){
                if(board[newRow][newCol].getColor() == this.getColor()){
                    return false;
                }
            }
        }  
        if(canBeMoved && inBounds){
            return true;
        }
        return false;
    }
}
