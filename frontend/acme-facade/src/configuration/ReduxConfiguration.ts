import { createStore, Store } from "redux";
import { composeWithDevTools } from "redux-devtools-extension";
import { ContainerModule, interfaces } from "inversify";
import { ReduxRegistryAssembly } from "acme-commons";

import { ReduxStoreAssembly, ReduxRegistry, combineRigistry } from "../commons/redux";

export const configuration = (): ContainerModule => {
    return new ContainerModule((bind) => {
        bind<interfaces.Factory<() => Store>>(ReduxStoreAssembly.Factory).toFactory<Store>((ctx) => () => {
            const state = {};
            const registry = ctx.container.get<ReduxRegistry>(ReduxRegistryAssembly.Instance);
            const reducer = combineRigistry(registry.reducers.value, state);
            const store = createStore(reducer, composeWithDevTools());

            registry.reducers.subscribe({
                next: (reducers) => {
                    store.replaceReducer(combineRigistry(reducers, state));
                },
            });

            return store;
        });

        bind<interfaces.Factory<() => ReduxRegistry>>(ReduxRegistryAssembly.Factory).toFactory<ReduxRegistry>(() => () => {
            const registry = new ReduxRegistry();
            return registry;
        });

        bind<Store>(ReduxStoreAssembly.Instance)
            .toDynamicValue((ctx) => {
                return ctx.container.get<() => Store>(ReduxStoreAssembly.Factory)();
            })
            .inSingletonScope();

        bind<ReduxRegistry>(ReduxRegistryAssembly.Instance)
            .toDynamicValue((ctx) => {
                return ctx.container.get<() => ReduxRegistry>(ReduxRegistryAssembly.Factory)();
            })
            .inSingletonScope();
    });
};
