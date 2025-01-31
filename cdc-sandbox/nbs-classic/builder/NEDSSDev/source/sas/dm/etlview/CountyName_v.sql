Create view CountyName_v as
SELECT     code as county,
		code_desc_txt as countyname,
		parent_is_cd as state
FROM         nbs_srtd..State_county_code_value
WHERE     (Indent_level_nbr = 2)

