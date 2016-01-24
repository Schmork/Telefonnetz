package Telefonnetz;

import java.util.Comparator;

/**
 * Created by Spzi on 1/24/2016.
 */
public class TelVerbindungComparator implements Comparator<TelVerbindung> {
    @Override
    public int compare(TelVerbindung t1, TelVerbindung t2) {
        return Integer.compare(t1.getWeight(), t1.getWeight());
    }
}
