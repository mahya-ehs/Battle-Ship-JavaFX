package sample;
import javafx.geometry.Point2D;
import javafx.scene.effect.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import java.util.ArrayList;

public class Board extends StackPane
{
    private int LastUse1 = 0, LastUse2 = 0, LastUse3 = 0;
    private int counter = 0;            //checks the number of turns
    private Player rival;
    private boolean clicked;
    private boolean PosMode;
    private boolean isAttackMode;
    private boolean isSoldier;
    private boolean isCavalier;
    private boolean isCastle;
    private boolean isHeadQuarter;
    private Player player;
    static int SIZE;
    private VBox ROWS = new VBox();
    private ArrayList<Cell> cells = new ArrayList<>(SIZE * SIZE);

    public Board(Player player, Player rival)
    {
        this.player = player;
        this.rival = rival;
        for (int i = 0; i < SIZE; i++)
        {
            HBox eachRow = new HBox();
            for (int j = 0; j < SIZE; j++)
            {
                Cell rec = new Cell(i, j);
                cells.add(rec);
                eachRow.getChildren().add(rec);
            }
            ROWS.getChildren().add(eachRow);
        }
        getChildren().add(ROWS);
    }

    public void setColor(Color color)           //colors the whole board
    {
        for (int i = 0; i < SIZE; i++)
        {
            for (int j = 0; j < SIZE; j++)
                getCell(i, j).setFill(color);
        }
    }

    public ArrayList<Cell> getCells()
    {
        return cells;
    }

    public class Cell extends Rectangle
    {
        public int x;
        public int y;
        boolean isChosen = false;
        boolean isPossessed = false;
        boolean isShot;     //fire
        boolean isAttacked;        //block
        private Cell(int x, int y)
        {
            super(600 / SIZE, 600 / SIZE);
            this.x = x;
            this.y = y;
            setFill(Color.LAVENDERBLUSH);
            setStroke(Color.BLACK);
        }
        private int getCellX()
        {
            return x;
        }
        private int getCellY()
        {
            return y;
        }

        public boolean isPossessed() {
            return isPossessed;
        }

        public void setPossessed(boolean possessed) {
            isPossessed = possessed;
        }

    }

    public void ColorCells()            //relates to the choosing scene
    {
        for (int u = 0; u < SIZE; u++)
            for (int p = 0; p < SIZE; p++)
            {
                Cell c = getCell(u, p);
                c.setOnMouseEntered(e ->{
                    Cell cell = (Cell)e.getSource();
                    int x = cell.getCellX();
                    int y = cell.getCellY();
                    int xLength = getLimitation('x');
                    int yLength = getLimitation('y');
                    for (int i = x; i < x + xLength; i++)
                        for (int j = y; j < y + yLength; j++)
                        {
                            if(x + xLength <= SIZE && y + yLength <= SIZE)
                                getCell(i, j).setFill(Color.LIGHTPINK);
                        }
                    c.setOnMouseClicked(e1 -> {
                        if(handleClicks(x, y) && isValidPos(x, y, xLength, yLength))
                        {
                            for (int k = x; k < x + xLength; k++)
                            {
                                for (int h = y; h < y + yLength; h++)
                                {
                                    if( ((x + xLength) <= SIZE) && ((y + yLength) <= SIZE) && (! getCell(k, h).isChosen))
                                    {
                                        getCell(k, h).setFill(Color.INDIANRED);
                                        getCell(k, h).isChosen = true;
                                    }
                                }
                            }

                        }
                        else
                            ConfirmBox.sorry("Oops! seems like you can't choose more. Try again");
                    });
                    c.setOnMouseExited(e1 -> {
                        for (int k = x; k < x + xLength; k++)
                        {
                            for (int h = y; h < y + yLength; h++)
                            {
                                if(k < SIZE && h < SIZE)
                                {
                                    if(!getCell(k, h).isChosen)
                                    {
                                        getCell(k, h).setFill(Color.LAVENDERBLUSH);
                                    }else{
                                        getCell(k, h).setFill(Color.INDIANRED);
                                    }
                                }
                            }
                        }
                    });

                });
            }

    }

    public void AttackCell()            //relates to the playing scene
    {
        for (int u = 0; u < SIZE; u++)
            for (int v = 0; v < SIZE; v++)
            {
                Cell c = getCell(u, v);
                c.setOnMouseEntered(e ->{
                    Cell cell = (Cell)e.getSource();
                    int x = cell.getCellX();
                    int y = cell.getCellY();
                    int xLength = getLimitation('x');
                    int yLength = getLimitation('y');
                        for (int i = x; i < x + xLength; i++)
                        for (int j = y; j < y + yLength; j++)
                        {
                            if(x + xLength <= SIZE && y + yLength <= SIZE)
                                getCell(i, j).setFill(Color.ALICEBLUE);
                        }
                    c.setOnMouseClicked(e1 -> {
                        if(rival.getNumberOfSoldiers() == 0 && counter != 0 && checkTime())
                        {
                            ConfirmBox.sorry("Oops! you're stuck. let your rival play the turn");
                            rival.setTurn(false);
                            player.setTurn(true);
                            counter ++;
                        }
                        if(rival.isTurn() && RecoveryTime())
                        {
                            counter ++;
                            rival.setTurn(false);
                            Time();
                            for (int k = x; k < x + xLength; k++)
                            {
                                for (int h = y; h < y + yLength; h++)
                                {
                                    if( ((x + xLength) <= SIZE) && ((y + yLength) <= SIZE) )
                                    {
                                        if((getCell(k, h).isChosen) && !getCell(k, h).isAttacked && !getCell(k, h).isShot)
                                        {
                                            getCell(k, h).setFill(Color.FIREBRICK);
                                            getCell(k, h).setEffect(new DropShadow());
                                            player.KillFighter(k, h);
                                            getCell(k, h).isShot = true;
                                        }
                                        else
                                        {
                                            if(!getCell(k, h).isAttacked && !getCell(k, h).isShot)
                                            {
                                                getCell(k, h).setFill(Color.TRANSPARENT);
                                                getCell(k, h).isAttacked = true;
                                            }
                                        }
                                        clicked = true;
                                    }
                                }
                            }
                            if(clicked)
                                player.setTurn(true);
                        }
                    });
                    c.setOnMouseExited(e1 -> {
                        for (int k = x; k < x + xLength; k++)
                        {
                            for (int h = y; h < y + yLength; h++)
                            {
                                if(k < SIZE && h < SIZE)
                                {
                                    if(! getCell(k, h).isShot && ! getCell(k, h).isAttacked)
                                        getCell(k, h).setFill(Color.LIGHTBLUE);
                                    else if(getCell(k, h).isAttacked)
                                    {
                                        getCell(k, h).setFill(Color.TRANSPARENT);
                                    }
                                    else if(getCell(k, h).isShot)
                                        getCell(k, h).setFill(Color.FIREBRICK);
                                }
                            }
                        }
                    });

                });
            }
    }

    public Cell getCell(int x, int y)
    {
        return (Cell) ((HBox) ROWS.getChildren().get(x)).getChildren().get(y);
    }

    public int getLimitation(char whichLimit)       //returns the length of each fighter
    {
        int xLength = 0, yLength = 0;
        if(isSoldier)
        {
            xLength = 1;
            yLength = 1;
        }
        else if(isCavalier)
        {
            xLength = 2;
            yLength = 1;
        }
        else if(isCastle)
        {
            xLength = 2;
            yLength = 2;
        }
        else if(isHeadQuarter)
        {
            xLength = 3;
            yLength = 3;
        }
        switch (whichLimit)
        {
            case 'x' :
                return xLength;
            case 'y' :
                return yLength;
            default:
                return 0;
        }
    }

    public boolean isValidPos(int x, int y, int Xlength, int Ylength)       //checks if the selected position is valid
    {
        int counter = 0;
        for (int i = x; i < Xlength + x; i++)
            for (int j = y; j < Ylength + y; j++)
            {
                if(! (getCell(i, j).isPossessed) )
                {
                    counter ++;
                }
            }
        if(counter == (Xlength * Ylength) - 1)
            return true;
        else
            return false;

    }

    public boolean handleClicks(int x, int y)       //adds a fighter by clicking the board
    {
        Point2D pos = new Point2D(x, y);
        if (!(getCell(x, y).isPossessed))
        {
            if(isSoldier)
            {
                if (player.getNumberOfSoldiers() < 5)
                {
                    Soldier s = new Soldier();
                    player.setSoldiers(s);
                    s.setPos(pos);
                }
                else
                    return false;
            }
            else if(isCavalier)
            {
                if (player.getNumberOfCavaliers() < 3)
                {
                    Cavalier c = new Cavalier();
                    player.setCavaliers(c);
                    c.setPos(pos);
                }
                else
                    return false;
            }
            else if(isCastle)
            {
                if (player.getNumberOfCastles() < 2)
                {
                    Castle cl = new Castle();
                    player.setCastles(cl);
                    cl.setPos(pos);
                }
                else
                    return false;
            }
            else if(isHeadQuarter)
            {
                if (player.getNumberOfHeads() < 1)
                {
                    Head h = new Head();
                    player.setHead(h);
                    h.setPos(pos);
                }
                else
                    return false;
            }
            getCell(x, y).isPossessed = true;
            return true;
        }
        return false;

    }

    public void setDefault()
    {
        setDisable(false);
        setSoldier(false);
        setCavalier(false);
        setCastle(false);
        setHeadQuarter(false);

    }

    public boolean isSoldier() {
        return isSoldier;
    }

    public boolean isCavalier() {
        return isCavalier;
    }

    public boolean isCastle() {
        return isCastle;
    }

    public boolean isHeadQuarter() {
        return isHeadQuarter;
    }

    public void setSoldier(boolean soldier) {
        isSoldier = soldier;
    }

    public void setCavalier(boolean cavalier) {
        isCavalier = cavalier;
    }

    public void setCastle(boolean castle) {
        isCastle = castle;
    }

    public void setHeadQuarter(boolean headQuarter) {
        isHeadQuarter = headQuarter;
    }

    public boolean isPosMode() {
        return PosMode;
    }

    public void setPosMode(boolean PosMode) {
        this.PosMode = PosMode;
    }

    public boolean isAttackMode() {
        return isAttackMode;
    }

    public void setAttackMode(boolean attackMode) {
        isAttackMode = attackMode;
    }

    public Player getRival() {
        return rival;
    }

    private void Time()         //stores the last use of every fighter
    {
        if(isCavalier)
            LastUse1 = counter;
        else if(isCastle)
            LastUse2 = counter;
        else if(isHeadQuarter)
            LastUse3 = counter;
    }

    public boolean RecoveryTime()       //checks if the player can use a fighter according to its recovery time
    {
        if(isSoldier || counter == 0)
        {
            return true;
        }

       if(isCavalier)
       {
           if( ((counter + 1) - LastUse1 >= 2) || LastUse1 == 0)
           {
               return true;
           }
       }
       else if(isCastle)
       {
           if( ((counter + 1) -  LastUse2 >= 3) || LastUse2 == 0)
           {
               return true;
           }
       }
       else if(isHeadQuarter)
       {
           if( ((counter + 1) - LastUse3 >= 4) || LastUse3 == 0)
           {
               return true;
           }
       }
        ConfirmBox.sorry("Give more time!");
        return false;
    }

    public boolean checkTime()      //checks if the player is stuck and can't play the turn
    {
       if( ( (((counter + 1) - LastUse1 < 2) && LastUse1 != 0) || rival.getNumberOfCavaliers()==0 ) && ( (((counter + 1) -  LastUse2 < 3) && LastUse2 != 0) || rival.getNumberOfCastles()==0 ) && ( (((counter + 1) - LastUse3 < 4) && LastUse3 != 0) || rival.getNumberOfHeads() ==0 ))
           return true;
       return false;
    }


}

