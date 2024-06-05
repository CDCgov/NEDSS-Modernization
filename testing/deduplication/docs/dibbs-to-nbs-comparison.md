<!-- omit in toc --> 
# DIBBS MPI to NBS field mappings 
- [About](#about)
- [First Name](#first-name)
- [Last Name](#last-name)
- [BirthDate](#birthdate)
- [Address](#address)
- [Zip](#zip)
- [City](#city)
- [State](#state)
- [Sex](#sex)
- [MRN](#mrn)

## About
The DIBBS algorithm comes out of the box with its own MPI (master patient index) that it automatically inserts patient data into as requests are processed. The new NBS 7 deduplication algorithm will need to work against the existing NBS 6 database and defer record inserts.

The following is a break down of every field supported by DIBBS, where the data is stored in NBS, and a sample query that can be used to pull all the data from NBS .

## First Name

|DIBBS Field | NBS 6 Field|
|----|----|
|`Patient.name.given`| `person_name.first_nm`|

```SQL
SELECT DISTINCT
	pn.first_nm,
	p.person_parent_uid
FROM
	person_name pn
	JOIN person p ON p.person_uid = pn.person_uid
WHERE
	pn.record_status_cd = 'ACTIVE'
	AND pn.nm_use_cd = 'L'
  AND p.cd = 'PAT'
```

## Last Name
|DIBBS Field | NBS 6 Field|
|----|----|
|`Patient.name.family`| `person_name.last_nm`|

```SQL
SELECT DISTINCT
	pn.last_nm,
	p.person_parent_uid
FROM
	person_name pn
	JOIN person p ON p.person_uid = pn.person_uid
WHERE
	pn.record_status_cd = 'ACTIVE'
	AND pn.nm_use_cd = 'L'
  AND p.cd = 'PAT'
```

## BirthDate
|DIBBS Field | NBS 6 Field|
|----|----|
|`Patient.birthDate`| `person.birth_time`|

```SQL
SELECT DISTINCT
	birth_time,
	person_parent_uid
FROM
	person
WHERE
	record_status_cd = 'ACTIVE'
	AND cd = 'PAT';
```

## Address
|DIBBS Field | NBS 6 Field|
|----|----|
|`Patient.address.line`| `postal_locator.street_addr1`|

```SQL
SELECT DISTINCT
	street_addr1,
	p.person_parent_uid
FROM
	postal_locator pl
	JOIN Entity_locator_participation elp ON elp.locator_uid = pl.postal_locator_uid
	JOIN person p ON elp.entity_uid = p.person_uid
WHERE
	elp.record_status_cd = 'ACTIVE'
  AND p.cd = 'PAT';
```

## Zip
|DIBBS Field | NBS 6 Field|
|----|----|
|`Patient.address.postalCode`| `postal_locator.zip_cd`|

```SQL
SELECT DISTINCT
	pl.zip_cd,
	p.person_parent_uid
FROM
	postal_locator pl
	JOIN Entity_locator_participation elp ON elp.locator_uid = pl.postal_locator_uid
	JOIN person p ON elp.entity_uid = p.person_uid
WHERE
	elp.record_status_cd = 'ACTIVE'
  AND p.cd = 'PAT';
```

## City
|DIBBS Field | NBS 6 Field|
|----|----|
|`Patient.address.city`| `postal_locator.city_desc_txt`|

```SQL
SELECT DISTINCT
	pl.city_desc_txt,
	p.person_parent_uid
FROM
	postal_locator pl
	JOIN Entity_locator_participation elp ON elp.locator_uid = pl.postal_locator_uid
	JOIN person p ON elp.entity_uid = p.person_uid
WHERE
	elp.record_status_cd = 'ACTIVE'
  AND p.cd = 'PAT';
```

## State
|DIBBS Field | NBS 6 Field|
|----|----|
|`Patient.address.state`| `postal_locator.state_cd`|

```SQL
SELECT DISTINCT
	pl.state_cd,
	p.person_parent_uid
FROM
	postal_locator pl
	JOIN Entity_locator_participation elp ON elp.locator_uid = pl.postal_locator_uid
	JOIN person p ON elp.entity_uid = p.person_uid
WHERE
	elp.record_status_cd = 'ACTIVE'
  AND p.cd = 'PAT';
```

## Sex
|DIBBS Field | NBS 6 Field|
|----|----|
|`Patient.address.gender`| `person.birth_gender_cd` OR `person.curr_sex_cd`|

```SQL
SELECT DISTINCT
	birth_gender_cd,
	curr_sex_cd,
	person_parent_uid
FROM
	person;
WHERE
  record_status_cd = 'ACTIVE'
  AND cd = 'PAT';
```

## MRN
|DIBBS Field | NBS 6 Field|
|----|----|
|`Patient.identifier.value WHERE(type.coding.code='MR')`| `entity_id.root_extension_txt WHERE type_cd = 'MR'`|

```SQL
SELECT DISTINCT
	id.root_extension_txt,
	p.person_parent_uid
FROM
	entity_id id
	JOIN person p ON p.person_uid = id.entity_uid
WHERE
	id.record_status_cd = 'ACTIVE'
	AND id.type_cd = 'MR'
	AND p.cd = 'PAT';
```
