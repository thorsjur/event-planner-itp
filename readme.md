[nav](/docs/nav.md)

[![Gitpod Ready-to-Code](https://img.shields.io/badge/Gitpod-Ready--to--Code-blue?logo=gitpod)](https://gitpod.stud.ntnu.no/#gitlab.stud.idi.ntnu.no/it1901/groups-2022/gr2225/gr2225) 

# Group 25 - EventPlanner

## Repository content
- The [**eventplanner**](eventplanner/) folder constitutes the local modules, as well as project documentation.
    - [eventplanner/core](eventplanner/core/) contains the core module, with a core logic layer and persistence layer using json.
        - The persistence layer can be found at [eventplanner/core/src/main/java/eventplanner/json](eventplanner/core/src/main/java/eventplanner/json/)
        - The core layer can be found at [eventplanner/core/src/main/java/eventplanner/core](eventplanner/core/src/main/java/eventplanner/core/)
    - [eventplanner/fxui](eventplanner/fxui/) contains the module for the user interface
    - [eventplanner/rest](eventplanner/rest/) contains the module for the rest server
    - [eventplanner/integrationtest](eventplanner/integrationtests/) contains the integration tests
    - Each of the modules have their respective test folders located at `eventplanner/[MODULE_NAME]/src/test/java/eventplanner/`
    - The respective resource directories are located at `eventplanner/[MODULE_NAME]/src/main/resources/`
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


## Building
 ```
cd eventplanner
mvn install
```

Running mavens install command will validate, compile, test, package and verify the code. After it is done it will install the package to the local repository. Make sure you are in the correct directory (`gr2225/eventplanner/`)

A flag can be passed to the build to skip tests:
 ```
mvn install -DskipTests
```

## Usage
In order to user remote data access (rest-api) the server needs to be booted (in separate terminal) before the application is launched.
 ```
cd eventplanner/rest/
mvn exec:java
```
To run the program: change directory to `eventplanner/fxui/` and run the application with `mvn javafx:run`.
 ```
cd eventplanner/fxui/
mvn javafx:run
```
If the application is launched without the rest-server booted. An alternative local data access will be used. The applications title will tell wether you are connected to the server.

## Reports

Jacoco code-coverage:

 ```
cd eventplanner/fxui or cd eventplanner/core
mvn test
```
Spotbugs and checkstyle:

 ```
cd eventplanner/fxui or cd eventplanner/core
mvn site
```

All reports can be found at each respectable modules `target/site` folder.

jacoco.html;
spotbugs.html;
checksstyle.html;