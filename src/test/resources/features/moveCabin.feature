Feature: move cabin

  Scenario: cabin is on the same floor
    Given All shaft are free and no sequence of stops in queue
    And shaft with index 0 has free cabin and cabin position 3
    And shaft with index 1 has free cabin and cabin position 7
    When passenger on floor 7 presses UpFloorButton with direction "UPWARDS"
    Then commands should have be invoked for shaft with index 1: OpenDoorCommand, CloseDoorCommand for floor 7

  Scenario: calling a cabin for both free cabins with direction "UPWARDS"
    Given All shaft are free and no sequence of stops in queue
    And shaft with index 0 has free cabin and cabin position 3
    And shaft with index 1 has free cabin and cabin position 5
    When passenger on floor 7 presses UpFloorButton with direction "UPWARDS"
    Then commands should have be invoked for shaft with index 1: MoveCabinCommand, StopCabinCommand, OpenDoorCommand, CloseDoorCommand for floor 7

  Scenario: move cabin with sequence and with "UPWARDS" direction
    Given All shaft are free and no sequence of stops in queue
    And  shaft with index 1 has sequence of stops with floors 3, 5, 7 and Direction "UPWARDS" and cabin position 2
    And  shaft with index 0 has sequence of stops with floor 12 and Direction "UPWARDS" and cabin position 9
    When start cabin with index 1 moving sequence of stops to
    Then commands should have be invoked for shaft with index 1: MoveCabinCommand, StopCabinCommand, OpenDoorCommand, CloseDoorCommand for floors 3, 5, 7