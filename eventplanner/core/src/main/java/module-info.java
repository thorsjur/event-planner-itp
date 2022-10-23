module eventplanner.core {
    requires transitive com.fasterxml.jackson.databind;
    
    exports eventplanner.core;
    exports eventplanner.json;
    exports eventplanner.json.util;
    exports eventplanner.core.util;
}
