import { Button } from 'components/button';
import { useEffect, useState } from 'react';
import { useFormContext } from 'react-hook-form';
import { BlockingField, BlockingFieldOption, blockingFieldOptions, MatchingConfiguration } from '../../Configuration';

type Props = {
    activePass: number;
    onAccept: (selectedFields: BlockingField[]) => void;
    onCancel: () => void;
};
export const BlockingCriteriaFieldSelection = ({ activePass, onAccept, onCancel }: Props) => {
    const [fields, setFields] = useState<BlockingFieldOption[]>(blockingFieldOptions);
    const form = useFormContext<MatchingConfiguration>();

    useEffect(() => {
        console.log('here', form.getValues(`passes.${activePass}.blockingCriteria`));
        const activeFields = form.getValues(`passes.${activePass}.blockingCriteria`)?.map((a) => a.field);
        setFields(
            blockingFieldOptions?.map((o) => {
                return { ...o, active: activeFields?.includes(o.value) };
            })
        );
    }, [form.getValues(`passes.${activePass}.blockingCriteria`)]);

    const handleToggleField = (index: number) => {
        console.log('clicked', fields[index]);
        setFields((prev) =>
            prev.map((p, i) => {
                if (index === i) {
                    return { ...p, active: !p.active };
                }
                return p;
            })
        );
    };

    const handleAccept = () => {
        onAccept(fields.filter((f) => f.active).map((f) => f.value));
    };

    return (
        <div>
            <div>
                {fields?.map((f, i) => (
                    <div key={i} onClick={() => handleToggleField(i)}>
                        Field: {f.label}, value: {f.value}, active: {f.active ? 'true' : 'false'}
                    </div>
                ))}
            </div>
            <Button outline onClick={onCancel}>
                Cancel
            </Button>
            <Button onClick={handleAccept}>Accept</Button>
        </div>
    );
};
