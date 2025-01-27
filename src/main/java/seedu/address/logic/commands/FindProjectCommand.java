package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.employee.EmployeeUnderFilteredProjectsPredicate;
import seedu.address.model.project.ProjectNameContainsKeywordsPredicate;

/**
 * Finds and lists all projects in TaskHub whose name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FindProjectCommand extends Command {

    public static final String COMMAND_WORD = "findP";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all projects whose names contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " Deployment Website";

    private final ProjectNameContainsKeywordsPredicate predicate;

    public FindProjectCommand(ProjectNameContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredProjectList(predicate);

        // update employee list to contain only employees under the filtered projects
        final EmployeeUnderFilteredProjectsPredicate employeeUnderFilteredProjectsPredicate =
                new EmployeeUnderFilteredProjectsPredicate(model.getFilteredProjectList());
        model.updateFilteredEmployeeList(employeeUnderFilteredProjectsPredicate);

        return new CommandResult(
                String.format(Messages.MESSAGE_PROJECTS_LISTED_OVERVIEW, model.getFilteredProjectList().size()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof FindProjectCommand)) {
            return false;
        }

        FindProjectCommand otherFindProjectCommand = (FindProjectCommand) other;
        return predicate.equals(otherFindProjectCommand.predicate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicate", predicate)
                .toString();
    }

}
