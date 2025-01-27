package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_EMPLOYEE;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.DeleteProjectCommand;

public class DeleteProjectCommandParserTest {
    private DeleteProjectCommandParser parser = new DeleteProjectCommandParser();

    @Test
    public void parse_validArgs_returnsDeleteEmployeeCommand() {
        assertParseSuccess(parser, "1", new DeleteProjectCommand(INDEX_FIRST_EMPLOYEE));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteProjectCommand.MESSAGE_USAGE));
    }
}
