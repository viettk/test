create database form_nvk
use form_nvk
go
if OBJECT_ID('sach')is not null
drop table sach
CREATE TABLE sach (
MASACH varchar(255) NOT NULL primary key,
  TENSACH nvarchar(255) NOT NULL,
  THELOAI nvarchar(255) NOT NULL,
  SL int NOT NULL,
  GIA money NOT NULL
)


INSERT INTO sach (MASACH, TENSACH, THELOAI,SL, GIA) VALUES
('sach001', N'hồng lô mộng', N'truyền kì', 10, 9999);


select *from sach