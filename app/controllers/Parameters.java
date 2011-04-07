package controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import models.tm.test.Run;
import models.tm.test.RunParam;
import models.tm.test.Script;
import models.tm.test.ScriptParam;
import play.libs.Codec;

/**
 * Utility class for handling Parameters in Steps.
 *
 * @author Manuel Bernhardt <bernhardt.manuel@gmail.com>
 */
public class Parameters {

    private static final Pattern parameterPattern = Pattern.compile("<<<[^>>>]*>>>");

    /**
     * Fetches the names of all parameters denoted by <<< tripe triangular brackets >>>
     *
     * @param text the text to search for parameters
     * @return a list of recognized, trimmed, parameter names
     */
    public static List<String> getParameterNames(String text) {
        List<String> res = new ArrayList<String>();
        Matcher matcher = parameterPattern.matcher(text);
        while (matcher.find()) {
            String name = extractParamName(text, matcher);
            res.add(name.trim());
        }
        return res;
    }

    private static String extractParamName(String text, Matcher matcher) {
        return text.substring(matcher.start() + 3, matcher.end() - 3);
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

    /**
     * Transform a text by adding the necessary CSS classes for edition to the recognized parameters.
     *
     * @param text the text to enhance with "editable" divs
     * @param run  the Run the texts belong to
     * @return a HTML snipet with "editable" divs.
     */
    public static String applyEditClass(String text, Run run) {
        return applyClass(text, run, "editStyle edit");
    }

    private static String applyClass(String text, Run run, String classes) {
        Matcher matcher = parameterPattern.matcher(text);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            RunParam p = RunParam.find("from RunParam p where p.run = ? and p.name = ?", run, extractParamName(text, matcher)).<RunParam>first();
            if (p.value == null) {
                matcher.appendReplacement(sb, "<div id=\"" + getParameterId(p) + "\" class=\"" + classes + "\">enter a value</div>");
            } else {
                matcher.appendReplacement(sb, "<div class=\"parameter\">" + p.value + "</div>");
            }
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

    private static String getParameterId(RunParam p) {
        return "param_" + p.id + "_" + Codec.UUID();
    }

    public static void main(String[] args) {
        String s = "this is some <<<param>>> with something";
        Matcher matcher = parameterPattern.matcher(s);
        while (matcher.find()) {
            System.out.println(matcher.group());
        }
        System.out.println(getParameterNames(s));
    }

}
