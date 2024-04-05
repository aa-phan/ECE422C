package assignment6;

import assignment6.Seat.SeatType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Cinema {

    private MovieTheater theater;
    private Map<String, SeatType[]> TicketBooth;
    /**
     * Constructor to initilize the simulation based on starter parameters. 
     * 
     * @param booths maps ticket booth id to seat type preferences of customers in line.
     * @param movieTheater the theater for which tickets are sold.
     */
    public Cinema(Map<String, SeatType[]> booths, MovieTheater movieTheater) {
        // TODO: Implement this constructor
        theater = movieTheater;
        TicketBooth = booths;
    }

    /**
     * Starts the ticket office simulation by creating (and starting) threads
     * for each ticket booth to sell tickets for the given movie.
     *
     * @return list of threads used in the simulation,
     *   should have as many threads as there are ticket booths.
     */
    public List<Thread> simulate() {
        // TODO: Implement this method.
        List<Thread> threadList = new ArrayList<>();
        for(Map.Entry<String, SeatType[]> entry : TicketBooth.entrySet()){
            Thread t = new Thread(new TicketProcess(entry.getKey(), entry.getValue()));
            t.start();
            threadList.add(t);
        }
        return threadList;
    }
    public class TicketProcess implements Runnable {
        private String boothName;
        private SeatType[] customerList;
        public TicketProcess(String key, SeatType[] value) {
            boothName = key;
            customerList = value;
        }
        @Override

        public void run(){
            /*Repeat until there are no tickets left to be sold or there are no customers :
                    If there is a seat of the requested type :
                        Mark the seat and print out the receipt .
                    Else if there are no more seats of the requested type :
                        Offer a downgraded seat by checking if one is available .
                    If one is available :
                        Mark the seat and print out the receipt .*/
            while(customerList.length == 0 || theater.getSeatLog().size() == theater.theater.size()*6){

            }
        }
    }
    public static void main(String[] args) {
        // For your testing purposes. We will not call this method.
        //MovieTheater test = new MovieTheater(2,1,3);
        Map < String , SeatType [] > booths = new HashMap< String , SeatType [] >();
        booths.put ( " TO1 " , new SeatType [] { SeatType . COMFORT , SeatType . COMFORT , SeatType . COMFORT });
        booths.put ( " TO3 " , new SeatType [] { SeatType . COMFORT , SeatType . STANDARD , SeatType . STANDARD });
        booths.put ( " TO2 " , new SeatType [] { SeatType . RUMBLE , SeatType . COMFORT ,
                SeatType . STANDARD , SeatType . STANDARD });
        booths.put ( " TO4 " , new SeatType [] { SeatType . STANDARD , SeatType . STANDARD , SeatType . STANDARD });
        booths.put ( " TO5 " , new SeatType [] { SeatType . COMFORT , SeatType . COMFORT , SeatType . COMFORT });
        Cinema client = new Cinema(booths, new MovieTheater(1,1,1));
    }
}
