DROP INDEX IF EXISTS idx_bookings_room_number;
DROP INDEX IF EXISTS idx_bookings_room_conflict;
DROP INDEX IF EXISTS idx_bookings_room_status_dates;

ALTER TABLE bookings DROP COLUMN IF EXISTS room_number;
