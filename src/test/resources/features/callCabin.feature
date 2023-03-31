Feature: Call cabin

  Scenario: calling a cabin for both free cabins with direction "UPWARDS"
    Given shaft with index 0 has free cabin and cabin position 3
    And shaft with index 1 has free cabin and cabin position 4
    When passenger on floor 5 presses UpFloorButton with direction "UPWARDS"
    Then Shaft with index 1 should have sequence of stops with floor 5 and direction "UPWARDS"
    And Shaft with index 0 should not have sequence of stops

  Scenario: calling a cabin with one free cabin with direction "UPWARDS"
    Given shaft with index 0 has sequence of stops with floor 10 and Direction "UPWARDS" and cabin position 4
    And shaft with index 1 has free cabin and cabin position 6
    When passenger on floor 2 presses UpFloorButton with direction "UPWARDS"
    Then Shaft with index 1 should have sequence of stops with floor 2 and direction "UPWARDS"
    And Shaft with index 0 should have sequence of stops with floor 10 and direction "UPWARDS"

  Scenario: calling a cabin with both busy cabins with same direction
    Given All shaft are free and no sequence of stops in queue
    And shaft with index 0 has sequence of stops with floor 10 and Direction "UPWARDS" and cabin position 4
    And shaft with index 1 has sequence of stops with floor 10 and Direction "UPWARDS" and cabin position 6
    When passenger on floor 3 presses UpFloorButton with direction "UPWARDS"
    Then Shaft with index 1 should have sequence of stops with floor 10 and direction "UPWARDS"
    And Shaft with index 0 should have sequence of stops with floor 10 and direction "UPWARDS"
    And ElevatorDriver has sequence of stops with floor 3

  Scenario: calling a cabin with both busy cabins with same direction: update case
    Given All shaft are free and no sequence of stops in queue
    And shaft with index 0 has sequence of stops with floor 10 and Direction "UPWARDS" and cabin position 4
    And shaft with index 1 has sequence of stops with floor 10 and Direction "UPWARDS" and cabin position 6
    When passenger on floor 8 presses UpFloorButton with direction "UPWARDS"
    Then Shaft with index 1 should have sequence of stops with floors 8, 10 and direction "UPWARDS"
    And  Shaft with index 0 should have sequence of stops with floor 10 and direction "UPWARDS"
