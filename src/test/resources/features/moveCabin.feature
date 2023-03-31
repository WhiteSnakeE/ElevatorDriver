Feature: move cabin

Scenario: cabin is on the same floor
Given All shaft are free and no sequence of stops in queue
And shaft with index 0 has free cabin and cabin position 3
And shaft with index 1 has free cabin and cabin position 7
When passenger on floor 7 presses UpFloorButton with direction "UPWARDS"
Then commands should have be invoked for shaft with index 1: openDoor, checkOverweight, closeDoor for floor 7