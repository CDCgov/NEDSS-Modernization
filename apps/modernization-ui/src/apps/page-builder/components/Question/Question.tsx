import { DatePickerInput } from 'components/FormInputs/DatePickerInput';
import './Question.scss';
import { Input } from 'components/FormInputs/Input';
import { SelectInput } from 'components/FormInputs/SelectInput';
import { PagesQuestion } from 'apps/page-builder/generated';

export const Question = ({ question }: { question: PagesQuestion }) => {
    return (
        <div className="question">
            <div className="question__name">
                {question.name}
                <span>{question.question}</span>
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
