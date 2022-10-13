package eventplanner.fxui.util;

import eventplanner.core.Event;
import eventplanner.core.User;
import eventplanner.fxui.App;
import eventplanner.fxui.AppController;
import eventplanner.fxui.MyEventsController;
import eventplanner.fxui.NewEventController;

import java.io.IOException;
import java.util.function.Supplier;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map;
import java.util.Random;

import javafx.beans.value.ChangeListener;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
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
    public static <T> FXMLLoader getFXMLLoaderWithFactory(String fxmlFilename, Class<T> cls, User user) {
        FXMLLoader loader = getFXMLLoader(fxmlFilename);
        loader.setControllerFactory(getControllerFactory(cls, user));
        return loader;
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
    private static <T> Callback<Class<?>, Object> getControllerFactory(Class<T> cls, User user) {
        final Map<Class<?>, Object> classMap = Map.of(
                AppController.class, new AppController(user),
                MyEventsController.class, new MyEventsController(user),
                NewEventController.class, new NewEventController(user));
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
     * Generates a random 5-digit number as a string.
     * 
     * @return 5-digit number string
     */
    public static String getRandomFiveDigitString() {
        Random random = new Random();
        return random.ints(5, 0, 10)
                .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append)
                .toString();
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

}
