Feature: move cabin

#  Scenario: cabin is on the same floor
#    Given All shaft are free and no sequence of stops in queue
#    And shaft with index 0 has free cabin and cabin position 3
#    And shaft with index 1 has free cabin and cabin position 7
#    When passenger on floor 7 presses UpFloorButton with direction "UPWARDS"
#    Then commands should have be invoked for shaft with index 1: OpenDoorCommand, CloseDoorCommand for floor 7
#
#  Scenario: calling a cabin for both free cabins with direction "UPWARDS"
#    Given All shaft are free and no sequence of stops in queue
#    And shaft with index 0 has free cabin and cabin position 3
#    And shaft with index 1 has free cabin and cabin position 5
#    When passenger on floor 7 presses UpFloorButton with direction "UPWARDS"
#    Then commands should have be invoked for shaft with index 1: StartEngineCommand, StopEngineCommand, OpenDoorCommand, CloseDoorCommand for floor 7
#
#  Scenario: move cabin with sequence and with "UPWARDS" direction
#    Given All shaft are free and no sequence of stops in queue
#    And  shaft with index 1 has sequence of stops with floors 3, 5, 7 and Direction "UPWARDS" and cabin position 2
#    And  shaft with index 0 has sequence of stops with floor 12 and Direction "UPWARDS" and cabin position 9
#    When start cabin with index 1 moving sequence of stops to
#    Then commands should have be invoked for shaft with index 1: StartEngineCommand, StopEngineCommand, OpenDoorCommand, CloseDoorCommand for floors 3, 5, 7


  Scenario: calling a cabin from certain house for both free cabins with direction "UPWARDS"
    Given house with id 1 is created in database
    And house with id 1 has shaft with id 1 and this shaft has free cabin and cabin position 3
    And house with id 1 has shaft with id 2 and this shaft has free cabin and cabin position 4
    When passenger in house with id 1 on floor 5 presses UpFloorButton
    Then commands should have be invoked for house with id 1 and shaft with id 2: MoveCabinCommand,VisitFloorCommand, StopEngineCommand, OpenDoorCommand, CloseDoorCommand for floor 5
    And shaft with id 2 of house with id 1 has cabin position 5