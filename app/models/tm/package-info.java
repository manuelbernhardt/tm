@FilterDef(name="project", parameters= { @ParamDef( name="project_id", type="long") }, defaultCondition=":project_id = project_id")
package models.tm;

import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;