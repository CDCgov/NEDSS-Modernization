import { render } from '@testing-library/react';
import { GroupSubSectionRequest, PagesQuestion, PagesSubSection } from 'apps/page-builder/generated';
import { ReactNode } from 'react';
import { FormProvider, useForm } from 'react-hook-form';
import { RepeatingBlock } from './RepeatingBlock';

const dateQuestion: PagesQuestion = {
    id: 3,
    name: 'date test question',
    order: 3,
    dataType: 'DATE',
    isStandard: true
};

const dropDownQuestion: PagesQuestion = {
    id: 4,
    name: 'test drop down question',
    order: 4,
    isStandard: true,
    displayComponent: 1007
};

const subSections: PagesSubSection = {
    id: 2,
    isGrouped: false,
    name: 'test subsection',
    order: 2,
    questions: [dateQuestion, dropDownQuestion],
    isGroupable: true,
    questionIdentifier: 'identifier',
    visible: true
};

type Additional = {
    repeatNumber: number;
    visibleText: string;
};

type GroupQuestionFormType = GroupSubSectionRequest & PagesSubSection & Additional;

const Wrapper = ({ children }: { children: ReactNode }) => {
    const methods = useForm<GroupQuestionFormType>({
        defaultValues: {
            name: 'subsection.name',
            batches: [
                {
                    appearsInTable: undefined,
                    width: undefined,
                    label: undefined,
                    id: 1234
                }
            ],
            blockName: undefined,
            id: 1234,
            visibleText: 'Y',
            repeatNumber: 1
        },
        mode: 'onBlur'
    });

    return <FormProvider {...methods}>{children}</FormProvider>;
};

const setup = () => {
    return render(
        <Wrapper>
            <RepeatingBlock questions={subSections.questions} valid={true} setValid={jest.fn()} />
        </Wrapper>
    );
};

describe('when Subsection renders', () => {
    it('should display input fields', () => {
        const { container } = setup();
        const inputs = container.getElementsByTagName('input');
        expect(inputs.length).toBe(2);
    });
    it('should display table headers', () => {
        const { container } = setup();
        const labels = container.getElementsByTagName('th');
        expect(labels.length).toBe(5);
    });
});
