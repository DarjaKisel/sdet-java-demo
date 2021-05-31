package by.dzinevich.model.request;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class BankAccountRequest {
    String bankAccount;
}
