import * as React from "react";

import { useEffect, useState, useContext } from "react";
import { Container, interfaces } from "inversify";
import { inversifyAutoConfiguration } from "../../inversify";

import { InversifyProviderProps } from "./InversifyProviderProps";

const InversifyContext = React.createContext<InversifyProviderProps>({ container: undefined });

export const InversifyProvider: React.FC<InversifyProviderProps> = (props) => {
    const [container] = useState(props.container || new Container());

    useEffect(() => {
        inversifyAutoConfiguration(container)
    }, [container, props])

    useEffect(() => {
        if (props.configurations) {
            container.load(...props.configurations);
        }
    }, [container, props])

    useEffect(() => {
        (async () => {
            if (props.asyncConfigurations) {
                await container.loadAsync(...props.asyncConfigurations);
            }
        })();
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
}

export const useInversify = (): interfaces.Container => {
    const context = useContext(InversifyContext);
    if (!context.container) {
        throw Error("Inversify context was not found.")
    }

    return context.container;
};