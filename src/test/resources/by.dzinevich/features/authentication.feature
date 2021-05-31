Feature: Authentication scenarios

  @smoke
  Scenario: Bank account validation responds with 401 response code, if authentication token was not provided.
    Given no authentication token
    And the bank account validation request with a valid IBAN 'GB09HAOE91311808002317'
    When bank account validation request is sent to the server
    Then server responds with HTTP response code '401'
    And response body message = 'Authorization has been denied for this request.'

  Scenario: Bank account validation responds with 401 response code, if authentication token was invalid.
    Given invalid authentication token
    And the bank account validation request with a valid IBAN 'GB09HAOE91311808002317'
    When bank account validation request is sent to the server
    Then server responds with HTTP response code '401'
    And response body message = 'Authorization has been denied for this request.'
