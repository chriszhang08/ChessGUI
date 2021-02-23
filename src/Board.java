import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Board extends JFrame implements MouseListener{
    private static Square[][] board;
    private static JButton[][] b;

    private static String defaultPiecePath = "D:\\Documents\\Computer Science\\resources\\";

    private Piece currPiece;
    private JButton start;
    private boolean whiteTurn = true;
    private boolean firstClick = true;
    private int columns;
    private int rows;

    public Board () throws IOException {
        board = new Square[8][8];  //first number is the row, second number is the column

        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                board[i][j] = new Square();
            }
        }

        for (int x = 0; x < 8; x++) {
            board[1][x].setPiece(new Pawn(1, board[1][x], board)); //instantiate black pawns
            board[6][x].setPiece(new Pawn(0, board[6][x], board)); //instantiate white pawns
        }

        board[0][3].setPiece(new Queen(1, board[0][3], board)); //instantiate black queen
        board[7][3].setPiece(new Queen(0, board[7][3], board)); //instantiate white queen

        board[0][4].setPiece(new King(1, board[0][4], board)); //instantiate black king
        board[7][4].setPiece(new King(0, board[7][4], board)); //instantiate white king

        board[0][0].setPiece(new Rook(1, board[0][0], board)); //instantiate black rooks
        board[0][7].setPiece(new Rook(1, board[0][7], board));
        board[7][0].setPiece(new Rook(0, board[7][0], board)); //instantiate white rooks
        board[7][7].setPiece(new Rook(0, board[7][7], board));

        board[0][1].setPiece(new Knight(1, board[0][1], board)); //instantiate black knights
        board[0][6].setPiece(new Knight(1, board[0][6], board));
        board[7][1].setPiece(new Knight(0, board[7][1], board)); //instantiate white knights
        board[7][6].setPiece(new Knight(0, board[7][6], board));

        board[0][2].setPiece(new Bishop(1, board[0][2], board)); //instantiate black bishops
        board[0][5].setPiece(new Bishop(1, board[0][5], board));
        board[7][2].setPiece(new Bishop(0, board[7][2], board)); //instantiate white bishops
        board[7][5].setPiece(new Bishop(0, board[7][5], board));

        JFrame frame = new JFrame();

        GridLayout gridLayout = new GridLayout(8,8, 0, 0);

        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
        panel.setLayout(gridLayout);
        panel.setSize(200,200);

        b = new JButton[8][8];
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                b[i][j] = new JButton();
            }
        }
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                b[i][j].setLayout(new OverlayLayout(b[i][j]));
                b[i][j].setOpaque(true);
                //b.setPreferredSize(new Dimension(80, 80));

                if ((j % 2 == 1 && i % 2 == 1) || (j % 2 == 0 && i % 2 == 0)){
                    b[i][j].setBackground(Color.white);
                    board[i][j].setColor(Color.white);
                }
                else {
                    b[i][j].setBackground(new Color(24, 127, 217));
                    board[i][j].setColor(new Color(24, 127, 217));
                }
                if(board[i][j].getPiece() != null)
                    b[i][j].add(create(ImageIO.read(new File(defaultPiecePath + board[i][j].getPiece().getColor()
                            + board[i][j].getPiece().toString() + ".png"))));
                b[i][j].addMouseListener(this);
                panel.add(b[i][j]);
            }

        }

        frame.add(panel, BorderLayout.CENTER);
        frame.setLocationByPlatform(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Chess");
        frame.setSize(800, 800);
        frame.setVisible(true);
        frame.setResizable(false);

    }

    private static JLabel create(Image image) {
        JLabel label = new JLabel() {
            private static final int N = 64;

            @Override
            public boolean isOpaque() {
                return false;
            }

            @Override
            public Dimension getPreferredSize() {
                return new Dimension(2 * N, 2 * N);
            }

            @Override
            public Dimension getMaximumSize() {
                return new Dimension(2 * N, 2 * N);
            }

        };
        label.setIcon(new ImageIcon(image));
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setVerticalAlignment(JLabel.CENTER);
        //label.setAlignmentX(0.0f);
        // label.setAlignmentY(0.0f);
        return label;

    }

    public static void castle(int[] ICords, int[] ECords){
        try {
            b[ECords[0]][ECords[1]].add(create(ImageIO.read(new File(defaultPiecePath + board[ECords[0]][ECords[1]].getPiece().getColor()
                    + board[ECords[0]][ECords[1]].getPiece() + ".png"))));
        } catch (IOException e) {
            e.printStackTrace();
        }
        b[ICords[0]][ICords[1]].remove(0);
        b[ICords[0]][ICords[1]].revalidate();
        b[ICords[0]][ICords[1]].repaint();
    }

    public static int[] getCords(Piece p){
        for(int i = 0; i < board.length; i++){
            for(int j = 0; j < board[i].length; j++){
                if(board[i][j].getPiece() != null && board[i][j].getPiece().equals(p)){
                    return new int[]{i, j};
                }
            }
        }
        return new int[2];
    }

    public static int[][] getCastleCords(int[] ECords){ //[0] is ICords, [1] is ECords
        if(ECords[0] == 0 && ECords[1] == 6){
            return new int[][]{ {0,7} , {0, 5}};
        }
        else if(ECords[0] == 0 && ECords[1] == 2){
            return new int[][]{ {0,0} , {0, 3}};
        }
        else if(ECords[0] == 7 && ECords[1] == 6){
            return new int[][]{ {7,7} , {7, 5}};
        }
        else if(ECords[0] == 7 && ECords[1] == 2){
            return new int[][]{ {7,0} , {7, 3}};
        }
        return new int[][]{ {-1, -1} , {-1, -1}};
    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {

        if(firstClick) {
            columns = (int) ((mouseEvent.getComponent().getLocation().getX() - 20) / 93);
            rows = (int) ((mouseEvent.getComponent().getLocation().getY() - 20) / 90);

            currPiece = board[rows][columns].getPiece(); //obtain logical board location

            if (currPiece == null) { //is there a selected piece
                return;
            }

            if (currPiece.getColor() == 1 && whiteTurn) { //is it the correct turn
                return;
            }
            if (currPiece.getColor() == 0 && !whiteTurn){
                return;
            }

            firstClick = false;

            start = (JButton) mouseEvent.getComponent();
            start.setBackground(new Color(131, 214, 255));
        }
        else{
            int c = (int) ((mouseEvent.getComponent().getLocation().getX() - 20)/93);
            int r = (int) ((mouseEvent.getComponent().getLocation().getY() - 20)/90);
            JButton end = (JButton) mouseEvent.getComponent(); //change GUI location to end
            end.setLayout(new OverlayLayout(end));

            int kx;
            int ky;

            currPiece.makeTmp();
            if(board[r][c].getPiece() == null){
                if(currPiece.isValidMove(new int[] {rows, columns}, new int[] {r, c})) {
                    currPiece.move(board[r][c]);
                    for(int i = 0; i < board.length; i++){
                        boolean quit = false;
                        if(Board.getCords(new Pawn(0))[0] == 0){
                            while(!quit) {
                                Scanner input = new Scanner(System.in);
                                System.out.println("What piece do you want: ");
                                String promote = input.nextLine().toUpperCase();
                                switch (promote) {
                                    case "QUEEN":
                                        board[r][c].setPiece(new Queen(0, board[r][c], board));
                                        quit = true;
                                        break;
                                    case "KNIGHT":
                                        board[r][c].setPiece(new Knight(0, board[r][c], board));
                                        quit = true;
                                        break;
                                    case "BISHOP":
                                        board[r][c].setPiece(new Bishop(0, board[r][c], board));
                                        quit = true;
                                        break;
                                    case "ROOK":
                                        board[r][c].setPiece(new Rook(0, board[r][c], board));
                                        quit = true;
                                        break;
                                    default:
                                        System.out.println("Please input a valid piece.");
                                        break;
                                }
                                currPiece = board[r][c].getPiece();
                            }
                        }
                        else if (Board.getCords(new Pawn(1))[0] == 7){
                            while(!quit){
                                Scanner input = new Scanner(System.in);
                                System.out.println("What piece do you want: ");
                                String promote = input.nextLine().toUpperCase();
                                switch (promote) {
                                    case "QUEEN":
                                        board[r][c].setPiece(new Queen(0, board[r][c], board));
                                        quit = true;
                                        break;
                                    case "KNIGHT":
                                        board[r][c].setPiece(new Knight(0, board[r][c], board));
                                        quit = true;
                                        break;
                                    case "BISHOP":
                                        board[r][c].setPiece(new Bishop(0, board[r][c], board));
                                        quit = true;
                                        break;
                                    case "ROOK":
                                        board[r][c].setPiece(new Rook(0, board[r][c], board));
                                        quit = true;
                                        break;
                                    default:
                                        System.out.println("Please input a valid piece.");
                                        break;
                                }
                                currPiece = board[r][c].getPiece();
                            }
                        }
                    }
                    if(whiteTurn){
                        kx = Board.getCords(new King(0))[1];
                        ky = Board.getCords(new King(0))[0];
                    } else {
                        kx = Board.getCords(new King(1))[1];
                        ky = Board.getCords(new King(1))[0];
                    }
                    if(board[ky][kx].getPiece().isInCheck(new int[]{ky, kx})){
                        ((King)board[ky][kx].getPiece()).setDoCastle(false);
                        currPiece.unmove();
                        firstClick = true;
                        start.setBackground(board[rows][columns].getColor());
                        return;
                    } else {
                        try {
                            end.add(create(ImageIO.read(new File(defaultPiecePath + currPiece.getColor()
                                    + currPiece + ".png"))));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        if( ((King)board[ky][kx].getPiece()).isDoCastle()){
                            Board.castle( getCastleCords(new int[]{r, c})[0], getCastleCords(new int[] {r, c})[1] );
                        }
                    }
                    //chess notation of move
                } else {
                    return;
                }
            }
            else if (board[r][c].getPiece().getColor() == currPiece.getColor()){
                firstClick = true;
                start.setBackground(board[rows][columns].getColor());
                return;
            }
            else{
                if(currPiece.isValidMove(new int[] {rows, columns}, new int[] {r, c})) {
                    currPiece.move(board[r][c]);
                    if(whiteTurn){
                        kx = Board.getCords(new King(0))[1];
                        ky = Board.getCords(new King(0))[0];
                    }
                    else{
                        kx = Board.getCords(new King(1))[1];
                        ky = Board.getCords(new King(1))[0];
                    }
                    if(board[ky][kx].getPiece().isInCheck(new int[]{ky, kx})){
                        ((King)board[ky][kx].getPiece()).setDoCastle(false);
                        currPiece.unmove();
                        firstClick = true;
                        start.setBackground(board[rows][columns].getColor());
                        return;
                    }
                    else {
                        currPiece.move(board[r][c]);
                        try {
                            end.add(create(ImageIO.read(new File(defaultPiecePath + currPiece.getColor()
                                    + currPiece + ".png"))));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    System.out.println(currPiece.annotate(false, true, columns, c, r));
                    //chess notation of move
                }
                else{
                    return;
                }
            }
            if(end.getComponentCount() > 1){
                end.remove(0);
            }

            start.remove(0);
            start.setBackground(board[rows][columns].getColor());
            firstClick = true;
            whiteTurn = !whiteTurn;

            end.revalidate();
            end.repaint();

        }
        start.revalidate();
        start.repaint();
    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {
    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {
    }


    public static void main(String[] args) throws IOException {

        new Board();

    }
}
