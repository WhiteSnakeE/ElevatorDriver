Feature: Call cabin

  Scenario: calling a cabin for both free cabins with direction "UPWARDS"
    Given shaft with index 0 has free cabin and cabin position 3
    And shaft with index 1 has free cabin and cabin position 4
    When passenger on floor 5 presses UpFloorButton with direction "UPWARDS"
    Then Shaft with index 1 should have sequence of stops with floor 5 and direction "UPWARDS"
    And Shaft with index 0 should not have sequence of stops