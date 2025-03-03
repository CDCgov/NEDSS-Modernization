/* eslint-disable */
import { Controller, useFormContext } from 'react-hook-form';
import { SearchCriteriaProvider } from 'providers/SearchCriteriaContext';
import { MultiSelect } from 'design-system/select';
import { SearchCriteria } from 'apps/search/criteria';
import { EntryFieldsProps } from 'design-system/entry';
import { UserAutocomplete } from 'options/autocompete/UserAutocomplete';
import {useProviderOptionsAutocomplete} from 'options/providers';

export const ElrCriteriaSearchFields = ({ sizing, orientation }: EntryFieldsProps) => {
    const { control } = useFormContext();
    const { options: providers } = useProviderOptionsAutocomplete();

    return (
        <SearchCriteriaProvider>
            <SearchCriteria>
                <Controller
                    control={control}
                    name="messageId"
                    render={({ field: { onChange, value, name } }) => (
                        <UserAutocomplete
                            id={name}
                            value={value}
                            label="Message ID"
                            sizing={sizing}
                            orientation={orientation}
                            onChange={onChange}
                        />
                    )}
                />
                <Controller
                    control={control}
                    name="sourceNm"
                    render={({ field: { onChange, value, name } }) => (
                        <MultiSelect
                            id={name}
                            value={value}
                            label="Reporting Facility"
                            sizing={sizing}
                            orientation={orientation}
                            onChange={onChange}
                            name={name}
                            options={providers}
                        />
                    )}
                />
                <Controller
                    control={control}
                    name="entityNm"
                    render={({ field: { onChange, value, name } }) => (
                        <UserAutocomplete
                            id={name}
                            value={value}
                            label="Patient Name"
                            sizing={sizing}
                            orientation={orientation}
                            onChange={onChange}
                        />
                    )}
                />
                <Controller
                    control={control}
                    name="observationId"
                    render={({ field: { onChange, value, name } }) => (
                        <UserAutocomplete
                            id={name}
                            value={value}
                            label="Observation ID"
                            sizing={sizing}
                            orientation={orientation}
                            onChange={onChange}
                        />
                    )}
                />
                <Controller
                    control={control}
                    name="accessionNbr"
                    render={({ field: { onChange, value, name } }) => (
                        <UserAutocomplete
                            id={name}
                            value={value}
                            label="Accession#"
                            sizing={sizing}
                            orientation={orientation}
                            onChange={onChange}
                        />
                    )}
                />
                <Controller
                    control={control}
                    name="exceptionTxt"
                    render={({ field: { onChange, value, name } }) => (
                        <MultiSelect
                            id={name}
                            value={value}
                            label="Exception Text"
                            sizing={sizing}
                            orientation={orientation}
                            onChange={onChange} name={''} options={[]}
                        />
                    )}
                />
            </SearchCriteria>
        </SearchCriteriaProvider>
    );
};

export default ElrCriteriaSearchFields;