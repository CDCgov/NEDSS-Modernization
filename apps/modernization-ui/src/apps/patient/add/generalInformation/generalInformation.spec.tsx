import { ReactNode } from 'react';
import { FormProvider, useForm } from 'react-hook-form';
import { render } from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import GeneralInformation from './generalInformation';

const Fixture = () => {
    const methods = useForm();
    return (
        <FormProvider {...methods}>
            <GeneralInformation id="test-id" title="Test Title" />
        </FormProvider>
    );
};

describe('GeneralInformation Component', () => {
    it('should renders without crashing', () => {
        const { getByLabelText } = render(<Fixture />);

        expect(getByLabelText('Information as of date')).toBeInTheDocument();
        expect(getByLabelText('Comments')).toBeInTheDocument();
    });

    it('should accepts input in the comments field', async () => {
        const user = userEvent.setup();

        const { getByLabelText } = render(<Fixture />);

        const commentsInput = getByLabelText('Comments');
        await user.type(commentsInput, 'Test comment');

        expect(commentsInput).toHaveValue('Test comment');
    });
});
