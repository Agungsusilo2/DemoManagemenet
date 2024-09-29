CREATE TABLE Barang (
                        id VARCHAR(255) NOT NULL PRIMARY KEY,
                        name VARCHAR(100) NOT NULL,
                        category VARCHAR(100) NOT NULL,
                        price INT NOT NULL,
                        stock INT NOT NULL,
                        description TEXT NOT NULL,
                        created_at timestamp default current_timestamp,
                        update_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB;


create table Supplier(
    id varchar(255) not null primary key ,
    name varchar(100) not null ,
    contact varchar(100) not null ,
    address text not null
)ENGINE = Innodb;

create table Transaksi(
    id varchar(255) not null primary key ,
    date long not null ,
    type enum('masuk','keluar'),
    total int not null ,
    id_barang VARCHAR(255) NOT NULL,
    id_supplier varchar(255),
    created_at timestamp default current_timestamp,
    update_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT fk_barang FOREIGN KEY (id_barang) REFERENCES Barang(id),
    CONSTRAINT fk_supplier FOREIGN KEY (id_supplier) REFERENCES Supplier(id))engine = innodb;

create table User(
    id varchar(255) not null primary key ,
    username varchar(100),
    password varchar(100),
    role enum('admin','operator')
);

