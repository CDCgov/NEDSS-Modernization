import { vi } from 'vitest';
import { FormProvider, useForm } from 'react-hook-form';
import { render } from '@testing-library/react';
import { Selectable } from 'options';
import { RaceEthnicity } from './RaceEthnicity';

vi.mock('options/concepts', () => ({
    useConceptOptions: () => ({ options: [] })
}));

const mockRaceCategories: Selectable[] = [
    { value: '1', name: 'race one name' },
    { value: '2', name: 'race two name' }
];

vi.mock('options/race', () => ({
    useRaceCategoryOptions: () => mockRaceCategories
}));

describe('When RaceEthnicity renders', () => {
    it('should render two Select inputs', () => {
        const Wrapper = () => {
            const methods = useForm();
            return (
                <FormProvider {...methods}>
                    <RaceEthnicity />
                </FormProvider>
            );
        };
        const { container } = render(<Wrapper />);
        const inputs = container.getElementsByTagName('select');
        expect(inputs).toHaveLength(2);
    });
});
