public class Rook extends Piece {
    public Rook(boolean isWhite, int row, int col){
        super(isWhite, row, col);
    }
    public String returnType(){
        return "Rook";
    }
    public Piece clone(){
        Piece p = new Rook(this.getColor(), this.getRow(), this.getCol());
        if(this.hasMoved()){
            move();
        }
        if(this.isPassantable()){
            passantToggle();
        }
        return p;
        
    }
    public boolean isValidMove(Piece[][] board, int newRow, int newCol){
        boolean canBeMoved = false;
        if(!(newCol >= 0 && newCol <= 7 && newRow >= 0 && newRow <= 7)){
            return false;
        }
        if(((newCol == startCol || newRow == startRow) && !(newCol == startCol && newRow == startRow))){
            canBeMoved = true;
        }
        if(!GameState.notOccupied(board, newRow, newCol)){
            if(board[newRow][newCol].getColor() == this.getColor()){
                return false;
            }
        }
        if(newRow==startRow){
            int step = newCol > startCol ? 1 : -1;
            for(int i=startCol+step; i != newCol; i+=step){
                if(!GameState.notOccupied(board, startRow,i)){
                    return false;
                }

            }
        }
        if(newCol==startCol){
            int step = newRow > startRow ? 1 : -1;
            for(int i=startRow+step; i!=newRow; i+=step){
                if(!GameState.notOccupied(board, i, startCol)){
                    return false;
                }

            }
        }
        if(canBeMoved){
            return true;
        }
        return false;
    }
}
