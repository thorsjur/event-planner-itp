[![Gitpod Ready-to-Code](https://img.shields.io/badge/Gitpod-Ready--to--Code-blue?logo=gitpod)](https://gitpod.stud.ntnu.no/#gitlab.stud.idi.ntnu.no/it1901/groups-2022/gr2225/gr2225) 

# Group 25 - EventPlanner

## Repository content
- The **eventplanner** folder constitutes the code for the project
    - [eventplanner/src/main/java/eventplanner](eventplanner/src/main/java/eventplanner/) contains the model and controllers
    - [eventplanner/src/main/java/json](eventplanner/src/main/java/json/) contains the json implementation for file reading/writing
    - [eventplanner/src/main/resources](eventplanner/src/main/resources/) contains the fxml files which compose the view, and datafiles for loading and saving events.
    - [eventplanner/src/test](eventplanner/src/test/) contains the tests
- Release documentation can be found at [docs/.](docs/)
- Project documentation can be found at [eventplanner/README.md](eventplanner/README.md)

## EventPlanner Architecture
![Architecture diagram](docs/diagrams/architecture.png)

**Legend:**
 - A red, dotted line from *A* to *B* depicts that *B* is a dependency of *A*.
    - Arrows from a module indicate that all layers in the module has the dependency
 - The "box" named *eventplanner* represents the collection of local modules.
 - The components with a symbol in the top-right corner represents modules, such as *javafx* and *core*.
 - The folders represent the current packages or layers of the modules.


## Usage

 ```
mvn javafx:run
```

