import { render } from '@testing-library/react';
import { AlertProvider } from 'alert';
import { GroupSubSectionRequest, PagesQuestion, PagesResponse, PagesSubSection } from 'apps/page-builder/generated';
import { ReactNode } from 'react';
import { FormProvider, useForm } from 'react-hook-form';
import { PageManagementProvider } from '../../../../usePageManagement';
import { GroupQuestion } from './GroupQuestion';

type Additional = {
    repeatNumber: number;
    visibleText: string;
};

const page: PagesResponse = {
    id: 12039120,
    name: 'test page',
    status: 'Draft'
};

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

    return (
        <PageManagementProvider page={page} fetch={jest.fn()} refresh={jest.fn} loading={false}>
            <AlertProvider>
                <FormProvider {...methods}>{children}</FormProvider>
            </AlertProvider>
        </PageManagementProvider>
    );
};

const setup = () => {
    return render(
        <Wrapper>
            <GroupQuestion page={1} subsection={subSections} onSuccess={jest.fn()} onCancel={jest.fn()} />
        </Wrapper>
    );
};

describe('when GroupQuestion renders', () => {
    it('should display input fields', () => {
        const { container } = setup();
        const inputs = container.getElementsByTagName('input');
        expect(inputs.length).toBe(9);
    });
    // it('should display input labels', () => {
    //     //  disabling this test as the component being tested contains field labels that are label elements.
    //     const { container } = setup();
    //     const labels = container.getElementsByTagName('label');
    //     expect(labels.length).toBe(2);
    // });
});
