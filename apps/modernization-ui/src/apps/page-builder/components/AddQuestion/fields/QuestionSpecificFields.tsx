import { useOptions } from 'apps/page-builder/hooks/api/useOptions';
import { Option } from 'generated';
import { useEffect, useState } from 'react';
import { useFormContext, useWatch } from 'react-hook-form';
import { CreateQuestionForm } from '../QuestionForm';
import { CodedFields } from './CodedFields';
import { DateFields } from './DateFields';
import { NumericFields } from './NumericFields';
import { TextFields } from './TextFields';

export const QuestionSpecificFields = () => {
    const watch = useWatch(useFormContext<CreateQuestionForm>());
    const [maskOptions, setMaskOptions] = useState<Option[]>([]);

    useEffect(() => {
        useOptions('NBS_MASK_TYPE').then((response) => setMaskOptions(response.options));
    }, []);
    return (
        <>
            {watch.questionType === 'CODED' && <CodedFields />}
            {watch.questionType === 'NUMERIC' && <NumericFields maskOptions={maskOptions} />}
            {watch.questionType === 'TEXT' && <TextFields maskOptions={maskOptions} />}
            {watch.questionType === 'DATE' && <DateFields maskOptions={maskOptions} />}
        </>
    );
};
