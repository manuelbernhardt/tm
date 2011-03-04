package tags;

import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import controllers.CRUD;
import groovy.lang.Closure;
import play.data.validation.Email;
import play.data.validation.Max;
import play.data.validation.MaxSize;
import play.data.validation.Min;
import play.data.validation.MinSize;
import play.data.validation.Range;
import play.data.validation.Required;
import play.data.validation.URL;
import play.data.validation.Validation;
import play.db.Model;
import play.i18n.Messages;
import play.libs.F;
import play.mvc.Scope;
import play.templates.FastTags;
import play.templates.GroovyTemplate;
import play.templates.JavaExtensions;

/**
 * Custom FastTags, mostly copied together from Play! and from the jquery validation module, with a few modifications
 * so they can be used in CRUD-like tags for form generation.
 *
 * @author Manuel Bernhardt <bernhardt.manuel@gmail.com>
 */
@FastTags.Namespace("forms")
public class FormTags extends FastTags {

    /**
     * The field tag is a helper, based on the spirit of Don't Repeat Yourself.
     *
     * @param args     tag attributes
     * @param body     tag inner body
     * @param out      the output writer
     * @param template enclosing template
     * @param fromLine template line number where the tag is defined
     */
    public static void _field(Map<?, ?> args, Closure body, PrintWriter out, GroovyTemplate.ExecutableTemplate template, int fromLine) {
        Map<String, Object> field = new HashMap<String, Object>();
        String path = args.get("field").toString();
        field.put("name", path);
        field.put("id", path.replace('.', '_'));
        field.put("flash", Scope.Flash.current().get(path));
        field.put("flashArray", field.get("flash") != null && !field.get("flash").toString().isEmpty() ? field.get("flash").toString().split(",") : new String[0]);
        field.put("error", Validation.error(path));
        field.put("errorClass", field.get("error") != null ? "hasError" : "");
        String[] pieces = path.split("\\.");
        Object obj = args.get("baseObject");
        if (obj != null) {
            if (pieces.length > 1) {
                    try {
                        F.Tuple<Field, Object> fo = getField(pieces, obj);
                        if (fo != null) {
                            field.put("validationData", buildValidationDataString(fo._1));

                            try {
                                Method getter = obj.getClass().getMethod("get" + JavaExtensions.capFirst(fo._1.getName()));
                                field.put("value", getter.invoke(obj, new Object[0]));
                            } catch (NoSuchMethodException e) {
                                field.put("value", fo._1.get(fo._2).toString());
                            }
                        }
                    } catch (Exception e) {
                        // if there is a problem reading the field we dont set any value
                    }
                }
            } else {
                field.put("value", obj);
            }
        body.setProperty("field", field);
        body.call();
    }

    private static F.Tuple<Field, Object> getField(String[] pieces, Object baseObject) throws NoSuchFieldException, IllegalAccessException {
        if (pieces.length > 1) {
            for (int i = 1; i < pieces.length; i++) {
                Field f = baseObject.getClass().getField(pieces[i]);
                if (i == (pieces.length - 1)) {
                    return new F.Tuple<Field, Object>(f, baseObject);
                } else {
                    baseObject = f.get(baseObject);
                }
            }
        }
        return null;
    }

    public static void _type(Map<?, ?> args, Closure body, PrintWriter out, GroovyTemplate.ExecutableTemplate template, int fromLine) {
        String path = args.get("field").toString();
        String[] pieces = path.split("\\.");
        Object obj = args.get("baseObject");
        if (obj != null) {
            if (pieces.length > 1) {
                try {
                    F.Tuple<Field, Object> fo = getField(pieces, obj);
                    if (fo != null) {
                        Model.Property property = new Model.Property();
                        property.field = fo._1;
                        property.isGenerated = false;
                        CRUD.ObjectType.ObjectField type = new CRUD.ObjectType.ObjectField(property);
                        body.setProperty("type", type.type);
                    }
                } catch(Exception e) {
                    e.printStackTrace();
                }

            } else {
                body.setProperty("type", obj.getClass().getName());
            }
        }
        body.call();
    }

    private static String buildValidationDataString(Field f) throws Exception {
        StringBuilder result = new StringBuilder("{");
        List<String> rules = new ArrayList<String>();
        Map<String, String> messages = new HashMap<String, String>();
        Required required = f.getAnnotation(Required.class);
        if (required != null) {
            rules.add("required:true");
            if (required.message() != null) {
                messages.put("required", Messages.get(required.message()));
            }
        }
        Min min = f.getAnnotation(Min.class);
        if (min != null) {
            rules.add("min:" + new Double(min.value()).toString());
            if (min.message() != null) {
                messages.put("min", Messages.get(min.message(), null, min.value()));
            }
        }
        Max max = f.getAnnotation(Max.class);
        if (max != null) {
            rules.add("max:" + new Double(max.value()).toString());
            if (max.message() != null) {
                messages.put("max", Messages.get(max.message(), null, max.value()));
            }
        }
        Range range = f.getAnnotation(Range.class);
        if (range != null) {
            rules.add("range:[" + new Double(range.min()).toString() + ", " + new Double(range.max()).toString() + "]");
            if (range.message() != null) {
                messages.put("range", Messages.get(range.message(), null, range.min(), range.max()));
            }
        }
        MaxSize maxSize = f.getAnnotation(MaxSize.class);
        if (maxSize != null) {
            rules.add("maxlength:" + new Integer(maxSize.value()).toString());
            if (maxSize.message() != null) {
                messages.put("maxlength", Messages.get(maxSize.message(), null, maxSize.value()));
            }
        }
        MinSize minSize = f.getAnnotation(MinSize.class);
        if (minSize != null) {
            rules.add("minlength:" + new Integer(minSize.value()).toString());
            if (minSize.message() != null) {
                messages.put("minlength", Messages.get(minSize.message(), null, minSize.value()));
            }
        }
        URL url = f.getAnnotation(URL.class);
        if (url != null) {
            rules.add("url:true");
            if (url.message() != null) {
                messages.put("url", Messages.get(url.message()));
            }
        }
        Email email = f.getAnnotation(Email.class);
        if (email != null) {
            rules.add("email:true");
            if (email.message() != null) {
                messages.put("email", Messages.get(email.message()));
            }
        }
        if (rules.size() > 0) {
            boolean first = true;
            for (String rule : rules) {
                if (first) {
                    first = false;
                } else {
                    result.append(",");
                }
                result.append(rule);
            }
        }
        if (messages.size() > 0) {
            result.append(",messages:{");
            boolean first = true;
            for (String key : messages.keySet()) {
                if (first) {
                    first = false;
                } else {
                    result.append(",");
                }
                result.append("\"");
                result.append(key);
                result.append("\"");
                result.append(":");
                result.append("\"");
                result.append(messages.get(key));
                result.append("\"");
            }
            result.append("}");
        }
        result.append("}");
        return result.toString();
    }

}
