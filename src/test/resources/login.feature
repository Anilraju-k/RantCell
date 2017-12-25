@basic
Feature: Test the Login functionality of Rant Cell
  I want to Test the Login functionality

  Scenario: Testing the login functionality with valid users
    Given I am on RantCell pre-login page
    When I enter username "testu321@gmail.com" and I enter password "@rantcell"
    And I click on login button
    Then I should be logged onto the RantCell Home page
    And I get the basic details
    And I logoff from the RantCell
    
    
  Scenario: Verify the registerd devices with valid users
    Given I am on RantCell pre-login page
    When I enter username "testu321@gmail.com" and I enter password "@rantcell"
    And I click on login button
    Then I should be logged onto the RantCell Home page
    And I click on Registerd devices for more information
    And I get the list of devices registerd
    And I logoff from the RantCell  
    
 Scenario: Verify the registerd devices with valid users
    Given I am on RantCell pre-login page
    When I enter username "testu321@gmail.com" and I enter password "@rantcell"
    And I click on login button
    Then I should be logged onto the RantCell Home page
    And I click on detected networks for more information
    And I get the list of detected networks
    And I logoff from the RantCell     
 
 Scenario: Verify the report for failed credentials
    Given I am on RantCell pre-login page
    When I enter username "testu321@gmail.com" and I enter password "@rantcelli"
    And I click on login button
    Then I should be logged onto the RantCell Home page
    
   
 
    
    
    #Examples: 
     # | UserName           | Password  |
     # | testu321@gmail.com | @rantcell |
    #https://demo.rantcell.com/
