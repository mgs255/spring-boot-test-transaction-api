package org.mgs.TestApps.TransactionsApi;

import java.util.Arrays;
import java.util.List;

public class TransactionFixture {

    public Transaction emptyT;
    public Transaction smallT;
    public Transaction medT;
    public Transaction bigT;
    public Transaction hugeT;
    public Transaction masssiveT;
    public List<Transaction> jumbledList;

    TransactionFixture() {
        emptyT = new Transaction();
        smallT = new Transaction(0.10, "GBP", "03-02-2018", "101012", "202020");
        medT = new Transaction(51.00, "GBP", "05-07-2018", "101013", "202020");
        bigT = new Transaction(10024.99, "GBP", "04-04-2011", "101014", "202020");
        hugeT = new Transaction(200322.2, "GBP", "01-04-2014", "101015", "202020");
        masssiveT = new Transaction(234923202.20, "GBP", "01-12-2015", "101016", "202020");
        Transaction[] jumbled = {hugeT,masssiveT,smallT,bigT,medT};
        jumbledList = Arrays.asList(jumbled);
    }
}
