import './Counter.scss';

type Props = {
    count: number;
};

export const Counter = ({ count }: Props) => {
    return <div className="counter">{count}</div>;
};
