import { MergeGeneralInfo } from 'apps/deduplication/api/model/MergeCandidate';
import { render, within } from '@testing-library/react';
import { FormProvider, useForm } from 'react-hook-form';
import { GeneralInfo } from './GeneralInfo';

let mockPermissions: string[] = [];

jest.mock('user', () => ({
    useUser: () => ({ state: { user: { permissions: mockPermissions } } })
}));

const data: MergeGeneralInfo = {
    asOf: '2025-05-20T00:00:00',
    maritalStatus: 'Annulled',
    mothersMaidenName: 'MotherMaiden',
    numberOfAdultsInResidence: '2',
    numberOfChildrenInResidence: '0',
    primaryOccupation: 'Mining',
    educationLevel: '10th grade',
    primaryLanguage: 'Eastern Frisian',
    speaksEnglish: 'Yes',
    stateHivCaseId: '123'
};
const Fixture = ({ generalInfo = data }: { generalInfo?: MergeGeneralInfo }) => {
    const form = useForm();
    return (
        <FormProvider {...form}>
            <GeneralInfo personUid="1" generalInfo={generalInfo} />
        </FormProvider>
    );
};
describe('GeneralInfo', () => {
    it('should render proper label and value', () => {
        mockPermissions = ['HIVQUESTIONS-GLOBAL'];

        const { getByText } = render(<Fixture />);

        const asOf = getByText('As of date');
        expect(asOf).toBeInTheDocument();
        expect(within(asOf.parentElement!).getByText('05/20/2025')).toBeInTheDocument();

        const maritalStatus = getByText('Marital status');
        expect(maritalStatus).toBeInTheDocument();
        expect(within(maritalStatus.parentElement!).getByText('Annulled')).toBeInTheDocument();

        const mothersMaidenName = getByText("Mother's maiden name");
        expect(mothersMaidenName).toBeInTheDocument();
        expect(within(mothersMaidenName.parentElement!).getByText('MotherMaiden')).toBeInTheDocument();

        const adultNumber = getByText('Number of adults in residence');
        expect(adultNumber).toBeInTheDocument();
        expect(within(adultNumber.parentElement!).getByText('2')).toBeInTheDocument();

        const childNumber = getByText('Number of children in residence');
        expect(childNumber).toBeInTheDocument();
        expect(within(childNumber.parentElement!).getByText('0')).toBeInTheDocument();

        const occupation = getByText('Primary occupation');
        expect(occupation).toBeInTheDocument();
        expect(within(occupation.parentElement!).getByText('Mining')).toBeInTheDocument();

        const education = getByText('Highest level of education');
        expect(education).toBeInTheDocument();
        expect(within(education.parentElement!).getByText('10th grade')).toBeInTheDocument();

        const language = getByText('Primary language');
        expect(language).toBeInTheDocument();
        expect(within(language.parentElement!).getByText('Eastern Frisian')).toBeInTheDocument();

        const english = getByText('Speaks english');
        expect(english).toBeInTheDocument();
        expect(within(english.parentElement!).getByText('Yes')).toBeInTheDocument();

        const hivCase = getByText('State HIV case ID');
        expect(hivCase).toBeInTheDocument();
        expect(within(hivCase.parentElement!).getByText('123')).toBeInTheDocument();
    });

    it('should render --- for missing values', () => {
        mockPermissions = ['HIVQUESTIONS-GLOBAL'];
        const { getByText } = render(<Fixture generalInfo={{}} />);

        const asOf = getByText('As of date');
        expect(asOf).toBeInTheDocument();
        expect(within(asOf.parentElement!).getByText('---')).toBeInTheDocument();

        const maritalStatus = getByText('Marital status');
        expect(maritalStatus).toBeInTheDocument();
        expect(within(maritalStatus.parentElement!).getByText('---')).toBeInTheDocument();

        const mothersMaidenName = getByText("Mother's maiden name");
        expect(mothersMaidenName).toBeInTheDocument();
        expect(within(mothersMaidenName.parentElement!).getByText('---')).toBeInTheDocument();

        const adultNumber = getByText('Number of adults in residence');
        expect(adultNumber).toBeInTheDocument();
        expect(within(adultNumber.parentElement!).getByText('---')).toBeInTheDocument();

        const childNumber = getByText('Number of children in residence');
        expect(childNumber).toBeInTheDocument();
        expect(within(childNumber.parentElement!).getByText('---')).toBeInTheDocument();

        const occupation = getByText('Primary occupation');
        expect(occupation).toBeInTheDocument();
        expect(within(occupation.parentElement!).getByText('---')).toBeInTheDocument();

        const education = getByText('Highest level of education');
        expect(education).toBeInTheDocument();
        expect(within(education.parentElement!).getByText('---')).toBeInTheDocument();

        const language = getByText('Primary language');
        expect(language).toBeInTheDocument();
        expect(within(language.parentElement!).getByText('---')).toBeInTheDocument();

        const english = getByText('Speaks english');
        expect(english).toBeInTheDocument();
        expect(within(english.parentElement!).getByText('---')).toBeInTheDocument();

        const hivCase = getByText('State HIV case ID');
        expect(hivCase).toBeInTheDocument();
        expect(within(hivCase.parentElement!).getByText('---')).toBeInTheDocument();
    });

    it('should not render State HIV case ID if user lacks permission', () => {
        mockPermissions = [];
        const { queryByText } = render(<Fixture />);
        const hivCase = queryByText('State HIV case ID');
        expect(hivCase).not.toBeInTheDocument();
    });
});
