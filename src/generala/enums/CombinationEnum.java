package generala.enums;

public enum CombinationEnum {
    PAIR(10, "Pair"),
    DOUBLE_PAIR(15, "Double Pair"),
    TRIPLE(20, "Triple"),
    FULL_HOUSE(25, "Full House"),
    STRAIGHT(30, "Straight"),
    FOUR_OF_A_KIND(40, "Four of a kind"),
    GENERALA(50, "GENERALA");

    private final int scoreConst;
    private final String label;


    CombinationEnum(int scoreConst, String label) {
        this.scoreConst = scoreConst;
        this.label = label;
    }

    public int getScoreConst() {
        return scoreConst;
    }

    public String getLabel() {
        return label;
    }
}
