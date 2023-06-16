package gov.cdc.nbs.questionbank.valueset.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import gov.cdc.nbs.questionbank.entity.CodeSetGroupMetaDatum;

public interface CodesetGroupMetadatumRepository extends JpaRepository <CodeSetGroupMetaDatum, Long> {
	
	@Query("SELECT count(*) FROM CodeSetGroupMetaDatum metadata, CodeSet codeSet WHERE metadata.codeSetShortDescTxt =:codeSetShrtDescText AND codeSet.id.classCd = 'code_value_general' AND metadata.codeSetNm =:codeSetNm")
	long  checkCodeSetGrpMetaDatEntry (@Param("codeSetShrtDescText")String codeSetShrtDescText,  @Param("codeSetNm")String codeSetNm);
	
	
	 @Query("SELECT MAX(id) FROM CodeSetGroupMetaDatum")
	 long getCodeSetGroupMaxID();
	 

}
