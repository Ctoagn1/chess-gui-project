import java.util.ArrayList;

public class GameState {
    private static Piece[][] board = new Piece[8][8];
    private static boolean whiteToMove = true;
    private static int counter = 0;
    private static ArrayList<Piece[][]> MoveHistory = new ArrayList<Piece[][]>();
    public static void setUpBoard(){
        for(int i=0; i<8; i++){
            board[1][i] = new Pawn(true, 1, i);
            board[6][i] = new Pawn(false, 6, i);
        }
        whiteToMove = true;
        board[0][0] = new Rook(true, 0, 0);
        board[0][1] = new Knight(true, 0, 1);
        board[0][2] = new Bishop(true, 0, 2);
        board[0][4] = new Queen(true, 0, 4);
        board[0][3] = new King(true, 0, 3);
        board[0][5] = new Bishop(true, 0, 5);
        board[0][6] = new Knight(true, 0, 6);
        board[0][7] = new Rook(true, 0 ,7);
        board[7][0] = new Rook(false, 7, 0);
        board[7][1] = new Knight(false, 7, 1);
        board[7][2] = new Bishop(false, 7, 2);
        board[7][4] = new Queen(false, 7, 4);
        board[7][3] = new King(false, 7, 3);
        board[7][5] = new Bishop(false, 7, 5);
        board[7][6] = new Knight(false, 7, 6);
        board[7][7] = new Rook(false, 7, 7);
        for(int i=2; i<6; i++){
            for(int j=0; j<8; j++){
                board[i][j] = null;
            }
        }
    }
    public static void addHistory(){
        MoveHistory.add(new Piece[board.length][board[0].length]);
        for(int i=0; i<board.length; i++){
            for(int j=0; j<board[i].length; j++){
                if(!notOccupied(i, j)){
                    MoveHistory.get(MoveHistory.size()-1)[i][j] = board[i][j].clone();
                }
            }
        }
    }
    public static void resetHistory(){
        while(!MoveHistory.isEmpty()){
            MoveHistory.remove(0);
        }
    }
    public static void goBack(){
        if(MoveHistory.isEmpty()){
            System.out.println("First move- can't go back any further!");
            return;
        }
        for(int i=0; i<board.length; i++){
            for(int j=0; j<board[i].length; j++){
                board[i][j] = MoveHistory.get(MoveHistory.size()-1)[i][j];
            }
        }
        MoveHistory.remove(MoveHistory.size()-1);
        whiteToMove=!whiteToMove;
        System.out.println(whiteToMove ? "White" : "Black" + " to Move:");
    }
    public static Piece[][] getBoard(){
        return board;
    }
    public static void moveFlipper(){
        whiteToMove=!whiteToMove;
    }
    public static void change(boolean color, String piece, int row, int col){
        if(piece.equals("Q")){
            board[row][col] = new Queen(color, board[row][col].getRow(), board[row][col].getCol());
        }
        if(piece.equals("R")){
            board[row][col] = new Rook(color, board[row][col].getRow(), board[row][col].getCol());
        }
        if(piece.equals("N")){
            board[row][col] = new Knight(color, board[row][col].getRow(), board[row][col].getCol());
        }
        if(piece.equals("B")){
            board[row][col] = new Bishop(color, board[row][col].getRow(), board[row][col].getCol());
        }
    }

    public static boolean notOccupied(int row, int col){
        if(row>7 || row<0 || col>7 || col<0){
            return true;
        }
        return(board[row][col] == null);
    }
    public static boolean notOccupied(Piece[][] board, int row, int col){
        if(row>7 || row<0 || col>7 || col<0){
            return true;
        }
        return(board[row][col] == null);
    }
    public static boolean whiteTurn(){
        return whiteToMove;
    } 
    public static boolean whiteThreatened(Piece[][] board){
        for(int i=0; i<board.length; i++){
            for(int j=0; j<board[i].length; j++){
                if(!notOccupied(board, i, j)){
                    if(board[i][j].getColor() == true && board[i][j].returnType().equals("King")){
                        return attacked(board, false, i, j);
                    }
                }
            }
        }
        return false;
    }
    public static boolean blackThreatened(Piece[][] board){
        for(int i=0; i<board.length; i++){
            for(int j=0; j<board[i].length; j++){
                if(!notOccupied(board, i, j)){
                    if(board[i][j].getColor() == false && board[i][j].returnType().equals("King")){
                        return attacked(board,true, i, j);
                    }
                }
            }
        }
        return false;
    }
    public static boolean movePiece(int startRow, int startCol, int endRow, int endCol){
        if(board[startRow][startCol] == null){
            return false;
        }
        if(board[startRow][startCol].isValidMove(board, endRow, endCol) && board[startRow][startCol].getColor() == whiteToMove && !unsafeMove(whiteToMove, startRow, startCol, endRow, endCol)){
            addHistory();
            boolean anythingHere = !notOccupied(endRow, endCol);
            board[endRow][endCol] = board[startRow][startCol];
            board[startRow][startCol] = null;
            board[endRow][endCol].setPos(endRow, endCol);
            board[endRow][endCol].move();
            if(board[endRow][endCol].returnType().equals("Pawn") && !notOccupied(startRow, endCol) && board[startRow][endCol].isPassantable()){
                board[startRow][endCol] = null;
            }
            if(board[endRow][endCol].returnType().equals("King") && startCol-endCol == -2){
                board[endRow][4] = board[endRow][7];
                board[endRow][7] = null;
                board[endRow][4].setPos(endRow, 4);
            }
            if(board[endRow][endCol].returnType().equals("King") && startCol-endCol == 2){
                board[endRow][2] = board[endRow][0];
                board[endRow][0] = null;
                board[endRow][2].setPos(endRow, 2);
            }
            for(int i=3; i<5; i++){
                for(int j=0; j<8; j++){
                    if(!notOccupied(i, j)&& board[i][j].isPassantable()){
                        board[i][j].passantToggle();
                    }
                }
            }
            if(board[endRow][endCol].returnType().equals("Pawn") && Math.abs(endRow-startRow) == 2){
                board[endRow][endCol].passantToggle();
            }
            whiteToMove = !whiteToMove;
            if(board[endRow][endCol].returnType().equals("Pawn") || anythingHere) {
                counter = 0;
            }
            counter++;
            return true;
        }
        return false;
    }
    public static boolean movePieceTest(boolean white, Piece[][] board, int startRow, int startCol, int endRow, int endCol){
        if(board[startRow][startCol] == null){
            return false;
        }
        if(board[startRow][startCol].isValidMove(board, endRow, endCol) && board[startRow][startCol].getColor() == white){
            board[endRow][endCol] = board[startRow][startCol];
            board[startRow][startCol] = null;
            board[endRow][endCol].setPos(endRow, endCol);
            if(board[endRow][endCol].returnType().equals("Pawn") && !notOccupied(board, startRow, endCol) && board[startRow][endCol].isPassantable()){
                board[startRow][endCol] = null;
            }
            if(board[endRow][endCol].returnType().equals("King") && startCol-endCol == -2){
                board[endRow][4] = board[endRow][7];
                board[endRow][7] = null;
                board[endRow][4].setPos(endRow, 4);
            }
            if(board[endRow][endCol].returnType().equals("King") && startCol-endCol == 2){
                board[endRow][2] = board[endRow][0];
                board[endRow][0] = null;
                board[endRow][2].setPos(endRow, 2);
            }
            for(int i=3; i<5; i++){
                for(int j=0; j<8; j++){
                    if(!notOccupied(board, i, j)&& board[i][j].isPassantable()){
                        board[i][j].passantToggle();
                    }
                }
            }
            if(board[endRow][endCol].returnType().equals("Pawn") && Math.abs(endRow-startRow) == 2){
                board[endRow][endCol].passantToggle();
            }
            return true;
        }
        return false;
    }
    
    public static boolean attacked(Piece[][] board, boolean white, int row, int col){
        for(int i=0; i<board.length; i++){
            for(int j=0; j<board[i].length; j++){
                if(!notOccupied(board, i, j)){
                    if(board[i][j].getColor() == white && !(board[i][j].returnType().equals("Pawn"))){
                        if(!board[i][j].returnType().equals("King") && board[i][j].isValidMove(board, row, col)){
                            return true;
                        }
                        if(board[i][j].returnType().equals("King") && Math.abs(j-col) < 2 && Math.abs(i-row) < 2 && !(col == j && row == i)){
                            return true;
                        }
                    }
                    if(board[i][j].getColor() == white && board[i][j].returnType().equals("Pawn")){
                        if(board[i][j].getColor() && (col == j+1 || col == j-1) && row == i+1){
                            return true;
                        }
                        if(!board[i][j].getColor() && (col == j+1 || col == j-1) && row == i-1){
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }  

    public static boolean counterCheck(){
        if( counter >= 50){
            return true;
        }
        return false;
    }
    public static boolean anyLegalMoves(boolean white){
        for(int i=0; i<board.length; i++){
            for(int j=0; j<board[i].length; j++){
                if(!notOccupied(i, j)){
                    if(board[i][j].getColor() == white){
                        for(int k=0; k<board.length; k++){
                            for(int l=0; l<board[k].length; l++){
                                if(board[i][j].isValidMove(board, k, l) && !unsafeMove(whiteToMove, i, j, k, l)){
                                    return true;
                                }
                            }
                        }   
                    }
                }
            }
        }   
        return false;
    }

    public static boolean unsafeMove(boolean white, int startRow, int startCol, int endRow, int endCol){
        boolean value = false;
        Piece[][] backupBoard = new Piece[8][8];
        for(int i=0; i<board.length; i++){
            for(int j=0; j<board[i].length; j++){
                if(board[i][j] != null){
                    backupBoard[i][j] = board[i][j].clone();
                }
                else{
                    backupBoard[i][j] = null;
                }
            }
        }
        movePieceTest(white, backupBoard, startRow, startCol, endRow, endCol);
        if(white){
            value = whiteThreatened(backupBoard);
        }
        if(!white){
            value = blackThreatened(backupBoard);
        }
        return value;
    }
}

