package com.esun.seatingsystem.repository;

import com.esun.seatingsystem.dto.SeatDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SeatingRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public SeatingRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    //呼叫 Stored Procedure：指派座位
    public void assignSeat(String empId, int seatSeq) {
        jdbcTemplate.update("CALL assign_seat(?, ?)", empId, seatSeq);
    }

    //呼叫 Stored Procedure：清除座位
    public void clearSeat(int seatSeq) {
        jdbcTemplate.update("CALL clear_seat(?)", seatSeq);
    }

    //查詢所有座位 + 員工
    public List<SeatDto> getAllSeats() {
        String sql = """
                SELECT s.FLOOR_SEAT_SEQ AS seatSeq,
                       s.FLOOR_NO AS floor,
                       s.SEAT_NO AS seatNo,
                       e.EMP_ID AS empId
                FROM SeatingChart s
                LEFT JOIN Employee e ON s.FLOOR_SEAT_SEQ = e.FLOOR_SEAT_SEQ
                ORDER BY s.FLOOR_NO, s.SEAT_NO
                """;

        return jdbcTemplate.query(sql, (rs, rowNum) -> new SeatDto(
                rs.getInt("seatSeq"),
                rs.getString("floor"),
                rs.getString("seatNo"),
                rs.getString("empId")
        ));
    }
}
