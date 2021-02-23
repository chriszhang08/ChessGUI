public class King extends Piece {

    private boolean doCastle;

    public King(int color, Square initSq, Square[][] board) {
        super(color, initSq, board);
    }

    public King(int color){ super(color); }

    public boolean isDoCastle(){
        return doCastle;

    }

    public void setDoCastle(boolean b){
        doCastle = b;
    }

    public boolean isInCheck(int[] KCords){
        for(int i = 0; i < board.length; i++){
            for(int j = 0; j < board[i].length; j++){
                if(board[i][j].getPiece() != null && board[i][j].getPiece().getColor() != this.getColor()){
                    if(board[i][j].getPiece().isValidMove(new int[]{i, j}, KCords)){
                        return  true;
                    }
                }
            }
        }
        //check every piece on the board and if king spot is valid move, is in check
        //if i move this piece and my king is in check, it is not a valid move
        //or i move the piece and my king is in check, then move it back
        return false;
    }

    @Override
    public boolean isValidMove(int[] ICords, int[] ECords) {
        doCastle = false;
        if(getFirstMove() && !getLinearOccupations(ICords, ECords)){ //castle clause
            if(ECords[0] == 0 && ECords[1] == 6){
                if(board[0][7].getPiece() != null && board[0][7].getPiece().getFirstMove() && !this.isInCheck(ICords)){
                    board[0][7].getPiece().move(board[0][5]);
                    doCastle = true;
                    return true;
                }
            }
            else if(ECords[0] == 0 && ECords[1] == 2){
                if(board[0][0].getPiece() != null && board[0][0].getPiece().getFirstMove() && !this.isInCheck(ICords)){
                    board[0][0].getPiece().move(board[0][3]);
                    doCastle = true;
                    return true;
                }
            }
            else if(ECords[0] == 7 && ECords[1] == 6){
                if(board[7][7].getPiece() != null && board[7][7].getPiece().getFirstMove() && !this.isInCheck(ICords)){
                    board[7][7].getPiece().move(board[7][5]);
                    doCastle = true;
                    return true;
                }
            }
            else if(ECords[0] == 7 && ECords[1] == 2){
                if(board[7][0].getPiece() != null && board[7][0].getPiece().getFirstMove() && !this.isInCheck(ICords)){
                    board[7][0].getPiece().move(board[7][3]);
                    doCastle = true;
                    return true;
                }
            }
        }
        return !(Math.abs(ECords[1] - ICords[1]) > 1 || Math.abs(ECords[0] - ICords[0]) > 1);
    }

    @Override
    public String toString() {
        return "KING";
    }
}
