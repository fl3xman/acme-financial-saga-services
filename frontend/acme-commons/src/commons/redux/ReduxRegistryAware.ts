/* eslint-disable @typescript-eslint/no-explicit-any */
interface ActionAware<T = any> {
    type: T;
}

type AnyActionAware = ActionAware;
type ReducerAware<S = any, A extends ActionAware = AnyActionAware> = (state: S | undefined, action: A) => S;

export interface ReduxRegistryAware {
    add(name: string, reducer: ReducerAware): ReduxRegistryAware;
    remove(name: string): ReduxRegistryAware;
}
