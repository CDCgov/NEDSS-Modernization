import { render } from "@testing-library/react";
import { Id } from "./Id"
import { FormProvider, useForm } from "react-hook-form";

describe('When Id renders', () => {
    it('should render X options', () => {
        const Wrapper = () => {
            const methods = useForm();
            return (
                <FormProvider {...methods}>
                    <Id />
                </FormProvider>
            );
        };
        const { container } = render(<Wrapper />);
        const options = container.getElementsByTagName('label');
        expect(options).toHaveLength(1);
    });
});
