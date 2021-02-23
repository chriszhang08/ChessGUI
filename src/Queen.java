public class Queen extends Piece{

    public Queen(int color, Square initSq, Square[][] board) {
        super(color, initSq, board);
    }

    @Override
    public boolean isValidMove(int[] ICords, int[] ECords) {
        if (!(Math.abs(ICords[0] - ECords[0]) > 0 && Math.abs(ICords[1] - ECords[1]) > 0)){ //if its moving in a row
            return !getLinearOccupations(ICords, ECords); //if there's a piece in its path
        }
        else if (Math.abs(ICords[0] - ECords[0]) == Math.abs(ICords[1] - ECords[1])){ //if its moving diagonal
            return !getDiagonalOccupations(ICords, ECords); //if there's a piece in its path
        }
        return false;
    }

    @Override
    public boolean isInCheck(int[] KCords) {
        return false;
    }

    @Override
    public String toString() {
        return "QUEEN";
    }
}
