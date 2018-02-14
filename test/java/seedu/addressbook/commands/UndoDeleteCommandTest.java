package seedu.addressbook.commands;

import org.junit.Before;
import org.junit.Test;
import seedu.addressbook.common.Messages;
import seedu.addressbook.data.AddressBook;
import seedu.addressbook.data.exception.IllegalValueException;
import seedu.addressbook.data.person.*;
import seedu.addressbook.data.person.UniquePersonList.PersonNotFoundException;
import seedu.addressbook.data.tag.UniqueTagList;
import seedu.addressbook.ui.TextUi;
import seedu.addressbook.util.TestUtil;
import seedu.addressbook.data.PreviousDeletes;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class UndoDeleteCommandTest {

    private AddressBook emptyAddressBook;
    private AddressBook addressBook;

    private List<ReadOnlyPerson> emptyDisplayList;
    private List<ReadOnlyPerson> listWithEveryone;
    private List<ReadOnlyPerson> listWithSurnameDoe;
    private PreviousDeletes deletes;

    @Before
    public void setUp() throws Exception {
        Person johnDoe = new Person(new Name("John Doe"), new Phone("61234567", false),
                new Email("john@doe.com", false), new Address("395C Ben Road", false), new UniqueTagList());
        Person janeDoe = new Person(new Name("Jane Doe"), new Phone("91234567", false),
                new Email("jane@doe.com", false), new Address("33G Ohm Road", false), new UniqueTagList());
        Person samDoe = new Person(new Name("Sam Doe"), new Phone("63345566", false),
                new Email("sam@doe.com", false), new Address("55G Abc Road", false), new UniqueTagList());
        Person davidGrant = new Person(new Name("David Grant"), new Phone("61121122", false),
                new Email("david@grant.com", false), new Address("44H Define Road", false),
                new UniqueTagList());

        emptyAddressBook = TestUtil.createAddressBook();
        addressBook = TestUtil.createAddressBook(johnDoe, janeDoe, davidGrant, samDoe);

        emptyDisplayList = TestUtil.createList();
        deletes = new PreviousDeletes();

        listWithEveryone = TestUtil.createList(johnDoe, janeDoe, davidGrant, samDoe);
        listWithSurnameDoe = TestUtil.createList(johnDoe, janeDoe, samDoe);
    }

    @Test
    public void execute_successful_undoDelete() throws PersonNotFoundException {
        assertUndoDeletionSuccessful(1, addressBook, listWithSurnameDoe);
    }

    /**
     * Creates a new delete command.
     *
     * @param targetVisibleIndex of the person that we want to delete
     */
    private DeleteCommand createDeleteCommand(int targetVisibleIndex, AddressBook addressBook,
                                                                      List<ReadOnlyPerson> displayList) {

        DeleteCommand command = new DeleteCommand(targetVisibleIndex);
        command.setData(addressBook, displayList);

        return command;
    }

    /**
     * Creates a new undoDelete command.
     *
     * @param
     */
    private UndoDeleteCommand createUndoDeleteCommand(AddressBook addressBook,
                                              List<ReadOnlyPerson> displayList) {

        UndoDeleteCommand command = new UndoDeleteCommand();
        command.setData(addressBook, displayList);

        return command;
    }

    /**
     * Asserts that the previously deleted person is added back into list
     *
     * The addressBook passed in will not be modified (no side effects).
     *
     * @throws PersonNotFoundException if the selected person is not in the address book
     */
    private void assertUndoDeletionSuccessful(int targetVisibleIndex, AddressBook addressBook,
                                              List<ReadOnlyPerson> displayList) throws PersonNotFoundException, UniquePersonList.DuplicatePersonException {

        ReadOnlyPerson targetPerson = displayList.get(targetVisibleIndex - TextUi.DISPLAYED_INDEX_OFFSET);
        Person target = new Person(targetPerson.getName(),targetPerson.getPhone(),targetPerson.getEmail(),targetPerson.getAddress(), targetPerson.getTags());

        AddressBook expectedAddressBook = TestUtil.clone(addressBook);
        expectedAddressBook.removePerson(targetPerson);
        expectedAddressBook.addPerson(target);
        String expectedUndoDeleteMessage = String.format(UndoDeleteCommand.MESSAGE_UNDO_SUCCESS);

        AddressBook actualAddressBook = TestUtil.clone(addressBook);


        DeleteCommand command = createDeleteCommand(targetVisibleIndex, actualAddressBook, displayList);
        command.execute(deletes);

        UndoDeleteCommand undoDelete = createUndoDeleteCommand(actualAddressBook,displayList);
        assertUndoDeleteCommandBehaviour(undoDelete,expectedUndoDeleteMessage,expectedAddressBook,actualAddressBook);


    }

    /**
     * Executes the command, and checks that the execution was what we had expected.
     */
    private void assertUndoDeleteCommandBehaviour(UndoDeleteCommand undoDeleteCommand, String expectedMessage,
                                        AddressBook expectedAddressBook, AddressBook actualAddressBook) {

        CommandResult result = undoDeleteCommand.execute(deletes);

        assertEquals(expectedMessage, result.feedbackToUser);
        assertEquals(expectedAddressBook.getAllPersons(), actualAddressBook.getAllPersons());
    }
}
