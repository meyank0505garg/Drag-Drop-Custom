# : Drag and Drop Guide:

1. DraggableSection Function: This function encapsulates the section of out UI where you want to enable drag and drop functionality. It acts as the parent container for draggable items.It takes component composable function as parameter, with which we can wrap our composable function. Inside DraggableSection, i have implemented the logic to handle drag and drop events. You can read the code and modify according to your need .

2. DraggableItem Function: it represents the individual items within the DraggableSection that can be dragged and dropped.It should provide a unique identifier for each item using the itemKey as parameter. You will recieve the source id from sourceKey callback function and destination id from destinationKey callback function.

3. Logic for Drag and Drop: Inside DraggableSection, i have implemented the logic to handle drag and drop events. When a DraggableItem is dragged, we'll capture its sourceKey.As the item is dragged over potential drop targets (other DraggableItems), we'll use the destinationKey to identify these targets. we can then implement logic to swap elements or visually indicate potential drop targets (e.g., by displaying borders around them).
4. User Interface: DraggableSection and DraggableItem should be integrated into your UI hierarchy. DraggableSection will contain DraggableItems. DraggableItems should display the content that users can interact with and drag.
# : Testing and Customization:

After implementing the basic functionality, thoroughly test the drag and drop behavior.
Customize the appearance and behavior as needed to fit your application's requirements.


## :camera: Screen Shots of App :
<p align="center">
    <img alt="Home screen" src="./ScreenShots/Screenshots 2024-03-24 at 2.09.52 PM.png" height = "700px"/>
</p>
<p align="center">
    <img alt="Home screen" src="./screenshots/Screenshots 2024-03-24 at 2.10.03 PM.png" height = "700px"/>
</p>