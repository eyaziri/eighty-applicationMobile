package com.example.spectacle;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.HashMap;
import java.util.Map;

public class payement extends AppCompatActivity {

    private LinearLayout cardBank, cardPaypal, cardD17;
    private Button buttonConfirm;
    private String selectedPaymentMethod = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_payement);

        // Associer les vues
        cardBank = findViewById(R.id.cardBank);
        cardPaypal = findViewById(R.id.cardPaypal);
        cardD17 = findViewById(R.id.cardD17);
        buttonConfirm = findViewById(R.id.buttonConfirm);

        // Ajustement pour les barres système
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.activity_payment), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Gérer les clics sur les options
        cardBank.setOnClickListener(v -> selectPaymentMethod("Carte Bancaire"));
        cardPaypal.setOnClickListener(v -> selectPaymentMethod("PayPal"));
        cardD17.setOnClickListener(v -> selectPaymentMethod("Carte D17"));

        // Bouton confirmer
        buttonConfirm.setOnClickListener(v -> {
            if (selectedPaymentMethod == null) {
                Toast.makeText(payement.this, "Veuillez sélectionner un mode de paiement.", Toast.LENGTH_SHORT).show();
            } else {
                processPayment(selectedPaymentMethod);
            }
        });
    }

    private void selectPaymentMethod(String method) {
        selectedPaymentMethod = method;

        // Réinitialiser le fond de tous les layouts
        cardBank.setBackgroundResource(R.drawable.payment_option_white_shadow);
        cardPaypal.setBackgroundResource(R.drawable.payment_option_white_shadow);
        cardD17.setBackgroundResource(R.drawable.payment_option_white_shadow);

        // Appliquer un fond différent pour montrer la sélection
        switch (method) {
            case "Carte Bancaire":
                cardBank.setBackgroundResource(R.drawable.payment_option_selected);
                break;
            case "PayPal":
                cardPaypal.setBackgroundResource(R.drawable.payment_option_selected);
                break;
            case "Carte D17":
                cardD17.setBackgroundResource(R.drawable.payment_option_selected);
                break;
        }
    }

    private void processPayment(String paymentMethod) {
        switch (paymentMethod) {
            case "Carte Bancaire":
                redirectToPaymentPage("Carte Bancaire");
                break;
            case "PayPal":
                redirectToPaymentPage("PayPal");
                break;
            case "Carte D17":
                redirectToPaymentPage("Carte D17");
                break;
            default:
                Toast.makeText(payement.this, "Erreur de méthode de paiement.", Toast.LENGTH_SHORT).show();
        }
    }

    private void redirectToPaymentPage(String paymentMethod) {
        String paymentUrl = getPaymentApiUrl(paymentMethod);

        if (paymentUrl == null) {
            Toast.makeText(payement.this, "Méthode de paiement non prise en charge", Toast.LENGTH_SHORT).show();
            return;
        }

        // Ouvrir la page de paiement dans un navigateur
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(paymentUrl));
        startActivity(browserIntent);
    }

    private String getPaymentApiUrl(String method) {
        switch (method) {
            case "Carte Bancaire":
                return "https://secure.stripe.com/checkout/session"; // URL réelle de Stripe Checkout
            case "PayPal":
                return "https://www.paypal.com/checkoutnow"; // URL de checkout PayPal
            case "Carte D17":
                return "https://paiement.d17.com/api/checkout"; // Remplace avec la vraie URL de D17 si elle existe
            default:
                return null;
        }
    }
}
