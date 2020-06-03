import { Observable } from "rxjs";

import { keyTypeOf } from "../../boot";
import { ManifestAttribute } from "../ManifestAttribute";
import { ManifestBuildAware } from "../ManifestBuildAware";

export const defaultStyleManifestBuilder = (built: ManifestBuildAware): Observable<void> => {
    return new Observable<void>((subscribe) => {
        if (keyTypeOf(built.manifest, ManifestAttribute.MainStyle)) {
            const link = document.createElement("link");
            link.id = `${built.id}-style`;
            link.rel = "stylesheet";
            link.href = `${built.url}${built.manifest[ManifestAttribute.MainStyle]}`;
            link.onload = () => {
                built.logger?.debug(`Manifest style=${built.id} built successfully.`);
                subscribe.complete();
            };

            document.head.appendChild(link);
        } else {
            built.logger?.debug(`Manifest does not contains style.`);
            subscribe.complete();
        }
    });
};
