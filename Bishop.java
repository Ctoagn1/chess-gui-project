public class Bishop extends Piece{
    public Bishop(boolean isWhite, int row, int col){
        super(isWhite, row, col);
    }
    public String returnType(){
        return "Bishop";
    }
    public Piece clone(){
        Piece p = new Bishop(this.getColor(), this.getRow(), this.getCol());
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
        if(Math.abs(newCol-startCol) == Math.abs(newRow-startRow) && newRow-startRow != 0){
            canBeMoved = true;
            if(!GameState.notOccupied(board, newRow, newCol)){
                if(board[newRow][newCol].getColor() == this.getColor()){
                    return false;
                }
            }
        }
        int rowStep = newRow > startRow ? 1 : -1;
        int colStep = newCol > startCol ? 1 : -1;
        for(int i=Math.abs(newRow-startRow)-1; i>0; i--){
            if(!GameState.notOccupied(board, startRow+i*rowStep, startCol+i*colStep)){
                return false;
            }
        }
        if(inBounds && canBeMoved){
            return true; 
        }
        return false;
    }
}
