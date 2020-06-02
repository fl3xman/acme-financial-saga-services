import * as React from "react";
import { useEffect, useState } from "react";

import { useInversify } from "../hooks/inversify";
import { useLoggerFactory } from "../hooks/logger";
import { useJoinPoint, hasJoinPointId } from "../hooks/mediator";
import { LoggerTag } from "../logger";
import { ManifestAssembly, ManifestBuilderFactory } from "../manifest";

import { MediatorProps } from "./MediatorProps";

export const Mediator: React.FC<MediatorProps> = (props) => {

    const [joinId] = useState(`${props.namespace}-${props.name}-joinpoint`);
    const [scriptId] = useState(`${joinId}-script`);

    const logger = useLoggerFactory(LoggerTag.Mediator);
    const container = useInversify();

    if (hasJoinPointId(scriptId)) {
        useEffect(() => {
            const { namespace, name, history } = props;

            logger?.debug(`Mediator ${namespace}:${name} will mount.`);
            useJoinPoint(namespace, name).mount(joinId, history, container.createChild());
        });
    } else {
        useEffect(() => {
            const { host, namespace, name, history } = props;
            const disposable = container
                .get<ManifestBuilderFactory>(ManifestAssembly.BuilderFactory)(host, joinId)
                .subscribe({
                    complete: () => {

                        logger?.debug(`Mediator ${namespace}:${name} will mount.`);
                        useJoinPoint(namespace, name).mount(joinId, history, container.createChild());
                    }
                });

            return () => {
                disposable.unsubscribe();
            };
        }, [props]);
    }

    useEffect(() => {
        return () => {
            const { namespace, name } = props;

            logger?.debug(`Mediator ${namespace}:${name} will unmount.`);
            useJoinPoint(namespace, name).unmount(joinId)
        };
    }, [props]);

    return (
        <main id={joinId} />
    );
}