Feature:Call cabin

  Scenario: passenger pushing up button
    Given cabin with id 1 and Engine has EngineState "STAYING" and Shaft has current position 3
    And cabin with id 2 and Engine has EngineState "STAYING" and Shaft has current position 4
    When passenger on floor 5 presses UpFloorButton with direction "UPWARDS"
    Then controller should create order with sequence of stops with floor 5 and id 2 and Direction "UPWARDS"