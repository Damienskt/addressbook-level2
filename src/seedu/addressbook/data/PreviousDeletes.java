package seedu.addressbook.data;

import seedu.addressbook.data.person.*;
import seedu.addressbook.data.tag.UniqueTagList;

import java.util.Stack;

public class PreviousDeletes {
    public static Stack<Person> previousDelete;

    public PreviousDeletes() {
        previousDelete = new Stack<>();
    }

    /**
     * Adds deleted person into stack
     * @param target
     */
    public void AddDeletedPerson(ReadOnlyPerson target) {
        Person deletedPerson = getPerson(target);
        previousDelete.push(deletedPerson);
    }

    /**
     * Create Person class
     * @param target
     * @return Person
     */
    private Person getPerson(ReadOnlyPerson target) {
        Name nameOfRemoved = target.getName();
        Phone phoneOfRemoved = target.getPhone();
        Address addOfRemoved = target.getAddress();
        Email emailOfRemoved = target.getEmail();
        UniqueTagList tagOfRemoved = target.getTags();
        return new Person(nameOfRemoved,phoneOfRemoved,emailOfRemoved,addOfRemoved,tagOfRemoved);
    }

    public int getSize() {
        return previousDelete.size();
    }

    public Person pop() {
        return previousDelete.pop();
    }
}
