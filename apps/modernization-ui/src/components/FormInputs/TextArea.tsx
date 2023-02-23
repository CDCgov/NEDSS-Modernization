import { Label } from '@trussworks/react-uswds';
import classNames from 'classnames';

type InputProps = {
    name?: string;
    className?: string;
    htmlFor?: string;
    label?: string;
    id?: string;
    onChange?: any;
};

export const TextArea = ({ name, className, htmlFor = '', label, id = '', onChange }: InputProps) => {
    return (
        <>
            <Label htmlFor={htmlFor}>{label}</Label>
            <TextArea id={id} onChange={onChange} name={name || ''} className={classNames(className)} />
        </>
    );
};
