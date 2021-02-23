public abstract class Piece {

    private Square currentSquare;
    private int color; //0 is white, 1 is black
    public Square[][] board;

    private Square[][] tmpB;

    public int moveCounter;

    public Piece(int color, Square initSq, Square[][] board) {
        this.color = color;
        this.currentSquare = initSq;
        this.board = board;

        tmpB = new Square[8][8];

        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                tmpB[i][j] = new Square();
            }
        }
    }
    
    public Piece (int color){
        this.color = color;
    }

    public void makeTmp(){
        System.out.println("move print");
        for(int i = 0; i < board.length; i++){
            for(int j = 0; j < board[i].length; j++){
                if(board[i][j].getPiece() != null){
                    tmpB[i][j].setPiece(board[i][j].getPiece());
                    System.out.print(tmpB[i][j].getPiece().toString() + " ");
                }
                else{
                    tmpB[i][j].setPiece(null);
                    System.out.print("null ");
                }
            }
            System.out.println();
        }
    }

    public void move(Square s) {
        moveCounter++;
        currentSquare.setPiece(null);
        currentSquare = s;
        s.setPiece(this);
    }

    public void unmove() {
        for(int i = 0; i < tmpB.length; i++){
            for(int j = 0; j < tmpB[i].length; j++){
                if(tmpB[i][j].getPiece() != null){
                    board[i][j].setPiece(tmpB[i][j].getPiece());
                }
                else{
                    board[i][j].setPiece(null);
                }
            }
        }
        moveCounter--;
    }

    public String annotate(boolean castle, boolean eat, int columns, int c, int r){
        if (eat) {
            if (this.toString().equals("PAWN")) {
                return (char)(columns+97) + "x" + (char)(c+97) + (8-r);
            }
            else if (this.toString().equals("KNIGHT")){
                return "Nx" + (char)(c+97) + (8-r);
            }
            else {
                return this.toString().substring(0, 1) + "x" + (char)(c+97) + (8-r);
            }
        }
        else if (castle){
            if(c == 2){
                return "O-O-O";
            }
            else{
                return "O-O";
            }
        }
        else{
            if (this.toString().equals("PAWN")) {
                return "" + (char)(c+97) + (8-r);
            }
            else if (this.toString().equals("KNIGHT")){
                return "N" + (char)(c+97) + (8-r);
            }
            else {
                return this.toString().substring(0, 1) + (char)(c+97) + (8-r);
            }
        }
    }

    public int getColor(){ return color;}


    public boolean getLinearOccupations(int[] ICords, int[] ECords){
        if(board[ECords[0]][ECords[1]].getPiece() != null && this.getColor() == board[ECords[0]][ECords[1]].getPiece().getColor()){
            return true; //final piece is same color
        }
        else if(ICords[0] == ECords[0]){ // rows or y's are the same
            if(ICords[1] > ECords[1]){ //moving left
                for(int i = ECords[1] + 1; i < ICords[1]; i++){
                    if(board[ICords[0]][i].getPiece() != null){
                        return true; //there is a piece occupying the path
                    }
                }
            }
            else {
                for(int i = ECords[1] - 1; i > ICords[1]; i--){ //moving right
                    if(board[ICords[0]][i].getPiece() != null){
                        return true; //there is a piece occupying the path
                    }
                }
            }
        }
        else { //columns or x's are the same
            if (ICords[0] > ECords[0]) { //moving up
                for(int i = ECords[0] + 1; i < ICords[0]; i++){
                    if(board[i][ICords[1]].getPiece() != null){
                        return true; //there is a piece occupying the path
                    }
                }
            } else {
                for(int i = ECords[0] - 1; i > ICords[0]; i--){ //moving down
                    if(board[i][ICords[1]].getPiece() != null){
                        return true; //there is a piece occupying the path
                    }
                }
            }
        }
        return false; //there isn't a piece in the path
    }

    public boolean getDiagonalOccupations(int[] ICords, int[] ECords){
        int x;
        int y;
        if(board[ECords[0]][ECords[1]].getPiece() != null && this.getColor() == board[ECords[0]][ECords[1]].getPiece().getColor()){
            return true; //the final piece is the same color
        }
        else if(ECords[0] > ICords[0] && ECords[1] > ICords[1]){ //Goes in the south east direction
            x = ECords[1] - 1;
            y = ECords[0] - 1;
            while(y > ICords[0] && x > ICords[1]){
                if(board[y][x].getPiece() != null){
                    return true; //there is a piece in the path
                }
                x--;
                y--;
            }
        }
        else if (ECords[0] < ICords[0] && ECords[1] > ICords[1]){ //Goes in the north east direction
            x = ECords[1] - 1;
            y = ECords[0] + 1;
            while(y < ICords[0] && x > ICords[1]){
                if(board[y][x].getPiece() != null){
                    return true; //there is a piece in the path
                }
                x--;
                y++;
            }
        }
        else if (ECords[0] < ICords[0] && ECords[1] < ICords[1]){ //Goes in the north west direction
            x = ECords[1] + 1; //columns
            y = ECords[0] + 1; //rows
            while(y < ICords[0] && x < ICords[1]){
                if(board[y][x].getPiece() != null){
                    return true; //there is a  piece in the path
                }
                x++;
                y++;
            }
        }
        else{ //Goes in the south west direction
            x = ECords[1] + 1;
            y = ECords[0] - 1;
            while(y > ICords[0] && x < ICords[1]){
                if(board[y][x].getPiece() != null){
                    return true; //there is a piece in the path
                }
                x++;
                y--;
            }
        }
        return false;
    }

    @Override
    public boolean equals(Object obj) {
        if(this.getClass().equals(obj.getClass())){
            return this.getColor() == ((Piece) obj).getColor();
        }
        return false;
    }

    public Square getSquare(){
        return currentSquare;
    }

    public abstract boolean isValidMove(int[] ICords, int[] ECords);

    protected boolean getFirstMove(){
        return !(moveCounter > 0);
    }

    public abstract boolean isInCheck(int[] KCords);
}
