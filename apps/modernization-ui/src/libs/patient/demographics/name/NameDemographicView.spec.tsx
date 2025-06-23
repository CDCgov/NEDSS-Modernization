import { render, screen } from '@testing-library/react';
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

        const actual = screen.getByRole('definition', { name: 'Name as of' });

        expect(actual).toHaveTextContent('09/27/2022');
    });

    it('should display the type', () => {
        render(
            <NameDemographicView entry={{ asOf: '2024-10-07', type: { name: 'type name', value: 'type value' } }} />
        );

        const actual = screen.getByRole('definition', { name: 'Type' });

        expect(actual).toHaveTextContent('type name');
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

        expect(screen.getByRole('definition', { name: 'Prefix' })).toHaveTextContent('---');

        expect(screen.getByRole('definition', { name: 'First' })).toHaveTextContent('---');

        expect(screen.getByRole('definition', { name: 'Middle' })).toHaveTextContent('---');

        expect(screen.getByRole('definition', { name: 'Second middle' })).toHaveTextContent('---');

        expect(screen.getByRole('definition', { name: 'Last' })).toHaveTextContent('---');

        expect(screen.getByRole('definition', { name: 'Second last' })).toHaveTextContent('---');

        expect(screen.getByRole('definition', { name: 'Suffix' })).toHaveTextContent('---');

        expect(screen.getByRole('definition', { name: 'Degree' })).toHaveTextContent('---');
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

        expect(screen.getByRole('definition', { name: 'Prefix' })).toHaveTextContent('prefix name');
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

        expect(screen.getByRole('definition', { name: 'First' })).toHaveTextContent('First name value');
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

        expect(screen.getByRole('definition', { name: 'Middle' })).toHaveTextContent('Middle name value');
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

        expect(screen.getByRole('definition', { name: 'Second middle' })).toHaveTextContent('Second middle name value');
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

        expect(screen.getByRole('definition', { name: 'Last' })).toHaveTextContent('Last name value');
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

        expect(screen.getByRole('definition', { name: 'Second last' })).toHaveTextContent('Second last name value');
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

        expect(screen.getByRole('definition', { name: 'Suffix' })).toHaveTextContent('suffix name');
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
        expect(screen.getByRole('definition', { name: 'Degree' })).toHaveTextContent('degree name');
    });
});
