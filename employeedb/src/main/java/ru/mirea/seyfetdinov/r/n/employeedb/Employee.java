package ru.mirea.seyfetdinov.r.n.employeedb;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Entity;
import androidx.room.Insert;
import androidx.room.PrimaryKey;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Entity
public class Employee {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public String name;
    public int age;
    public String superpower;
}

