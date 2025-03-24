import { fireEvent, render } from '@testing-library/react';
import { AlertProvider } from 'alert';
import { BrowserRouter } from 'react-router';
import { CreateCondition } from './CreateCondition';

describe('Create Condition', () => {
    const modal = { current: null };
    describe('General information component tests', () => {
        it('should display create condition form', () => {
            const { getByTestId } = render(
                <AlertProvider>
                    <CreateCondition modal={modal} />
                </AlertProvider>
            );
            expect(getByTestId('header-title').innerHTML).toBe('Condition details');
        });
    });

    describe('When page loads', () => {
        it('Create & add condition button should be disabled', () => {
            const { container } = render(
                <BrowserRouter>
                    <AlertProvider>
                        <CreateCondition modal={modal} />
                    </AlertProvider>
                </BrowserRouter>
            );
            const btn = container.getElementsByClassName('usa-button')[0];
            expect(btn.hasAttribute('disabled'));
        });
    });

    describe('Condition component tests', () => {
        it('should render a grid with 10 inputs labels which are Condition Name, Coding System, Condition Code, Others', () => {
            const { getByText } = render(
                <AlertProvider>
                    <CreateCondition modal={modal} />
                </AlertProvider>
            );
            expect(getByText('Condition Name')).toBeTruthy();
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
            const { getByTestId, queryByText, container } = render(
                <AlertProvider>
                    <CreateCondition modal={modal} />
                </AlertProvider>
            );
            const nameElement = getByTestId('conditionName');
            fireEvent.change(nameElement, { target: { value: 'valuetest' } });
            fireEvent.blur(nameElement);
            const nameErrorText = queryByText('Condition Name Not Valid');
            expect(nameErrorText).not.toBeInTheDocument();

            const options = container.getElementsByTagName('option');

            expect(options[0]).toHaveTextContent('- Select -');

            for (let i = 1; i < options.length; i++) {
                expect(options[i].value).toBe(options[i].value);
            }
        });
    });
});
