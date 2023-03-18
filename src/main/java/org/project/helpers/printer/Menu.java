package org.project.helpers.printer;

import org.project.helpers.arrayUtils.ArrayUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

/** Representation of a Menu on terminal, this class can help to build console menus
 * en getting the user input from them*/
public class Menu{

    /**Index selected by the user, -1 if no given yet*/
    private int selectedIndex = -1;

    // Data
    /**Header of the menu*/
    private String header = "";
    /**Footer of the menu*/
    private String footer = "";
    /**List of MenuEntries the menu will present to user*/
    private List<MenuEntry> entries = new ArrayList<>();

    // Colors
    // Two colors: one for foreground, the other for background.
    /**Colors for printing the menu index*/
    private AnsiColors[] indexColors = new AnsiColors[2];
    /**Colors for printing the menu entries*/
    private AnsiColors[] entriesColors = new AnsiColors[2];
    /**Colors for printing the menu header*/
    private AnsiColors[] headerColors = new AnsiColors[2];
    /**Colors for printing the menu footer*/
    private AnsiColors[] footerColors = new AnsiColors[2];

    /**Menu Constructor*/
    public Menu(){}

    /**
     * Check if the index selected by user does really exist,
     * or if it is available.
     * @param selectedIndex Index selected by the user.
     * @return True if is valid, false if not.
     */
    private boolean isValidIndex(int selectedIndex){
        MenuEntry a = entries.get(0);
        String tryAgainMessage = "Press <Enter> to try again...";
        String entryUnavailableMessage = "Sorry this entry is not currently available...";
        String wrongIndexMessage = "Wrong index";
        if(!ArrayUtils.isIndexInList(entries, selectedIndex)){
            Printer.clearConsole();
            // Messages
            Printer.printConfirmation(wrongIndexMessage,
                    tryAgainMessage,
                    AnsiColors.RED);
            return false;
        }
        else if(!entries.get(selectedIndex).canShow()){
            Printer.clearConsole();
            Printer.printConfirmation(entryUnavailableMessage,
                    tryAgainMessage);
            return false;
        }
        return true;
    }

    /**
     * Print the given data on nice formatted menu form.
     */
    private void printMenu(){
        // PRINTING
        Printer.println(header, headerColors[0], headerColors[1]);
        for (int i = 0; i < this.entries.size(); i++) {
            printEntry(i, entries.get(i));
        }
        Printer.println(footer, footerColors[0], footerColors[1]);
    }

    /**
     * Print an entry with a given index.
     * @param entryIndex MenuEntry's index.
     * @param entry MenuEntry object to print.
     */
    private void printEntry(int entryIndex, MenuEntry entry){
        String index = "(" + entryIndex + ")";
        Printer.print(index, indexColors[0], indexColors[1]);
        if(entry.canShow())
            Printer.println(" " + entry.getPrompt(), entriesColors[0], entriesColors[1]);
        else
            Printer.println(" " + entry.getPrompt(), AnsiColors.BLACK, AnsiColors.BLACK_BACKGROUND);
    }

    // ----- PUBLIC API ------

    /**
     * Prints the menu with the given data,
     * and receives the user input.
     */
    public Menu show(){
        while(true){
            // Printing
            printMenu();
            // Getting user input
            int selectedEntry = Printer.inputInt("");
            // Evaluating
            if(isValidIndex(selectedEntry)){
                this.selectedIndex = selectedEntry;
                break;
            }
        }
        return this;
    }

    /**
     * Returns the object corresponding the entry selected
     * by the user. Refers to the initial objects given when
     * adding the entries with data() methods.
     * @return The Object contained in that menuEntry.
     * @param <T> Object's type.
     */
    public <T> T getEntryValue(){
        if(selectedIndex == -1)
            return null;
        return (T) entries.get(selectedIndex).getObservedObject();
    }

    /**
     *  Returns the index given by the user, the last time this
     *  object was call.
     * @return Index selected by the user.
     */
    public int getSelectedIndex() {
        return selectedIndex;
    }

    /**
     * Sets the Menu header.
     * @param header Menu's header.
     * @return this.
     */
    public Menu header(String header){
        this.header = header;
        return this;
    }

    /**
     * Sets the Menu footer.
     * @param footer Menu's footer.
     * @return this.
     */
    public Menu footer(String footer){
        this.footer = footer;
        return this;
    }

    /**
     * Sets the Menu's entries.
     * @param entries List of MenuEntries.
     * @return this.
     */
    public Menu data(List<MenuEntry> entries){
        this.entries = entries;
        return this;
    }

    /**
     * Sets the Menu's entries, and sets
     * a custom Function to print the objects given on the
     * entries.
     * @param entries List of MenuEntries.
     * @param prompter Custom function to print the objects.
     * @return this.
     * @param <G> Type of the object entries.
     */
    public <G> Menu data(List<G> entries, Function<G, String> prompter){
        for (G entry: entries)
            this.entries.add(new MenuEntry<G>(entry, prompter));
        return this;
    }

    /**
     * Sets the Menu's entries, and sets
     * a custom Function to print the objects given on the
     * entries.
     * @param entries List of MenuEntries.
     * @param prompter Custom function to print the objects.
     * @param canShowFunction Custom predicate to check if an Entry can be shown or not.
     *                        It must receive and object and return a boolean.
     * @return this.
     * @param <G> Type of the object entries.
     */
    public <G> Menu data(List<G> entries, Function<G, String> prompter, Predicate<G> canShowFunction){
        for (G entry: entries)
            this.entries.add(new MenuEntry<>(entry, prompter, canShowFunction));
        return this;
    }

    /**
     * Sets the colors for printing the index.
     * Play with both colors to set the text foreground and background.
     * @param color1
     * @param color2
     * @return this.
     */
    public Menu indexColors(AnsiColors color1, AnsiColors color2){
        this.indexColors = new AnsiColors[] {color1, color2};
        return this;
    }

    /**
     * Sets the colors for printing the entries.
     * Play with both colors to set the text foreground and background.
     * @param color1
     * @param color2
     * @return this.
     */
    public Menu entryColors(AnsiColors color1, AnsiColors color2){
        this.entriesColors = new AnsiColors[] {color1, color2};
        return this;
    }

    /**
     * Sets the colors for printing the header.
     * Play with both colors to set the text foreground and background.
     * @param color1
     * @param color2
     * @return this.
     */
    public Menu headerColors(AnsiColors color1, AnsiColors color2){
        this.headerColors = new AnsiColors[] {color1, color2};
        return this;
    }

    /**
     * Sets the colors for printing the footer.
     * Play with both colors to set the text foreground and background.
     * @param color1
     * @param color2
     * @return this.
     */
    public Menu footerColors(AnsiColors color1, AnsiColors color2){
        this.footerColors = new AnsiColors[] {color1, color2};
        return this;
    }
}
