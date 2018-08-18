package org.mgs.TestApps.TransactionsApi;

import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransactionService {

    /**
     * Return a comparator defaults to value
     *
     * @param sortField
     * @param sortOrder
     * @return
     */
    private Comparator<Transaction> getComparator(TransactionSortField sortField, SortOrder sortOrder) {

        Comparator comparator;

        switch(sortField) {
            case date:
                comparator = Comparator.comparing(Transaction::getDate);
                break;
            case value:
            default:
                comparator = Comparator.comparing(Transaction::getValue);
                break;
        }

        if (sortOrder == SortOrder.descend) {
            comparator = comparator.reversed();
        }

        return comparator;
    }

    /**
     * Sort transactions - supports sorting arbitrary lists of transactions.
     * @param transactions
     * @param sortParam
     * @param sortOrder
     * @return the sorted list of transactions, according to the specified parameters.
     * @throws IllegalArgumentException
     */
    List<Transaction> sortTransactions(List<Transaction> transactions, TransactionSortField sortParam, SortOrder sortOrder)
            throws IllegalArgumentException {

        return (List<Transaction>) transactions.stream()
                .sorted(getComparator(sortParam, sortOrder))
                .collect(Collectors.toList());
    }
}
