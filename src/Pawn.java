public class Pawn extends Piece {

    public Pawn(int color, Square initSq, Square[][] board) {
        super(color, initSq, board);
    }

    public Pawn(int color){
        super(color);
    }

    @Override                 // initial pos    end pos
    public boolean isValidMove(int[] ICords, int[] ECords) {  //[0] is the Y cord [1] is the X cord

        if(this.getColor() == 0){ //if the piece is white
            if(board[ECords[0]][ECords[1]].getPiece() != null && //eat function
                    board[ECords[0]][ECords[1]].getPiece().getColor() != board[ICords[0]][ICords[1]].getPiece().getColor()){
                return (ECords[0] - ICords[0] == -1 && Math.abs(ECords[1] - ICords[1]) == 1);
            }
            if(getFirstMove()){ //checks first move
                return ( (ECords[0] - ICords[0] > -3 && ECords[1] - ICords[1] == 0) && !getLinearOccupations(ICords, ECords) );
            }
            else{
                return (ECords[0] - ICords[0] == -1 && ECords[1] - ICords[1] == 0);
            }
        }
        else{ //if this piece is black
            if(board[ECords[0]][ECords[1]].getPiece() != null && //eat function
                    board[ECords[0]][ECords[1]].getPiece().getColor() != board[ICords[0]][ICords[1]].getPiece().getColor()){
                return (ECords[0] - ICords[0] == 1 && Math.abs(ECords[1] - ICords[1]) == 1);
            }
            if(getFirstMove()){ //checks first move
                return ( (ECords[0] - ICords[0] < 3 && ECords[1] - ICords[1] == 0) && !getLinearOccupations(ICords, ECords) );
            }
            else{
                return (ECords[0] - ICords[0] == 1 && ECords[1] - ICords[1] == 0);
            }
        }
    }


    @Override
    public boolean isInCheck(int[] KCords) {
        return false;
    }

    @Override
    public String toString() {
        return "PAWN";
    }
}
