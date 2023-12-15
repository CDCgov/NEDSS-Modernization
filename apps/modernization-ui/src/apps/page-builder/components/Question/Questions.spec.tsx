import { render } from '@testing-library/react';
import { AlertProvider } from '../../../../alert';
import { BrowserRouter } from 'react-router-dom';
import { QuestionWrap } from './Question';
import React from 'react';

describe('Question logic wrap component tests', () => {
    it('should render a grid with 3 info labels with button and dropdown', () => {
        const data = {
            id: 1101202,
            isStandard: true,
            standard: 'SYS',
            question: 'DEM182',
            name: 'Email',
            order: 40,
            subGroup: 'IPO',
            description: "The patient's email address.",
            coInfection: false,
            dataType: 'TEXT',
            mask: 'TXT_EMAIL',
            allowFutureDates: false,
            tooltip: "The patient's email address.",
            display: true,
            enabled: true,
            required: false,
            defaultValue: null,
            valueSet: null
        };
        const { getByText } = render(
            <BrowserRouter>
                <AlertProvider>
                    <QuestionWrap question={data} />
                </AlertProvider>
            </BrowserRouter>
        );
        expect(getByText('Question Label')).toBeInTheDocument();
        expect(getByText('Question Type')).toBeInTheDocument();
        expect(getByText('Add Logic')).toBeInTheDocument();
    });
});
