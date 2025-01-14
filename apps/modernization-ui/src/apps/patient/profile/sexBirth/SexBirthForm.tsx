import { Button, ButtonGroup } from '@trussworks/react-uswds';
import { externalizeDate, externalizeDateTime } from 'date';
import { FormProvider, useForm } from 'react-hook-form';
import { maybeNumber, orNull } from 'utils';
import { BirthAndGenderEntryFields } from './BirthAndGenderEntryFields';
import { BirthAndGenderEntry } from './BirthAndGenderEntry';

type Props = {
    entry: BirthAndGenderEntry;
    onChanged: (updated: BirthAndGenderEntry) => void;
    onCancel: () => void;
};

export const SexBirthForm = ({ entry, onChanged, onCancel }: Props) => {
    const form = useForm<BirthAndGenderEntry>({ mode: 'onBlur', defaultValues: entry });

    const onSubmit = (entered: BirthAndGenderEntry) => {
        onChanged({
            asOf: externalizeDateTime(entered.asOf),
            birth: {
                bornOn: externalizeDate(entered.birth.bornOn),
                gender: orNull(entered.birth.gender),
                multipleBirth: orNull(entered.birth.multipleBirth),
                birthOrder: maybeNumber(entered.birth.birthOrder),
                city: entered.birth.city,
                state: orNull(entered.birth.state),
                county: orNull(entered.birth.county),
                country: orNull(entered.birth.country)
            },
            gender: {
                current: orNull(entered.gender.current),
                unknownReason: orNull(entered.gender.unknownReason),
                preferred: orNull(entered.gender.preferred),
                additional: entered.gender.additional
            }
        });
    };

    return (
        <>
            <FormProvider {...form}>
                <BirthAndGenderEntryFields />
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
