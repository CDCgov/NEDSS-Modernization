import { useOptions } from 'apps/page-builder/hooks/api/useOptions';
import { useFormContext, useWatch } from 'react-hook-form';
import { CreateQuestionForm } from '../QuestionForm';
import { CodedFields } from './CodedFields';
import { DateFields } from './DateFields';
import { NumericFields } from './NumericFields';
import { TextFields } from './TextFields';

export const QuestionSpecificFields = () => {
    const form = useFormContext<CreateQuestionForm>();
    const questionType = useWatch({ control: form.control, name: 'questionType', exact: true });
    const { options: maskOptions } = useOptions('NBS_MASK_TYPE');

    return (
        <>
            {questionType === 'CODED' && <CodedFields />}
            {questionType === 'NUMERIC' && <NumericFields maskOptions={maskOptions} />}
            {questionType === 'TEXT' && <TextFields maskOptions={maskOptions} />}
            {questionType === 'DATE' && <DateFields maskOptions={maskOptions} />}
        </>
    );
};
