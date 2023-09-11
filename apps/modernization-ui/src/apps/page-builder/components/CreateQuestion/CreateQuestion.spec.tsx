import { fireEvent, render } from '@testing-library/react';
import { AlertProvider } from 'alert';
import { BrowserRouter } from 'react-router-dom';
import { CreateQuestion } from './CreateQuestion';

describe('General information component tests', () => {
    it('should display create question form', () => {
        const { getByTestId } = render(
            <AlertProvider>
                <CreateQuestion />
            </AlertProvider>
        );
        expect(getByTestId('header-title').innerHTML).toBe("Let's create a new question");
    });
});

describe('When page loads', () => {
    it('Create and add to page button should be disabled', () => {
        const { container } = render(
            <BrowserRouter>
                <AlertProvider>
                    <CreateQuestion />
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
                <CreateQuestion />
            </AlertProvider>
        );
        expect(getByText('Question Label')).toBeInTheDocument();
        expect(getByText('Description')).toBeInTheDocument();
        expect(getByText('Field Type')).toBeInTheDocument();
        expect(getByText('Subgroup')).toBeInTheDocument();
        expect(getByText('LOCAL')).toBeInTheDocument();
        expect(getByText('PHIN')).toBeInTheDocument();
        expect(getByText('Unique ID')).toBeInTheDocument();
        expect(getByText('Unique name')).toBeInTheDocument();
        expect(getByText('Default Label in report')).toBeInTheDocument();
        expect(getByText('Default RDB table name')).toBeTruthy();
        expect(getByText('RDB column name')).toBeTruthy();
        expect(getByText('Data mart column name')).toBeTruthy();
        expect(getByText('Included in message?')).toBeTruthy();
        expect(getByText('Message ID')).toBeTruthy();
        expect(getByText('Message label')).toBeTruthy();
        expect(getByText('Code system name')).toBeTruthy();
        expect(getByText('HL7 data type')).toBeTruthy();
        expect(getByText('Administrative comments')).toBeTruthy();
    });

    it('should render a dropdown to select Program Area from the provided options', () => {
        const { getByTestId, queryByText, container } = render(
            <AlertProvider>
                <CreateQuestion />
            </AlertProvider>
        );
        const nameElement = getByTestId('questionLabel');
        fireEvent.change(nameElement, { target: { value: 'question Label' } });
        fireEvent.blur(nameElement);
        const nameErrorText = queryByText('Question Name Not Valid');
        expect(nameErrorText).not.toBeInTheDocument();

        const options = container.getElementsByTagName('option');

        expect(options[0]).toHaveTextContent('-Select-');

        for (let i = 1; i < options.length; i++) {
            expect(options[i].value).toBe(options[i].textContent);
        }
    });
});
