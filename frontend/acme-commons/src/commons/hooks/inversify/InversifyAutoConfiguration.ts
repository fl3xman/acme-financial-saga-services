import { interfaces } from "inversify";

import { autoconfigure as ManifestAutoConfigure } from "../../manifest/autoconfigure";

export const useAutoConfiguration = (container: interfaces.Container): interfaces.Container => {
    container.load(ManifestAutoConfigure());
    return container;
};
