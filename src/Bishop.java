public class Bishop extends Piece {

    public Bishop(int color, Square initSq, Square[][] board) {
        super(color, initSq, board);
    }

    @Override
    public boolean isValidMove(int[] ICords, int[] ECords) {
        if(Math.abs(ICords[0] - ECords[0]) != Math.abs(ICords[1] - ECords[1])){
            return false;
        }
        else {
            return !getDiagonalOccupations(ICords, ECords);
        }
    }

    @Override
    public boolean isInCheck(int[] KCords) {
        return false;
    }

    @Override
    public String toString() {
        return "BISHOP";
    }
}
