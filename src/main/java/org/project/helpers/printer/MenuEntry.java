package org.project.helpers.printer;

import java.util.function.Function;
import java.util.function.Predicate;

/** Representation of a Menu entry, this class work together with
 * Menu class, for building console Menu's.
 *
 * Menu class contains --> MenuEntry entities.*/
public class MenuEntry<T extends Object> {

    /**Message that will be printed to the user.*/
    private String prompt = "";
    /**Object this menuEntry represents*/
    private T observedObject;
    /**Custom Predicate to tell the Menu class if this
     * entry must be shown or not.
     * If null, this menuEntry will always be show.*/
    private Predicate<T> observerFunction;

    /**
     * Constructor
     * @param observedObject Object this menuEntry represents.
     */
    public MenuEntry(T observedObject){
        this.observedObject = observedObject;
        this.prompt = observedObject.toString();
    }

    /**
     * Constructor
     * @param observedObject Object this menuEntry represents.
     * @param prompter Custom Function to set the Entry prompt.
     *                 Especially useful, for complex objects where
     *                 its necessary to print just an specific values.
     *                 ¿How it works?
     *                 this function receives the observedObject and
     *                 can build a custom prompt with the object attributes.
     *                 Its up to your imagination.
     */
    public MenuEntry(T observedObject, Function<T, String> prompter){
        this.observedObject = observedObject;
        this.prompt = prompter.apply(observedObject);
    }

    /**
     * Constructor
     * @param observedObject Object this menuEntry represents.
     * @param prompter Custom Function to set the Entry prompt.
     *                 Especially useful, for complex objects where
     *                 its necessary to print just an specific values.
     *                 ¿How it works?
     *                 this function receives the observedObject and
     *                 can build a custom prompt with the object attributes.
     *                 Its up to your imagination.
     * @param canShowFunction Custom Predicate to tell the Menu
     *                        if this Entry must be shown or not.
     */
    public MenuEntry(T observedObject, Function<T, String> prompter, Predicate<T> canShowFunction){
        this.observedObject = observedObject;
        this.prompt = prompter.apply(observedObject);
        this.observerFunction = canShowFunction;
    }

    /**
     * Constructor
     * @param prompt Message that will be show to the user.
     * @param observedObject Object this menuEntry represents.
     * @param canShowFunction Custom Predicate to tell the Menu
     *                        if this Entry must be shown or not.
     */
    public MenuEntry(String prompt, T observedObject, Predicate<T> canShowFunction){
        this.prompt = prompt;
        this.observedObject = observedObject;
        this.observerFunction = canShowFunction;
    }

    /**
     *
     * @return true if the Entry can be show or not.
     */
    public boolean canShow(){
        if(observerFunction != null){
            return observerFunction.test(observedObject);
        }
        return true;
    }

    /**
     * @return Entry prompt.
     */
    public String getPrompt() {
        return prompt;
    }

    /**
     *
     * @return Entry observed Object.
     */
    public T getObservedObject(){ return observedObject; }
}
