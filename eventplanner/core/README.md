[![Gitpod Ready-to-Code](https://img.shields.io/badge/Gitpod-Ready--to--Code-blue?logo=gitpod)](https://gitpod.stud.ntnu.no/#gitlab.stud.idi.ntnu.no/it1901/groups-2022/gr2225/gr2225) 
#### Navigation:
- modules
    - [Eventplanner](../../README.md)
    - [fxui](../fxui/README.md)
    - core
- other
    - [application description and user stories](../README.md)
    - [release 1](../../docs/release1.md)
- repo
    - [gitlab](https://gitlab.stud.idi.ntnu.no/it1901/groups-2022/gr2225/gr2225)
    - [issue-tracker](https://gitlab.stud.idi.ntnu.no/it1901/groups-2022/gr2225/gr2225/-/issues)

# Group 25 - Core module

## Module content

Core module contains core logic layer and persistence layer using json.
        - The persistence layer can be found at [eventplanner/core/src/main/java/eventplanner/json](eventplanner/core/src/main/java/eventplanner/json/)
        - The core layer can be found at [eventplanner/core/src/main/java/eventplanner/core](eventplanner/core/src/main/java/eventplanner/core/)

## Module Architecture
![Architecture diagram](docs/diagrams/architecture.png)

**Legend:**
 - A red, dotted line from *A* to *B* depicts that *B* is a dependency of *A*.
    - Arrows from a module indicate that all layers in the module has the dependency
 - The "box" named *eventplanner* represents the collection of local modules.
 - The components with a symbol in the top-right corner represents modules, such as *javafx* and *core*.
 - The folders represent the current packages or layers of the modules.

## Reports

Jacoco code-coverage:

 ```
mvn test
```
Spotbugs and checkstyle:

 ```
mvn site
```

All reports can be found at each respectable modules `target/site` folder.

jacoco.html;
spotbugs.html;
checksstyle.html;

## Prerequistes

- Maven (install: https://maven.apache.org/install.html)
- Latest Java-version recommended