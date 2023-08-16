import { Button, ButtonGroup, Grid, Icon, Label, ModalFooter, ModalHeading, Textarea } from '@trussworks/react-uswds';
import './AddressEntryForm.scss';
import { Controller, FieldValues, useForm, useWatch } from 'react-hook-form';
import { externalizeDateTime } from 'date';
import { SelectInput } from 'components/FormInputs/SelectInput';
import { DatePickerInput } from 'components/FormInputs/DatePickerInput';
import { Input } from 'components/FormInputs/Input';
import { usePatientAddressCodedValues } from './usePatientAddressCodedValues';
import { useCountyCodedValues, useLocationCodedValues } from 'location';
import { AddressEntry } from './AddressEntry';
import { orNull } from 'utils';
import { ChangeEvent, useEffect, useRef, useState } from 'react';
import { AddressSuggestion } from 'pages/addPatient/components/addressFields/AddressFields';
import { StateCodedValue } from 'location';

type EntryProps = {
    action: string;
    entry: AddressEntry;
    onChange: (updated: AddressEntry) => void;
    onCancel: () => void;
    onModalContextChange?: (modalContext: string) => void;
};

export const AddressEntryForm = ({ action, entry, onChange, onCancel, onModalContextChange }: EntryProps) => {
    const {
        handleSubmit,
        control,
        formState: { isValid },
        setValue
    } = useForm({ mode: 'onBlur' });

    const wrapperRef = useRef<any>(null);
    const [showSuggestions, setShowSuggestions] = useState<boolean>(false);
    const [suggestions, setSuggestions] = useState<AddressSuggestion[]>([]);
    const [verified, setVerified] = useState<boolean>(false);
    const [unverified, setUnverified] = useState<boolean>(false);

    const selectedState = useWatch({ control, name: 'state', defaultValue: entry.state });

    const coded = usePatientAddressCodedValues();
    const location = useLocationCodedValues();
    const byState = useCountyCodedValues(selectedState);

    useEffect(() => {
        if (onModalContextChange) {
            onModalContextChange(
                (verified && 'verified-modal') || (unverified && 'unverified-modal') || 'add-address-modal'
            );
        }
    }, [verified, unverified]);

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

    function handleSuggestionSelection(idx: number, statesDetails: StateCodedValue[]) {
        const selectedSuggestion = suggestions[idx];
        const selectedState =
            statesDetails.find((state) => state.abbreviation === selectedSuggestion.state)?.value || '00';

        // Set values of other address inputs
        setValue('address1', selectedSuggestion.street_line);
        setValue('city', selectedSuggestion.city);
        setValue('state', selectedState);
        setValue('zipcode', selectedSuggestion.zipcode);
        setValue('address2', selectedSuggestion.secondary);
        setShowSuggestions(false);
    }

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

    const onSubmit = (entered: FieldValues) => {
        onChange({
            ...entry,
            asOf: externalizeDateTime(entered.asOf),
            use: orNull(entered.use),
            type: orNull(entered.type),
            address1: entered.address1,
            address2: entered.address2,
            city: entered.city,
            state: orNull(entered.state),
            zipcode: entered.zipcode,
            county: orNull(entered.county),
            censusTract: entered.censusTract,
            country: orNull(entered.country),
            comment: entered.comment
        });
    };

    return (
        <div className="width-full maxw-full modal-form">
            {!unverified && !verified && (
                <div>
                    <div className="modal-body address-entry-form-container">
                        <Grid row>
                            <Grid col={12} className="border-bottom border-base-lighter padding-bottom-2 padding-2">
                                <Controller
                                    control={control}
                                    name="asOf"
                                    rules={{ required: { value: true, message: 'As of date is required.' } }}
                                    defaultValue={entry.asOf}
                                    render={({ field: { onBlur, onChange, value }, fieldState: { error } }) => (
                                        <DatePickerInput
                                            flexBox
                                            defaultValue={value}
                                            onBlur={onBlur}
                                            onChange={onChange}
                                            name="asOf"
                                            htmlFor={'asOf'}
                                            label="As of"
                                            errorMessage={error?.message}
                                        />
                                    )}
                                />
                            </Grid>
                            <Grid col={12} className="border-bottom border-base-lighter padding-bottom-2 padding-2">
                                <Controller
                                    control={control}
                                    name="type"
                                    defaultValue={entry.type}
                                    rules={{ required: { value: true, message: 'Type is required.' } }}
                                    render={({ field: { onBlur, onChange, value }, fieldState: { error } }) => (
                                        <SelectInput
                                            flexBox
                                            defaultValue={value}
                                            onBlur={onBlur}
                                            onChange={onChange}
                                            htmlFor={'type'}
                                            label="Type"
                                            options={coded.types}
                                            error={error?.message}
                                        />
                                    )}
                                />
                            </Grid>
                            <Grid col={12} className="border-bottom border-base-lighter padding-bottom-2 padding-2">
                                <Controller
                                    control={control}
                                    name="use"
                                    defaultValue={entry.use}
                                    rules={{ required: { value: true, message: 'Use is required.' } }}
                                    render={({ field: { onBlur, onChange, value }, fieldState: { error } }) => (
                                        <SelectInput
                                            flexBox
                                            defaultValue={value}
                                            onBlur={onBlur}
                                            onChange={onChange}
                                            htmlFor={'use'}
                                            label="Use"
                                            options={coded.uses}
                                            error={error?.message}
                                        />
                                    )}
                                />
                            </Grid>
                            <Grid col={12} className="border-bottom border-base-lighter padding-bottom-2 padding-2">
                                <Controller
                                    control={control}
                                    name="address1"
                                    defaultValue={entry.address1}
                                    render={({ field: { onChange, value } }) => (
                                        <Input
                                            flexBox
                                            onKeyDown={handleKeyDown}
                                            onChange={(e: ChangeEvent<HTMLInputElement>) => {
                                                onChange(e);
                                                populateSuggestions(e);
                                            }}
                                            defaultValue={value}
                                            type="text"
                                            label="Street address 1"
                                            name="address1"
                                            htmlFor="address1"
                                            id="address1"
                                        />
                                    )}
                                />

                                {showSuggestions && (
                                    <div className="button-group">
                                        <ul
                                            ref={wrapperRef}
                                            id="basic-nav-section-one"
                                            className="suggestion-list-container usa-nav__submenu">
                                            {suggestions.map((suggestion, idx) => (
                                                <li key={idx} className="usa-nav__submenu-item">
                                                    <Button
                                                        key={idx}
                                                        onClick={() => handleSuggestionSelection(idx, location.states)}
                                                        type={'button'}
                                                        unstyled>
                                                        <div className="address-suggestion-line">
                                                            {suggestion.street_line}
                                                        </div>
                                                        <div className="address-suggestion-line">
                                                            {suggestion.city}, {suggestion.state} {suggestion.zipcode}
                                                        </div>
                                                    </Button>
                                                </li>
                                            ))}
                                        </ul>
                                    </div>
                                )}
                            </Grid>
                            <Grid col={12} className="border-bottom border-base-lighter padding-bottom-2 padding-2">
                                <Controller
                                    control={control}
                                    name="address2"
                                    defaultValue={entry.address2}
                                    render={({ field: { onChange, value } }) => (
                                        <Input
                                            flexBox
                                            onChange={onChange}
                                            defaultValue={value}
                                            type="text"
                                            label="Street address 2"
                                            name="address2"
                                            htmlFor="address2"
                                            id="address2"
                                        />
                                    )}
                                />
                            </Grid>
                            <Grid col={12} className="border-bottom border-base-lighter padding-bottom-2 padding-2">
                                <Controller
                                    control={control}
                                    name="city"
                                    defaultValue={entry.city}
                                    render={({ field: { onChange, value } }) => (
                                        <Input
                                            flexBox
                                            onChange={onChange}
                                            defaultValue={value}
                                            type="text"
                                            label="City"
                                            name="city"
                                            htmlFor="city"
                                            id="city"
                                        />
                                    )}
                                />
                            </Grid>
                            <Grid col={12} className="border-bottom border-base-lighter padding-bottom-2 padding-2">
                                <Controller
                                    control={control}
                                    name="state"
                                    defaultValue={entry.state}
                                    render={({ field: { onChange, value } }) => (
                                        <SelectInput
                                            flexBox
                                            defaultValue={value}
                                            onChange={onChange}
                                            htmlFor={'state'}
                                            label="State"
                                            options={location.states}
                                        />
                                    )}
                                />
                            </Grid>
                            <Grid col={12} className="border-bottom border-base-lighter padding-bottom-2 padding-2">
                                <Controller
                                    control={control}
                                    name="zipcode"
                                    defaultValue={entry.zipcode}
                                    rules={{
                                        pattern: { value: /^\d{5}(?:[-\s]\d{4})?$/, message: 'Invalid zip code' }
                                    }}
                                    render={({ field: { onChange, value }, fieldState: { error } }) => (
                                        <Input
                                            flexBox
                                            onChange={onChange}
                                            defaultValue={value}
                                            type="text"
                                            label="Zip"
                                            name="zipcode"
                                            htmlFor="zipcode"
                                            id="zipcode"
                                            error={error?.message}
                                        />
                                    )}
                                />
                            </Grid>
                            <Grid col={12} className="border-bottom border-base-lighter padding-bottom-2 padding-2">
                                <Controller
                                    control={control}
                                    name="county"
                                    defaultValue={entry.county}
                                    render={({ field: { onChange, value } }) => (
                                        <SelectInput
                                            flexBox
                                            defaultValue={value}
                                            onChange={onChange}
                                            htmlFor={'county'}
                                            label="County"
                                            options={byState.counties}
                                        />
                                    )}
                                />
                            </Grid>
                            <Grid col={12} className="border-bottom border-base-lighter padding-bottom-2 padding-2">
                                <Controller
                                    control={control}
                                    name="censusTract"
                                    defaultValue={entry.censusTract}
                                    render={({ field: { onChange, value } }) => (
                                        <Input
                                            flexBox
                                            onChange={onChange}
                                            defaultValue={value}
                                            type="text"
                                            label="Census tract"
                                            name="censusTract"
                                            htmlFor="censusTract"
                                            id="censusTract"
                                        />
                                    )}
                                />
                            </Grid>
                            <Grid col={12} className="border-bottom border-base-lighter padding-bottom-2 padding-2">
                                <Controller
                                    control={control}
                                    name="country"
                                    defaultValue={entry.country}
                                    render={({ field: { onChange, value } }) => (
                                        <SelectInput
                                            flexBox
                                            defaultValue={value}
                                            onChange={onChange}
                                            htmlFor={'country'}
                                            label="Country"
                                            options={location.countries}
                                        />
                                    )}
                                />
                            </Grid>
                            <Grid col={12} className="border-bottom border-base-lighter padding-bottom-2 padding-2">
                                <Controller
                                    control={control}
                                    name="comment"
                                    defaultValue={entry.comment}
                                    render={({ field: { onChange, value } }) => (
                                        <Grid col>
                                            <Grid className="flex-align-self-center">
                                                <Label htmlFor={'comment'}>Additional comments:</Label>
                                            </Grid>
                                            <Grid>
                                                <Textarea
                                                    onChange={onChange}
                                                    name="comment"
                                                    id="comment"
                                                    defaultValue={value}
                                                />
                                            </Grid>
                                        </Grid>
                                    )}
                                />
                            </Grid>
                        </Grid>
                    </div>
                    <div className="border-top border-base-lighter padding-2 margin-left-auto">
                        <ButtonGroup className="flex-justify-end">
                            <Button
                                type="button"
                                className="margin-top-0"
                                data-testid="cancel-btn"
                                outline
                                onClick={onCancel}>
                                Go Back
                            </Button>
                            <Button
                                disabled={!isValid}
                                onClick={handleSubmit(onSubmit)}
                                type="submit"
                                className="padding-105 text-center margin-top-0">
                                {action}
                            </Button>
                        </ButtonGroup>
                    </div>
                </div>
            )}

            {unverified && (
                <span>
                    <ModalHeading
                        id="incomplete-form-confirmation-modal-heading"
                        className="border-bottom border-base-lighter font-sans-lg padding-2">
                        Unverified Address
                    </ModalHeading>
                    <div className="margin-2 grid-row flex-no-wrap border-left-1 border-accent-warm flex-align-center">
                        <Icon.Warning className="font-sans-2xl margin-x-2" />
                        <p className="" id="incomplete-form-confirmation-modal-description">
                            We were unable to find a valid address matching what was entered. Are you sure you want to
                            continue?
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
                            <Button onClick={() => setUnverified(false)} outline type={'button'}>
                                Go back
                            </Button>
                            <Button onClick={() => setUnverified(false)} type={'button'}>
                                Continue anyways
                            </Button>
                        </ButtonGroup>
                    </ModalFooter>
                </span>
            )}

            {verified && (
                <span className="verified-address-suggestion-modal">
                    <ModalHeading
                        id="incomplete-form-confirmation-modal-heading"
                        className="border-bottom border-base-lighter font-sans-lg padding-2">
                        Verified Address
                    </ModalHeading>
                    <div className="margin-2 grid-row flex-no-wrap border-left-1 border-accent-warm flex-align-center">
                        <Icon.Warning className="font-sans-2xl margin-x-2" />
                        <p className="" id="incomplete-form-confirmation-modal-description">
                            You are about to add a new patient with invalid inputs. We found a valid address. Would you
                            like to update to the valid address found?
                        </p>
                    </div>
                    <div className="margin-left-9">
                        <p className="padding-right-2" id="incomplete-form-confirmation-modal-description">
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
                            <Button onClick={() => setVerified(false)} outline type={'button'}>
                                Go back
                            </Button>
                            <Button onClick={() => setVerified(false)} outline type={'button'}>
                                Continue without update
                            </Button>{' '}
                            <Button
                                onClick={() => {
                                    handleSuggestionSelection(0, location.states);
                                    setVerified(false);
                                }}
                                type={'button'}>
                                Update address and continue
                            </Button>
                        </ButtonGroup>
                    </ModalFooter>
                </span>
            )}
        </div>
    );
};
