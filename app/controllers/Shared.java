package controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import models.project.test.Script;
import models.project.test.ScriptParam;

/**
 * @author Manuel Bernhardt <bernhardt.manuel@gmail.com>
 */
public class Shared extends TMController {

    private static final Pattern parameterPattern = Pattern.compile("<<<[^>>>]*>>>");

    public static List<String> getParameterNames(String text) {
        List<String> res = new ArrayList<String>();
        Matcher matcher = parameterPattern.matcher(text);
        while (matcher.find()) {
            String name = text.substring(matcher.start() + 3, matcher.end() - 3);
            res.add(name);
        }
        return res;
    }

    /**
     * Creates new {@link ScriptParam} instances without persisting them based on a text in which the parameters appear
     *
     * @param text the text to look for parameters, denoted with <<< >>>
     * @return a List of non-persisted parameter instances
     */
    public static List<ScriptParam> getScriptParameters(String text, Script script) {
        List<ScriptParam> res = new ArrayList<ScriptParam>();
        for (String p : getParameterNames(text)) {
            ScriptParam param = new ScriptParam();
            param.project = script.project;
            param.script = script;
            param.name = p;
            res.add(param);
        }
        return res;
    }

    public static void main(String[] args) {
        String s = "this is some <<<param>>> with something";
        Matcher matcher = parameterPattern.matcher(s);
        while(matcher.find()) {
            System.out.println(matcher.group());
        }
        System.out.println(getParameterNames(s));
    }

}
