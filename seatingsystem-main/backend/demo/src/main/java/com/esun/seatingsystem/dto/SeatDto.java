package com.esun.seatingsystem.dto;

public class SeatDto {
    private int seatSeq;
    private String floor;
    private String seatNo;
    private String empId;

    public SeatDto(int seatSeq, String floor, String seatNo, String empId) {
        this.seatSeq = seatSeq;
        this.floor = floor;
        this.seatNo = seatNo;
        this.empId = empId;
    }

    public int getSeatSeq() {
        return seatSeq;
    }

    public String getFloor() {
        return floor;
    }

    public String getSeatNo() {
        return seatNo;
    }

    public String getEmpId() {
        return empId;
    }
}
