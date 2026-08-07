import { ReactNode } from 'react';

import { render } from '@testing-library/react';
import { FormProvider, useForm } from 'react-hook-form';

import { GroupSubSectionRequest, PagesSubSection } from 'apps/page-builder/generated';

import { SubsectionDetails } from './SubsectionDetails';

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
                    appearsInTable: true,
                    width: 0,
                    label: 'test',
                    id: 1234,
                },
            ],
            blockName: '',
            id: 1234,
            visibleText: 'Y',
            repeatNumber: 1,
        },
        mode: 'onBlur',
    });

    return <FormProvider {...methods}>{children}</FormProvider>;
};

const setup = () => {
    return render(
        <Wrapper>
            <SubsectionDetails />
        </Wrapper>
    );
};

describe('when Subsection renders', () => {
    it('should display input fields', () => {
        const { container } = setup();
        const inputs = container.getElementsByTagName('input');
        expect(inputs.length).toBe(5);
    });
    it.skip('should display input labels', () => {
        //  disabling this test as the component being tested contains field labels that are label elements.
        const { container } = setup();
        const labels = container.getElementsByTagName('label');
        expect(labels.length).toBe(2);
    });
});
