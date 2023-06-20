package gov.cdc.nbs.questionbank.valueset.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import gov.cdc.nbs.questionbank.entity.CodeSet;
import gov.cdc.nbs.questionbank.entity.CodeSetId;

public interface ValueSetRepository extends JpaRepository <CodeSet,CodeSetId> {
	
	
 @Query("SELECT count(*) FROM CodeSet WHERE codeSetGroup.codeSetNm =:name AND id.classCd = 'code_value_general'")
 long  checkValueSetName(@Param("name")String name);
 
 
 @Query("SELECT count(codeSetGroup.id) FROM CodeSet WHERE codeSetGroup.id > 99900")
 int getCodeSetGroupCeilID();
 

}
