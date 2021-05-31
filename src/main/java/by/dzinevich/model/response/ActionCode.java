package by.dzinevich.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum ActionCode {
    @JsonProperty("Unavailable") UNAVAILABLE,
    @JsonProperty("AskConsumerToConfirm") ASK_CONSUMER_TO_CONFIRM,
    @JsonProperty("AskConsumerToReEnterData") ASK_CONSUMER_TO_RE_ENTER_DATA,
    @JsonProperty("OfferSecurePaymentMethods") OFFER_SECURE_PAYMENT_METHODS,
    @JsonProperty("RequiresSsn") REQUIRES_SSN;
}
