
/*database = DK_Agile nhé*/
/*database = DK_Agile nhé*/
/*database = DK_Agile nhé*/
/*database = DK_Agile nhé*/
/*database = DK_Agile nhé*/

if OBJECT_ID('nv') is not null
drop table nv
go
create table nv
(
	manv	nvarchar(50),
	hoten	nvarchar(50),
	tuoi	int,
	gioitinh	nvarchar(50),
	taikhoan	nvarchar(50),
	password	nvarchar(50),
	chucvu		nvarchar(50),
	calam	nvarchar(50),
	constraint pk_nv primary key(taikhoan)
)
delete nv

if OBJECT_ID('aadmin') is not null
drop table aadmin
go
create table aadmin
(
	hoten	nvarchar(50),
	password nvarchar(50)
	constraint pk_aadmin primary key(hoten)
)
insert into aadmin values ('quanli','123')

if OBJECT_ID('nhanvien') is not null
drop table nhanvien
go
create table nhanvien
(
	taikhoan	nvarchar(50),
	password nvarchar(50),
	constraint pk_nhanvien primary key(taikhoan),
	constraint fk_nv_nhanvien foreign key(taikhoan)
	references nv
)

if OBJECT_ID('nhanvienkho') is not null
drop table nhanvienkho
go
create table nhanvienkho
(
	taikhoan	nvarchar(50),
	password nvarchar(50),
	constraint pk_nhanvienkho primary key(taikhoan),
	constraint fk_nv_nhanvienkho foreign key(taikhoan)
	references nv
)
select * from nhanvienkho
select * from nv
select* from nhanvienkho


delete nv
select * from nv
select * from nhanvien