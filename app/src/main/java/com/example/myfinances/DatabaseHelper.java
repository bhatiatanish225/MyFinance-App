package com.example.myfinances;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "MyFinances.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME = "financial_objects";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_ACCOUNT_TYPE = "account_type";
    private static final String COLUMN_ACCOUNT_NUMBER = "account_number";
    private static final String COLUMN_INITIAL_BALANCE = "initial_balance";
    private static final String COLUMN_CURRENT_BALANCE = "current_balance";
    private static final String COLUMN_PAYMENT_AMOUNT = "payment_amount";
    private static final String COLUMN_INTEREST_RATE = "interest_rate";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_ACCOUNT_TYPE + " TEXT, " +
                COLUMN_ACCOUNT_NUMBER + " TEXT, " +
                COLUMN_INITIAL_BALANCE + " REAL, " +
                COLUMN_CURRENT_BALANCE + " REAL, " +
                COLUMN_PAYMENT_AMOUNT + " REAL, " +
                COLUMN_INTEREST_RATE + " REAL)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean insertData(String accountType, String accountNumber, double initialBalance,
                              double currentBalance, double paymentAmount, double interestRate) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_ACCOUNT_TYPE, accountType);
        contentValues.put(COLUMN_ACCOUNT_NUMBER, accountNumber);
        contentValues.put(COLUMN_INITIAL_BALANCE, initialBalance);
        contentValues.put(COLUMN_CURRENT_BALANCE, currentBalance);
        contentValues.put(COLUMN_PAYMENT_AMOUNT, paymentAmount);
        contentValues.put(COLUMN_INTEREST_RATE, interestRate);

        long result = db.insert(TABLE_NAME, null, contentValues);
        return result != -1;
    }
}
