package generala;

import generala.objects.Player;

import java.util.*;

final class GaneralaUtils {
    private static GaneralaUtils instance;

    private GaneralaUtils() {
    }

    public static GaneralaUtils getInstance() {

        if (instance == null) {
            instance = new GaneralaUtils();
        }

        return instance;
    }

    private String generateDiceRoll(){
        final int NUMBER_OF_DIE=5;
        StringBuilder diceRoll = new StringBuilder(NUMBER_OF_DIE);
        Random random = new Random();
        for (int i = 0; i < NUMBER_OF_DIE; i++) {
            if (i > 0) {
                diceRoll.append(",");
            }
            diceRoll.append(random.nextInt(6) + 1);

        }


        return diceRoll.toString();
    }

    private Map<CombinationEnum, Integer> countCombos(Player player) {
        Map<CombinationEnum, Integer> comboMap = new LinkedHashMap<>();
        Set<Integer> searchedValues = removeDuplicates(player.getDiceRoll());
        List<String> roll = splitRollToList(player);

        for (Integer num : searchedValues) {
            int duplicateCount = findDuplicates(roll, num);

            if (duplicateCount >= 2 && duplicateCount <= 5) {
                switch (duplicateCount) {


                    case 2:
                        if (comboMap.containsKey(CombinationEnum.PAIR)) {
                            comboMap.put(CombinationEnum.DOUBLE_PAIR, comboMap.get(CombinationEnum.PAIR) + num * 2);
                        } else if (comboMap.containsKey(CombinationEnum.TRIPLE)) {
                            comboMap.put(CombinationEnum.FULL_HOUSE,
                                    comboMap.get(CombinationEnum.TRIPLE) + num * 2);
                        }
                        comboMap.put(CombinationEnum.PAIR, num * 2);

                        break;
                    case 3:
                        if (comboMap.containsKey(CombinationEnum.PAIR)) {
                            comboMap.put(CombinationEnum.FULL_HOUSE,
                                    comboMap.get(CombinationEnum.PAIR) + num * 3);
                        }
                        comboMap.put(CombinationEnum.TRIPLE, num * 3);
                        break;
                    case 4:
                        comboMap.put(CombinationEnum.FOUR_OF_A_KIND, num * 4);
                        break;
                    case 5:
                        comboMap.put(CombinationEnum.GENERALA, num * 5);
                        break;
                    default:
                        break;
                }
            }
            //for finding straight
            if (findStraight(roll)) {
                comboMap.put(CombinationEnum.STRAIGHT, findTypeOfStraight(Integer.valueOf(roll.get(0))));
            }


        }


        return comboMap;
    }

    private CombinationEnum addScore(Player player) {
        Map<CombinationEnum, Integer> comboMap = countCombos(player);
        int finalComboConstScore = 0;
        int finalComboValue = 0;
        CombinationEnum combo = null;

        for (Map.Entry<CombinationEnum, Integer> entry : comboMap.entrySet()) {

            if (!player.getRolledCombinations().contains(entry.getKey())
                    && finalComboConstScore < entry.getValue()) {
                finalComboConstScore = entry.getKey().getScoreConst();
                finalComboValue = entry.getValue();
                combo = entry.getKey();
            }
        }
        if (finalComboConstScore != 0) {
            player.setScore(player.getScore() +
                    finalComboConstScore + finalComboValue);
            player.getRolledCombinations().add(combo);
            return combo;
        }
        return null;
    }

    public void playGenerala(int playerCount, int roundCount) {

        List<Player> players = new ArrayList<>();
        CombinationEnum currentCombo = null;

        for (int i = 0; i < playerCount; i++) {
            players.add(new Player("Player " + (i + 1)));
        }

        for (int i = 0; i < roundCount; i++) {
            int oldScore;
            System.out.println("<-------------------------------------------------->");
            System.out.println();
            System.out.println(">>>Round " + (i + 1));
            for (Player player : players) {
                System.out.println();
                System.out.println(">" + player.getName());
                System.out.println("Current Score: " + player.getScore());
                oldScore = player.getScore();
                player.setDiceRoll(getInstance().generateDiceRoll());
                System.out.print("Dice roll: " + player.getDiceRoll());
                currentCombo = getInstance().addScore(player);

                if (currentCombo == null) {
                    System.out.println("-> Nothing");
                } else {
                    System.out.println("-> " + currentCombo.toString() + " (" + (player.getScore() - oldScore) + ")");
                }
                System.out.println("New Score: " + player.getScore());
                if (currentCombo != null && currentCombo.equals(CombinationEnum.GENERALA)) {
                    System.out.println("GENERALA SCORED " + player.getName() + " WINS !!!");
                    System.out.println("GAME IS ENDING!!!!");
                    printScore(players, player.getName());
                    return;
                }
            }
        }
        printScore(players, null);
    }

    private void printScore(List<Player> players, String winnerName) {
        Iterator<Player> playerIterator = players.listIterator();
        Player currentPlayer = null;
        int lastPlayerScore = 0;
        int positionIndex = 0;

        System.out.println();
        System.out.println("SCORES!!!!");
        if (winnerName != null) {
            while (playerIterator.hasNext()) {
                currentPlayer = playerIterator.next();

                if (currentPlayer.getName().equals(winnerName)) {
                    System.out.println("GENERALA " + winnerName + " GENERALA");
                    System.out.println("SCORE: " + currentPlayer.getScore());
                    playerIterator.remove();
                    break;
                }
            }
            players.sort(Comparator.comparing(Player::getScore).reversed());
            players.add(0, currentPlayer);
        } else {
            players.sort(Comparator.comparing(Player::getScore).reversed());
        }
        for (int i = 0; i < players.size(); i++) {
            currentPlayer = players.get(i);

            if (currentPlayer.getScore() != lastPlayerScore) {
                positionIndex++;
            }

            System.out.println((positionIndex) + ". " + currentPlayer.getName() + " -> Score: " + currentPlayer.getScore());
            lastPlayerScore = currentPlayer.getScore();
        }

    }

    private boolean findStraight(List<String> roll) {
        int counter = 0;

        for (int i = 0; i < roll.size() - 1; i++) {
            if (Integer.valueOf(roll.get(i)).equals(Integer.valueOf(roll.get(i + 1)) - 1)) {
                counter++;
            }
        }
        return counter == (roll.size() - 1);
    }

    private int findTypeOfStraight(int firstNum) {
        if (firstNum == 1) {
            return 15;
        } else
            return 20;
    }

    private Set<Integer> removeDuplicates(String s) {
        Set<Integer> integerSet = new TreeSet<>();
        String[] strings = s.split(",");
        for (int i = 0; i < strings.length; i++) {
            integerSet.add(Integer.valueOf(strings[i]));
        }


        return integerSet;

    }

    private int findDuplicates(List<String> roll, Integer searchedNum) {
        int duplicateCount = 0;
        for (int i = 0; i < roll.size(); i++) {
            if (searchedNum.intValue() == Integer.valueOf(roll.get(i))) {
                duplicateCount++;
            }
        }
        return duplicateCount;
    }

    private List<String> splitRollToList(Player player) {
        List<String> roll = new ArrayList<>(Arrays.asList(player.getDiceRoll().split(",")));
        Collections.sort(roll);
        return roll;
    }
}
