import { render } from '@testing-library/react';
import { LabTestSummary } from 'generated/graphql/schema';
import { ResultedTestDisplay } from './ResultedTestDisplay';

describe('ResultedTestDisplay', () => {
    it('displays numeric result with unit', () => {
        const summary: LabTestSummary = {
            numeric: 1144,
            unit: 'someUnit'
        };

        const { getByText } = render(<ResultedTestDisplay test={summary} />);
        expect(getByText('1144 someUnit')).toBeInTheDocument();
    });

    it('displays numeric result without unit', () => {
        const summary: LabTestSummary = {
            numeric: 1144
        };

        const { getByText } = render(<ResultedTestDisplay test={summary} />);
        expect(getByText('1144')).toBeInTheDocument();
    });

    it('displays reference range with high and low', () => {
        const summary: LabTestSummary = {
            high: '200',
            low: '10'
        };

        const { getByText } = render(<ResultedTestDisplay test={summary} />);
        expect(getByText('Reference range:')).toBeInTheDocument();
        expect(getByText('(10 - 200)')).toBeInTheDocument();
    });

    it('displays reference range with high', () => {
        const summary: LabTestSummary = {
            high: '200'
        };

        const { getByText } = render(<ResultedTestDisplay test={summary} />);
        expect(getByText('Reference range:')).toBeInTheDocument();
        expect(getByText('(200)')).toBeInTheDocument();
    });

    it('displays reference range with low', () => {
        const summary: LabTestSummary = {
            low: '10'
        };

        const { getByText } = render(<ResultedTestDisplay test={summary} />);
        expect(getByText('Reference range:')).toBeInTheDocument();
        expect(getByText('(10)')).toBeInTheDocument();
    });

    it('displays reference range with high, low, and status', () => {
        const summary: LabTestSummary = {
            high: '200',
            low: '10',
            status: 'FINAL'
        };

        const { getByText } = render(<ResultedTestDisplay test={summary} />);
        expect(getByText('Reference range:')).toBeInTheDocument();
        expect(getByText('(10 - 200) - (FINAL)')).toBeInTheDocument();
    });

    it('displays reference range with high and status', () => {
        const summary: LabTestSummary = {
            high: '200',
            status: 'FINAL'
        };

        const { getByText } = render(<ResultedTestDisplay test={summary} />);
        expect(getByText('Reference range:')).toBeInTheDocument();
        expect(getByText('(200) - (FINAL)')).toBeInTheDocument();
    });

    it('displays reference range with low and status', () => {
        const summary: LabTestSummary = {
            low: '10',
            status: 'FINAL'
        };

        const { getByText } = render(<ResultedTestDisplay test={summary} />);
        expect(getByText('Reference range:')).toBeInTheDocument();
        expect(getByText('(10) - (FINAL)')).toBeInTheDocument();
    });

    it('displays coded result', () => {
        const summary: LabTestSummary = {
            coded: 'abnormal'
        };

        const { getByText } = render(<ResultedTestDisplay test={summary} />);
        expect(getByText('abnormal')).toBeInTheDocument();
    });
});
