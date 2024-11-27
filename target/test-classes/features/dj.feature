Feature: DJ Management
  As a user
  I want to manage DJs
  So that I can create and retrieve DJs

  Scenario: Create a new DJ
    Given I have a new DJ with the name "DJ Mike", genre "Techno", and age 35
    When I send a request to create the DJ
    Then the response should return the created DJ with the name "DJ Mike"

#  Scenario: Get DJ by ID
#    Given a DJ with the ID 1 exists
#    When I send a request to get the DJ by ID
#    Then the response should return the DJ with the name "DJ Mike"