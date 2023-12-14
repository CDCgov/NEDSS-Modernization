import { ButtonGroup, ModalFooter, ModalRef, ModalToggleButton } from '@trussworks/react-uswds';
import {
    AddDefault,
    AddHyperlink,
    AddReadOnlyComments,
    PageStaticControllerService
} from 'apps/page-builder/generated';
import { Input } from 'components/FormInputs/Input';
import { SelectInput } from 'components/FormInputs/SelectInput';
import { ModalComponent } from 'components/ModalComponent/ModalComponent';
import { RefObject } from 'react';
import { Controller, FormProvider, useForm, useWatch } from 'react-hook-form';
import { maxLengthRule } from 'validation/entry/maxLengthRule';
import { HyperlinkFields } from './HyperlinkFields';
import { CommentsFields } from './CommentsFields';
import { authorization } from 'authorization';
import { usePageManagement } from '../../usePageManagement';

const staticType = [
    { value: 'LIN', name: 'Line separator' },
    { value: 'HYP', name: 'Hyperlink' },
    { value: 'COM', name: 'Comments (read-only)' },
    { value: 'PAR', name: 'Participant list (read-only)' },
    { value: 'ELE', name: 'Electronic document list (read-only)' }
];

type AddStaticElementModalProps = {
    modalRef: RefObject<ModalRef>;
    subsectionId: number;
};

type StaticElementType = {
    type: 'HYP' | 'COM' | 'LIN' | 'PAR' | 'ELE';
};

type StaticElementFormValues = (AddReadOnlyComments | AddHyperlink | AddDefault) & StaticElementType;

const AddStaticElementModal = ({ modalRef, subsectionId }: AddStaticElementModalProps) => {
    const form = useForm<StaticElementFormValues>({
        mode: 'onBlur'
    });
    const watch = useWatch({ control: form.control });
    const { page } = usePageManagement();

    const handleSubmit = () => {
        onSubmit();
    };
    const onSubmit = form.handleSubmit(async (data) => {
        switch (data.type) {
            case 'HYP': {
                data.subSectionId = subsectionId;
                PageStaticControllerService.addStaticHyperLinkUsingPost({
                    authorization: authorization(),
                    page: page.id,
                    request: data
                });
                form.reset();
                break;
            }
            case 'COM': {
                data.subSectionId = subsectionId;
                PageStaticControllerService.addStaticReadOnlyCommentsUsingPost({
                    authorization: authorization(),
                    page: page.id,
                    request: data
                });
                form.reset();
                break;
            }
            case 'LIN': {
                data.subSectionId = subsectionId;
                PageStaticControllerService.addStaticLineSeparatorUsingPost({
                    authorization: authorization(),
                    page: page.id,
                    request: data
                });
                form.reset();
                break;
            }
            case 'ELE': {
                data.subSectionId = subsectionId;
                PageStaticControllerService.addStaticOriginalElectronicDocListUsingPost({
                    authorization: authorization(),
                    page: page.id,
                    request: data
                });
                form.reset();
                break;
            }
            case 'PAR': {
                data.subSectionId = subsectionId;
                PageStaticControllerService.addStaticReadOnlyParticipantsListUsingPost({
                    authorization: authorization(),
                    page: page.id,
                    request: data
                });
                form.reset();
                break;
            }
        }
    });

    return (
        <ModalComponent
            modalRef={modalRef}
            modalHeading={'Add static element'}
            modalBody={
                <>
                    <Controller
                        control={form.control}
                        name="type"
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
                    {watch.type != undefined && (
                        <>
                            {watch.type == 'HYP' ? (
                                <>
                                    <FormProvider {...form}>
                                        <HyperlinkFields />
                                    </FormProvider>
                                </>
                            ) : (
                                <>
                                    {watch.type == 'COM' && (
                                        <>
                                            <FormProvider {...form}>
                                                <CommentsFields />
                                            </FormProvider>
                                        </>
                                    )}
                                </>
                            )}
                            <Controller
                                control={form.control}
                                name="adminComments"
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
                            <ModalToggleButton modalRef={modalRef} closer outline onClick={() => form.reset()}>
                                Cancel
                            </ModalToggleButton>
                            <ModalToggleButton
                                modalRef={modalRef}
                                closer
                                disabled={!form.formState.isValid}
                                onClick={handleSubmit}>
                                Save Changes
                            </ModalToggleButton>
                        </ButtonGroup>
                    </ModalFooter>
                </>
            }></ModalComponent>
    );
};

export default AddStaticElementModal;
