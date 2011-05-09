package controllers;

import java.util.List;

import controllers.tabularasa.TableController;
import models.tm.Defect;
import models.tm.DefectStatus;
import models.tm.TMUser;
import play.data.validation.Valid;

/**
 * @author Manuel Bernhardt <bernhardt.manuel@gmail.com>
 */
public class Defects extends TMController {

    public  static void index(){
        render();
    }

    public static void defects(String tableId, int iDisplayStart, int iDisplayLength, String sColumns, String sEcho){
        List<Defect> defects = Defect.find("order by id").fetch();
        TableController.renderJSON(defects, Defect.class, defects.size(), sColumns, sEcho);
    }

    public static void createDefect(@Valid Defect defect){
        defect.account = getConnectedUserAccount();
        defect.project = getActiveProject();
        defect.create();
        ok();
    }

    public static void  updateDefect(@Valid Defect defect){
        Defect d = Defect.findById(defect.id);
        d.name = defect.name;
        d.assignedTo = defect.assignedTo;
        d.status = defect.status;
        d.save();
        ok();
    }

    public static void defectDetails(Long baseObjectId, String[] fields){
        Defect defect = Defect.findById(baseObjectId);
        renderFields(defect, fields);
    }
}
