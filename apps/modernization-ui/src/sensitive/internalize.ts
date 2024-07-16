/* eslint-disable no-redeclare */
import { Sensitive as GraphQLSensitive, Maybe } from 'generated/graphql/schema';
import { orNull } from 'utils';

function interalize(input?: Maybe<GraphQLSensitive>): string | null {
    return input?.__typename === 'Allowed' ? orNull(input.value) : null;
}

export { interalize };
