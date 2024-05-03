import { render } from "@testing-library/react"
import { asPersonInput } from "./asPersonInput"

describe('when asPersonInput renders', () => {
    const data = {
        asOf: '',
        identification: [],
        phoneNumbers: [
            {
                number: '111-111-1111',
                type: 'CP',
                use: 'MC'
            }
        ],
        emailAddresses: []
    };

    it('should have the right values for cell phones', () => {
        const result = asPersonInput(data);
        expect(result).toEqual(
            {
                "addresses": [],
                "asOf": null,
                "birthGender": undefined,
                "comments": undefined,
                "currentGender": undefined,
                "dateOfBirth": null,
                "deceased": undefined,
                "deceasedTime": null,
                "emailAddresses": [],
                "ethnicity": undefined,
                "identifications": [],
                "maritalStatus": undefined,
                "names": [],
                "phoneNumbers": [
                    {
                        "number": "111-111-1111",
                        "type": "CP",
                        "use": "MC"
                    }
                ],
                "races": undefined,
                "stateHIVCase": undefined
            }
        )
    });
});
