import { asGeneral } from './asGeneral';

describe('when mapping a general information demographic to a format accepted by the API', () => {
    it('should include the as of date', () => {
        const demographic = {
            asOf: '04/13/2017',
            adultsInResidence: 17
        };

        const actual = asGeneral(demographic);

        expect(actual).toEqual(expect.objectContaining({ asOf: '04/13/2017' }));
    });

    it('should include the marital status', () => {
        const demographic = {
            asOf: '04/13/2017',
            maritalStatus: { value: 'marital-status-value', name: 'marital-status-name' }
        };

        const actual = asGeneral(demographic);

        expect(actual).toEqual(expect.objectContaining({ maritalStatus: 'marital-status-value' }));
    });

    it('should include the maternal maiden name', () => {
        const demographic = {
            asOf: '04/13/2017',
            maternalMaidenName: 'maternal-maiden-name-value'
        };

        const actual = asGeneral(demographic);

        expect(actual).toEqual(expect.objectContaining({ maternalMaidenName: 'maternal-maiden-name-value' }));
    });

    it('should include the adults in residence', () => {
        const demographic = {
            asOf: '04/13/2017',
            adultsInResidence: 17
        };

        const actual = asGeneral(demographic);

        expect(actual).toEqual(expect.objectContaining({ adultsInResidence: 17 }));
    });

    it('should include the children in residence', () => {
        const demographic = {
            asOf: '04/13/2017',
            childrenInResidence: 39
        };

        const actual = asGeneral(demographic);

        expect(actual).toEqual(expect.objectContaining({ childrenInResidence: 39 }));
    });

    it('should include the primary occupation', () => {
        const demographic = {
            asOf: '04/13/2017',
            primaryOccupation: { value: 'primary-occupation-value', name: 'primary-occupation-name' }
        };

        const actual = asGeneral(demographic);

        expect(actual).toEqual(expect.objectContaining({ primaryOccupation: 'primary-occupation-value' }));
    });

    it('should include the education level', () => {
        const demographic = {
            asOf: '04/13/2017',
            educationLevel: { value: 'education-level-value', name: 'education-level-name' }
        };

        const actual = asGeneral(demographic);

        expect(actual).toEqual(expect.objectContaining({ educationLevel: 'education-level-value' }));
    });

    it('should include patient primary language', () => {
        const demographic = {
            asOf: '04/13/2017',
            primaryLanguage: { value: 'W', name: 'Welsh' }
        };

        const actual = asGeneral(demographic);

        expect(actual).toEqual(expect.objectContaining({ primaryLanguage: 'W' }));
    });

    it('should include wether the patient speaks english', () => {
        const demographic = {
            asOf: '04/13/2017',
            speaksEnglish: { value: 'speaks-english-value', name: 'speaks-english-name' }
        };

        const actual = asGeneral(demographic);

        expect(actual).toEqual(expect.objectContaining({ speaksEnglish: 'speaks-english-value' }));
    });

    it('should include the state HIV case', () => {
        const demographic = {
            asOf: '04/13/2017',
            stateHIVCase: { value: 'state-hiv-case-value' }
        };

        const actual = asGeneral(demographic);

        expect(actual).toEqual(expect.objectContaining({ stateHIVCase: 'state-hiv-case-value' }));
    });

    it('should not map when only as of is present', () => {
        const demographic = {
            asOf: '04/13/2017'
        };

        const actual = asGeneral(demographic);

        expect(actual).toBeUndefined();
    });
});
