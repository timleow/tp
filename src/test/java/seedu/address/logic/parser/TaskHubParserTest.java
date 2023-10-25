package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DEADLINE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMPLOYEE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PROJECT;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_EMPLOYEE;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PROJECT;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AddEmployeeCommand;
import seedu.address.logic.commands.AddProjectCommand;
import seedu.address.logic.commands.AssignEmployeeCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.DeleteEmployeeCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditCommand.EditEmployeeDescriptor;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindEmployeeCommand;
import seedu.address.logic.commands.FindProjectCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.ListEmployeeCommand;
import seedu.address.logic.commands.ListProjectCommand;
import seedu.address.logic.commands.ProjectDeadlineCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.employee.Employee;
import seedu.address.model.employee.EmployeeNameContainsKeywordsPredicate;
import seedu.address.model.project.Deadline;
import seedu.address.model.project.Project;
import seedu.address.model.project.ProjectNameContainsKeywordsPredicate;
import seedu.address.testutil.EditEmployeeDescriptorBuilder;
import seedu.address.testutil.EmployeeBuilder;
import seedu.address.testutil.EmployeeUtil;

public class TaskHubParserTest {

    private final TaskHubParser parser = new TaskHubParser();

    @Test
    public void parseCommand_add() throws Exception {
        Employee employee = new EmployeeBuilder().build();
        AddEmployeeCommand command = (AddEmployeeCommand) parser
                .parseCommand(EmployeeUtil.getAddEmployeeCommand(employee));
        assertEquals(new AddEmployeeCommand(employee), command);
    }

    @Test
    public void parseCommand_clear() throws Exception {
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD) instanceof ClearCommand);
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD + " 3") instanceof ClearCommand);
    }

    @Test
    public void parseCommand_delete() throws Exception {
        DeleteEmployeeCommand command = (DeleteEmployeeCommand) parser.parseCommand(
                DeleteEmployeeCommand.COMMAND_WORD + " " + INDEX_FIRST_EMPLOYEE.getOneBased());
        assertEquals(new DeleteEmployeeCommand(INDEX_FIRST_EMPLOYEE), command);
    }

    @Test
    public void parseCommand_edit() throws Exception {
        Employee employee = new EmployeeBuilder().build();
        EditEmployeeDescriptor descriptor = new EditEmployeeDescriptorBuilder(employee).build();
        EditCommand command = (EditCommand) parser.parseCommand(EditCommand.COMMAND_WORD + " "
                + INDEX_FIRST_EMPLOYEE.getOneBased() + " " + EmployeeUtil.getEditEmployeeDescriptorDetails(descriptor));
        assertEquals(new EditCommand(INDEX_FIRST_EMPLOYEE, descriptor), command);
    }

    @Test
    public void parseCommand_exit() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD + " 3") instanceof ExitCommand);
    }

    @Test
    public void parseCommand_findEmployee() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        FindEmployeeCommand command = (FindEmployeeCommand) parser.parseCommand(
                FindEmployeeCommand.COMMAND_WORD + " " + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new FindEmployeeCommand(new EmployeeNameContainsKeywordsPredicate(keywords)), command);
    }

    @Test
    public void parseCommand_findProject() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        FindProjectCommand command = (FindProjectCommand) parser.parseCommand(
                FindProjectCommand.COMMAND_WORD + " " + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new FindProjectCommand(new ProjectNameContainsKeywordsPredicate(keywords)), command);
    }

    @Test
    public void parseCommand_help() throws Exception {
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD) instanceof HelpCommand);
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD + " 3") instanceof HelpCommand);
    }

    @Test
    public void parseCommand_listEmployee() throws Exception {
        assertTrue(parser.parseCommand(ListEmployeeCommand.COMMAND_WORD) instanceof ListEmployeeCommand);
        assertTrue(parser.parseCommand(ListEmployeeCommand.COMMAND_WORD + " 3") instanceof ListEmployeeCommand);
    }

    @Test
    public void parseCommand_listProject() throws Exception {
        assertTrue(parser.parseCommand(ListProjectCommand.COMMAND_WORD) instanceof ListProjectCommand);
        assertTrue(parser.parseCommand(ListProjectCommand.COMMAND_WORD + " 3") instanceof ListProjectCommand);
    }

    @Test
    public void parseCommand_assignEmployeeToProject() throws Exception {
        AssignEmployeeCommand command =
                (AssignEmployeeCommand) parser.parseCommand(AssignEmployeeCommand.COMMAND_WORD + " "
                 + PREFIX_PROJECT + INDEX_FIRST_EMPLOYEE.getOneBased() + " " + PREFIX_EMPLOYEE
                        + INDEX_FIRST_EMPLOYEE.getOneBased());
        assertEquals(new AssignEmployeeCommand(INDEX_FIRST_EMPLOYEE,
                                                new ArrayList<>(Arrays.asList(INDEX_FIRST_EMPLOYEE))), command);
    }

    @Test
    public void parseCommand_projectDeadline() throws Exception {
        final Deadline deadline = new Deadline("12-10-2022");
        ProjectDeadlineCommand command =
                (ProjectDeadlineCommand) parser.parseCommand(ProjectDeadlineCommand.COMMAND_WORD + " "
                 + INDEX_FIRST_PROJECT.getOneBased() + " " + PREFIX_DEADLINE + deadline.value);
        assertEquals(new ProjectDeadlineCommand(INDEX_FIRST_PROJECT, deadline), command);
    }

    @Test
    public void parseCommand_newProject() throws Exception {
        AddProjectCommand command = (AddProjectCommand) parser.parseCommand(AddProjectCommand.COMMAND_WORD + " "
                            + PREFIX_PROJECT + "Alpha");
        AddProjectCommand expected = new AddProjectCommand(new Project("Alpha"), new ArrayList<>());
        assertEquals(expected, command);
    }

    @Test
    public void parseCommand_unrecognisedInput_throwsParseException() {
        assertThrows(ParseException.class, String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE), ()
                -> parser.parseCommand(""));
    }

    @Test
    public void parseCommand_unknownCommand_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_UNKNOWN_COMMAND, () -> parser.parseCommand("unknownCommand"));
    }
}