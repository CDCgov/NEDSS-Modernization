import { Shown } from 'conditional-render';
import { LabeledValue, LabeledValueProps } from './LabeledValue';
import { exists } from 'utils';

type MaybeLabeledValueProps = LabeledValueProps & {
    children?: LabeledValueProps['children'];
};

const MaybeLabeledValue = ({ children, ...remaining }: MaybeLabeledValueProps) => (
    <Shown when={exists(children)}>
        <LabeledValue {...remaining}>{children}</LabeledValue>
    </Shown>
);

export { MaybeLabeledValue };
