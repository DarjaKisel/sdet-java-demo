# Test automation solution

## Dependencies

1. Make sure Java is installed and set to the local environment.

    The solution is configured to use Java 11. 
    ```
    java --version
    > openjdk 11.0.2
    ```
 
2. Make sure Maven is installed and set to the local environment
    ```
    mvn --version
    > Apache Maven 3.8.1
    ```

3. Make sure Allure is installed and set to the local environment
    ```
    allure --version
    > 2.14.0
    ```

## Running on your local machine

1. Clone this github project 
    ```
    git clone https://github.com/DarjaKisel/sdet-java-demo.git
    ```

2. Navigate to the project directory 
   
3. Execute tests

    * Note that in order to make the tests configurable, many of run parameters have command line overrides. 
    Examples of these overrides can be found below in the `Run command` section.
    
    | Run command                                 | Run configuration description                                        |
    |---------------------------------------------|----------------------------------------------------------------------|
    | mvn clean test                              | run all tests, use default profile                                   |    
    | -Pdev                                       | use dev profile, no overrides for default dev profile parameters     | 
    | -Pdev -Dprofile.token=...                   | use dev profile, override the `profile.token` profile parameter      |
    | -DauthToken=...                             | use default profile, override the `authToken` parameter              |
    | -Dtest=BankAccountSmokeTest                 | run a single test class `BankAccountSmokeTest` using default profile |
    | -Dcucumber.options="--tags '@extended'"     | parameter to execute only`@extended` tagged scenarios                |         

## Generating reports

4. Reports should be generated automatically under `/target/allure-results/`

4. View the allure reports. It should open the default browser with generated reports.
    ```
    allure serve target/allure-results/
    ```
   