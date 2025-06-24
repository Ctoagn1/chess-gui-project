public class Piece {
    private boolean isWhite;
    private boolean moved = false;
    private boolean passantAble = false;
    int startRow = -1;
    int startCol = -1;
    public Piece(boolean isWhite, int row, int col){
        this.isWhite = isWhite;
        startRow = row;
        startCol = col;
    }  
    public boolean getColor(){
        return isWhite;
    }
    public boolean isValidMove(Piece[][] board,int newRow, int newCol){
        return true;
    }
    public String returnType(){
        return "Piece";
    }
    public boolean hasMoved(){
        return moved;
    }
    public void move(){
        moved = true;
    }
    public int getRow(){
        return startRow;
    }
    public int getCol(){
        return startCol;
    }
    public void setPos(int row, int col){
        startRow = row;
        startCol = col;
    }
    public boolean isPassantable(){
        return passantAble;
    }
    public void passantToggle(){
        passantAble = !passantAble;
    }
    public Piece clone(){
        Piece p = new Piece(this.isWhite, this.getRow(), this.getCol());
        p.moved = this.moved;
        p.passantAble = this.passantAble;
        return p;
    }
}
