import { render } from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import { useForm } from 'react-hook-form';
import { AdministrativeInformationFields } from './AdministrativeInformationFields';
import { PatientDemographics } from '../../demographics';

const Fixture = (props: { sizing?: 'small' | 'medium' | 'large' }) => {
    const methods = useForm<PatientDemographics>({ mode: 'onBlur' });
    return <AdministrativeInformationFields sizing={props.sizing} form={methods} orientation={'horizontal'} />;
};

describe('when entering patient administrative information', () => {
    it('should render all input fields', () => {
        const { getByLabelText } = render(<Fixture />);

        expect(getByLabelText('As of')).toBeInTheDocument();
        expect(getByLabelText('Comments')).toBeInTheDocument();
    });

    it('should render all input fields with the correct sizing when sizing is small.', () => {
        const { getByText } = render(<Fixture sizing="small" />);

        //  This is assuming that the elements have a specific structure.
        expect(getByText('As of').parentElement?.parentElement).toHaveClass('small');
        expect(getByText('Comments').parentElement?.parentElement).toHaveClass('small');
    });

    it('should require as of date', async () => {
        const { getByLabelText, queryByText } = render(<Fixture />);

        const dateInput = getByLabelText('As of');

        expect(queryByText('The As of is required.')).not.toBeInTheDocument();

        const user = userEvent.setup();

        await user.click(dateInput).then(() => user.tab());

        expect(queryByText('The As of is required.')).toBeInTheDocument();
    });
    it('should have accessibility description for the as of date field', () => {
        const { getByLabelText } = render(<Fixture />);
        const dateInput = getByLabelText('As of');
        expect(dateInput).toHaveAttribute(
            'aria-description',
            "This field defaults to today's date and can be changed if needed."
        );
    });
});
