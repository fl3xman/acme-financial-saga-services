import { Observable, defer, from, forkJoin, of } from "rxjs";
import { concatMap, map, tap, concatMapTo } from "rxjs/operators";
import { ContainerModule, interfaces } from "inversify";

import { keyTypeOf } from "../../boot";
import { inversifyOptionalGet } from "../../inversify";
import { LoggerFactory, LoggerAssembly, LoggerTag } from "../../logger";

import { ManifestLoaderFactory } from "../ManifestLoaderFactory";
import { ManifestAssembly } from "../ManifestAssembly";
import { Manifest } from "../Manifest";
import { AnyManifestAttribute, ManifestAttribute } from "../ManifestAttribute";
import { ManifestBuilderFactory } from "../ManifestBuilderFactory";
import { defaultScriptManifestBuilder, defaultStyleManifestBuilder } from "../builders";

export const autoconfigure = (): ContainerModule => {
    return new ContainerModule((bind, _, isBound) => {
        // Autoconfigurtion checks for bindings
        if (!isBound(ManifestAssembly.Factory)) {
            bind<interfaces.Factory<ManifestLoaderFactory>>(ManifestAssembly.Factory).toFactory<Observable<Manifest>>(
                (ctx) => (host: string) => {
                    const logger = inversifyOptionalGet<LoggerFactory>(ctx.container, LoggerAssembly.Factory)?.(LoggerTag.Manifest);
                    return defer(() => from(fetch(`${host}/manifest.json`))).pipe(
                        concatMap((response) => from(from(response.json()))),
                        map((json) => {
                            if (keyTypeOf<Manifest, AnyManifestAttribute>(json, ManifestAttribute.MainScript)) {
                                return json;
                            }
                            throw TypeError(`Fetched json response=${JSON.stringify(json)} is not Manifest.`);
                        }),
                        tap({
                            next: (result) => logger?.debug(`Manifest loader result = ${JSON.stringify(result)}`),
                            error: (error) => logger?.error(`Manifest loader failed with error=${JSON.stringify(error)}.`),
                        }),
                    );
                },
            );
        }

        // Autoconfigurtion checks for bindings
        if (!isBound(ManifestAssembly.BuilderFactory)) {
            bind<interfaces.Factory<ManifestBuilderFactory>>(ManifestAssembly.BuilderFactory).toFactory<Observable<void>>(
                (ctx) => (url: string, id: string) => {
                    const logger = inversifyOptionalGet<LoggerFactory>(ctx.container, LoggerAssembly.Factory)?.(LoggerTag.Manifest);
                    const builder = ctx.container.get<ManifestLoaderFactory>(ManifestAssembly.Factory)(url);

                    return builder.pipe(
                        concatMap((manifest) => {
                            return forkJoin(
                                defaultStyleManifestBuilder({ url, id, manifest, logger }),
                                defaultScriptManifestBuilder({ url, id, manifest, logger }),
                            );
                        }),
                        concatMapTo(of<void>()),
                    );
                },
            );
        }
    });
};
