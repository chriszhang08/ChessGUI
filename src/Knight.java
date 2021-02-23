public class Knight extends Piece {

    public Knight(int color, Square initSq, Square[][] board) {
        super(color, initSq, board);
    }

    @Override                 // initial pos    end pos
    public boolean isValidMove(int[] ICords, int[] ECords) {  //[0] is the Y cord [1] is the X cord
        return (Math.abs(ECords[1] - ICords[1]) == 2 && Math.abs(ECords[0] - ICords[0]) == 1) ||
                (Math.abs(ECords[1] - ICords[1]) == 1 && Math.abs(ECords[0] - ICords[0]) == 2);
    }

    @Override
    public boolean isInCheck(int[] KCords) {
        return false;
    }

    @Override
    public String toString() {
        return "KNIGHT";
    }

}
