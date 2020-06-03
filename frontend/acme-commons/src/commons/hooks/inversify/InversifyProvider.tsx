import * as React from "react";

import { useEffect, useState, useContext } from "react";
import { Container, interfaces } from "inversify";

import { useAutoConfiguration } from "./InversifyAutoConfiguration";
import { InversifyProviderProps } from "./InversifyProviderProps";

const InversifyContext = React.createContext<InversifyProviderProps>({ container: undefined });

export const InversifyProvider: React.FC<InversifyProviderProps> = (props: React.PropsWithChildren<InversifyProviderProps>) => {
    const [container] = useState(useAutoConfiguration(props.container || new Container()));

    if (props.configurations) {
        container.load(...props.configurations);
    }

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
