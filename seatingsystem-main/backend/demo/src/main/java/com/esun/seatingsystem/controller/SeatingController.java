package com.esun.seatingsystem.controller;

import com.esun.seatingsystem.dto.SeatDto;
import com.esun.seatingsystem.service.SeatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/seats")
@CrossOrigin(origins = "http://localhost:8801")  // Vue 前端的 port
public class SeatingController {

    private final SeatingService seatingService;

    @Autowired
    public SeatingController(SeatingService seatingService) {
        this.seatingService = seatingService;
    }

    //查詢所有座位
    @GetMapping
    public List<SeatDto> getAllSeats() {
        return seatingService.getAllSeats();
    }

    //指派座位（呼叫 Stored Procedure）
    @PostMapping("/assign")
    public ResponseEntity<String> assignSeat(
            @RequestParam String empId,
            @RequestParam int seatSeq) {
        System.out.println("Received empId: " + empId); // 添加日誌以便調試
        seatingService.assignSeat(empId, seatSeq);
        return ResponseEntity.ok("Seat assigned successfully.");
    }

    //清除座位（呼叫 Stored Procedure）
    @PostMapping("/clear")
    public ResponseEntity<String> clearSeat(@RequestParam int seatSeq) {
        seatingService.clearSeat(seatSeq);
        return ResponseEntity.ok("Seat cleared successfully.");
    }
}
