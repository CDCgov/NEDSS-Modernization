import { fireEvent, render } from '@testing-library/react';
import { CreateCondition } from './CreateCondition';
import { BrowserRouter } from 'react-router-dom';

describe('General information component tests', () => {
    it('should display create condition form', () => {
        const { getByTestId } = render(<CreateCondition />);
        expect(getByTestId('header-title').innerHTML).toBe('Create a new Condition');
    });
});

describe('When page loads', () => {
    it('Create & add condition button should be disabled', () => {
        const { container } = render(
            <BrowserRouter>
                <CreateCondition />
            </BrowserRouter>
        );
        const btn = container.getElementsByClassName('usa-button')[0];
        expect(btn.hasAttribute('disabled'));
    });
});

describe('Condition component tests', () => {
    it('should render a grid with 10 inputs labels which are Condition Name, Coding System, Condition Code, Others', () => {
        const { getByText } = render(<CreateCondition />);
        expect(getByText('Condition Name')).toBeInTheDocument();
        expect(getByText('Coding System')).toBeTruthy();
        expect(getByText('Condition Code')).toBeTruthy();
        expect(getByText('Program Area')).toBeTruthy();
        expect(getByText('Is this a CDC reportable condition (NND)?')).toBeTruthy();
        expect(getByText('Is this reportable through Morbidity Reports?')).toBeTruthy();
        expect(getByText('Is this reportable in Aggregate (summary)?')).toBeTruthy();
        expect(getByText('Will this condition need the Contact Tracing Module?')).toBeTruthy();
        expect(getByText('Condition family')).toBeTruthy();
        expect(getByText('Co-infection group')).toBeTruthy();
    });

    it('should render a dropdown to select Program Area from the provided options', () => {
        const { getByTestId, queryByText, container } = render(<CreateCondition />);
        const nameElement = getByTestId('conditionName');
        fireEvent.change(nameElement, { target: { value: 'valuetest' } });
        fireEvent.blur(nameElement);
        const nameErrorText = queryByText('Condition Name Not Valid');
        expect(nameErrorText).not.toBeInTheDocument();

        const options = container.getElementsByTagName('option');

        expect(options[0]).toHaveTextContent('- Select -');

        for (let i = 1; i < options.length; i++) {
            expect(options[i].value).toBe(options[i].textContent);
        }
    });
});
