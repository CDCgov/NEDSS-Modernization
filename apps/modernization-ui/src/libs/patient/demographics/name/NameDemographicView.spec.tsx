import { render, screen, within } from '@testing-library/react';
import { NameDemographicView } from './NameDemographicView';
import { asSelectable } from 'options/selectable';
import { NameDemographic } from './names';

const entry: NameDemographic = {
    asOf: '12/25/2020',
    type: asSelectable('AN', 'Adopted name'),
    prefix: asSelectable('MS', 'Miss'),
    first: 'test first',
    middle: 'test middle',
    secondMiddle: 'second middle',
    last: 'test last',
    secondLast: 'second last',
    suffix: asSelectable('SR', 'test 2'),
    degree: asSelectable('BA', 'test ba')
};

describe('NameDemographicView', () => {
    it('should display the as of', () => {
        render(<NameDemographicView entry={{ asOf: '2022-09-27', type: asSelectable('type') }} />);

        const label = screen.getByText('As of').parentElement!;

        const value = within(label).getByText('09/27/2022');

        expect(value).toBeInTheDocument();
    });

    it('should display the type', () => {
        render(
            <NameDemographicView entry={{ asOf: '2024-10-07', type: { name: 'type name', value: 'type value' } }} />
        );

        const label = screen.getByText('Type').parentElement!;

        const value = within(label).getByText('type name');

        expect(value).toBeInTheDocument();
    });

    it('should display the no data placeholder when values are not present', () => {
        render(
            <NameDemographicView
                entry={{
                    asOf: '2024-10-07',
                    type: asSelectable('type')
                }}
            />
        );

        const prefix = screen.getByText('Prefix').parentElement!;
        within(prefix).getByText('---');

        const first = screen.getByText('First').parentElement!;
        within(first).getByText('---');

        const middle = screen.getByText('Middle').parentElement!;
        within(middle).getByText('---');

        const secondMiddle = screen.getByText('Second middle').parentElement!;
        within(secondMiddle).getByText('---');

        const last = screen.getByText('Last').parentElement!;
        within(last).getByText('---');

        const secondLast = screen.getByText('Second last').parentElement!;
        within(secondLast).getByText('---');

        const suffix = screen.getByText('Suffix').parentElement!;
        within(suffix).getByText('---');

        const degree = screen.getByText('Degree').parentElement!;
        within(degree).getByText('---');
    });

    it('should display the prefix when present', () => {
        render(
            <NameDemographicView
                entry={{
                    asOf: '2024-10-07',
                    type: asSelectable('type'),
                    prefix: { name: 'prefix name', value: 'prefix value' }
                }}
            />
        );

        const label = screen.getByText('Prefix').parentElement!;

        const value = within(label).getByText('prefix name');

        expect(value).toBeInTheDocument();
    });

    it('should display the first name when present', () => {
        render(
            <NameDemographicView
                entry={{
                    asOf: '2024-10-07',
                    type: asSelectable('type'),
                    first: 'First name value'
                }}
            />
        );

        const label = screen.getByText('First').parentElement!;

        const value = within(label).getByText('First name value');

        expect(value).toBeInTheDocument();
    });

    it('should display the middle name when present', () => {
        render(
            <NameDemographicView
                entry={{
                    asOf: '2024-10-07',
                    type: asSelectable('type'),
                    middle: 'Middle name value'
                }}
            />
        );

        const label = screen.getByText('Middle').parentElement!;

        const value = within(label).getByText('Middle name value');

        expect(value).toBeInTheDocument();
    });

    it('should display the second middle name when present', () => {
        render(
            <NameDemographicView
                entry={{
                    asOf: '2024-10-07',
                    type: asSelectable('type'),
                    secondMiddle: 'Second middle name value'
                }}
            />
        );

        const label = screen.getByText('Second middle').parentElement!;

        const value = within(label).getByText('Second middle name value');

        expect(value).toBeInTheDocument();
    });

    it('should display the last name when present', () => {
        render(
            <NameDemographicView
                entry={{
                    asOf: '2024-10-07',
                    type: asSelectable('type'),
                    last: 'Last name value'
                }}
            />
        );

        const label = screen.getByText('Last').parentElement!;

        const value = within(label).getByText('Last name value');

        expect(value).toBeInTheDocument();
    });

    it('should display the second last name when present', () => {
        render(
            <NameDemographicView
                entry={{
                    asOf: '2024-10-07',
                    type: asSelectable('type'),
                    secondLast: 'Second last name value'
                }}
            />
        );

        const label = screen.getByText('Second last').parentElement!;

        const value = within(label).getByText('Second last name value');

        expect(value).toBeInTheDocument();
    });

    it('should display the suffix when present', () => {
        render(
            <NameDemographicView
                entry={{
                    asOf: '2024-10-07',
                    type: asSelectable('type'),
                    suffix: { name: 'suffix name', value: 'suffix value' }
                }}
            />
        );

        const label = screen.getByText('Suffix').parentElement!;

        const value = within(label).getByText('suffix name');

        expect(value).toBeInTheDocument();
    });

    it('should display the degree when present', () => {
        render(
            <NameDemographicView
                entry={{
                    asOf: '2024-10-07',
                    type: asSelectable('type'),
                    degree: { name: 'degree name', value: 'degree value' }
                }}
            />
        );

        const label = screen.getByText('Degree').parentElement!;

        const value = within(label).getByText('degree name');

        expect(value).toBeInTheDocument();
    });
});
