package cz.muni.fi.pa165.api.facade;

import cz.muni.fi.pa165.api.dto.BookingDTO;
import cz.muni.fi.pa165.api.dto.UserDTO;
import cz.muni.fi.pa165.api.DateRange;
import cz.muni.fi.pa165.api.dto.BookingCreateDTO;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author Petr Valenta
 */
public interface BookingFacade {

    /**
     * Returns a List of all bookings.
     *
     * @return List of bookings
     */
    List<BookingDTO> getAllBookings();
    
    /**
     * Returns booking with given identifier.
     * 
     * @param id given id
     * @return booking with given identifier
     */
    BookingDTO getBooking(Long id);

    /**
     * Returns a List of all bookings in a given range for a given room.
     * @param range
     * @param roomId
     * @return list of bookings
     */
    List<BookingDTO> findBookingsByRange(DateRange range, Long roomId);
    
    /**
     * Returns a List of all bookings in a given range for a given user.
     * @param range
     * @param roomId
     * @return list of bookings
     */
    List<BookingDTO> findBookingsByRangeByUser(DateRange range, Long userId);

    /**
     * 
     * @param bookingCreate 
     */
    void createBooking(BookingCreateDTO bookingCreate);
    
    /**
     * Cancels a booking.
     * @param id of the specified booking
     */
    void cancelBooking(Long id);
    
    /**
     * Updates a booking.
     * @param id of the specified booking
     */
    void updateBooking(Long id);	

    /**
     * Calculates discount for a given booking.
     * @param booking
     * @return price after discount
     */
    public BigDecimal calculateDiscount(BookingDTO booking);
}
