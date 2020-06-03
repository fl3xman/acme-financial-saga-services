import * as React from "react";

import { from, Subscription } from "rxjs";
import { useEffect, useState, useContext } from "react";
import { Container, interfaces } from "inversify";

import { useAutoConfiguration } from "./InversifyAutoConfiguration";
import { InversifyProviderProps } from "./InversifyProviderProps";

const InversifyContext = React.createContext<InversifyProviderProps>({ container: undefined });

export const InversifyProvider: React.FC<InversifyProviderProps> = (props: React.PropsWithChildren<InversifyProviderProps>) => {
    const [container] = useState(useAutoConfiguration(props.container || new Container()));

    useEffect(() => {
        if (props.configurations) {
            container.load(...props.configurations);
        }
    }, [container, props]);

    useEffect(() => {
        let disposable: Subscription | null;
        if (props.asyncConfigurations) {
            disposable = from(container.loadAsync(...props.asyncConfigurations)).subscribe();
        }

        return () => {
            disposable?.unsubscribe();
        };
    }, [container, props]);

    useEffect(() => {
        return () => {
            container.unbindAll();
        };
    }, [container]);

    return (
        <InversifyContext.Provider key={props.id} value={{ container }}>
            {props.children}
        </InversifyContext.Provider>
    );
};

export const useInversify = (): interfaces.Container => {
    const context = useContext(InversifyContext);
    if (!context.container) {
        throw Error("Inversify context was not found.");
    }

    return context.container;
};
