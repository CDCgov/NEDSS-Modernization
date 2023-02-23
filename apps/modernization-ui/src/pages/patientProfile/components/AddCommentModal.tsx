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
                            name="administrativeDate"
                            render={({ field: { onChange, value } }) => (
                                <DatePickerInput
                                    defaultValue={value}
                                    onChange={onChange}
                                    name="administrativeDate"
                                    htmlFor={'administrativeDate'}
                                    label="Administrative as of"
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

export const AddCommentModal = ({ modalRef }: AddCommentModalProps) => {
    const methods = useForm();
    const { handleSubmit, control } = methods;

    const onSubmit = (data: any) => {
        console.log(data);
    };

    return (
        <ModalComponent
            modalRef={modalRef}
            modalHeading="Add - Comment"
            modalBody={<ModalBody control={control} onSubmit={handleSubmit(onSubmit)} modalRef={modalRef} />}
        />
    );
};
