import { DatePickerInput } from 'components/FormInputs/DatePickerInput';
import './Question.scss';
import { Input } from 'components/FormInputs/Input';
import { SelectInput } from 'components/FormInputs/SelectInput';
import { PageQuestion } from 'apps/page-builder/generated';

export const Question = ({ question }: { question: PageQuestion }) => {
    return (
        <div className="question">
            <div className="question__name">
                {question.labelOnScreen}
                <span>{question.questionIdentifier}</span>
            </div>
            <div className="question__field">
                {question.dataType === 'CODED' ? (
                    <SelectInput options={[]} />
                ) : question.dataType === 'DATE' ? (
                    <DatePickerInput />
                ) : (
                    // Readonly to skirt errors for now
                    <Input type="text" readOnly />
                )}
            </div>
        </div>
    );
};
