package ru.mirea.seyfetdinov.r.n.securesharedpreferences;

import androidx.appcompat.app.AppCompatActivity;
import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKeys;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.security.keystore.KeyGenParameterSpec;
import android.text.TextUtils;
import android.util.Base64;
import android.view.View;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;

import ru.mirea.seyfetdinov.r.n.securesharedpreferences.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        KeyGenParameterSpec keyGenParameterSpec = MasterKeys.AES256_GCM_SPEC;
        SharedPreferences secureSharedPreferences;
        try {
            String mainKeyAlias = MasterKeys.getOrCreate(keyGenParameterSpec);
            secureSharedPreferences = EncryptedSharedPreferences.create(
                    "secret_shared_prefs",
                    mainKeyAlias,
                    getBaseContext(),
                    EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                    EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            );
        } catch (GeneralSecurityException | IOException e) {
            throw new RuntimeException(e);
        }

        binding.saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String favoriteActorName = binding.favoriteActor.getText().toString();
                secureSharedPreferences.edit().putString("actor_name", favoriteActorName).apply();
                if (binding.favoriteActor.getText().toString() == "Мэттью Макконахи"){
                    binding.imageActor.setImageResource(R.drawable.favoriteactor);
                }
                else{
                    binding.imageActor.setImageResource(R.drawable.ic_launcher_background);
                }
                if (!TextUtils.isEmpty(favoriteActorName)) {
                    Drawable drawable = binding.imageActor.getDrawable();
                    Bitmap actorPhoto = null;
                    if (drawable instanceof BitmapDrawable) {
                        actorPhoto = ((BitmapDrawable) drawable).getBitmap();
                    } else {
                        actorPhoto = BitmapFactory.decodeResource(getResources(), R.drawable.favoriteactor);
                    }

                    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                    actorPhoto.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
                    byte[] photoBytes = outputStream.toByteArray();
                    String base64Photo = Base64.encodeToString(photoBytes, Base64.DEFAULT);
                    secureSharedPreferences.edit().putString("actor_photo", base64Photo).apply();
                    binding.favoriteActor.getText().clear();
                } else {
                    Toast.makeText(MainActivity.this, "Введите имя актера", Toast.LENGTH_SHORT).show();
                }
            }
        });

        String actorName = secureSharedPreferences.getString("actor_name", null);
        String base64Photo = secureSharedPreferences.getString("actor_photo", null);


        if (base64Photo != null) {
            byte[] photoBytes = Base64.decode(base64Photo, Base64.DEFAULT);
            Bitmap actorPhoto = BitmapFactory.decodeByteArray(photoBytes, 0, photoBytes.length);
            binding.imageActor.setImageBitmap(actorPhoto);
        }

        if (actorName != null) {
            binding.favoriteActor.setText(actorName);
        }
    }
}