package com.example.pizzeria;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

/********************************************************
 * nazwa klasy: SummaryActivity
 * opis klasy: Klasa reprezentująca drugi ekran (podsumowanie zamówienia), odbierająca parametry z konfiguratora oraz prezentująca szczegóły i łączny koszt.
 * pola:
 *   - tvSummaryDetails: etykieta wyświetlająca rozmiar, ciasto oraz sztuki (TextView)
 *   - tvTotalPrice: zielona etykieta z całkowitą kwotą w PLN (TextView)
 *   - btnBack: przycisk powrotu do poprzedniej aktywności (Button)
 * autor: 00000000000
 ********************************************************/
public class SummaryActivity extends AppCompatActivity {

    private TextView tvSummaryDetails, tvTotalPrice;
    private Button btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);

        tvSummaryDetails = findViewById(R.id.tvSummaryDetails);
        tvTotalPrice = findViewById(R.id.tvTotalPrice);
        btnBack = findViewById(R.id.btnBack);

        // Odbieranie danych z intencji
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String sizeName = extras.getString("SIZE_NAME", "Średnia");
            boolean isThick = extras.getBoolean("IS_THICK", false);
            int quantity = extras.getInt("QUANTITY", 1);
            int totalPrice = extras.getInt("TOTAL_PRICE", 0);

            // Ustawienie tekstu podsumowania
            String crustType = isThick ? "Grube" : "Cienkie";
            String detailsText = "• Rozmiar: " + sizeName + "\n" +
                    "• Ciasto: " + crustType + "\n" +
                    "• Sztuk: " + quantity;
            tvSummaryDetails.setText(detailsText);

            // Ustawienie sformatowanego zielonego wyniku
            String priceText = String.format(Locale.GERMANY, "Razem: %.2f PLN", (double) totalPrice);
            tvTotalPrice.setText(priceText);
        }

        // Obsługa przycisku powrotu
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Zamknięcie aktywności i powrót do konfiguratora
            }
        });
    }
}