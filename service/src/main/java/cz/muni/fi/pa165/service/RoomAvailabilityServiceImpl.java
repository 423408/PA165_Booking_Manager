package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.dao.BookingDao;
import cz.muni.fi.pa165.entity.Booking;
import cz.muni.fi.pa165.entity.Room;
import cz.muni.fi.pa165.service.exceptions.BookingManagerDataAccessException;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Implementation of hotel rooms availability using BookingDao and Java 8
 * Streams API.
 * 
 * @author Miloslav Zezulka
 */
@Service
public class RoomAvailabilityServiceImpl implements RoomAvailabilityService {

    @Autowired
    BookingDao bookingDao;

    @Override
    public List<Booking> getBookingsInRange(DateRange range, Room room) {
        if (range == null) {
            throw new IllegalArgumentException("Range cannot be null.");
        }
        if (room == null) {
            throw new IllegalArgumentException("Room cannot be null.");
        }
        try {
            return bookingDao.findByRoom(room).stream()
                    .filter(booking -> isBookingInsideDateRange(booking, range))
                    .collect(Collectors.toList());
        } catch (IllegalArgumentException | ConstraintViolationException e) {
            throw new BookingManagerDataAccessException("DAO exception was thrown:", e);
        }
    }

    private static boolean isBookingInsideDateRange(Booking booking, DateRange range) {
        return !booking.getFrom().isBefore(range.getFromDate())
                && !booking.getTo().isAfter(range.getToDate());
    }

}