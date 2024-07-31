import { render } from '@testing-library/react';
import { FormProvider, useForm } from "react-hook-form";
import { RaceEthnicity } from "./RaceEthnicity";

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
