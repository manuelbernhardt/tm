package controllers;

import java.util.List;
import javax.persistence.Query;

import controllers.tabularasa.TableController;
import models.tm.test.Instance;
import models.tm.test.InstanceParam;
import models.tm.test.Script;
import models.tm.test.ScriptParam;
import models.tm.test.ScriptStep;
import org.apache.commons.lang.StringEscapeUtils;
import play.data.validation.Valid;
import play.data.validation.Validation;
import play.db.jpa.GenericModel;
import play.db.jpa.JPA;

/**
 * @author Manuel Bernhardt <bernhardt.manuel@gmail.com>
 */
public class Repository extends TMController {

    public static void index() {
        render();
    }

    public static void scriptDetailsData(Long baseObjectId, String[] fields) {
        Object base = Lookups.getScript(baseObjectId);
        renderFields(base, fields);
    }

    public static void stepDetailsData(Long scriptId, Long baseObjectId, String[] fields) {
        Script script = Lookups.getTestScript(scriptId);
        // TODO do this right
        ScriptStep step = (baseObjectId != null ? ScriptStep.<ScriptStep>findById(baseObjectId) : null);
        renderFields(step, fields);
    }


    public static void content(Long scriptNodeId) {
        Script script = getScript(scriptNodeId);
        render(script);
    }

    public static void scriptDetails(Long scriptId) {
        Script script = Lookups.getTestScript(scriptId);
        render(script);
    }

    public static void steps(Long scriptId) {
        Script script = Lookups.getTestScript(scriptId);
        render(script);
    }

    public static void linked(Long scriptId) {
        Script script = Lookups.getTestScript(scriptId);
        render(script);
    }

    public static void editScript(@Valid Script script) {
        checkInAccount(script);
        if (Validation.hasErrors()) {
            // TODO handle validation errors in view somehow
            error();
        }
        script.save();
        ok();
    }

    public static void stepDialog(Long scriptId, Long stepId) {
        Script script = Lookups.getTestScript(scriptId);
        // TODO do this right
        ScriptStep step = (stepId != null ? ScriptStep.<ScriptStep>findById(stepId) : null);
        render("Repository/stepForm.html", script, step);

    }

    public static void createOrUpdateStep(@Valid ScriptStep step, Long scriptId) {
        if (scriptId == null) {
            error();
        }
        Script script = Script.findById(scriptId);
        if (script == null) {
            notFound();
        } else {
            checkInAccount(script);

            // unescape HTML
            step.description = StringEscapeUtils.unescapeHtml(step.description);
            step.expectedResult = StringEscapeUtils.unescapeHtml(step.expectedResult);

            // create parameters
            List<ScriptParam> scriptParameters = ParameterHandler.getScriptParameters(step.description, script);
            scriptParameters.addAll(ParameterHandler.getScriptParameters(step.expectedResult, script));

            // create only the new ones
            for(ScriptParam p : scriptParameters) {
                if(ScriptParam.count("from ScriptParam p where p.name = ? and p.script = ?", p.name, script) == 0) {
                    p.create();
                    // propagate creation of a new param to all instances of this script
                    for(Instance i : script.getInstances()) {
                        InstanceParam ip = new InstanceParam();
                        ip.project = i.project;
                        ip.instance = i;
                        ip.scriptParam = p;
                        ip.create();
                    }
                }
            }

            // for new steps only
            if(step.id == null) {
                step.project = TMController.getActiveProject();
                step.script = script;
            }

            // what's your favourite position?
            if(ScriptStep.count("script.id = ? and position = ?", script.getId(), step.position) > 0) {
                Query query = JPA.em().createQuery("update ScriptStep s set s.position = s.position + 1 where s.script.id = :scriptId and s.position > :position");
                query.setParameter("scriptId", script.getId());
                query.setParameter("position", step.position - 1);
                query.executeUpdate();
            }

            // TODO is there not a "createOrUpdate"
            if(step.id == null) {
                boolean created = step.create();
                if (!created) {
                    error();
                } else {
                    ok();
                }
            } else {
                step.save();
                ok();
            }
        }
    }

    public static void stepData(String tableId, Long scriptId,
                                Integer iDisplayStart,
                                Integer iDisplayLength,
                                String sColumns,
                                String sEcho,
                                String sSearch) {
        GenericModel.JPAQuery query = ScriptStep.find("script.id = ? order by position", scriptId).from(iDisplayStart == null ? 0 : iDisplayStart);
        List<ScriptStep> steps = query.fetch(iDisplayLength == null ? 10 : iDisplayLength);
        long totalRecords = ScriptStep.count("script.id", scriptId);
        TableController.renderJSON(steps, ScriptStep.class, totalRecords, sColumns, sEcho);
    }


    private static Script getScript(Long scriptId) {
        if (scriptId == null) {
            return null;
        }
        Script script = Script.findById(scriptId);
        if (script == null) {
            return null;
        }
        checkInAccount(script);
        return script;
    }

}
