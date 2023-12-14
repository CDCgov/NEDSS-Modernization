import { ButtonGroup, ModalFooter, ModalRef, ModalToggleButton } from '@trussworks/react-uswds';
import { AddDefault, AddHyperlink, AddReadOnlyComments } from 'apps/page-builder/generated';
import { Input } from 'components/FormInputs/Input';
import { SelectInput } from 'components/FormInputs/SelectInput';
import { ModalComponent } from 'components/ModalComponent/ModalComponent';
import { RefObject } from 'react';
import { Controller, useForm, useWatch } from 'react-hook-form';
import { maxLengthRule } from 'validation/entry/maxLengthRule';

const staticType = [
    { value: 'HYP', name: 'Hyperlink' },
    { value: 'COM', name: 'Comments' },
    { value: 'LIN', name: 'Line separator' },
    { value: 'PAR', name: 'Participant list (read-only)' },
    { value: 'ELE', name: 'Electronic document list (read-only)' }
];

type AddStaticElementModalProps = {
    modalRef: RefObject<ModalRef>;
};

type StaticElementType = {
    type: 'HYP' | 'COM' | 'LIN' | 'PAR' | 'ELE';
};

type StaticElementFormValues = {
    type: (AddReadOnlyComments | AddHyperlink | AddDefault) & StaticElementType;
};

const AddStaticElementModal = ({ modalRef }: AddStaticElementModalProps) => {
    const form = useForm<StaticElementFormValues>({ mode: 'onBlur' });
    const watch = useWatch({ control: form.control });

    return (
        <ModalComponent
            modalRef={modalRef}
            modalHeading={'Add static element'}
            modalBody={
                <>
                    <Controller
                        control={form.control}
                        name="type.type"
                        rules={{ required: { value: true, message: 'Static element type is required.' } }}
                        render={({ field: { onBlur, onChange, value }, fieldState: { error } }) => (
                            <SelectInput
                                label="Choose a static element"
                                options={staticType}
                                required
                                defaultValue={value}
                                onChange={onChange}
                                onBlur={onBlur}
                                error={error?.message}></SelectInput>
                        )}
                    />
                    {watch.type?.type != ('HYP' || 'COM' || 'LIN' || 'PAR' || 'ELE') ? (
                        <></>
                    ) : (
                        <>
                            {watch.type.type == 'HYP' ? (
                                <>
                                    <Controller
                                        control={form.control}
                                        name="type.label"
                                        rules={{
                                            required: { value: true, message: 'Label is required' },
                                            ...maxLengthRule(50)
                                        }}
                                        render={({ field: { onBlur, onChange, value }, fieldState: { error } }) => (
                                            <Input
                                                onChange={onChange}
                                                onBlur={onBlur}
                                                defaultValue={value}
                                                label="Label"
                                                type="text"
                                                error={error?.message}
                                                required
                                            />
                                        )}
                                    />
                                    <Controller
                                        control={form.control}
                                        name="type.linkUrl"
                                        rules={{
                                            required: { value: true, message: 'Link is required' },
                                            ...maxLengthRule(50)
                                        }}
                                        render={({ field: { onBlur, onChange, value }, fieldState: { error } }) => (
                                            <Input
                                                onChange={onChange}
                                                onBlur={onBlur}
                                                defaultValue={value}
                                                label="Link URL"
                                                type="text"
                                                error={error?.message}
                                                required
                                            />
                                        )}
                                    />
                                </>
                            ) : (
                                <></>
                            )}
                            <>
                                {watch.type.type == 'COM' ? (
                                    <>
                                        <Controller
                                            control={form.control}
                                            name="type.commentsText"
                                            rules={{
                                                required: { value: true, message: 'Comments is required' },
                                                ...maxLengthRule(2000)
                                            }}
                                            render={({ field: { onBlur, onChange, value }, fieldState: { error } }) => (
                                                <Input
                                                    onChange={onChange}
                                                    onBlur={onBlur}
                                                    defaultValue={value}
                                                    label="Comments text"
                                                    required
                                                    type="text"
                                                    error={error?.message}
                                                    multiline
                                                />
                                            )}
                                        />
                                    </>
                                ) : (
                                    <> </>
                                )}
                            </>

                            <Controller
                                control={form.control}
                                name="type.adminComments"
                                rules={{ ...maxLengthRule(2000) }}
                                render={({ field: { onBlur, onChange, value }, fieldState: { error } }) => (
                                    <Input
                                        onChange={onChange}
                                        onBlur={onBlur}
                                        defaultValue={value}
                                        label="Administrative Comments"
                                        type="text"
                                        multiline
                                        error={error?.message}
                                    />
                                )}
                            />
                        </>
                    )}

                    <ModalFooter className="padding-2 margin-left-auto footer">
                        <ButtonGroup className="flex-justify-end">
                            <ModalToggleButton modalRef={modalRef} closer outline>
                                Cancel
                            </ModalToggleButton>
                            <ModalToggleButton modalRef={modalRef} closer disabled={!form.formState.isValid}>
                                Save Changes
                            </ModalToggleButton>
                        </ButtonGroup>
                    </ModalFooter>
                </>
            }></ModalComponent>
    );
};

export default AddStaticElementModal;
