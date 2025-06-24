public class Queen extends Piece{
    public Queen(boolean isWhite, int row, int col){
        super(isWhite, row, col);
    }
    public String returnType(){
        return "Queen";
    }
    public Piece clone(){
        Piece p = new Queen(this.getColor(), this.getRow(), this.getCol());
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
        if(!(newCol >= 0 && newCol <= 7 && newRow >= 0 && newRow <= 7)){
            return false;
        }
        if((newCol == startCol || newRow == startRow) && !(newCol == startCol && newRow == startRow)){
            canBeMoved = true;
        }
        if(Math.abs(newCol-startCol) == Math.abs(newRow-startRow) && newRow-startRow != 0){
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
                if(!GameState.notOccupied(board, startRow, i)){
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
        if (newCol != startCol && newRow != startRow){
            int rowStep = newRow > startRow ? 1 : -1;
            int colStep = newCol > startCol ? 1 : -1;
            for(int i=Math.abs(newRow-startRow)-1; i>0; i--){
                if(!GameState.notOccupied(board, startRow+i*rowStep, startCol+i*colStep)){
                    return false;
                }
            }
        }
        if (canBeMoved){
            return true;
        }
        return false;
    }
}
