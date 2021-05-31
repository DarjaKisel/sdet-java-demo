package by.dzinevich.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum Type {
    @JsonProperty("BusinessError") BUSINESS_ERROR,
    @JsonProperty("TechnicalError") TECHNICAL_ERROR,
    @JsonProperty("NotificationMessage") NOTIFICATION_MESSAGE;
}
