#!/bin/sh

ACCOUNT_ID=1
GENERAL_OPTS="--no-create-info --skip-add-locks --skip-triggers"

# dump meta-Account tables
mysqldump -u root tm $GENERAL_OPTS general_Product > products.sql
mysqldump -u root tm $GENERAL_OPTS account_Account --where="id = $ACCOUNT_ID" > account.sql
mysqldump -u root tm $GENERAL_OPTS account_AccountProduct account_AccountProductPeriod account_PurchaseOrder --where="account_id = $ACCOUNT_ID" > general.sql

# dump main AccountEntities without relation tables
mysqldump -u root tm $GENERAL_OPTS tm_approach_Release tm_Defect tm_DefectStatus tm_Filter tm_FilterConstraint tm_Instance tm_InstanceParam tm_Project tm_ProjectCategory tm_ProjectRole tm_ProjectTreeNode tm_ProjectWidget tm_Requirement tm_RequirementFolder tm_test_Run tm_test_RunParam tm_test_RunStep tm_test_Script tm_test_ScriptFolder tm_test_ScriptParam tm_test_ScriptStep tm_TMUser tm_test_Tag tm_approach_TestCycle account_User tm_TMUserWidget --where="account_id = $ACCOUNT_ID" > accountEntities.sql

# dump association tables, one by one
mysqldump -u root --lock-all-tables tm $GENERAL_OPTS tm_Defect_tm_test_Tag --where="Defect_id in (select id from Defect where account_id = $ACCOUNT_ID)" > defect_tag.sql
mysqldump -u root --lock-all-tables tm $GENERAL_OPTS tm_Filter_tm_FilterConstraint --where="Filter_id in (select id from Filter where account_id = $ACCOUNT_ID)" > filter_filterConstraint.sql
mysqldump -u root --lock-all-tables tm $GENERAL_OPTS tm_test_Instance_tm_Defect --where="Instance_id in (select id from Instance where account_id = $ACCOUNT_ID)" > instance_defect.sql
mysqldump -u root --lock-all-tables tm $GENERAL_OPTS tm_test_Instance_tm_test_Tag --where="Instance_id in (select id from Instance where account_id = $ACCOUNT_ID)" > instance_tag.sql
mysqldump -u root --lock-all-tables tm $GENERAL_OPTS tm_ProjectRole_unitRoles --where="ProjectRole_id in (select id from ProjectRole where account_id = $ACCOUNT_ID)" > projectrole_unitRoles.sql
mysqldump -u root --lock-all-tables tm $GENERAL_OPTS tm_ProjectWidget_parameters --where="ProjectWidget_id in (select id from ProjectWidget where account_id = $ACCOUNT_ID)" > projectwidget_parameters.sql
mysqldump -u root --lock-all-tables tm $GENERAL_OPTS tm_Requirement_tm_test_Script --where="Requirement_id in (select id from Requirement where account_id = $ACCOUNT_ID)" > requirement_script.sql
mysqldump -u root --lock-all-tables tm $GENERAL_OPTS tm_Requirement_tm_test_Tag --where="Requirement_id in (select id from Requirement where account_id = $ACCOUNT_ID)" > requirement_tag.sql
mysqldump -u root --lock-all-tables tm $GENERAL_OPTS tm_test_Script_tm_test_Tag --where="Script_id in (select id from Script where account_id = $ACCOUNT_ID)" > script_tag.sql
mysqldump -u root --lock-all-tables tm $GENERAL_OPTS tm_TMUser_tm_ProjectRole --where="TMUser_id in (select id from TMUser where account_id = $ACCOUNT_ID)" > tmuser_projectRole.sql
mysqldump -u root --lock-all-tables tm $GENERAL_OPTS tm_TMUser_accountRoles --where="TMUser_id in (select id from TMUser where account_id = $ACCOUNT_ID)" > tmuser_accountRoles.sql

rm conf/initial-data-demo.sql
cat products.sql account.sql general.sql accountEntities.sql defect_tag.sql filter_filterConstraint.sql instance_defect.sql instance_tag.sql projectrole_unitRoles.sql projectwidget_parameters.sql requirement_script.sql requirement_tag.sql script_tag.sql tmuser_projectRole.sql tmuser_accountRoles.sql > conf/initial-data-demo.sql

rm -f products.sql account.sql general.sql accountEntities.sql defect_tag.sql filter_filterConstraint.sql instance_defect.sql instance_tag.sql projectrole_unitRoles.sql projectwidget_parameters.sql requirement_script.sql requirement_tag.sql script_tag.sql tmuser_projectRole.sql tmuser_accountRoles.sql