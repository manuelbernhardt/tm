@FilterDef(name="account", parameters= { @ParamDef( name="account_id", type="long") }, defaultCondition=":account_id = account_id")
package models;

import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;