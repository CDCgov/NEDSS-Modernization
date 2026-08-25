import { useEffect, useState } from 'react';

import { Shown } from 'conditional-render';
import { Button } from 'design-system/button';

type Props = {
    content: string;
    limit: number;
};
export const LengthConstrained = ({ content, limit }: Props) => {
    const [constrained, setConstrained] = useState<boolean>(content.length > limit);
    const [displayedText, setDisplayedText] = useState<string>(content);

    useEffect(() => {
        if (constrained) {
            setDisplayedText(content.substring(0, limit));
        } else {
            setDisplayedText(content);
        }
    }, [content, constrained]);

    return (
        <>
            <span>
                {displayedText}
                {constrained && <span>...</span>}{' '}
            </span>
            <Shown when={content.length > limit}>
                <Shown
                    when={constrained}
                    fallback={
                        <Button tertiary={true} sizing="small" onClick={() => setConstrained(true)}>
                            [show less]
                        </Button>
                    }
                >
                    <Button tertiary={true} sizing="small" onClick={() => setConstrained(false)}>
                        [show more]
                    </Button>
                </Shown>
            </Shown>
        </>
    );
};
