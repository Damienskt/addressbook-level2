package seedu.addressbook.commands;

import seedu.addressbook.data.PreviousDeletes;
import seedu.addressbook.data.person.*;

public class UndoDeleteCommand extends Command {
    public static final String COMMAND_WORD = "undoDelete";
    public static final String MESSAGE_UNDO_FAILED = "Failed to undo delete";

    public UndoDeleteCommand() {
    }

    public CommandResult execute(PreviousDeletes undo) {
        if(undo.getSize()>0) {
            try {
                addressBook.addPerson(undo.pop());
            } catch (UniquePersonList.DuplicatePersonException e) {
                e.printStackTrace();
            }
            return new CommandResult("Undo Complete");
        }
        else return new CommandResult(MESSAGE_UNDO_FAILED);
    }
}
