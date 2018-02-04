package seedu.addressbook.commands;

import seedu.addressbook.data.person.*;
import seedu.addressbook.data.tag.UniqueTagList;


import java.util.Stack;

public class UndoDeleteCommand extends Command {
    public static Stack<Person> previousDelete;
    public static final String COMMAND_WORD = "undoDelete";

    public UndoDeleteCommand() {
        previousDelete = new Stack<Person>();
    }

    public void AddDeleted (ReadOnlyPerson target) {
        Name nameOfRemoved = target.getName();
        Phone phoneOfRemoved = target.getPhone();
        Address addOfRemoved = target.getAddress();
        Email emailOfRemoved = target.getEmail();
        UniqueTagList tagOfRemoved = target.getTags();
        Person deletedPerson = new Person(nameOfRemoved,phoneOfRemoved,emailOfRemoved,addOfRemoved,tagOfRemoved);
        previousDelete.push(deletedPerson);
    }

    public void UndoDelete() throws UniquePersonList.DuplicatePersonException {
        if(UndoDeleteCommand.previousDelete.size()>0)
            addressBook.addPerson(previousDelete.pop());
        else {
            System.out.println("error");
        }
    }
}
