package lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.AccountDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.DataBaseUtil;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.exception.InvalidAccountException;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.Account;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.ExpenseType;


public class SQLiteAccountDAO extends DataBaseUtil implements AccountDAO {

    public SQLiteAccountDAO(@Nullable Context context) {
        super(context);
    }

    @Override
    public List<String> getAccountNumbersList() {

        List<String> accountNoList = new ArrayList<String>();

        String sqlString = "SELECT "+ COL_ACCOUNT_NO +" FROM " + TABLE_ACCOUNT;
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        Cursor cursor = sqLiteDatabase.rawQuery(sqlString,null);

        if(cursor.moveToFirst()){
            do {
                String account_no = cursor.getString(0);

                accountNoList.add(account_no);
            } while (cursor.moveToNext());
        }else{

        }

        cursor.close();
        return accountNoList;
    }

    @Override
    public List<Account> getAccountsList() {

        List<Account> accountList = new ArrayList<Account>();

        String sqlString = "SELECT * FROM " + TABLE_ACCOUNT;
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        Cursor cursor = sqLiteDatabase.rawQuery(sqlString,null);

        if(cursor.moveToFirst()){
            do {
                String account_no = cursor.getString(0);
                String holder = cursor.getString(1);
                String bank = cursor.getString(2);
                Double balance = cursor.getDouble(3);

                Account account = new Account(account_no,bank,holder,balance);
                accountList.add(account);
            } while (cursor.moveToNext());
        }else{

        }

        cursor.close();
        return accountList;
    }

    @Override
    public Account getAccount(String accountNo) throws InvalidAccountException {

        String sqlString = "SELECT * FROM " + TABLE_ACCOUNT + " WHERE " + COL_ACCOUNT_NO + " = ?";
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        String[] selectionArgs = {accountNo};
        Cursor cursor = sqLiteDatabase.rawQuery(sqlString,selectionArgs);


        if(cursor.moveToFirst()){
                String account_no = cursor.getString(0);
                String holder = cursor.getString(1);
                String bank = cursor.getString(2);
                Double balance = cursor.getDouble(3);

                Account account = new Account(account_no,bank,holder,balance);
                cursor.close();
                sqLiteDatabase.close();
                return account;
        }else{
            cursor.close();
            sqLiteDatabase.close();
            return null;
        }


    }

    @Override
    public boolean addAccount(Account account) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_ACCOUNT_NO,account.getAccountNo());
        contentValues.put(COL_BANK,account.getBankName());
        contentValues.put(COL_HOLDER,account.getAccountHolderName());
        contentValues.put(COL_BALANCE,account.getBalance());

        long insert = database.insert(TABLE_ACCOUNT,null,contentValues);
        if(insert == -1){
            return false;
        }else{
            return true;
        }
    }

    @Override
    public void removeAccount(String accountNo) throws InvalidAccountException {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        String sqlStatement = "DELETE FROM " + TABLE_ACCOUNT + " WHERE account_no = " + accountNo +";";
        sqLiteDatabase.execSQL(sqlStatement);
    }

    @Override
    public void updateBalance(String accountNo, ExpenseType expenseType, double amount) throws InvalidAccountException {
        Account account = this.getAccount(accountNo);
        double newBalance;
        if(expenseType == ExpenseType.EXPENSE){
            newBalance = account.getBalance() - amount;
        }else{
            newBalance = account.getBalance() + amount;
        }

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        String sqlStatement = "UPDATE account SET balance = " + newBalance + " WHERE account_no = '" + accountNo + "';";
        sqLiteDatabase.execSQL(sqlStatement);
    }
}
