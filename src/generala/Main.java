package generala;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Main {


    public static void main(String[] args) {
        GaneralaUtils ganerala = GaneralaUtils.getInstance();
        int playerCount = 0;
        int roundCount = 0;
        try (InputStream input = Main.class.getClassLoader().getResourceAsStream("generala.properties")) {
            Properties properties = new Properties();
            properties.load(input);
            playerCount = Integer.valueOf(properties.getProperty("players"));
            roundCount = Integer.valueOf(properties.getProperty("rounds"));


        } catch (IOException e) {
            e.printStackTrace();
        }

        ganerala.playGenerala(playerCount, roundCount);


    }
}
