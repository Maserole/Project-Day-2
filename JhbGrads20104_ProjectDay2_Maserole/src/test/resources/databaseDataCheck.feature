Feature: Database data check from CSV file

  Scenario: Database data existence check from CSV file
    Given The CSV File Exist
    When The file is processed
    Then The serenity report display the test outcomes
