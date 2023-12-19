import { PersonFilter } from 'generated/graphql/schema';
import { SearchCriteriaContext } from 'providers/SearchCriteriaContext';
import Chip from '../../../../pages/advancedSearch/components/chips/Chip';

type PatientChipsProps = {
    filter: PersonFilter;
    onChange: (personFilter: PersonFilter) => void;
};
export const PatientChips = ({ filter, onChange }: PatientChipsProps) => {
    return (
        <>
            <SearchCriteriaContext.Consumer>
                {({ searchCriteria }) => (
                    <>
                        {filter.lastName ? (
                            <Chip
                                name="LAST"
                                value={filter.lastName}
                                handleClose={() => onChange({ ...filter, lastName: undefined })}
                            />
                        ) : null}
                        {filter.firstName ? (
                            <Chip
                                name="FIRST"
                                value={filter.firstName}
                                handleClose={() => onChange({ ...filter, firstName: undefined })}
                            />
                        ) : null}
                        {filter.dateOfBirth ? (
                            <Chip
                                name="DOB"
                                value={filter.dateOfBirth}
                                handleClose={() => onChange({ ...filter, dateOfBirth: undefined })}
                            />
                        ) : null}
                        {filter.gender ? (
                            <Chip
                                name="SEX"
                                value={filter.gender}
                                handleClose={() => onChange({ ...filter, gender: undefined })}
                            />
                        ) : null}
                        {filter.id ? (
                            <Chip
                                name="ID"
                                value={filter.id}
                                handleClose={() => onChange({ ...filter, id: undefined })}
                            />
                        ) : null}
                        {filter.address ? (
                            <Chip
                                name="ADDRESS"
                                value={filter.address}
                                handleClose={() => onChange({ ...filter, address: undefined })}
                            />
                        ) : null}
                        {filter.city ? (
                            <Chip
                                name="CITY"
                                value={filter.city}
                                handleClose={() => onChange({ ...filter, city: undefined })}
                            />
                        ) : null}
                        {filter.state ? (
                            <Chip
                                name="STATE"
                                value={searchCriteria.states.find((s) => s.value === filter.state)?.name ?? ''}
                                handleClose={() => onChange({ ...filter, state: undefined })}
                            />
                        ) : null}
                        {filter.zip ? (
                            <Chip
                                name="ZIP"
                                value={filter.zip}
                                handleClose={() => onChange({ ...filter, zip: undefined })}
                            />
                        ) : null}
                        {filter.phoneNumber ? (
                            <Chip
                                name="PHONENUMBER"
                                value={filter.phoneNumber}
                                handleClose={() => onChange({ ...filter, phoneNumber: undefined })}
                            />
                        ) : null}
                        {filter.email ? (
                            <Chip
                                name="EMAIL"
                                value={filter.email}
                                handleClose={() => onChange({ ...filter, email: undefined })}
                            />
                        ) : null}
                        {filter.identification?.identificationType ? (
                            <Chip
                                name="ID TYPE"
                                value={
                                    searchCriteria.identificationTypes.find(
                                        (i) => i.id.code === filter.identification?.identificationType
                                    )?.codeDescTxt ?? ''
                                }
                                handleClose={() => onChange({ ...filter, identification: undefined })}
                            />
                        ) : null}
                        {filter.identification?.identificationNumber ? (
                            <Chip
                                name="ID NUMBER"
                                value={filter.identification.identificationNumber}
                                handleClose={() => onChange({ ...filter, identification: undefined })}
                            />
                        ) : null}
                        {filter.ethnicity ? (
                            <Chip
                                name="ETHNICITY"
                                value={
                                    searchCriteria.ethnicities.find((e) => e.id.code === filter.ethnicity)
                                        ?.codeDescTxt ?? ''
                                }
                                handleClose={() => onChange({ ...filter, ethnicity: undefined })}
                            />
                        ) : null}
                        {filter.race ? (
                            <Chip
                                name="RACE"
                                value={searchCriteria.races.find((r) => r.id.code === filter.race)?.codeDescTxt ?? ''}
                                handleClose={() => onChange({ ...filter, race: undefined })}
                            />
                        ) : null}
                    </>
                )}
            </SearchCriteriaContext.Consumer>
        </>
    );
};
