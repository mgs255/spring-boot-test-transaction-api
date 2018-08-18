package org.mgs.TestApps.TransactionsApi;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import org.springframework.validation.Errors;


public class Transaction {

    public final static String SUPPORTED_DATE_FORMAT = "dd-MM-yyyy";


    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = SUPPORTED_DATE_FORMAT)
    @NotNull(message = "{transaction.date.invalid-format}")
    private Date date;

    @NotNull
    @PositiveOrZero(message = "{transaction.value.negative}")
    private BigDecimal value;

    @NotBlank
    private String currency;
    @NotBlank
    private String sourceAccount;
    @NotBlank
    private String destAccount;

    private final static DateFormat dateFormatValidator;

    static {
        dateFormatValidator = new SimpleDateFormat(SUPPORTED_DATE_FORMAT);
        dateFormatValidator.setLenient(false);
    }

    public Transaction() {}

    public Transaction(double value, String currency, String date, String sourceAccount, String destAccount) {
        setValue(value);
        setDate(date);
        this.currency = currency;
        this.sourceAccount = sourceAccount;
        this.destAccount = destAccount;
    }

    public Date getDate() {
        return date;
    }

    public BigDecimal getValue() {
        return value;
    }

    public String getCurrency() {
        return currency;
    }

    public String getSourceAccount() {
        return sourceAccount;
    }

    public String getDestAccount() {
        return destAccount;
    }

    public void setValue(double value) {
        this.value = new BigDecimal(value).setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public void setDate(String date) {

        try {
            this.date = dateFormatValidator.parse(date);
        } catch (ParseException ex) {
            this.date = null;
        }
    }

    public void setSourceAccount(String sourceAccount) {
        this.sourceAccount = sourceAccount;
    }

    public void setDestAccount(String destAccount) {
        this.destAccount = destAccount;
    }
}
