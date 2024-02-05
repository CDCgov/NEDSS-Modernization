import { useOptions } from 'apps/page-builder/hooks/api/useOptions';
import { useFormContext, useWatch } from 'react-hook-form';
import { CreateQuestionForm } from '../QuestionForm';
import { CodedFields } from './CodedFields';
import { DateFields } from './DateFields';
import { NumericFields } from './NumericFields';
import { TextFields } from './TextFields';

export const QuestionSpecificFields = () => {
    const watch = useWatch(useFormContext<CreateQuestionForm>());
    const { options: maskOptions } = useOptions('NBS_MASK_TYPE');

    return (
        <>
            {watch.questionType === 'CODED' && <CodedFields />}
            {watch.questionType === 'NUMERIC' && <NumericFields maskOptions={maskOptions} />}
            {watch.questionType === 'TEXT' && <TextFields maskOptions={maskOptions} />}
            {watch.questionType === 'DATE' && <DateFields maskOptions={maskOptions} />}
        </>
    );
};
