package ru.mirea.seyfetdinov.r.n.employeedb;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Database;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.List;

import ru.mirea.seyfetdinov.r.n.employeedb.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        AppDatabase db = App.getInstance().getDatabase();
        EmployeeDao employeeDao = db.employeeDao();

        binding.saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Employee employee = new Employee();
                employee.id = Integer.parseInt(binding.personId.getText().toString());
                employee.name = binding.personName.getText().toString();
                employee.age = Integer.parseInt(binding.personAge.getText().toString());
                employee.superpower = binding.personSuperpower.getText().toString();

                employeeDao.insert(employee);
            }
        });

        binding.getInfoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Employee> employees = employeeDao.getAll();

                if (binding.personId.getText().toString() != null) {
                    int personId = Integer.parseInt(binding.personId.getText().toString());
                    Employee employee = employeeDao.getById(personId);
                    String name = employee.name;
                    String superpower = employee.superpower;
                    Toast.makeText(MainActivity.this, "Имя: " + name + " " + "Суперспособность: " + superpower, Toast.LENGTH_SHORT).show();
                }
                else
                    Toast.makeText(MainActivity.this, "Введите ID супергероя", Toast.LENGTH_SHORT).show();
            }
        });
    }
}

