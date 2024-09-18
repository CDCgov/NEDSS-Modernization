import { renderHook } from '@testing-library/react-hooks';
import { CodedValue } from 'coded/CodedValue';
import { RaceEntry } from '../entry';
import { FormProvider, useForm } from 'react-hook-form';
import { act, fireEvent, render, waitFor } from '@testing-library/react';
import { RaceEntryFields } from './RaceEntryFields';
import userEvent from '@testing-library/user-event';

const mockRaceCodedValues: CodedValue[] = [{ value: '1', name: 'race name' }];

jest.mock('coded/race/useRaceCodedValues', () => ({
    useRaceCodedValues: () => mockRaceCodedValues
}));
const mockDetailedOptions: CodedValue[] = [
    { value: '2', name: 'detailed race1' },
    { value: '3', name: 'detailed race2' }
];

jest.mock('coded/race/useDetailedRaceCodedValues', () => ({
    useDetailedRaceCodedValues: () => mockDetailedOptions
}));

const form = renderHook(() =>
    useForm<RaceEntry>({
        mode: 'onBlur',
        defaultValues: {
            asOf: undefined,
            race: undefined,
            detailed: undefined
        }
    })
).result.current;

describe('Race entry fields', () => {
    it('should render the proper labels', () => {
        const { getByLabelText } = render(
            <FormProvider {...form}>
                <RaceEntryFields />
            </FormProvider>
        );

        expect(getByLabelText('As of')).toBeInTheDocument();
        expect(getByLabelText('Race')).toBeInTheDocument();
    });

    it('detailed race should render once race is chosen', async () => {
        const { getByLabelText, getByText } = render(
            <FormProvider {...form}>
                <RaceEntryFields />
            </FormProvider>
        );

        const race = getByLabelText('Race');

        act(() => {
            userEvent.type(race, '1');
        });

        await waitFor(() => {
            expect(getByText('Detailed race')).toBeInTheDocument();
        });
    });

    it('should require as of', async () => {
        const { getByLabelText, getByText } = render(
            <FormProvider {...form}>
                <RaceEntryFields />
            </FormProvider>
        );

        const asOf = getByLabelText('As of');
        act(() => {
            fireEvent.blur(asOf);
        });
        await waitFor(() => {
            expect(getByText('As of date is required.')).toBeInTheDocument();
        });
    });

    it('should require race', async () => {
        const { getByLabelText, getByText } = render(
            <FormProvider {...form}>
                <RaceEntryFields />
            </FormProvider>
        );

        const raceInput = getByLabelText('Race');
        act(() => {
            fireEvent.blur(raceInput);
        });
        await waitFor(() => {
            expect(getByText('Race is required.')).toBeInTheDocument();
        });
    });

    it('should be valid with as of, race', async () => {
      const { getByLabelText } = render(
          <FormProvider {...form}>
              <RaceEntryFields />
          </FormProvider>
      );

      const asOf = getByLabelText('As of');
      const race = getByLabelText('Race');
      act(() => {
          userEvent.paste(asOf, '01/20/2020');
          fireEvent.blur(asOf);
          userEvent.selectOptions(race, '1');
          fireEvent.blur(race);
      });

      await waitFor(() => {
          expect(form.getFieldState('asOf').invalid).toBeFalsy();
          expect(form.getFieldState('race').invalid).toBeFalsy();
      });
  });
});
