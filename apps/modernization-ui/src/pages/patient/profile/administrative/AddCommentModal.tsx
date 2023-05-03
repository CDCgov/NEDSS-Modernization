import { Ref } from 'react';
import { ModalComponent } from '../../../../components/ModalComponent/ModalComponent';
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
import { DatePickerInput } from '../../../../components/FormInputs/DatePickerInput';

type AddCommentModalProps = {
    modalRef: Ref<ModalRef> | undefined;
    modalHead?: string;
};

const ModalBody = ({ control, onSubmit, modalRef }: any) => {
    return (
        <Form onSubmit={onSubmit} className="width-full maxw-full modal-form">
            <div className="modal-body">
                <Grid row>
                    <Grid col={12} className="border-bottom border-base-lighter padding-bottom-2 padding-2">
                        <Controller
                            control={control}
                            name="administrativeDate"
                            render={({ field: { onChange, value } }) => (
                                <DatePickerInput
                                    flexBox
                                    defaultValue={value}
                                    onChange={onChange}
                                    name="administrativeDate"
                                    htmlFor={'administrativeDate'}
                                    label="Administrative as of"
                                />
                            )}
                        />
                    </Grid>
                    <Grid col={12} className="border-bottom border-base-lighter padding-bottom-2 padding-2">
                        <Controller
                            control={control}
                            name="additionalComments"
                            render={({ field: { onChange } }) => (
                                <Grid row>
                                    <Grid col={6} className="flex-align-self-center">
                                        <Label htmlFor={'additionalComments'}>Additional comments:</Label>
                                    </Grid>
                                    <Grid col={6}>
                                        <Textarea
                                            onChange={onChange}
                                            name="additionalComments"
                                            id={'additionalComments'}
                                        />
                                    </Grid>
                                </Grid>
                            )}
                        />
                    </Grid>
                </Grid>
            </div>

            <ModalFooter className="border-top border-base-lighter padding-2 margin-left-auto margin-0">
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

export const AddCommentModal = ({ modalRef, modalHead }: AddCommentModalProps) => {
    const methods = useForm();
    const { handleSubmit, control } = methods;

    const onSubmit = (data: any) => {
        console.log(data);
    };

    return (
        <ModalComponent
            modalRef={modalRef}
            modalHeading={modalHead}
            modalBody={<ModalBody control={control} onSubmit={handleSubmit(onSubmit)} modalRef={modalRef} />}
        />
    );
};
