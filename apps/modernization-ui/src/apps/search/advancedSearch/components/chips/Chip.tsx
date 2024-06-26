import { Icon } from '@trussworks/react-uswds';

type ChipProps = {
    name: string;
    value: string;
    handleClose: () => void;
};

const Chip = ({ name, value, handleClose }: ChipProps) => {
    return (
        <div
            className="margin-left-1 margin-bottom-05 padding-05 font-sans-3xs"
            data-testid={`${name}-chip`}
            style={{ backgroundColor: '#005EA2', color: 'white', borderRadius: '2px' }}>
            <span style={{ textTransform: 'uppercase' }}>{name}</span>
            <span>: </span>
            <span>{value}</span>
            <Icon.Close onClick={() => handleClose()} className="margin-left-05" style={{ cursor: 'pointer' }} />
        </div>
    );
};

export default Chip;
