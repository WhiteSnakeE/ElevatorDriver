Feature: Say helloWorld

  Scenario: user want to call sayHello method
    When the user calls sayHello
    Then operation is successful
    And method sayHello should return "Hello World!"

