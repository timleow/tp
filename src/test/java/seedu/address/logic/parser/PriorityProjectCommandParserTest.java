package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.PROJECT_PRIORITY_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PRIORITY_AMY;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PROJECT;

import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.PriorityProjectCommand;
import seedu.address.model.project.Priority;

public class PriorityProjectCommandParserTest {
    private PriorityProjectCommandParser parser = new PriorityProjectCommandParser();
    @Test
    public void parse_allFieldsPresent_success() {
        Priority priority = new Priority("high");
        Index targetIndex = INDEX_FIRST_PROJECT;
        String userInput = INDEX_FIRST_PROJECT.getOneBased() + PROJECT_PRIORITY_DESC_AMY;
        assertParseSuccess(parser, userInput,
                new PriorityProjectCommand(priority, List.of(targetIndex)));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                PriorityProjectCommand.MESSAGE_USAGE));

        // missing priority prefix
        assertParseFailure(parser, INDEX_FIRST_PROJECT.getOneBased() + " "
                + VALID_PRIORITY_AMY + " ", expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, INDEX_FIRST_PROJECT.getOneBased() + " "
                + VALID_PRIORITY_AMY + " ", expectedMessage);
    }
}
