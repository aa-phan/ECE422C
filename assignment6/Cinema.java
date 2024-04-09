package assignment6;

import assignment6.Seat.SeatType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Cinema {

    private final MovieTheater theater;
    private Map<String, SeatType[]> TicketBooth;
    private int customerIDCounter;
    private Lock lock;
    //private Lock lock;
    //final Object obj = new Object();
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
        customerIDCounter = 1;
        lock = new ReentrantLock();
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
        /*for (Thread t : threadList) {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("///////////////");
        theater.getSeatLog();
        System.out.println("///////////////");
        theater.getTransactionLog();*/
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

        public void run() {
            for (Seat.SeatType seatType : customerList) {
                synchronized (boothName.intern()) {
                    /*if(theater.theaterCapacity() == theater.getMaxCapacity()){
                        System.out.println("theater is full, no more tickets");
                        return;
                    }*/
                    Seat seat = theater.getNextAvailableSeat(seatType);
                    if (seat != null) {
                        int customerID = generateCustomerID(); // Generate unique customer ID
                        try {
                            theater.printTicket(boothName, seat, customerID);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    } else {
                        return;
                        /*Seat.SeatType downgradedSeatType = downgradeSeatType(seatType);
                        if (downgradedSeatType != null) {
                            Seat downgradedSeat = theater.getNextAvailableSeat(downgradedSeatType);
                            if (downgradedSeat != null) {
                                int customerID = generateCustomerID(); // Generate unique customer ID
                                try {
                                    theater.printTicket(boothName, downgradedSeat, customerID);
                                } catch (InterruptedException e) {
                                    throw new RuntimeException(e);
                                }
                            }
                            else{
                                downgradedSeatType = downgradeSeatType(downgradedSeatType);
                                downgradedSeat = theater.getNextAvailableSeat(downgradedSeatType);
                                if (downgradedSeat != null) {
                                    int customerID = generateCustomerID(); // Generate unique customer ID
                                    try {
                                        theater.printTicket(boothName, downgradedSeat, customerID);
                                    } catch (InterruptedException e) {
                                        throw new RuntimeException(e);
                                    }
                                }
                        }
                        }*/
                    }
                }
            }
        }

        private synchronized int generateCustomerID() {
            lock.lock();
            try{
                return customerIDCounter++; // Return current counter value and then increment
            }finally{
                lock.unlock();
            }
        }

    }
    public static void main(String[] args) {
        // For your testing purposes. We will not call this method.
        /*MovieTheater test = new MovieTheater(2,1,3);
        if(test.getNextAvailableSeat(SeatType.COMFORT) != null){
            System.out.println("Working");
        }
        System.out.println(test.printTicket("T01", test.getNextAvailableSeat(SeatType.COMFORT), 3).toString());*/
        Map < String , SeatType [] > booths = new HashMap< String , SeatType [] >();
        booths.put ( " TO1 " , new SeatType [] { SeatType . COMFORT , SeatType . COMFORT , SeatType . COMFORT });
        booths.put ( " TO3 " , new SeatType [] { SeatType . COMFORT , SeatType . STANDARD , SeatType . STANDARD });
        booths.put ( " TO2 " , new SeatType [] { SeatType . RUMBLE , SeatType . COMFORT ,
                SeatType . STANDARD , SeatType . STANDARD });
        booths.put ( " TO4 " , new SeatType [] { SeatType . STANDARD , SeatType . STANDARD , SeatType . STANDARD });
        booths.put ( " TO5 " , new SeatType [] { SeatType . COMFORT , SeatType . COMFORT , SeatType . COMFORT });
        /*Map<String, SeatType[]> testCases = new HashMap<>();

        // Test Case 1
        testCases.put("TO1", new SeatType[] { SeatType.COMFORT, SeatType.COMFORT, SeatType.COMFORT });

        // Test Case 2
        testCases.put("TO2", new SeatType[] { SeatType.RUMBLE, SeatType.COMFORT, SeatType.STANDARD, SeatType.STANDARD });

        // Test Case 3
        testCases.put("TO3", new SeatType[] { SeatType.COMFORT, SeatType.STANDARD, SeatType.STANDARD });

        // Test Case 4
        testCases.put("TO4", new SeatType[] { SeatType.STANDARD, SeatType.STANDARD, SeatType.STANDARD });

        // Test Case 5
        testCases.put("TO5", new SeatType[] { SeatType.COMFORT, SeatType.COMFORT, SeatType.COMFORT });

        // Test Case 6
        testCases.put("TO6", new SeatType[] { SeatType.RUMBLE, SeatType.RUMBLE, SeatType.RUMBLE });

        // Test Case 7
        testCases.put("TO7", new SeatType[] { SeatType.STANDARD, SeatType.COMFORT, SeatType.RUMBLE });

        // Test Case 8
        testCases.put("TO8", new SeatType[] { SeatType.COMFORT, SeatType.COMFORT, SeatType.STANDARD });

        // Test Case 9
        testCases.put("TO9", new SeatType[] { SeatType.COMFORT, SeatType.STANDARD, SeatType.COMFORT });

        // Test Case 10
        testCases.put("TO10", new SeatType[] { SeatType.STANDARD, SeatType.COMFORT, SeatType.STANDARD });

        // Test Case 11
        testCases.put("TO11", new SeatType[] { SeatType.COMFORT, SeatType.RUMBLE, SeatType.STANDARD });

        // Test Case 12
        testCases.put("TO12", new SeatType[] { SeatType.RUMBLE, SeatType.COMFORT, SeatType.RUMBLE });

        // Test Case 13
        testCases.put("TO13", new SeatType[] { SeatType.COMFORT, SeatType.COMFORT, SeatType.RUMBLE });

        // Test Case 14
        testCases.put("TO14", new SeatType[] { SeatType.COMFORT, SeatType.STANDARD, SeatType.RUMBLE });

        // Test Case 15
        testCases.put("TO15", new SeatType[] { SeatType.RUMBLE, SeatType.RUMBLE, SeatType.STANDARD });*/
        MovieTheater test = new MovieTheater(1,1,1);
        Cinema client = new Cinema(booths, test);
        List<Thread> threadList = client.simulate();
        for (Thread t : threadList) {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("///////////////");
        //test.getSeatLog();
        System.out.println("///////////////");
        //test.getTransactionLog();

    }
}
