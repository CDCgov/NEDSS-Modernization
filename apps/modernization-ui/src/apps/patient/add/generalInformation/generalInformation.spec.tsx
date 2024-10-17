import React from 'react';
import { render } from '@testing-library/react';
import { FormProvider, useForm } from 'react-hook-form';
import GeneralInformation from './generalInformation';
import userEvent from '@testing-library/user-event';

const Wrapper = ({ children }: { children: React.ReactNode }) => {
    const methods = useForm();
    return <FormProvider {...methods}>{children}</FormProvider>;
};

describe('GeneralInformation Component', () => {
    it('should renders without crashing', () => {
        const { getByTestId, getByLabelText } = render(
            <Wrapper>
                <GeneralInformation id="test-id" title="Test Title" />
            </Wrapper>
        );

        expect(getByTestId('required-text')).toBeInTheDocument();
        expect(getByLabelText('Information as of Date')).toBeInTheDocument();
        expect(getByLabelText('Comments')).toBeInTheDocument();
    });

    it('should accepts input in the comments field', () => {
        const { getByLabelText } = render(
            <Wrapper>
                <GeneralInformation id="test-id" title="Test Title" />
            </Wrapper>
        );

        const commentsInput = getByLabelText('Comments');
        userEvent.paste(commentsInput, 'Test comment');

        expect(commentsInput).toHaveValue('Test comment');
    });
});
