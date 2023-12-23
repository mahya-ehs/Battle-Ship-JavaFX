package sample;

import javafx.geometry.Point2D;

import java.util.ArrayList;

public class Warrior
{
    ArrayList<Point2D> possessed = new ArrayList<>();       //it stores the possessed cells by each fighter
    private String type;        //it can be soldier, cavalier, castle or head
    private Player player;
    protected boolean isAlive = true;
    private Point2D pos;
    protected int Area;       //1 cell, 2, 4, or 9
    private int power;
    private int movement;   // 0, 1 or 2 cells
    private Point2D[][] position;

    public String getType() {
        return type;
    }

    public void setType(String name) {
        this.type = type;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void setAlive(boolean alive) {
        isAlive = alive;
    }

    public int getArea() {
        return Area;
    }

    public void setArea(int area) {
        this.Area = Area;
    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public int getMovement() {
        return movement;
    }

    public void setMovement(int movement) {
        this.movement = movement;
    }

    public Point2D getPos() {
        return pos;
    }

    public Point2D[][] getPosition()
    {
        return position;
    }
    public void setPos(Point2D pos) {
    this.pos = pos; }

    public void shoot()
    {
        Area -= 1;

    }

    public ArrayList<Point2D> getPossessed() {
        return possessed;
    }
}

class Soldier extends Warrior
{

    private Point2D[][] position = new Point2D[1][1];
    int h = 0, k = 0;
    Soldier(){Area = 1;}
    public Point2D getPos() {
        return position[0][0];
    }

    public void setPos(Point2D pos)
    {
        position[0][0] = pos;
        possessed.add(position[0][0]);
    }
    public String getType()
    {
        return "soldier";
    }
}

class Cavalier extends Warrior
{
    private Point2D[][] position = new Point2D[2][2];
    private boolean isVertical = true;
    int k = 0, h = 0, Xlength, Ylength;

    Cavalier(){Area = 2;}
    public String getType()
    {
        return "cavalier";
    }
    public void setPos(Point2D pos)
    {
        int x = (int) pos.getX();
        int y = (int) pos.getY();
        if(isVertical)
        {
            Xlength = x + 2;
            Ylength = y + 1;
        }else{
            Xlength = x + 1;
            Ylength = y + 2;
        }

        if( Xlength <= Board.SIZE && Ylength <= Board.SIZE)
        {
            for (int i = x; i < Xlength; i++ )
            {
                h = 0;
                for (int j = y; j < Ylength; j++)
                {
                    position[k][h] = new Point2D(i, j);
                    possessed.add(position[k][h]);
                    h++;
                }
                k++;
            }
        }}

    public Point2D[][] getPosition()
    {
        return position;
    }

    public boolean isVertical() {
        return isVertical;
    }

    public void setVertical(boolean vertical) {
        isVertical = vertical;
    }
}

class Castle extends Warrior
{
    private Point2D[][] position = new Point2D[2][2];
    int k = 0, h = 0;
    Castle(){Area = 4;}
    public String getType()
    {
        return "castle";
    }
    public void setPos(Point2D pos)
    {
        int x = (int) pos.getX();
        int y = (int) pos.getY();
        if( (x + 2) <= Board.SIZE && (y + 2) <= Board.SIZE)
        {
            for (int i = x; i < x + 2; i++ )
            {
                h = 0;
                for (int j = y; j < y + 2; j++)
                {
                    position[k][h] = new Point2D(i, j);
                    possessed.add(position[k][h]);
                    h++;
                }
                k++;
            }
        }
    }
    public Point2D[][] getPosition()
    {
        return position;
    }
}

class Head extends Warrior
{
    private Point2D[][] position = new Point2D[3][3];
    private int k = 0, h = 0;
    Head()
    {
        Area = 9;
    }
    public String getType()
    {
        return "head";
    }
    public void setPos(Point2D pos)
    {
        int x = (int) pos.getX();
        int y = (int) pos.getY();
        if ((x + 3) <= Board.SIZE && (y + 3) <= Board.SIZE)
        {
            for (int i = x; i < x + 3; i++)
            {
                h = 0;
                for (int j = y; j < y + 3; j++)
                {

                    position[k][h] = new Point2D(i, j);
                    possessed.add(position[k][h]);

                    h++;
                }
                k++;
            }
        }

    }
}