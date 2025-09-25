/* eslint-disable @typescript-eslint/no-unused-vars */
import { Radio } from '@trussworks/react-uswds';
import { ChangeEvent } from 'react';

type Props = {
    options: [];
    onChange: (event: ChangeEvent<HTMLInputElement>) => void;
};

export const RadioGroup = ({ options, onChange }: Props) => {
    return (
        <div className="radio-group">
            {/* {options.map((option: Option[]) => {
                <Radio
                    id={option.display}
                    onChange={onChange}
                    name={option.display}
                    label={option.display}
                />
            })} */}
        </div>
    );
};
