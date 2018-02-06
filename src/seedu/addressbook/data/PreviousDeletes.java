package seedu.addressbook.data;

import seedu.addressbook.data.person.*;
import seedu.addressbook.data.tag.UniqueTagList;

import java.util.Stack;

public class PreviousDeletes {
    public static Stack<Person> previousDelete;

    public PreviousDeletes() {
        previousDelete = new Stack<>();
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
    public int getSize() {
        return previousDelete.size();
    }

    public Person pop() {
        return previousDelete.pop();
    }
}
