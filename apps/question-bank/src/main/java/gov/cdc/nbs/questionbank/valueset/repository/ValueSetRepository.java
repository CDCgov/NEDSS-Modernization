package gov.cdc.nbs.questionbank.valueset.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import gov.cdc.nbs.questionbank.entity.CodeSet;
import gov.cdc.nbs.questionbank.entity.CodeSetId;

public interface ValueSetRepository extends JpaRepository <CodeSet,CodeSetId> {
	
	
 @Query("SELECT count(*) FROM nbs_srte..Codeset WHERE CODE_SET_NM =:name AND CLASS_CD = 'code_value_general'")
 long  checkValueSetName(@Param("name")String name);
 
 
 @Query("SELECT code_set_group_id FROM nbs_srte..CODESET WHERE code_set_group_id > 99900")
 long getCodeSetGroupCeilID();
 
 @Query("SELECT MAX(code_set_group_id) FROM nbs_srte..Codeset_Group_Metadata")
 long getCodeSetGroupMaxID();
 
 

}
