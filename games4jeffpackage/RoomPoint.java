package games4jeffpackage;

public class RoomPoint
{
    private int x;
    private int y;

    public RoomPoint(int x, int y){
        this.x = x;
        this.y = y;
    }
    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }
    public boolean isPoint(RoomPoint A){
        if (this.x == A.getX() && this.y == A.getY()){
          return true;
        }
        return false;
    }
    public String toString(){
        return "[" + x + ", " + y + "]";
    }
}