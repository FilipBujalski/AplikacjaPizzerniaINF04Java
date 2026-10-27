package com.example.pizzeria;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

/********************************************************
 - nazwa klasy: MainActivity
 - opis klasy: Klasa reprezentująca pierwszy ekran (konfigurator), obsługująca wybór parametrów pizzy (rozmiar przez RadioGroup w pionie lub Spinner w poziomie, ciasto, ilość) oraz walidację i przejście do podsumowania.
 - pola:
 - rgSize: grupa wyboru rozmiaru pizzy (RadioGroup - widoczna w pionie)
 - spinnerSize: lista rozwijana rozmiaru pizzy (Spinner - widoczna w poziomie)
 - cbThickCrust: pole wyboru grubego ciasta (CheckBox)
 - etQuantity: pole wprowadzania liczby sztuk (EditText)
 - btnNext: przycisk inicjujący przejście do drugiej aktywności (Button)
 - autor: 00000000000
 ********************************************************/
public class MainActivity extends AppCompatActivity {

    private RadioGroup rgSize;
    private Spinner spinnerSize;
    private CheckBox cbThickCrust;
    private EditText etQuantity;
    private Button btnNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rgSize = findViewById(R.id.rgSize);
        spinnerSize = findViewById(R.id.spinnerSize);
        cbThickCrust = findViewById(R.id.cbThickCrust);
        etQuantity = findViewById(R.id.etQuantity);
        btnNext = findViewById(R.id.btnNext);

        // Jeśli aplikacja uruchomiła się w poziomie (Spinner istnieje), skonfiguruj adapter listy
        if (spinnerSize != null) {
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                    R.array.pizza_sizes, android.R.layout.simple_spinner_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerSize.setAdapter(adapter);
        }

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateAndProceed();
            }
        });
    }

    private void validateAndProceed() {
        String quantityStr = etQuantity.getText().toString().trim();

        if (quantityStr.isEmpty()) {
            Toast.makeText(this, "Podaj liczbę sztuk!", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            int quantity = Integer.parseInt(quantityStr);
            if (quantity <= 0) {
                Toast.makeText(this, "Liczba sztuk musi być większa od 0!", Toast.LENGTH_SHORT).show();
                return;
            }

            // Pobranie rozmiaru i obliczenie ceny bazowej w zależności od orientacji
            String sizeName = "Średnia";
            int basePrice = 35;

            if (spinnerSize != null) {
                // Tryb poziomy (Spinner)
                String selectedText = spinnerSize.getSelectedItem().toString();
                if (selectedText.contains("Mała")) {
                    sizeName = "Mała";
                    basePrice = 25;
                } else if (selectedText.contains("Duża")) {
                    sizeName = "Duża";
                    basePrice = 45;
                }
            } else if (rgSize != null) {
                // Tryb pionowy (RadioGroup)
                int checkedId = rgSize.getCheckedRadioButtonId();
                if (checkedId == R.id.rbSmall) {
                    sizeName = "Mała";
                    basePrice = 25;
                } else if (checkedId == R.id.rbLarge) {
                    sizeName = "Duża";
                    basePrice = 45;
                }
            }

            boolean isThick = cbThickCrust.isChecked();
            if (isThick) {
                basePrice += 5; // Dodatek za grube ciasto
            }

            int totalPrice = basePrice * quantity;

            // Przekazanie danych do drugiej aktywności
            Intent intent = new Intent(MainActivity.this, SummaryActivity.class);
            intent.putExtra("SIZE_NAME", sizeName);
            intent.putExtra("IS_THICK", isThick);
            intent.putExtra("QUANTITY", quantity);
            intent.putExtra("TOTAL_PRICE", totalPrice);
            startActivity(intent);

        } catch (NumberFormatException e) {
            Toast.makeText(this, "Niepoprawna wartość liczby sztuk!", Toast.LENGTH_SHORT).show();
        }
    }
}