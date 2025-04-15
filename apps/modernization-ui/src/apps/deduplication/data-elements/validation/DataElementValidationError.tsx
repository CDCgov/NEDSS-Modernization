import { Shown } from 'conditional-render';
import { AlertMessage } from 'design-system/message';
import React from 'react';

export type InUseDataElements = { passes: string[]; fields: string[] };

type Props = {
    validationError?: InUseDataElements;
};
export const DataElementValidationError = ({ validationError }: Props) => {
    return (
        <Shown when={validationError !== undefined}>
            <AlertMessage title="Data element currently in use" type="error">
                <span>
                    The request to remove the{' '}
                    <span style={{ fontWeight: '700' }}>{validationError?.fields.join(', ')}</span> data element
                    {(validationError?.fields.length ?? 0) > 1 ? 's' : ''} is not possible because{' '}
                    {(validationError?.fields.length ?? 0) > 1 ? 'they are' : 'it is'} currently being used in a pass
                    configuration. If you would like to remove this data element, first remove it from the following
                    pass configurations:
                </span>
                <ul>{validationError?.passes.map((p, k) => <li key={k}>{p}</li>)}</ul>
            </AlertMessage>
        </Shown>
    );
};
