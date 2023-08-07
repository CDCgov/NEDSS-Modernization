import { ChangeEvent, useEffect, useRef, useState } from 'react';
import { Controller, useFormContext, useWatch } from 'react-hook-form';
import {
    Button,
    ButtonGroup,
    ErrorMessage,
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
import FormCard from 'components/FormCard/FormCard';
import { SelectInput } from 'components/FormInputs/SelectInput';

import { CodedValue } from 'coded';
import { Input } from 'components/FormInputs/Input';
import { StateCodedValue } from 'location';

export interface AddressSuggestion {
    street_line: string;
    secondary: string;
    city: string;
    state: string;
    zipcode: string;
    entries: number;
}

type CodedValueLists = {
    states: StateCodedValue[];
    countries: CodedValue[];
    byState: (state: string) => CodedValue[];
};

type Props = {
    id: string;
    title: string;
    coded: CodedValueLists;
};

export default function AddressFields({ id, title, coded }: Props) {
    const wrapperRef = useRef<any>(null);
    const [showSuggestions, setShowSuggestions] = useState<boolean>(false);
    const [suggestions, setSuggestions] = useState<AddressSuggestion[]>([]);

    const [verified, setVerified] = useState<boolean>(false);
    const [unverified, setUnverified] = useState<boolean>(false);
    const verifiedModalRef = useRef<ModalRef>(null);
    const unverifiedModalRef = useRef<ModalRef>(null);

    const { control, setValue } = useFormContext();

    const selectedState = useWatch({ control, name: 'state' });

    const counties = coded.byState(selectedState);

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

    async function populateSuggestions(eve: ChangeEvent<HTMLInputElement>) {
        if (eve.target.value.length > 2) {
            const data = await fetch(
                `https://us-autocomplete-pro.api.smarty.com/lookup?key=166215385741384990&search=${eve.target.value}`,
                {
                    headers: {
                        referer: 'localhost'
                    }
                }
            ).then((resp) => resp.json());
            setSuggestions(data.suggestions);
        }
        setShowSuggestions(true);
    }

    function handleSuggestionSelection(selected: AddressSuggestion) {
        setValue('streetAddress1', selected.street_line);
        setValue('streetAddress2', selected.secondary);
        setValue('city', selected.city);

        const state = coded.states.find((state) => state.abbreviation === selected.state)?.value || '';
        setValue('state', state);
        setValue('zip', selected.zipcode);

        setShowSuggestions(false);
    }

    useEffect(() => {
        verifiedModalRef.current?.toggleModal(undefined, true);
    }, [verified]);

    useEffect(() => {
        unverifiedModalRef.current?.toggleModal(undefined, true);
    }, [unverified]);

    const handleKeyDown = async (event: any) => {
        setVerified(false);
        setUnverified(false);
        switch (event.key) {
            case 'Enter' || 'enter': {
                event.preventDefault();
                await populateSuggestions(event);
                setShowSuggestions(false);
            }
        }
    };

    return (
        <FormCard id={id} title={title}>
            <Grid col={12} className="padding-x-3 padding-bottom-3 address-fields-grid">
                <Grid row>
                    <Grid col={6}>
                        <Controller
                            control={control}
                            name="streetAddress1"
                            render={({ field: { onChange, value } }) => (
                                <Input
                                    onChange={(v: any) => {
                                        populateSuggestions(v);
                                        onChange(v);
                                    }}
                                    type="text"
                                    label="Street address 1"
                                    defaultValue={value}
                                    htmlFor="streetAddress1"
                                    id="streetAddress1"
                                    onKeyDown={handleKeyDown}
                                />
                            )}
                        />
                        {showSuggestions && (
                            <div className="button-group">
                                <ul ref={wrapperRef} id="basic-nav-section-one" className="usa-nav__submenu">
                                    {suggestions.map((suggestion, idx) => (
                                        <li key={idx} className="usa-nav__submenu-item">
                                            <Button
                                                key={idx}
                                                onClick={() => handleSuggestionSelection(suggestion)}
                                                type={'button'}
                                                unstyled>
                                                <span className="address-suggestion-line">
                                                    {suggestion.street_line}
                                                </span>
                                                <span className="address-suggestion-line">
                                                    {suggestion.city}, {suggestion.state} {suggestion.zipcode}
                                                </span>
                                            </Button>
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
                                    className="padding-0 verified-modal"
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
                                                onClick={() => {
                                                    handleSuggestionSelection(suggestions[0]);
                                                    setVerified(false);
                                                }}
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
                        <Controller
                            control={control}
                            name="streetAddress2"
                            render={({ field: { onChange, value } }) => (
                                <Input
                                    onChange={onChange}
                                    type="text"
                                    label="Street address 2"
                                    defaultValue={value}
                                    htmlFor="streetAddress2"
                                    id="streetAddress2"
                                />
                            )}
                        />
                    </Grid>
                </Grid>
                <Grid row>
                    <Grid col={6}>
                        <Controller
                            control={control}
                            name="city"
                            render={({ field: { onChange, value } }) => (
                                <Input
                                    id="city"
                                    name="city"
                                    type="text"
                                    label="City"
                                    htmlFor="city"
                                    defaultValue={value}
                                    onChange={onChange}
                                />
                            )}
                        />
                    </Grid>
                </Grid>
                <Grid row gap={2}>
                    <Grid col={4}>
                        <Controller
                            control={control}
                            name="state"
                            render={({ field: { onChange, value } }) => (
                                <SelectInput
                                    onChange={onChange}
                                    defaultValue={value}
                                    name="state"
                                    htmlFor={'state'}
                                    label="State"
                                    options={coded.states}
                                />
                            )}
                        />
                    </Grid>
                    <Grid col={2}>
                        <Controller
                            control={control}
                            name="zip"
                            rules={{ pattern: { value: /[\d]{5}(-[\d]{4})?/, message: 'Invalid zip' } }}
                            render={({ field: { onChange, value }, fieldState: { error } }) => (
                                <>
                                    <Label htmlFor="zip">ZIP</Label>
                                    <TextInput
                                        id="zip"
                                        name="zip"
                                        type="text"
                                        inputSize="medium"
                                        defaultValue={value}
                                        onChange={onChange}
                                    />
                                    {error && <ErrorMessage>{error.message}</ErrorMessage>}
                                </>
                            )}
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
                                    options={counties}
                                />
                            )}
                        />
                    </Grid>
                </Grid>
                <Grid row>
                    <Grid col={6}>
                        <Controller
                            control={control}
                            name="censusTract"
                            rules={{
                                pattern: {
                                    value: /[0-9]{4}(.(([0-8][0-9])|([9][0-8])))?/,
                                    message:
                                        ' Census Tract should be in numeric XXXX or XXXX.xx format where XXXX is the basic tract and xx is the suffix. XXXX ranges from 0001 to 9999. The suffix is limited to a range between .01 and .98.'
                                }
                            }}
                            render={({ field: { onChange, value }, fieldState: { error } }) => (
                                <Input
                                    onChange={onChange}
                                    type="text"
                                    label="Census Tract"
                                    defaultValue={value}
                                    htmlFor="censusTract"
                                    id="censusTract"
                                    error={error?.message}
                                />
                            )}
                        />
                    </Grid>
                </Grid>
                <Grid row>
                    <Grid col={6}>
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
                                    options={coded.countries}
                                />
                            )}
                        />
                    </Grid>
                </Grid>
            </Grid>
        </FormCard>
    );
}
