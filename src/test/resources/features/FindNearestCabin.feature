Feature: HelloWorld return

Scenario: calling a cabin
Given cabin with id 1
And Shaft has current position 4
And Engine has EngineState "STAYING"
And cabin with id 2
And Shaft has current position 6
And engine has state "STAYING"
When passenger on floor 2 pushes UpFloorButton with direction "DOWNWARDS"
Then controller should create sequence of stops with floor 5 and id 2 and Direction "DOWNWARDS"