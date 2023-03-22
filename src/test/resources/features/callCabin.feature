Feature: Call cabin

  Scenario: passenger pushing up button
    Given cabin with index 0 and Engine has EngineState "STAYING" and Shaft has current position 3
    And cabin with index 1 and Engine has EngineState "STAYING" and Shaft has current position 4
    When passenger on floor 5 presses UpFloorButton with direction "UPWARDS"
    Then controller should create sequence of stops with floor 5 for Cabin with index 1 and Direction "UPWARDS"