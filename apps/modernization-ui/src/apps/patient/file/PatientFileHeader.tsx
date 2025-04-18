import { Heading } from 'components/heading';

interface PatientFileHeaderProps {
    id: string;
}

export const PatientFileHeader = ({ id }: PatientFileHeaderProps) => {
    return (
        <header>
            <Heading level={1}>Patient ID: {id}</Heading>
        </header>
    );
};
