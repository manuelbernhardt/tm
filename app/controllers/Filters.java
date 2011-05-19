package controllers;

import controllers.deadbolt.Restrict;
import models.general.UnitRole;
import models.tm.Filter;
import models.tm.FilterConstraint;
import play.mvc.Util;

import java.util.List;

/**
 *  nikola
 * Date: 5/19/11
 * Time: 5:57 PM
 */
public class Filters extends TMController{

    @Util
    public static void saveFilter(String name,
                           String entity,
                           String filterParams){
        Filter filter = null;
        filter.name = name;
        filter.entity = entity;


    }
}
