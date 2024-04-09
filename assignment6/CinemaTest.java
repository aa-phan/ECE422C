package assignment6;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class CinemaTest {

    // Write your JUnit tests here

    //test that a cinema is properly generated and functions normally
    @Test
    public void testA(){
        Map< String , Seat.SeatType[] > booths = new HashMap< String , Seat.SeatType[] >();
        booths.put ( " TO1 " , new Seat.SeatType[] { Seat.SeatType. COMFORT , Seat.SeatType. COMFORT , Seat.SeatType. COMFORT });
        booths.put ( " TO3 " , new Seat.SeatType[] { Seat.SeatType. COMFORT , Seat.SeatType. STANDARD , Seat.SeatType. STANDARD });
        booths.put ( " TO2 " , new Seat.SeatType[] { Seat.SeatType. RUMBLE , Seat.SeatType. COMFORT ,
                Seat.SeatType. STANDARD , Seat.SeatType. STANDARD });
        booths.put ( " TO4 " , new Seat.SeatType[] { Seat.SeatType. STANDARD , Seat.SeatType. STANDARD , Seat.SeatType. STANDARD });
        booths.put ( " TO5 " , new Seat.SeatType[] { Seat.SeatType. COMFORT , Seat.SeatType. COMFORT , Seat.SeatType. COMFORT });
        MovieTheater test = new MovieTheater(3,3,3);
        Cinema client = new Cinema(booths, test);
        List<Thread> threadList = client.simulate();
        for (Thread t : threadList) {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        assertFalse(test.getSeatLog().isEmpty());
        assertFalse(test.getTransactionLog().isEmpty());
        assertTrue(interleaving(test.getTransactionLog()));
        assertFalse(hasDuplicates(test.getSeatLog()));
    }
    //case where a cinema with no spots is created
    @Test
    public void testB(){
        Map< String , Seat.SeatType[] > booths = new HashMap< String , Seat.SeatType[] >();
        booths.put ( " TO1 " , new Seat.SeatType[] { Seat.SeatType. COMFORT , Seat.SeatType. COMFORT , Seat.SeatType. COMFORT });
        MovieTheater test = new MovieTheater(0,0,0);
        Cinema client = new Cinema(booths, test);
        List<Thread> threadList = client.simulate();
        for (Thread t : threadList) {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        assertTrue(test.getSeatLog().isEmpty());
        assertTrue(test.getTransactionLog().isEmpty());
        

    }
    //super large case with multiple customers, ensure functionality under high stress
    @Test
    public void testC(){
        Map < String , Seat.SeatType[] > booths = new HashMap< String , Seat.SeatType[] >();
        booths.put("TO1", new Seat.SeatType[] { Seat.SeatType.COMFORT, Seat.SeatType.COMFORT, Seat.SeatType.COMFORT });
        booths.put("TO2", new Seat.SeatType[] { Seat.SeatType.RUMBLE, Seat.SeatType.COMFORT, Seat.SeatType.STANDARD, Seat.SeatType.STANDARD });
        booths.put("TO3", new Seat.SeatType[] { Seat.SeatType.COMFORT, Seat.SeatType.STANDARD, Seat.SeatType.STANDARD });
        booths.put("TO4", new Seat.SeatType[] { Seat.SeatType.STANDARD, Seat.SeatType.STANDARD, Seat.SeatType.STANDARD });
        booths.put("TO5", new Seat.SeatType[] { Seat.SeatType.COMFORT, Seat.SeatType.COMFORT, Seat.SeatType.COMFORT });
        booths.put("TO6", new Seat.SeatType[] { Seat.SeatType.RUMBLE, Seat.SeatType.RUMBLE, Seat.SeatType.RUMBLE });
        booths.put("TO7", new Seat.SeatType[] { Seat.SeatType.STANDARD, Seat.SeatType.COMFORT, Seat.SeatType.RUMBLE });
        booths.put("TO8", new Seat.SeatType[] { Seat.SeatType.COMFORT, Seat.SeatType.COMFORT, Seat.SeatType.STANDARD });
        booths.put("TO9", new Seat.SeatType[] { Seat.SeatType.COMFORT, Seat.SeatType.STANDARD, Seat.SeatType.COMFORT });
        booths.put("TO10", new Seat.SeatType[] { Seat.SeatType.STANDARD, Seat.SeatType.COMFORT, Seat.SeatType.STANDARD });
        booths.put("TO11", new Seat.SeatType[] { Seat.SeatType.COMFORT, Seat.SeatType.RUMBLE, Seat.SeatType.STANDARD });
        booths.put("TO12", new Seat.SeatType[] { Seat.SeatType.RUMBLE, Seat.SeatType.COMFORT, Seat.SeatType.RUMBLE });
        booths.put("TO13", new Seat.SeatType[] { Seat.SeatType.COMFORT, Seat.SeatType.COMFORT, Seat.SeatType.RUMBLE });
        booths.put("TO14", new Seat.SeatType[] { Seat.SeatType.COMFORT, Seat.SeatType.STANDARD, Seat.SeatType.RUMBLE });
        booths.put("TO15", new Seat.SeatType[] { Seat.SeatType.RUMBLE, Seat.SeatType.RUMBLE, Seat.SeatType.STANDARD });
        MovieTheater test = new MovieTheater(3,3,3);
        Cinema client = new Cinema(booths, test);
        List<Thread> threadList = client.simulate();
        for (Thread t : threadList) {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        assertFalse(test.getSeatLog().isEmpty());
        assertFalse(test.getTransactionLog().isEmpty());
        assertTrue(interleaving(test.getTransactionLog()));
        assertFalse(hasDuplicates(test.getSeatLog()));

    }
    //case where not enough spots exist
    @Test
    public void testD(){
        Map < String , Seat.SeatType[] > booths = new HashMap< String , Seat.SeatType[] >();
        booths.put("TO1", new Seat.SeatType[] { Seat.SeatType.COMFORT, Seat.SeatType.COMFORT, Seat.SeatType.COMFORT });
        booths.put("TO2", new Seat.SeatType[] { Seat.SeatType.RUMBLE, Seat.SeatType.COMFORT, Seat.SeatType.STANDARD, Seat.SeatType.STANDARD });
        booths.put("TO3", new Seat.SeatType[] { Seat.SeatType.COMFORT, Seat.SeatType.STANDARD, Seat.SeatType.STANDARD });
        MovieTheater test = new MovieTheater(1,0,1);
        Cinema client = new Cinema(booths, test);
        List<Thread> threadList = client.simulate();
        for (Thread t : threadList) {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        assertEquals(test.theaterCapacity(), 7);
        assertFalse(test.getSeatLog().isEmpty());
        assertFalse(test.getTransactionLog().isEmpty());
        assertTrue(interleaving(test.getTransactionLog()));
        assertFalse(hasDuplicates(test.getSeatLog()));

    }
    //case where every customer is downgraded to a lower seat
    @Test
    public void testE(){
        Map < String , Seat.SeatType[] > booths = new HashMap< String , Seat.SeatType[] >();
        booths.put("TO1", new Seat.SeatType[] { Seat.SeatType.RUMBLE, Seat.SeatType.RUMBLE, Seat.SeatType.RUMBLE });
        booths.put("TO2", new Seat.SeatType[] { Seat.SeatType.RUMBLE, Seat.SeatType.RUMBLE, Seat.SeatType.RUMBLE, Seat.SeatType.RUMBLE });
        booths.put("TO3", new Seat.SeatType[] { Seat.SeatType.RUMBLE, Seat.SeatType.RUMBLE, Seat.SeatType.RUMBLE });
        MovieTheater test = new MovieTheater(0,0,2);
        Cinema client = new Cinema(booths, test);
        List<Thread> threadList = client.simulate();
        for (Thread t : threadList) {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        assertFalse(test.getSeatLog().isEmpty());
        assertFalse(test.getTransactionLog().isEmpty());
        assertTrue(interleaving(test.getTransactionLog()));
        assertFalse(hasDuplicates(test.getSeatLog()));
    }
    @Test(timeout = 120000)
    
    public void givenSeatClassIsFullWhenGetNextAvailableSeatThenLowerSeatClass() throws InterruptedException {
        final Seat.SeatType[] seatPreferences = new Seat.SeatType[6];
        Arrays.fill(seatPreferences, Seat.SeatType.RUMBLE);
        final Seat.SeatType[] seatPreferences2 = Arrays.copyOf(seatPreferences, 6);

        Map<String, Seat.SeatType[]> booths = new HashMap<String, Seat.SeatType[]>() {{
            put("TB1", seatPreferences);
            put("TB2", seatPreferences2);
        }};

        MovieTheater movieTheater = new MovieTheater(2, 2, 10);
        Cinema cinema = new Cinema(booths, movieTheater);
        joinAllThreads(cinema.simulate());

        Seat next = movieTheater.getNextAvailableSeat(Seat.SeatType.RUMBLE);
        assertNotNull(next);
        assertEquals("3A (COMFORT)", next.toString());
    }
    private static void joinAllThreads(List<Thread> threads)
            throws InterruptedException {
        for (Thread t : threads) {
            t.join();
        }
    }

    public static boolean interleaving(List<Ticket> ticketLog){
        //boolean seatInter = true;
        boolean ticketInter = true;
        int count = 0;
        /*for(int i = 0; i<seatLog.size(); i++){
            if(i == seatLog.size()-1) break;
            int curLetter = seatLog.get(i).getLetter().getIntValue();
            int nextLetter = seatLog.get(i+1).getLetter().getIntValue();
            int curRow = seatLog.get(i).getRow();
            int nextRow = seatLog.get(i+1).getRow();
            if( curLetter < nextLetter && curRow == nextRow){
                count++;
            }

            if(count == 5){ //a whole row was assigned in order, means its not interleaving
                seatInter = false;
            }
            if(curRow != nextRow){ //seat assignment changed row, indicates interleaving
                count = 0;
            }
        }*/
        //count = 0;
        for(int i = 0; i<ticketLog.size(); i++){
            if(i==ticketLog.size()-1) break;
            String curID = ticketLog.get(i).getBoothId();
            String nextID = ticketLog.get(i+1).getBoothId();
            if(curID.compareTo(nextID) == 0){
                count++;
            }
            if(count == 5){
                ticketInter = false;
            }
            if(curID.compareTo(nextID) != 0){
                count = 0;
            }
        }
        return ticketInter;
    }
    public static boolean hasDuplicates(List<Seat> list) {
        return list.stream()
                .distinct() // Removes duplicates
                .count() != list.size(); // If counts are different, then duplicates exist
    }
}
