


/*
 * Copyright 2015 Department of Computer Science and Engineering, University of Moratuwa.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *                  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package lk.ac.mrt.cse.dbs.simpleexpensemanager;
import android.content.Context;

import androidx.test.core.app.ApplicationProvider;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

import java.util.Date;
import java.util.List;

import lk.ac.mrt.cse.dbs.simpleexpensemanager.control.ExpenseManager;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.control.SqliteExpenseManager;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.ExpenseType;


/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest {

    private static ExpenseManager expenseManager;

    @Before
    public void setUp(){

        Context context = ApplicationProvider.getApplicationContext();
        expenseManager = new SqliteExpenseManager(context);
    }

    @Test
    public void testAddAccount(){
        expenseManager.addAccount("00001","BOC","S.M. Sirisena", 1500.00);
        List<String> accountNumbersList = expenseManager.getAccountNumbersList();
        assertTrue(accountNumbersList.contains("00001"));



    }

    @Test
    public void testAddTransaction(){
        int i = expenseManager.getTransactionLogs().size();
        expenseManager.getTransactionsDAO().logTransaction(new Date(), "00001", ExpenseType.EXPENSE,500.00);
        int j = expenseManager.getTransactionLogs().size();
        assertTrue(i +1 == j);
    }
}