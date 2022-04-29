package lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.DataBaseUtil;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.TransactionDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.Account;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.ExpenseType;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.Transaction;

public class SQLiteTransactionDAO extends DataBaseUtil implements TransactionDAO {

    public SQLiteTransactionDAO(@Nullable Context context) {
        super(context);
    }

    @Override
    public boolean logTransaction(Date date, String accountNo, ExpenseType expenseType, double amount) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_ACCOUNT_NO,accountNo );
        if(expenseType == ExpenseType.INCOME){
            contentValues.put(COL_TYPE,1);
        }else{
            contentValues.put(COL_TYPE,0);
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        String dateString = calendar.get(Calendar.YEAR) + "-" + (calendar.get(Calendar.MONTH)) + "-" + calendar.get(Calendar.DATE);

        contentValues.put(COL_TRANSACTION_DATE, dateString);
        contentValues.put(COL_AMOUNT,amount);

        long insert = database.insert(TABLE_TRANSACTION,null,contentValues);
        if(insert == -1){
            return false;
        }else{
            return true;
        }
    }

    @Override
    public List<Transaction> getAllTransactionLogs() {

        List<Transaction> transactionList = new ArrayList<Transaction>();

        String sqlString = "SELECT * FROM `" + TABLE_TRANSACTION + "`";
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        Cursor cursor = sqLiteDatabase.rawQuery(sqlString,null);

        if(cursor.moveToFirst()){
            do {
                String account_no = cursor.getString(1);
                ExpenseType expenseType = cursor.getInt(2) == 0 ? ExpenseType.EXPENSE : ExpenseType.INCOME;
                String[] dateString =  cursor.getString(3).split("-");
                Calendar calendar = Calendar.getInstance();
                calendar.set(Integer.parseInt(dateString[0]),Integer.parseInt(dateString[1]),Integer.parseInt(dateString[2]));
                Date date = calendar.getTime();
                Double amount = cursor.getDouble(4);

                Transaction transaction = new Transaction(date,account_no,expenseType,amount);
                transactionList.add(transaction);
            } while (cursor.moveToNext());
        }else{

        }

        cursor.close();
        return transactionList;
    }

    @Override
    public List<Transaction> getPaginatedTransactionLogs(int limit) {
        List<Transaction> transactionList = new ArrayList<Transaction>();

        String sqlString = "SELECT * FROM `" + TABLE_TRANSACTION + "`";
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        Cursor cursor = sqLiteDatabase.rawQuery(sqlString,null);
        int i = 0;
        if(cursor.moveToFirst()){
            do {
                String account_no = cursor.getString(1);
                ExpenseType expenseType = cursor.getInt(2) == 0 ? ExpenseType.EXPENSE : ExpenseType.INCOME;
                String[] dateString =  cursor.getString(3).split("-");
                Calendar calendar = Calendar.getInstance();
                calendar.set(Integer.parseInt(dateString[0]),Integer.parseInt(dateString[1]),Integer.parseInt(dateString[2]));
                Date date = calendar.getTime();
                Double amount = cursor.getDouble(4);

                Transaction transaction = new Transaction(date,account_no,expenseType,amount);
                transactionList.add(transaction);
                i++;
                if(!cursor.moveToNext()){
                    break;
                }
            } while (i < limit);
        }else{

        }

        cursor.close();
        return transactionList;
    }
}
