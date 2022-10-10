[nav](../../docs/nav.md)

# Group 25 - FXUI module

## FXUI module content
- FXML files
- Controllers
    - Handles fxml functinality.

## FXUI module Architecture
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

All reports can be found at each respective modules `target/site` folder.

jacoco.html;
spotbugs.html;
checksstyle.html;

## Prerequistes

- Maven (install: https://maven.apache.org/install.html)
- Latest Java-version recommended