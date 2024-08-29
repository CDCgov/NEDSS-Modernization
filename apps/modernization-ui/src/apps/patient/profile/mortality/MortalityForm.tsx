import { Button, ButtonGroup } from '@trussworks/react-uswds';
import { FieldValues, FormProvider, useForm } from 'react-hook-form';
import { orNull } from 'utils';
import { MortalityEntryFields } from './MortalityEntryFields';
import { internalizeDate } from 'date';
import { MortalityEntry } from './MortalityEntry';

type Props = {
    entry: MortalityEntry;
    onChanged: (updated: MortalityEntry) => void;
    onCancel: () => void;
};

export const MortalityForm = ({ entry, onChanged, onCancel }: Props) => {
    const form = useForm<MortalityEntry>({
        mode: 'onBlur',
        defaultValues: { ...entry, asOf: entry.asOf ? entry.asOf : internalizeDate(new Date()) }
    });

    const onSubmit = (entered: FieldValues) => {
        onChanged({
            asOf: entered.asOf,
            deceased: entered.deceased,
            deceasedOn: entered.deceasedOn,
            city: entered.city,
            state: orNull(entered.state),
            county: orNull(entered.county),
            country: orNull(entered.country)
        });
    };

    return (
        <>
            <FormProvider {...form}>
                <MortalityEntryFields />
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
