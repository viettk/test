create database KH
use KH

go 
if OBJECT_ID('khach_hang') is not null
drop table khach_hang
go 
create table khach_hang(
	makh nvarchar(10) primary key ,
	ten_kh nvarchar(20),
	sđt nvarchar(10),
	diachi nvarchar(50) ,
	ngay_sinh datetime,
	gioi_tinh nvarchar(10),
)
