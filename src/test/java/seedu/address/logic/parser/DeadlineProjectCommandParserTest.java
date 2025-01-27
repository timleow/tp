package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DEADLINE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PROJECT;

import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeadlineProjectCommand;
import seedu.address.model.project.Deadline;

public class DeadlineProjectCommandParserTest {
    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeadlineProjectCommand.MESSAGE_USAGE);

    private DeadlineProjectCommandParser parser = new DeadlineProjectCommandParser();
    private final String validDeadline = "21-02-2023";


    @Test
    public void parse_validArgs_success() {
        Index targetIndex = INDEX_FIRST_PROJECT;
        String userInput = targetIndex.getOneBased() + " " + PREFIX_DEADLINE + validDeadline;
        DeadlineProjectCommand expectedCommand = new DeadlineProjectCommand(List.of(INDEX_FIRST_PROJECT),
                new Deadline(validDeadline));
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_missingDeadlineDate_success() {
        String userInput = INDEX_FIRST_PROJECT.getOneBased() + " " + PREFIX_DEADLINE + "";
        DeadlineProjectCommand expectedCommand = new DeadlineProjectCommand(List.of(INDEX_FIRST_PROJECT),
                new Deadline(""));
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_missingDeadlinePrefix_failure() {
        String userInput = INDEX_FIRST_PROJECT.getOneBased() + " " + validDeadline;
        assertParseFailure(parser, userInput, MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidDeadline_failure() {
        // Invalid date format
        String userInput = INDEX_FIRST_PROJECT.getOneBased() + " " + PREFIX_DEADLINE + "21/02/2023";
        assertParseFailure(parser, userInput, Deadline.MESSAGE_CONSTRAINTS);

        // Invalid date value
        userInput = INDEX_FIRST_PROJECT.getOneBased() + " " + PREFIX_DEADLINE + "32-02-2023";
        assertParseFailure(parser, userInput, Deadline.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_invalidIndex_failure() {
        // Invalid index
        String userInput = "-1" + " " + PREFIX_DEADLINE + "28-02-2023";
        assertParseFailure(parser, userInput, String.format(ParserUtil.MESSAGE_INVALID_INDEX, "-1"));
    }
}
