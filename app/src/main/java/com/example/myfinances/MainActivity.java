package com.example.myfinances;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText editTextAccountNumber, editTextInitialBalance, editTextCurrentBalance, editTextInterestRate, editTextPaymentAmount;
    private RadioGroup radioGroupAccountType;
    private Button buttonSave, buttonCancel;

    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DatabaseHelper(this);

        editTextAccountNumber = findViewById(R.id.editTextAccountNumber);
        editTextInitialBalance = findViewById(R.id.editTextInitialBalance);
        editTextCurrentBalance = findViewById(R.id.editTextCurrentBalance);
        editTextInterestRate = findViewById(R.id.editTextInterestRate);
        editTextPaymentAmount = findViewById(R.id.editTextPaymentAmount);

        radioGroupAccountType = findViewById(R.id.radioGroupAccountType);

        buttonSave = findViewById(R.id.buttonSave);
        buttonCancel = findViewById(R.id.buttonCancel);

        radioGroupAccountType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                handleAccountTypeChange(checkedId);
            }
        });

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveAccount();
            }
        });

        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearFields();
            }
        });
    }

    private void handleAccountTypeChange(int checkedId) {
        editTextInitialBalance.setVisibility(View.GONE);
        editTextInterestRate.setVisibility(View.GONE);
        editTextPaymentAmount.setVisibility(View.GONE);

        if (checkedId == R.id.radioButtonCD || checkedId == R.id.radioButtonLoan) {
            editTextInitialBalance.setVisibility(View.VISIBLE);
            editTextInterestRate.setVisibility(View.VISIBLE);
        }
        if (checkedId == R.id.radioButtonLoan) {
            editTextPaymentAmount.setVisibility(View.VISIBLE);
        }
    }

    private void saveAccount() {
        String accountType = "";
        int selectedId = radioGroupAccountType.getCheckedRadioButtonId();
        if (selectedId == R.id.radioButtonCD) {
            accountType = "CD";
        } else if (selectedId == R.id.radioButtonLoan) {
            accountType = "Loan";
        } else if (selectedId == R.id.radioButtonChecking) {
            accountType = "Checking";
        }

        String accountNumber = editTextAccountNumber.getText().toString();
        String initialBalance = editTextInitialBalance.getText().toString();
        String currentBalance = editTextCurrentBalance.getText().toString();
        String interestRate = editTextInterestRate.getText().toString();
        String paymentAmount = editTextPaymentAmount.getText().toString();

        // Convert input values to the correct types
        double initialBalanceValue = initialBalance.isEmpty() ? 0 : Double.parseDouble(initialBalance);
        double currentBalanceValue = currentBalance.isEmpty() ? 0 : Double.parseDouble(currentBalance);
        double interestRateValue = interestRate.isEmpty() ? 0 : Double.parseDouble(interestRate);
        double paymentAmountValue = paymentAmount.isEmpty() ? 0 : Double.parseDouble(paymentAmount);

        boolean isInserted = dbHelper.insertData(accountType, accountNumber, initialBalanceValue,
                currentBalanceValue, paymentAmountValue, interestRateValue);

        if (isInserted) {
            Toast.makeText(this, "Account saved successfully", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Failed to save account", Toast.LENGTH_SHORT).show();
        }
        clearFields();
    }

    private void clearFields() {
        editTextAccountNumber.setText("");
        editTextInitialBalance.setText("");
        editTextCurrentBalance.setText("");
        editTextInterestRate.setText("");
        editTextPaymentAmount.setText("");
        radioGroupAccountType.clearCheck();
        editTextInitialBalance.setVisibility(View.GONE);
        editTextInterestRate.setVisibility(View.GONE);
        editTextPaymentAmount.setVisibility(View.GONE);
    }
}
