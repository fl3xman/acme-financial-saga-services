import { Observable } from "rxjs";

import { keyTypeOf } from "../../boot";
import { ManifestAttribute } from "../ManifestAttribute";
import { ManifestBuildAware } from "../ManifestBuildAware";

export const defaultScriptManifestBuilder = (build: ManifestBuildAware): Observable<void> => {
    return new Observable<void>((subscribe) => {
        if (keyTypeOf(build.manifest, ManifestAttribute.MainScript)) {
            const script = document.createElement("script");
            script.id = `${build.id}-script`;
            script.src = `${build.url}/${build.manifest[ManifestAttribute.MainScript]}`;
            script.onload = () => {
                build.logger?.debug(`Manifest script=${build.id} built successfully.`)
                subscribe.complete();
            };

            document.head.appendChild(script);
        } else {
            build.logger?.debug(`Manifest does not contains script.`);
            subscribe.complete();
        }
    });
};
