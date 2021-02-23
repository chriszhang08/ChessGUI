import javax.swing.*;
import java.awt.*;

public class Square extends JComponent implements Cloneable{
    private Piece occupied; //occupying piece of the square
    private Color color; //color of the square, 0 is white, 1 is black, 2 is neither

    public Square(Piece piece){
        occupied = piece;
    }

    public Square(){
    }

    public void setColor(Color color){
        this.color = color;
    }

    public Color getColor(){ return color; }

    public void setPiece(Piece piece){
        occupied = piece;
    }

    public Piece getPiece(){ return occupied; }

    @Override
    public String toString(){
        if(occupied == null)
            return "null";
        else
            return occupied.toString();
    }

}
