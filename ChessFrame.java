import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
public class ChessFrame extends JFrame implements MouseListener,ActionListener, MouseMotionListener{
    Color red = new Color(90, 0, 0);
    Color black = new Color(20, 20, 20);
    boolean flipped = false; //flips the board for black
    Color brown = new Color(153, 102, 51);
    int selectCol = -1;
    int selectRow = -1;
    int promoRow = -1;
    int promoCol = -1;
    boolean mouseDisabled = false;
    JButton util1 = new JButton("New Game");
    
    JButton util2 = new JButton("Undo Move");
    JButton util3 = new JButton("Flip Board");
    JButton promo1 = new JButton("Queen");
    JButton promo2 = new JButton("Knight");
    JButton promo3 = new JButton("Bishop");
    JButton promo4 = new JButton("Rook");
    JLabel[][] board = new JLabel[8][8];
    Point selected;
    Piece[][] pieceList = GameState.getBoard();
    JPanel panel = new JPanel();
    JPanel textBox = new JPanel();
    JLabel text = new JLabel("White to Move:");
    JLabel warning = new JLabel("");
    ImageIcon wPawn = new ImageIcon("pieces/Chess_plt45.svg.png");
    ImageIcon wKnight = new ImageIcon("pieces/Chess_nlt45.svg.png");
    ImageIcon wKing = new ImageIcon("pieces/Chess_klt45.svg.png");
    ImageIcon wBishop = new ImageIcon("pieces/Chess_blt45.svg.png");
    ImageIcon wRook = new ImageIcon("pieces/Chess_rlt45.svg.png");
    ImageIcon wQueen = new ImageIcon("pieces/Chess_qlt45.svg.png");
    ImageIcon bPawn = new ImageIcon("pieces/Chess_pdt45.svg.png");
    ImageIcon bKnight = new ImageIcon("pieces/Chess_ndt45.svg.png");
    ImageIcon bKing = new ImageIcon("pieces/Chess_kdt45.svg.png");
    ImageIcon bBishop = new ImageIcon("pieces/Chess_bdt45.svg.png");
    ImageIcon bRook = new ImageIcon("pieces/Chess_rdt45.svg.png");
    ImageIcon bQueen = new ImageIcon("pieces/Chess_qdt45.svg.png");
        
    public ChessFrame(){
        this.setLayout(new BorderLayout());
        this.setBackground(brown);
        panel.setPreferredSize(new java.awt.Dimension(640, 640));
        textBox.setPreferredSize(new java.awt.Dimension(320, 640));
        textBox.add(text);
        textBox.add(util1);
        textBox.add(util2);
        textBox.add(util3);
        textBox.add(promo1);
        textBox.add(promo2);
        textBox.add(promo3);
        textBox.add(promo4);
        util1.addActionListener(this);
        util2.addActionListener(this);  
        util3.addActionListener(this);
        promo1.addActionListener(this);
        promo2.addActionListener(this);
        promo3.addActionListener(this);
        promo4.addActionListener(this);
        promo1.setVisible(false); 
        promo2.setVisible(false);
        promo3.setVisible(false);
        promo4.setVisible(false);
        textBox.setBackground(brown);
        textBox.add(warning);
        textBox.setLayout(new BoxLayout(textBox, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createBevelBorder(1));
        this.setTitle("Chess");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(960, 640);
        panel.setLayout(new GridLayout(8,8));
        for(int i=0; i<board.length; i++){
            for(int j=0; j<board[0].length; j++){
                board[i][j] = new JLabel();
                board[i][j].setOpaque(true);
                if((i+j)%2==0){
                    board[i][j].setBackground(red);
                }
                else{
                    board[i][j].setBackground(black);
                }
                board[i][j].addMouseListener(this);

                panel.add(board[i][j]);
            }
        }
        this.add(panel, BorderLayout.CENTER);
        this.add(textBox, BorderLayout.EAST);
        
        this.setVisible(true);
    }
    public void setText(String s){
        text.setText(s);
    }
    public void setWarning(String s){
        warning.setText(s);
    }
    public void movableSquares(int row, int col){
        if (row != -1 && col != -1) {
            for(int i=0; i<board.length; i++){
                for(int j=0; j<board[0].length; j++){
                    int displayRow = flipped ? i : 7-i; //flips the board for black
                    int displayCol = flipped ? j : 7-j; //flips the board for black
                    if(pieceList[row][col].isValidMove(pieceList, i, j) && pieceList[row][col].getColor() == GameState.whiteTurn() && !GameState.unsafeMove(GameState.whiteTurn(), row, col, i, j)){
                        board[displayRow][displayCol].setBackground(board[displayRow][displayCol].getBackground().brighter().brighter());
                    }
                    else{
                        if((i+j)%2==0){
                            board[displayRow][displayCol].setBackground(red);
                        }
                        else{
                            board[displayRow][displayCol].setBackground(black);
                        }
                    }
                }
            }
        }
    }
    public void doAction(ActionEvent e){
        if(mouseDisabled){
            return;
        }
        if(e.getSource() == util1){
            GameState.resetHistory();
            GameState.setUpBoard();
            updateBoardState();
            setText("White to Move:");
            setWarning("New game started.");
        }
        if(e.getSource() == util2){
            GameState.goBack();
            updateBoardState();
            setText(GameState.whiteTurn() ? "White to Move:" : "Black to Move:");
            if(GameState.whiteThreatened(GameState.getBoard())){
                setText("Check! White to move.");
            }
            if(GameState.blackThreatened(GameState.getBoard())){
                setText("Check! Black to move.");
            }
            setWarning("Last move undone.");
        }
        if(e.getSource() == util3){
            flipped = !flipped;
            updateBoardState();
            setWarning("Board flipped.");
        }
    }
        public void setPromo(int row, int col){
            promoRow = row;
            promoCol = col;
        }
    public void promote(ActionEvent e){
        if(e.getSource() == promo1){
            GameState.change(promoRow == 7, "Q", promoRow, promoCol);
            setWarning("Promotion to Queen.");
        }
        if(e.getSource() == promo2){
            GameState.change(promoRow == 7, "N", promoRow, promoCol);
            setWarning("Promotion to Knight.");
        }
        if(e.getSource() == promo3){
            GameState.change(promoRow == 7, "B", promoRow, promoCol);
            setWarning("Promotion to Bishop.");
        }
        if(e.getSource() == promo4){
            GameState.change(promoRow == 7, "R", promoRow, promoCol);
            setWarning("Promotion to Rook.");
        }
        updateBoardState();
        mouseDisabled = false;
        promo1.setVisible(false);
        promo2.setVisible(false);
        promo3.setVisible(false);
        promo4.setVisible(false);
    }
    public void mousePressed(MouseEvent e){
        if(mouseDisabled){
            return;
        }
        for(int i=0; i<board.length; i++){
            for(int j=0; j<board[0].length; j++){
                int logicalRow = flipped ? i : 7-i; //flips the board for black
                int logicalCol = flipped ? j : 7-j; //flips the board for black
                if(e.getSource() == board[i][j]){
                    if(!GameState.notOccupied(logicalRow, logicalCol)){
                        selectRow = logicalRow;
                        selectCol = logicalCol;
                        movableSquares(logicalRow, logicalCol);
                    }
                    if(GameState.notOccupied(logicalRow, logicalCol)){
                        selectRow = -1;
                        selectCol = -1;
                    }
                }
            }
        }
    }
    public void promotion(){
        promo1.setVisible(true);
        promo2.setVisible(true);
        promo3.setVisible(true);
        promo4.setVisible(true);
        warning.setText("Select a piece to promote to.");
        mouseDisabled = true;
    }
    public void actionPerformed(ActionEvent e){
        doAction(e);
        if(e.getSource() == promo1 || e.getSource() == promo2 || e.getSource() == promo3 || e.getSource() == promo4){
            promote(e);
        }
    }
    public void mouseEntered(MouseEvent e){
    }
    public void mouseReleased(MouseEvent e){
        if(mouseDisabled){
            return;
        }
        for(int i=0; i<board.length; i++){
            for(int j=0; j<board[0].length; j++){
                board[i][j].setBackground((i+j)%2==0 ? red : black);
            }
        }
        Point boardPoint = SwingUtilities.convertPoint((Component)e.getSource(), e.getPoint(), (Component) panel);
        for(int i=0; i<board.length; i++){
            for(int j=0; j<board[0].length; j++){
                if(board[i][j].getBounds().contains(boardPoint)){
                    int logicalRow = flipped ? i : 7-i; //flips the board for black
                    int logicalCol = flipped ? j : 7-j; //flips the board for black
                    if(selectRow != -1){
                        ChessPlayer.move = "" + selectRow+selectCol+ " "+logicalRow+logicalCol;
                        ChessPlayer.click = true;
                        System.out.println(ChessPlayer.move); //click functionality test
                        selectRow = -1;
                        selectCol = -1;
                    }
                }
            }
        }
    }
    public void mouseClicked(MouseEvent e){
    }
    public void mouseExited(MouseEvent e){
    }
    public void updateBoardState(){
        for(int i=7; i>=0; i--){
            for(int j=0; j<board[0].length; j++){
                 board[i][j].setIcon(null);
            }
        }

        for(int i=7; i>=0; i--){
            for(int j=0; j<board[0].length; j++){
                int displayRow = flipped ? i : 7-i; //flips the board for black
                int displayCol = flipped ? j : 7-j; //flips the board for black
                if(!GameState.notOccupied(i, j) && pieceList[i][j].getColor()){
                    if(pieceList[i][j].returnType() == "Pawn"){
                        board[displayRow][displayCol].setIcon(wPawn);
                    }
                    if(pieceList[i][j].returnType() == "Queen"){
                        board[displayRow][displayCol].setIcon(wQueen);
                    }
                    if(pieceList[i][j].returnType() == "Rook"){
                        board[displayRow][displayCol].setIcon(wRook);
                    }
                    if(pieceList[i][j].returnType() == "Bishop"){
                        board[displayRow][displayCol].setIcon(wBishop);
                    }
                    if(pieceList[i][j].returnType() == "King"){
                        board[displayRow][displayCol].setIcon(wKing);
                    }
                    if(pieceList[i][j].returnType() == "Knight"){
                        board[displayRow][displayCol].setIcon(wKnight);
                    }
                }
                if(!GameState.notOccupied(i, j) && !pieceList[i][j].getColor()){
                    if(pieceList[i][j].returnType() == "Pawn"){
                        board[displayRow][displayCol].setIcon(bPawn);
                    }
                    if(pieceList[i][j].returnType() == "Queen"){
                        board[displayRow][displayCol].setIcon(bQueen);
                    }
                    if(pieceList[i][j].returnType() == "Rook"){
                        board[displayRow][displayCol].setIcon(bRook);
                    }
                    if(pieceList[i][j].returnType() == "Bishop"){
                        board[displayRow][displayCol].setIcon(bBishop);
                    }
                    if(pieceList[i][j].returnType() == "King"){
                        board[displayRow][displayCol].setIcon(bKing);
                    }
                    if(pieceList[i][j].returnType() == "Knight"){
                        board[displayRow][displayCol].setIcon(bKnight);
                    }
                }
            }
        }
    }

    public void mouseDragged(MouseEvent e) {
        
    }
    public void mouseMoved(MouseEvent e) {
    
    }
}



