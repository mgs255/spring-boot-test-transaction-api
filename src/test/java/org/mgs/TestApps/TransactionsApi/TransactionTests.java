package org.mgs.TestApps.TransactionsApi;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;


public class TransactionTests {

    static private Calendar cal;

    @BeforeClass
    static public void init() {
        cal = Calendar.getInstance();
    }

    private int getDateComp(Date date, int part) {
        cal.setTime(date);
        return cal.get(part);
    }
    
    private BigDecimal getRoundedValue(double v) {
        return new BigDecimal(v).setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    private void assertEqualsValue(BigDecimal expected, BigDecimal actual) {
        assertEquals(expected.toPlainString(), actual.toPlainString());
    }

    @Test
    public void testGettersAndSetters() {
        Transaction t = new Transaction();
        t.setValue(1.00);
        assertEqualsValue(getRoundedValue(1.00), t.getValue());
        t.setDate("18-10-2018");
        assertEquals(18, getDateComp(t.getDate(), Calendar.DAY_OF_MONTH));
        assertEquals(Calendar.OCTOBER, getDateComp(t.getDate(), Calendar.MONTH));
        assertEquals(2018, getDateComp(t.getDate(), Calendar.YEAR));

        t.setCurrency("POUNDS");
        assertEquals("POUNDS", t.getCurrency());
        t.setSourceAccount("aaaaaaaaa");
        assertEquals("aaaaaaaaa", t.getSourceAccount());
        t.setDestAccount("bbbbbbbb");
        assertEquals("bbbbbbbb", t.getDestAccount());

        Transaction t2 = new Transaction(1.00, "POUNDS", "18-10-2018", "aaaaaaaaa","bbbbbbbb");
        assertEquals(t2.getValue(),t.getValue());
        assertEquals(t2.getDate(),t.getDate());
        assertEquals(t2.getCurrency(),t.getCurrency());
        assertEquals(t2.getSourceAccount(),t.getSourceAccount());
        assertEquals(t2.getDestAccount(),t.getDestAccount());
    }

    @Test
    public void testInvalidDate() {
        Transaction t = new Transaction();
        t.setDate("30-02-2018");
        assertNull(t.getDate());
    }


    @Test
    public void testRoundedUp() {
        Transaction t = new Transaction();
        t.setValue(10.006);
        assertEqualsValue(getRoundedValue(10.01), t.getValue());
    }

    @Test
    public void testRoundedDown() {
        Transaction t = new Transaction();
        t.setValue(10.014);
        assertEqualsValue(getRoundedValue(10.01), t.getValue());
    }

}
