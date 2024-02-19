import { CreateCodedQuestionRequest } from 'apps/page-builder/generated';
import { useValueset } from 'apps/page-builder/hooks/api/useValueset';
import { useEffect, useState } from 'react';
import { useFormContext } from 'react-hook-form';
import { AddValueset } from '../../AddValueset/AddValueset';
import { EditValueset } from '../../EditValueset/EditValueset';

type Props = {
    onCancel: () => void;
    onClose: () => void;
    onAccept: () => void;
};
export const CreateEditValueset = ({ onClose, onCancel, onAccept }: Props) => {
    const [state, setState] = useState<'create' | 'edit'>('create');
    const form = useFormContext<CreateCodedQuestionRequest>();
    const { valueset, fetch } = useValueset();

    useEffect(() => {
        if (valueset) {
            form.setValue('valueSet', valueset.id);
        }
    }, [valueset]);

    return (
        <>
            {state === 'create' && (
                <AddValueset
                    onCancel={onCancel}
                    onClose={onClose}
                    onCreated={(valueset: string) => {
                        fetch(valueset);
                        setState('edit');
                    }}
                />
            )}
            {state === 'edit' && valueset && (
                <EditValueset onAccept={onAccept} onCancel={onCancel} onClose={onClose} valueset={valueset} />
            )}
        </>
    );
};
