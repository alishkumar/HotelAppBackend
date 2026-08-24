CREATE INDEX idx_bookings_check_in ON bookings(check_in);
CREATE INDEX idx_bookings_check_out ON bookings(check_out);
CREATE INDEX idx_bookings_room_number ON bookings(room_number);
CREATE INDEX idx_bookings_status ON bookings(status);
CREATE INDEX idx_bookings_phone ON bookings(phone);
CREATE INDEX idx_bookings_room_conflict ON bookings(room_number, status, check_in, check_out);
