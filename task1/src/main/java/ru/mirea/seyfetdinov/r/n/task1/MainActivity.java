package ru.mirea.seyfetdinov.r.n.task1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import ru.mirea.seyfetdinov.r.n.task1.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        SharedPreferences SharedPref = getSharedPreferences("mirea_settings", Context.MODE_PRIVATE);

        binding.saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = SharedPref.edit();

                editor.putString("group_number", binding.personGroupNumber.getText().toString());
                editor.putString("list_number", binding.personListNumber.getText().toString());
                editor.putString("favorite_movie", binding.personFavoriteMovie.getText().toString());

                editor.apply();
            }
        });

        String groupNumber = SharedPref.getString("group_number", "XXXX-XX-XX");
        String listNumber = SharedPref.getString("list_number", "00");
        String favoriteMovie = SharedPref.getString("favorite_movie", "Волк с Уолл-стрит");

        binding.personGroupNumber.setText(groupNumber);
        binding.personListNumber.setText(listNumber);
        binding.personFavoriteMovie.setText(favoriteMovie);
    }
}