
public class ChessPlayer {
      static boolean click = false;
      static String move;
      public static void main(String[]args){
         GameState.setUpBoard();
         ChessFrame game = new ChessFrame();
         game.updateBoardState();
         boolean validAnswer = false;
         while(true){
            while(!click){
               try{
                  Thread.sleep(10);
               }
               catch(InterruptedException e){
                  e.printStackTrace();
               }
            }
            click = false;
            game.updateBoardState();
            validAnswer = false;
            if(GameState.whiteTurn()){
               game.setText("White to Move:"); 
            }
            else{
               game.setText("Black to Move:");
            }
            if((GameState.whiteThreatened(GameState.getBoard()))){
               game.setText("Check! White to move.");
            }
            if((GameState.blackThreatened(GameState.getBoard()))){
               game.setText("Check! Black to move.");
            }
            while(!validAnswer){
               String input = move;
                  if(input.toLowerCase().equals("quit")){
                     System.exit(0);
                  }
            
                  if(input.toLowerCase().equals("reset")){
                     GameState.resetHistory();
                     GameState.setUpBoard();
                     game.updateBoardState();
                     if(!GameState.whiteTurn()){
                        GameState.moveFlipper();
                     }
                     System.out.println("White to Move:"); 
                     continue;
                  }
                  if(input.toLowerCase().equals("back")){
                     GameState.goBack();
                     continue;
                  }
                  if(input.toLowerCase().equals("help")){
                     System.out.println("Enter coordinates with a space between them to begin. The first set has the piece you want to move- the second, where you want to move it to. \nExample: E2 E4 \nMove your king two spaces to castle, if possible. Type \"show\" to display board again. \nType \"back\" to go back a turn. \nType \"quit\" to stop.\nType \"help\" to bring up these instructions again.\nType \"reset\" to reset board." );
                     continue;
                  }
                  int a = input.charAt(0);
                  int b = input.charAt(1);
                  int c = input.charAt(3);
                  int d = input.charAt(4);
                  if(GameState.movePiece(a-'0', b-'0', c-'0', d-'0')){
                     game.setWarning("");
                     game.updateBoardState();
                     validAnswer = true;
                     for(int i=0; i<8; i++){ //promotion checker
                        for(int j=0; j<8; j+=7){
                           if(!GameState.notOccupied(j, i) && GameState.getBoard()[j][i].returnType() == "Pawn"){
                              game.setPromo(j, i);
                              game.setWarning("Select the piece you want to promote to.");
                              game.promotion();

                              /*do{
                                 game.setText("Select the piece you want to promote to.");
                                 String pieceType = scanner.nextLine();
                                 if(pieceType.toLowerCase().equals("queen")){
                                    GameState.change(j!=0,"Q", j, i);
                                    validSelect=true;
                                 }
                                 else if(pieceType.toLowerCase().equals("rook")){
                                    GameState.change(j!=0,"R", j, i);
                                    validSelect=true;
                                 
                                 }
                                 else if(pieceType.toLowerCase().equals("knight")){
                                    GameState.change(j!=0,"N", j, i);
                                    validSelect=true;
                                 }
                                 else if(pieceType.toLowerCase().equals("bishop")){
                                    GameState.change(j!=0,"B", j, i);
                                    validSelect = true;
                                 }
                                 else{
                                    validSelect = false;
                                    System.out.println("Not a valid piece type.");
                                 }
                              }while(validSelect == false);*/
                           }
                        }
                     }
                     if(!GameState.anyLegalMoves(GameState.whiteTurn())){
                        if((GameState.whiteTurn() && GameState.whiteThreatened(GameState.getBoard())) || (!GameState.whiteTurn() && GameState.blackThreatened(GameState.getBoard()))){
                           String winner;
                           if(GameState.whiteTurn()){
                              winner = "Black";
                           }
                           else{
                              winner = "White";
                           }
                           game.setText("Checkmate! " + winner + " wins!");
                           game.updateBoardState();
                           try{
                              Thread.sleep(2000);
                           }catch(InterruptedException e){
                              e.printStackTrace();
                           }
                           System.exit(0);
                        }
                        else{
                           game.setText("Stalemate! Too bad. :(");
                           game.updateBoardState();
                           try{
                              Thread.sleep(2000);
                           }catch(InterruptedException e){
                              e.printStackTrace();
                           }
                           System.exit(0);
                        }
                     }
                     if(GameState.counterCheck()){
                        game.setText("Draw by 50 move rule!");
                        game.updateBoardState();
                        try{
                              Thread.sleep(2000);
                           }catch(InterruptedException e){
                              e.printStackTrace();
                           }
                        System.exit(0);
                     }
                  }
                  else{
                     game.setWarning("Invalid move. Try again.");
                     validAnswer=false;
                  }
            }
        }
     }

}

