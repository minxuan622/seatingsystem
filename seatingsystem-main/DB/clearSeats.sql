delimiter //

create procedure clear_seat(
    in seat_seq int
)

begin
	update Employee
	set FLOOR_SEAT_SEQ = null
	where FLOOR_SEAT_SEQ = seat_seq;
end //

delimiter ;