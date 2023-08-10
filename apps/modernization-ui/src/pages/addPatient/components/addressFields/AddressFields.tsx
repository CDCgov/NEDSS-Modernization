import { useEffect, useRef, useState } from 'react';
import { Controller, useFormContext, useWatch } from 'react-hook-form';
import { LocationCodedValues } from 'location';
import { AddressSuggestion, AddressSuggestionInput } from 'address/suggestion';

import {
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

import { Input } from 'components/FormInputs/Input';

type Props = {
    id: string;
    title: string;
    coded: LocationCodedValues;
};

export default function AddressFields({ id, title, coded }: Props) {
    const [verified, setVerified] = useState<boolean>(false);
    const [unverified, setUnverified] = useState<boolean>(false);
    const verifiedModalRef = useRef<ModalRef>(null);
    const unverifiedModalRef = useRef<ModalRef>(null);

    const { control, setValue } = useFormContext();

    const selectedState = useWatch({ control, name: 'state' });
    const enteredCity = useWatch({ control, name: 'city' });
    const enteredZip = useWatch({ control, name: 'zip' });

    const counties = coded.counties.byState(selectedState);

    function handleSuggestionSelection(selected: AddressSuggestion) {
        setValue('streetAddress1', selected.address1);
        setValue('city', selected.city);
        setValue('state', selected.state?.value);
        setValue('zip', selected.zip);
    }

    useEffect(() => {
        verifiedModalRef.current?.toggleModal(undefined, true);
    }, [verified]);

    useEffect(() => {
        unverifiedModalRef.current?.toggleModal(undefined, true);
    }, [unverified]);

    return (
        <FormCard id={id} title={title}>
            <Grid col={12} className="padding-x-3 padding-bottom-3 address-fields-grid">
                <Grid row>
                    <Grid col={6}>
                        <Controller
                            control={control}
                            name="streetAddress1"
                            render={({ field: { onChange, value, name } }) => (
                                <AddressSuggestionInput
                                    id={name}
                                    locations={coded}
                                    criteria={{ zip: enteredZip, city: enteredCity, state: selectedState }}
                                    label="Street address 1"
                                    defaultValue={value}
                                    onChange={onChange}
                                    onSelection={handleSuggestionSelection}
                                />
                            )}
                        />

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
                                            <p>street_line</p>
                                            <p>city, state zipcode</p>
                                        </div>
                                        <div className="margin-right-9">
                                            <p className="text-bold">Valid address found:</p>
                                            <p>street_line</p>
                                            <p>city, state zipcode</p>
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
                                    options={coded.states.all}
                                />
                            )}
                        />
                    </Grid>
                    <Grid col={2}>
                        <Controller
                            control={control}
                            name="zip"
                            rules={{ pattern: { value: /[\d]{5}(-[\d]{4})?/, message: 'Invalid zip' } }}
                            render={({ field: { onChange, value, name }, fieldState: { error } }) => (
                                <>
                                    <Label htmlFor={name}>ZIP</Label>
                                    <TextInput
                                        id={name}
                                        name={name}
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
