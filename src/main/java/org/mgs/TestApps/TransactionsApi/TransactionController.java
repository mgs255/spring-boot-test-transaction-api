package org.mgs.TestApps.TransactionsApi;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.Validation;
import javax.validation.ValidatorFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

@RestController
@RequestMapping(value = "/api/transactions")
@Api(value = "Transactions")
public class TransactionController {

    @Autowired
    TransactionService transactionsService;

    @Autowired
    LocalValidatorFactoryBean validatorFactoryBean;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        // Add our custom collection validator as the validation doesn't get invoked against
        // collections without it.
        binder.addValidators(new CollectionValidator(validatorFactoryBean));
    }

    /*
     * sortTransactions
     *
     * Sort transactions and return them
     *
     */
    @RequestMapping(value = "/sort", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Interface to sort a list of transactions supplied in request body",
        notes = "Use this interface to register an appliance.")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Request completed successfully", responseContainer = "List", response = Transaction.class),
        @ApiResponse(code = 400, message = "Problem occured processing request")})
    public ResponseEntity<List<Transaction>> sortTransactions(
        // We are relying on the JSR
        @Valid @RequestBody List<Transaction> transactions,
        @RequestParam(defaultValue = "value", name = "sortField", required = false) TransactionSortField sortField,
        @RequestParam(defaultValue = "descend", name = "sortOrder", required = false) SortOrder sortOrder) throws Exception {

        List<Transaction> sortedTransactions = transactionsService.sortTransactions(transactions, sortField, sortOrder);

        return new ResponseEntity<List<Transaction>>(sortedTransactions, HttpStatus.OK);
    }

}

