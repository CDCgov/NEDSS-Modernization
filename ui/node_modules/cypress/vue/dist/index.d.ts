/// <reference types="cypress" />
/// <reference types="cypress" />
import type { ComponentPublicInstance, VNodeProps, AllowedComponentProps, ComponentCustomProps, ExtractPropTypes, ExtractDefaultPropTypes, DefineComponent, FunctionalComponent, ComputedOptions, MethodOptions, ComponentOptionsMixin, EmitsOptions, ComponentOptionsWithObjectProps, ComponentPropsOptions, ComponentOptionsWithArrayProps, ComponentOptionsWithoutProps, Prop } from 'vue';
import type { MountingOptions as VTUMountingOptions, VueWrapper } from './@vue/test-utils';
import { StyleOptions } from '@cypress/mount-utils';
import * as _VueTestUtils from './@vue/test-utils';
declare const VueTestUtils: {
    enableAutoUnmount: typeof _VueTestUtils.enableAutoUnmount;
    disableAutoUnmount: typeof _VueTestUtils.disableAutoUnmount;
    RouterLinkStub: DefineComponent<{
        to: {
            type: (ObjectConstructor | StringConstructor)[];
            required: true;
        };
        custom: {
            type: BooleanConstructor;
            default: boolean;
        };
    }, unknown, unknown, {}, {}, ComponentOptionsMixin, ComponentOptionsMixin, Record<string, any>, string, VNodeProps & AllowedComponentProps & ComponentCustomProps, Readonly<ExtractPropTypes<{
        to: {
            type: (ObjectConstructor | StringConstructor)[];
            required: true;
        };
        custom: {
            type: BooleanConstructor;
            default: boolean;
        };
    }>>, {
        custom: boolean;
    }>;
    VueWrapper: typeof VueWrapper;
    DOMWrapper: typeof _VueTestUtils.DOMWrapper;
    BaseWrapper: typeof _VueTestUtils.BaseWrapper;
    config: import("./@vue/test-utils/config").GlobalConfigOptions;
    flushPromises: typeof _VueTestUtils.flushPromises;
    createWrapperError: typeof _VueTestUtils.createWrapperError;
};
export { VueTestUtils };
declare type GlobalMountOptions = Required<VTUMountingOptions<any>>['global'];
declare global {
    namespace Cypress {
        interface Cypress {
            vueWrapper: VueWrapper<ComponentPublicInstance>;
            vue: ComponentPublicInstance;
        }
    }
}
declare type MountingOptions<Props, Data = {}> = Omit<VTUMountingOptions<Props, Data>, 'attachTo'> & {
    log?: boolean;
    /**
     * @deprecated use vue-test-utils `global` instead
     */
    extensions?: GlobalMountOptions & {
        use?: GlobalMountOptions['plugins'];
        mixin?: GlobalMountOptions['mixins'];
    };
} & Partial<StyleOptions>;
export declare type CyMountOptions<Props, Data = {}> = MountingOptions<Props, Data>;
/**
 * The types for mount have been copied directly from the VTU mount
 * https://github.com/vuejs/vue-test-utils-next/blob/master/src/mount.ts.
 *
 * There isn't a good way to make them generic enough that we can extend them.
 *
 * In addition, we modify the types slightly.
 *
 * `MountOptions` are modifying, including some Cypress specific options like `styles`.
 * The return type is different. Instead of VueWrapper, it's Cypress.Chainable<VueWrapper<...>>.
 */
declare type PublicProps = VNodeProps & AllowedComponentProps & ComponentCustomProps;
declare type ComponentMountingOptions<T> = T extends DefineComponent<infer PropsOrPropOptions, any, infer D, any, any> ? MountingOptions<Partial<ExtractDefaultPropTypes<PropsOrPropOptions>> & Omit<Readonly<ExtractPropTypes<PropsOrPropOptions>> & PublicProps, keyof ExtractDefaultPropTypes<PropsOrPropOptions>>, D> & Record<string, any> : MountingOptions<any>;
export declare function mount<V extends {}>(originalComponent: {
    new (...args: any[]): V;
    __vccOpts: any;
}, options?: MountingOptions<any> & Record<string, any>): Cypress.Chainable<VueWrapper<ComponentPublicInstance<V>>>;
export declare function mount<V extends {}, P>(originalComponent: {
    new (...args: any[]): V;
    __vccOpts: any;
    defaultProps?: Record<string, Prop<any>> | string[];
}, options?: MountingOptions<P & PublicProps> & Record<string, any>): Cypress.Chainable<VueWrapper<ComponentPublicInstance<V>>>;
export declare function mount<V extends {}>(originalComponent: {
    new (...args: any[]): V;
    registerHooks(keys: string[]): void;
}, options?: MountingOptions<any> & Record<string, any>): Cypress.Chainable<VueWrapper<ComponentPublicInstance<V>>>;
export declare function mount<V extends {}, P>(originalComponent: {
    new (...args: any[]): V;
    props(Props: P): any;
    registerHooks(keys: string[]): void;
}, options?: MountingOptions<P & PublicProps> & Record<string, any>): Cypress.Chainable<VueWrapper<ComponentPublicInstance<V>>>;
export declare function mount<Props extends {}, E extends EmitsOptions = {}>(originalComponent: FunctionalComponent<Props, E>, options?: MountingOptions<Props & PublicProps> & Record<string, any>): Cypress.Chainable<VueWrapper<ComponentPublicInstance<Props>>>;
export declare function mount<PropsOrPropOptions = {}, RawBindings = {}, D = {}, C extends ComputedOptions = ComputedOptions, M extends MethodOptions = MethodOptions, Mixin extends ComponentOptionsMixin = ComponentOptionsMixin, Extends extends ComponentOptionsMixin = ComponentOptionsMixin, E extends EmitsOptions = Record<string, any>, EE extends string = string, PP = PublicProps, Props = Readonly<ExtractPropTypes<PropsOrPropOptions>>, Defaults extends {} = ExtractDefaultPropTypes<PropsOrPropOptions>>(component: DefineComponent<PropsOrPropOptions, RawBindings, D, C, M, Mixin, Extends, E, EE, PP, Props, Defaults>, options?: MountingOptions<Partial<Defaults> & Omit<Props & PublicProps, keyof Defaults>, D> & Record<string, any>): Cypress.Chainable<VueWrapper<InstanceType<DefineComponent<PropsOrPropOptions, RawBindings, D, C, M, Mixin, Extends, E, EE, PP, Props, Defaults>>>>;
export declare function mount<T extends DefineComponent<any, any, any, any>>(component: T, options?: ComponentMountingOptions<T>): Cypress.Chainable<VueWrapper<InstanceType<T>>>;
export declare function mount<Props = {}, RawBindings = {}, D extends {} = {}, C extends ComputedOptions = {}, M extends Record<string, Function> = {}, E extends EmitsOptions = Record<string, any>, Mixin extends ComponentOptionsMixin = ComponentOptionsMixin, Extends extends ComponentOptionsMixin = ComponentOptionsMixin, EE extends string = string>(componentOptions: ComponentOptionsWithoutProps<Props, RawBindings, D, C, M, E, Mixin, Extends, EE>, options?: MountingOptions<Props & PublicProps, D>): Cypress.Chainable<VueWrapper<ComponentPublicInstance<Props, RawBindings, D, C, M, E, VNodeProps & Props>>> & Record<string, any>;
export declare function mount<PropNames extends string, RawBindings, D extends {}, C extends ComputedOptions = {}, M extends Record<string, Function> = {}, E extends EmitsOptions = Record<string, any>, Mixin extends ComponentOptionsMixin = ComponentOptionsMixin, Extends extends ComponentOptionsMixin = ComponentOptionsMixin, EE extends string = string, Props extends Readonly<{
    [key in PropNames]?: any;
}> = Readonly<{
    [key in PropNames]?: any;
}>>(componentOptions: ComponentOptionsWithArrayProps<PropNames, RawBindings, D, C, M, E, Mixin, Extends, EE, Props>, options?: MountingOptions<Props & PublicProps, D>): Cypress.Chainable<VueWrapper<ComponentPublicInstance<Props, RawBindings, D, C, M, E>>>;
export declare function mount<PropsOptions extends Readonly<ComponentPropsOptions>, RawBindings, D extends {}, C extends ComputedOptions = {}, M extends Record<string, Function> = {}, E extends EmitsOptions = Record<string, any>, Mixin extends ComponentOptionsMixin = ComponentOptionsMixin, Extends extends ComponentOptionsMixin = ComponentOptionsMixin, EE extends string = string>(componentOptions: ComponentOptionsWithObjectProps<PropsOptions, RawBindings, D, C, M, E, Mixin, Extends, EE>, options?: MountingOptions<ExtractPropTypes<PropsOptions> & PublicProps, D>): Cypress.Chainable<VueWrapper<ComponentPublicInstance<ExtractPropTypes<PropsOptions>, RawBindings, D, C, M, E, VNodeProps & ExtractPropTypes<PropsOptions>>>>;
/**
 * Helper function for mounting a component quickly in test hooks.
 * @example
 *  import {mountCallback} from '@cypress/vue'
 *  beforeEach(mountVue(component, options))
 */
export declare function mountCallback(component: any, options?: any): () => Cypress.Chainable;
