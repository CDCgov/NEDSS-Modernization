import { Shown } from 'conditional-render';
import { AlertMessage } from 'design-system/message';

export type InUseDataElements = { passes: string[]; fields: string[] };

type Props = {
    validationError?: InUseDataElements;
};
export const DataElementValidationError = ({ validationError }: Props) => {
    return (
        <Shown when={validationError !== undefined}>
            <AlertMessage title="Data element currently in use" type="error">
                <span>
                    The request to remove the <strong>{validationError?.fields.join(', ')}</strong> data element
                    {(validationError?.fields.length ?? 0) > 1 ? 's' : ''} is not possible because{' '}
                    {(validationError?.fields.length ?? 0) > 1 ? 'they are' : 'it is'} currently being used in a pass
                    configuration. If you would like to remove this data element, first remove it from the following
                    pass configurations:
                </span>
                <ul>
                    {validationError?.passes.map((p) => (
                        <li key={p}>{p}</li>
                    ))}
                </ul>
            </AlertMessage>
        </Shown>
    );
};
