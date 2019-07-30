package generala;

public enum CombinationEnum {
    PAIR(10),
    DOUBLE_PAIR(15),
    TRIPLE(20),
    FULL_HOUSE(25),
    STRAIGHT(30),
    FOUR_OF_A_KIND(40),
    GENERALA(50);

    private final int scoreConst;


    CombinationEnum(int scoreConst) {
        this.scoreConst = scoreConst;
    }

    public int getScoreConst() {
        return scoreConst;
    }

}
