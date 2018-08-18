package org.mgs.TestApps.TransactionsApi;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

import java.util.LinkedList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class TransactionServiceTests {

    @InjectMocks
    @Spy
    TransactionService transactionsService;

    private TransactionFixture fixture;

    @Before
    public void init() {
        fixture = new TransactionFixture();
    }

    @Test
    public void testSortEmptyList() {
        List<Transaction> sorted = transactionsService.sortTransactions(new LinkedList<>(), TransactionSortField.value, SortOrder.ascend);
        assertTrue(sorted.isEmpty());
    }

    @Test
    public void testAmountAsc() {
        List<Transaction> sorted = transactionsService.sortTransactions(fixture.jumbledList, TransactionSortField.value, SortOrder.ascend);
        assertEquals(fixture.smallT, sorted.get(0));
        assertEquals(fixture.masssiveT, sorted.get(4));
    }

    @Test
    public void testAmountDesc() {
        List<Transaction> sorted = transactionsService.sortTransactions(fixture.jumbledList, TransactionSortField.value, SortOrder.descend);
        assertEquals(fixture.smallT, sorted.get(4));
        assertEquals(fixture.masssiveT, sorted.get(0));
    }

    @Test
    public void testDateAsc() {
        List<Transaction> sorted = transactionsService.sortTransactions(fixture.jumbledList, TransactionSortField.date, SortOrder.ascend);
        assertEquals(fixture.bigT, sorted.get(0));
        assertEquals(fixture.medT, sorted.get(4));
    }

    @Test
    public void testDateDesc() {
        List<Transaction> sorted = transactionsService.sortTransactions(fixture.jumbledList, TransactionSortField.date, SortOrder.descend);
        assertEquals(fixture.bigT, sorted.get(4));
        assertEquals(fixture.medT, sorted.get(0));
    }
}
