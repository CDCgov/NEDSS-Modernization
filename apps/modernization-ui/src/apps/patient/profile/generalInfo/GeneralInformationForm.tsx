import { Button, ButtonGroup } from '@trussworks/react-uswds';
import { FieldValues, FormProvider, useForm } from 'react-hook-form';
import { maybeNumber, orNull } from 'utils';
import { GeneralInformationFields } from './GeneralInformationFields';
import { GeneralInformationEntry } from './GeneralInformationEntry';

type Props = {
    entry?: GeneralInformationEntry | null;
    onChanged?: (updated: GeneralInformationEntry) => void;
    onCancel?: () => void;
};

export const GeneralPatientInformationForm = ({ entry, onChanged = () => {}, onCancel = () => {} }: Props) => {
    const form = useForm<GeneralInformationEntry>({ mode: 'onBlur', defaultValues: entry ? entry : {} });

    const onSubmit = (entered: FieldValues) => {
        onChanged({
            asOf: entered?.asOf,
            maritalStatus: orNull(entered?.maritalStatus),
            maternalMaidenName: entered?.maternalMaidenName,
            adultsInHouse: maybeNumber(entered?.adultsInHouse),
            childrenInHouse: maybeNumber(entered?.childrenInHouse),
            occupation: orNull(entered?.occupation),
            educationLevel: orNull(entered?.educationLevel),
            primaryLanguage: orNull(entered?.primaryLanguage),
            speaksEnglish: orNull(entered?.speaksEnglish),
            stateHIVCase: entered?.stateHIVCase
        });
    };

    return (
        <>
            <FormProvider {...form}>
                <GeneralInformationFields />
            </FormProvider>
            <div className="border-top border-base-lighter padding-2 margin-left-auto">
                <ButtonGroup className="flex-justify-end">
                    <Button type="button" className="margin-top-0" outline onClick={onCancel}>
                        Cancel
                    </Button>
                    <Button
                        onClick={form.handleSubmit(onSubmit)}
                        type="submit"
                        className="padding-105 text-center margin-top-0"
                        disabled={!form.formState.isValid}>
                        Save
                    </Button>
                </ButtonGroup>
            </div>
        </>
    );
};
