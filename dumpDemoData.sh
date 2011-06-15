#!/bin/sh

ACCOUNT_ID=1
GENERAL_OPTS=--no-create-info

# dump meta-Account tables
mysqldump -u root tm $GENERAL_OPTS Product > products.sql
mysqldump -u root tm $GENERAL_OPTS Account --where="id = $ACCOUNT_ID" > account.sql
mysqldump -u root tm $GENERAL_OPTS AccountProduct AccountProductPeriod PurchaseOrder --where="account_id = $ACCOUNT_ID" > general.sql

# dump main AccountEntities without relation tables
mysqldump -u root tm $GENERAL_OPTS ApproachRelease Defect DefectStatus Filter FilterConstraint Instance InstanceParam Project ProjectCategory ProjectRole ProjectTreeNode ProjectWidget Requirement RequirementFolder Run RunParam RunStep Script ScriptFolder ScriptParam ScriptStep TMUser Tag TestCycle User UserWidget --where="account_id = $ACCOUNT_ID" > accountEntities.sql

# dump association tables, one by one
mysqldump -u root --lock-all-tables tm $GENERAL_OPTS Defect_Tag --where="Defect_id in (select id from Defect where account_id = $ACCOUNT_ID)" > defect_tag.sql
mysqldump -u root --lock-all-tables tm $GENERAL_OPTS Filter_FilterConstraint --where="Filter_id in (select id from Filter where account_id = $ACCOUNT_ID)" > filter_filterConstraint.sql
mysqldump -u root --lock-all-tables tm $GENERAL_OPTS Instance_Defect --where="Instance_id in (select id from Instance where account_id = $ACCOUNT_ID)" > instance_defect.sql
mysqldump -u root --lock-all-tables tm $GENERAL_OPTS Instance_Tag --where="Instance_id in (select id from Instance where account_id = $ACCOUNT_ID)" > instance_tag.sql
mysqldump -u root --lock-all-tables tm $GENERAL_OPTS ProjectRole_unitRoles --where="ProjectRole_id in (select id from ProjectRole where account_id = $ACCOUNT_ID)" > projectrole_unitRoles.sql
mysqldump -u root --lock-all-tables tm $GENERAL_OPTS ProjectWidget_parameters --where="ProjectWidget_id in (select id from ProjectWidget where account_id = $ACCOUNT_ID)" > projectwidget_parameters.sql
mysqldump -u root --lock-all-tables tm $GENERAL_OPTS Requirement_Script --where="Requirement_id in (select id from Requirement where account_id = $ACCOUNT_ID)" > requirement_script.sql
mysqldump -u root --lock-all-tables tm $GENERAL_OPTS Requirement_Tag --where="Requirement_id in (select id from Requirement where account_id = $ACCOUNT_ID)" > requirement_tag.sql
mysqldump -u root --lock-all-tables tm $GENERAL_OPTS Script_Tag --where="Script_id in (select id from Script where account_id = $ACCOUNT_ID)" > script_tag.sql
mysqldump -u root --lock-all-tables tm $GENERAL_OPTS TMUser_ProjectRole --where="TMUser_id in (select id from TMUser where account_id = $ACCOUNT_ID)" > tmuser_projectRole.sql
mysqldump -u root --lock-all-tables tm $GENERAL_OPTS TMUser_accountRoles --where="TMUser_id in (select id from TMUser where account_id = $ACCOUNT_ID)" > tmuser_accountRoles.sql

cat products.sql account.sql general.sql accountEntities.sql defect_tag.sql filter_filterConstraint.sql instance_defect.sql instance_tag.sql projectrole_unitRoles.sql projectwidget_parameters.sql requirement_script.sql requirement_tag.sql script_tag.sql tmuser_projectRole.sql tmuser_accountRoles.sql > tmDemoDump.sql

rm -f products.sql account.sql general.sql accountEntities.sql defect_tag.sql filter_filterConstraint.sql instance_defect.sql instance_tag.sql projectrole_unitRoles.sql projectwidget_parameters.sql requirement_script.sql requirement_tag.sql script_tag.sql tmuser_projectRole.sql tmuser_accountRoles.sql