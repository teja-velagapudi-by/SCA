package com.jda.dss.analysis.rules;

import com.blueyonder.wm.csi.core.LocalSyntaxCompiler;
import com.redprairie.moca.analysis.Issue;
import com.redprairie.moca.analysis.IssueLevel;
import com.redprairie.moca.analysis.RuleSet;
import com.redprairie.moca.analysis.StaticAnalysisRule;
import com.redprairie.moca.server.exec.CommandUnit;
import com.redprairie.moca.server.repository.LocalSyntaxCommand;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class SelectCheckRule implements RuleSet {
    @StaticAnalysisRule(id = "Select (*) check")
    public Issue execute(LocalSyntaxCommand command){
        final Pattern pattern = Pattern.compile("^(\\bselect\\b\\s+\\*)");
        final String name = command.getName();
        final LocalSyntaxCompiler COMPILER = new LocalSyntaxCompiler();
        final String message = "Please Follow Proper SQL checks:- ";
        List<CommandUnit> units = COMPILER.compile(command);

        for (CommandUnit unit : units) {
            if (StringUtils.isNotBlank(unit.getSql())) {
                Matcher matcher = pattern.matcher(unit.getSql().toLowerCase());
                if (matcher.find()) {
                    String description = "Please specify the columns in the select clause instead of using * in the Command: ";
                    return Issue.newIssue(message + description + name, IssueLevel.INFO);
                }
            }
        }

        return Issue.noIssue();

    }
}
