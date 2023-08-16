import { fireEvent, render } from '@testing-library/react';
import { AlertProvider } from 'alert';
import { BrowserRouter } from 'react-router-dom';
import { EditQuestion } from './EditQuestion';

describe('General information component tests', () => {
    it('should display edit question form', () => {
        const { getByTestId } = render(
            <AlertProvider>
                <EditQuestion />
            </AlertProvider>
        );
        expect(getByTestId('header-title').innerHTML).toBe('Edit Question');
    });
});

describe('When page loads', () => {
    it('Save', () => {
        const { container } = render(
            <BrowserRouter>
                <AlertProvider>
                    <EditQuestion />
                </AlertProvider>
            </BrowserRouter>
        );
        const btn = container.getElementsByClassName('usa-button')[0];
        expect(btn.hasAttribute('disabled'));
    });
});

describe('Question component tests', () => {
    it('should render a grid with 10 inputs labels which are Question Name, Coding System, Question Code, Others', () => {
        const { getByText } = render(
            <AlertProvider>
                <EditQuestion />
            </AlertProvider>
        );
        expect(getByText('Question Name')).toBeInTheDocument();
        expect(getByText('Coding System')).toBeTruthy();
        expect(getByText('Question Code')).toBeTruthy();
        expect(getByText('Program Area')).toBeTruthy();
        expect(getByText('Is this a CDC reportable question (NND)?')).toBeTruthy();
        expect(getByText('Is this reportable through Morbidity Reports?')).toBeTruthy();
        expect(getByText('Is this reportable in Aggregate (summary)?')).toBeTruthy();
        expect(getByText('Will this question need the Contact Tracing Module?')).toBeTruthy();
        expect(getByText('Question family')).toBeTruthy();
        expect(getByText('Co-infection group')).toBeTruthy();
    });

    it('should render a dropdown to select Program Area from the provided options', () => {
        const { getByTestId, queryByText, container } = render(
            <AlertProvider>
                <EditQuestion />
            </AlertProvider>
        );
        const nameElement = getByTestId('QuestionName');
        fireEvent.change(nameElement, { target: { value: 'valuetest' } });
        fireEvent.blur(nameElement);
        const nameErrorText = queryByText('Question Name Not Valid');
        expect(nameErrorText).not.toBeInTheDocument();

        const options = container.getElementsByTagName('option');

        expect(options[0]).toHaveTextContent('- Select -');

        for (let i = 1; i < options.length; i++) {
            expect(options[i].value).toBe(options[i].textContent);
        }
    });
});
