package com.jda.dss.analysis.rules;

import com.blueyonder.wm.csi.core.LocalSyntaxCompiler;
import com.redprairie.moca.analysis.Issue;
import com.redprairie.moca.analysis.IssueLevel;
import com.redprairie.moca.analysis.StaticAnalysisRule;
import com.redprairie.moca.server.exec.CommandUnit;

import com.redprairie.moca.server.repository.LocalSyntaxCommand;
import com.redprairie.moca.analysis.RuleSet;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Component
public class SqlExtractionFromMocaRule implements RuleSet {
    @StaticAnalysisRule(id = "Sql Extraction From MOCA Rule")
    public Issue execute(LocalSyntaxCommand command) {

        List<String> sqlStatement = new ArrayList<>();
        final Pattern JOIN_TABLE_PATTERN = Pattern.compile("[ /]join ([a-z0-9_ ]+)");
        int count = 0;

        final LocalSyntaxCompiler COMPILER = new LocalSyntaxCompiler();
        List<CommandUnit> units = COMPILER.compile(command);
        for (CommandUnit unit : units) {
            if (StringUtils.isNotBlank(unit.getSql())) {
                sqlStatement.add(unit.getSql());


            }
        }

        /*
        Validate SQL
        */

        /* Count joins */
        for (String cmd : sqlStatement) {
            Matcher matcher = JOIN_TABLE_PATTERN.matcher(cmd);
            while (matcher.find()) {
                count++;
            }
        }

        return Issue.newIssue("SQL RULE CHANGED", IssueLevel.WARN);


    }
}
