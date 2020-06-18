/* eslint-disable @typescript-eslint/no-explicit-any */
interface ActionLike<T = any> {
    type: T;
}

type AnyActionLike = ActionLike;
type ReducerLike<S = any, A extends ActionLike = AnyActionLike> = (state: S | undefined, action: A) => S;

export interface ReduxRegistryAware {
    readonly combinedReducers: ReducerLike;

    add(name: string, reducer: ReducerLike): ReduxRegistryAware;
    remove(name: string): ReduxRegistryAware;
}
