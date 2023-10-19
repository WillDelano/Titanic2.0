package edu.core.billingmanagement;

/**
 * Represents payment information associated with a user or transaction.
 * This class provides attributes to store credit/debit card details
 * like card number, expiry details, card type, etc., and offers methods
 * to get and set these attributes.
 *
 *
 * @author Vincent Dinh
 * @version 1.0
 */
public class PaymentInfo {

    /**
     * Enumeration representing the type of payment - CREDIT_CARD or DEBIT_CARD.
     */
    public enum PaymentType {
        CREDIT_CARD,
        DEBIT_CARD
    }

    private PaymentType paymentType;
    private String cardholderName;
    private String cardNumber;
    private int expiryMonth;
    private int expiryYear;
    private String cardType;

    /**
     * Constructs a new PaymentInfo object with the given details.
     *
     * @param paymentType The type of payment - CREDIT_CARD or DEBIT_CARD.
     * @param cardholderName The name of the card holder.
     * @param cardNumber The card number
     * @param expiryMonth The expiration month of the card.
     * @param expiryYear The expiration year of the card.
     * @param cardType The type of the card (e.g., Visa, MasterCard, etc.).
     */
    public PaymentInfo(PaymentType paymentType, String cardholderName, String cardNumber,
                       int expiryMonth, int expiryYear, String cardType) {
        this.paymentType = paymentType;
        this.cardholderName = cardholderName;
        setCardNumber(cardNumber);
        this.expiryMonth = expiryMonth;
        this.expiryYear = expiryYear;
        this.cardType = cardType;
    }

    /**
     * Sets the name of the cardholder.
     *
     * @param cardholderName The name of the cardholder.
     */
    public void setCardholderName(String cardholderName) {
        this.cardholderName = cardholderName;
    }

    /**
     * Sets the card number.
     *
     * @param cardNumber The card number.
     */
    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    /**
     * Sets the type of the card (e.g., Visa, MasterCard, etc.).
     *
     * @param cardType The type of the card.
     */
    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    /**
     * Sets the expiration month of the card.
     *
     * @param expiryMonth The expiration month of the card.
     */
    public void setExpiryMonth(int expiryMonth) {
        this.expiryMonth = expiryMonth;
    }

    /**
     * Sets the expiration year of the card.
     *
     * @param expiryYear The expiration year of the card.
     */
    public void setExpiryYear(int expiryYear) {
        this.expiryYear = expiryYear;
    }

    /**
     * Sets the type of payment (CREDIT_CARD or DEBIT_CARD).
     *
     * @param paymentType The type of payment.
     */
    public void setPaymentType(PaymentType paymentType) {
        this.paymentType = paymentType;
    }

    /**
     * Gets the expiration month of the card.
     *
     * @return The expiration month of the card.
     */
    public int getExpiryMonth() {
        return expiryMonth;
    }

    /**
     * Gets the expiration year of the card.
     *
     * @return The expiration year of the card.
     */
    public int getExpiryYear() {
        return expiryYear;
    }

    /**
     * Gets the type of payment (CREDIT_CARD or DEBIT_CARD).
     *
     * @return The type of payment.
     */
    public PaymentType getPaymentType() {
        return paymentType;
    }

    /**
     * Gets the name of the cardholder.
     *
     * @return The name of the cardholder.
     */
    public String getCardholderName() {
        return cardholderName;
    }

    /**
     * Gets the card number.
     *
     * @return The card number.
     */
    public String getCardNumber() {
        return cardNumber;
    }

    /**
     * Gets the type of the card (e.g., Visa, MasterCard, etc.).
     *
     * @return The type of the card.
     */
    public String getCardType() {
        return cardType;
    }
}
