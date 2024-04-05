package assignment6;

import assignment6.Seat.SeatType;

import java.util.ArrayList;
import java.util.List;

public class MovieTheater {

    private int printDelay;
    private SalesLogs log;

    List<List<Seat>> theater;

    /**
     * Constructs a MovieTheater, where there are a set number of rows per seat type.
     *
     * @param rumbleNum the number of rows with rumble seats.
     * @param comfortNum the number of rows with comfort seats.
     * @param standardNum the number of rows with standard seats.
     */
    public MovieTheater(int rumbleNum, int comfortNum, int standardNum){
        printDelay = 10;
        log = new SalesLogs();
        List<List<Seat>> theater= new ArrayList<>(rumbleNum + comfortNum + standardNum);
        int typeCounter = 0; int createCounter = 0;
        for(int i = 0; i<rumbleNum + comfortNum + standardNum; i++){
            theater.add(new ArrayList<>());
        }
        for(int i = 0; i<theater.size();i++){
            if(typeCounter == 0 && createCounter < rumbleNum){
                for(int j = 0; j<6;j++){
                    theater.get(i).add(j, new Seat(SeatType.values()[typeCounter], i, Seat.SeatLetter.values()[j]));
                }
                createCounter++;
            }
            else if(typeCounter == 1 && createCounter < comfortNum){
                for(int j = 0; j<6;j++){
                    theater.get(i).add(j, new Seat(SeatType.values()[typeCounter], i, Seat.SeatLetter.values()[j]));
                }
                createCounter++;
            }
            else if(typeCounter == 2 && createCounter < standardNum){
                for(int j = 0; j<6;j++){
                    theater.get(i).add(j, new Seat(SeatType.values()[typeCounter], i, Seat.SeatLetter.values()[j]));
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

    /**
     * Returns the next available seat not yet reserved for a given seat type.
     *
     * @param seatType the type of seat (RUMBLE, COMFORT, STANDARD).
     * @return the next available seat or null if the theater is full.
     */
    public Seat getNextAvailableSeat(SeatType seatType) {
        // TODO: Implement this method.
        for(int i = 0; i<theater.size();i++){
            if(theater.get(i).get(0).getSeatType() == seatType){

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
    public Ticket printTicket(String boothId, Seat seat, int customer) {
        // TODO: Implement this method.
        //if the theater has a free seat

        return null;
    }
    
    /**
     * Lists all seats sold for the movie in the order of reservation.
     *
     * @return list of seats sold.
     */
    public List<Seat> getSeatLog() {
        // TODO: Implement this method.
        for(Seat s : log.seatLog){
            System.out.println(s);
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
        for(Ticket t : log.ticketLog){
            System.out.println(t);
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
