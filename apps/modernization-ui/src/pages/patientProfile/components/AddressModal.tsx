import { Ref } from 'react';
import { ModalComponent } from '../../../components/ModalComponent/ModalComponent';
import {
    Button,
    ButtonGroup,
    Form,
    Grid,
    Label,
    ModalFooter,
    ModalRef,
    ModalToggleButton,
    Textarea
} from '@trussworks/react-uswds';
import { Controller, useForm } from 'react-hook-form';
import { DatePickerInput } from '../../../components/FormInputs/DatePickerInput';
import { SelectInput } from '../../../components/FormInputs/SelectInput';
import { Input } from '../../../components/FormInputs/Input';
import { stateList } from '../../../constant/states';

type AddCommentModalProps = {
    modalRef: Ref<ModalRef> | undefined;
};

const ModalBody = ({ control, onSubmit, modalRef }: any) => {
    return (
        <Form onSubmit={onSubmit} className="width-full maxw-full modal-form">
            <div className="padding-2 modal-body">
                <Grid row>
                    <Grid col={10}>
                        <Controller
                            control={control}
                            name="addressAsOf"
                            render={({ field: { onChange, value } }) => (
                                <DatePickerInput
                                    defaultValue={value}
                                    onChange={onChange}
                                    name="addressAsOf"
                                    htmlFor={'addressAsOf'}
                                    label="Address as of"
                                />
                            )}
                        />
                    </Grid>
                    <Grid col={10}>
                        <Controller
                            control={control}
                            name="type"
                            render={({ field: { onChange, value } }) => (
                                <SelectInput
                                    defaultValue={value}
                                    onChange={onChange}
                                    name="type"
                                    htmlFor={'type'}
                                    label="Type"
                                    options={[]}
                                />
                            )}
                        />
                    </Grid>
                    <Grid col={10}>
                        <Controller
                            control={control}
                            name="use"
                            render={({ field: { onChange, value } }) => (
                                <SelectInput
                                    defaultValue={value}
                                    onChange={onChange}
                                    name="use"
                                    htmlFor={'use'}
                                    label="Use"
                                    options={[]}
                                />
                            )}
                        />
                    </Grid>
                    <Grid col={10}>
                        <Controller
                            control={control}
                            name="address1"
                            render={({ field: { onChange, value } }) => (
                                <Input
                                    onChange={onChange}
                                    defaultValue={value}
                                    type="text"
                                    label="Street address 1"
                                    name="address1"
                                    htmlFor="address1"
                                    id="address1"
                                />
                            )}
                        />
                    </Grid>
                    <Grid col={10}>
                        <Controller
                            control={control}
                            name="address2"
                            render={({ field: { onChange, value } }) => (
                                <Input
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
                    <Grid col={10}>
                        <Controller
                            control={control}
                            name="city"
                            render={({ field: { onChange, value } }) => (
                                <Input
                                    onChange={onChange}
                                    defaultValue={value}
                                    type="text"
                                    label="City"
                                    htmlFor="city"
                                    id="city"
                                />
                            )}
                        />
                    </Grid>
                    <Grid col={10}>
                        <Controller
                            control={control}
                            name="state"
                            render={({ field: { onChange, value } }) => (
                                <SelectInput
                                    defaultValue={value}
                                    onChange={onChange}
                                    htmlFor={'state'}
                                    label="State"
                                    options={stateList}
                                />
                            )}
                        />
                    </Grid>
                    <Grid col={10}>
                        <Controller
                            control={control}
                            name="zip"
                            render={({ field: { onChange, value } }) => (
                                <Input
                                    onChange={onChange}
                                    defaultValue={value}
                                    type="text"
                                    label="Zip code"
                                    htmlFor="zip"
                                    id="zip"
                                />
                            )}
                        />
                    </Grid>
                    <Grid col={10}>
                        <Controller
                            control={control}
                            name="county"
                            render={({ field: { onChange, value } }) => (
                                <SelectInput
                                    defaultValue={value}
                                    onChange={onChange}
                                    htmlFor={'county'}
                                    label="County"
                                    options={stateList}
                                />
                            )}
                        />
                    </Grid>
                    <Grid col={10}>
                        <Controller
                            control={control}
                            name="country"
                            render={({ field: { onChange, value } }) => (
                                <SelectInput
                                    defaultValue={value}
                                    onChange={onChange}
                                    htmlFor={'country'}
                                    label="Country"
                                    options={stateList}
                                />
                            )}
                        />
                    </Grid>
                    <Grid col={10}>
                        <Controller
                            control={control}
                            name="censusTract"
                            render={({ field: { onChange, value } }) => (
                                <Input
                                    onChange={onChange}
                                    defaultValue={value}
                                    type="text"
                                    label="Census tract"
                                    htmlFor="censusTract"
                                    id="censusTract"
                                />
                            )}
                        />
                    </Grid>
                    <Grid col={10}>
                        <Controller
                            control={control}
                            name="additionalComments"
                            render={({ field: { onChange } }) => (
                                <>
                                    <Label htmlFor={'additionalComments'}>Additional comments</Label>
                                    <Textarea onChange={onChange} name="additionalComments" id={'additionalComments'} />
                                </>
                            )}
                        />
                    </Grid>
                </Grid>
            </div>

            <ModalFooter className="border-top border-base-lighter padding-2 margin-left-auto">
                <ButtonGroup>
                    <ModalToggleButton className="margin-top-0" outline modalRef={modalRef} closer>
                        Go back
                    </ModalToggleButton>
                    <Button type="submit" className="padding-105 text-center margin-top-0">
                        Add
                    </Button>
                </ButtonGroup>
            </ModalFooter>
        </Form>
    );
};

export const AddAddressModal = ({ modalRef }: AddCommentModalProps) => {
    const methods = useForm();
    const { handleSubmit, control } = methods;

    const onSubmit = (data: any) => {
        console.log(data);
    };

    return (
        <ModalComponent
            modalRef={modalRef}
            modalHeading="Add - Address"
            modalBody={<ModalBody control={control} onSubmit={handleSubmit(onSubmit)} modalRef={modalRef} />}
        />
    );
};
