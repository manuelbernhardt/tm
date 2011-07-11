@FilterDefs({
        @FilterDef(name="account", parameters= { @ParamDef( name="account_id", type="long") }, defaultCondition=":account_id = account_id"),
        @FilterDef(name="activeUser", parameters= { @ParamDef( name="active", type="boolean") }, defaultCondition=":active = active"),
        @FilterDef(name="activeTMUser", parameters= { @ParamDef( name="active", type="boolean") }, defaultCondition=":active = (select u.active from account_User u where user_id = u.id)")
})
package models;

import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.FilterDefs;
import org.hibernate.annotations.ParamDef;

