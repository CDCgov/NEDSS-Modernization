/* generated using openapi-typescript-codegen -- do not edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { Clause } from './Clause';
import type { Expr } from './Expr';
export type Connector = (Expr & {
    operator?: Connector.operator;
    left?: (Clause | Connector);
    right?: (Clause | Connector);
} & {
    operator?: Connector.operator;
    left?: any;
    right?: any;
} & {
    operator: Connector.operator;
    left: (Clause | Connector);
    right: (Clause | Connector);
    operator: Connector.operator;
    left: any;
    right: any;
});
export namespace Connector {
    export enum operator {
        OR = 'OR',
        AND = 'AND',
    }
}

