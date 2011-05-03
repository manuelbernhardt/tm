package util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import models.tm.test.Param;
import models.tm.test.ParameterHolder;
import models.tm.test.RunParam;
import models.tm.test.Script;
import models.tm.test.ScriptParam;
import play.libs.Codec;

/**
 * Utility class for handling ParameterHandler in Steps.
 *
 * @author Manuel Bernhardt <bernhardt.manuel@gmail.com>
 */
public class ParameterHandler {

    private static final Pattern parameterPattern = Pattern.compile("<<<[^>>>]*>>>");
    public static final String VIEW_CLASS = "viewParam";
    public static final String EDIT_CLASS = "editParam";

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
            ScriptParam param = new ScriptParam(script.project);
            param.script = script;
            param.name = p;
            res.add(param);
        }
        return res;
    }

    /**
     * Transform a text by adding CSS classes on recognized parameters.
     *
     * @param text            the text to enhance with specific classes
     * @param parameterHolder the holder for the parameters the texts belong to
     * @return a HTML snipet with divs having the specified classes
     */
    public static String applyClass(String text, ParameterHolder parameterHolder, String classes) {
        Matcher matcher = parameterPattern.matcher(text);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            Param p = parameterHolder.getParam(extractParamName(text, matcher));

            if (p instanceof RunParam) {
                RunParam rp = (RunParam) p;
                if (rp.value == null) {
                    matcher.appendReplacement(sb, "<div id=\"" + getRunParameterId(rp) + "\" class=\"" + classes + "\">enter a value</div>");
                } else {
                    matcher.appendReplacement(sb, "<div id=\"" + getRunParameterId(rp) + "\" class=\"" + classes + "\">" + rp.value + "</div>");
                }
            } else if (p instanceof ScriptParam) {
                matcher.appendReplacement(sb, "<div class=\"viewParam\">" + ((ScriptParam) p).name + "</div>");
            } else {
                throw new RuntimeException("What are you trying to do?");
            }
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

    private static String getRunParameterId(RunParam p) {
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
