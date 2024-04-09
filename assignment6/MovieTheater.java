package assignment6;

import assignment6.Seat.SeatType;

import java.util.*;

public class MovieTheater {

    private int printDelay;
    private SalesLogs log;
    private int capacity;
    private int maxCapacity;
    List<Map<Seat, Boolean>> theater;

    /**
     * Constructs a MovieTheater, where there are a set number of rows per seat type.
     *
     * @param rumbleNum the number of rows with rumble seats.
     * @param comfortNum the number of rows with comfort seats.
     * @param standardNum the number of rows with standard seats.
     */
    public MovieTheater(int rumbleNum, int comfortNum, int standardNum){
        printDelay = 100;
        log = new SalesLogs();
        capacity = 0;
        maxCapacity = (rumbleNum + comfortNum + standardNum)*6;
        List<Map<Seat, Boolean>> theater= new ArrayList<>(rumbleNum + comfortNum + standardNum);
        int typeCounter = 0; int createCounter = 0;
        for(int i = 0; i<rumbleNum + comfortNum + standardNum; i++){
            theater.add(new LinkedHashMap<>());
        }
        for(int i = 0; i<theater.size();i++){
            if(typeCounter == 0 && createCounter < rumbleNum){
                for(int j = 0; j<6;j++){
                    theater.get(i).put(new Seat(SeatType.values()[typeCounter], i + 1, Seat.SeatLetter.values()[j]), false);
                }
                createCounter++;
            }
            else if(typeCounter == 1 && createCounter < comfortNum){
                for(int j = 0; j<6;j++){
                    theater.get(i).put(new Seat(SeatType.values()[typeCounter], i + 1, Seat.SeatLetter.values()[j]), false);
                }
                createCounter++;
            }
            else if(typeCounter == 2 && createCounter < standardNum){
                for(int j = 0; j<6;j++){
                    theater.get(i).put(new Seat(SeatType.values()[typeCounter], i + 1, Seat.SeatLetter.values()[j]), false);
                }
                createCounter++;
            }
            else{
                createCounter = 0; typeCounter++; i--;
            }
        }
        this.theater = theater;

        // TODO: Finish implementing this constructor.
    }
    public int theaterCapacity(){
        return capacity;
    }
    public int getMaxCapacity(){
        return maxCapacity;
    }
    /**
     * Returns the next available seat not yet reserved for a given seat type.
     *
     * @param seatType the type of seat (RUMBLE, COMFORT, STANDARD).
     * @return the next available seat or null if the theater is full.
     */
    public Seat getNextAvailableSeat(SeatType seatType) {
        // TODO: Implement this method.

        for(Map<Seat, Boolean> row : theater){
            synchronized(theater){
                for(Map.Entry<Seat,Boolean> entry : row.entrySet()){
                    Seat seat = entry.getKey();
                    Boolean occupancy = entry.getValue();
                    if(seat.getSeatType().equals(seatType) && !occupancy){
                        //System.out.println("Matching seat found, assigning");

                        row.put(seat, true); //unsure if i need to change the occupancy here, leaving this for now
                        log.addSeat(seat);
                        capacity++;

                        return seat; //returns the seat that's free, can use later for key lookup
                    }
                }

            }

        }
        return null;
    }


    /**
     * Prints a ticket to the console for the customer after they reserve a seat.
     *
     * @param boothId id of the ticket booth.
     * @param seat a particular seat in the theater.
     * @return a movie ticket or null if a ticket booth failed to reserve the seat.
     */
    public Ticket printTicket(String boothId, Seat seat, int customer) throws InterruptedException {
        // TODO: Implement this method.
        //if the theater has a free seat
            /*System.out.println("printing ticket");
            return new Ticket(boothId, seat, customer);*/
            for(Map<Seat, Boolean> row: theater){
                synchronized(theater){
                    if(row.containsKey(seat)){
                        //row.put(sea, true);
                        Ticket t = new Ticket(boothId, seat, customer);
                        log.addTicket(t);
                        System.out.println(t.toString());
                        Thread.sleep(printDelay);
                        //System.out.println("printing ticket");
                        return t;
                    }

                }
            }
        return null;
    }
    
    /**
     * Lists all seats sold for the movie in the order of reservation.
     *
     * @return list of seats sold.
     */
    public List<Seat> getSeatLog() {
        // TODO: Implement this method.
        //System.out.println("Total tickets " + log.seatLog.size());
        for(Seat s : log.getSeatLog()){
            System.out.println(s.toString());
            //System.out.print("t");
        }
        return log.getSeatLog();
    }

    /**
     * Lists all tickets sold for the movie in order of printing.
     *
     * @return list of tickets sold.
     */
    public List<Ticket> getTransactionLog() {
        // TODO: Implement this method.
        for(Ticket t : log.getTicketLog()){
            System.out.println(t.toString());
        }
        return log.getTicketLog();
    }

    static class SalesLogs {

        private ArrayList<Seat> seatLog;
        private ArrayList<Ticket> ticketLog;

        private SalesLogs() {
            seatLog = new ArrayList<Seat>();
            ticketLog = new ArrayList<Ticket>();
        }

        public List<Seat> getSeatLog() {
            return (List<Seat>)(seatLog.clone());
        }

        public List<Ticket> getTicketLog() {
            return (List<Ticket>)(ticketLog.clone());
        }

        public void addSeat(Seat s) {
            seatLog.add(s);
        }

        public void addTicket(Ticket t) {
            ticketLog.add(t);
        }
    }
}
