/* -------------------------------------------------------------

    Ajith Kallambella
    Release 1.1.4 Notes 

    This SAS program is used to perform all post ETL operations 
    just before DBLoad. At the time of execution, it can be assumed
    that RDBDATA is populated with clean data elements and is ready
    to be loaded to RDB.
    
    Some examples of Post ETL operations are - clean up, key
    transformations, building inferred views/tables, building summary
    tables, deduced resultsets, integrity verification etc. 
    Although most of these operations can be performed
    directloy on the RDB after DBLoad executes, such an approach will 
    be implicitly transactional and hence the ability to roll-back on 
    error will be compromised. On the other hand, performing post ETL 
    operations prior to DBLoad affects only the staged data(RDBDATA) 
    that is not yet written to RDB. This gives us complete control over 
    error handling, recovery and in extreme situations, even abandoning 
    DBLoad. 
    
    In terms of performance, operations on staged data(RDBDATA) 
    are faster when compared with operations on data residing in RDB. 
    Staged data is local whereas RDB access may involve network roundtrips.
    Staged data is managed by SAS using proprietary( and faster ) access
    mechanisms and indexes whereas similar operations on RDB involves
    several layers of indirection(JDBC drivers, binding, datbase bookkeeping etc)
    that add to the overhead.
    
    
   ------------------------------------------------------------- */

/* 
	Uncommenting  the block below allows macro parameters and macro execution
   	to be logged. Very useful for debugging. 
*/

/*
OPTIONS MPRINT;
OPTIONS SYMBOLGEN ;
*/


%macro UpdateRecordStatusCode(parentTable, childTable, parentKey, childKey );
 Proc SQL;
  UPDATE &childTable childAlias
   SET record_status_cd = 'INACTIVE'
   WHERE &childKey IN ( SELECT &parentKey from &parentTable parentAlias WHERE parentAlias.record_status_cd = 'INACTIVE');
 Quit;
%mend;
%UpdateRecordStatusCode (rdbdata.MORBIDITY_REPORT, rdbdata.MORBIDITY_REPORT_EVENT, morb_rpt_key, morb_rpt_key);
%UpdateRecordStatusCode (rdbdata.MORBIDITY_REPORT, rdbdata.MORB_RPT_USER_COMMENT, morb_rpt_key, morb_rpt_key);
%UpdateRecordStatusCode (rdbdata.MORBIDITY_REPORT, rdbdata.LAB_TEST_RESULT, morb_rpt_key, morb_rpt_key);

%UpdateRecordStatusCode (rdbdata.MORBIDITY_REPORT, rdbdata.TREATMENT_EVENT, morb_rpt_key, morb_rpt_key);
%UpdateRecordStatusCode (rdbdata.TREATMENT_EVENT, rdbdata.TREATMENT, treatment_key, treatment_key);

/*Proc SQL;
	update rdbdata.lab_test a 
	set record_status_cd='INACTIVE' 
	where a.lab_test_key in( 
	select b.lab_test_key 
    from rdbdata.lab_test_result b, 
	rdbdata.morbidity_report c 
	where c.morb_rpt_key = b.morb_rpt_key 
	and b.lab_test_key = a.lab_test_key 
	and c.record_status_cd = 'INACTIVE' );
Quit;
*/
Proc SQL;
	CREATE table Inactive_Morbs as ( 
	select b.lab_test_key,c.record_status_cd 
    from 	rdbdata.lab_test a,
			rdbdata.lab_test_result b, 
			rdbdata.morbidity_report c 
	where 	c.morb_rpt_key = b.morb_rpt_key 
	and 	b.lab_test_key = a.lab_test_key 
	and 	c.record_status_cd = 'INACTIVE');


	UPDATE rdbdata.lab_test 
	SET record_status_cd = 'INACTIVE'
	WHERE lab_test_key in ( 
		SELECT lab_test_key from inactive_Morbs );
	
	drop table inactive_Morbs ;





/* 
	Because lab_test table stores all descendants of a lab_report ie., OT, RT, Reflex Tests,
	successibility, drug etc., the simple macro approach( as above) will not work for record_status_cd
	updates. Therefore lab_test we use a separate stand-alone update statement. Note that all
	descendant objects reference to the main lab report through the root_ordered_test_pntr
	attribute and therefore a single update is sufficient to update the entire object tree 
	regardless of its depth.

	Also note that other lab related tables are updated *after* the main lab_test table is
	updated. Sequencing is important here in order to ensure the record_status_cd is trickled
	down to all levels.

	SAS has a limitation that in an UPDATE statement, the same table cannot participate
	more than once in a join. In order to overcome this problem, a temporary table is utilized.

*/

Proc SQL;

	CREATE table Inactive_Labs as ( 
	SELECT lab_test_uid, record_status_cd  from rdbdata.lab_test 
		WHERE lab_test_type = 'Order' 
			  and record_status_cd = 'INACTIVE');

	UPDATE rdbdata.lab_test parent
	SET record_status_cd = 'INACTIVE'
	WHERE root_ordered_test_pntr IN ( 
		SELECT lab_test_uid from inactive_labs );
	
	drop table inactive_labs ;

Quit;
%UpdateRecordStatusCode (rdbdata.LAB_TEST, rdbdata.LAB_RPT_USER_COMMENT, lab_test_key, lab_test_key);
%UpdateRecordStatusCode (rdbdata.LAB_TEST, rdbdata.LAB_TEST_RESULT, lab_test_key, lab_test_key);
%UpdateRecordStatusCode (rdbdata.LAB_TEST_RESULT, rdbdata.LAB_RESULT_VAL, test_result_grp_key, test_result_grp_key);
%UpdateRecordStatusCode (rdbdata.LAB_TEST_RESULT, rdbdata.LAB_RESULT_COMMENT, result_comment_grp_key, result_comment_grp_key);



