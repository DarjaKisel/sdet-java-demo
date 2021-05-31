Feature: Bank account validation scenarios

  Background:
    Given valid authentication token

  @smoke
  Scenario: Bank account validation returns isValid flag = true for valid IBANs
    Given the bank account validation request with a valid IBAN 'GB09HAOE91311808002317'
    When bank account validation request is sent to the server
    Then server responds with HTTP response code '200'
    And response body contains isValid = 'true'

  Scenario Outline: Bank account pre-validation returns validation message for invalid IBANs
    Given the bank account validation request with a valid IBAN '<bankAccount>'
    When bank account validation request is sent to the server
    Then server responds with HTTP response code '<responseCode>'
    And response body contains message = '<message>'
    Examples:
      | bankAccount                           | responseCode | message                                           |
      | GB09HAOE91311808002317GB09HAOE9133333 | 400          | A string value exceeds maximum length of 34.      |
      | GB09HA                                | 400          | A string value with minimum length 7 is required. |

  @extended
  Scenario: Bank account validation returns validation response for invalid IBANs
    Given the bank account validation request with a invalid IBAN 'GB09HAOE913118080023170'
    When bank account validation request is sent to the server
    Then server responds with HTTP response code '200'
    And validation response body is
      | isValid               | false                                                                       |
      | type                  | BusinessError                                                               |
      | code                  | 200.908                                                                     |
      | message               | Value format is incorrect.                                                  |
      | customerFacingMessage | Die eingegebenen Bankdaten sind ungültig, bitte überprüfen und korrigieren. |
      | actionCode            | AskConsumerToReEnterData                                                    |
      | fieldReference        | bankAccount                                                                 |

  @extended
  Scenario: Bank account validation returns validation response for invalid IBANs
    Given the bank account validation request with a invalid IBAN ''
    When bank account validation request is sent to the server
    Then server responds with HTTP response code '400'
    And pre-validation response body is
      | type                  | BusinessError            |
      | code                  | 400.002                  |
      | message               | Value is required.       |
      | customerFacingMessage | Value is required.       |
      | actionCode            | AskConsumerToReEnterData |
      | fieldReference        | bankAccount              |
