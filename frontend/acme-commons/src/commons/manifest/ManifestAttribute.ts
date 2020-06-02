export enum ManifestAttribute {
    MainScript = "main.js",
    MainStyle = "main.css",
}

export type AnyManifestAttribute = ManifestAttribute.MainScript | ManifestAttribute.MainStyle;