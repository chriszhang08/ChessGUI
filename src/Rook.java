public class Rook extends Piece {

    public Rook(int color, Square initSq, Square[][] board) {
        super(color, initSq, board);
    }

    @Override                 // initial pos    end pos
    public boolean isValidMove(int[] ICords, int[] ECords) {   //[0] is the Y cord [1] is the X cord
        if (Math.abs(ICords[0] - ECords[0]) > 0 && Math.abs(ICords[1] - ECords[1]) > 0) {
            return false;
        }
        else {
            return !getLinearOccupations(ICords, ECords);
        }
    }

    @Override
    public boolean isInCheck(int[] KCords) {
        return false;
    }

    @Override
    public String toString() {
        return "ROOK";
    }
}
