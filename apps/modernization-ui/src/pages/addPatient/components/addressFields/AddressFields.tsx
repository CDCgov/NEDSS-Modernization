import { ComboBox, ErrorMessage, FormGroup, Grid, Label, TextInput } from '@trussworks/react-uswds';
import FormCard from '../../../../components/FormCard/FormCard';
import { Controller } from 'react-hook-form';
import { SelectInput } from 'components/FormInputs/SelectInput';
import { useEffect, useState } from 'react';
import { SearchCriteriaContext } from 'providers/SearchCriteriaContext';
import {
    CountyCode,
    FindAllCountyCodesForStateQuery,
    useFindAllCountyCodesForStateLazyQuery
} from 'generated/graphql/schema';
import 'react-bootstrap-typeahead/css/Typeahead.css';
import 'react-bootstrap-typeahead/css/Typeahead.bs5.css';
import './AddressFields.scss';
import { ComboBoxOption } from '@trussworks/react-uswds/lib/components/forms/ComboBox/ComboBox';

export interface InputAddressFields {
    streetAddress1: string;
    streetAddress2: string;
    city: string;
    state: string;
    zip: string;
    county: string;
    censusTract: string;
    country: string;
}

interface SmartyAutocompleteFields {
    street_line: string;
    secondary: string;
    city: string;
    state: string;
    zipcode: string;
    entries: number;
}

export default function AddressFields(
    this: any,
    {
        addressFields,
        updateCallback,
        id,
        title,
        control
    }: {
        addressFields: InputAddressFields;
        updateCallback: (inputNameFields: InputAddressFields) => void;
        id?: string;
        title?: string;
        control?: any;
    }
) {
    const [isTractValid, setIsTractValid] = useState(true);

    const [counties, setCounties] = useState<CountyCode[]>([]);

    const [addressTypeAheadQuery, setAddressTypeAheadQuery] = useState<string>('');

    const [addressSuggestions, setAddressSuggestions] = useState<ComboBoxOption[]>([]);

    const setCounty = (results: FindAllCountyCodesForStateQuery) => {
        if (results?.findAllCountyCodesForState) {
            const counties: CountyCode[] = [];
            results.findAllCountyCodesForState.forEach((i) => i && counties.push(i));
            counties.sort((a, b) => {
                if (a?.codeDescTxt && b?.codeDescTxt) {
                    return a.codeDescTxt.localeCompare(b.codeDescTxt);
                }
                return 0;
            });
            setCounties(counties);
        }
    };
    const [getAllStateCounty] = useFindAllCountyCodesForStateLazyQuery({ onCompleted: setCounty });

    function updateTractValidity() {
        setIsTractValid((document.getElementById('census-tract') as HTMLInputElement).validity.valid);
    }

    const fetchAddressSuggestions = async () => {
        if (addressTypeAheadQuery.length > 2) {
            const data = await fetch(
                `https://us-autocomplete-pro.api.smarty.com/lookup?key=166215385741384990&search=${addressTypeAheadQuery}`,
                {
                    headers: {
                        referer: 'localhost'
                    }
                }
            ).then((resp) => resp.json());
            setAddressSuggestions(
                data.suggestions.map((suggestion: SmartyAutocompleteFields) => ({
                    label: suggestion.street_line,
                    value: JSON.stringify(suggestion)
                }))
            );
        }
    };

    useEffect(() => {
        fetchAddressSuggestions().catch(console.error);
    }, [addressTypeAheadQuery]);

    return (
        <FormCard id={id} title={title}>
            <Grid col={12} className="padding-x-3 padding-bottom-3">
                <Grid row>
                    <Grid col={6}>
                        <Label htmlFor="mailingAddress1">Street address 1</Label>
                        <SearchCriteriaContext.Consumer>
                            {({ searchCriteria }) => {
                                return (
                                    <Controller
                                        control={control}
                                        name="streetAddress1"
                                        render={({ field: { onChange, value } }) => (
                                            <ComboBox
                                                id={'streetAddress1'}
                                                name={'streetAddress1'}
                                                onChange={(value) => {
                                                    if (value) {
                                                        const smartyAddress: SmartyAutocompleteFields =
                                                            JSON.parse(value);
                                                        const convertedStateValue: string =
                                                            searchCriteria.states.find(
                                                                (state) => state.stateNm === smartyAddress.state
                                                            )?.id || '';
                                                        console.log(`smartyAddress: ${smartyAddress}`);
                                                        console.log(`convertedStateValue: ${convertedStateValue}`);
                                                        updateCallback({
                                                            ...addressFields,
                                                            streetAddress1: smartyAddress.street_line,
                                                            streetAddress2: smartyAddress.secondary,
                                                            city: smartyAddress.city,
                                                            state: convertedStateValue,
                                                            zip: smartyAddress.zipcode
                                                        });
                                                        onChange(value);
                                                    }
                                                }}
                                                defaultValue={value}
                                                inputProps={{
                                                    onChange: (e) => {
                                                        setAddressTypeAheadQuery(e.target.value);
                                                    }
                                                }}
                                                options={addressSuggestions}
                                            />
                                        )}
                                    />
                                );
                            }}
                        </SearchCriteriaContext.Consumer>
                    </Grid>
                </Grid>
                <Grid row>
                    <Grid col={6}>
                        <Label htmlFor="mailingAddress2" hint=" (optional)">
                            Street address 2
                        </Label>
                        <TextInput
                            id="mailingAddress2"
                            name="mailingAddress2"
                            type="text"
                            defaultValue={addressFields.streetAddress2}
                            onChange={(v) =>
                                updateCallback({
                                    ...addressFields,
                                    streetAddress2: v.target.value
                                })
                            }
                        />
                    </Grid>
                </Grid>
                <Grid row>
                    <Grid col={6}>
                        <Controller
                            control={control}
                            name="city"
                            render={({ field: { onChange, value } }) => (
                                <span>
                                    <Label htmlFor="city">City</Label>
                                    <TextInput
                                        id="city"
                                        name="city"
                                        type="text"
                                        defaultValue={value}
                                        onChange={onChange}
                                    />
                                </span>
                            )}
                        />
                    </Grid>
                </Grid>
                <Grid row gap={2}>
                    <Grid col={4}>
                        <SearchCriteriaContext.Consumer>
                            {({ searchCriteria }) => {
                                return (
                                    <Controller
                                        control={control}
                                        name="state"
                                        render={({ field: { onChange, value } }) => (
                                            <SelectInput
                                                onChange={(e: any) => {
                                                    if (e.target.value) {
                                                        console.log(`e.target.value: ${e.target.value}`);
                                                        getAllStateCounty({
                                                            variables: {
                                                                stateCode: e.target.value,
                                                                page: { pageNumber: 0, pageSize: 50 }
                                                            }
                                                        });
                                                    }
                                                    onChange(e);
                                                }}
                                                defaultValue={value}
                                                name="state"
                                                htmlFor={'state'}
                                                label="State"
                                                options={Object.values(searchCriteria.states).map((state) => {
                                                    return {
                                                        name: state?.codeDescTxt || '',
                                                        value: state?.id || ''
                                                    };
                                                })}
                                            />
                                        )}
                                    />
                                );
                            }}
                        </SearchCriteriaContext.Consumer>
                    </Grid>
                    <Grid col={2}>
                        <Label htmlFor="zip">ZIP</Label>
                        <TextInput
                            id="zip"
                            name="zip"
                            type="text"
                            inputSize="medium"
                            pattern="[\d]{5}(-[\d]{4})?"
                            defaultValue={addressFields.zip}
                            onChange={(v) => updateCallback({ ...addressFields, zip: v.target.value })}
                        />
                    </Grid>
                </Grid>
                <Grid row>
                    <Grid col={6}>
                        <Controller
                            control={control}
                            name="county"
                            render={({ field: { onChange, value } }) => (
                                <SelectInput
                                    onChange={onChange}
                                    defaultValue={value}
                                    name="county"
                                    htmlFor={'county'}
                                    label="County"
                                    options={counties.map((state) => {
                                        return {
                                            name: state?.codeDescTxt || '',
                                            value: state?.id || ''
                                        };
                                    })}
                                />
                            )}
                        />
                    </Grid>
                </Grid>
                <Grid row>
                    <Grid col={6}>
                        <FormGroup error={!isTractValid}>
                            <Label htmlFor="censusTract" error={!isTractValid}>
                                Census Tract
                            </Label>
                            <TextInput
                                id="censusTract"
                                name="censusTract"
                                type="text"
                                pattern="[0-9]{4}(.(([0-8][0-9])|([9][0-8])))?"
                                onBlur={() => updateTractValidity()}
                                defaultValue={addressFields.censusTract}
                                onChange={(v) =>
                                    updateCallback({
                                        ...addressFields,
                                        censusTract: v.target.value
                                    })
                                }
                            />
                            {!isTractValid ? (
                                <ErrorMessage>
                                    Census Tract should be in numeric XXXX or XXXX.xx format where XXXX is the basic
                                    tract and xx is the suffix. XXXX ranges from 0001 to 9999. The suffix is limited to
                                    a range between .01 and .98.
                                </ErrorMessage>
                            ) : (
                                ''
                            )}
                        </FormGroup>
                    </Grid>
                </Grid>
                <Grid row>
                    <Grid col={6}>
                        <SearchCriteriaContext.Consumer>
                            {({ searchCriteria }) => {
                                return (
                                    <Controller
                                        control={control}
                                        name="country"
                                        render={({ field: { onChange, value } }) => (
                                            <SelectInput
                                                onChange={onChange}
                                                defaultValue={value}
                                                name="country"
                                                htmlFor={'country'}
                                                label="Country"
                                                options={
                                                    searchCriteria.countries
                                                        ? Object.values(searchCriteria.countries).map((country) => {
                                                              return {
                                                                  name: country?.codeDescTxt || '',
                                                                  value: country?.id || ''
                                                              };
                                                          })
                                                        : []
                                                }
                                            />
                                        )}
                                    />
                                );
                            }}
                        </SearchCriteriaContext.Consumer>
                    </Grid>
                </Grid>
            </Grid>
        </FormCard>
    );
}
