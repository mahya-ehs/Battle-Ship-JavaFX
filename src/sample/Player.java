package sample;

import javafx.geometry.Point2D;

import java.util.ArrayList;

public class Player
{
    private String name;
    private boolean turn;
    private boolean isWinner;
    private Board board;
    private ArrayList<Warrior> fighters = new ArrayList<>();
    private ArrayList<Soldier> soldiers = new ArrayList<>();
    private ArrayList<Cavalier> cavaliers = new ArrayList<>();
    private ArrayList<Castle> castles = new ArrayList<>();
    private ArrayList<Point2D> possessedCells = new ArrayList<>();
    private Head head = new Head();
    private int NumberOfWarriors = 0, NumberOfSoldiers = 0, NumberOfCavaliers = 0, NumberOfCastles = 0, NumberOfHeads = 0;

    public Player(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isWinner() {
        return isWinner;
    }

    public void setWinner(boolean winner) {
        isWinner = winner;
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board)
    {
        this.board = board;
    }

    public boolean isTurn() {
        return turn;
    }

    public void setTurn(boolean turn) {
        this.turn = turn;
    }

    public Head getHead() {
        return head;
    }

    public void setHead(Head head) {
        this.head = head;
        NumberOfHeads ++;
        fighters.add(head);
        NumberOfWarriors ++;
    }

    public void setSoldiers(Soldier soldier) {
        soldiers.add(soldier);
        fighters.add(soldier);

        NumberOfSoldiers ++;
        NumberOfWarriors ++;
    }

    public void setCavaliers(Cavalier cavalier) {
        cavaliers.add(cavalier);
        fighters.add(cavalier);

        NumberOfWarriors ++;
        NumberOfCavaliers ++;
    }

    public void setCastles(Castle castle) {
        castles.add(castle);
        fighters.add(castle);

        NumberOfWarriors ++;
        NumberOfCastles ++;
    }

    public int getNumberOfSoldiers() {
        return NumberOfSoldiers;
    }

    public int getNumberOfCavaliers() {
        return NumberOfCavaliers;
    }

    public int getNumberOfCastles() {
        return NumberOfCastles;
    }

    public int getNumberOfHeads() {
        return NumberOfHeads;
    }

    public int getNumberOfWarriors() {
        return NumberOfWarriors;
    }

    public ArrayList<Warrior> getFighters()
    {
        return fighters;
    }

    public void KillFighter(int x, int y)
    {
        Point2D p = new Point2D(x, y);
        if (head.getPossessed().contains(p))
        {
            head.shoot();
            if (head.getArea() < 1)
            {
                NumberOfHeads --;
                NumberOfWarriors --;
            }
        }

        for (int i = 0; i < soldiers.size(); i++)
            if (soldiers.get(i).getPos().getX() == x && soldiers.get(i).getPos().getY() == y)
            {
                soldiers.get(i).shoot();
                soldiers.get(i).setAlive(false);
                NumberOfSoldiers--;
                NumberOfWarriors --;
            }

        for (int i = 0; i < castles.size(); i++)
            if (castles.get(i).getPossessed().contains(new Point2D(x, y)))
            {
                castles.get(i).shoot();
                if (castles.get(i).getArea() < 1)
                {
                    castles.get(i).setAlive(false);
                    NumberOfCastles --;
                    NumberOfWarriors --;
                }
            }

        for (int i = 0; i < cavaliers.size(); i++)
            if (cavaliers.get(i).getPossessed().contains(new Point2D(x, y)))
            {
                cavaliers.get(i).shoot();
                if ( cavaliers.get(i).getArea() < 1)
                {
                    cavaliers.get(i).setAlive(false);
                    NumberOfCavaliers --;
                    NumberOfWarriors --;
                }
            }
    }

    public ArrayList<Point2D> getPossessedCells() {
        return possessedCells;
    }
}
