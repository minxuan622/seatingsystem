package com.esun.seatingsystem.service;

import com.esun.seatingsystem.dto.SeatDto;
import com.esun.seatingsystem.repository.SeatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class SeatingService {

    private final SeatingRepository repository;

    @Autowired
    public SeatingService(SeatingRepository repository) {
        this.repository = repository;
    }

    public List<SeatDto> getAllSeats() {
        return repository.getAllSeats();
    }

    public void assignSeat(String empId, int seatSeq) {
        repository.assignSeat(empId, seatSeq);
    }

    public void clearSeat(int seatSeq) {
        repository.clearSeat(seatSeq);
    }
}
