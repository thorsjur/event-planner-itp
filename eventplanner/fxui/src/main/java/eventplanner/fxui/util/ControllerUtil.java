package eventplanner.fxui.util;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map;
import java.util.function.Predicate;
import java.util.function.Supplier;

import eventplanner.core.Event;
import eventplanner.core.User;
import eventplanner.fxui.App;
import eventplanner.fxui.DataAccess;
import eventplanner.fxui.AllEventsController;
import eventplanner.fxui.EventPageController;
import eventplanner.fxui.LoginController;
import eventplanner.fxui.NewEventController;
import eventplanner.fxui.RegisterController;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.util.Callback;

/**
 * Static utility methods to simplify logic in the controllers.
 */
public class ControllerUtil {

    /**
     * Takes a FXMLLoader and a child of the current scene
     * and sets a new root based on the given loader.
     * 
     * @param loader the FXMLLoader of the current scene
     * @param child  a child of the current scene
     */
    public static void setSceneFromChild(FXMLLoader loader, Node child) {
        try {
            loader.load();
            child.getScene().setRoot(loader.getRoot());
        } catch (IOException e) {
            System.out.println("IOException occurred while loading scene.");
        }
    }

    /**
     * Returns a FXMLLoader from the provided fxml file name
     * 
     * @param fxmlFilename the filename of the fxml file in the resources directory
     * @return a FXMLLoader from the given fxml filename
     */
    public static FXMLLoader getFXMLLoader(String fxmlFilename) {
        if (fxmlFilename == null) {
            throw new IllegalArgumentException("filename cannot be null");
        }
        return new FXMLLoader(App.class.getResource(fxmlFilename));
    }

    /**
     * Returns a FXMLLoader with a controller factory respective to the given class.
     * 
     * @param <T>          represents the generic type of the controller
     * @param fxmlFilename the filename of the fxml file in the resources directory
     * @param cls          the class of the controller
     * @param user         the user to be passed to the controller
     * @return a FXMLLoader with an associated controller factory
     */
    public static <T> FXMLLoader getFXMLLoaderWithFactory(String fxmlFilename, Class<T> cls, User user, DataAccess dataAccess) {
        FXMLLoader loader = getFXMLLoader(fxmlFilename);
        loader.setControllerFactory(getControllerFactory(cls, user, dataAccess));
        return loader;
    }

    /**
     * Returns a FXMLLoader with a controller factory for the EventPage controller.
     * 
     * @param event        the event to be displayed
     * @param user         the user to be passed to the controller
     * @return a FXMLLoader with the associated event page controller
     */
    public static FXMLLoader getFXMLLoaderWithEventPageFactory(User user, Event event, DataAccess dataAccess) {
        FXMLLoader loader =  getFXMLLoader("EventPage.fxml");
        loader.setControllerFactory(getEventPageControllerFactory(user, event, dataAccess));
        return loader;
    }

    private static Callback<Class<?>, Object> getEventPageControllerFactory(User user, Event event, DataAccess dataAccess) {
        return param -> new EventPageController(user, event, dataAccess);
    }

    /**
     * Returns a controller factory from the given class, and provides the created
     * controller with the user.
     * 
     * @param <T>  represents the generic class T
     * @param cls  the class of the controller
     * @param user the user to be passed to the controller
     * @return the controller factory
     */
    private static <T> Callback<Class<?>, Object> getControllerFactory(Class<T> cls, User user, DataAccess dataAccess) {
        final Map<Class<?>, Object> classMap = Map.of(
                AllEventsController.class, new AllEventsController(user, dataAccess),
                NewEventController.class, new NewEventController(user, dataAccess), 
                LoginController.class, new LoginController(dataAccess), 
                RegisterController.class, new RegisterController(dataAccess)
                );
        if (!classMap.containsKey(cls)) {
            throw new IllegalArgumentException("Invalid class provided");
        }
        return param -> classMap.get(cls);
    }
    
    /**
     * Returns a ChangeListener based on a specific validation.
     * Runs actionIfValid if supplier validation return true,
     * otherwise it runs actionIfInvalid.
     * 
     * @param validation      supplier that returns a boolean value indicating
     *                        validity
     * @param actionIfValid   action on valid supplier return
     * @param actionIfInvalid action of invalid supplier return
     * @return ChangeListener for change in object focus
     */
    public static ChangeListener<Boolean> getValidationFocusListener(
            Supplier<Boolean> validation,
            Runnable actionIfValid,
            Runnable actionIfInvalid) {

        return (arg0, oldValue, newValue) -> {
            if (!newValue) {
                if (validation.get()) {
                    actionIfValid.run();
                } else {
                    actionIfInvalid.run();
                }
            }
        };
    }

    /**
     * Returns a comparator that sort events where the last events come first, and
     * first events come last.
     * 
     * @return comparator for events
     */
    public static Comparator<Event> getDateComparator() {
        return (e1, e2) -> e1.getStartDate().compareTo(e2.getStartDate());
    }

    /**
     * Returns a comparator that sort events where the first events come last, and
     * last events come first.
     * 
     * @return comparator for events
     */
    public static Comparator<Event> getReverseDateComparator() {
        return Collections.reverseOrder(getDateComparator());
    }

   /**
    * Takes events, and filters out the ones
    * that don't match with the value in the searchbar. 
    * Returns a sortedList of the matching events.
    *
    * @param eventCollection    Collection of events
    * @param searchBar          TextField with input that gets observed
    * @return SortedList of matching events
    */
    public static FilteredList<Event> searchFiltrator(Collection<Event> eventCollection, TextField searchBar) {
        
        ObservableList<Event> observableEventList = FXCollections.observableArrayList(eventCollection);
        FilteredList<Event> filteredEvents = new FilteredList<>(observableEventList, b -> true);
        
        searchBar.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredEvents.setPredicate(event -> {

                // If no search value is entered then all events will be displayed
                if(newValue == null || newValue.isBlank()) {
                    return true;
                }

                String searchKeyword = newValue.toLowerCase();
                Predicate<String> matchPredicate = str -> str.toLowerCase().indexOf(searchKeyword) > -1;

                // Found eventname match
                if (matchPredicate.test(event.getName())) {
                    return true;
                }

                // Found type match
                if (matchPredicate.test(event.getType().toString())) {
                    return true;
                }

                // Found location match
                if (matchPredicate.test(event.getLocation())) {
                    return true;
                }

                return false;
            });
        });

        return filteredEvents;
    }

    public static String SERVER_ERROR = "Server error. Please try again.";

}
