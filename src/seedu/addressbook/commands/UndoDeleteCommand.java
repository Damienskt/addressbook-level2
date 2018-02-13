package seedu.addressbook.commands;

import seedu.addressbook.data.PreviousDeletes;
import seedu.addressbook.data.person.*;

public class UndoDeleteCommand extends Command {
    public static final String COMMAND_WORD = "undodelete";
    public static final String MESSAGE_UNDO_FAILED = "Failed to undo delete";

    public UndoDeleteCommand() {
    }

    /**
     *
     * @param listOfDeleted
     * @return return result of command
     */
    public CommandResult execute(PreviousDeletes listOfDeleted) {
        if(listOfDeleted.getSize()>0) {
            return getCommandResult(listOfDeleted);
        }
        else return new CommandResult(MESSAGE_UNDO_FAILED);
    }

    /**
     * Store previously deleted Person into address book
     * @param listOfDeleted
     * @return return result of command
     */
    private CommandResult getCommandResult(PreviousDeletes listOfDeleted) {
        try {
            addressBook.addPerson(listOfDeleted.pop());
        } catch (UniquePersonList.DuplicatePersonException e) {
            e.printStackTrace();
        }
        return new CommandResult("Undo Complete");
    }
}
