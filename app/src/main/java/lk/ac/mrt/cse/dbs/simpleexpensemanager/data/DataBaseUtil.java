package lk.ac.mrt.cse.dbs.simpleexpensemanager.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

public class DataBaseUtil extends SQLiteOpenHelper {

    public static final String TABLE_ACCOUNT = "account";
    public static final String COL_ACCOUNT_NO = TABLE_ACCOUNT + "_no";
    public static final String COL_HOLDER = "holder";
    public static final String COL_BANK = "bank";
    public static final String COL_BALANCE = "balance";
    public static final String COL_TYPE = "type";
    public static final String TABLE_TRANSACTION = "transactions";
    public static final String COL_TRANSACTION_DATE = TABLE_TRANSACTION + "_date";
    public static final String COL_AMOUNT = "amount";
    public static final String COL_TRANSACTION_ID = "" +
            "transaction_id";

    public DataBaseUtil(@Nullable Context context) {
        super(context, "190116U.db",null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createAccountTable = "CREATE TABLE `" + TABLE_ACCOUNT + "`(" +
                COL_ACCOUNT_NO + " TEXT," +
                COL_HOLDER + " TEXT," +
                COL_BANK + " TEXT," +
                COL_BALANCE + " REAL);";

        String createTransactionTable = "CREATE TABLE `" + TABLE_TRANSACTION + "`(" +
                COL_TRANSACTION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT ," +
                COL_ACCOUNT_NO + " TEXT," +
                COL_TYPE + " INTEGER," +
                COL_TRANSACTION_DATE + " TEXT," +
                COL_AMOUNT + " REAL, " +
                "CONSTRAINT fk_accounts FOREIGN KEY (" + COL_ACCOUNT_NO + ") REFERENCES " + TABLE_ACCOUNT + "(" + COL_ACCOUNT_NO +") ON DELETE CASCADE);";

        String insert1 = "INSERT INTO " + TABLE_ACCOUNT + " VALUES('12345A','Anakin Skywalker','Yoda Bank',10000.0);";
        String insert2 = "INSERT INTO " + TABLE_ACCOUNT + " VALUES('78945Z','Obi-Wan Kenobi','Clone BC',80000.0);";

        sqLiteDatabase.execSQL(createAccountTable);
        sqLiteDatabase.execSQL(createTransactionTable);
        sqLiteDatabase.execSQL(insert1);
        sqLiteDatabase.execSQL(insert2);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
