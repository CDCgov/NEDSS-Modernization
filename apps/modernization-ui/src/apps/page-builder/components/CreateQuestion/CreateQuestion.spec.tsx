import { fireEvent, getByRole, render, within } from '@testing-library/react';
import userEvent from '@testing-library/user-event';
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

    it('Should display disabled hl7 Segment selection with a constant value', () => {
        const { container } = render(
            <BrowserRouter>
                <AlertProvider>
                    <CreateQuestion />
                </AlertProvider>
            </BrowserRouter>
        );
        const hl7Segment = container.getElementsByClassName('hl7-segment')[0];
        expect(hl7Segment.hasAttribute('disabled'));
        expect(container.querySelector('#hl7Segment')!.textContent).toBe(
            'OBX-3.0'
          );
        
    });
});

describe('Question component tests', () => {
    it('should render a grid with 10 inputs labels which are Question Name, Coding System, Question Code, Others', () => {
        const { getByText, getByLabelText } = render(
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
        expect(getByText('HL7 Segment')).toBeTruthy();
    });

    it('should allow valid input', () => {
        const { getByLabelText, queryByText } = render(
            <AlertProvider>
                <CreateQuestion />
            </AlertProvider>
        );
        const nameElement = getByLabelText(/Question Label/);

        userEvent.type(nameElement, 'question Label');

        fireEvent.blur(nameElement);
        const nameErrorText = queryByText('Question Name Not Valid');
        expect(nameErrorText).not.toBeInTheDocument();
    });

    it('should allow selection of Display Type', () => {
        const { getByLabelText } = render(
            <AlertProvider>
                <CreateQuestion />
            </AlertProvider>
        );

        const select = getByLabelText(/Display Type/);

        const placeholder = within(select).getByText('-Select-');

        expect(placeholder).toBeInTheDocument();

        const text = within(select).getByText('User entered text, number or date');

        expect(text).toHaveValue('1008');

        const multiLineText = within(select).getByText('Multi-line user-entered text');

        expect(multiLineText).toHaveValue('1009');

        const notes = within(select).getByText('Multi-line Notes with User/Date Stamp');

        expect(notes).toHaveValue('1019');

        const readOnly = within(select).getByText('Readonly User entered text, number, or date');

        expect(readOnly).toHaveValue('1026');

        const readOnlyAlternative = within(select).getByText('Readonly User text, number, or date no save');

        expect(readOnlyAlternative).toHaveValue('1029');
    });
});
