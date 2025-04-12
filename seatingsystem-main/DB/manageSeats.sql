delimiter //

create procedure assign_seat(
	in employeeID varchar(5),
    in seat_seq int
)

begin
	declare count_people int;
    
    -- 查看座位是否被占用
    select count(*) into count_people from Employee
    where FLOOR_SEAT_SEQ = seat_seq and EMP_ID <> employeeID;
    
    if count_people > 0 then
		-- 已被占用，因此發送錯誤訊息
        signal sqlstate '45000'
        set message_text = 'This seat is already taken.';
	else
		-- 清除該員工原本座位
        update Employee
        set FLOOR_SEAT_SEQ = null
        where EMP_ID = employeeID;
        
        -- 將座位分配給他
        update Employee
        set FLOOR_SEAT_SEQ = seat_seq
        where EMP_ID = employeeID;
	end if;
end //

delimiter ;