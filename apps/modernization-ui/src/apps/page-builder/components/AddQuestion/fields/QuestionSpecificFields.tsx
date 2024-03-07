import { useOptions } from 'apps/page-builder/hooks/api/useOptions';
import { useFormContext, useWatch } from 'react-hook-form';
import { CreateQuestionForm } from '../QuestionForm';
import { CodedFields } from './CodedFields';
import { DateFields } from './DateFields';
import { NumericFields } from './NumericFields';
import { TextFields } from './TextFields';

type Props = {
    onFindValueSet?: () => void;
    editing?: boolean;
    published?: boolean;
};
export const QuestionSpecificFields = ({ onFindValueSet, editing = false, published = false }: Props) => {
    const form = useFormContext<CreateQuestionForm>();
    const questionType = useWatch({ control: form.control, name: 'questionType', exact: true });
    const { options: maskOptions } = useOptions('NBS_MASK_TYPE');

    return (
        <>
            {questionType === 'CODED' && (
                <CodedFields editing={editing} published={published} onFindValueSet={onFindValueSet} />
            )}
            {questionType === 'NUMERIC' && (
                <NumericFields editing={editing} published={published} maskOptions={maskOptions} />
            )}
            {questionType === 'TEXT' && (
                <TextFields editing={editing} published={published} maskOptions={maskOptions} />
            )}
            {questionType === 'DATE' && <DateFields published={published} maskOptions={maskOptions} />}
        </>
    );
};
