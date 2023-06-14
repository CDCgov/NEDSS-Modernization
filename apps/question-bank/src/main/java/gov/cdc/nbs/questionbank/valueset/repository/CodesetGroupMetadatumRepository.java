package gov.cdc.nbs.questionbank.valueset.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import gov.cdc.nbs.questionbank.entity.CodeSetGroupMetaDatum;

public interface CodesetGroupMetadatumRepository extends JpaRepository <CodeSetGroupMetaDatum, Long> {
	
	@Query("SELECT count(*) FROM nbs_srte..Codeset_Group_Metadata metadata,nbs_srte..CodeSet codeSet WHERE metadata.code_set_short_desc_txt =:codeSetShrtDescText AND codeSet.CLASS_CD = 'code_value_general' AND metadata.code_set_nm =:codeSetNm")
	long  checkCodeSetGrpMetaDatEntry (@Param("codeSetShrtDescText")String codeSetShrtDescText,  @Param("codeSetNm")String codeSetNm);

}
