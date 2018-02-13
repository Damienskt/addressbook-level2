package seedu.addressbook.commands;


import seedu.addressbook.data.PreviousDeletes;

/**
 * Represents an incorrect command. Upon execution, produces some feedback to the user.
 */
public class IncorrectCommand extends Command {

    public final String feedbackToUser;

    public IncorrectCommand(String feedbackToUser) {
        this.feedbackToUser = feedbackToUser;
    }

    @Override
    public CommandResult execute() {
        return new CommandResult(feedbackToUser);
    }
    @Override
    public CommandResult execute(PreviousDeletes undo) {
        return new CommandResult(feedbackToUser);
    }

}
