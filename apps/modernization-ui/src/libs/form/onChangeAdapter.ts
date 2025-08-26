type Fn = <V>(value?: V | null) => void;

const onChangeAdapter =
    (onChange: Fn): Fn =>
    (...value: any[]) => {
        if (value === undefined) {
            onChange(null);
        } else {
            onChange(value);
        }
    };

export { onChangeAdapter };
