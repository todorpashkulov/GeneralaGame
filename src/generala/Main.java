package generala;

import generala.objects.DiceRoll;
import generala.objects.Generala;
import generala.objects.Player;

public class Main {


    public static void main(String[] args) {
      Generala ganerala = Generala.getInstance();
    /*     GeneralaUtils.loadProperties();
        DiceRoll diceRoll=new DiceRoll(5,6);
        diceRoll.getDieSideDuplicatesMap().put(6,1);
        diceRoll.getDieSideDuplicatesMap().put(5,1);
        diceRoll.getDieSideDuplicatesMap().put(4,1);
        diceRoll.getDieSideDuplicatesMap().put(3,1);
        diceRoll.getDieSideDuplicatesMap().put(2,1);
        diceRoll.getDieSideDuplicatesMap().put(1,0);
        System.out.println(diceRoll.getDiceRollString());
        System.out.println(diceRoll.getDieSideDuplicatesMap());
        Player player=new Player("1");
        player.setDiceRollObj(diceRoll);
       ganerala.countScore(player);*/


            ganerala.playGenerala();








    }
}
