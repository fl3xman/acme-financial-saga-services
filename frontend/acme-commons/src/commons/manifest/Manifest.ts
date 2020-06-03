import { ManifestAttribute } from "./ManifestAttribute";

export interface Manifest {
    [key: string]: string;
    [ManifestAttribute.MainScript]: string;
    [ManifestAttribute.MainStyle]: string;
}
