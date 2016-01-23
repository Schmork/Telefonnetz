package Telefonnetz;

import java.util.Random;

// random number generator using singleton pattern
public class RNG {
    private static Random instance;

    public static Random getInstance() {
        if (instance == null) {
            instance = new Random();
        }
        return instance;
    }

    public static double nextDouble() {
        return getInstance().nextDouble();
    }
}
