import { NameFieldsExtended } from "./NameFieldsExtended"
import { render } from "@testing-library/react";
import { FormProvider, useForm } from 'react-hook-form';
import { ReactNode } from "react";

describe('When NameFieldsExtended renders', () => {
    const Wrapper = ({ children }: { children: ReactNode }) => {
        const formMethods = useForm();
    
        return (
        <FormProvider {...formMethods}>
            {children}
        </FormProvider>
        );
    };

    it('should exist', () => {
        const { container } = render(
            <Wrapper><NameFieldsExtended coded={{suffixes: []}}/></Wrapper>           
        );
        expect(container).toBeTruthy();
    });

    it('should render inputs', () => {
        const { container } = render(
            <Wrapper><NameFieldsExtended coded={{suffixes: []}}/></Wrapper>           
        );
        const inputs = container.getElementsByTagName('input');
        expect(inputs).toHaveLength(7);
    });

    it('should render selects', () => {
        const { container } = render(
            <Wrapper><NameFieldsExtended coded={{suffixes: []}}/></Wrapper>           
        );
        const inputs = container.getElementsByTagName('select');
        expect(inputs).toHaveLength(3);
    });
});