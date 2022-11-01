module eventplanner.rest {
    requires transitive com.fasterxml.jackson.databind;
    requires transitive eventplanner.core;

    requires spring.boot;
    requires spring.boot.autoconfigure;
    requires spring.context;
    requires spring.web;
    requires spring.beans;
    requires transitive spring.core;

    opens eventplanner.rest;
    exports eventplanner.rest;
}
