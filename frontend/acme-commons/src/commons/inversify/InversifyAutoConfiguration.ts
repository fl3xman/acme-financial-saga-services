import { interfaces } from "inversify";

import { autoconfigure as ManifestAutoConfigure } from "../manifest/autoconfigure";

export const inversifyAutoConfiguration = (container: interfaces.Container): void => {
    container.load(
        ManifestAutoConfigure()
    );
}