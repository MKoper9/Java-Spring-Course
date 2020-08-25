drop table if exist tasks;
create table tasks(
    id int primary key auto_increment,
    description varchar(100) not null,
    done bit,
)