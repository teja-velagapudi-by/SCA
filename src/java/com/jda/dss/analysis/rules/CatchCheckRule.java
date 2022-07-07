package com.jda.dss.analysis.rules;


import com.redprairie.moca.analysis.Issue;
import com.redprairie.moca.analysis.IssueLevel;
import com.redprairie.moca.analysis.RuleSet;
import com.redprairie.moca.analysis.StaticAnalysisRule;
import com.redprairie.moca.server.repository.LocalSyntaxCommand;
import org.springframework.stereotype.Component;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class CatchCheckRule implements RuleSet {
    @StaticAnalysisRule(id = "Catch(@?) check rule")
    public Issue execute(LocalSyntaxCommand command) {

        final Pattern pattern = Pattern.compile("\\bcatch\\b\\(@\\?\\)");
        final String name = command.getName();
        final String message = "Please Follow Proper SQL checks:- ";

                Matcher matcher = pattern.matcher(command.getSyntax().toLowerCase());
                if (matcher.find()) {
                    String description = "Please avoid using catch(@?) until necessary in the Command: ";
                    return Issue.newIssue(message + description + name, IssueLevel.INFO);
                }


        return Issue.noIssue();
    }

}
