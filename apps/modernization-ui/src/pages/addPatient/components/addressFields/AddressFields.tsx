import {
    Button,
    ButtonGroup,
    ErrorMessage,
    FormGroup,
    Grid,
    Icon,
    Label,
    Modal,
    ModalFooter,
    ModalHeading,
    ModalRef,
    ModalToggleButton,
    TextInput
} from '@trussworks/react-uswds';
import FormCard from '../../../../components/FormCard/FormCard';
import { Controller } from 'react-hook-form';
import { SelectInput } from 'components/FormInputs/SelectInput';
import { ChangeEvent, useEffect, useRef, useState } from 'react';
import { SearchCriteriaContext } from 'providers/SearchCriteriaContext';
import {
    CountyCode,
    FindAllCountyCodesForStateQuery,
    StateCode,
    useFindAllCountyCodesForStateLazyQuery
} from 'generated/graphql/schema';

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

export interface AddressSuggestion {
    street_line: string;
    secondary: string;
    city: string;
    state: string;
    zipcode: string;
    entries: number;
}

export default function AddressFields({
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
}) {
    const [isTractValid, setIsTractValid] = useState(true);
    const wrapperRef = useRef<any>(null);
    const [showSuggestions, setShowSuggestions] = useState<boolean>(false);
    const [suggestions, setSuggestions] = useState<AddressSuggestion[]>([]);
    const [counties, setCounties] = useState<CountyCode[]>([]);
    const [verified, setVerified] = useState<boolean>(false);
    const [unverified, setUnverified] = useState<boolean>(false);
    const verifiedModalRef = useRef<ModalRef>(null);
    const unverifiedModalRef = useRef<ModalRef>(null);
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

    useEffect(() => {
        function handleClickOutside(event: any) {
            if (wrapperRef.current && !wrapperRef.current.contains(event.target)) {
                setShowSuggestions(false);
            }
        }
        // Bind the event listener
        document.addEventListener('mousedown', handleClickOutside);
        return () => {
            // Unbind the event listener on clean up
            document.removeEventListener('mousedown', handleClickOutside);
        };
    }, [wrapperRef]);

    function updateTractValidity() {
        setIsTractValid((document.getElementById('census-tract') as HTMLInputElement).validity.valid);
    }

    function populateSuggestions(eve: ChangeEvent<HTMLInputElement>) {
        // TODO- this will come from the api
        const mockSuggestionsResp = {
            suggestions: [
                {
                    street_line: '7927 Jones Branch Dr',
                    secondary: '',
                    city: 'Mc Lean',
                    state: 'VA',
                    zipcode: '22102',
                    entries: 0
                },
                {
                    street_line: '7927 Jones Branch Dr',
                    secondary: 'Ste',
                    city: 'Mc Lean',
                    state: 'VA',
                    zipcode: '22102',
                    entries: 38
                },
                {
                    street_line: '7927 Jones Rd',
                    secondary: '',
                    city: 'Cleveland',
                    state: 'OH',
                    zipcode: '44105',
                    entries: 0
                }
            ]
        };
        eve.target.value;
        setSuggestions(mockSuggestionsResp.suggestions);
        setShowSuggestions(true);
    }

    function handleSuggestionSelection(idx: number, states: StateCode[]) {
        const selectedSuggestion = suggestions[idx];
        addressFields.city = selectedSuggestion.city;
        addressFields.state = states.find((state) => state.stateNm === selectedSuggestion.state)?.id || '00';
        addressFields.streetAddress1 = selectedSuggestion.street_line;
        addressFields.streetAddress2 = selectedSuggestion.secondary;
        addressFields.zip = selectedSuggestion.zipcode;
        updateCallback({
            ...addressFields
        });
        setShowSuggestions(false);
    }

    useEffect(() => {
        verifiedModalRef.current?.toggleModal(undefined, true);
    }, [verified]);

    useEffect(() => {
        unverifiedModalRef.current?.toggleModal(undefined, true);
    }, [unverified]);

    const handleKeyDown = (event: any) => {
        setVerified(false);
        setUnverified(false);
        switch (event.key) {
            case 'Enter' || 'enter': {
                event.preventDefault();
                // Do verification here and show either unverified or verified modal based on it.
                // mock verified vs unverified by just checking for a certaiun number
                // TODO will change to api fetch once ready.
                if (event.target.value.includes('1234')) {
                    setSuggestions([
                        {
                            street_line: '1234 Salem St',
                            secondary: '',
                            city: 'Mc Lean',
                            state: 'VA',
                            zipcode: '22102',
                            entries: 0
                        }
                    ]);
                    setVerified(true);
                } else {
                    setUnverified(true);
                }
                setShowSuggestions(false);
            }
        }
    };

    return (
        <FormCard id={id} title={title}>
            <Grid col={12} className="padding-x-3 padding-bottom-3 address-fields-grid">
                <Grid row>
                    <Grid col={6}>
                        <Label htmlFor="mailingAddress1">Street address 1</Label>
                        <TextInput
                            id="mailingAddress1"
                            name="mailingAddress1"
                            type="text"
                            value={addressFields.streetAddress1}
                            defaultValue={addressFields.streetAddress1}
                            onKeyDown={handleKeyDown}
                            onChange={(v) => {
                                populateSuggestions(v);
                                updateCallback({
                                    ...addressFields,
                                    streetAddress1: v.target.value
                                });
                            }}
                        />
                        {showSuggestions && (
                            <div className="button-group">
                                <ul ref={wrapperRef} id="basic-nav-section-one" className="usa-nav__submenu">
                                    {suggestions.map((suggestion, idx) => (
                                        <li key={idx} className="usa-nav__submenu-item">
                                            <SearchCriteriaContext.Consumer>
                                                {({ searchCriteria }) => {
                                                    return (
                                                        <Button
                                                            key={idx}
                                                            onClick={() =>
                                                                handleSuggestionSelection(idx, searchCriteria.states)
                                                            }
                                                            type={'button'}
                                                            unstyled>
                                                            <span className="address-suggestion-line">
                                                                {suggestion.street_line}
                                                            </span>
                                                            <span className="address-suggestion-line">
                                                                {suggestion.city}, {suggestion.state}{' '}
                                                                {suggestion.zipcode}
                                                            </span>
                                                        </Button>
                                                    );
                                                }}
                                            </SearchCriteriaContext.Consumer>
                                        </li>
                                    ))}
                                </ul>
                            </div>
                        )}

                        {unverified && (
                            <span>
                                <Modal
                                    ref={unverifiedModalRef}
                                    forceAction
                                    id="example-incomplete-form-confirmation-modal"
                                    aria-labelledby="incomplete-form-confirmation-modal-heading"
                                    className="padding-0"
                                    aria-describedby="incomplete-form-confirmation-modal-description">
                                    <ModalHeading
                                        id="incomplete-form-confirmation-modal-heading"
                                        className="border-bottom border-base-lighter font-sans-lg padding-2">
                                        Unverified Address
                                    </ModalHeading>
                                    <div className="margin-2 grid-row flex-no-wrap border-left-1 border-accent-warm flex-align-center">
                                        <Icon.Warning className="font-sans-2xl margin-x-2" />
                                        <p className="" id="incomplete-form-confirmation-modal-description">
                                            We were unable to find a valid address matching what was entered. Are you
                                            sure you want to continue?
                                        </p>
                                    </div>
                                    <div>
                                        <p
                                            className="margin-left-9 padding-right-2"
                                            id="incomplete-form-confirmation-modal-description">
                                            Note: You may go back to modify the address.
                                        </p>
                                    </div>
                                    <ModalFooter className="border-top border-base-lighter padding-2 margin-left-auto">
                                        <ButtonGroup>
                                            <ModalToggleButton
                                                onClick={() => setUnverified(false)}
                                                outline
                                                modalRef={unverifiedModalRef}
                                                closer>
                                                Go back
                                            </ModalToggleButton>
                                            <ModalToggleButton
                                                onClick={() => setUnverified(false)}
                                                modalRef={unverifiedModalRef}
                                                closer
                                                className="padding-105 text-center">
                                                Continue anyways
                                            </ModalToggleButton>
                                        </ButtonGroup>
                                    </ModalFooter>
                                </Modal>
                            </span>
                        )}

                        {verified && (
                            <span>
                                <Modal
                                    ref={verifiedModalRef}
                                    forceAction
                                    id="example-incomplete-form-confirmation-modal"
                                    aria-labelledby="incomplete-form-confirmation-modal-heading"
                                    className="padding-0"
                                    aria-describedby="incomplete-form-confirmation-modal-description">
                                    <ModalHeading
                                        id="incomplete-form-confirmation-modal-heading"
                                        className="border-bottom border-base-lighter font-sans-lg padding-2">
                                        Verified Address
                                    </ModalHeading>
                                    <div className="margin-2 grid-row flex-no-wrap border-left-1 border-accent-warm flex-align-center">
                                        <Icon.Warning className="font-sans-2xl margin-x-2" />
                                        <p className="" id="incomplete-form-confirmation-modal-description">
                                            You are about to add a new patient with invalid inputs. We found a valid
                                            address. Would you like to update to the valid address found?
                                        </p>
                                    </div>
                                    <div className="margin-left-9">
                                        <p
                                            className="padding-right-2"
                                            id="incomplete-form-confirmation-modal-description">
                                            Note: 1 Valid address found below
                                        </p>
                                    </div>
                                    <div className="margin-left-9 address-section">
                                        <div>
                                            <p className="text-bold">Entered address:</p>
                                            <p>{suggestions[0].street_line}</p>
                                            <p>
                                                {suggestions[0].city}, {suggestions[0].state} {suggestions[0].zipcode}
                                            </p>
                                        </div>
                                        <div className="margin-right-9">
                                            <p className="text-bold">Valid address found:</p>
                                            <p>{suggestions[0].street_line}</p>
                                            <p>
                                                {suggestions[0].city}, {suggestions[0].state} {suggestions[0].zipcode}
                                            </p>
                                        </div>
                                    </div>
                                    <ModalFooter className="border-top border-base-lighter padding-2 margin-left-auto">
                                        <ButtonGroup className="verified-button-group">
                                            <ModalToggleButton
                                                onClick={() => setVerified(false)}
                                                outline
                                                modalRef={verifiedModalRef}
                                                closer>
                                                Go back
                                            </ModalToggleButton>
                                            <ModalToggleButton
                                                onClick={() => setVerified(false)}
                                                outline
                                                modalRef={verifiedModalRef}
                                                closer>
                                                Continue without update
                                            </ModalToggleButton>
                                            <ModalToggleButton
                                                onClick={() => setVerified(false)}
                                                modalRef={verifiedModalRef}
                                                closer
                                                className="padding-105 text-center">
                                                Update address and continue
                                            </ModalToggleButton>
                                        </ButtonGroup>
                                    </ModalFooter>
                                </Modal>
                            </span>
                        )}
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
