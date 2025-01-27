import { Button, ButtonGroup } from '@trussworks/react-uswds';
import { externalizeDate, internalizeDate } from 'date';
import { FormProvider, useForm } from 'react-hook-form';
import { orNull } from 'utils';
import { EthnicityEntryFields } from './EthnicityEntryFields';
import { EthnicityEntry } from './EthnicityEntry';

type Props = {
    entry: EthnicityEntry;
    onChanged: (updated: EthnicityEntry) => void;
    onCancel: () => void;
};

export const EthnicityForm = ({ entry, onChanged = () => {}, onCancel = () => {} }: Props) => {
    const form = useForm<EthnicityEntry>({
        mode: 'onBlur',
        defaultValues: { ...entry, asOf: entry.asOf ? entry.asOf : internalizeDate(new Date()) }
    });

    const onSubmit = (entered: EthnicityEntry) => {
        onChanged({
            asOf: externalizeDate(entered.asOf),
            ethnicGroup: orNull(entered.ethnicGroup),
            unknownReason: orNull(entered.unknownReason),
            detailed: entered.detailed ? entered.detailed : []
        });
    };

    return (
        <>
            <FormProvider {...form}>
                <EthnicityEntryFields />
            </FormProvider>
            <div className="border-top border-base-lighter padding-2 margin-left-auto">
                <ButtonGroup className="flex-justify-end">
                    <Button type="button" className="margin-top-0" data-testid="cancel-btn" outline onClick={onCancel}>
                        Cancel
                    </Button>
                    <Button
                        disabled={!form.formState.isValid}
                        onClick={form.handleSubmit(onSubmit)}
                        type="submit"
                        className="padding-105 text-center margin-top-0">
                        Save
                    </Button>
                </ButtonGroup>
            </div>
        </>
    );
};
