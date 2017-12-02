@tag
Feature: Test the Login functionality of Rant Cell
  I want to Test the Login functionality

  @tag2
  Scenario Outline: Testing the login functionality with valid users
    Given I am on RantCell pre-login page
    When I enter username "<UserName>" and I enter password "<Password>"
    And I click on login button
    Then Ishould be logged onto the RantCell Home page
    And I get the basic details
    And I logoff from the RantCell

    Examples: 
      | UserName           | Password  |
      | testu321@gmail.com | @rantcell |
    #https://demo.rantcell.com/
